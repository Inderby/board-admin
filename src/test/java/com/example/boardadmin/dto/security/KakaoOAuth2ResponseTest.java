package com.example.boardadmin.dto.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;

@DisplayName("DTO - Kakao OAuth 2.0 인증 응답 데이터 테스트")
class KakaoOAuth2ResponseTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @DisplayName("인증 결과를 Map(deserialized json)으로 받으면, 카카오 인증 응답 객체로 변환 한다.")
    @Test
    void givenMapFromJson_whenInstantiating_thenReturnsKakaoReponseObject() throws Exception{
        String serializedResponse = """
                {
                     "id":123456789,
                     "connected_at": "2024-03-01T01:45:28Z",
                     "kakao_account": {
                         "profile_nickname_needs_agreement": false,
                         "profile": {
                             "nickname": "홍길동"
                         },
                         "email_needs_agreement":false,
                         "is_email_valid": true,
                         "is_email_verified": true,
                         "email": "sample@sample.com"
                     }
                }
                """;
        Map<String, Object> attributes = mapper.readValue(serializedResponse, new TypeReference<Map<String, Object>>() {});
        KakaoOAuth2Response result = KakaoOAuth2Response.from(attributes);
        assertThat(result)
                .hasFieldOrPropertyWithValue("id", 123456789L)
                .hasFieldOrPropertyWithValue("connectedAt", ZonedDateTime.of(
                        2024, 3, 1, 1, 45, 28,0, ZoneOffset.UTC
                ).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())
                .hasFieldOrPropertyWithValue("kakaoAccount.profile.nickname", "홍길동")
                .hasFieldOrPropertyWithValue("kakaoAccount.email", "sample@sample.com");
    }
}