package com.systemmanager.configurations;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CacheData2 {
	
	
	LoadingCache<String, ConfigurationMap> cache;
		
	private static ConfigurationMap loadCache(String cfgKey,List<ConfigurationMap>list) {
		//this function will only be called when the cfg value is not found and we will load the value from the "list"
	      
	      for(int i=0;i<list.size();i++)
	      {
	    	  if(list.get(i).getCfgKey().equals(cfgKey))
	    		  {
	    		  	System.out.println("Loading into the Cache for this cfgKey");
	    		  	return list.get(i);
	    		  }
	      }
	      System.out.println("Invalid Configuration Key for the given Domain and Realm");
	      return null;
}
	

	public CacheData2(List<ConfigurationMap> list,Integer cacheTime) {
	      //create a cache for configurations data based on the cfgKey
		try {
			
			
			 CacheLoader<String, ConfigurationMap> loader = new CacheLoader<String,ConfigurationMap>() { 
	        	 // build the cacheloader
	            @Override
	            public ConfigurationMap load(String cfgKey) throws Exception {
	               //make the expensive call
	               return loadCache(cfgKey,list);
	           }
			 };
			  cache = CacheBuilder.newBuilder()
			         .expireAfterAccess(cacheTime, TimeUnit.MINUTES)      // cache will expire after 30 minutes of access
			         .build(loader);

		}
		catch (Exception e) {}

}
	public Map calling (String cfgKey) {
		try {
			System.out.println("Caching for the given CfgKey");
			cache.getUnchecked(cfgKey);
			return cache.asMap();
		}
		catch (Exception e) {}
		return null;	
	}
	
	
	
	
}
