package std.demo.local.mongoDB;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoDBDemo {

	public static void main(String[] args) {
		
		MongoClient mongoClient = null;
		try {
			// 连接到 mongodb 服务
			mongoClient = new MongoClient("localhost", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
			System.out.println("Connect to database successfully");

			// showMongoIterableValues(mongoClient.listDatabaseNames());

			// showMongoIterableValues(mongoDatabase.listCollectionNames());

			// mongoDatabase.createCollection("test");

			MongoCollection<Document> collection = mongoDatabase.getCollection("test");

//			Document document = new Document();
//			document.append("title", "jsjky");
//			document.append("name", "gug");
//			document.append("count", 181);
//			document.append("soo", 1181);
//
//			collection.insertOne(document);

			FindIterable<Document> iterable = collection.find();

			MongoCursor<Document> it = iterable.iterator();

			while (it.hasNext()) {
				Document doc = it.next();
				
				System.out.println(doc);
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			mongoClient.close();
		}

	}

	public static void showMongoIterableValues(MongoIterable<String> mongoIterable) {
		MongoCursor<String> cursor = mongoIterable.iterator();
		StringBuilder sbd = new StringBuilder();
		while (cursor.hasNext()) {
			sbd.append(cursor.next());
			sbd.append("\r\n");
		}

		System.out.println(sbd.toString());
	}

}
