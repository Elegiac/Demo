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

Date: 2016-12-09 18:23:58
*/


-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS "teacher";
CREATE TABLE "teacher" (
"id" int4 SERIAL primary key,
"name" varchar(125)
);

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO "teacher" VALUES ('1', 'bill');
INSERT INTO "teacher" VALUES ('2', 'liza');
