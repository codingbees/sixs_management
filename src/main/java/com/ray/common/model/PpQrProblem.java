package com.ray.common.model;

import com.ray.common.model.base.BasePpQrProblem;

/**
 * 
 * Generated by JBolt.
 */
@SuppressWarnings("serial")
public class PpQrProblem extends BasePpQrProblem<PpQrProblem> {
	//建议将dao放在Service中只用作查询 
	public static final PpQrProblem dao = new PpQrProblem().dao();
	//在Service中声明 可直接复制过去使用
	//private PpQrProblem dao = new PpQrProblem().dao();  
}
