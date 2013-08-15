package ar.com.carloscurotto.infinispan.distribution.execution;

import java.io.Serializable;
import java.util.Set;

import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;
import org.infinispan.remoting.transport.Address;

public class PrintCacheKeysDistributedCallable implements DistributedCallable<String, String, Address>,
                Serializable {

    private static final long serialVersionUID = 1L;

    private Cache<String, String> cache;
    private Set<String> keys;

    @Override
    public Address call() throws Exception {
        for (String key : this.cache.keySet()) {
            String value = this.cache.get(key);
            System.out.println("Printing [key=" + key + ",value=" + value + "].");
        }
        return this.cache.getAdvancedCache().getCacheManager().getAddress();
    }

    @Override
    public void setEnvironment(Cache<String, String> cache, Set<String> keys) {
        this.cache = cache;
        this.keys = keys;
        System.out.println("Setting enironment cache=[" + this.cache.getName() + "], keys=["
                        + this.keys + "]");
    }

}
