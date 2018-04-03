package cl.mobdev.sample.api.dogs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RelayControllerTest {
    private RelayController controller;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RestTemplate restTemplate;

    @Mock
    ResponseEntity<String> responseEntity;

    @Mock
    PrintWriter output;

    @Before
    public void setup() {
        controller = new RelayController(restTemplate);
    }

    @Test
    public void testMirrorRest() throws IOException, URISyntaxException {
        when(request.getRequestURI()).thenReturn("/breeds/list/all");
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCodeValue()).thenReturn(HttpStatus.OK.value());
        when(responseEntity.getHeaders()).thenReturn(new HttpHeaders());
        when(responseEntity.getBody()).thenReturn("aBody");
        when(response.getWriter()).thenReturn(output);

        controller.mirrorRest(null, HttpMethod.GET, request, response);

        verify(restTemplate).exchange(eq("https://dog.ceo/api/breeds/list/all"), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class));
        verify(response).setStatus(HttpStatus.OK.value());
        verify(output).append("aBody");
    }

}
