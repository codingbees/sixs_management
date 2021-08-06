package com.ray.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseQrProblem<M extends BaseQrProblem<M>> extends Model<M> implements IBean {

	/**
	 * ID
	 */
	public M setId(Integer id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * ID
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 编号
	 */
	public M setNo(String no) {
		set("no", no);
		return (M)this;
	}
	
	/**
	 * 编号
	 */
	public String getNo() {
		return getStr("no");
	}

	/**
	 * 问题描述
	 */
	public M setDescription(String description) {
		set("description", description);
		return (M)this;
	}
	
	/**
	 * 问题描述
	 */
	public String getDescription() {
		return getStr("description");
	}

	/**
	 * 车间
	 */
	public M setWorkshop(String workshop) {
		set("workshop", workshop);
		return (M)this;
	}
	
	/**
	 * 车间
	 */
	public String getWorkshop() {
		return getStr("workshop");
	}

	/**
	 * 产线
	 */
	public M setProductLine(String productLine) {
		set("product_line", productLine);
		return (M)this;
	}
	
	/**
	 * 产线
	 */
	public String getProductLine() {
		return getStr("product_line");
	}

	/**
	 * 班次
	 */
	public M setClasses(String classes) {
		set("classes", classes);
		return (M)this;
	}
	
	/**
	 * 班次
	 */
	public String getClasses() {
		return getStr("classes");
	}

	/**
	 * 是否重复发生
	 */
	public M setIsRepeat(Integer isRepeat) {
		set("is_repeat", isRepeat);
		return (M)this;
	}
	
	/**
	 * 是否重复发生
	 */
	public Integer getIsRepeat() {
		return getInt("is_repeat");
	}

	/**
	 * 上次发生日期
	 */
	public M setLastHappenDate(java.util.Date lastHappenDate) {
		set("last_happen_date", lastHappenDate);
		return (M)this;
	}
	
	/**
	 * 上次发生日期
	 */
	public java.util.Date getLastHappenDate() {
		return get("last_happen_date");
	}

	/**
	 * 发生问题
	 */
	public M setLastDescription(String lastDescription) {
		set("last_description", lastDescription);
		return (M)this;
	}
	
	/**
	 * 发生问题
	 */
	public String getLastDescription() {
		return getStr("last_description");
	}

	/**
	 * 问题维度
	 */
	public M setDimensionality(String dimensionality) {
		set("dimensionality", dimensionality);
		return (M)this;
	}
	
	/**
	 * 问题维度
	 */
	public String getDimensionality() {
		return getStr("dimensionality");
	}

	/**
	 * 主持人ID
	 */
	public M setPersonLeaderId(String personLeaderId) {
		set("person_leader_id", personLeaderId);
		return (M)this;
	}
	
	/**
	 * 主持人ID
	 */
	public String getPersonLeaderId() {
		return getStr("person_leader_id");
	}

	/**
	 * 主持人姓名
	 */
	public M setPersonLeaderName(String personLeaderName) {
		set("person_leader_name", personLeaderName);
		return (M)this;
	}
	
	/**
	 * 主持人姓名
	 */
	public String getPersonLeaderName() {
		return getStr("person_leader_name");
	}

	/**
	 * 责任人ID
	 */
	public M setPersonLiableId(String personLiableId) {
		set("person_liable_id", personLiableId);
		return (M)this;
	}
	
	/**
	 * 责任人ID
	 */
	public String getPersonLiableId() {
		return getStr("person_liable_id");
	}

	/**
	 * 责任人姓名
	 */
	public M setPersonLiableName(String personLiableName) {
		set("person_liable_name", personLiableName);
		return (M)this;
	}
	
	/**
	 * 责任人姓名
	 */
	public String getPersonLiableName() {
		return getStr("person_liable_name");
	}

	/**
	 * 跟踪人ID
	 */
	public M setPersonTrackingId(String personTrackingId) {
		set("person_tracking_id", personTrackingId);
		return (M)this;
	}
	
	/**
	 * 跟踪人ID
	 */
	public String getPersonTrackingId() {
		return getStr("person_tracking_id");
	}

	/**
	 * 跟踪人姓名
	 */
	public M setPersonTrackingName(String personTrackingName) {
		set("person_tracking_name", personTrackingName);
		return (M)this;
	}
	
	/**
	 * 跟踪人姓名
	 */
	public String getPersonTrackingName() {
		return getStr("person_tracking_name");
	}

	/**
	 * 是否升级
	 */
	public M setIsUpgrade(Integer isUpgrade) {
		set("is_upgrade", isUpgrade);
		return (M)this;
	}
	
	/**
	 * 是否升级
	 */
	public Integer getIsUpgrade() {
		return getInt("is_upgrade");
	}

	/**
	 * 下次回顾日期
	 */
	public M setNextReviewDate(java.util.Date nextReviewDate) {
		set("next_review_date", nextReviewDate);
		return (M)this;
	}
	
	/**
	 * 下次回顾日期
	 */
	public java.util.Date getNextReviewDate() {
		return get("next_review_date");
	}

	/**
	 * 问题第一发现人ID
	 */
	public M setFindUserid(String findUserid) {
		set("find_userid", findUserid);
		return (M)this;
	}
	
	/**
	 * 问题第一发现人ID
	 */
	public String getFindUserid() {
		return getStr("find_userid");
	}

	/**
	 * 问题第一发现人姓名
	 */
	public M setFindUsername(String findUsername) {
		set("find_username", findUsername);
		return (M)this;
	}
	
	/**
	 * 问题第一发现人姓名
	 */
	public String getFindUsername() {
		return getStr("find_username");
	}

	/**
	 * 问题第一发现人部门ID
	 */
	public M setFindUserPartId(String findUserPartId) {
		set("find_user_part_id", findUserPartId);
		return (M)this;
	}
	
	/**
	 * 问题第一发现人部门ID
	 */
	public String getFindUserPartId() {
		return getStr("find_user_part_id");
	}

	/**
	 * 问题第一发现人部门名称
	 */
	public M setFindUserPartName(String findUserPartName) {
		set("find_user_part_name", findUserPartName);
		return (M)this;
	}
	
	/**
	 * 问题第一发现人部门名称
	 */
	public String getFindUserPartName() {
		return getStr("find_user_part_name");
	}

	/**
	 * 拍照上传图片
	 */
	public M setFilename(String filename) {
		set("filename", filename);
		return (M)this;
	}
	
	/**
	 * 拍照上传图片
	 */
	public String getFilename() {
		return getStr("filename");
	}

	/**
	 * 发生日期
	 */
	public M setHappenDate(java.util.Date happenDate) {
		set("happen_date", happenDate);
		return (M)this;
	}
	
	/**
	 * 发生日期
	 */
	public java.util.Date getHappenDate() {
		return get("happen_date");
	}

	/**
	 * when
	 */
	public M setWhen(String when) {
		set("when", when);
		return (M)this;
	}
	
	/**
	 * when
	 */
	public String getWhen() {
		return getStr("when");
	}

	/**
	 * who
	 */
	public M setWho(String who) {
		set("who", who);
		return (M)this;
	}
	
	/**
	 * who
	 */
	public String getWho() {
		return getStr("who");
	}

	/**
	 * where
	 */
	public M setWhere(String where) {
		set("where", where);
		return (M)this;
	}
	
	/**
	 * where
	 */
	public String getWhere() {
		return getStr("where");
	}

	/**
	 * what
	 */
	public M setWhat(String what) {
		set("what", what);
		return (M)this;
	}
	
	/**
	 * what
	 */
	public String getWhat() {
		return getStr("what");
	}

	/**
	 * why
	 */
	public M setWhy(String why) {
		set("why", why);
		return (M)this;
	}
	
	/**
	 * why
	 */
	public String getWhy() {
		return getStr("why");
	}

	/**
	 * how
	 */
	public M setHow(String how) {
		set("how", how);
		return (M)this;
	}
	
	/**
	 * how
	 */
	public String getHow() {
		return getStr("how");
	}

	/**
	 * how_match
	 */
	public M setHowMatch(String howMatch) {
		set("how_match", howMatch);
		return (M)this;
	}
	
	/**
	 * how_match
	 */
	public String getHowMatch() {
		return getStr("how_match");
	}

	/**
	 * 影响
	 */
	public M setEffect(String effect) {
		set("effect", effect);
		return (M)this;
	}
	
	/**
	 * 影响
	 */
	public String getEffect() {
		return getStr("effect");
	}

	/**
	 * 遏制状态:0=否,1=是,2=完成
	 */
	public M setContainStatu(Integer containStatu) {
		set("contain_statu", containStatu);
		return (M)this;
	}
	
	/**
	 * 遏制状态:0=否,1=是,2=完成
	 */
	public Integer getContainStatu() {
		return getInt("contain_statu");
	}

	/**
	 * 来料状态:0=否,1=是
	 */
	public M setContainLlStatu(Integer containLlStatu) {
		set("contain_ll_statu", containLlStatu);
		return (M)this;
	}
	
	/**
	 * 来料状态:0=否,1=是
	 */
	public Integer getContainLlStatu() {
		return getInt("contain_ll_statu");
	}

	/**
	 * 来料数量/范围
	 */
	public M setContainLlQty(String containLlQty) {
		set("contain_ll_qty", containLlQty);
		return (M)this;
	}
	
	/**
	 * 来料数量/范围
	 */
	public String getContainLlQty() {
		return getStr("contain_ll_qty");
	}

	/**
	 * 来料NG数量
	 */
	public M setContainLlNgQty(String containLlNgQty) {
		set("contain_ll_ng_qty", containLlNgQty);
		return (M)this;
	}
	
	/**
	 * 来料NG数量
	 */
	public String getContainLlNgQty() {
		return getStr("contain_ll_ng_qty");
	}

	/**
	 * 来料措施
	 */
	public M setContainLlMeasure(String containLlMeasure) {
		set("contain_ll_measure", containLlMeasure);
		return (M)this;
	}
	
	/**
	 * 来料措施
	 */
	public String getContainLlMeasure() {
		return getStr("contain_ll_measure");
	}

	/**
	 * 在制品状态:0=否,1=是
	 */
	public M setContainZzpStatu(Integer containZzpStatu) {
		set("contain_zzp_statu", containZzpStatu);
		return (M)this;
	}
	
	/**
	 * 在制品状态:0=否,1=是
	 */
	public Integer getContainZzpStatu() {
		return getInt("contain_zzp_statu");
	}

	/**
	 * 在制品数量/范围
	 */
	public M setContainZzpQty(String containZzpQty) {
		set("contain_zzp_qty", containZzpQty);
		return (M)this;
	}
	
	/**
	 * 在制品数量/范围
	 */
	public String getContainZzpQty() {
		return getStr("contain_zzp_qty");
	}

	/**
	 * 在制品NG数量
	 */
	public M setContainZzpNgQty(String containZzpNgQty) {
		set("contain_zzp_ng_qty", containZzpNgQty);
		return (M)this;
	}
	
	/**
	 * 在制品NG数量
	 */
	public String getContainZzpNgQty() {
		return getStr("contain_zzp_ng_qty");
	}

	/**
	 * 在制品措施
	 */
	public M setContainZzpMeasure(String containZzpMeasure) {
		set("contain_zzp_measure", containZzpMeasure);
		return (M)this;
	}
	
	/**
	 * 在制品措施
	 */
	public String getContainZzpMeasure() {
		return getStr("contain_zzp_measure");
	}

	/**
	 * 成品状态:0=否,1=是
	 */
	public M setContainCpStatu(Integer containCpStatu) {
		set("contain_cp_statu", containCpStatu);
		return (M)this;
	}
	
	/**
	 * 成品状态:0=否,1=是
	 */
	public Integer getContainCpStatu() {
		return getInt("contain_cp_statu");
	}

	/**
	 * 成品数量/范围
	 */
	public M setContainCpQty(String containCpQty) {
		set("contain_cp_qty", containCpQty);
		return (M)this;
	}
	
	/**
	 * 成品数量/范围
	 */
	public String getContainCpQty() {
		return getStr("contain_cp_qty");
	}

	/**
	 * 成品NG数量
	 */
	public M setContainCpNgQty(String containCpNgQty) {
		set("contain_cp_ng_qty", containCpNgQty);
		return (M)this;
	}
	
	/**
	 * 成品NG数量
	 */
	public String getContainCpNgQty() {
		return getStr("contain_cp_ng_qty");
	}

	/**
	 * 成品措施
	 */
	public M setContainCpMeasure(String containCpMeasure) {
		set("contain_cp_measure", containCpMeasure);
		return (M)this;
	}
	
	/**
	 * 成品措施
	 */
	public String getContainCpMeasure() {
		return getStr("contain_cp_measure");
	}

	/**
	 * 客户处状态:0=否,1=是
	 */
	public M setContainKhcStatu(Integer containKhcStatu) {
		set("contain_khc_statu", containKhcStatu);
		return (M)this;
	}
	
	/**
	 * 客户处状态:0=否,1=是
	 */
	public Integer getContainKhcStatu() {
		return getInt("contain_khc_statu");
	}

	/**
	 * 客户处数量/范围
	 */
	public M setContainKhcQty(String containKhcQty) {
		set("contain_khc_qty", containKhcQty);
		return (M)this;
	}
	
	/**
	 * 客户处数量/范围
	 */
	public String getContainKhcQty() {
		return getStr("contain_khc_qty");
	}

	/**
	 * 客户处NG数量
	 */
	public M setContainKhcNgQty(String containKhcNgQty) {
		set("contain_khc_ng_qty", containKhcNgQty);
		return (M)this;
	}
	
	/**
	 * 客户处NG数量
	 */
	public String getContainKhcNgQty() {
		return getStr("contain_khc_ng_qty");
	}

	/**
	 * 客户处措施
	 */
	public M setContainKhcMeasure(String containKhcMeasure) {
		set("contain_khc_measure", containKhcMeasure);
		return (M)this;
	}
	
	/**
	 * 客户处措施
	 */
	public String getContainKhcMeasure() {
		return getStr("contain_khc_measure");
	}

	/**
	 * 临时措施
	 */
	public M setContainTemp(String containTemp) {
		set("contain_temp", containTemp);
		return (M)this;
	}
	
	/**
	 * 临时措施
	 */
	public String getContainTemp() {
		return getStr("contain_temp");
	}

	/**
	 * 遏制表单
	 */
	public M setContainForm(String containForm) {
		set("contain_form", containForm);
		return (M)this;
	}
	
	/**
	 * 遏制表单
	 */
	public String getContainForm() {
		return getStr("contain_form");
	}

	/**
	 * 追溯
	 */
	public M setRetrospect(String retrospect) {
		set("retrospect", retrospect);
		return (M)this;
	}
	
	/**
	 * 追溯
	 */
	public String getRetrospect() {
		return getStr("retrospect");
	}

	/**
	 * 遏制状态颜色
	 */
	public M setContainStatuColor(String containStatuColor) {
		set("contain_statu_color", containStatuColor);
		return (M)this;
	}
	
	/**
	 * 遏制状态颜色
	 */
	public String getContainStatuColor() {
		return getStr("contain_statu_color");
	}

	/**
	 * 人
	 */
	public M setReasonPeople(String reasonPeople) {
		set("reason_people", reasonPeople);
		return (M)this;
	}
	
	/**
	 * 人
	 */
	public String getReasonPeople() {
		return getStr("reason_people");
	}

	/**
	 * 机
	 */
	public M setReasonEquipment(String reasonEquipment) {
		set("reason_equipment", reasonEquipment);
		return (M)this;
	}
	
	/**
	 * 机
	 */
	public String getReasonEquipment() {
		return getStr("reason_equipment");
	}

	/**
	 * 料
	 */
	public M setReasonMaterial(String reasonMaterial) {
		set("reason_material", reasonMaterial);
		return (M)this;
	}
	
	/**
	 * 料
	 */
	public String getReasonMaterial() {
		return getStr("reason_material");
	}

	/**
	 * 法
	 */
	public M setReasonFa(String reasonFa) {
		set("reason_fa", reasonFa);
		return (M)this;
	}
	
	/**
	 * 法
	 */
	public String getReasonFa() {
		return getStr("reason_fa");
	}

	/**
	 * 环
	 */
	public M setReasonEnvironment(String reasonEnvironment) {
		set("reason_environment", reasonEnvironment);
		return (M)this;
	}
	
	/**
	 * 环
	 */
	public String getReasonEnvironment() {
		return getStr("reason_environment");
	}

	/**
	 * 测
	 */
	public M setReasonMeasure(String reasonMeasure) {
		set("reason_measure", reasonMeasure);
		return (M)this;
	}
	
	/**
	 * 测
	 */
	public String getReasonMeasure() {
		return getStr("reason_measure");
	}

	/**
	 * 产生原因：技术
	 */
	public M setProduceReasonSkill(String produceReasonSkill) {
		set("produce_reason_skill", produceReasonSkill);
		return (M)this;
	}
	
	/**
	 * 产生原因：技术
	 */
	public String getProduceReasonSkill() {
		return getStr("produce_reason_skill");
	}

	/**
	 * 产生原因：技术有效性
	 */
	public M setProduceResaonSkillValidity(String produceResaonSkillValidity) {
		set("produce_resaon_skill_validity", produceResaonSkillValidity);
		return (M)this;
	}
	
	/**
	 * 产生原因：技术有效性
	 */
	public String getProduceResaonSkillValidity() {
		return getStr("produce_resaon_skill_validity");
	}

	/**
	 * 产生原因：管理
	 */
	public M setProduceReasonManage(String produceReasonManage) {
		set("produce_reason_manage", produceReasonManage);
		return (M)this;
	}
	
	/**
	 * 产生原因：管理
	 */
	public String getProduceReasonManage() {
		return getStr("produce_reason_manage");
	}

	/**
	 * 产生原因：管理有效性
	 */
	public M setProduceResaonManageValidity(String produceResaonManageValidity) {
		set("produce_resaon_manage_validity", produceResaonManageValidity);
		return (M)this;
	}
	
	/**
	 * 产生原因：管理有效性
	 */
	public String getProduceResaonManageValidity() {
		return getStr("produce_resaon_manage_validity");
	}

	/**
	 * 流出原因：技术
	 */
	public M setOutflowReasonSkill(String outflowReasonSkill) {
		set("outflow_reason_skill", outflowReasonSkill);
		return (M)this;
	}
	
	/**
	 * 流出原因：技术
	 */
	public String getOutflowReasonSkill() {
		return getStr("outflow_reason_skill");
	}

	/**
	 * 流出原因：技术有效性
	 */
	public M setOutflowResaonSkillValidity(String outflowResaonSkillValidity) {
		set("outflow_resaon_skill_validity", outflowResaonSkillValidity);
		return (M)this;
	}
	
	/**
	 * 流出原因：技术有效性
	 */
	public String getOutflowResaonSkillValidity() {
		return getStr("outflow_resaon_skill_validity");
	}

	/**
	 * 流出原因：管理
	 */
	public M setOutflowReasonManage(String outflowReasonManage) {
		set("outflow_reason_manage", outflowReasonManage);
		return (M)this;
	}
	
	/**
	 * 流出原因：管理
	 */
	public String getOutflowReasonManage() {
		return getStr("outflow_reason_manage");
	}

	/**
	 * 流出原因：管理有效性
	 */
	public M setOutflowResaonManageValidity(String outflowResaonManageValidity) {
		set("outflow_resaon_manage_validity", outflowResaonManageValidity);
		return (M)this;
	}
	
	/**
	 * 流出原因：管理有效性
	 */
	public String getOutflowResaonManageValidity() {
		return getStr("outflow_resaon_manage_validity");
	}

	/**
	 * 原因分析附件
	 */
	public M setReasonFile(String reasonFile) {
		set("reason_file", reasonFile);
		return (M)this;
	}
	
	/**
	 * 原因分析附件
	 */
	public String getReasonFile() {
		return getStr("reason_file");
	}

	/**
	 * 原因分析确认人
	 */
	public M setReasonConfirmUser(String reasonConfirmUser) {
		set("reason_confirm_user", reasonConfirmUser);
		return (M)this;
	}
	
	/**
	 * 原因分析确认人
	 */
	public String getReasonConfirmUser() {
		return getStr("reason_confirm_user");
	}

	/**
	 * 原因分析确认时间
	 */
	public M setReasonConfirmDate(java.util.Date reasonConfirmDate) {
		set("reason_confirm_date", reasonConfirmDate);
		return (M)this;
	}
	
	/**
	 * 原因分析确认时间
	 */
	public java.util.Date getReasonConfirmDate() {
		return get("reason_confirm_date");
	}

	/**
	 * 原因分析状态颜色
	 */
	public M setReasonStatuColor(String reasonStatuColor) {
		set("reason_statu_color", reasonStatuColor);
		return (M)this;
	}
	
	/**
	 * 原因分析状态颜色
	 */
	public String getReasonStatuColor() {
		return getStr("reason_statu_color");
	}

	/**
	 * 永久措施：防产生-技术-行动计划
	 */
	public M setMeasurePgSkillPlan(String measurePgSkillPlan) {
		set("measure_pg_skill_plan", measurePgSkillPlan);
		return (M)this;
	}
	
	/**
	 * 永久措施：防产生-技术-行动计划
	 */
	public String getMeasurePgSkillPlan() {
		return getStr("measure_pg_skill_plan");
	}

	/**
	 * 永久措施：防产生-技术-责任人ID
	 */
	public M setMeasurePgSkillUserid(String measurePgSkillUserid) {
		set("measure_pg_skill_userid", measurePgSkillUserid);
		return (M)this;
	}
	
	/**
	 * 永久措施：防产生-技术-责任人ID
	 */
	public String getMeasurePgSkillUserid() {
		return getStr("measure_pg_skill_userid");
	}

	/**
	 * 永久措施：防产生-技术-责任人姓名
	 */
	public M setMeasurePgSkillUsername(String measurePgSkillUsername) {
		set("measure_pg_skill_username", measurePgSkillUsername);
		return (M)this;
	}
	
	/**
	 * 永久措施：防产生-技术-责任人姓名
	 */
	public String getMeasurePgSkillUsername() {
		return getStr("measure_pg_skill_username");
	}

	/**
	 * 永久措施：防产生-技术-完成时间
	 */
	public M setMeasurePgSkillDate(java.util.Date measurePgSkillDate) {
		set("measure_pg_skill_date", measurePgSkillDate);
		return (M)this;
	}
	
	/**
	 * 永久措施：防产生-技术-完成时间
	 */
	public java.util.Date getMeasurePgSkillDate() {
		return get("measure_pg_skill_date");
	}

	/**
	 * 永久措施：防产生-管理-行动计划
	 */
	public M setMeasurePgManagePlan(String measurePgManagePlan) {
		set("measure_pg_manage_plan", measurePgManagePlan);
		return (M)this;
	}
	
	/**
	 * 永久措施：防产生-管理-行动计划
	 */
	public String getMeasurePgManagePlan() {
		return getStr("measure_pg_manage_plan");
	}

	/**
	 * 永久措施：防产生-管理-责任人ID
	 */
	public M setMeasurePgManageUserid(String measurePgManageUserid) {
		set("measure_pg_manage_userid", measurePgManageUserid);
		return (M)this;
	}
	
	/**
	 * 永久措施：防产生-管理-责任人ID
	 */
	public String getMeasurePgManageUserid() {
		return getStr("measure_pg_manage_userid");
	}

	/**
	 * 永久措施：防产生-管理-责任人姓名
	 */
	public M setMeasurePgManageUsername(String measurePgManageUsername) {
		set("measure_pg_manage_username", measurePgManageUsername);
		return (M)this;
	}
	
	/**
	 * 永久措施：防产生-管理-责任人姓名
	 */
	public String getMeasurePgManageUsername() {
		return getStr("measure_pg_manage_username");
	}

	/**
	 * 永久措施：防产生-管理-完成时间
	 */
	public M setMeasurePgManageDate(java.util.Date measurePgManageDate) {
		set("measure_pg_manage_date", measurePgManageDate);
		return (M)this;
	}
	
	/**
	 * 永久措施：防产生-管理-完成时间
	 */
	public java.util.Date getMeasurePgManageDate() {
		return get("measure_pg_manage_date");
	}

	/**
	 * 永久措施：防流出-技术-行动计划
	 */
	public M setMeasurePoSkillPlan(String measurePoSkillPlan) {
		set("measure_po_skill_plan", measurePoSkillPlan);
		return (M)this;
	}
	
	/**
	 * 永久措施：防流出-技术-行动计划
	 */
	public String getMeasurePoSkillPlan() {
		return getStr("measure_po_skill_plan");
	}

	/**
	 * 永久措施：防流出-技术-责任人ID
	 */
	public M setMeasurePoSkillUserid(String measurePoSkillUserid) {
		set("measure_po_skill_userid", measurePoSkillUserid);
		return (M)this;
	}
	
	/**
	 * 永久措施：防流出-技术-责任人ID
	 */
	public String getMeasurePoSkillUserid() {
		return getStr("measure_po_skill_userid");
	}

	/**
	 * 永久措施：防流出-技术-责任人姓名
	 */
	public M setMeasurePoSkillUsername(String measurePoSkillUsername) {
		set("measure_po_skill_username", measurePoSkillUsername);
		return (M)this;
	}
	
	/**
	 * 永久措施：防流出-技术-责任人姓名
	 */
	public String getMeasurePoSkillUsername() {
		return getStr("measure_po_skill_username");
	}

	/**
	 * 永久措施：防流出-技术-完成时间
	 */
	public M setMeasurePoSkillDate(java.util.Date measurePoSkillDate) {
		set("measure_po_skill_date", measurePoSkillDate);
		return (M)this;
	}
	
	/**
	 * 永久措施：防流出-技术-完成时间
	 */
	public java.util.Date getMeasurePoSkillDate() {
		return get("measure_po_skill_date");
	}

	/**
	 * 永久措施：防流出-管理-行动计划
	 */
	public M setMeasurePoManagePlan(String measurePoManagePlan) {
		set("measure_po_manage_plan", measurePoManagePlan);
		return (M)this;
	}
	
	/**
	 * 永久措施：防流出-管理-行动计划
	 */
	public String getMeasurePoManagePlan() {
		return getStr("measure_po_manage_plan");
	}

	/**
	 * 永久措施：防流出-管理-责任人ID
	 */
	public M setMeasurePoManageUserid(String measurePoManageUserid) {
		set("measure_po_manage_userid", measurePoManageUserid);
		return (M)this;
	}
	
	/**
	 * 永久措施：防流出-管理-责任人ID
	 */
	public String getMeasurePoManageUserid() {
		return getStr("measure_po_manage_userid");
	}

	/**
	 * 永久措施：防流出-管理-责任人姓名
	 */
	public M setMeasurePoManageUsername(String measurePoManageUsername) {
		set("measure_po_manage_username", measurePoManageUsername);
		return (M)this;
	}
	
	/**
	 * 永久措施：防流出-管理-责任人姓名
	 */
	public String getMeasurePoManageUsername() {
		return getStr("measure_po_manage_username");
	}

	/**
	 * 永久措施：防流出-管理-完成时间
	 */
	public M setMeasurePoManageDate(java.util.Date measurePoManageDate) {
		set("measure_po_manage_date", measurePoManageDate);
		return (M)this;
	}
	
	/**
	 * 永久措施：防流出-管理-完成时间
	 */
	public java.util.Date getMeasurePoManageDate() {
		return get("measure_po_manage_date");
	}

	/**
	 * 永久措施：附件
	 */
	public M setMeasureFile(String measureFile) {
		set("measure_file", measureFile);
		return (M)this;
	}
	
	/**
	 * 永久措施：附件
	 */
	public String getMeasureFile() {
		return getStr("measure_file");
	}

	/**
	 * 永久措施：跟踪情况
	 */
	public M setMeasureTracking(String measureTracking) {
		set("measure_tracking", measureTracking);
		return (M)this;
	}
	
	/**
	 * 永久措施：跟踪情况
	 */
	public String getMeasureTracking() {
		return getStr("measure_tracking");
	}

	/**
	 * 永久措施：状态颜色
	 */
	public M setMeasureStatu(String measureStatu) {
		set("measure_statu", measureStatu);
		return (M)this;
	}
	
	/**
	 * 永久措施：状态颜色
	 */
	public String getMeasureStatu() {
		return getStr("measure_statu");
	}

	/**
	 * 标准化：FMEA
	 */
	public M setStandardFmea(Integer standardFmea) {
		set("standard_fmea", standardFmea);
		return (M)this;
	}
	
	/**
	 * 标准化：FMEA
	 */
	public Integer getStandardFmea() {
		return getInt("standard_fmea");
	}

	/**
	 * 标准化：FMEA文件
	 */
	public M setStandardFmeaFile(String standardFmeaFile) {
		set("standard_fmea_file", standardFmeaFile);
		return (M)this;
	}
	
	/**
	 * 标准化：FMEA文件
	 */
	public String getStandardFmeaFile() {
		return getStr("standard_fmea_file");
	}

	/**
	 * 标准化：CP
	 */
	public M setStandardCp(Integer standardCp) {
		set("standard_cp", standardCp);
		return (M)this;
	}
	
	/**
	 * 标准化：CP
	 */
	public Integer getStandardCp() {
		return getInt("standard_cp");
	}

	/**
	 * 标准化：CP文件
	 */
	public M setStandardCpFile(String standardCpFile) {
		set("standard_cp_file", standardCpFile);
		return (M)this;
	}
	
	/**
	 * 标准化：CP文件
	 */
	public String getStandardCpFile() {
		return getStr("standard_cp_file");
	}

	/**
	 * 标准化：作业指导书
	 */
	public M setStandardZyzds(Integer standardZyzds) {
		set("standard_zyzds", standardZyzds);
		return (M)this;
	}
	
	/**
	 * 标准化：作业指导书
	 */
	public Integer getStandardZyzds() {
		return getInt("standard_zyzds");
	}

	/**
	 * 标准化：作业指导书文件
	 */
	public M setStandardZyzdsFile(String standardZyzdsFile) {
		set("standard_zyzds_file", standardZyzdsFile);
		return (M)this;
	}
	
	/**
	 * 标准化：作业指导书文件
	 */
	public String getStandardZyzdsFile() {
		return getStr("standard_zyzds_file");
	}

	/**
	 * 标准化：横向展开
	 */
	public M setStandardHxzk(Integer standardHxzk) {
		set("standard_hxzk", standardHxzk);
		return (M)this;
	}
	
	/**
	 * 标准化：横向展开
	 */
	public Integer getStandardHxzk() {
		return getInt("standard_hxzk");
	}

	/**
	 * 标准化：横向展开文件
	 */
	public M setStandardHxzkFile(String standardHxzkFile) {
		set("standard_hxzk_file", standardHxzkFile);
		return (M)this;
	}
	
	/**
	 * 标准化：横向展开文件
	 */
	public String getStandardHxzkFile() {
		return getStr("standard_hxzk_file");
	}

	/**
	 * 点检表附件
	 */
	public M setDjbUrl(String djbUrl) {
		set("djb_url", djbUrl);
		return (M)this;
	}
	
	/**
	 * 点检表附件
	 */
	public String getDjbUrl() {
		return getStr("djb_url");
	}

	/**
	 * 检验指导书附件
	 */
	public M setJyzdsUrl(String jyzdsUrl) {
		set("jyzds_url", jyzdsUrl);
		return (M)this;
	}
	
	/**
	 * 检验指导书附件
	 */
	public String getJyzdsUrl() {
		return getStr("jyzds_url");
	}

	/**
	 * 分层审核附件
	 */
	public M setFcshUrl(String fcshUrl) {
		set("fcsh_url", fcshUrl);
		return (M)this;
	}
	
	/**
	 * 分层审核附件
	 */
	public String getFcshUrl() {
		return getStr("fcsh_url");
	}

	/**
	 * 标准化：状态颜色
	 */
	public M setStandardStatu(String standardStatu) {
		set("standard_statu", standardStatu);
		return (M)this;
	}
	
	/**
	 * 标准化：状态颜色
	 */
	public String getStandardStatu() {
		return getStr("standard_statu");
	}

	/**
	 * 问题状态:0=发起,1=已升版,2=已升级,3=已关闭
	 */
	public M setStatu(Integer statu) {
		set("statu", statu);
		return (M)this;
	}
	
	/**
	 * 问题状态:0=发起,1=已升版,2=已升级,3=已关闭
	 */
	public Integer getStatu() {
		return getInt("statu");
	}

	/**
	 * 创建时间
	 */
	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	/**
	 * 0,记录。1已过升版，记录在统计中
	 */
	public M setIsstatu2(Integer isstatu2) {
		set("isstatu2", isstatu2);
		return (M)this;
	}
	
	/**
	 * 0,记录。1已过升版，记录在统计中
	 */
	public Integer getIsstatu2() {
		return getInt("isstatu2");
	}

	/**
	 * 1再次升版
	 */
	public M setStatu2(Integer statu2) {
		set("statu2", statu2);
		return (M)this;
	}
	
	/**
	 * 1再次升版
	 */
	public Integer getStatu2() {
		return getInt("statu2");
	}

	/**
	 * 再再次升版
	 */
	public M setStatu3(Integer statu3) {
		set("statu3", statu3);
		return (M)this;
	}
	
	/**
	 * 再再次升版
	 */
	public Integer getStatu3() {
		return getInt("statu3");
	}

	/**
	 * 关闭日期
	 */
	public M setCloseDate(java.util.Date closeDate) {
		set("close_date", closeDate);
		return (M)this;
	}
	
	/**
	 * 关闭日期
	 */
	public java.util.Date getCloseDate() {
		return get("close_date");
	}

	/**
	 * 生产姓名
	 */
	public M setScUsernames(String scUsernames) {
		set("sc_usernames", scUsernames);
		return (M)this;
	}
	
	/**
	 * 生产姓名
	 */
	public String getScUsernames() {
		return getStr("sc_usernames");
	}

	/**
	 * 技术姓名
	 */
	public M setJsUsernames(String jsUsernames) {
		set("js_usernames", jsUsernames);
		return (M)this;
	}
	
	/**
	 * 技术姓名
	 */
	public String getJsUsernames() {
		return getStr("js_usernames");
	}

	/**
	 * 质量人姓名
	 */
	public M setZlUsernames(String zlUsernames) {
		set("zl_usernames", zlUsernames);
		return (M)this;
	}
	
	/**
	 * 质量人姓名
	 */
	public String getZlUsernames() {
		return getStr("zl_usernames");
	}

	/**
	 * 主持人备忘录
	 */
	public M setZhuchirenbwl(String zhuchirenbwl) {
		set("zhuchirenbwl", zhuchirenbwl);
		return (M)this;
	}
	
	/**
	 * 主持人备忘录
	 */
	public String getZhuchirenbwl() {
		return getStr("zhuchirenbwl");
	}

	/**
	 * 问题输出要求
	 */
	public M setWtscyq(String wtscyq) {
		set("wtscyq", wtscyq);
		return (M)this;
	}
	
	/**
	 * 问题输出要求
	 */
	public String getWtscyq() {
		return getStr("wtscyq");
	}

	/**
	 * 产品名称
	 */
	public M setCpName(String cpName) {
		set("cp_name", cpName);
		return (M)this;
	}
	
	/**
	 * 产品名称
	 */
	public String getCpName() {
		return getStr("cp_name");
	}

	/**
	 * 批次
	 */
	public M setPcNo(String pcNo) {
		set("pc_no", pcNo);
		return (M)this;
	}
	
	/**
	 * 批次
	 */
	public String getPcNo() {
		return getStr("pc_no");
	}

}
