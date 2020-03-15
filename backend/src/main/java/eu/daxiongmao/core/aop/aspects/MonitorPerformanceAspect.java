package eu.daxiongmao.core.aop.aspects;


/**
 * Utility class used to log method execution time.
 * To use it, simply add the corresponding annotation to the method / class you want to monitor.
 * <ul>
 *     <li>Class monitoring (all methods): add @MonitorClassPerformance</li>
 *     <li>Method monitoring (particular method): add @MonitorMethodPerformance</li>
 * </ul>
 * This feature is disabled by default, to enable it add the following property to your application.properties file:
 * spring.common.log.method-execution-time.enabled
 *
 * @author David Felten
 */

public class MonitorPerformanceAspect {
}
