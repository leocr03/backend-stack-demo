package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leocr.backendstackdemo.api.v1.dto.KafkaDto;
import com.leocr.backendstackdemo.api.v1.dto.KafkaPageDto;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.ClassRule;
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

@ActiveProfiles("integration-test")
@Testcontainers
public class KafkaControllerIT {
    @ClassRule
    public static DockerComposeContainer<?> environment =
            new DockerComposeContainer<>(new File("src/test/resources/docker-compose-integration.yaml"))
                    .withExposedService("backend-stack-demo_1", 8080,
                            Wait.forHttp("/actuator/health").forStatusCode(200));

    @BeforeEach
    void setUp() {
        environment.start();
    }

    @Test
    public void testProduceConsumeAndList() throws IOException, InterruptedException {
        sendMessage("1");
        sendMessage("2");
        sendMessage("3");

        Thread.sleep(Duration.ofSeconds(5).toMillis());

        final String uriList = "http://localhost:8080/api/v1/kafka/messages";
        final HttpUriRequest request = new HttpGet(uriList);

        final HttpResponse listResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK, listResponse.getStatusLine().getStatusCode());
        final ObjectMapper objectMapper = new ObjectMapper();
        final KafkaPageDto result = objectMapper.readValue(listResponse.getEntity().getContent(), KafkaPageDto.class);
        final KafkaPageDto expected = new KafkaPageDto(Arrays.stream(new String[]{"1", "2", "3"}).collect(toSet()));
        assertEquals(expected, result);
    }

    private void sendMessage(String value) throws IOException {
        final String uri = String.format("http://localhost:8080/api/v1/kafka/message/%s", value);
        final HttpUriRequest request = new HttpGet(uri);
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        final ObjectMapper objectMapper = new ObjectMapper();
        final KafkaDto dto = objectMapper.readValue(httpResponse.getEntity().getContent(), KafkaDto.class);
        final KafkaDto expected = new KafkaDto(value, "Value produced to Kafka: " + value);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        assertEquals(expected, dto);
    }
}
