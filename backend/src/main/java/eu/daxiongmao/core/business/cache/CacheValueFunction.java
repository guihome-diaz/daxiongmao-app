package eu.daxiongmao.core.business.cache;


import java.util.Map;

/**
 * To retrieve values to cache
 * @param <T> Cache key
 * @param <K> Cache Value
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
@FunctionalInterface
public interface CacheValueFunction<T, K> {

    /**
     * Dedicated function to retrieve values to cache in local dictionary.
     * @return values to cache
     * @throws Exception something went wrong
     */
    Map<T, K> getValuesToCache() throws Exception;

}
