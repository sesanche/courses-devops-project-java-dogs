package cl.mobdev.sample.api.dogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RelayControllerIntTest {
    private TreeMap expected;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:/breeds.json")
    private Resource breeds;

    @Before
    public void setup() throws IOException {
        expected = mapper.readValue(breeds.getInputStream(), TreeMap.class);
    }

    @Test
    public void testMirrorRest() {
        TreeMap response = restTemplate.getForObject(
                "http://localhost:" + port + "/breeds/list/all",
                TreeMap.class);

        assertEquals(expected, response);
    }

}
