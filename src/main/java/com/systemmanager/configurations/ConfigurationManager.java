package com.systemmanager.configurations;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.systemmanager.configurations.ConfigurationMap.MapValue;


public interface ConfigurationManager {
	
	
	List<ConfigurationMap> myDomainRealm();       //Client will give his Domain and Realm of its application
	Map Cache(String cfgKey );                                                                                     //if client needs for the cache service	
	String GetParticularConfigurationStringValue(String cfgKey);
	MapValue GetParticularConfigurationMapValue(String cfgKey);
	Integer GetParticularConfigurationIntegerValue(String cfgKey);
	Double GetParticularConfigurationDoubleValue(String cfgKey);
  
	

}
