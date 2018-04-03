package cl.mobdev.sample.api.dogs;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class RelayController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/**")
    @ResponseBody
    public void mirrorRest(@RequestBody(required = false) String body,
                           HttpMethod method,
                           HttpServletRequest request,
                           HttpServletResponse response) throws URISyntaxException, IOException {
        URI uri = new URI("https", null, "dog.ceo", 443, "/api" + request.getRequestURI(), request.getQueryString(), null);

        ResponseEntity<String> resp = restTemplate.exchange(uri, method, new HttpEntity<>(body), String.class);

        response.setStatus(resp.getStatusCodeValue());
        resp.getHeaders().forEach((k, v) -> v.forEach($ -> response.addHeader(k, $)));
        response.getWriter().append(resp.getBody());
    }

}
