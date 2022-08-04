SET SESSION FOREIGN_KEY_CHECKS=0;


/* Create Tables */

-- 家庭成员
CREATE TABLE family_member
(
	id varchar(64) NOT NULL COMMENT 'ID',
	name varchar(64) COMMENT '姓名',
	sex char(1) COMMENT '成员性别',
	phone varchar(11) COMMENT '手机号',
	email varchar(64) COMMENT '邮箱',
	father varchar(64) NOT NULL COMMENT '父亲',
	mother varchar(64) NOT NULL COMMENT '母亲',
	marriage char(1) COMMENT '已婚',
	levers varchar(64) NOT NULL COMMENT '对象',
	birthday datetime COMMENT '出生日期',
	user_id varchar(64) COMMENT '用户',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(500) COMMENT '备注信息',
	PRIMARY KEY (id)
) COMMENT = '家庭成员';


-- 成员属性
CREATE TABLE family_member_properties
(
	id varchar(64) NOT NULL COMMENT 'ID',
	property_name varchar(64) COMMENT '属性名',
	property_value varchar(255) COMMENT '属性值',
	sort int COMMENT '排序值',
	member_id varchar(64) NOT NULL COMMENT '家庭成员',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(500) COMMENT '备注信息',
	PRIMARY KEY (id)
) COMMENT = '成员属性';

-- 2022-8-4 修改表结构，family_member 添加头像字段
alter table family_member add avatar varchar(1000) COMMENT '头像';

alter table family_member drop avatar; alter column avatar varchar(1000) COMMENT '头像';