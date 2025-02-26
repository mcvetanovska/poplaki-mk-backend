package mk.poplaki.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration implements OpenApiCustomizer {

    private final String title;
    private final String version;

    public OpenApiConfiguration(@Value("${springdoc.info.title:}") String title, @Value("${springdoc.info.version:}") String version) {
        this.title = title;
        this.version = version;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                ).info(new Info().title(title).version(version));
    }

    @Override
    public void customise(OpenAPI openApi) {
        openApi.getServers().forEach(server -> {
            if (!server.getUrl().contains(":80")) {
                server.setUrl(server.getUrl().replace("http://", "https://"));
            }
        });
    }
}
