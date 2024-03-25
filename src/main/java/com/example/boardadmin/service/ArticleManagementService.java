package com.example.boardadmin.service;

import com.example.boardadmin.dto.ArticleDto;
import com.example.boardadmin.dto.properties.ProjectProperties;
import com.example.boardadmin.dto.response.ArticleClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleManagementService {

    private final RestTemplate restTemplate;
    private final ProjectProperties properties;
    public List<ArticleDto> getArticles(){
         URI uri = UriComponentsBuilder.fromHttpUrl(properties.board().url() + "/api/articles")
                 .queryParam("size", 10000) // TODO : 실제 게시글의 갯수를 조회하여 출력해오는 것이 아니기 때문에 10000개 이상의 게시글이 있을 경우 전제 게시글을 가져오는 것이 아니게 된다. 간편하지만 불안정한 방식이다.
                 .build()
                 .toUri();

        ArticleClientResponse response = restTemplate.getForObject(uri, ArticleClientResponse.class);
        return Optional.ofNullable(response).orElseGet(ArticleClientResponse::empty).articles();
    }

    public ArticleDto getArticle(Long articleId){
        URI uri = UriComponentsBuilder.fromHttpUrl(properties.board().url() + "/api/articles/" + articleId)
                .build()
                .toUri();
        ArticleDto response = restTemplate.getForObject(uri, ArticleDto.class);
        return Optional.ofNullable(response)
                .orElseThrow(() -> new NoSuchElementException("게시글이 없습니다 - articleId:" + articleId));
    }

    public void deleteArticle(Long articleId){
        URI uri = UriComponentsBuilder.fromHttpUrl(properties.board().url() + "/api/articles/" + articleId)
                .build()
                .toUri();
        restTemplate.delete(uri);
    }
}
