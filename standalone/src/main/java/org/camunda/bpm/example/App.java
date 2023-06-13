package org.camunda.bpm.example;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;

public class App {
  static Logger logger = Logger.getLogger(App.class.getName());

  public static void main(String[] args) {

    // bootstrap the client
    ExternalTaskClient client = ExternalTaskClient.create()
        .baseUrl("http://localhost:8080/engine-rest")
        .build();

    // subscribe to the topic
    client.subscribe("integrityChecker")
        .lockDuration(1000)
        .handler((externalTask, externalTaskService) -> {

          logger.log(Level.INFO, "開始檢查投保申請資料的完整性");

          boolean isDocumentComplete = checkRequestIntegrity(externalTask);
          logger.log(Level.INFO, "申請資料是完整的嗎？" + isDocumentComplete);

          // add the invoice object and its id to a map
          Map<String, Object> variables = new HashMap<>();
          variables.put("isDocumentComplete", isDocumentComplete);

          if (!isDocumentComplete) {
            variables.put("declineMessage", "投保申請資料不完整，請核對併補充相關資料。");
          }

          // complete the external task
          externalTaskService.complete(externalTask, variables);

          logger.log(Level.INFO, "The External Task " + externalTask.getId() + " has been completed!");

        }).open();

  }

  private static boolean checkRequestIntegrity(ExternalTask execution) {
    // TODO: 檢查投保申請資料的完整性
    String id = (String) execution.getVariable("id");
    String name = (String) execution.getVariable("name");
    System.out.println(id + ": " + name);

    return id != null && !id.isEmpty() && name != null && !name.isEmpty();
  }
}
