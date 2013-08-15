package ar.com.carloscurotto.infinispan.distribution.execution;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;

public class GetAllKeysDistributedCallable implements DistributedCallable<String, String, Set<String>>,
                Serializable {

    private static final long serialVersionUID = 1L;

    private Cache<String, String> cache;

    @Override
    public Set<String> call() throws Exception {
        return new HashSet<String>(this.cache.keySet());
    }

    @Override
    public void setEnvironment(Cache<String, String> cache, Set<String> keys) {
        this.cache = cache;
    }

}
