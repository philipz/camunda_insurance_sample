package org.cibseven.bpm.example;

import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.cibseven.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("qualificationChecker")
public class QualificationCheckerDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(QualificationCheckerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("開始審核申請人的保險資格");

        String isQualified = checkInsuranceQualification(execution);
        execution.setVariable("isQualified", isQualified);
    }

    private String checkInsuranceQualification(DelegateExecution execution) {
        Boolean hasSocialSecurity = (Boolean) execution.getVariable("hasSocialSecurity");
        Boolean hasOtherInsurance = (Boolean) execution.getVariable("hasOtherInsurance");

        //TODO: 檢查申請人資格，此處僅是根據申請人是否有健保和商業保險來進行判斷
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
