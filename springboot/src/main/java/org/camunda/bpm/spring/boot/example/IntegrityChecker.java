package org.camunda.bpm.spring.boot.example;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.spring.boot.starter.ClientProperties;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ExternalTaskSubscription("integrityChecker")
public class IntegrityChecker implements ExternalTaskHandler {

  protected static Logger LOGGER = LoggerFactory.getLogger(HandlerConfiguration.class);

    @Override
    public void execute(ExternalTask externalTask, 
                        ExternalTaskService externalTaskService) {
      LOGGER.info("開始檢查投保申請資料的完整性");


      boolean isDocumentComplete = checkRequestIntegrity(externalTask);
      LOGGER.info("申請資料是完整的嗎？" + isDocumentComplete);

      // add the invoice object and its id to a map
      Map<String, Object> variables = new HashMap<>();
      variables.put("isDocumentComplete", isDocumentComplete);

      if (!isDocumentComplete) {
        variables.put("declineMessage", "投保申請資料不完整，請核對併補充相關資料。");
      }

      // complete the external task
      externalTaskService.complete(externalTask, variables);

      LOGGER.info("The External Task " + externalTask.getId() + " has been completed!");
    };

    private boolean checkRequestIntegrity(ExternalTask execution) {
      // TODO: 檢查投保申請資料的完整性
      String id = (String) execution.getVariable("id");
      String name = (String) execution.getVariable("name");
      System.out.println(id + ": " + name);
  
      return id != null && !id.isEmpty() && name != null && !name.isEmpty();
    }

}
