package eu.daxiongmao.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {DaxiongmaoApplication.class})
@ActiveProfiles({"test"})
public class DaxiongmaoApplicationTest {

	@Test
	protected void contextLoads() {
	}

}
