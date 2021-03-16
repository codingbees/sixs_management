package com.ray.common.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.utils.FileExportUtil;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.json.FastJson;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.upload.UploadFile;
import com.ray.common.interceptor.AopContext;
import com.ray.common.interceptor.MetaObjectIntercept;
import com.ray.common.interceptor.TemplateUtil;
import com.ray.common.model.DataField;
import com.ray.common.model.DataObject;
import com.ray.common.model.Dicts;

/**
 * 工具类
 * @author Ray
 *
 */
public class CommonController extends BaseController {
	
	final Controller ctrl = this;
	protected MetaObjectIntercept intercept = null;
	static Prop p = PropKit.use("config-dev.properties").appendIfExists("config-pro.properties");
	
	/**
	 * 根据表名和字段获取字典list
	 */
	public void getDictsByObjectAndField() {
		List<Dicts> list = Dicts.dao.find("select * from dicts where object = '"+get("object")+"' and field = '"+get("field")+"'");
		renderJson(list);
	}
	
	/**
	 * 根据表名和字段和key获取字典list
	 */
	public void getDictsByOFV() {
		Dicts model = Dicts.dao.findFirst("select * from dicts where object = '"+get("object")+"' and field = '"+get("field")+"' and value = "+get("value"));
		renderJson(model);
	}
	
	/**
	 * 上传文件
	 */
	public void upload() {
		Record resp = new Record();
		try {
			UploadFile file = getFile("file", "");
			resp.set("code", 1);
			resp.set("fileName", file.getFileName());
			resp.set("column", get("column"));
			resp.set("msg", "上传到临时文件夹成功！");
		} catch (Exception e) {
			resp.set("code", 0);
			resp.set("msg", "上传到临时文件夹失败，原因："+e.getMessage());
			e.printStackTrace();
		}
    	renderJson(resp);
	}
	
	/**
	 * 获取文件列表
	 */
	@NotAction
	public static List<Record> getFileList(String ids) {
		List<Record> list = new ArrayList<Record>();
		if(ids!=null && !"".equals(ids)) {
			list = Db.find("select * from file where id in ("+ids+")");
		}
		return list;
	}
	
	/**
	 * 获取文件列表
	 */
	public void getFileList() {
		JSONObject row = JSONObject.parseObject(get("row"));
		String ids = row.getString(get("column"));
		List<Record> list = getFileList(ids);
		renderJson(list);
	}
	
	/**
	 * 删除文件
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void delFile() {
		try {
			DataObject object = DataObject.dao.findById(get("object_id"));
			Map map = FastJson.getJson().parse(get("row"), Map.class);
			Record model = new Record().setColumns(map);
			model = Db.findById(object.getTableName(), model.get("id"));
			String[] fileidArray = model.get(get("column")).toString().split(",");
			String newIds = "";
			for (int i = 0; i < fileidArray.length; i++) {
				if(!fileidArray[i].equals(get("file_id"))) {
					newIds += fileidArray[i]+",";
				}
			}
			if(!"".equals(newIds)) {
				newIds = newIds.substring(0, newIds.length()-1);
			}
			model.set(get("column"), newIds);
			Db.update(object.getTableName(), model);
			renderJson(Ret.ok("msg", "删除文件成功！"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(Ret.fail("msg", "删除文件失败,"+e.getMessage()));
		}
	}
	
	/**
	 * 修改文件重新上传
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void confirmUpload() {
		Map map = FastJson.getJson().parse(get("row"), Map.class);
		DataObject object = DataObject.dao.findById(get("object_id"));
		Record model = new Record().setColumns(map);
		model = Db.findById(object.getTableName(), model.getInt("id"));
		DataField file_field = DataField.dao.findFirst("select * from data_field where data_object_id = "
				+ get("object_id") + " and en = '"+get("column")+"'");
		
		//已上传个数
		int has = 0;
		if(!"".equals(model.getStr(get("column"))) && model.getStr(get("column"))!=null) {
			has = model.getStr(get("column")).split(",").length;
		}
		//当前上传个数
		String[] fileArray = getParaValues("fileList[]");
		int now = fileArray.length;
		//最多个数限制
		int all = Integer.valueOf(file_field.getFormatter().split("\\|")[1]);
		//判断文件个数限制
		if(has+now<=all) {
			String fileIds = model.get(get("column"));
			if(!"".equals(model.get(get("column"))) && model.get(get("column"))!=null) {
				fileIds+=",";
			}else {
				fileIds = "";
			}
			List<Record> respFileList = new ArrayList<Record>();
			for (int i = 0; i < fileArray.length; i++) {
				JSONObject jb = JSONObject.parseObject(fileArray[i]);
				if(!"".equals(jb.getString("response")) && jb.getString("response")!=null) {
					JSONObject response = JSONObject.parseObject(jb.getString("response"));
					File file = new File(p.get("domin_path")+"/temp/"+response.getString("fileName"));
					File newfile = new File(p.get("domin_path")+"/"+file_field.getFormatter().split("\\|")[0]+"/"+UUID.randomUUID()+"."+response.getString("fileName").split("\\.")[1]);
					File fileParent = newfile.getParentFile();
					//判断文件夹是否存在
					if (!fileParent.exists()) {
						fileParent.mkdirs();
					}
					try {
						//从temp文件夹移动到正式文件夹
						if(file.renameTo(newfile)){
							Record fileRecord = new Record();
							fileRecord.set("name", response.getString("fileName"));
							fileRecord.set("url", "/"+file_field.getFormatter().split("\\|")[0]+"/"+newfile.getName());
							Db.save("file", fileRecord);
							respFileList.add(fileRecord);
							fileIds +=fileRecord.getStr("id")+",";
						}else {
							renderJson(Ret.fail("error", "新增失败，原因：文件处理失败"));
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
						renderJson(Ret.fail("error", "新增失败，原因："+e.getMessage()));
						return;
					}
				}
			}
			model.set(file_field.getEn(), fileIds.substring(0, fileIds.length()-1));
			Db.update(object.getTableName(), model);
			renderJson(Ret.ok("msg", "保存成功").set("respFileList", respFileList));
		}else {
			renderJson(Ret.fail("msg", "文件个数超出限制，最多上传"+all+"个文件"));
		}
	}
	
	/**
	 * 导出
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void export() {
		DataObject model = DataObject.dao.findById(get("object_id"));
		List<DataField> fields = DataField.dao.find("select * from data_field where data_object_id = "+get("object_id"));
		String sqlString = "select ";
		for (int i = 0; i < fields.size(); i++) {
			if(i>0) {
				sqlString += ",";
			}
			if(fields.get(i).getIsFictitious()) {
				if("select".equals(fields.get(i).getType()) || "radio".equals(fields.get(i).getType())) {
					sqlString += "(select "+fields.get(i).getTypeConfig().split("\\|")[1]+" "+fields.get(i).getTypeConfig().split("\\|")[0]+""
							+ " and "+fields.get(i).getTypeConfig().split("\\|")[2]+" =("+fields.get(i).getFictitiousSql()+")) as "+fields.get(i).getEn();
				}else {
					sqlString += "("+fields.get(i).getFictitiousSql()+") as "+fields.get(i).getEn();
				}
			}else {
				if("select".equals(fields.get(i).getType()) || "radio".equals(fields.get(i).getType())) {
					sqlString += "(select "+fields.get(i).getTypeConfig().split("\\|")[1]+" "+fields.get(i).getTypeConfig().split("\\|")[0]+""
							+ " and "+fields.get(i).getTypeConfig().split("\\|")[2]+" = "+fields.get(i).getEn()+") as "+fields.get(i).getEn();
				}else {
					sqlString += fields.get(i).getEn();
				}
				
			}
		}
		sqlString += " from "+model.getTableName()+" t where 1=1 ";
		sqlString += model.getWhereAttr()!=null?model.getWhereAttr():"";
		JSONObject query = JSONObject.parseObject(get("queryForm"));
		if(query!=null) {
			for (int i = 0; i < fields.size(); i++) {
				if(fields.get(i).getIsQuery() && query.get(fields.get(i).getEn())!=null) {
					if(fields.get(i).getIsFictitious()) {
						sqlString += " and ("+fields.get(i).getConfig()+") like '%"+query.get(fields.get(i).getEn())+"%'";
					}else if("input".equals(fields.get(i).getType())) {
						sqlString += " and "+fields.get(i).getEn()+" like '%"+query.get(fields.get(i).getEn())+"%'";
					}else if("select".equals(fields.get(i).getType()) || "radio".equals(fields.get(i).getType())) {
						if(!"".equals(query.get(fields.get(i).getEn()))) {
							sqlString += " and "+fields.get(i).getEn()+" = "+query.get(fields.get(i).getEn());
						}
					}else if("date".equals(fields.get(i).getType()) || "datetime".equals(fields.get(i).getType())) {
						String dateRange = query.get(fields.get(i).getEn()).toString();
						String start = dateRange.split(",")[0].split("\"")[1];
						String end = dateRange.split(",")[1].split("\"")[1];
						sqlString += " and "+fields.get(i).getEn()+" >= '"+start+"'";
						sqlString += " and "+fields.get(i).getEn()+" <= '"+end+"'";
					}
				}
			}
		}
		if(get("son_id_field")!=null && !"".equals(get("son_id_field"))) {
			JSONObject row = JSONObject.parseObject(get("parent_row"));
			sqlString += " and "+get("son_id_field")+" = "+ row.getIntValue(get("parent_id_field"));
		}
		
		/**
		 * query before
		 */
		try {
			intercept = TemplateUtil.initMetaObjectIntercept(model.getInterceptor());
			if (intercept != null) {
				AopContext ac = new AopContext(ctrl);
				ac.sql = sqlString;
				sqlString = intercept.queryBefore(ac);
			}
		} catch (Exception e) {
			renderJson(Ret.fail("msg", e.getMessage()));
			e.printStackTrace();
			return;
		}
		
		SqlPara sql = new SqlPara();
		sql.setSql(sqlString);
		//获取数据
		List<Record> list = Db.find(sqlString);
		//初始化表头
		JSONObject body = JSONObject.parseObject(get("body"));
		JSONArray fieldsArray = body.getJSONArray("fields");
		Map<String, String> headerMap = new LinkedHashMap<>();
		for (int i = 0; i < fieldsArray.size(); i++) {
			JSONObject field = fieldsArray.getJSONObject(i);
			headerMap.put(field.getString("field"), field.getString("title"));
		}
		List<Map> dataMapList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> temp = list.get(i).getColumns();
			dataMapList.add(temp);
		}
		List<String> titles = new ArrayList(headerMap.values());
		List<String> orders = new ArrayList(headerMap.keySet());
		Workbook workbook = DefaultExcelBuilder.of(Map.class)
		        .sheetName("导出")
		        .titles(titles)
		        .widths(10,20)
		        .fieldDisplayOrder(orders)
		        .fixedTitles()
		        .build(dataMapList);
		String filename = body.getString("filename")+"_"+new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date())+".xlsx";
	    File file = new File(p.get("domin_path")+"/export/"+filename);
	    try {
			FileExportUtil.export(workbook,file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    Record resp = new Record();
	    resp.set("url", "/export/"+filename);
	    renderJson(resp);
	}
}
