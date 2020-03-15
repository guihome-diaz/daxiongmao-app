package eu.daxiongmao.core.dao;

import eu.daxiongmao.core.DaxiongmaoApplicationTest;
import eu.daxiongmao.core.model.db.Parameter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;



@Log4j2
public class ParameterRepositoryTest extends DaxiongmaoApplicationTest {

    @Autowired
    private ParameterRepository parameterRepository;

    @Test
    public void findAll() {
        Assertions.assertNotNull(parameterRepository);
        final List<Parameter> parameters = parameterRepository.findAll();
        Assertions.assertNotNull(parameters);
        Assertions.assertFalse(parameters.isEmpty());
        parameters.forEach((parameter -> {log.info(parameter); }));
    }

}
