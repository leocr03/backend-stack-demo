package integration;

import com.leocr.backendstackdemo.BackendStackDemoApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BackendStackDemoApplication.class)
class BackendStackDemoApplicationTests {

	@Test
	@Timeout(5)
	void contextLoads() throws InterruptedException {
	}
}
