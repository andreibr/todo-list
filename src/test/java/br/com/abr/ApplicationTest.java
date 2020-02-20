package br.com.abr;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

	@Test
	public void contextLoads() { }

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void appStatus() {
		String body = this.restTemplate.getForObject("/actuator/health", String.class);
		assertThat(body).contains("UP");
	}
	
	@Test
	public void appVersion() {
		String body = this.restTemplate.getForObject("/v0/to-do/version", String.class);
		assertThat(body).contains("versao");
	}
}
