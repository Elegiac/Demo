CREATE TABLE student(
	id SERIAL PRIMARY KEY,
	name VARCHAR(125),
	c_id int4
);

CREATE TABLE teacher(
	id SERIAL PRIMARY KEY,
	name VARCHAR(125)
);

CREATE TABLE class(
	id SERIAL PRIMARY KEY,
	name VARCHAR(125),
	t_id int4
);

INSERT INTO teacher VALUES (1,'bill');
INSERT INTO teacher VALUES (2,'liza');

INSERT INTO class VALUES (1,'class1','1');
INSERT INTO class VALUES (2,'class2','2');

INSERT INTO student (name,c_id) VALUES ('bob',1);
INSERT INTO student (name,c_id) VALUES ('hoge',1);
INSERT INTO student (name,c_id) VALUES ('mill',1);
INSERT INTO student (name,c_id) VALUES ('tirr',2);
INSERT INTO student (name,c_id) VALUES ('bill',2);


