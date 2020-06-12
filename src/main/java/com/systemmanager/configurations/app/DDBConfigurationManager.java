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
import com.systemmanager.configurations.entity.ConfigurationMap.MapValue;

public class DDBConfigurationManager implements ConfigurationManager  {

	String domain;
	String realm;
	Integer cacheTime;
	static AmazonDynamoDB clientDetails;
	static List<ConfigurationMap> myDomainRealm =  Collections.emptyList();
	CacheData cache;
	Map<String, ConfigurationMap> cache2 = new HashMap<String, ConfigurationMap>();
	
	
	
	public static void appendList(String domain,String realm)
	{
		try {
			myDomainRealm = ListUtils.union(myDomainRealm,query(domain,realm));
		}
		catch(Exception e){}		
	}
	public static List<ConfigurationMap> query(String domain,String realm) {
		
		String domainRealm =  Joiner.on(",").skipNulls().join(Arrays.asList(domain,realm));
	    DynamoDBMapper mapper = new DynamoDBMapper(clientDetails);
	    Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(domainRealm));
		DynamoDBQueryExpression<ConfigurationMap> queryExpression = new DynamoDBQueryExpression<ConfigurationMap>()
				.withKeyConditionExpression("DomainRealm = :v1")
				.withExpressionAttributeValues(eav);
		PaginatedQueryList<ConfigurationMap> list = mapper.query(ConfigurationMap.class, queryExpression);
		return list;
	}
	
	
	public DDBConfigurationManager(String domain,String realm,Integer cacheTime,DynamoDBMapper mapper) {
		this.domain = domain;
		this.realm = realm;
		this.cacheTime = cacheTime;
		this.clientDetails = clientDetails;
		
		
		appendList(domain,realm);
		appendList(domain,"*");
		appendList("*",realm);
		appendList("*","*");
		
		cache = new CacheData(mapper,cacheTime,domain,realm);
	}
	
	@Override
	public List<ConfigurationMap> myDomainRealm() {
		return myDomainRealm;
	}
	
	@Override
	public String getConfigurationStringValue(String cfgKey) throws IllegalArgumentException{
			String stringValue =  cache.getConfigurationFromCache(cfgKey).getStringValue();
			if(stringValue == null) throw new IllegalArgumentException("No String Value Found!");
			return stringValue;
	}

	@Override
	public MapValue getConfigurationMapValue(String cfgKey) throws IllegalArgumentException  {
		MapValue mapValue = cache.getConfigurationFromCache(cfgKey).getMapValue();
		if(mapValue == null) throw new IllegalArgumentException("No Map Value Found!");
		return mapValue;
	}
	
	
	@Override
	public Integer getConfigurationIntegerValue(String cfgKey) throws IllegalArgumentException {
		Integer integerValue = cache.getConfigurationFromCache(cfgKey).getIntegerValue();
		if(integerValue == null) throw new IllegalArgumentException("No Integer Value Found!");
		return integerValue;
	}

	@Override
	public Double getConfigurationDoubleValue(String cfgKey) throws IllegalArgumentException {
		Double doubleValue = cache.getConfigurationFromCache(cfgKey).getDoubleValue();
		if(doubleValue == null )throw new IllegalArgumentException("No Double Value Found!");
		return doubleValue;
	}


}
