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

Date: 2016-12-09 18:23:39
*/


-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS "class";
CREATE TABLE "class" (
"id" int4 SERIAL primary key,
"name" varchar(125),
"t_id" int4
);

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO "class" VALUES ('1', 'class1', '1');
INSERT INTO "class" VALUES ('2', 'class2', '2');
