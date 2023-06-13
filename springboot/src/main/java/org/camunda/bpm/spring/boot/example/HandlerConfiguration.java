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

@Configuration
@ExternalTaskSubscription("qualificationChecker")
public class HandlerConfiguration implements ExternalTaskHandler {

  protected static Logger LOGGER = LoggerFactory.getLogger(HandlerConfiguration.class);

    @Override
    public void execute(ExternalTask externalTask, 
                        ExternalTaskService externalTaskService) {
      LOGGER.info("開始審核申請人的保險資格");

      String isQualified = checkInsuranceQualification(externalTask);
      externalTaskService.complete(externalTask, Variables.putValue("isQualified", isQualified));
    };

  private String checkInsuranceQualification(ExternalTask execution) {
    Boolean hasSocialSecurity = (Boolean) execution.getVariable("hasSocialSecurity");
    Boolean hasOtherInsurance = (Boolean) execution.getVariable("hasOtherInsurance");

    // TODO: 檢查申請人資格，此處僅是根據申請人是否有健保和商業保險來進行判斷
    String result = "";
    if (hasSocialSecurity) {
      if (hasOtherInsurance) {
        result = "yes";
        LOGGER.info("申請人保險資格查驗結果：合格");
      } else {
        result = "other";
        LOGGER.info("申請人保險資格查驗結果：需人工審核");
      }
    } else {
      result = "no";
      LOGGER.info("申請人保險資格查驗結果：不合格");
    }

    return result;
  }

}
