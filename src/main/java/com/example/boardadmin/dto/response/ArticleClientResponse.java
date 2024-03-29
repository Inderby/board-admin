package com.example.boardadmin.dto.response;

import com.example.boardadmin.dto.ArticleDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ArticleClientResponse(
        @JsonProperty("_embedded") Embedded embedded,
        @JsonProperty("page") Page page
) {

    public static ArticleClientResponse empty(){
        return new ArticleClientResponse(new Embedded(List.of()), new Page(1, 0,1, 0));
    }

    public static ArticleClientResponse of(List<ArticleDto> articles) {
        return new ArticleClientResponse(
                new Embedded(articles),
                new Page(articles.size(), articles.size(), 1, 0)
        );
    }
    public List<ArticleDto> articles(){return this.embedded.articles();}
    record Embedded(@JsonProperty("articles")List<ArticleDto> articles){}
    record Page(
       int size,
       int totalElements,
       int totalPages,
       int number
    ){}
}
