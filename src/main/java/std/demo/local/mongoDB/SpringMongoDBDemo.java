package std.demo.local.mongoDB;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class SpringMongoDBDemo {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"spring-mongodb.xml");
		UserDaoImpl dao = (UserDaoImpl) ac.getBean("userDaoImpl");
		
//		User user = new User();
//		user.setId("4");
//		user.setName("田老八");
//		user.setPassword("888");
//		user.setAge(35);
//		dao.insert(user, "test");
		
		
//		Map<String,Object>params = new HashMap<>();
//		params.put("id", "1");
//		User u = dao.findOne(params, "test");
//		
//		System.out.println(u);
		
		MongoTemplate template = (MongoTemplate) ac.getBean("mongoTemplate");

		System.out.println(template.findAll(User.class,"test"));
	}

}
