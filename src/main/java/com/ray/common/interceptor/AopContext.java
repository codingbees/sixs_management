/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.ray.common.interceptor;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.model.DataObject;


/**
 * AOP 上下斿
 *
 * @author ray
 * @date 2017-7-15
 */
public class AopContext {

    /**
     * 当前控制噿
     */
    public Controller ctrl;

    /**
     * 当前用户对象
     */
    public Record user;
    
    /**
     * 当前元对豿(object.fields=元字段集吿)
     */
    public DataObject object;

    /**
     * 当前操作数据雿(批量操作)
     */
    public List<Record> records;

    /**
     * 当前操作对象(单条数据操作)
     */
    public Record record;

    /**
     * 当前操作对象固定倿
     * 用鿔：新增/编辑时预设固定初始忿
     * 推荐：固定初始忼，建议禁用字段使用addBefore()拦截添加倿
     */
    public Record fixed;

    /**
     * 追加SQL条件
     */
	public String condition = "";
    /**
     * 自定义SQL覆盖默认查询条件
     * 格式: where xxx = xxx
     */
    public String where;
    /**
     * 自定义SQL参数
     */
    public List<Object> params = new ArrayList<Object>();
    /**
     * 自定义SQL覆盖默认排序
     * 格式: order by xxx desc
     */
    public String sort;
	/**
	 * 完全自定义整个SQL语句(可以支持任意语法,多层嵌套,多表连接查询筿)
	 */
	public String sql;

    public AopContext(Controller ctrl){
        this.ctrl = ctrl;
        this.user = (Record)ctrl.getSessionAttr("user");
    }

    public AopContext(Controller ctrl, List<Record> records){
        this(ctrl);
        this.records = records;
    }

    public AopContext(Controller ctrl, Record record){
        this(ctrl);
        this.record = record;
    }

	public String UID() {
		return this.user.get("id");
	}

}