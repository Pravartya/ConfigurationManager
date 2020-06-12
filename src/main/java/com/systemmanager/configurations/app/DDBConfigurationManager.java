package com.systemmanager.configurations.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.ListUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.base.Joiner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.jdi.IntegerValue;
import com.systemmanager.configurations.cache.CacheData;
import com.systemmanager.configurations.entity.ConfigurationMap;
public class DDBConfigurationManager implements ConfigurationManager  {

	String domain;
	String realm;
	Integer cacheTime;
	CacheData cache;
	Map<String, ConfigurationMap> cache2 = new HashMap<String, ConfigurationMap>();

	
	
	public DDBConfigurationManager(String domain,String realm,Integer cacheTime,DynamoDBMapper mapper,String applicationName) {
		this.domain = domain;
		this.realm = realm;
		this.cacheTime = cacheTime;
		cache = new CacheData(mapper,cacheTime,domain,realm,applicationName);
	}

	
	
	//** Returns stringValue if found, otherwise throws IllegalArgumentException **///
	@Override
	public String getConfigurationStringValue(String cfgKey) throws IllegalArgumentException{
		String stringValue =  cache.getConfigurationFromCache(cfgKey).getStringValue();
		if(stringValue == null) throw new IllegalArgumentException("No String Value Found! for: "+ cfgKey);
		return stringValue;
	}

	//** Returns listStringValue if found, otherwise throws IllegalArgumentException **///
	@Override
	public List<String> getConfigurationListStringValue(String cfgKey) throws IllegalArgumentException  {
		List<String> listStringValue = cache.getConfigurationFromCache(cfgKey).getListStringValue();
		if(listStringValue == null) throw new IllegalArgumentException("No Map Value Found!for: "+ cfgKey);
		return listStringValue ;
	}


	//** Returns integerValue if found, otherwise throws IllegalArgumentException **///
	@Override
	public Integer getConfigurationIntegerValue(String cfgKey) throws IllegalArgumentException {
		Integer integerValue = cache.getConfigurationFromCache(cfgKey).getIntegerValue();
		if(integerValue == null) throw new IllegalArgumentException("No Integer Value Found!for: "+ cfgKey);
		return integerValue;
	}

	//** Returns doubleValue if found, otherwise throws IllegalArgumentException **///
	@Override
	public Double getConfigurationDoubleValue(String cfgKey) throws IllegalArgumentException {
		Double doubleValue = cache.getConfigurationFromCache(cfgKey).getDoubleValue();
		if(doubleValue == null )throw new IllegalArgumentException("No Double Value Found!for: "+ cfgKey);
		return doubleValue;
	}


}
