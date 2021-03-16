package com.ray.common.model;

import com.ray.common.model.base.BaseSerialNumber;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class SerialNumber extends BaseSerialNumber<SerialNumber> {
	//建议将dao放在Service中只用作查询 
	public static final SerialNumber dao = new SerialNumber().dao();
	//在Service中声明 可直接复制过去使用
	//private SerialNumber dao = new SerialNumber().dao();  
}
