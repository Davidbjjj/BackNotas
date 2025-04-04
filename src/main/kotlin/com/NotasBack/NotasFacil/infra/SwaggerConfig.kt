package com.NotasBack.NotasFacil.infra


import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("NotasFácil API")
                    .version("1.0.0")
                    .description("Documentação interativa da API para gerenciamento de alunos, professores e notas.")
            )
    }
}
