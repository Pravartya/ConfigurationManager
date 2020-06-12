package com.systemmanager.configurations.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.systemmanager.configurations.entity.ConfigurationMap;
import com.systemmanager.configurations.store.DDBConfigurationMapStore;

public class CacheData {

	String domain;
	String realm;
	DynamoDBMapper mapper;
	LoadingCache<String, ConfigurationMap> cache;

	//***  Return Reference type object of ConfigurationMap from cache  **///
	public ConfigurationMap getConfigurationFromCache(String cfgKey) {
		return cache.getUnchecked(cfgKey);
	}

	
	//** Constructor call to build the cache in the system based on the configurationKey provided by the client **///
	public CacheData(DynamoDBMapper mapper  ,Integer cacheTime ,String domain,String realm,String applicationName) {
		
		try {

			CacheLoader<String, ConfigurationMap> loader = new CacheLoader<String,ConfigurationMap>() { 

				@Override
				public ConfigurationMap load(String cfgKey) throws Exception {
					return getConfigurationFromDDB(cfgKey,applicationName);
				}
			};
			cache = CacheBuilder.newBuilder()
					.expireAfterAccess(cacheTime, TimeUnit.MINUTES)      // cache will expire after 30 minutes of access
					.build(loader);

		}
		catch (Exception e) {}

	}

	
	//***  Return Reference type object of ConfigurationMap from DDB Table  **///
	private ConfigurationMap getConfigurationFromDDB(String cfgKey,String applicationName) throws IllegalArgumentException {
		DDBConfigurationMapStore ddbConfigurationMapStore = new DDBConfigurationMapStore();

		if(ddbConfigurationMapStore.getConfigurationMap(domain, realm, cfgKey, mapper,applicationName) != null )
			return ddbConfigurationMapStore.getConfigurationMap(domain, realm, cfgKey, mapper,applicationName);

		if(ddbConfigurationMapStore.getConfigurationMap(domain, "*", cfgKey, mapper,applicationName) != null)
			return ddbConfigurationMapStore.getConfigurationMap(domain, "*", cfgKey, mapper,applicationName);

		if(ddbConfigurationMapStore.getConfigurationMap("*", realm, cfgKey, mapper,applicationName)!=null )
			return ddbConfigurationMapStore.getConfigurationMap("*", realm, cfgKey, mapper,applicationName);

		if(ddbConfigurationMapStore.getConfigurationMap("*", "*", cfgKey, mapper,applicationName) != null)
			return ddbConfigurationMapStore.getConfigurationMap("*", "*", cfgKey, mapper,applicationName);

		throw new IllegalArgumentException("Invalid Input! for: "+cfgKey); 
	}

}
