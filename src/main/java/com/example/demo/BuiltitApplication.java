package com.example.demo;

import com.example.demo.rental.integration.gateways.RentalService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
//@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class BuiltitApplication {
//	@Autowired
//	@Qualifier("_halObjectMapper")
//	private ObjectMapper springHateoasObjectMapper;
//	@Bean(name = "objectMapper")
//	ObjectMapper objectMapper() {
//		return springHateoasObjectMapper
//				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//				.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
//				.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
//				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
//				.registerModules(new JavaTimeModule());
//	}
//	@Bean
//	public RestTemplate restTemplate() {
//		RestTemplate _restTemplate = new RestTemplate();
//		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//		//messageConverters.add(new MappingJackson2HttpMessageConverter(springHateoasObjectMapper));
//		_restTemplate.setMessageConverters(messageConverters);
//		return _restTemplate;
//	}

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BuiltitApplication.class, args);
		RentalService service = ctx.getBean(RentalService.class);

		System.out.println(
				service.findPlants("exc", LocalDate.now(), LocalDate.now().plusDays(2))
		);

	}
}
