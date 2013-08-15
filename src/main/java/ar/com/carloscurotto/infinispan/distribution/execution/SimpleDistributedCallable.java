package ar.com.carloscurotto.infinispan.distribution.execution;

import java.io.Serializable;
import java.util.Set;

import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;
import org.infinispan.remoting.transport.Address;

public class SimpleDistributedCallable implements DistributedCallable<String, String, Address>,
                Serializable {

    private static final long serialVersionUID = 1L;

    private Cache<String, String> cache;
    private Set<String> keys;

    @Override
    public Address call() throws Exception {
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
