package com.ray.common.model;

import com.ray.common.model.base.BaseRpPrizeExchangeList;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class RpPrizeExchangeList extends BaseRpPrizeExchangeList<RpPrizeExchangeList> {
	//建议将dao放在Service中只用作查询 
	public static final RpPrizeExchangeList dao = new RpPrizeExchangeList().dao();
	//在Service中声明 可直接复制过去使用
	//private RpPrizeExchangeList dao = new RpPrizeExchangeList().dao();  
}