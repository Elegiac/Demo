package std.demo.local.springmybatis;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.JobMessageFromOperator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import std.demo.local.springmybatis.dao.ClassMapper;

public class Main {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"spring-mybatis.xml");
		ClassMapper classDao = ac.getBean(ClassMapper.class);

		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		//System.out.println(classDao.findClasses());
		 //System.out.println(classDao.findClassesWithStudents());
		 System.out.println(classDao.findClassesWithStudentsByIds(ids));
		 
		 
	}
}
