package org.camunda.bpm.example;

import org.camunda.community.process_test_coverage.spring_test.common.ProcessEngineCoverageProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CoverageTestConfiguration {

    @Bean
    public ProcessEngineCoverageProperties processEngineCoverageProperties() {
        return ProcessEngineCoverageProperties.builder()
                .assertClassCoverageAtLeast(0.9)
                .build();
    }

}
