package br.com.cotiinformatica.api_clientes.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

    @Value("${cors.allowed}")
    private String[] corsAllowed;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        //Permissão para o projeto Angular
        registry
                .addMapping("/**") //permissão para todos os ENDPOINTS
                .allowedOrigins(corsAllowed) //projeto Angular
                .allowedMethods("POST", "PUT", "DELETE", "GET")
                .allowedHeaders("*");
    }
}
