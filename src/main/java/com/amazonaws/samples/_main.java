package com.amazonaws.samples;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
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
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class _main {
	public static void main(String args[]) throws IOException
	{
		_main AndreiGay = new _main();
		//AndreiGay.CreateTable();
		
		//AndreiGay.TestS3();
		//AndreiGay.UploadS3Obj();
		AndreiGay.DonwloadS3();
	}

	public void DonwloadS3() throws IOException
	{
		 Regions clientRegion = Regions.DEFAULT_REGION;
	        String bucketName = "ytwff672f82f";
	        String key = "Test";

	        
	        
	        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
	        try {
	            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	                    .withRegion(clientRegion)
	                    .withCredentials(new ProfileCredentialsProvider("credentials.txt", "Ethan2"))
	                    .build();

	            // Get an object and print its contents.
	            System.out.println("Downloading an object");
	            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
	            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
	            System.out.println("Content: ");
	            displayTextInputStream(fullObject.getObjectContent());

	            // Get a range of bytes from an object and print the bytes.
	           /* GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key)
	                    .withRange(0, 9);
	            objectPortion = s3Client.getObject(rangeObjectRequest);
	            System.out.println("Printing bytes retrieved.");
	            displayTextInputStream(objectPortion.getObjectContent());

	            // Get an entire object, overriding the specified response headers, and print the object's content.
	            ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
	                    .withCacheControl("No-cache")
	                    .withContentDisposition("attachment; filename=example.txt");
	            GetObjectRequest getObjectRequestHeaderOverride = new GetObjectRequest(bucketName, key)
	                    .withResponseHeaders(headerOverrides);
	            headerOverrideObject = s3Client.getObject(getObjectRequestHeaderOverride);
	            displayTextInputStream(headerOverrideObject.getObjectContent());*/
	        } catch (AmazonServiceException e) {
	            // The call was transmitted successfully, but Amazon S3 couldn't process 
	            // it, so it returned an error response.
	            e.printStackTrace();
	        } catch (SdkClientException e) {
	            // Amazon S3 couldn't be contacted for a response, or the client
	            // couldn't parse the response from Amazon S3.
	            e.printStackTrace();
	        } finally {
	            // To ensure that the network connection doesn't remain open, close any open input streams.
	            if (fullObject != null) {
	                fullObject.close();
	            }
	            if (objectPortion != null) {
	                objectPortion.close();
	            }
	            if (headerOverrideObject != null) {
	                headerOverrideObject.close();
	            }
	        }
	}
	public void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }
	
	public void TestS3()
	{
		Regions clientRegion = Regions.US_WEST_2;
        String bucketName = "test";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider("credentials.txt", "Ethan2"))
                    .withRegion(clientRegion)
                    .build();
            
            if (!s3Client.doesBucketExistV2(bucketName)) {
                // Because the CreateBucketRequest object doesn't specify a region, the
                // bucket is created in the region specified in the client.
                s3Client.createBucket(new CreateBucketRequest(bucketName));

                // Verify that the bucket was created by retrieving it and checking its location.
                String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
                System.out.println("Bucket location: " + bucketLocation);
            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it and returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
	
	public void ListS3()
	{
		Regions clientRegion = Regions.US_WEST_2;
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();
		
		List<Bucket> buckets = s3.listBuckets();
		for(Bucket b: buckets)
		{
			System.out.println(b.getName());
		}
	}
	
	public void UploadS3Obj()
	{
		
		 Regions clientRegion = Regions.DEFAULT_REGION;
	        String bucketName = "";
	        String stringObjKeyName = "*** String object key name ***";
	        String fileObjKeyName = "*** File object key name ***";
	        String fileName = "/Users/courtneydavies/Desktop/ShrekS3Test.jpg";

	        try {
	            //This code expects that you have AWS credentials set up per:
	            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
	            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	                    .withRegion(clientRegion)
	                    .build();

	            // Upload a text string as a new object.
	            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

	            // Upload a file as a new object with ContentType and title specified.
	            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
	            ObjectMetadata metadata = new ObjectMetadata();
	            metadata.setContentType("plain/text");
	            metadata.addUserMetadata("title", "someTitle");
	            request.setMetadata(metadata);
	            s3Client.putObject(request);
	        } catch (AmazonServiceException e) {
	            // The call was transmitted successfully, but Amazon S3 couldn't process 
	            // it, so it returned an error response.
	            e.printStackTrace();
	        } catch (SdkClientException e) {
	            // Amazon S3 couldn't be contacted for a response, or the client
	            // couldn't parse the response from Amazon S3.
	            e.printStackTrace();
	        }
		
		/*
		try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
			Regions clientRegion = Regions.US_WEST_2;
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Upload a text string as a new object.
            //s3Client.putObject("ytwff672f82f", "Test", "Uploaded String Object");

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest("ytwff672f82f", "TestImage", new File("/Users/courtneydavies/Desktop/ShrekS3Test.jpg"));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }*/
	}
	
	public void CreateTable()
	{
		/* ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
	        try {
	            credentialsProvider.getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. " +
	                    "Please make sure that your credentials file is at the correct " +
	                    "location (/Users/johnmortensen/.aws/credentials), and is in valid format.",
	                    e);
	        }*/
		
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider("credentials.txt", "Ethan2");
        credentialsProvider.getCredentials();

		
	        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
	        	.withCredentials(credentialsProvider)
	            .withRegion("us-west-2")
	            .build();

	        DynamoDB dynamoDB = new DynamoDB(client);

	        String tableName = "TestLocalCreds";

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
