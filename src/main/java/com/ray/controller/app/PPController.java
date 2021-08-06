package com.ray.controller.app;


import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.ray.common.ding.AccessTokenUtil;
import com.ray.common.model.Pp;
import com.ray.common.model.PpQrProblem;
import com.ray.common.model.QrProblem;
import com.ray.common.model.UserRole;
import com.ray.util.SerialNumberUtil;
import com.taobao.api.ApiException;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
            String sql2 = "select t.* from ROUTING_OPERATION_TAB t " +
                    "where t.part_no = '" + get("part_no") + "' ";
//           "and t.routing_REVISION = (select max(routing_revision)from ROUTING_OPERATION_TAB where part_no = '" + get("part_no") + "')";
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
        List<Record> r = Db.find(sqlCheckDuplicate);
        if (r.size() > 0) {
            record.set("id", r.get(0).get("id"));
            record.set("msg", "该日该工序数据已上传");
            record.set("code", 1);
        } else {
            try {
                pp.set("pp_no","PP"+SerialNumberUtil.generator("pp_no"));
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
    public void updatePPData(){

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
        sql+=" group by user_job_number  LIMIT 20 ";
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

        String sql2 = "SELECT * from pp where user_job_number = '" + get("user_job_number") +"' ";
        List<Record> employeeSkillGrades = Db.find(sql2);
        record.set("employeeSkillGrades", employeeSkillGrades);
        record.set("code", 200);
        renderJson(record);
    }
    //向BIQS系统发起问题
    public void pubQrProblem(){
        Record resp = new Record();
        try {
            PpQrProblem qp = getModel(PpQrProblem.class,"");
            qp.setCreateTime(new Date());
            qp.setNo("FLPMQP"+ SerialNumberUtil.generator("qr_problem_no"));
            qp.save();
            resp.set("code", 200);
            resp.set("msg", "发起问题成功！");
        } catch (Exception e) {
            resp.set("code", 0);
            resp.set("msg", "发起问题失败，原因："+e.getMessage());
            e.printStackTrace();
        }
        renderJson(resp);
    }
    //获取主持人
    public void getLeaderList() throws ApiException {
        List<Record> ur = Db.use("db131").find("select * from user_role where role_id = 14");
        Object[] leaderArray = new String[ur.size()];
        for (int i = 0; i < leaderArray.length; i++) {
            leaderArray[i] = ur.get(i).get("user_id");
        }
        Record resp = new Record();
        resp.set("code", 0);
        resp.set("leaders", leaderArray);
        renderJson(resp);
    }
    //获取班长
    public void getShiftLeaderList() throws ApiException {
        List<Record> ur = Db.use("db131").find("select * from user_role where role_id = 28");

        String[] leaderArray = new String[ur.size()];
        for (int i = 0; i < leaderArray.length; i++) {
            leaderArray[i] = ur.get(i).get("user_id").toString();
        }
        Record resp = new Record();
        resp.set("code", 0);
        resp.set("shiftLeaders", leaderArray);
        renderJson(resp);
    }
    public void copyLastestData(){
        Record resp = new Record();
        String sql = "SELECT * FROM pp WHERE user_job_number = '"+get("job_number")+"' AND work_date = (" +
                " SELECT MAX(work_date) AS maxdate  FROM pp WHERE user_job_number = '"+get("job_number")+"')";

        if(Db.find(sql).size()<1){
            resp.set("code",1);
            resp.set("msg","你还没有提交过数据");
        }else{
            List<Record> processList = Db.find(sql);
            resp.set("code",200);
            resp.set("msg","获取成功");
            resp.set("processList",processList);
        }
        renderJson(resp);
    }
    public void getExceptionList(){
        Record resp = new Record();
        List<Record> exceptionList = Db.find("select * from pp_qr_problem where shift_leader_id = "+get("userid")+
                " and ppcheck_result IS NULL  ");
        resp.set("exceptionList",exceptionList);
        resp.set("code",200);
        renderJson(resp);
    }
    public void getQrProblemItems(){
        Record resp = new Record();
        PpQrProblem qrProblem  = PpQrProblem.dao.findById(get("id"));

        resp.set("qrProblem",qrProblem);
        resp.set("code",200);
        renderJson(resp);
    }
    public void updatePpCheckStatus(){
        Record resp = new Record();
        PpQrProblem qrProblem  = PpQrProblem.dao.findById(get("id"));
        qrProblem.set("ppcheck_result",get("checkStatus"));
        qrProblem.update();
        resp.set("code",200);
        renderJson(resp);
    }

    public void getEfficiencyData(){
        Record record = new Record();
        String sql = "SELECT *,100*(IFNULL(qualified_qty,0)+IFNULL(unqualified_qty,0))/work_time/labor_run_factor as efficiency from pp where 1=1 and work_date >= '"+get("startDate")+"' and  work_date <= '"+get("startDate")+"' ";
        String group = "";
        if (!"".equals(get("department")) && get("department") != null) {
            sql += " and department like '%" + get("department") + "%' ";
            if (!"".equals(get("workshop")) && get("workshop") != null) {
                sql += " and workshop like '%" + get("workshop") + "%' ";
                if (!"".equals(get("production_line")) && get("production_line") != null) {
                    sql += " and production_line like '%" + get("production_line") + "%' ";

                }

            }
        }

        List<Record> efficiencyList = Db.find(sql+group);
        record.set("efficiencyList", efficiencyList);
        record.set("code", 200);

        renderJson(record);
    }

}
