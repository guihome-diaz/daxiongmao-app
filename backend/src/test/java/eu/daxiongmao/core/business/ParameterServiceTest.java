package eu.daxiongmao.core.business;

import eu.daxiongmao.core.DaxiongmaoApplicationTest;
import eu.daxiongmao.core.model.dto.ParameterDTO;
import eu.daxiongmao.core.model.enums.param.BusinessParam;
import eu.daxiongmao.core.utils.StringToClassUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Log4j2
public class ParameterServiceTest extends DaxiongmaoApplicationTest {

    @Autowired
    private ParameterService parameterService;

    @Test
    public void getAll() {
        final List<ParameterDTO> publicParams = parameterService.getAll(false, false);
        Assertions.assertNotNull(publicParams);
        Assertions.assertFalse(publicParams.isEmpty());

        final List<ParameterDTO> allParams = parameterService.getAll(true, true);
        Assertions.assertNotNull(allParams);
        Assertions.assertFalse(allParams.isEmpty());

        Assertions.assertTrue(allParams.size() > publicParams.size());
    }

    @Test
    public void getLabel() {
        Optional<ParameterDTO> param = parameterService.getParam(BusinessParam.APP_DEFAULT_LANGUAGE);
        Assertions.assertTrue(param.isPresent());
        log.info("Found param: {}", param);
        Assertions.assertTrue(StringUtils.isNotBlank(param.get().getParamValue()));

        final String paramValue1 = (String) StringToClassUtils.getValue(param.get().getParamValue(),param.get().getParamType());
        Assertions.assertNotNull(paramValue1);
        Assertions.assertTrue(StringUtils.isNotBlank(paramValue1));
        final String paramValue2 = (String) StringToClassUtils.getValue(param.get().getParamValue(),param.get().getParamTypeClass());
        Assertions.assertNotNull(paramValue2);
        Assertions.assertTrue(StringUtils.isNotBlank(paramValue2));
        Assertions.assertEquals(paramValue1, paramValue2);
    }
}
