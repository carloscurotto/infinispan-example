package ar.com.carloscurotto.infinispan.distribution.cache;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryLoaded;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryVisited;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryLoadedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryVisitedEvent;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStarted;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStopped;
import org.infinispan.notifications.cachemanagerlistener.event.CacheStartedEvent;
import org.infinispan.notifications.cachemanagerlistener.event.CacheStoppedEvent;

/**
 * This is a simple listener that is used to listen for both cache-manager and cache events. This
 * listener will just receive the events notification and will print the event out to the console.
 *
 * @author Carlos Curotto
 *
 */
@Listener
public class ConsoleListener {

    @CacheEntryCreated
    @SuppressWarnings("rawtypes")
    public void cacheEntryCreated(CacheEntryCreatedEvent event) {
        if (event.isPre()) {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] to be added.");
        } else {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] has been added.");
        }
    }

    @CacheEntryRemoved
    @SuppressWarnings("rawtypes")
    public void cacheEntryRemoved(CacheEntryRemovedEvent event) {
        if (event.isPre()) {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] to be removed.");
        } else {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] has been removed.");
        }
    }

    @CacheEntryModified
    @SuppressWarnings("rawtypes")
    public void cacheEntryModified(CacheEntryModifiedEvent event) {
        if (event.isPre()) {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] to be modified.");
        } else {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] has been modified.");
        }
    }

    @CacheEntryVisited
    @SuppressWarnings("rawtypes")
    public void cacheEntryVisited(CacheEntryVisitedEvent event) {
        if (event.isPre()) {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] to be visited.");
        } else {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] has been visited.");
        }
    }

    @CacheEntryLoaded
    @SuppressWarnings("rawtypes")
    public void cacheEntryLoaded(CacheEntryLoadedEvent event) {
        if (event.isPre()) {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] to be loaded.");
        } else {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] has been loaded.");
        }
    }

    @CacheStarted
    public void cacheStarted(CacheStartedEvent event) {
        System.out.println("Cache [" + event.getCacheName() + "] has been started.");
    }

    @CacheStopped
    public void cacheStopped(CacheStoppedEvent event) {
        System.out.println("Cache [" + event.getCacheName() + "] has been stopped.");
    }

}
