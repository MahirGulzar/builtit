package com.example.demo.invoice.integration.flows;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Component
@ConfigurationProperties
@Configuration
class RemittanceFlow {

//    @Value("${rentItUri.remittance}")
//    String remittanceUri;

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
    IntegrationFlow sendRemittance() {
        return IntegrationFlows.from("sendRemittanceHTTP")
                .handle(Http
                        .outboundChannelAdapter("http://localhost:8090/api/invoicing/remittance")
                        .httpMethod(HttpMethod.POST).requestFactory(requestFactory()))
                .get();


    }
}
