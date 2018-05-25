package com.example.demo.procurement.integration.flows;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.scripting.dsl.Scripts;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

//@Configuration

@Component
@ConfigurationProperties
@Configuration
class FindPlantsFlows {


    @Configuration
    @PropertySource("classpath:credentials.properties")
    @ConfigurationProperties
    @Data
    class CredentialsProperties {
        private Map<String, Map<String, String>> credentials;
    }

    static class BasicSecureSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
        @Autowired
        CredentialsProperties credentials;

        public BasicSecureSimpleClientHttpRequestFactory() {}

        public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
            ClientHttpRequest result = super.createRequest(uri, httpMethod);

            for (Map<String, String> map: credentials.getCredentials().values()) {
                String authority = map.get("authority");
                if (authority != null && authority.equals(uri.getAuthority())) {
                    result.getHeaders().add("Authorization", map.get("authorization"));
                    break;
                }
            }
            return result;
        }
    }

    @Bean
    public ClientHttpRequestFactory requestFactory() {
        return new BasicSecureSimpleClientHttpRequestFactory();
    }


    @Bean
    IntegrationFlow scatterComponent() {
        return IntegrationFlows.from("req-channel")
                .publishSubscribeChannel(conf ->
                        conf.applySequence(true)
                                //TODO Will use Gather and Scatter once we implment the final project
                                //.subscribe(f -> f.channel("rentmt-channel"))
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
                .handle("findPlantsCustomTransformer", "fromJson")
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
                        .httpMethod(HttpMethod.GET).requestFactory(requestFactory())
                        .expectedResponseType(String.class)
                )
                .handle("findPlantsCustomTransformer", "fromHALForms")
                .channel("gather-channel")
                .get();
    }
}
