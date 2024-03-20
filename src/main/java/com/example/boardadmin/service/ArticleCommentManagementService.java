package com.example.boardadmin.service;

import com.example.boardadmin.dto.ArticleCommentDto;
import com.example.boardadmin.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCommentManagementService {
    public List<ArticleCommentDto> getArticleComments(){
        return List.of();
    }

    public ArticleCommentDto getArticleComment(Long id){
        return null;
    }

    public void deleteArticleComment(Long articleId){

    }
}
