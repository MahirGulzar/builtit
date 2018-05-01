package com.example.demo.rental.integration.flows;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.scripting.dsl.Scripts;

import java.util.stream.Collectors;

@Configuration
class CreatePurcahseOrderFlows {

    @Bean
    IntegrationFlow rentItFlow() {
        return IntegrationFlows.from("req-po-channel")
                .handle(Http.outboundGateway("http://localhost:8090/api/sales/orders")
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(String.class)
                )
                .handle("createPurchaseOrderCustomTransformer", "fromHALForms")
                .channel("rep-po-channel")
                .get();
    }
}
