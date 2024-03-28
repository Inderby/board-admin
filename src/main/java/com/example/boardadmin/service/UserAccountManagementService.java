package com.example.boardadmin.service;

import com.example.boardadmin.dto.UserAccountDto;
import com.example.boardadmin.dto.properties.ProjectProperties;
import com.example.boardadmin.dto.response.UserAccountClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAccountManagementService {
    private final RestTemplate restTemplate;
    private final ProjectProperties properties;
    public List<UserAccountDto> getUserAccounts(){
        URI uri = UriComponentsBuilder.fromHttpUrl(properties.board().url() + "/api/userAccounts")
                .queryParam("size", 10000)
                .build()
                .toUri();

        UserAccountClientResponse response = restTemplate.getForObject(uri, UserAccountClientResponse.class);
        return Optional.ofNullable(response).orElseGet(UserAccountClientResponse::empty).userAccounts();
    }

    public UserAccountDto getUserAccount(String userId){
        URI uri = UriComponentsBuilder.fromHttpUrl(properties.board().url() + "/api/userAccounts/" + userId)
                .build()
                .toUri();
        UserAccountDto response = restTemplate.getForObject(uri, UserAccountDto.class);


        return Optional.ofNullable(response).orElseThrow(() -> new NoSuchElementException("사용자가 없습니다 UserId : " + userId));
    }

    public void deleteUserAccount(String userId){
        URI uri = UriComponentsBuilder.fromHttpUrl(properties.board().url() + "/api/userAccounts/" + userId)
                .build()
                .toUri();
        restTemplate.delete(uri);
    }
}
