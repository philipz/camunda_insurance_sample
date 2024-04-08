package org.camunda.bpm.example;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.community.process_test_coverage.spring_test.platform7.ProcessEngineCoverageConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import({CoverageTestConfiguration.class, ProcessEngineCoverageConfiguration.class})
public class rejectinsuranceTest {

        private static final String PROCESS_DEFINITION_KEY = "rejectInsurance";

        @Autowired
        public RuntimeService runtimeService;

        @Test
        public void shouldExecuteHappyPath() {

                ProcessInstance instance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

                assertThat(instance)
                                .hasPassed("EndEvent_InsuranceRejected")
                                .isEnded();
        }

}
