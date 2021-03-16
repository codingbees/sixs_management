package com.ray.common.model;

import com.ray.common.model.base.BaseDataField;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class DataField extends BaseDataField<DataField> {
	//建议将dao放在Service中只用作查询 
	public static final DataField dao = new DataField().dao();
	//在Service中声明 可直接复制过去使用
	//private DataField dao = new DataField().dao();  
}
