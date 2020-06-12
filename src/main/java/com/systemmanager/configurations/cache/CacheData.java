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
		
	
	public ConfigurationMap getConfigurationFromCache(String cfgKey) {
		return cache.getUnchecked(cfgKey);
	}
	
	public CacheData(DynamoDBMapper mapper  ,Integer cacheTime ,String domain,String realm) {
	      //create a cache for configurations data based on the cfgKey
		try {
			
			 CacheLoader<String, ConfigurationMap> loader = new CacheLoader<String,ConfigurationMap>() { 
	        	 // build the cacheloader
	            @Override
	            public ConfigurationMap load(String cfgKey) throws Exception {
	               //make the expensive call
	               return getConfigurationFromDDB(cfgKey);
	           }
			 };
			  cache = CacheBuilder.newBuilder()
			         .expireAfterAccess(cacheTime, TimeUnit.MINUTES)      // cache will expire after 30 minutes of access
			         .build(loader);

		}
		catch (Exception e) {}

}

	private ConfigurationMap getConfigurationFromDDB(String cfgKey) throws IllegalArgumentException {
		DDBConfigurationMapStore ddbConfigurationMapStore = new DDBConfigurationMapStore();
		
		if(ddbConfigurationMapStore.getConfigurationMap(domain, realm, cfgKey, mapper) != null )
			return ddbConfigurationMapStore.getConfigurationMap(domain, realm, cfgKey, mapper);
		
		if(ddbConfigurationMapStore.getConfigurationMap(domain, "*", cfgKey, mapper) != null)
			return ddbConfigurationMapStore.getConfigurationMap(domain, "*", cfgKey, mapper);
		
		if(ddbConfigurationMapStore.getConfigurationMap("*", realm, cfgKey, mapper)!=null )
			return ddbConfigurationMapStore.getConfigurationMap("*", realm, cfgKey, mapper);
		
		if(ddbConfigurationMapStore.getConfigurationMap("*", "*", cfgKey, mapper) != null)
			return ddbConfigurationMapStore.getConfigurationMap("*", "*", cfgKey, mapper);
		
		throw new IllegalArgumentException("Invalid Input!"); 
	}

}
