package com.systemmanager.configurations.app;

import java.util.List;
import java.util.Map;



public interface ConfigurationManager {

	String getConfigurationStringValue(String cfgKey) throws IllegalArgumentException;
	Integer getConfigurationIntegerValue(String cfgKey) throws IllegalArgumentException;
	Double getConfigurationDoubleValue(String cfgKey) throws IllegalArgumentException;
	List<String> getConfigurationListStringValue(String cfgKey) throws IllegalArgumentException;
	Map<String,String> getConfigurationMapValue(String cfgKey) ;

}
