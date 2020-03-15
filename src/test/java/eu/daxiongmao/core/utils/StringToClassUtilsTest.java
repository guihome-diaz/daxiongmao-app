package eu.daxiongmao.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringToClassUtilsTest {

    @Test
    public void getValueAsString() {
        final String expectation = "this is a nice String test";
        final String paramValue = StringToClassUtils.getValue(expectation, String.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(expectation, paramValue);
    }

    @Test
    public void getValueAsStringFromString() {
        final String expectation = "this is a nice String test";
        final String paramValue = (String) StringToClassUtils.getValue(expectation, "java.lang.String");
        Assertions.assertNotNull(paramValue);
        Assertions.assertEquals(expectation, paramValue);
    }


    @Test
    public void getValueAsInteger() {
        // Nominal case
        final String expectation = "19823";
        final Integer paramValue1 = StringToClassUtils.getValue(expectation, Integer.class);
        Assertions.assertNotNull(paramValue1);
        Assertions.assertEquals(19823, paramValue1);

        // Empty case
        final Integer paramValue2 = StringToClassUtils.getValue("  ", Integer.class);
        Assertions.assertNull(paramValue2);
    }

    @Test
    public void getValueAsIntegerFromString() {
        // Nominal case
        final String expectation = "19823";
        final Integer paramValue1 = (Integer) StringToClassUtils.getValue(expectation, "java.lang.Integer");
        Assertions.assertNotNull(paramValue1);
        Assertions.assertEquals(19823, paramValue1);

        // Empty case
        final Integer paramValue2 = (Integer) StringToClassUtils.getValue("  ", "java.lang.Integer");
        Assertions.assertNull(paramValue2);
    }

    @Test
    public void getValueAsBoolean() {
        // Nominal cases - explicit letters
        Boolean paramValue = StringToClassUtils.getValue("true", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = StringToClassUtils.getValue("false", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - numbers
        paramValue = StringToClassUtils.getValue("1", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = StringToClassUtils.getValue("0", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - letters
        paramValue = StringToClassUtils.getValue("Y", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = StringToClassUtils.getValue("y", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = StringToClassUtils.getValue("N", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        paramValue = StringToClassUtils.getValue("n", Boolean.class);
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);
    }

    @Test
    public void getValueAsBooleanFromString() {
        // Nominal cases - explicit letters
        Boolean paramValue = (Boolean) StringToClassUtils.getValue("true", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = (Boolean) StringToClassUtils.getValue("false", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - numbers
        paramValue = (Boolean) StringToClassUtils.getValue("1", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = (Boolean) StringToClassUtils.getValue("0", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        // Nominal cases - letters
        paramValue = (Boolean) StringToClassUtils.getValue("Y", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = (Boolean) StringToClassUtils.getValue("y", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertTrue(paramValue);

        paramValue = (Boolean) StringToClassUtils.getValue("N", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);

        paramValue = (Boolean) StringToClassUtils.getValue("n", "java.lang.Boolean");
        Assertions.assertNotNull(paramValue);
        Assertions.assertFalse(paramValue);
    }
}
