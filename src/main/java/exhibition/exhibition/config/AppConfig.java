package exhibition.exhibition.config;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getFactory().configure(
                JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
