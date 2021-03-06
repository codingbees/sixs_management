package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseS6sKpiTarget<M extends BaseS6sKpiTarget<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 年度kpi指标
	 */
	public M setTarget(java.lang.Float target) {
		set("target", target);
		return (M)this;
	}
	
	/**
	 * 年度kpi指标
	 */
	public java.lang.Float getTarget() {
		return getFloat("target");
	}

	/**
	 * 年份
	 */
	public M setYear(java.lang.Integer year) {
		set("year", year);
		return (M)this;
	}
	
	/**
	 * 年份
	 */
	public java.lang.Integer getYear() {
		return getInt("year");
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

}
