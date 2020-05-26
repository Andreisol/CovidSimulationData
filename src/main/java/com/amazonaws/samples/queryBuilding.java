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

        Table table = dynamoDB.getTable("CovidSimData"); //Change this table name to what ever table you make.

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#sID", "SimID");
        
        //nameMap.put("#stats", "Stats"); //Only need this line when querying off partition and sort key

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":id", 2);
        //valueMap.put(":Sts", "STATS#b3"); //Only need this line when querying off partition and sort key

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#sID = :id").withNameMap(nameMap)
            .withValueMap(valueMap); //Queries off of partition Key

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        //This chunk of code will Query based off partition key
        
        /*
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
        */
        
        
        //This chunk of code will query based off partition key and sortKey
        
        /*
        querySpec
            .withKeyConditionExpression("#sID = :id and #stats = :Sts").withNameMap(nameMap)
            .withValueMap(valueMap);

        try {
            System.out.println("Simulation 1 for Stats#b1");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getNumber("SimID") + ": " + item.getString("Stats") + " " + item.getString("building") + " : Active infected: " + item.getNumber("activeInfected"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to query Sim1 for Stats#b1");
            System.err.println(e.getMessage());
        }
        */
        
        
    }

}
