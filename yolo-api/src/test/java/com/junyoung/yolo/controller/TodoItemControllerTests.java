package com.junyoung.yolo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junyoung.yolo.RestDocumentTests;
import com.junyoung.yolo.domain.todo.service.TodoService;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.junyoung.yolo.doc.ApiDocumentUtils.getDocumentRequest;
import static com.junyoung.yolo.doc.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoItemControllerTests extends RestDocumentTests {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @Transactional
    @Test
    public void todoItem_저장을_한다() throws Exception {
        //given
        TodoItemRequest todoItemRequest = new TodoItemRequest();
        todoItemRequest.setText("어디로 가야하오?");
        todoItemRequest.setDone(true);
        todoItemRequest.setMemberId("a79007714@gmail.com");

        TodoItemResponse todoItemResponse = new TodoItemResponse("0e896b67-7437-406b-b44d-e01371e06498", "이것을 리턴한다!!",
                true, "a79007714@gmail.com");

        given(todoService.saveTodoItem(any())).willReturn(todoItemResponse);
        //when(todoService.saveTodoItem(todoItemRequest)).thenReturn(todoItemResponse);

        // when
        ResultActions result = this.mockMvc.perform(post("/api/v1/todos")
                .content(objectMapper.writeValueAsString(todoItemRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        result.andExpect(status().isOk())
                .andDo(document("saveTodoItem",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("text").type(JsonFieldType.STRING).description("오늘의 할 일"),
                                fieldWithPath("done").type(JsonFieldType.BOOLEAN).description("업무 완료 여"),
                                fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("TodoItem 식별 "),
                                fieldWithPath("text").type(JsonFieldType.STRING).description("오늘의 할 일"),
                                fieldWithPath("done").type(JsonFieldType.BOOLEAN).description("업무 완료 여부"),
                                fieldWithPath("memberId").type(JsonFieldType.STRING).description("회원 ID"))
                ));
    }

    @Transactional
    @Test
    public void todoItem_날짜_조회_테스트() throws Exception {
        //given
        String startDate = "2021-04-28";
        String endDate = "2021-04-29";
        String id ="a79007714@gmail.com";

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("selectedStartDate", startDate);
        info.add("selectedEndDate", endDate);

        List<TodoItemResponse> todoItemResponseList = new ArrayList<>();

        TodoItemResponse todoItemResponse = new TodoItemResponse("d57f0343-2247-43c2-b215-97b882fbeb47",
                "안녕하시렵니까?", true, "a79007714@gmail.com");
        todoItemResponseList.add(todoItemResponse);

        given(todoService.findTodoItemsByDate(any(), any(), any())).willReturn(todoItemResponseList);

        //when
        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/todos/{id}", id)
                //.params(info)
                .param("selectedStartDate", startDate)
                .param("selectedEndDate", endDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(document( "findTodoItemByDate",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        requestParameters(
                                parameterWithName("selectedStartDate").description("조회 시작 일자"),
                                parameterWithName("selectedEndDate").description("조회 종료 일자")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.STRING).description("TodoItem 식별 값"),
                                fieldWithPath("[].text").type(JsonFieldType.STRING).description("오늘의 할 일"),
                                fieldWithPath("[].done").type(JsonFieldType.BOOLEAN).description("업무 완료 여부"),
                                fieldWithPath("[].memberId").type(JsonFieldType.STRING).description("회원 ID"))
                ));
     }
}
