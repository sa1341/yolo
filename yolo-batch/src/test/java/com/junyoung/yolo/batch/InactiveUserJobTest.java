package com.junyoung.yolo.batch;

import com.junyoung.yolo.batch.entity.UserRepository;
import com.junyoung.yolo.batch.entity.enums.UserStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.batch.runtime.BatchStatus;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InactiveUserJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 휴먼_회원_전환_테스트() throws Exception {
        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(BatchStatus.COMPLETED).isEqualTo(jobExecution.getStatus());
        assertThat(0).isEqualTo(userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1)
                , UserStatus.ACTIVE).size());
     }
}
