package com.kema.mongodb;

import com.mongodb.reactivestreams.client.*;
import org.bson.Document;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class Test01React {

	@Test
	public void test() throws Exception {
		MongoClient mongoClient = MongoClients.create("mongodb://localhost");
		MongoDatabase database = mongoClient.getDatabase("MyDb");
		MongoCollection<Document> collection = database.getCollection("MyCol");

		Document doc = new Document("name", "kema02")
				.append("company", "nokia")
				.append("count", 1)
				.append("info", new Document("x", 203).append("y", 102));


		final Subscriber<Success> subscriber = new Subscriber<Success>() {
			@Override
			public void onSubscribe(Subscription subscription) {
				subscription.request(1);
			}

			@Override
			public void onNext(Success success) {

			}

			@Override
			public void onError(Throwable throwable) {

			}

			@Override
			public void onComplete() {

			}
		};
		collection.insertOne(doc).subscribe(subscriber);
		Thread.sleep(10000);
		// database.getCollectionNames().forEach(System.out::println);
	}


}
