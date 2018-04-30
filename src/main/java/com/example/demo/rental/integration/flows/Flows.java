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
class Flows {

    @Bean
    IntegrationFlow scatterComponent() {
        return IntegrationFlows.from("req-channel")
                .publishSubscribeChannel(conf ->
                        conf.applySequence(true)
                                .subscribe(f -> f.channel("rentmt-channel"))
                                .subscribe(f -> f.channel("rentit-channel"))
                )
                .get();
    }

    @Bean
    IntegrationFlow gatherComponent() {
        return IntegrationFlows.from("gather-channel")
                .aggregate(spec -> spec.outputProcessor(proc ->
                        new Resources<>(
                                proc.getMessages()
                                        .stream()
                                        .map(msg -> ((Resources) msg.getPayload()).getContent())
                                        .collect(Collectors.toList())))
                        .groupTimeout(2000)
                        .releaseStrategy(group -> group.size() > 1)
                        .sendPartialResultOnExpiry(true))
                .channel("rep-channel")
                .get();
    }

    @Bean
    IntegrationFlow rentMTFlow() {
        return IntegrationFlows.from("rentmt-channel")
                .handle(Http.outboundGateway("http://localhost:8088/api/v1/plant?filter[plant]=name==*{name}*")
                        .uriVariable("name", "payload")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class)
                )
                .transform(Scripts.processor("classpath:/JsonApi2HAL.js")
                        .lang("javascript"))
                .handle("customTransformer", "fromJson")
                .channel("gather-channel")
                .get();
    }

    @Bean
    IntegrationFlow rentItFlow() {
        return IntegrationFlows.from("rentit-channel")
                .handle(Http.outboundGateway("http://localhost:8090/api/sales/plants?name={name}&startDate={startDate}&endDate={endDate}")
                        .uriVariable("name", "payload")
                        .uriVariable("startDate", "headers.startDate")
                        .uriVariable("endDate", "headers.endDate")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class)
                )
                .handle("customTransformer", "fromHALForms")
                .channel("gather-channel")
                .get();
    }
}
