package com.junyoung.yolo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.junyoung.yolo.doc.ApiDocumentUtils.getDocumentRequest;
import static com.junyoung.yolo.doc.ApiDocumentUtils.getDocumentResponse;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Transactional
    @DisplayName("TodoItem을 저장하는 REST API를 테스트 한다.")
    @Test
    public void todoItem_저장을_한다() throws Exception {

        //given
        TodoItemRequest todoItemRequest = new TodoItemRequest();
        todoItemRequest.setText("어디로 가야하오?");
        todoItemRequest.setDone(true);
        todoItemRequest.setMemberId("a79007714@gmail.com");

        TodoItemResponse todoItemResponse = new TodoItemResponse("0e896b67-7437-406b-b44d-e01371e06498", "이것을 리턴한다!!",
                true,"a79007714@gmail.com");

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/todos")
                .content(objectMapper.writeValueAsString(todoItemRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("success")))
                .andDo(document("todoItem",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("text").type(JsonFieldType.STRING).description("오늘의 할 일"),
                                fieldWithPath("isDone").type(JsonFieldType.BOOLEAN).description("업무 완료 여"),
                                fieldWithPath("memberId").type(JsonFieldType.BOOLEAN).description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("text").type(JsonFieldType.STRING).description("오늘의 할 일"),
                                fieldWithPath("isDone").type(JsonFieldType.BOOLEAN).description("업무 완료 여부"),
                                fieldWithPath("memberId").type(JsonFieldType.BOOLEAN).description("회원 ID")                        )
                ));
        //then
     }

}
