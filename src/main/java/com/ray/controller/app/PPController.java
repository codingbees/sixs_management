package com.ray.controller.app;


import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.model.Pp;

import java.util.List;

//import com.dingtalk.api.request.OapiUserGetByMobileRequest;

public class PPController extends Controller {

    public void getLines() {
        Record record = new Record();
        List<Record> lineList = Db.use("rp").find("SELECT * FROM rp_line_structure");
        record.set("lineList", lineList);
        renderJson(record);
    }

    public void getNameByNo() {
        String partNo = get("part_no");
        String sql = "SELECT * FROM trace_basedata_product WHERE product_no LIKE '%" + partNo + "%'";
        List<Record> nameList = Db.use("pt").find(sql);
        Record record = new Record();
        record.set("nameList", nameList);
        renderJson(record);

    }

    public void getNameAndNoProduct() {
        //查询总成
        Record record = new Record();
        try {
            String sql = "select * from INVENTORY_PART where " + get("method") + " like '%" + get("value") + "%' and accounting_group ='300' and part_product_code < 99";
            List<Record> nameList = Db.use("ifsapp").find(sql);
            record.set("nameList", nameList);
            record.set("msg", "请求成功");
            record.set("code", 200);
        } catch (Exception e) {
            record.set("msg", "请求失败：" + e);
            record.set("code", 0);
        }
        renderJson(record);
    }

    public void getNameAndNoPart() {
        //查询零件 与getNameAndNoPro区别在于accounting_group
        Record record = new Record();
        try {

            String sql = "select * from INVENTORY_PART where " + get("method") + " like '%" + get("value") + "%' and (accounting_group ='200' or accounting_group ='100') and part_product_code < 99";
            List<Record> nameList = Db.use("ifsapp").find(sql);
            record.set("nameList", nameList);
            record.set("msg", "请求成功");
            record.set("code", 200);
        } catch (Exception e) {
            record.set("msg", "请求失败：" + e);
            record.set("code", 0);
        }
        renderJson(record);
    }

    public void getProcessNameAndNo() {
        //查询工艺路线的名称和编号
        Record record = new Record();
        try {
            String sql2 = "select t.* from ROUTING_OPERATION_TAB t \n" +
                    "where t.part_no = '" + get("part_no") + "' and  \n" +
                    "t.routing_REVISION = (select max(routing_revision)from ROUTING_OPERATION_TAB where part_no = '" + get("part_no") + "')";
            List<Record> processList = Db.use("ifsapp").find(sql2);
            record.set("processList", processList);
            record.set("msg", "请求成功");
            record.set("code", 200);
        } catch (Exception e) {
            record.set("msg", "请求失败：" + e);
            record.set("code", 0);
        }
        renderJson(record);
    }

    public void savePPData() {
        Pp pp = getModel(Pp.class, "");
        Record record = new Record();
        String sqlCheckDuplicate = "SELECT * from pp where user_job_number = '" + get("user_job_number") + "' and work_date = '" + get("work_date") + "' and process_no = '" + get("process_no") + "'";
        if (Db.find(sqlCheckDuplicate).size() > 0) {
            record.set("msg", "该日该工序数据已上传，请勿重复提交");
            record.set("code", 1);
        } else {
            try {
                pp.save();
                record.set("msg", "数据上传成功");
                record.set("code", 200);
            } catch (Exception e) {
                record.set("msg", "数据上传失败：" + e);
                record.set("code", 0);
            }
        }


        renderJson(record);
    }

    public void getEmployeeList() {
        Pp pp = getModel(Pp.class, "");
        Record record = new Record();
        String sql = "SELECT * from pp where 1=1 ";
        if (!"".equals(get("department")) && get("department") != null) {
            sql += " and department like '%" + get("department") + "%' ";
        }
        if (!"".equals(get("workshop")) && get("workshop") != null) {
            sql += " and workshop like '%" + get("workshop") + "%' ";
        }
        if (!"".equals(get("production_line")) && get("production_line") != null) {
            sql += " and production_line like '%" + get("production_line") + "%' ";
        }
        if (!"".equals(get("user_name")) && get("user_name") != null) {
            sql += " and user_name like '%" + get("user_name") + "%' ";
        }
        if (!"".equals(get("user_job_number")) && get("user_job_number") != null) {
            sql += " and user_job_number like '%" + get("user_job_number") + "%' ";
        }
        sql+=" group by user_job_number  LIMIT 10 ";
        List<Record> employeeList = Db.find(sql);
        record.set("employeeList", employeeList);
        record.set("code", 200);

        renderJson(record);
    }
    public void getUserEfficiency(){

        Record record = new Record();
        String sql = "SELECT *,ifnull(sum(work_time),0) as total_work_time,ifnull(sum(qualified_qty),0) as total_qualified_qty,ifnull(sum(unqualified_qty),0) as total_unqualified_qty  from pp where user_job_number = '" + get("user_job_number") +"' group by pd_no,part_no,process_no";
        List<Record> employeeProcessList = Db.find(sql);
        record.set("employeeProcessList", employeeProcessList);
        record.set("code", 200);
        renderJson(record);
    }
}
