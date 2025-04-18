package ma.emsi.controle.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de Swagger/OpenAPI pour la documentation de l'API.
 */
@Configuration
public class SwaggerConfig {

        /**
         * Configure l'API OpenAPI avec les informations de base.
         *
         * @return L'objet OpenAPI configuré
         */
        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("API de Gestion des Campagnes de Dons")
                            .version("1.0")
                            .description("API REST pour gérer les campagnes de dons et les transactions associées"));
        }
}
