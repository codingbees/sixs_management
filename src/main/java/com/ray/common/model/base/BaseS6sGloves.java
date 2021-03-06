package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseS6sGloves<M extends BaseS6sGloves<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 发布日期
	 */
	public M setReleaseDate(java.util.Date releaseDate) {
		set("release_date", releaseDate);
		return (M)this;
	}
	
	/**
	 * 发布日期
	 */
	public java.util.Date getReleaseDate() {
		return get("release_date");
	}

	/**
	 * 检查状态：0=差，1=中，2=优
	 */
	public M setCheckStatus(java.lang.Integer checkStatus) {
		set("check_status", checkStatus);
		return (M)this;
	}
	
	/**
	 * 检查状态：0=差，1=中，2=优
	 */
	public java.lang.Integer getCheckStatus() {
		return getInt("check_status");
	}

	/**
	 * 手套图片
	 */
	public M setPicture(java.lang.String picture) {
		set("picture", picture);
		return (M)this;
	}
	
	/**
	 * 手套图片
	 */
	public java.lang.String getPicture() {
		return getStr("picture");
	}

	/**
	 * 区域名称
	 */
	public M setDisName(java.lang.String disName) {
		set("dis_name", disName);
		return (M)this;
	}
	
	/**
	 * 区域名称
	 */
	public java.lang.String getDisName() {
		return getStr("dis_name");
	}

	/**
	 * 区域id
	 */
	public M setDistrictId(java.lang.Integer districtId) {
		set("district_id", districtId);
		return (M)this;
	}
	
	/**
	 * 区域id
	 */
	public java.lang.Integer getDistrictId() {
		return getInt("district_id");
	}

}
