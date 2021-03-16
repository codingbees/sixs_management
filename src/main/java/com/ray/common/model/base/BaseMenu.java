package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMenu<M extends BaseMenu<M>> extends Model<M> implements IBean {

	/**
	 * ID
	 */
	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * ID
	 */
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 菜单层级
	 */
	public M setMenuLevel(java.lang.Integer menuLevel) {
		set("menu_level", menuLevel);
		return (M)this;
	}
	
	/**
	 * 菜单层级
	 */
	public java.lang.Integer getMenuLevel() {
		return getInt("menu_level");
	}

	/**
	 * 父级菜单
	 */
	public M setParentMenu(java.lang.Integer parentMenu) {
		set("parent_menu", parentMenu);
		return (M)this;
	}
	
	/**
	 * 父级菜单
	 */
	public java.lang.Integer getParentMenu() {
		return getInt("parent_menu");
	}

	/**
	 * 菜单英文
	 */
	public M setTitleEn(java.lang.String titleEn) {
		set("title_en", titleEn);
		return (M)this;
	}
	
	/**
	 * 菜单英文
	 */
	public java.lang.String getTitleEn() {
		return getStr("title_en");
	}

	/**
	 * 菜单名称
	 */
	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}
	
	/**
	 * 菜单名称
	 */
	public java.lang.String getTitle() {
		return getStr("title");
	}

	/**
	 * 菜单序号
	 */
	public M setSeqNum(java.lang.Integer seqNum) {
		set("seq_num", seqNum);
		return (M)this;
	}
	
	/**
	 * 菜单序号
	 */
	public java.lang.Integer getSeqNum() {
		return getInt("seq_num");
	}

	/**
	 * 菜单图标
	 */
	public M setIcon(java.lang.String icon) {
		set("icon", icon);
		return (M)this;
	}
	
	/**
	 * 菜单图标
	 */
	public java.lang.String getIcon() {
		return getStr("icon");
	}

	/**
	 * 菜单类型:1=单表,2=主子表,3=自定义
	 */
	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}
	
	/**
	 * 菜单类型:1=单表,2=主子表,3=自定义
	 */
	public java.lang.Integer getType() {
		return getInt("type");
	}

	/**
	 * 数据模型
	 */
	public M setDataObjectId(java.lang.Integer dataObjectId) {
		set("data_object_id", dataObjectId);
		return (M)this;
	}
	
	/**
	 * 数据模型
	 */
	public java.lang.Integer getDataObjectId() {
		return getInt("data_object_id");
	}

	/**
	 * 子表数据模型
	 */
	public M setSonDataObjectId(java.lang.Integer sonDataObjectId) {
		set("son_data_object_id", sonDataObjectId);
		return (M)this;
	}
	
	/**
	 * 子表数据模型
	 */
	public java.lang.Integer getSonDataObjectId() {
		return getInt("son_data_object_id");
	}

	/**
	 * 主表ID
	 */
	public M setParentIdField(java.lang.String parentIdField) {
		set("parent_id_field", parentIdField);
		return (M)this;
	}
	
	/**
	 * 主表ID
	 */
	public java.lang.String getParentIdField() {
		return getStr("parent_id_field");
	}

	/**
	 * 子表关联ID
	 */
	public M setSonIdField(java.lang.String sonIdField) {
		set("son_id_field", sonIdField);
		return (M)this;
	}
	
	/**
	 * 子表关联ID
	 */
	public java.lang.String getSonIdField() {
		return getStr("son_id_field");
	}

	/**
	 * 菜单链接
	 */
	public M setHref(java.lang.String href) {
		set("href", href);
		return (M)this;
	}
	
	/**
	 * 菜单链接
	 */
	public java.lang.String getHref() {
		return getStr("href");
	}

	/**
	 * 是否隐藏：1隐藏，0显示
	 */
	public M setIsHide(java.lang.Integer isHide) {
		set("is_hide", isHide);
		return (M)this;
	}
	
	/**
	 * 是否隐藏：1隐藏，0显示
	 */
	public java.lang.Integer getIsHide() {
		return getInt("is_hide");
	}

}