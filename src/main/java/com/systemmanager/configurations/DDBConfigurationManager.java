package com.systemmanager.configurations;

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
import com.systemmanager.configurations.ConfigurationMap.MapValue;

public class DDBConfigurationManager implements ConfigurationManager  {

	String domain;
	String realm;
	Integer cacheTime;
	static AmazonDynamoDB clientDetails;
	static List<ConfigurationMap> myDomainRealm =  Collections.emptyList();
	CacheData2 cache;
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
	
	
	public DDBConfigurationManager(String domain,String realm,Integer cacheTime,AmazonDynamoDB clientDetails) {
		this.domain = domain;
		this.realm = realm;
		this.cacheTime = cacheTime;
		this.clientDetails = clientDetails;
		
		
		appendList(domain,realm);
		appendList(domain,"*");
		appendList("*",realm);
		appendList("*","*");
		
		cache = new CacheData2(myDomainRealm,cacheTime);
	}
	
	@Override
	public List<ConfigurationMap> myDomainRealm() {
		return myDomainRealm;
	}
	
	@Override
	public String GetParticularConfigurationStringValue(String cfgKey) {
		for(int i=0;i<myDomainRealm.size();i++)
		 {
			if(myDomainRealm.get(i).getCfgKey().equals(cfgKey))return myDomainRealm.get(i).getStringValue();
		 }
		
		return null;
	}

	@Override
	public MapValue GetParticularConfigurationMapValue(String cfgKey) {
		
		for(int i=0;i<myDomainRealm.size();i++)
		 {
			if(myDomainRealm.get(i).getCfgKey().equals(cfgKey))return myDomainRealm.get(i).getMapValue();
		 }
		return null;
	}
	
	@Override
	public Integer GetParticularConfigurationIntegerValue(String cfgKey) {
		
		for(int i=0;i<myDomainRealm.size();i++)
		 {
			if(myDomainRealm.get(i).getCfgKey().equals(cfgKey))return myDomainRealm.get(i).getIntegerValue();
		 }
		return null;
	}

	@Override
	public Double GetParticularConfigurationDoubleValue(String cfgKey) {
		for(int i=0;i<myDomainRealm.size();i++)
		 {
			if(myDomainRealm.get(i).getCfgKey().equals(cfgKey))return myDomainRealm.get(i).getDoubleValue();
		 }
		return null;
	}

	@Override
	public Map Cache(String cfgKey) {
		 cache2= cache.calling(cfgKey);
		 return cache2;
	}
}
