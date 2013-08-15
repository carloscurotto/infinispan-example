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

import java.io.IOException;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

@SuppressWarnings("unused")
public abstract class AbstractNode {

    private final EmbeddedCacheManager cacheManager;

    public AbstractNode() {
        //this.cacheManager = createCacheManagerProgramatically();
        try {
            this.cacheManager = createCacheManagerFromXml();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static EmbeddedCacheManager createCacheManagerProgramatically() {
        return new DefaultCacheManager(GlobalConfigurationBuilder.defaultClusteredBuilder()
                        .transport().addProperty("configurationFile", "jgroups.xml").build(),
                        new ConfigurationBuilder().clustering().cacheMode(CacheMode.DIST_SYNC)
                                        .hash().numOwners(2).build());
    }

    private static EmbeddedCacheManager createCacheManagerFromXml() throws IOException {
        return new DefaultCacheManager("infinispan-distribution.xml");
    }

    protected EmbeddedCacheManager getCacheManager() {
        return cacheManager;
    }

    protected abstract int getNodeId();

}
