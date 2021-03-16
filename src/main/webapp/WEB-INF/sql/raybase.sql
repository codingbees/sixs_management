/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.13 : Database - raybase
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`raybase` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `raybase`;

/*Table structure for table `data_button` */

DROP TABLE IF EXISTS `data_button`;

CREATE TABLE `data_button` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `data_object_id` int(11) NOT NULL COMMENT '元数据ID',
  `location` int(1) NOT NULL COMMENT '按钮位置:1=头部,2=行内',
  `type` int(1) NOT NULL COMMENT '按钮类型:1=confirm,2=combobox',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '按钮排序',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮图标',
  `name` varchar(255) NOT NULL COMMENT '按钮名称',
  `color` varchar(255) DEFAULT NULL COMMENT '按钮样式',
  `tip` text NOT NULL COMMENT '按钮提示信息',
  `dialog_title` varchar(255) DEFAULT NULL COMMENT 'dialog标题',
  `dialog_width` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '600px' COMMENT 'dialog宽度',
  `dialog_height` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '50%' COMMENT 'dialog高度',
  `dialog_src` varchar(255) DEFAULT NULL COMMENT 'dialog地址',
  `action` varchar(255) NOT NULL COMMENT '按钮访问后端action',
  `auth_role` varchar(255) DEFAULT NULL COMMENT '角色标识权限集合',
  `auth_row` varchar(255) DEFAULT NULL COMMENT '行内权限',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='元数据按钮';

/*Data for the table `data_button` */

insert  into `data_button`(`id`,`data_object_id`,`location`,`type`,`order_num`,`icon`,`name`,`color`,`tip`,`dialog_title`,`dialog_width`,`dialog_height`,`dialog_src`,`action`,`auth_role`,`auth_row`,`is_show`) values (1,18,2,1,0,'el-icon-edit','提交报告','success','确认是否要提交此报告？',NULL,NULL,NULL,NULL,'/test/ttt','admin','[{\"field\":\"statu\",\"operator\":\"==\",\"value\":\"0\"},{\"field\":\"test_statu\",\"operator\":\"==\",\"value\":\"1\"}]',1),(7,18,1,1,1,NULL,'嘻哈','info','请输入嘻嘻哈哈',NULL,NULL,NULL,NULL,'/test/ttt','admin','[{\"field\":\"statu\",\"operator\":\"==\",\"value\":\"1\"}]',1),(9,20,1,1,0,NULL,'test','primary','test',NULL,NULL,NULL,NULL,'/test/ttt',NULL,NULL,1),(10,20,2,1,1,NULL,'tt','success','tt',NULL,NULL,NULL,NULL,'/test/ttt',NULL,NULL,1),(11,20,2,3,2,NULL,'34234','info','4545','tttt',NULL,NULL,'/page/sys/template/buttonDialog.html','/',NULL,NULL,1),(12,18,2,2,1,NULL,'你好','warning','你好，请输入你的名字',NULL,NULL,NULL,NULL,'/test/ttt',NULL,NULL,1),(13,18,2,3,2,NULL,'弹出','danger','1111','你好','1000px',NULL,'/page/sys/template/buttonDialog.html','1111',NULL,NULL,1),(14,20,1,1,1,NULL,'sdfsdf','success','sdfsdf',NULL,NULL,NULL,NULL,'/test/ttt',NULL,NULL,1),(15,2,2,1,0,NULL,'启用','success','确认要启用该定时任务吗？',NULL,NULL,NULL,NULL,'/quartz/start',NULL,'[{\"field\":\"state\",\"operator\":\"==\",\"value\":\"0\"}]',1),(16,2,2,1,1,NULL,'关闭','danger','确认要关闭该定时任务吗？',NULL,NULL,NULL,NULL,'/quartz/stop',NULL,'[{\"field\":\"state\",\"operator\":\"==\",\"value\":\"1\"}]',1),(17,2,2,1,2,NULL,'执行一次','primary','确认是否要立即执行一次？',NULL,NULL,NULL,NULL,'/quartz/trigger',NULL,NULL,1),(18,18,1,3,2,NULL,'弹出','primary','/','弹出','600px','50%','/page/sys/template/buttonDialog.html','/',NULL,NULL,1);

/*Table structure for table `data_field` */

DROP TABLE IF EXISTS `data_field`;

CREATE TABLE `data_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `data_object_id` int(11) NOT NULL COMMENT 'object_id',
  `order_num` int(4) NOT NULL COMMENT '排序索引',
  `en` varchar(50) NOT NULL COMMENT '英文名',
  `cn` varchar(50) NOT NULL COMMENT '中文名',
  `is_auto` tinyint(1) DEFAULT '0' COMMENT '主键是否自增长',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'input' COMMENT '控件类型',
  `type_config` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '控件配置',
  `align` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'center' COMMENT '排列',
  `is_query` tinyint(1) DEFAULT '0' COMMENT '是否可查询',
  `is_show` tinyint(1) DEFAULT '1' COMMENT '是否可显示',
  `is_disable` tinyint(1) DEFAULT '0' COMMENT '是否禁用',
  `is_order` tinyint(1) DEFAULT '1' COMMENT '是否可排序',
  `is_add` tinyint(1) DEFAULT '1' COMMENT '是否可新增',
  `is_update` tinyint(1) DEFAULT '1' COMMENT '是否可修改',
  `is_line_update` tinyint(1) DEFAULT '1' COMMENT '是否可行内修改',
  `update_validate` varchar(255) DEFAULT NULL COMMENT '是否可修改数据校验',
  `is_edit` tinyint(1) DEFAULT '1' COMMENT '是否可编辑',
  `is_required` tinyint(1) DEFAULT '1' COMMENT '是否必填',
  `is_multiple` tinyint(1) DEFAULT '0' COMMENT '是否多选项',
  `is_fictitious` tinyint(1) DEFAULT '0' COMMENT '是否虚拟字段',
  `fictitious_sql` varchar(255) DEFAULT NULL COMMENT '虚拟字段来源sql',
  `placeholder` varchar(255) DEFAULT NULL COMMENT '输入提示',
  `formatter` text COMMENT '格式化',
  `validator` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'UI校验表达式',
  `defaulter` varchar(255) DEFAULT NULL COMMENT '默认值表达式',
  `width` int(4) DEFAULT NULL COMMENT '控件宽度',
  `height` int(4) DEFAULT '20' COMMENT '控件高度',
  `config` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配置',
  `add_status` int(3) DEFAULT '0' COMMENT '状态：0=正常，10=只读，20=隐藏，50=禁用',
  `update_status` int(3) DEFAULT '0' COMMENT '状态：0=正常，10=只读，20=隐藏，50=禁用',
  `data_type` int(5) DEFAULT '12' COMMENT '数据类型',
  `data_type_name` varchar(20) DEFAULT 'VARCHAR' COMMENT '数据类型名称',
  `data_size` int(2) DEFAULT '1' COMMENT '整数位长度',
  `data_decimal` int(2) DEFAULT '0' COMMENT '小数位长度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4214 DEFAULT CHARSET=utf8 COMMENT='元数据字段';

/*Data for the table `data_field` */

insert  into `data_field`(`id`,`data_object_id`,`order_num`,`en`,`cn`,`is_auto`,`type`,`type_config`,`align`,`is_query`,`is_show`,`is_disable`,`is_order`,`is_add`,`is_update`,`is_line_update`,`update_validate`,`is_edit`,`is_required`,`is_multiple`,`is_fictitious`,`fictitious_sql`,`placeholder`,`formatter`,`validator`,`defaulter`,`width`,`height`,`config`,`add_status`,`update_status`,`data_type`,`data_type_name`,`data_size`,`data_decimal`) values (4076,1,1,'id','ID',1,'input',NULL,'left',0,0,0,1,0,0,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4077,1,2,'value','字典值',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,'[{ required: true, message: \'请输入字典值\', trigger: \'blur\' }]',NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4078,1,3,'name','字典中文',0,'input',NULL,'left',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,'[{ required: true, message: \'请输入字典中文\', trigger: \'blur\' }]',NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4079,1,4,'object','表名',0,'input',NULL,'left',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,'[{ required: true, message: \'请输入表名\', trigger: \'blur\' }]',NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4080,1,5,'field','字段名',0,'input',NULL,'left',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,'[{ required: true, message: \'请输入字段名\', trigger: \'blur\' }]',NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4081,1,6,'ext','扩展',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4148,18,1,'id','ID',0,'input',NULL,'left',0,0,0,1,0,0,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4149,18,2,'workshop','车间',0,'select','from dicts where object = \'workshop\' and field = \'workshop\'|name|value','center',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4150,18,3,'no','产线编号',0,'input',NULL,'left',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,'[{ required: true, message: \'请输入产线编号\', trigger: \'blur\' }]',NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4151,18,4,'name','产线名称',0,'input',NULL,'left',1,1,0,1,1,1,1,'[{\"field\":\"statu\",\"operator\":\"==\",\"value\":\"1\"}]',1,1,0,0,NULL,NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4152,18,5,'file_url','文件',0,'file','test|2|.jpg','left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,80,20,NULL,0,0,12,'VARCHAR',1,0),(4153,18,6,'create_user_id','创建者ID',0,'input',NULL,'center',0,0,0,1,0,0,1,NULL,0,1,0,0,NULL,NULL,NULL,NULL,'function(){return 1}',130,20,NULL,0,0,12,'VARCHAR',1,0),(4154,18,7,'create_user_name','创建者姓名',0,'input',NULL,'center',1,1,0,1,0,0,1,NULL,0,1,0,0,NULL,NULL,'\'<i class=\"el-icon-user-solid\"></i>\'+cellValue',NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4155,18,9,'create_date','时间',0,'date',NULL,'left',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4156,18,10,'create_time','创建时间',0,'datetime',NULL,'left',0,1,0,1,0,1,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4168,18,5,'is_show','是否显示',0,'switch','from table_name where 1=1|label|value','left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,90,20,NULL,0,0,12,'VARCHAR',1,0),(4169,18,8,'statu','状态',0,'select','from dicts where object=\'tt\' and field = \'statu\'|name|value','center',0,1,0,1,0,1,1,NULL,1,1,0,0,NULL,NULL,' if(cellValue==0){\n\'<span style=\"background-image: linear-gradient(to top, #30cfd0 0%, #330867 100%);color:white;padding:10px\">新增</span>\'\n}else if(cellValue==1){\n\'<span style=\"background-image: linear-gradient(to top, #5ee7df 0%, #b490ca 100%);color:white;padding:10px\">进行中</span>\'\n}',NULL,NULL,100,20,NULL,0,0,12,'VARCHAR',1,0),(4170,18,12,'update_time','修改时间',0,'datetime',NULL,'left',0,1,0,1,1,1,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4172,18,8,'test_statu','测试字典',0,'select','from dicts where object=\'tt\' and field = \'test_statu\'|name|value','left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4173,18,1,'operation','工序',0,'input',NULL,'center',0,1,0,1,0,0,0,NULL,1,1,0,1,'select 1 from dual',NULL,NULL,NULL,NULL,130,20,NULL,0,0,12,'VARCHAR',1,0),(4177,20,1,'id','ID',0,'input',NULL,'left',0,0,0,1,0,0,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4178,20,2,'yw_test_id','产线ID',0,'select','from dicts where object=\'yw_test_son\' and field = \'yw_test_id\'|name|value','left',0,0,0,1,0,0,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4179,20,3,'no','工序号',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,'[{ required: true, message: \'请输入产线编号\', trigger: \'blur\' }]',NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4180,20,4,'name','工序名称',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4181,21,1,'id','ID',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4182,21,2,'workshop','车间',0,'select','from dicts where object=\'yw_test\' and field = \'workshop\'|name|value','left',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4183,21,3,'no','产线编号',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4184,21,4,'name','产线名称',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4185,21,5,'is_show','是否显示',0,'switch',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4186,21,6,'file_url','文件',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4187,21,7,'statu','状态',0,'select','from dicts where object=\'yw_test\' and field = \'statu\'|name|value','left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4188,21,8,'test_statu','测试字典',0,'select','from dicts where object=\'yw_test\' and field = \'test_statu\'|name|value','left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4189,21,9,'create_user_id','创建者ID',0,'input',NULL,'left',0,1,0,1,1,0,1,NULL,1,1,0,0,NULL,NULL,'\'<el-button>默认按钮</el-button>\'',NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4190,21,10,'create_user_name','创建者姓名',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4191,21,11,'create_date','',0,'date',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4192,21,12,'create_time','创建时间',0,'datetime',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4193,21,13,'update_time','',0,'datetime',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4200,2,1,'id','ID',0,'input',NULL,'left',0,0,0,1,0,0,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4201,2,2,'state','状态',0,'select','from dicts where object=\'data_task\' and field = \'state\'|name|value','center',1,1,0,1,0,0,0,NULL,1,1,0,0,NULL,NULL,' if(cellValue==0){\'<span style=\"background-color:red;color:white;padding:10px\">停止</span>\'}else{\'<span style=\"background-color:green;color:white;padding:10px\">运行中</span>\'}',NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4202,2,3,'name','名称',0,'input',NULL,'left',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4203,2,4,'exp','表达式',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4204,2,5,'clazz','实现类',0,'input',NULL,'left',0,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4205,2,6,'info','说明',0,'input',NULL,'left',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4206,18,1,'jtgj','交通工具',0,'input',NULL,'center',0,1,0,1,0,0,0,NULL,1,1,0,1,NULL,NULL,'if(row.statu==0){\'<img src=\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596454726984&di=5d9f8961be56e855b726f2f7e2b78798&imgtype=0&src=http%3A%2F%2Fimg.bimg.126.net%2Fphoto%2FZ4x4yd8Tnnp917cm-mDCfA%3D%3D%2F2827697616036751350.jpg\" height=\"30\" alt=\"上海鲜花港\" />\'}else{\'<img src=\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596534492495&di=53c81241108da85021a9d238c4e3be73&imgtype=0&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1685755823%2C3020780630%26fm%3D214%26gp%3D0.jpg\" height=\"30\" alt=\"上海鲜花港\" />\'}',NULL,NULL,150,20,'select 1 from dual',0,0,12,'VARCHAR',1,0),(4207,3,1,'id','ID',0,'input',NULL,'left',0,0,0,1,0,0,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4208,3,2,'username','登录账号',0,'input',NULL,'center',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4209,3,3,'password','登录密码',0,'input',NULL,'center',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4210,3,4,'nickname','用户姓名',0,'input',NULL,'center',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4211,3,5,'ding_user_id','钉钉USERID',0,'input',NULL,'center',1,1,0,1,1,1,1,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4212,3,6,'update_time','修改时间',0,'datetime',NULL,'center',0,1,0,1,0,0,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0),(4213,3,7,'create_time','创建时间',0,'datetime',NULL,'center',0,1,0,1,0,0,0,NULL,1,1,0,0,NULL,NULL,NULL,NULL,NULL,NULL,20,NULL,0,0,12,'VARCHAR',1,0);

/*Table structure for table `data_object` */

DROP TABLE IF EXISTS `data_object`;

CREATE TABLE `data_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) NOT NULL COMMENT '表名',
  `en` varchar(255) NOT NULL COMMENT '英文编码',
  `cn` varchar(255) NOT NULL COMMENT '中文名',
  `where_attr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '查询表达式',
  `interceptor` varchar(255) DEFAULT NULL COMMENT '拦截器',
  `is_add` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否新增',
  `is_update` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否修改',
  `is_line_update` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可行内修改',
  `is_delete` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否删除',
  `is_handle` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否拥有操作列',
  `handle_width` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '100' COMMENT '操作列宽度',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='元数据对象';

/*Data for the table `data_object` */

insert  into `data_object`(`id`,`table_name`,`en`,`cn`,`where_attr`,`interceptor`,`is_add`,`is_update`,`is_line_update`,`is_delete`,`is_handle`,`handle_width`,`create_time`) values (1,'dicts','dicts','字典表',NULL,NULL,1,1,1,1,1,'100','2020-06-29 10:15:29'),(2,'data_task','quartz','定时任务',NULL,'com.ray.common.quartz.TaskIntercept',1,1,1,1,1,'260','2020-08-03 09:44:46'),(3,'user','user','用户管理',NULL,NULL,1,1,1,1,1,'100','2020-08-11 14:34:11'),(18,'yw_test','yw_test','业务_测试',NULL,'com.ray.interceptor.Test',1,1,1,1,1,'350','2020-07-06 17:52:27'),(20,'yw_test_son','yw_test_son','业务子数据_测试',NULL,NULL,1,1,1,1,1,'240','2020-07-24 15:08:56'),(21,'yw_test','test','测试',NULL,NULL,1,1,1,1,1,NULL,'2020-07-25 09:29:57');

/*Table structure for table `data_task` */

DROP TABLE IF EXISTS `data_task`;

CREATE TABLE `data_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态:0=停止,1=启动',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `exp` varchar(50) NOT NULL COMMENT '表达式',
  `clazz` varchar(255) NOT NULL COMMENT '实现类',
  `info` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='任务调度';

/*Data for the table `data_task` */

insert  into `data_task`(`id`,`state`,`name`,`exp`,`clazz`,`info`) values (2,0,'每时','0 0 * * * ? *','com.ray.job.EveryHourJob','每小时统计一次'),(12,0,'每天','59 59 23 * * ? *','com.ray.job.EveryDayJob','每天23点59分59秒跑一下'),(13,0,'每秒','* * * * * ? *','com.ray.job.EveryMinJob','每秒钟来一发');

/*Table structure for table `dicts` */

DROP TABLE IF EXISTS `dicts`;

CREATE TABLE `dicts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` int(11) NOT NULL COMMENT '字典值',
  `name` varchar(50) NOT NULL COMMENT '字典中文',
  `object` varchar(50) NOT NULL COMMENT '表名',
  `field` varchar(50) NOT NULL COMMENT '字段名',
  `ext` varchar(255) DEFAULT '' COMMENT '扩展Json',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=617 DEFAULT CHARSET=utf8 COMMENT='字典表';

/*Data for the table `dicts` */

insert  into `dicts`(`id`,`value`,`name`,`object`,`field`,`ext`) values (1,1,'工艺图纸','form_ck','type',''),(593,1,'VVT','workshop','workshop',''),(594,2,'机械挺柱','workshop','workshop',''),(595,3,'减速器','workshop','workshop',''),(596,4,'液压挺柱','workshop','workshop',''),(597,5,'精密','workshop','workshop',''),(598,6,'张紧器','workshop','workshop',''),(599,7,'喷嘴','workshop','workshop',''),(600,8,'电子电器','workshop','workshop',''),(601,1,'首末检记录表','form_tx_element','type','{template:\'smjjlb.html\',table_name:\'form_tx_smjjlb\'}'),(602,2,'作业指导书','form_ck','type',''),(606,3,'工艺参数验证','form_ck','type',''),(608,2,'标校防错记录表','form_tx_element','type','{template:\'bjfcjlb.html\',table_name:\'form_tx_bjfcjlb\'}'),(609,0,'新增','tt','statu',''),(610,1,'进行中','tt','statu',''),(611,1,'哈哈','tt','test_statu',''),(612,2,'嘿嘿','tt','test_statu',''),(613,1,'哈哈','yw_test','test_statu',''),(614,2,'嘿嘿','yw_test','test_statu',''),(615,0,'停止','data_task','state',''),(616,1,'运行中','data_task','state','');

/*Table structure for table `file` */

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `url` text NOT NULL COMMENT '文件路径',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='文件表';

/*Data for the table `file` */

insert  into `file`(`id`,`name`,`url`,`create_time`) values (106,'焦头烂额1.png','/test/968872a5-d279-4bb4-bc09-ae3a494e6afb.png','2020-07-25 09:47:55');

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `menu_level` int(11) NOT NULL COMMENT '菜单层级',
  `parent_menu` int(11) NOT NULL COMMENT '父级菜单',
  `title_en` varchar(255) NOT NULL COMMENT '菜单英文',
  `title` varchar(255) NOT NULL COMMENT '菜单名称',
  `seq_num` int(11) NOT NULL COMMENT '菜单序号',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `type` int(1) NOT NULL COMMENT '菜单类型:1=单表,2=主子表,3=自定义',
  `data_object_id` int(11) DEFAULT '0' COMMENT '数据模型',
  `son_data_object_id` int(11) DEFAULT '0' COMMENT '子表数据模型',
  `parent_id_field` varchar(255) DEFAULT NULL COMMENT '主表ID',
  `son_id_field` varchar(255) DEFAULT NULL COMMENT '子表关联ID',
  `href` varchar(255) DEFAULT NULL COMMENT '菜单链接',
  `is_hide` int(1) NOT NULL COMMENT '是否隐藏：1隐藏，0显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8 COMMENT='菜单';

/*Data for the table `menu` */

insert  into `menu`(`id`,`menu_level`,`parent_menu`,`title_en`,`title`,`seq_num`,`icon`,`type`,`data_object_id`,`son_data_object_id`,`parent_id_field`,`son_id_field`,`href`,`is_hide`) values (1,1,0,'system','系统设置',999,'pfep-shezhi',3,0,0,NULL,NULL,NULL,0),(2,2,1,'role','角色管理',1,NULL,3,0,0,NULL,NULL,'/sys/role',0),(3,2,1,'menu','菜单管理',2,NULL,3,0,0,NULL,NULL,'/sys/menu',0),(145,2,1,'/data','元数据管理',998,'pfep-shujumoxing',3,9,9,NULL,NULL,'/data',0),(148,1,0,'basedata','基础演示',997,'pfep-jichushujuweihu',3,4,4,NULL,NULL,NULL,0),(151,2,1,'zd','字典管理',5,NULL,1,1,1,NULL,NULL,NULL,0),(152,2,148,'test','单表模板',3,NULL,1,18,4,NULL,NULL,NULL,0),(153,2,148,'zzb','主子表模板',4,NULL,2,18,20,'id','yw_test_id',NULL,0),(156,2,1,'quartz','定时任务',6,NULL,1,2,1,NULL,NULL,NULL,0),(157,2,1,'user','用户管理',0,NULL,1,3,1,NULL,NULL,NULL,0);

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `permission_name` varchar(255) DEFAULT NULL COMMENT '权限表达式',
  `type` int(1) NOT NULL COMMENT '权限类型：1菜单，2按钮，3数据',
  `gl_id` int(11) DEFAULT NULL COMMENT '关联菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2325 DEFAULT CHARSET=utf8 COMMENT='权限';

/*Data for the table `permissions` */

insert  into `permissions`(`id`,`name`,`permission_name`,`type`,`gl_id`) values (1994,NULL,NULL,1,112),(1995,NULL,NULL,1,133),(1996,NULL,NULL,1,113),(1997,NULL,NULL,1,114),(1998,NULL,NULL,1,121),(1999,NULL,NULL,1,132),(2094,NULL,NULL,1,109),(2095,NULL,NULL,1,125),(2096,NULL,NULL,1,111),(2097,NULL,NULL,1,124),(2098,NULL,NULL,1,137),(2099,NULL,NULL,1,115),(2100,NULL,NULL,1,123),(2101,NULL,NULL,1,112),(2102,NULL,NULL,1,114),(2103,NULL,NULL,1,121),(2104,NULL,NULL,1,122),(2105,NULL,NULL,1,126),(2106,NULL,NULL,1,130),(2107,NULL,NULL,1,127),(2108,NULL,NULL,1,121),(2109,NULL,NULL,1,122),(2167,NULL,NULL,1,138),(2168,NULL,NULL,1,139),(2169,NULL,NULL,1,140),(2170,NULL,NULL,1,141),(2171,NULL,NULL,1,142),(2172,NULL,NULL,1,143),(2173,NULL,NULL,1,138),(2174,NULL,NULL,1,139),(2175,NULL,NULL,1,140),(2176,NULL,NULL,1,141),(2177,NULL,NULL,1,142),(2178,NULL,NULL,1,143),(2259,NULL,NULL,1,148),(2260,NULL,NULL,1,149),(2261,NULL,NULL,1,151),(2314,NULL,NULL,1,1),(2315,NULL,NULL,1,4),(2316,NULL,NULL,1,157),(2317,NULL,NULL,1,2),(2318,NULL,NULL,1,3),(2319,NULL,NULL,1,151),(2320,NULL,NULL,1,156),(2321,NULL,NULL,1,145),(2322,NULL,NULL,1,148),(2323,NULL,NULL,1,152),(2324,NULL,NULL,1,153);

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2338 DEFAULT CHARSET=utf8 COMMENT='角色_权限';

/*Data for the table `role_permission` */

insert  into `role_permission`(`id`,`role_id`,`permission_id`) values (2186,2,2173),(2187,2,2174),(2188,2,2175),(2189,2,2176),(2190,2,2177),(2191,2,2178),(2327,1,2314),(2328,1,2315),(2329,1,2316),(2330,1,2317),(2331,1,2318),(2332,1,2319),(2333,1,2320),(2334,1,2321),(2335,1,2322),(2336,1,2323),(2337,1,2324);

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` varchar(255) NOT NULL COMMENT '角色',
  `role_nick_name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '具体描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `roles` */

insert  into `roles`(`id`,`role_name`,`role_nick_name`,`role_desc`) values (1,'admin','管理员','所有权限'),(2,'basic','普通用户','默认权限');

/*Table structure for table `serial_number` */

DROP TABLE IF EXISTS `serial_number`;

CREATE TABLE `serial_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `part_invoice_no` int(11) NOT NULL DEFAULT '1' COMMENT '收货单号',
  `part_check_no` int(11) NOT NULL DEFAULT '1' COMMENT '检验单号',
  `ingredient_plan_no` int(11) NOT NULL DEFAULT '1' COMMENT '配料单号',
  `cust_order_no` int(11) NOT NULL DEFAULT '1' COMMENT '客户订单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='自增单号';

/*Data for the table `serial_number` */

insert  into `serial_number`(`id`,`part_invoice_no`,`part_check_no`,`ingredient_plan_no`,`cust_order_no`) values (1,1,1,1,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) NOT NULL COMMENT '登录账号',
  `password` varchar(255) NOT NULL COMMENT '登录密码',
  `nickname` varchar(255) NOT NULL COMMENT '用户姓名',
  `ding_user_id` varchar(255) NOT NULL COMMENT '钉钉USERID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户';

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`nickname`,`ding_user_id`,`update_time`,`create_time`) values (1,'admin','admin','超级管理员','09112815001228979','2020-08-11 14:34:54','2020-08-11 14:34:54');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4657 DEFAULT CHARSET=utf8 COMMENT='用户_角色';

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`) values (1,1,1);

/*Table structure for table `yw_test` */

DROP TABLE IF EXISTS `yw_test`;

CREATE TABLE `yw_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `workshop` int(11) NOT NULL COMMENT '车间',
  `no` varchar(255) NOT NULL COMMENT '产线编号',
  `name` varchar(255) NOT NULL COMMENT '产线名称',
  `is_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `file_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件',
  `statu` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `test_statu` int(11) DEFAULT '0' COMMENT '测试字典:1=哈哈,2=嘿嘿',
  `create_user_id` varchar(255) NOT NULL COMMENT '创建者ID',
  `create_user_name` varchar(255) NOT NULL COMMENT '创建者姓名',
  `create_date` date DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='业务演示_父表';

/*Data for the table `yw_test` */

insert  into `yw_test`(`id`,`workshop`,`no`,`name`,`is_show`,`file_url`,`statu`,`test_statu`,`create_user_id`,`create_user_name`,`create_date`,`create_time`,`update_time`) values (1,4,'1','第一条产线',0,'91,98',0,1,'1','超级管理员','2020-07-01','2020-06-24 16:32:15','2020-09-02 11:41:01'),(2,7,'NO2斯蒂芬斯蒂芬斯蒂芬啥地方sdfsdfsdfsdfsdfsdfsdfsdf电话','第二条产线哟',0,'',0,1,'1','超级管理员','2020-07-22','2020-06-28 10:38:15','2020-09-02 11:41:09'),(40,4,'122312121','收拾收拾',1,'104,105',1,1,'1','超级管理员','2020-07-25','2020-07-15 14:22:29','2020-09-02 11:42:35'),(41,1,'12','1322122222222',1,'106',1,1,'1','超级管理员','2020-07-15','2020-07-15 14:48:44','2020-09-02 11:40:23'),(42,2,'122','斯蒂芬斯蒂芬斯蒂芬12121时代发生的发的收到发生大火sdf收到收到收到发生的 ',1,NULL,1,1,'1','超级管理员','2020-07-24','2020-07-15 15:10:59','2020-09-01 10:16:36'),(43,3,'sdfsdf芬斯蒂芬斯蒂芬sdfsdf','1sdfsdf',1,NULL,0,2,'1','超级管理员','2020-07-25','2020-07-15 15:16:09','2020-08-04 16:11:09'),(56,3,'2123123','222222',1,NULL,0,1,'1','超级管理员','2020-07-08','2020-07-24 15:29:24','2020-08-04 11:45:51');

/*Table structure for table `yw_test_son` */

DROP TABLE IF EXISTS `yw_test_son`;

CREATE TABLE `yw_test_son` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `yw_test_id` int(11) NOT NULL COMMENT '产线ID',
  `no` varchar(255) DEFAULT NULL COMMENT '工序号',
  `name` varchar(255) DEFAULT NULL COMMENT '工序名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='业务演示_子表';

/*Data for the table `yw_test_son` */

insert  into `yw_test_son`(`id`,`yw_test_id`,`no`,`name`) values (1,40,'01','01111'),(2,40,'02斯蒂芬斯蒂芬斯蒂芬','02和文化潍坊潍坊潍坊2222'),(3,40,'22','22');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
