package ar.com.carloscurotto.infinispan.distribution.execution;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;
import org.infinispan.remoting.transport.Address;

public class GetOptimizedPrimaryKeysDistributedCallable implements
                DistributedCallable<String, String, Set<String>>, Serializable {

    private static final long serialVersionUID = 1L;

    private Cache<String, String> cache;

    @Override
    public Set<String> call() throws Exception {
        Set<String> result = new HashSet<String>();
        Address localAddress = this.cache.getCacheManager().getAddress();
        Address remoteAddress = null;
        for (String key : this.cache.keySet()) {
            remoteAddress = this.cache.getAdvancedCache().getDistributionManager()
                            .getPrimaryLocation(key);
            if (localAddress.equals(remoteAddress)) {
                System.out.println("Key=[" + key + "] is primary on node [" + localAddress + "]");
                result.add(key);
            }
        }
        return result;
    }

    @Override
    public void setEnvironment(Cache<String, String> cache, Set<String> inputKeys) {
        this.cache = cache;
    }

}
