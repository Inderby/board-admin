package com.example.boardadmin.service;

import com.example.boardadmin.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleManagementService {
    public List<ArticleDto> getArticles(){
        return List.of();
    }

    public ArticleDto getArticle(Long id){
        return null;
    }

    public void deleteArticle(Long articleId){

    }
}
