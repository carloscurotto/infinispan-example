package ar.com.carloscurotto.infinispan.distribution.execution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.infinispan.Cache;
import org.infinispan.distexec.DefaultExecutorService;
import org.infinispan.distexec.DistributedCallable;

public class CacheSizeCalculator {

    public int size(Cache<String, String> cache,
                    DistributedCallable<String, String, Set<String>> callable) throws Exception {
        Set<String> allUniqueKeys = new HashSet<String>();
        DefaultExecutorService executorService = new DefaultExecutorService(cache);
        List<Future<Set<String>>> allNodesAllKeysResults = executorService
                        .submitEverywhere(callable);
        for (Future<Set<String>> eachNodeAllKeysResults : allNodesAllKeysResults) {
            allUniqueKeys.addAll(eachNodeAllKeysResults.get());
        }
        return allUniqueKeys.size();
    }

}
