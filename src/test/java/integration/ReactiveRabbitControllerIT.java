package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leocr.backendstackdemo.api.v1.dto.BrokerDto;
import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("integration-test")
@Testcontainers
public class ReactiveRabbitControllerIT {

    public final static DockerComposeContainer<?> environment =
            new DockerComposeContainer<>(new File("src/test/resources/docker-compose-integration.yaml"))
                    .withExposedService("backend-stack-demo_1", 8080,
                            Wait.forHttp("/actuator/health").forStatusCode(200));

    @BeforeEach
    void setUp() {
        environment.start();
    }

    @AfterEach
    void tearDown() {
        environment.stop();
    }

    @Test
    public void testReactiveRabbitList() throws IOException, InterruptedException {
        sendMessage("10");
        sendMessage("11");
        sendMessage("12");

        Thread.sleep(Duration.ofSeconds(60).toMillis());

        final String uriList = "http://localhost:8080/api/v1/reactive/rabbit/messages";
        final HttpUriRequest request = new HttpGet(uriList);

        final HttpResponse listResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, listResponse.getStatusLine().getStatusCode());
        final ObjectMapper objectMapper = new ObjectMapper();
        final BrokerPageDto[] dto =
                objectMapper.readValue(listResponse.getEntity().getContent(), BrokerPageDto[].class);
        final BrokerPageDto[] expected =
                { new BrokerPageDto(Arrays.stream(new String[]{"10", "11", "12"}).collect(toSet())) };
        assertNotNull(dto);
        assertEquals(1, dto.length);
        assertEquals(expected[0], dto[0]);
    }

    private void sendMessage(String value) throws IOException {
        final String uri = String.format("http://localhost:8080/api/v1/rabbit/message/%s", value);
        final HttpUriRequest request = new HttpGet(uri);
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        final ObjectMapper objectMapper = new ObjectMapper();
        final BrokerDto dto = objectMapper.readValue(httpResponse.getEntity().getContent(), BrokerDto.class);
        final BrokerDto expected = new BrokerDto(value, "Value produced to RabbitMQ: " + value);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals(expected, dto);
    }
}
