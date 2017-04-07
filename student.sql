/*
Navicat PGSQL Data Transfer

Source Server         : 192.168.1.11
Source Server Version : 90204
Source Host           : 192.168.1.11:5432
Source Database       : test
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90204
File Encoding         : 65001

Date: 2016-12-09 18:23:49
*/


-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS "student";
CREATE TABLE "student" (
"id" int4 SERIAL primary key,
"name" varchar(125),
"c_id" int4
);

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO "student" VALUES ('1', 'bob', '1');
INSERT INTO "student" VALUES ('2', 'hoge', '1');
INSERT INTO "student" VALUES ('3', 'mill', '1');
INSERT INTO "student" VALUES ('4', 'tirr', '2');
INSERT INTO "student" VALUES ('5', 'bill', '2');
