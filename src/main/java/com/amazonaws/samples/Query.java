package com.amazonaws.samples;


import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

public class Query {
	
	public static void main(String[] args)
	{
		Item itemboy = Query.getItem(2, "p27");
		
		System.out.println(itemboy.getInt("collisions"));
	}
	
	public static Item getItem(int primaryKey,String sortKey)
	{
    	ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/johnmortensen/.aws/credentials), and is in valid format.",
                    e);
        }
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        	.withCredentials(credentialsProvider)
            .withRegion("us-west-2")
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("CovidSimData");

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#sID", "SimID");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":id", primaryKey);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#sID = :id").withNameMap(nameMap)
            .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        
           
        valueMap.put(":id", primaryKey);
        valueMap.put(":Sts", sortKey);
  

        querySpec
            .withKeyConditionExpression("#sID = :id and Stats = :Sts").withNameMap(nameMap)
            .withValueMap(valueMap);

        try {
            System.out.println("Simulation 1 for Stats#b0");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query Sim1 for Stats#b0");
            System.err.println(e.getMessage());
        }
        
        return item;     
    }

	
	public static Object getAttribute(int primaryKey,String sortKey,String attribute)
	{
    	ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/johnmortensen/.aws/credentials), and is in valid format.",
                    e);
        }
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        	.withCredentials(credentialsProvider)
            .withRegion("us-west-2")
            .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("CovidSimData");

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#sID", "SimID");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":id", primaryKey);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#sID = :id").withNameMap(nameMap)
            .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

      
        
        
        valueMap.put(":id", primaryKey);
        valueMap.put(":Sts", sortKey);
  

        querySpec
            .withKeyConditionExpression("#sID = :id and Stats = :Sts").withNameMap(nameMap)
            .withValueMap(valueMap);

        try {
            System.out.println("Simulation " + primaryKey + " for sortKey " + sortKey + " for attr " + attribute);
            items = table.query(querySpec);
            iterator = items.iterator();
            
            while (iterator.hasNext()) {
                item = iterator.next();
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query Sim1 for Stats#b0");
            System.err.println(e.getMessage());
        }
        
        return item.get(attribute);
	}
}



