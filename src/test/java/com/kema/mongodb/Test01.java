package com.kema.mongodb;

import static org.junit.Assert.*;

import com.mongodb.session.ClientSession;
import org.bson.Document;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
//import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Test01 {

	@Test
	public void test() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("MyDb");
		MongoCollection<BasicDBObject> collection = database.getCollection("MyCol", BasicDBObject.class);

		BasicDBObject document = new BasicDBObject();
		document.put("name", "kema");
		document.put("company", "nokia");

		collection.insertOne(document);
		// database.getCollectionNames().forEach(System.out::println);
	}

	@Test
	public void testTrans() {
		MongoClient client = new MongoClient("localhost", 27017);
		MongoCollection<BasicDBObject> collection = client.getDatabase("MyDb").getCollection("MyCol", BasicDBObject.class);

		BasicDBObject document = new BasicDBObject();
		document.put("name", "kema02");
		document.put("company", "nokia");
		
		 ClientSessionOptions option = ClientSessionOptions.builder().build();
		try (ClientSession clientSession = client.startSession(option)) {
//			clientSession.startTransaction();
			collection.insertOne(clientSession, document);
//			collection.insertOne(clientSession, docTwo);
//			clientSession.commitTransaction();
		}
	}

}
