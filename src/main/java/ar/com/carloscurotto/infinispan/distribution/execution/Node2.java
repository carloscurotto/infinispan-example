/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other
 * contributors as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a full listing of
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package ar.com.carloscurotto.infinispan.distribution.execution;

import java.util.List;
import java.util.concurrent.Future;

import org.infinispan.Cache;
import org.infinispan.distexec.DefaultExecutorService;
import org.infinispan.remoting.transport.Address;

public class Node2 extends AbstractNode {

    public static void main(String[] args) throws Exception {
        new Node2().run();
    }

    public void run() throws Exception {
        Cache<String, String> cache = getCacheManager().getCache("Demo");

        // Add a listener so that we can see the puts to this node
        cache.addListener(new ConsoleListener());

        // Put a few entries into the cache so that we can see them distribution to the other nodes
        for (int i = 1; i <= 5; i++) {
            cache.put("key" + i, "value" + i);
        }

        // Creates executor service
        DefaultExecutorService executorService = new DefaultExecutorService(cache);

        // Submits a task to a specific set of keys on every node.  Note that infinispan sends only this
        // callable to the nodes where any of the specified keys are primary, it doesnt send this callable to
        // the nodes where there are no primary keys in the specified list.
        System.out.println("Submiting task to keys [key1, key2, key5] only on every node...");
        List<Future<Address>> allNodesSpecificKeysResults = executorService.submitEverywhere(
                        new PrintCacheKeysDistributedCallable(), "key1", "key2", "key5");
        for (Future<Address> allNodesSpecificKeysResult : allNodesSpecificKeysResults) {
            System.out.println(allNodesSpecificKeysResult.get());
        }

        // Submits a task to every key on every node
        System.out.println("Submiting task to every key on every node...");
        List<Future<Address>> allNodesAllKeysResults = executorService
                        .submitEverywhere(new PrintCacheKeysDistributedCallable());
        for (Future<Address> allNodesAllKeysResult : allNodesAllKeysResults) {
            System.out.println(allNodesAllKeysResult.get());
        }

        // Gets a member other than the local one.
        Address remoteMember = null;
        for (Address member : cache.getCacheManager().getMembers()) {
            if (member.compareTo(cache.getCacheManager().getAddress()) != 0) {
                remoteMember = member;
                break;
            }
        }

        // Submits a task to all keys on a specific node
        System.out.println("Submiting task to node [" + remoteMember + "] only...");
        Future<Address> specificNodeAllKeysResult = executorService.submit(remoteMember, new PrintCacheKeysDistributedCallable());
        System.out.println(specificNodeAllKeysResult.get());

        // Calculate distributed cache size
        CacheSizeCalculator calculator = new CacheSizeCalculator();
        System.out.println(calculator.size(cache, new GetAllKeysDistributedCallable()));
        System.out.println(calculator.size(cache, new GetPrimaryKeysDistributedCallable()));
        System.out.println(calculator.size(cache, new GetOptimizedPrimaryKeysDistributedCallable()));

        System.out.println("Done.");
    }

    @Override
    protected int getNodeId() {
        return 2;
    }

}
