package com.systemmanager.configurations.entity;
//***** POJO CLASS **********/
// This is basically the POJO Class which contains the format of the DDB Table///
// Each Java Property (eg. domain,realm,etc.) corresponds to the Attributes of the items in Table


import java.util.List;
import java.util.Map;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;




@DynamoDBTable(tableName="ConfigurationTable")
public class ConfigurationMap {

	private String cfgKey;
	private String stringValue;
	private Integer integerValue;
	private Double doubleValue;
	private String domainRealm;
	private List<String> listStringValue;
	private Map<String,String> mapValue;





	@DynamoDBHashKey(attributeName="DomainRealm")
	public String getDomainRealm() { return domainRealm; }
	public void setDomainRealm(String domainRealm) {this.domainRealm = domainRealm; }


	@DynamoDBRangeKey(attributeName="CfgKey")
	public String getCfgKey() {return cfgKey; }
	public void setCfgKey(String cfgKey) { this.cfgKey= cfgKey; }	

	@DynamoDBAttribute(attributeName="StringValue")
	public String getStringValue() {return stringValue; }
	public void setStringValue(String stringValue) { this.stringValue= stringValue; }


	@DynamoDBAttribute(attributeName="IntegerValue")
	public Integer getIntegerValue() {return integerValue; }
	public void setIntegerValue(Integer integerValue) { this.integerValue= integerValue; }			


	@DynamoDBAttribute(attributeName="DoubleValue")
	public Double getDoubleValue() {return doubleValue; }
	public void setDoubleValue(Double doubleValue) { this.doubleValue= doubleValue; }		


	@DynamoDBAttribute(attributeName = "ListStringValue")
	public List<String> getListStringValue() {return listStringValue;}
	public void setListStringValue(List<String> listStringValue) {this.listStringValue = listStringValue;}


	@DynamoDBAttribute(attributeName = "MapValue")
	public Map<String, String> getMapValue() {return mapValue;}
	public void setMapValue(Map<String, String> mapValue) {this.mapValue = mapValue;}





}



