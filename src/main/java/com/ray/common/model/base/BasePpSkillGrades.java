package com.ray.common.model.base;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 
 * Generated by JBolt, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePpSkillGrades<M extends BasePpSkillGrades<M>> extends Model<M> implements IBean {

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
	 * 姓名
	 */
	public M setUserName(java.lang.String userName) {
		set("user_name", userName);
		return (M)this;
	}
	
	/**
	 * 姓名
	 */
	public java.lang.String getUserName() {
		return getStr("user_name");
	}

	/**
	 * 工号
	 */
	public M setUserJobNumber(java.lang.String userJobNumber) {
		set("user_job_number", userJobNumber);
		return (M)this;
	}
	
	/**
	 * 工号
	 */
	public java.lang.String getUserJobNumber() {
		return getStr("user_job_number");
	}

	/**
	 * 产品编号
	 */
	public M setPdNo(java.lang.String pdNo) {
		set("pd_no", pdNo);
		return (M)this;
	}
	
	/**
	 * 产品编号
	 */
	public java.lang.String getPdNo() {
		return getStr("pd_no");
	}

	/**
	 * 产品名称
	 */
	public M setPdName(java.lang.String pdName) {
		set("pd_name", pdName);
		return (M)this;
	}
	
	/**
	 * 产品名称
	 */
	public java.lang.String getPdName() {
		return getStr("pd_name");
	}

	/**
	 * 零件编号
	 */
	public M setPartNo(java.lang.String partNo) {
		set("part_no", partNo);
		return (M)this;
	}
	
	/**
	 * 零件编号
	 */
	public java.lang.String getPartNo() {
		return getStr("part_no");
	}

	/**
	 * 零件名称
	 */
	public M setPartName(java.lang.String partName) {
		set("part_name", partName);
		return (M)this;
	}
	
	/**
	 * 零件名称
	 */
	public java.lang.String getPartName() {
		return getStr("part_name");
	}

	/**
	 * 工序编号
	 */
	public M setProcessNo(java.lang.String processNo) {
		set("process_no", processNo);
		return (M)this;
	}
	
	/**
	 * 工序编号
	 */
	public java.lang.String getProcessNo() {
		return getStr("process_no");
	}

	/**
	 * 工序名称
	 */
	public M setProcessName(java.lang.String processName) {
		set("process_name", processName);
		return (M)this;
	}
	
	/**
	 * 工序名称
	 */
	public java.lang.String getProcessName() {
		return getStr("process_name");
	}

	/**
	 * 技能等级
	 */
	public M setSkillGrade(java.lang.Integer skillGrade) {
		set("skill_grade", skillGrade);
		return (M)this;
	}
	
	/**
	 * 技能等级
	 */
	public java.lang.Integer getSkillGrade() {
		return getInt("skill_grade");
	}

	/**
	 * 审核人
	 */
	public M setAuditPerson(java.lang.String auditPerson) {
		set("audit_person", auditPerson);
		return (M)this;
	}
	
	/**
	 * 审核人
	 */
	public java.lang.String getAuditPerson() {
		return getStr("audit_person");
	}

}
