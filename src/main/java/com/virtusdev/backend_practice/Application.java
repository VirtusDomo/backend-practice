package com.virtusdev.backend_practice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.virtusdev.backend_practice.user.User;
import com.virtusdev.backend_practice.user.UserHttpClient;
import com.virtusdev.backend_practice.user.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.virtusdev.backend_practice.run.Location;
import com.virtusdev.backend_practice.run.Run;
import com.virtusdev.backend_practice.run.RunRepository;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
//
//	@Bean
//	UserHttpClient userHttpClient(){
//		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
//		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
//		return factory.createClient(UserHttpClient.class);
//	}
//
//	@Bean
//	 CommandLineRunner runner(UserHttpClient restClient) {
//	 	return args -> {
//	 		List<User> userList = restClient.findAll();
//			System.out.println(userList);
//
//			User user = restClient.findById(1);
//			System.out.println(user);
//	 	};
//	 }

}
