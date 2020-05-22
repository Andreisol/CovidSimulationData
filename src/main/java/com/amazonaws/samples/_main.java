package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class _main {
	public static void main(String args[])
	{
		_main AndreiGay = new _main();
		AndreiGay.testData();
	}

	public void CreateTable()
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

	        String tableName = "CovidSimData";

	        try {
	            System.out.println("Attempting to create table; please wait...");
	            Table table = dynamoDB.createTable(tableName,
	                Arrays.asList(new KeySchemaElement("SimID", KeyType.HASH), // Partition
	                                                                          // key
	                    new KeySchemaElement("Stats", KeyType.RANGE)), // Sort key
	                Arrays.asList(new AttributeDefinition("SimID", ScalarAttributeType.N),
	                    new AttributeDefinition("Stats", ScalarAttributeType.S)),
	                new ProvisionedThroughput(10L, 10L));
	            table.waitForActive();
	            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

	        }
	        catch (Exception e) {
	            System.err.println("Unable to create table: ");
	            System.err.println(e.getMessage());
	        }

	}
	
	public void testData()
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
	    String stats = "Settings";
	    String nameOfSim = "Test";
	    String peopleInSim = "500";
	    String gridSize = "[640,480]";
	    String gridDensity = "people/pixels";
	    Double infectStartPct = 3.0;
	    Double infectStartRoamers = 5.0;
	    
       Item load = new Item()
    		   .withPrimaryKey("SimID", simId, "Stats", stats)
    		   .withString("nameOfSim", nameOfSim)
    		   .withString("peopleInSim", peopleInSim)
    		   .withString("gridSize", gridSize)
    		   .withString("gridDensity", gridDensity)
    		   .withDouble("infectStartPct", 3.0)
    		   .withDouble("infectStartRoamers", infectStartRoamers);
       
       table.putItem(load);
	}
	
	public void work()
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
		
		// Build a list of related items
		List<Number> relatedItems = new ArrayList<Number>();
		relatedItems.add(341);
		relatedItems.add(472);
		relatedItems.add(649);

		//Build a map of product pictures
		Map<String, String> pictures = new HashMap<String, String>();
		pictures.put("FrontView", "http://example.com/products/123_front.jpg");
		pictures.put("RearView", "http://example.com/products/123_rear.jpg");
		pictures.put("SideView", "http://example.com/products/123_left_side.jpg");

		//Build a map of product reviews
		Map<String, List<String>> reviews = new HashMap<String, List<String>>();

		List<String> fiveStarReviews = new ArrayList<String>();
		fiveStarReviews.add("Excellent! Can't recommend it highly enough!  Buy it!");
		fiveStarReviews.add("Do yourself a favor and buy this");
		reviews.put("FiveStar", fiveStarReviews);

		List<String> oneStarReviews = new ArrayList<String>();
		oneStarReviews.add("Terrible product!  Do not buy this.");
		reviews.put("OneStar", oneStarReviews);

		// Build the item
		Item item = new Item()
		    .withPrimaryKey("SimID", 11, "Stats", "test")
		    .withString("Title", "Bicycle 123")
		    .withString("Description", "123 description")
		    .withString("BicycleType", "Hybrid")
		    .withString("Brand", "Brand-Company C")
		    .withNumber("Price", 500)
		    .withStringSet("Color",  new HashSet<String>(Arrays.asList("Red", "Black")))
		    .withString("ProductCategory", "Bicycle")
		    .withBoolean("InStock", true)
		    .withNull("QuantityOnHand")
		    .withList("RelatedItems", relatedItems)
		    .withMap("Pictures", pictures)
		    .withMap("Reviews", reviews);

		// Write the item to the table
		PutItemOutcome outcome = table.putItem(item);
	}
}
