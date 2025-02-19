package org.cibseven.bpm.example;

import org.cibseven.bpm.engine.RuntimeService;
import org.cibseven.bpm.engine.runtime.Execution;
import org.cibseven.bpm.engine.runtime.ProcessInstance;
import org.cibseven.bpm.engine.variable.Variables;
import org.cibseven.community.process_test_coverage.spring_test.platform7.ProcessEngineCoverageConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.cibseven.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import({CoverageTestConfiguration.class, ProcessEngineCoverageConfiguration.class})
public class insuranceTest {

        private static final String PROCESS_DEFINITION_KEY = "insurance";

        @Autowired
        public RuntimeService runtimeService;

        private Map<String, Object> variables;

        @Test
        public void shouldExecuteHappyPath() {
                // Define scenarios by using camunda-bpm-assert-scenario:
                variables = Variables.createVariables();
                variables.put("id", "id");
                variables.put("name", "name");
                variables.put("hasSocialSecurity", true);
                variables.put("hasOtherInsurance", true);
                ProcessInstance instance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
                                variables);

                assertThat(instance).isWaitingAt("IntermediateThrowEvent_ReceiveInsuranceContract");

                assertThat(instance).variables().containsEntry("premium", 600L);

                Execution execution = runtimeService.createExecutionQuery().processInstanceId(instance.getId())
                                .messageEventSubscriptionName("Message_ReceiveInsuranceContract").singleResult();

                runtimeService.messageEventReceived("Message_ReceiveInsuranceContract", execution.getId());

                assertThat(instance)
                                .hasPassed("EndEvent_InsuranceConfirmed")
                                .isEnded();
        }

        @Test
        public void shouldExecuteHappyPath1() {
                // Define scenarios by using camunda-bpm-assert-scenario:
                variables = Variables.createVariables();
                variables.put("id", "id");
                variables.put("name", "name");
                variables.put("hasSocialSecurity", true);
                variables.put("hasOtherInsurance", false);
                ProcessInstance instance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
                                variables);

                assertThat(instance).isWaitingAt("Task_CheckInsuranceCase");

                complete(task(), withVariables("isRiskManagable", true));

                assertThat(instance).variables().containsEntry("premium", 1000L);

                Execution execution = runtimeService.createExecutionQuery().processInstanceId(instance.getId())
                                .messageEventSubscriptionName("Message_ReceiveInsuranceContract").singleResult();
                runtimeService.messageEventReceived("Message_ReceiveInsuranceContract", execution.getId());

                assertThat(instance)
                                .hasPassed("EndEvent_InsuranceConfirmed")
                                .isEnded();
        }

        @Test
        public void missData() {
                variables = Variables.createVariables();
                variables.put("id", "id");
                variables.put("hasSocialSecurity", false);
                variables.put("hasOtherInsurance", true);
                ProcessInstance instance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
                                variables);

                assertThat(instance)
                                .hasPassed("EndEvent_NeedMoreDocument")
                                .isEnded();
        }

        @Test
        public void shouldRejectCondition1() {
                variables = Variables.createVariables();
                variables.put("id", "id");
                variables.put("name", "name");
                variables.put("hasSocialSecurity", false);
                variables.put("hasOtherInsurance", true);
                ProcessInstance instance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
                                variables);

                assertThat(instance)
                                .hasPassed("EndEvent_InsuranceRejected_NotQualify")
                                .isEnded();
        }

        @Test
        public void shouldRejectCondition2() {
                variables = Variables.createVariables();
                variables.put("id", "id");
                variables.put("name", "name");
                variables.put("hasSocialSecurity", true);
                variables.put("hasOtherInsurance", false);
                ProcessInstance instance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
                                variables);

                assertThat(instance).isWaitingAt("Task_CheckInsuranceCase");

                complete(task(), withVariables("isRiskManagable", false));

                assertThat(instance)
                                .hasPassed("EndEvent_InsuranceRejected_Risky")
                                .isEnded();

        }

}
