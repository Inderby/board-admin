package com.example.boardadmin.repository;

import com.example.boardadmin.domain.AdminAccount;
import com.example.boardadmin.domain.constant.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;


@DisplayName("JPA 연결 테스트")
@Import(JpaRepositoryTest.TestJpaConfig.class)
@DataJpaTest
public class JpaRepositoryTest {
    private final AdminAccountRepository adminAccountRepository;

    public JpaRepositoryTest(@Autowired AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }

    @DisplayName("회원 정보 select 테스트")
    @Test
    void givenAdminAccounts_whenSelecting_thenWorksFine() {
        //Given

        //When
        List<AdminAccount> adminAccounts = adminAccountRepository.findAll();
        //Then
        assertThat(adminAccounts)
                .isNotNull()
                .hasSize(4);
    }

    @DisplayName("회원 정보 insert 테스트")
    @Test
    void givenAdminAccount_whenInserting_thenWorksFine() {
        //Given
        long previousCount = adminAccountRepository.count();
        AdminAccount adminAccount = AdminAccount.of("test", "pw", Set.of(RoleType.DEVELOPER), "email", "test", null);
        //When
        adminAccountRepository.save(adminAccount);
        //Then
        assertThat(adminAccountRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("회원 정보 update 테스트")
    @Test
    void givenAdminAccountAndRoleType_whenUpdating_thenWorksFine() {
        //Given
        AdminAccount adminAccount = adminAccountRepository.getReferenceById("inderby");
        adminAccount.addRoleType(RoleType.DEVELOPER);
        adminAccount.addRoleTypes(List.of(RoleType.USER, RoleType.USER));
        adminAccount.removeRoleType(RoleType.ADMIN);
        //When
        AdminAccount updatedAccount = adminAccountRepository.saveAndFlush(adminAccount);
        //Then
        assertThat(updatedAccount)
                .hasFieldOrPropertyWithValue("userId", "inderby")
                .hasFieldOrPropertyWithValue("roleTypes", Set.of(RoleType.DEVELOPER, RoleType.USER));
    }

    @DisplayName("회원 정보 delete 테스트")
    @Test
    void givenAdminAccount_whenDeleting_thenWorksFine() {
        //Given
        long previousCount = adminAccountRepository.count();
        AdminAccount adminAccount = adminAccountRepository.getReferenceById("inderby");
        //When
        adminAccountRepository.delete(adminAccount);
        //Then
        assertThat(adminAccountRepository.count()).isEqualTo(previousCount - 1);
    }

    @EnableJpaAuditing
    @TestConfiguration
    static class TestJpaConfig { //jpa 테스트를 시행할 때 Auditing Field에 대해서 자동 주입을 해줄 필요성이 있기 때문에 해당 테스트에서 일시적으로 사용하는 클래스
        @Bean
        AuditorAware<String> auditorAware() {
            return () -> Optional.of("inderby");
        }
    }
}
