package com.example.boardadmin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
//@EnableConfigurationProperties(ThymeleafConfig.Thymeleaf3Properties.class)
public class ThymeleafConfig {
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(true);
        return defaultTemplateResolver;
    }


    @Getter
    @Setter
    public static class Thymeleaf3Properties {
        private boolean decoupledLogic;

//        @ConstructorBinding
        public Thymeleaf3Properties(boolean decoupledLogic) {
            /**
             * Use Thymeleaf 3 Decoupled Logic
             * */
            this.decoupledLogic = decoupledLogic;
        }

    }
}
