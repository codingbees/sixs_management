package com.ray.common.model;

import com.ray.common.model.base.BaseDataObject;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class DataObject extends BaseDataObject<DataObject> {
	//建议将dao放在Service中只用作查询 
	public static final DataObject dao = new DataObject().dao();
	//在Service中声明 可直接复制过去使用
	//private DataObject dao = new DataObject().dao();  
}