package com.systemmanager.configurations;


import java.util.Scanner;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class Main {

	public static void main(String[] args) {
		String domain;
		String realm;
		Integer cacheTime;
		AmazonDynamoDB clientDetails;
		
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
	    try {
	        credentialsProvider.getCredentials();
	    } catch (Exception e) {
	        throw new AmazonClientException(
	                "Cannot load the credentials from the credential profiles file. " +
	                "Please make sure that your credentials file is at the correct " +
	                "location (C:\\Users\\PD\\.aws\\credentials), and is in valid format.",
	                e);
	    }
		clientDetails = AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider)
				 .withRegion("us-east-1")
				 .build();
		
	
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Domain of your application");
		domain = sc.nextLine();
		System.out.println("Enter Realm of your application");
		realm = sc.nextLine();
		System.out.println("Enter the Cache time required in minutes");
		cacheTime = sc.nextInt();
		
		
		DDBConfigurationManager newApplication = new DDBConfigurationManager(domain, realm, cacheTime, clientDetails);
		//System.out.println(newApplication.myDomainRealm().get(0).getCfgKey());
		System.out.println("Size of myDomainRealm list " +newApplication.myDomainRealm().size());
		
		System.out.println(newApplication.Cache("sqs.configuration"));
		System.out.println(newApplication.Cache("sqs.configuration"));
		System.out.println(newApplication.Cache("sqs.reservationRequests.queueName"));
		
		
		System.out.println(newApplication.GetParticularConfigurationIntegerValue("visibilityTimeout"));
		System.out.println(newApplication.GetParticularConfigurationMapValue("sqs.configuration"));
		System.out.println(newApplication.GetParticularConfigurationDoubleValue("sqs.configuration"));
		
		
		//System.out.println(newApplication.GetParticularConfigurationStringValue("sqs.configurations"));
	

	}



}
