package std.demo.local.springmybatis.dao;

import java.util.List;

import std.demo.local.springmybatis.entity.Classes;

public interface ClassMapper {

	List<Classes> findClasses();

	List<Classes> findClassesWithStudents();

	List<Classes> findClassesWithStudentsByIds(List<Integer> ids);
}
