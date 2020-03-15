package eu.daxiongmao.core.model.dto;

import eu.daxiongmao.core.model.enums.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterDTOTest {

    @Test
    public void getParamValueAsString() {
        final String expectation = "this is a nice String test";
        final ParameterDTO param = new ParameterDTO("java.lang.String","test", expectation, "Test parameter", true);
        final String paramValue = param.getValue(String.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(expectation, paramValue);
    }

    @Test
    public void getParamValueAsInteger() {
        // Nominal case
        final String expectation = "19823";
        final ParameterDTO param1 = new ParameterDTO("java.lang.Integer","test", expectation, "Test parameter", true);
        final Integer paramValue1 = param1.getValue(Integer.class);
        Assertions.assertNotNull(paramValue1);
        Assertions.assertEquals(19823, paramValue1);

        // Empty case
        final ParameterDTO param2 = new ParameterDTO("java.lang.Integer", "test", "  ", "Test parameter", true);
        final Integer paramValue2 = param2.getValue(Integer.class);
        Assertions.assertNull(paramValue2);
    }

    @Test
    public void getParamValueAsBoolean() {
        // Nominal cases - explicit letters
        ParameterDTO param = new ParameterDTO("java.lang.Boolean","test", "true", "Test parameter", true);
        Boolean paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new ParameterDTO("java.lang.Boolean", "test", "false", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - numbers
        param = new ParameterDTO("java.lang.Boolean","test", "1", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new ParameterDTO("java.lang.Boolean","test", "0", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - letters
        param = new ParameterDTO("java.lang.Boolean","test", "Y", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new ParameterDTO("java.lang.Boolean","test", "y", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new ParameterDTO("java.lang.Boolean","test", "N", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        param = new ParameterDTO("java.lang.Boolean","test", "n", "Test parameter", true);
        paramValue = param.getValue(Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);
    }

    @Test
    public void getParamValueAsEnum() {
        // Nominal cases
        ParameterDTO param = new ParameterDTO(Environment.class.getName(), "APP.ENVIRONMENT", "DEV", "Application's environment", true);
        Environment environment = param.getValue(Environment.class);
        Assertions.assertNotNull(environment);
        Assertions.assertEquals(Environment.DEV, environment);

        param = new ParameterDTO(Environment.class.getName(), "APP.ENVIRONMENT", "PRE_PROD", "Application's environment", true);
        environment = param.getValue(Environment.class);
        Assertions.assertNotNull(environment);
        Assertions.assertEquals(Environment.PRE_PROD, environment);

        param = new ParameterDTO(Environment.class.getName(), "APP.ENVIRONMENT", "PROD", "Application's environment", true);
        environment = param.getValue(Environment.class);
        Assertions.assertNotNull(environment);
        Assertions.assertEquals(Environment.PROD, environment);

        // error
        try {
            param = new ParameterDTO(Environment.class.getName(), "APP.ENVIRONMENT", "SOMETHING_ELSE", "Application's environment", true);
            environment = param.getValue(Environment.class);
            Assertions.fail("Failure was expected: this is not a valid value");
        } catch (Exception e) {
            Assertions.assertTrue(true, "Exception expected, this is not a valid value");
        }
    }
}
