package com.virtusdev.backend_practice.user;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class UserRestClient  implements UserHttpClient{

    private final RestClient restClient;

    public UserRestClient(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

    public List<User> findAll(){
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    public User findById(Integer id){
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }

    public void create(User user){
        restClient.post()
                .uri("/users")
                .body(user)
                .retrieve()
                .toBodilessEntity();
    }

    public void update(User user, Integer id){
        restClient.put()
                .uri("/users/{id}", id)
                .body(user)
                .retrieve()
                .toEntity(User.class);
    }

    public void delete(Integer id){
        restClient.delete()
                .uri("/users/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

}
