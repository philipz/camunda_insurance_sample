package org.cibseven.bpm.example;

import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.cibseven.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("integrityChecker")
public class IntegrityCheckerDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(IntegrityCheckerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("開始檢查投保申請資料的完整性");

        boolean isDocumentComplete = checkRequestIntegrity(execution);
        LOGGER.info("申請資料是完整的嗎？" + isDocumentComplete);

        execution.setVariable("isDocumentComplete", isDocumentComplete);

        if (!isDocumentComplete) {
            execution.setVariable("declineMessage", "投保申請資料不完整，請核對併補充相關資料。");
        }
    }

    private boolean checkRequestIntegrity(DelegateExecution execution) {
        //TODO: 檢查投保申請資料的完整性
        String id = (String) execution.getVariable("id");
        String name = (String) execution.getVariable("name");
        LOGGER.fine(id + ": " + name);

        return id != null && !id.isEmpty() && name != null && !name.isEmpty();
    }
}
