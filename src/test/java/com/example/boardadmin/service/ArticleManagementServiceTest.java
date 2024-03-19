package com.example.boardadmin.service;

import com.example.boardadmin.domain.constant.RoleType;
import com.example.boardadmin.dto.ArticleClientResponse;
import com.example.boardadmin.dto.ArticleDto;
import com.example.boardadmin.dto.UserAccountDto;
import com.example.boardadmin.dto.properties.ProjectProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("test")
@DisplayName("비즈니스 로직 - 게시글 관리")
class ArticleManagementServiceTest {

    //    @Disabled("실제 API 호출 결과 관찰용이므로 평상시엔 비활성화")
    @DisplayName("실제 API 호출 테스트")
    @SpringBootTest
    @Nested
    class RealApiTest {
        private final ArticleManagementService sut;

        @Autowired
        public RealApiTest(ArticleManagementService sut) {
            this.sut = sut;
        }

        @DisplayName("게시글 API를 호출하면, 게시글을 가져온다.")
        @Test
        void given_when_then(){
            //Given

            //When
            List<ArticleDto> result = sut.getArticles();
            //Then
            System.out.println(result.stream().findFirst());
            assertThat(result).isNotNull();
        }
    }

    @DisplayName("API mocking 테스트")
    @EnableConfigurationProperties(ProjectProperties.class)
    @AutoConfigureWebClient(registerRestTemplate = true) // 지금 모듈에서 등록된 restTemplate을 가져와 쓴다는 표현
    @RestClientTest(ArticleManagementService.class) //테스트의 대상을 지정하는 표현
    @Nested // 중첩 테스트
    class RestTemplateTest {
        private final ArticleManagementService sut;
        private final ProjectProperties projectProperties;
        private final MockRestServiceServer server; //RestClientTest로 인해 자동으로 생성해주는 bean
        private final ObjectMapper objectMapper;

        @Autowired
        public RestTemplateTest(ArticleManagementService sut, ProjectProperties projectProperties, MockRestServiceServer server, ObjectMapper objectMapper) {
            this.sut = sut;
            this.projectProperties = projectProperties;
            this.server = server;
            this.objectMapper = objectMapper;
        }

        @DisplayName("게시글 목록 API를 호출하면, 게시글들을 가져온.")
        @Test
        void givenNothing_whenCallingArticleApi_thenReturnsArticleList() throws JsonProcessingException {
            //Given
            ArticleDto expectedArticle = createdArticle("제목", "글");
            ArticleClientResponse articleClientResponse = ArticleClientResponse.of(List.of(expectedArticle));
            server
                    .expect(requestTo(projectProperties.board().url() + "/api/articles?size=10000")) // TODO : 페이지를 통으로 가져와야 하지만 그런 API는 존재하지 않기에 일단 크게 넣어둠
                    .andRespond(withSuccess(
                            objectMapper.writeValueAsString(articleClientResponse),
                            MediaType.APPLICATION_JSON
                    ));
            //When
            List<ArticleDto> result = sut.getArticles();
            //Then
            assertThat(result)
                    .first()
                    .hasFieldOrPropertyWithValue("id", expectedArticle.id())
                    .hasFieldOrPropertyWithValue("title", expectedArticle.title())
                    .hasFieldOrPropertyWithValue("content", expectedArticle.content())
                    .hasFieldOrPropertyWithValue("userAccount.nickname", expectedArticle.userAccount().nickname());

            server.verify();
        }

        @DisplayName("게시글 API를 호출하면, 게시글을 가져온.")
        @Test
        void givenNothing_whenCallingArticleApi_thenReturnsArticle() throws JsonProcessingException {
            //Given
            Long articleId = 1L;
            ArticleDto expectedArticle = createdArticle("제목", "글");
            server
                    .expect(requestTo(projectProperties.board().url() + "/api/articles/" + articleId))
                    .andRespond(withSuccess(
                            objectMapper.writeValueAsString(expectedArticle),
                            MediaType.APPLICATION_JSON
                    ));
            //When
            List<ArticleDto> result = sut.getArticles();
            //Then
            assertThat(result)
                    .first()
                    .hasFieldOrPropertyWithValue("id", expectedArticle.id())
                    .hasFieldOrPropertyWithValue("title", expectedArticle.title())
                    .hasFieldOrPropertyWithValue("content", expectedArticle.content())
                    .hasFieldOrPropertyWithValue("userAccount.nickname", expectedArticle.userAccount().nickname());

            server.verify();
        }

        @DisplayName("게시글 ID와 함께 게시글 삭제 API를 호출하면, 게시글을 삭제한다.")
        @Test
        void givenArticleId_whenCallingDeleteArticleApi_thenDeletesAnArticle() {
            //Given
            Long articleId = 1L;
            server
                    .expect(requestTo(projectProperties.board().url() + "/api/articles/" + articleId))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withSuccess());
            //When
            sut.deleteArticle(articleId);
            //Then
            server.verify();
        }

        private ArticleDto createdArticle(String title, String content) {
            return ArticleDto.of(1L,
                    createdUserAccount(),
                    title,
                    content,
                    null,
                    LocalDateTime.now(),
                    "inderby",
                    LocalDateTime.now(),
                    "inderby");
        }

        private UserAccountDto createdUserAccount() {
            return UserAccountDto.of(
                    "inderby",
                    "temp",
                    Set.of(RoleType.ADMIN),
                    "email",
                    "derby",
                    "memo"
            );
        }
    }
}