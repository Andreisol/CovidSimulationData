package com.amazonaws.samples;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class addBuilding {
	
	public static void main(String[] args)
	{
		
	}
	
	public void addBuild(String sortKey,String building, String gridRegion, int msOfLastUpdate, int activeInfected,int activeRoamers)
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

	    Integer simId = 1;
	    String stats = sortKey;
	    String build = building;
	    String grid = gridRegion;
	    int lastUpdate = msOfLastUpdate;
	    int infected = activeInfected;
	    int roamers = activeRoamers;

	    
       Item load = new Item()
    		   .withPrimaryKey("SimID", simId, "Stats", stats)
    		   .withString("building", build)
    		   .withString("gridRegion", grid)
    		   .withInt("msOfLastUpdate", lastUpdate)
    		   .withInt("activeInfected", infected)
    		   .withInt("activeRoamers", roamers);
    		  
       
       table.putItem(load);
	}
}


