package ar.com.carloscurotto.infinispan.distribution.execution;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;

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
        if (!event.isPre()) {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] has been added.");
        }
    }

    @CacheEntryRemoved
    @SuppressWarnings("rawtypes")
    public void cacheEntryRemoved(CacheEntryRemovedEvent event) {
        if (!event.isPre()) {
            System.out.println("Cache entry [" + event.getKey() + "=" + event.getValue()
                            + "] has been removed.");
        }
    }

}
