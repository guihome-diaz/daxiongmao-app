package eu.daxiongmao.core.utils;

import eu.daxiongmao.core.model.enums.Environment;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class to convert a String input into a particular output on runtime.
 * This is a trick to compensate static language to allow dynamic things during execution.
 * @author Guillaume Diaz
 * @version 1.0 (2020/01)
 */
public class StringToClassUtils {

    // ------------------ Singleton ------------------
    /** Create an instance of the class at the time of class loading */
    private static final StringToClassUtils INSTANCE = new StringToClassUtils();

    /** @return Parameter utils class */
    private static StringToClassUtils getInstance() {
        return INSTANCE;
    }

    // ------------------ Class settings ------------------
    /** List of available converters. <br>
     * Because Java isn't dynamic, you must create a dedicated converter for each object type you'd like to convert from X (String) to Y (convert class)
     */
    private final Map<Class<?>, Function<String,?>> converters = new HashMap<>();

    /**
     * Default constructor.<br>
     *     This initialize the converters.
     */
    private StringToClassUtils() {
        // String (default, no changes)
        addConverter(String.class, Function.identity());

        // Boolean
        addConverter(Boolean.class, (String input) -> {
            final String lowerCaseInput = input.toLowerCase();
            if ("true".equals(lowerCaseInput) || "y".equals(lowerCaseInput) || "1".equals(lowerCaseInput)) {
                return true;
            } else if ("false".equals(lowerCaseInput) || "n".equals(lowerCaseInput) || "0".equals(lowerCaseInput)){
                return false;
            } else {
                throw new IllegalStateException("Given value is not a boolean, cannot convert: " + input);
            }
        });

        // Numbers
        addConverter(Integer.class, Integer::valueOf);
        addConverter(Double.class, Double::valueOf);
        addConverter(Float.class, Float::valueOf);
        addConverter(Short.class, Short::valueOf);

        // Enum
        addConverter(Environment.class, (String input) -> {
            final String upperCaseInput = input.toUpperCase();
            return Environment.valueOf(upperCaseInput);
        });
    }

    /**
     * To add a new converter
     * @param clazz output class (= result type)
     * @param func function to apply to transform a String into the requested output class
     * @param <T> result into output class or NULL
     */
    private <T> void addConverter(Class<T> clazz, Function<String,T> func) {
        this.converters.put(clazz, func);
    }

    /**
     * To use a converter. <br>
     *     Based on the requested output class the right converter will be returned.
     * @param clazz output class (= result type)
     * @return function to apply to convert from a String into requested type
     */
    private <T> Function<String,T> getConverter(Class<T> clazz) {
        Function<String,T> func = (Function<String,T>) this.converters.get(clazz);
        if (func == null) {
            throw new IllegalStateException("Missing converter for type " + clazz);
        }
        return func;
    }


    /**
     * To convert a given String value into a particular output type
     * @param valueToConvert input String to convert
     * @param clazz output class to convert the String into
     * @param <T> String class
     * @return given String input into requested class or NULL
     * @throws IllegalArgumentException bad input: requested output type is missing
     * @throws IllegalStateException no converter available for the requested output type
     */
    private <T> T convertValue(final String valueToConvert, final Class<T> clazz) {
        // Arg check
        if (clazz == null) {
            throw new IllegalArgumentException("You must provide a valid target class to convert the input to");
        }
        if (StringUtils.isBlank(valueToConvert)) {
            return null;
        }
        // Convert to correct type
        return getConverter(clazz).apply(valueToConvert.trim());
    }

    /**
     * To convert a String into an object of a particular class
     * @param valueToConvert value to convert
     * @param className full qualified class name (ex: java.lang.String, java.lang.Integer)
     * @return given String input into requested class or NULL
     * @throws IllegalArgumentException bad input: requested output type is missing
     * @throws IllegalStateException no converter available for the requested output type
     * @throws ClassCastException requested class does not exists
     */
    private Object convertValue(final String valueToConvert, final String className) {
        try {
            // Get class
            final Class<?> classDefinition = Class.forName(className);
            // Convert value
            return convertValue(valueToConvert, classDefinition);
        } catch (ClassNotFoundException e) {
            throw new ClassCastException("Failed to convert value '" + valueToConvert + "' as: " + className);
        }
    }

    /**
     * To convert a given String value into a particular output type
     * @param valueToConvert input String to convert
     * @param clazz output class to convert the String into
     * @param <T> String class
     * @return given String input into requested class or NULL
     * @throws IllegalArgumentException bad input: requested output type is missing
     * @throws IllegalStateException no converter available for the requested output type
     */
    public static <T> T getValue(final String valueToConvert, final Class<T> clazz) {
        return getInstance().convertValue(valueToConvert, clazz);
    }

    /**
     * To convert a String into an object of a particular class
     * @param valueToConvert value to convert
     * @param className full qualified class name (ex: java.lang.String, java.lang.Integer)
     * @return given String input into requested class or NULL
     * @throws IllegalArgumentException bad input: requested output type is missing
     * @throws IllegalStateException no converter available for the requested output type
     * @throws ClassCastException requested class does not exists
     */
    public static Object getValue(final String valueToConvert, final String className) {
        return getInstance().convertValue(valueToConvert, className);
    }
}
