package com.amazonaws.samples;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class addData {
	
	public static void main(String[] args)
	{
		addData addBoy = new addData();
		addBoy.addBuild("STATS#b1", "Empire State", "[0,60,0,48]", 160, 1, 1);


	}
	
	public Table connect()
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
        
        return table;
	}
	
	public void addBuild(String sortKey,String building, String gridRegion, int msOfLastUpdate, int activeInfected,int activeRoamers)
	{
		Table table = connect();

	    Integer simId = 2;
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
	
	public void addPers(String sortKey, String healthStatus, boolean isRoamer, int collisions, String infectedBy, int infectionLength, int deathTime)
	{
		Table table = connect();

	    Integer simId = 2;
	    
       Item load = new Item()
    		   .withPrimaryKey("SimID", simId, "Stats", sortKey)
    		   .withString("healthStatus", healthStatus)
    		   .withBoolean("isRoamer", isRoamer)
    		   .withInt("collisions", collisions)
    		   .withString("infectedBy", infectedBy)
    		   .withInt("infecitonLength", infectionLength)
    		   .withInt("deathTime", deathTime);
       
       table.putItem(load);
	}
}


