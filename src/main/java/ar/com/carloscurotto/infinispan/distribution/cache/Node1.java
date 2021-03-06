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
package ar.com.carloscurotto.infinispan.distribution.cache;

import org.infinispan.Cache;

public class Node1 extends AbstractNode {

    public static void main(String[] args) throws Exception {
        new Node1().run();
    }

    public void run() {
        Cache<String, String> cache = getCacheManager().getCache("Demo");

        // Add a listener so that we can see the puts to this node
        cache.addListener(new ConsoleListener());

        // IsEmpty
        System.out.println(cache.isEmpty());

        // ContainsKey
        System.out.println(cache.containsKey("key1"));

        // ContainsValue
        System.out.println(cache.containsKey("value1"));

        // KeySet
        System.out.println(cache.keySet());

        // Values
        System.out.println(cache.values());

        // EntrySet
        System.out.println(cache.entrySet());

        // Clear
        cache.clear();
        System.out.println(cache.containsKey("key1"));

        System.out.println("Done.");
    }

    @Override
    protected int getNodeId() {
        return 1;
    }

}
