package br.com.abr;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.abr.controller.TaskController;
import br.com.abr.entity.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ApplicationTest {
//
//	@Test
//	public void contextLoads() { }

	@Autowired
	private TestRestTemplate testRestTemplate;

//	@Autowired
//	private Task task;

	@Test
	@DisplayName("Validação do status do serviço")
	public void appStatus() {
		String body = this.testRestTemplate.getForObject("/actuator/health", String.class);
		assertThat(body).contains("UP");
	}

	@Test
	@DisplayName("Verificando versão da aplicação")
	public void appVersion() {
		String body = this.testRestTemplate.getForObject("/v0/to-do/version", String.class);
		assertThat(body).contains("versao");
	}
}
