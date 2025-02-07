package org.cibseven.bpm.example;

import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.cibseven.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("insuranceContractProvider")
public class InsuranceContractProviderDelegate implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(InsuranceContractProviderDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("開始準備保單");

        Long premium = (Long) execution.getVariable("premium");
        String contractMessage = prepareInsuranceContract(premium);
        LOGGER.info(contractMessage);

        execution.setVariable("contractMessage", contractMessage);
    }

    private String prepareInsuranceContract(Long premium) {
        //TODO: 準備保單
        return "恭喜！核保通過，保費為每年：" + premium + "。";
    }
}
