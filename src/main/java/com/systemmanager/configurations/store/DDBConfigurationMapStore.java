package com.systemmanager.configurations.store;

import java.util.Arrays;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.google.common.base.Joiner;
import com.systemmanager.configurations.entity.ConfigurationMap;

public class DDBConfigurationMapStore {
	
	public ConfigurationMap getConfigurationMap(String domain,String realm,String cfgKey,DynamoDBMapper mapper) {
		
		ConfigurationMap configurationMap = new ConfigurationMap();
		String domainRealm =  Joiner.on(",").skipNulls().join(Arrays.asList(domain,realm));
		configurationMap.setDomainRealm(domainRealm);
		configurationMap.setCfgKey(cfgKey);
	
		return mapper.load(configurationMap, getDDBMapperConfig());
	}
	
	private  DynamoDBMapperConfig getDDBMapperConfig() {
        return new DynamoDBMapperConfig.Builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT).build();
    }
	

}
