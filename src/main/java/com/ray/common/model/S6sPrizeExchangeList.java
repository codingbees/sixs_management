package com.ray.common.model;

import com.ray.common.model.base.BaseS6sPrizeExchangeList;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class S6sPrizeExchangeList extends BaseS6sPrizeExchangeList<S6sPrizeExchangeList> {
	//建议将dao放在Service中只用作查询 
	public static final S6sPrizeExchangeList dao = new S6sPrizeExchangeList().dao();
	//在Service中声明 可直接复制过去使用
	//private S6sPrizeExchangeList dao = new S6sPrizeExchangeList().dao();  
}
