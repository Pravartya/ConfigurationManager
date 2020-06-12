package com.systemmanager.configurations.app;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.systemmanager.configurations.entity.ConfigurationMap;


public interface ConfigurationManager {
	                                                                              //if client needs for the cache service	
	String getConfigurationStringValue(String cfgKey) throws IllegalArgumentException;
	Integer getConfigurationIntegerValue(String cfgKey) throws IllegalArgumentException;
	Double getConfigurationDoubleValue(String cfgKey) throws IllegalArgumentException;
	List<String> getConfigurationListStringValue(String cfgKey) throws IllegalArgumentException;
  

}
