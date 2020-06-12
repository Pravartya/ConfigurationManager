package com.systemmanager.configurations.app;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.systemmanager.configurations.entity.ConfigurationMap;
import com.systemmanager.configurations.entity.ConfigurationMap.MapValue;


public interface ConfigurationManager {
	
	List<ConfigurationMap> myDomainRealm();       //Client will give his Domain and Realm of its application                                                                                   //if client needs for the cache service	
	String getConfigurationStringValue(String cfgKey) throws Exception;
	MapValue getConfigurationMapValue(String cfgKey) throws Exception;
	Integer getConfigurationIntegerValue(String cfgKey) throws Exception;
	Double getConfigurationDoubleValue(String cfgKey) throws Exception;
  

}
