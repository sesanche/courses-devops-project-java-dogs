package cl.mobdev.sample.api.dogs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        template.setErrorHandler(new NullResponseErrorHandler());
        return template;
    }

}

class NullResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw new UnsupportedOperationException();
    }

}