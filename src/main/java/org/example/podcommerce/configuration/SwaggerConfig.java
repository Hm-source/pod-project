package org.example.podcommerce.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("POD Commerce API")
                .version("1.0.0")
                .description("POD Commerce 플랫폼 REST API 문서")
            )
            .servers(List.of(
                    new Server()
                        .url("http://localhost:8080")
                        .description("개발 서버")
                )
            )
            .components(new Components()
                .addSecuritySchemes("sessionAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.COOKIE)
                    .name("JSESSIONID")
                    .description("세션 기반 인증")))
            .addSecurityItem(new SecurityRequirement().addList("sessionAuth"));
    }

    @Bean
    public GroupedOpenApi grouping() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
            .group("spec")
            .pathsToMatch(paths)
            .build();
    }
}

