package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseS6sPrizeList<M extends BaseS6sPrizeList<M>> extends Model<M> implements IBean {

	/**
	 * 序号
	 */
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * 序号
	 */
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 奖品图片名称
	 */
	public M setPrizePicName(java.lang.String prizePicName) {
		set("prize_pic_name", prizePicName);
		return (M)this;
	}
	
	/**
	 * 奖品图片名称
	 */
	public java.lang.String getPrizePicName() {
		return getStr("prize_pic_name");
	}

	/**
	 * 奖品所需积分
	 */
	public M setCostScore(java.lang.Integer costScore) {
		set("cost_score", costScore);
		return (M)this;
	}
	
	/**
	 * 奖品所需积分
	 */
	public java.lang.Integer getCostScore() {
		return getInt("cost_score");
	}

	/**
	 * 奖品名称
	 */
	public M setPrizeNameCn(java.lang.String prizeNameCn) {
		set("prize_name_cn", prizeNameCn);
		return (M)this;
	}
	
	/**
	 * 奖品名称
	 */
	public java.lang.String getPrizeNameCn() {
		return getStr("prize_name_cn");
	}

}
