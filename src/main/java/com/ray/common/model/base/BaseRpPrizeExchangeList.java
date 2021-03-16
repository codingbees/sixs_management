package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRpPrizeExchangeList<M extends BaseRpPrizeExchangeList<M>> extends Model<M> implements IBean {

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
	 * 申请人id
	 */
	public M setApplyUserid(java.lang.String applyUserid) {
		set("apply_userid", applyUserid);
		return (M)this;
	}
	
	/**
	 * 申请人id
	 */
	public java.lang.String getApplyUserid() {
		return getStr("apply_userid");
	}

	/**
	 * 申请人姓名
	 */
	public M setApplyUsername(java.lang.String applyUsername) {
		set("apply_username", applyUsername);
		return (M)this;
	}
	
	/**
	 * 申请人姓名
	 */
	public java.lang.String getApplyUsername() {
		return getStr("apply_username");
	}

	/**
	 * 申请日期
	 */
	public M setApplyDate(java.util.Date applyDate) {
		set("apply_date", applyDate);
		return (M)this;
	}
	
	/**
	 * 申请日期
	 */
	public java.util.Date getApplyDate() {
		return get("apply_date");
	}

	/**
	 * 所需积分
	 */
	public M setScore(java.lang.Integer score) {
		set("score", score);
		return (M)this;
	}
	
	/**
	 * 所需积分
	 */
	public java.lang.Integer getScore() {
		return getInt("score");
	}

	/**
	 * 奖品名称
	 */
	public M setPrizeName(java.lang.String prizeName) {
		set("prize_name", prizeName);
		return (M)this;
	}
	
	/**
	 * 奖品名称
	 */
	public java.lang.String getPrizeName() {
		return getStr("prize_name");
	}

	/**
	 * 奖品id
	 */
	public M setPrizeid(java.lang.Integer prizeid) {
		set("prizeid", prizeid);
		return (M)this;
	}
	
	/**
	 * 奖品id
	 */
	public java.lang.Integer getPrizeid() {
		return getInt("prizeid");
	}

}
