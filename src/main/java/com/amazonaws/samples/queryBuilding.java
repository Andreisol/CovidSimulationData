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

public class queryBuilding {
	
    public static void main(String[] args) throws Exception {

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
        valueMap.put(":id", 1);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#sID = :id").withNameMap(nameMap)
            .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        
        try {
            System.out.println("Stat name from Sim 1");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getNumber("SimID") + ": " + item.getString("Stats"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query simID from 1");
            System.err.println(e.getMessage());
        }
        
        
        valueMap.put(":id", 1);
        valueMap.put(":Sts", "STATS#b0");
  

        querySpec
            .withKeyConditionExpression("#sID = :id and Stats = :Sts").withNameMap(nameMap)
            .withValueMap(valueMap);

        try {
            System.out.println("Simulation 1 for Stats#b0");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getNumber("SimID") + ": " + item.getString("Stats") + " " + item.getNumber("activeInfected") + " "  + item.getNumber("activeRoamers"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query Sim1 for Stats#b0");
            System.err.println(e.getMessage());
        }
        
    }

}
