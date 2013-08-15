package ar.com.carloscurotto.infinispan.local;

import junit.framework.Assert;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

/**
 * This is an example of how to start a local (non-distributed) cache using infinispan. This local
 * cache will behave the same way as a local hash map.
 *
 * @author Carlos Curotto
 *
 */
public class Node0 {

    private static final Integer ITEMS_QUANTITY = 10;

    public static void main(String[] args) {

        ConsoleListener listener = new ConsoleListener();

        DefaultCacheManager manager = new DefaultCacheManager();
        manager.addListener(listener);

        Cache<String, String> cache = manager.getCache("TestCache");
        cache.addListener(listener);

        for (int i = 1; i <= ITEMS_QUANTITY; i++) {
            cache.put("key-" + i, "value-" + i);
        }

        Assert.assertTrue("Wrong cache size.", cache.size() == ITEMS_QUANTITY);
    }

}
