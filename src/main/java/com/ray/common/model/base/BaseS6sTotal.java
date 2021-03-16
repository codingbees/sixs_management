package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseS6sTotal<M extends BaseS6sTotal<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 6S序号
	 */
	public M setS6No(java.lang.String s6No) {
		set("s6_no", s6No);
		return (M)this;
	}
	
	/**
	 * 6S序号
	 */
	public java.lang.String getS6No() {
		return getStr("s6_no");
	}

	/**
	 * 问题描述
	 */
	public M setProblem(java.lang.String problem) {
		set("problem", problem);
		return (M)this;
	}
	
	/**
	 * 问题描述
	 */
	public java.lang.String getProblem() {
		return getStr("problem");
	}

	/**
	 * 问题图片
	 */
	public M setPicture(java.lang.String picture) {
		set("picture", picture);
		return (M)this;
	}
	
	/**
	 * 问题图片
	 */
	public java.lang.String getPicture() {
		return getStr("picture");
	}

	/**
	 * 区域
	 */
	public M setDistrict(java.lang.String district) {
		set("district", district);
		return (M)this;
	}
	
	/**
	 * 区域
	 */
	public java.lang.String getDistrict() {
		return getStr("district");
	}

	/**
	 * 发起日期
	 */
	public M setDateTime(java.util.Date dateTime) {
		set("date_time", dateTime);
		return (M)this;
	}
	
	/**
	 * 发起日期
	 */
	public java.util.Date getDateTime() {
		return get("date_time");
	}

	/**
	 * 审核状态：0=未审核，1=审核通过，2=拒绝
	 */
	public M setCheckStatus(java.lang.Integer checkStatus) {
		set("check_status", checkStatus);
		return (M)this;
	}
	
	/**
	 * 审核状态：0=未审核，1=审核通过，2=拒绝
	 */
	public java.lang.Integer getCheckStatus() {
		return getInt("check_status");
	}

	/**
	 * 处理结果：0=未关闭，1=已关闭
	 */
	public M setHandleStatus(java.lang.Integer handleStatus) {
		set("handle_status", handleStatus);
		return (M)this;
	}
	
	/**
	 * 处理结果：0=未关闭，1=已关闭
	 */
	public java.lang.Integer getHandleStatus() {
		return getInt("handle_status");
	}

	/**
	 * 整改后图片
	 */
	public M setPicAfterCk(java.lang.String picAfterCk) {
		set("pic_after_ck", picAfterCk);
		return (M)this;
	}
	
	/**
	 * 整改后图片
	 */
	public java.lang.String getPicAfterCk() {
		return getStr("pic_after_ck");
	}

	/**
	 * 首次要求完成时间
	 */
	public M setFirstReqDate(java.util.Date firstReqDate) {
		set("first_req_date", firstReqDate);
		return (M)this;
	}
	
	/**
	 * 首次要求完成时间
	 */
	public java.util.Date getFirstReqDate() {
		return get("first_req_date");
	}

	/**
	 * 实际完成时间
	 */
	public M setActFinDate(java.util.Date actFinDate) {
		set("act_fin_date", actFinDate);
		return (M)this;
	}
	
	/**
	 * 实际完成时间
	 */
	public java.util.Date getActFinDate() {
		return get("act_fin_date");
	}

	/**
	 * 备注
	 */
	public M setNote(java.lang.String note) {
		set("note", note);
		return (M)this;
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getNote() {
		return getStr("note");
	}

	/**
	 * 检举人
	 */
	public M setPName(java.lang.String pName) {
		set("p_name", pName);
		return (M)this;
	}
	
	/**
	 * 检举人
	 */
	public java.lang.String getPName() {
		return getStr("p_name");
	}

	/**
	 * 检举人工号
	 */
	public M setPJobNumber(java.lang.String pJobNumber) {
		set("p_job_number", pJobNumber);
		return (M)this;
	}
	
	/**
	 * 检举人工号
	 */
	public java.lang.String getPJobNumber() {
		return getStr("p_job_number");
	}

	/**
	 * 检举分值
	 */
	public M setScore(java.lang.Integer score) {
		set("score", score);
		return (M)this;
	}
	
	/**
	 * 检举分值
	 */
	public java.lang.Integer getScore() {
		return getInt("score");
	}

}