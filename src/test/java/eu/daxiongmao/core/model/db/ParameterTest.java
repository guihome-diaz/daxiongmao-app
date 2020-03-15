package eu.daxiongmao.core.model.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterTest {

    @Test
    public void getParamValueAsString() {
        final String expectation = "this is a nice String test";
        final Parameter param = new Parameter("java.lang.String","test", expectation);
        final String paramValue = (String) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(expectation, paramValue);
    }

    @Test
    public void getParamValueAsInteger() {
        // Nominal case
        final String expectation = "19823";
        Parameter param = new Parameter("java.lang.Integer","test", expectation);
        Integer paramValue = (Integer) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(19823, paramValue);

        // Empty case
        param = new Parameter("java.lang.Integer","test", "   ");
        paramValue = (Integer) param.getValue();
        Assertions.assertNull(paramValue);
    }


    @Test
    public void getParamValueAsBoolean() {
        // Nominal cases - explicit letters
        Parameter param = new Parameter("java.lang.Boolean","test", "true");
        Boolean paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("java.lang.Boolean","test", "  true  ");
        paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("java.lang.Boolean","test", "false");
        paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - numbers
        param = new Parameter("java.lang.Boolean","test", "1");
        paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("java.lang.Boolean","test", "0");
        paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - letters
        param = new Parameter("java.lang.Boolean","test", "Y");
        paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("java.lang.Boolean","test", "y");
        paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        param = new Parameter("java.lang.Boolean","test", "N");
        paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        param = new Parameter("java.lang.Boolean","test", "n");
        paramValue = (Boolean) param.getValue();
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);
    }
}
