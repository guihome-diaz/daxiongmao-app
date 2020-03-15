package eu.daxiongmao.core.aop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to put on class you'd like to monitor for performances.
 * All classes' methods' will be monitored
 * @version 1.0
 * @since 2020/03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MonitorClassPerformance {
}
