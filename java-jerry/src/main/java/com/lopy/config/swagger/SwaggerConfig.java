package com.lopy.config.swagger;

import com.lopy.common.constant.AuthConstant;
import com.lopy.common.utils.StringUtil;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


@Configuration
public class SwaggerConfig {

    @Resource
    private Environment environment;

    @Bean
    public GroupedOpenApi accountGroup() {
       return GroupedOpenApi.builder()
               .group("Account API")
               .addOpenApiCustomiser(this::addGlobalRequestHeaders)
               .pathsToMatch("/api/v1/auth/**",
                       "/api/v1/user/**",
                       "/api/v1/customer/**",
                       "/api/v1/restaurateur",
                       "/api/v1/permission/**",
                       "/api/v1/role/**")
               .build();
    }

    @Bean
    public GroupedOpenApi coreGroup() {
        return GroupedOpenApi.builder()
                .group("Core API")
                .addOpenApiCustomiser(this::addGlobalRequestHeaders)
                .pathsToMatch("/api/v1/restaurant/**",
                        "/api/v1/menu/**",
                        "/api/v1/menu-category/**",
                        "/api/v1/menu-item/**",
                        "/api/v1/cart/**",
                        "/api/v1/order/**",
                        "/api/v1/order-item/**",
                        "/api/v1/cuisine/**",
                        "/api/v1/history-keyword/**",
                        "/api/v1/user-card/**",
                        "/api/v1/payment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi demoGroup() {
        return GroupedOpenApi.builder()
                .group("Demo API")
                .addOpenApiCustomiser(this::addGlobalRequestHeaders)
                .pathsToMatch("/api/v1/demo/**")
                .build();
    }

    @Bean
    public GroupedOpenApi stripeGroup() {
        return GroupedOpenApi.builder()
                .group("Stripe API")
                .addOpenApiCustomiser(this::addGlobalRequestHeaders)
                .pathsToMatch("/api/v1/stripe/**")
                .build();
    }

    @Bean
    public GroupedOpenApi webhookGroup() {
        return GroupedOpenApi.builder()
                .group("Webhook API")
                .addOpenApiCustomiser(this::addGlobalRequestHeaders)
                .pathsToMatch("/api/v1/webhook/**")
                .build();
    }

    private void addGlobalRequestHeaders(OpenAPI api) {
        List<Parameter> headers = buildHeaderParams();
        api.getPaths().values().forEach(o -> o.readOperations().forEach(ops -> headers.forEach(ops::addParametersItem)));
    }

    private List<Parameter> buildHeaderParams() {
        Parameter authTokenHeader = new HeaderParameter().name(AuthConstant.CURRENT_AUTH_TOKEN_HEADER).description("access token").schema(new StringSchema());
        Parameter idHeader = new HeaderParameter().name(AuthConstant.CURRENT_USER_HEADER).description("user id").schema(new StringSchema());
        Parameter localeHeader = new HeaderParameter().name(AuthConstant.CURRENT_USER_LOCALE_HEADER).description("user locale").schema(new StringSchema());
        return List.of(authTokenHeader, idHeader, localeHeader);
    }

    @Bean
    public OpenAPI buildOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lopy API Endpoint")
                        .description("backend service")
                        .version("1.0.0")
                        .license(new License().name("license name here").url("http://lopy.com")))
                .addServersItem(buildServer())
                .externalDocs(new ExternalDocumentation()
                        .description("description of external docs")
                        .url("url of external docs"));
    }

    private Server buildServer() {
        // if env is empty or dev, use localhost
        String env = StringUtil.trim(environment.getProperty("lopy.config.env"));
        if (StringUtil.isEmpty(env) || Objects.equals("dev", env)) {
            return new Server().url(String.format("http://localhost:%s", environment.getProperty("server.port")));
        }
        // else, use of the domain with https
        String domain = StringUtil.trim(environment.getProperty("lopy.config.domain"));
        String url = String.format("https://%s", domain);
        return new Server().url(url);
    }
}
