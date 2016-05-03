package com.rhc.jdg.provider;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;

public class JavaCacheProvider {
	private CacheContainer cacheContainer;
// Java code - embeded method gives you access to cache.evict("key1"); to evict the record. 
// or we can set maxEntry and then the system can then move it to file system.
//the way to get rid of it is to remove/delete it.
	protected CacheContainer getCacheContainer() {
		if (cacheContainer == null) {
			Configuration config = new ConfigurationBuilder()
				.jmxStatistics()
					.enable()
				.persistence().addSingleFileStore().purgeOnStartup(true)
				.build();
			cacheContainer = new DefaultCacheManager(config);
		}
		return cacheContainer;
	}

	public Cache<String, String> getCache(String identifier) {
		return getCacheContainer().getCache(identifier);
	}

	public Cache<String, String> getCache() {
		return getCacheContainer().getCache();
	}
	
	public void stop() {
		if (cacheContainer != null) {
			cacheContainer.stop();
		}
	}
}
