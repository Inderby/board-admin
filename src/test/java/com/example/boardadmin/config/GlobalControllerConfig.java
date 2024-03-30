package com.example.boardadmin.config;

import com.example.boardadmin.domain.constant.RoleType;
import com.example.boardadmin.dto.AdminAccountDto;
import com.example.boardadmin.service.AdminAccountService;
import com.example.boardadmin.service.VisitCounterService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
@TestConfiguration
public class GlobalControllerConfig {
    @MockBean
    private VisitCounterService visitCounterService;
    @BeforeTestMethod
    public void securitySetup(){
        given(visitCounterService.visitCount()).willReturn(0L);
    }
}
