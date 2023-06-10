package org.camunda.bpm.example;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("declineMessageProvider")
public class DeclineMessageProviderDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(DeclineMessageProviderDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("開始準備拒保通知");

        String declineMessage = prepareDeclineMessage();

        execution.setVariable("declineMessage", declineMessage);
    }

    private String prepareDeclineMessage() {
        //TODO: 準備拒保通知
        return "抱歉！保險申請被拒絕，請聯繫客服中心以便獲得更詳細資訊。";
    }
}
