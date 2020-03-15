package eu.daxiongmao.core.business.cache;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Cache utility class, this should be embedded in other classes.
 * @param <T> Cache key
 * @param <K> Cache Value
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2020/03
 */
@RequiredArgsConstructor
public class InnerCache<T, K> {

    /** Maximum time to wait to get lock in seconds */
    private static final int MAX_TIME_TO_WAIT_TO_GET_LOCK_IN_SECONDS = 10;

    /** Logger to use to track exceptions */
    private final Logger log;

    /** Delay to respect between 2 cache refresh, in seconds. This prevents multi-threads issues */
    private final long delayBetweenRefreshInSeconds;

    /** Way to retrieve values to cache periodically */
    private final CacheValueFunction<T, K> cachingFunction;

    /**
     * Cache dictionary
     * <ul>
     *     <li>Key: parameter name of type T</li>
     *     <li>Value: parameter value of type K</li>
     * </ul>
     */
    private final Map<T, K> cachedValues = new ConcurrentHashMap<>();

    /** To avoid concurrent DB operations */
    private final Lock lock = new ReentrantLock();

    /** Moment of the last update. This avoid to avoid constant cache update. */
    private LocalDateTime lastUpdateCacheTime;

    /**
     * To retrieve the current cached values
     * @return cached values
     */
    public Map<T, K> getCachedValues() {
        if (cachedValues.isEmpty()) {
            updateCache(false);
        }
        return Collections.unmodifiableMap(cachedValues);
    }

    /**
     * To refresh the parameters cache.
     * @param forceRefresh flag - "true" to force cache refresh no matter the previous refresh time ; else cache can only be refresh after seconds
     */
    public void updateCache(boolean forceRefresh) {
        // Avoid multi-thread issues: only 1 refresh at a time
        try {
            if (lock.tryLock(MAX_TIME_TO_WAIT_TO_GET_LOCK_IN_SECONDS, TimeUnit.SECONDS)) {
                try {
                    doCacheRefresh(forceRefresh);
                } catch (Exception e) {
                    log.error("Failed to refresh cache: inner algorithm failure", e);
                } finally {
                    // Make sure to release the lock to avoid deadlock
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            log.warn("Failed to refresh cache: cannot get lock");
        }
    }

    /**
     * To perform a cache refresh if required
     * @param forceRefresh flag - "true" to force cache refresh no matter the previous refresh time ; else cache can only be refresh after seconds
     * @throws Exception something went wrong
     */
    private void doCacheRefresh(boolean forceRefresh) throws Exception {
        // Only refresh at periodic interval or user-request
        final LocalDateTime updateTriggerTime = LocalDateTime.now(ZoneOffset.UTC).minus(Duration.ofSeconds(delayBetweenRefreshInSeconds));
        if (forceRefresh
                || cachedValues.isEmpty()
                || lastUpdateCacheTime == null
                || lastUpdateCacheTime.isBefore(updateTriggerTime)) {
            // Get values to cache
            Map<T, K> valuesToCache = cachingFunction.getValuesToCache();

            // Update general cache and register refresh time
            cachedValues.clear();
            cachedValues.putAll(valuesToCache);
            lastUpdateCacheTime = LocalDateTime.now(ZoneOffset.UTC);
        }
    }
}
