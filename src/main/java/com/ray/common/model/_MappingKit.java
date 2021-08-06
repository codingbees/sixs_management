package com.ray.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JBolt, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("data_button", "id", DataButton.class);
		arp.addMapping("data_field", "id", DataField.class);
		arp.addMapping("data_object", "id", DataObject.class);
		arp.addMapping("data_task", "id", DataTask.class);
		arp.addMapping("dicts", "id", Dicts.class);
		arp.addMapping("file", "id", File.class);
		arp.addMapping("man_hour", "id", ManHour.class);
		arp.addMapping("menu", "id", Menu.class);
		arp.addMapping("permissions", "id", Permissions.class);
		arp.addMapping("pp", "id", Pp.class);
		arp.addMapping("pp_qr_problem", "id", PpQrProblem.class);
		arp.addMapping("pp_skill_grades", "id", PpSkillGrades.class);
		arp.addMapping("rational_proposal", "id", RationalProposal.class);
		arp.addMapping("role_permission", "id", RolePermission.class);
		arp.addMapping("roles", "id", Roles.class);
		arp.addMapping("rp_comment", "id", RpComment.class);
		arp.addMapping("rp_improve_type", "id", RpImproveType.class);
		arp.addMapping("rp_like", "no", RpLike.class);
		arp.addMapping("rp_line_structure", "id", RpLineStructure.class);
		arp.addMapping("rp_prize_exchange_list", "id", RpPrizeExchangeList.class);
		arp.addMapping("rp_prize_list", "id", RpPrizeList.class);
		arp.addMapping("rp_shift_leader", "id", RpShiftLeader.class);
		arp.addMapping("s6s_award_record", "id", S6sAwardRecord.class);
		arp.addMapping("s6s_distriction", "id", S6sDistriction.class);
		arp.addMapping("s6s_gloves", "id", S6sGloves.class);
		arp.addMapping("s6s_handlers", "id", S6sHandlers.class);
		arp.addMapping("s6s_impeach", "id", S6sImpeach.class);
		arp.addMapping("s6s_kpi_target", "id", S6sKpiTarget.class);
		arp.addMapping("s6s_prize_exchange_list", "id", S6sPrizeExchangeList.class);
		arp.addMapping("s6s_prize_list", "id", S6sPrizeList.class);
		arp.addMapping("s6s_staff_qty", "id", S6sStaffQty.class);
		arp.addMapping("s6s_total", "id", S6sTotal.class);
		arp.addMapping("serial_number", "id", SerialNumber.class);
		arp.addMapping("user", "id", User.class);
		arp.addMapping("user_role", "id", UserRole.class);
		arp.addMapping("valve_delivery", "id", ValveDelivery.class);
		arp.addMapping("valve_inventory", "id", ValveInventory.class);
		arp.addMapping("valve_part_type", "id", ValvePartType.class);
		// Composite Primary Key order: id,part_no,qr_code
		arp.addMapping("valve_parts", "id,part_no,qr_code", ValveParts.class);
		arp.addMapping("yw_test", "id", YwTest.class);
		arp.addMapping("yw_test_son", "id", YwTestSon.class);
	}
}

