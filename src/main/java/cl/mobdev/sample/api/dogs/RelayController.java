package cl.mobdev.sample.api.dogs;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class RelayController {
    private final RestTemplate restTemplate;

    public RelayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/**")
    @ResponseBody
    public void mirrorRest(@RequestBody(required = false) String body,
                           HttpMethod method,
                           HttpServletRequest request,
                           HttpServletResponse response) throws URISyntaxException, IOException {
        String url = "https://dog.cep/api" + request.getRequestURI();

        String query = request.getQueryString();
        if (query != null) {
            url += "?" + query;
        }

        ResponseEntity<String> resp = restTemplate.exchange(url, method, new HttpEntity<>(body), String.class);

        response.setStatus(resp.getStatusCodeValue());
        resp.getHeaders().forEach((k, v) -> v.forEach($ -> response.addHeader(k, $)));
        response.getWriter().append(resp.getBody());
    }

}
