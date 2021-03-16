package com.ray.common.controller.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.json.FastJson;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.ray.common.controller.BaseController;
import com.ray.common.interceptor.AopContext;
import com.ray.common.interceptor.MetaObjectIntercept;
import com.ray.common.interceptor.TemplateUtil;
import com.ray.common.model.DataButton;
import com.ray.common.model.DataField;
import com.ray.common.model.DataObject;
import com.ray.common.model.Menu;
import com.ray.util.Commen;

/**
 * 主子表增删改查模板
 * @author Ray
 * @contact 461812883@qq.com
 * @time 2020年9月3日 下午3:26:36
 */
public class ZZController extends BaseController {

	final Controller ctrl = this;
	protected MetaObjectIntercept intercept = null;

//	/**
//	 * 跳转主页
//	 */
//	public void zz() {
//		Menu menu = Menu.dao.findById(get("id"));
//		set("data_object",DataObject.dao.findById(menu.getDataObjectId()));
//		set("menu", menu);
//		if(!"null".equals(get("url")) && !"null".equals(get("url"))) {
//			render(get("url"));
//		}
//	}

	/**
	 * 获取菜单信息
	 * @param id:菜单ID
	 * @author Ray
	 * @contact 461812883@qq.com
	 * @time 2020年9月3日 下午3:24:22
	 */
	public void getMenuInfo() {
		Menu model = Menu.dao.findById(get("id"));
		String domin_url = PropKit.get("domin_url");
		Record r = new Record();
		r.set("menu", model);
		r.set("domin_url", domin_url);
		renderJson(r);
	}

	/**
	 * 获取数据库字段表头
	 * @author Ray
	 * @contact 461812883@qq.com
	 * @time 2020年9月3日 下午3:24:44
	 */
	public void getColumns() {
		Record resp = new Record();
		List<DataField> list = DataField.dao
				.find("select * from data_field where data_object_id = " + get("object_id") + " order by order_num");
		resp.set("list", list);
		// 是否显示查询按钮
		boolean is_query = false;
		List<List<Record>> map = new ArrayList<List<Record>>();
		Record validator = new Record();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIsQuery()) {
				is_query = true;
			}
			List<Record> temp = new ArrayList<Record>();
			if (list.get(i).getType().equals("select") || list.get(i).getType().equals("radio")) {
				String sql = "select " + list.get(i).getTypeConfig().split("\\|")[1] + " as label,"
						+ list.get(i).getTypeConfig().split("\\|")[2] + " as value "
						+ list.get(i).getTypeConfig().split("\\|")[0];
				
				if (list.get(i).getTypeConfig().split("\\|").length >= 4) {
					if (list.get(i).getTypeConfig().split("\\|")[0].indexOf("where") == -1) {
						sql += " where 1=1";
					}
					String[] query = list.get(i).getTypeConfig().split("\\|")[3].split(",");
					Record user = (Record) getSessionAttr("user");
					for (int j = 0; j < query.length; j++) {
						sql += " and " + query[j].split(":")[0] + user.get(query[j].split(":")[1]);
					}
				}
				temp = Db.find(sql);
			}
			map.add(temp);
			// 获取字段前端校验
			JSONArray JA = JSONArray.parseArray(list.get(i).getValidator());
			validator.set(list.get(i).getEn(), JA);
		}
		// 获取头部buttons
		List<DataButton> headButtons = DataButton.dao.find("select * from data_button where data_object_id = "
				+ get("object_id") + " and location=1 order by order_num");
		resp.set("headButtons", headButtons);
		// 获取行内buttons
		List<DataButton> lineButtons = DataButton.dao.find("select * from data_button where data_object_id = "
				+ get("object_id") + " and location=2 order by order_num");
		resp.set("lineButtons", lineButtons);
		resp.set("data_object", DataObject.dao.findById(get("object_id")));
		resp.set("selectMap", map);
		resp.set("validator", validator);
		resp.set("is_query", is_query);
		renderJson(resp);
	}

	/**
	 * 获取主表数据
	 * @author Ray
	 * @contact 461812883@qq.com
	 * @time 2020年9月3日 下午3:27:58
	 */
	public void getData() {
		SqlPara sql = new SqlPara();
		DataObject model = DataObject.dao.findById(get("object_id"));
		List<DataField> fields = DataField.dao
				.find("select * from data_field where data_object_id = " + get("object_id"));
		String sqlString = "select ";
		for (int i = 0; i < fields.size(); i++) {
			if (i > 0) {
				sqlString += ",";
			}
			if (fields.get(i).getIsFictitious()) {
				sqlString += "(" + fields.get(i).getFictitiousSql() + ") as " + fields.get(i).getEn();
			} else {
				sqlString += fields.get(i).getEn();
			}
		}
		sqlString += " from " + model.getTableName() + " t where 1=1 ";
		sqlString += model.getWhereAttr() != null ? model.getWhereAttr() : "";
		JSONObject query = JSONObject.parseObject(get("queryForm"));
		if (query != null) {
			for (int i = 0; i < fields.size(); i++) {
				if (fields.get(i).getIsQuery() && query.get(fields.get(i).getEn()) != null) {
					if (fields.get(i).getIsFictitious()) {
						sqlString += " and (" + fields.get(i).getFictitiousSql() + ") like '%"
								+ query.get(fields.get(i).getEn()) + "%'";
					} else if ("input".equals(fields.get(i).getType())) {
						sqlString += " and " + fields.get(i).getEn() + " like '%" + query.get(fields.get(i).getEn())
								+ "%'";
					} else if ("select".equals(fields.get(i).getType()) || "radio".equals(fields.get(i).getType())) {
						if (!"".equals(query.get(fields.get(i).getEn()))) {
							sqlString += " and " + fields.get(i).getEn() + " = " + query.get(fields.get(i).getEn());
						}
					} else if ("date".equals(fields.get(i).getType()) || "datetime".equals(fields.get(i).getType())) {
						String dateRange = query.get(fields.get(i).getEn()).toString();
						String start = dateRange.split(",")[0].split("\"")[1];
						String end = dateRange.split(",")[1].split("\"")[1];
						sqlString += " and " + fields.get(i).getEn() + " >= '" + start + "'";
						sqlString += " and " + fields.get(i).getEn() + " <= '" + end + "'";
					}
				}
			}
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

		sql.setSql(sqlString);
		Page<Record> list = Db.paginate(getInt("currentPage"), getInt("pageSize"), sql);
		Record resp = new Record();
		resp.set("list", list.getList());
		resp.set("totalResult", list.getTotalRow());
		renderJson(resp);
	}

	/**
	 * 获取子表数据
	 * @author Ray
	 * @contact 461812883@qq.com
	 * @time 2020年9月3日 下午3:28:07
	 */
	public void getSonData() {
		try {
			Menu menu = Menu.dao.findById(get("menu_id"));
			JSONObject row = JSONObject.parseObject(get("row"));
			SqlPara sql = new SqlPara();
			DataObject model = DataObject.dao.findById(menu.getSonDataObjectId());
			List<DataField> fields = DataField.dao
					.find("select * from data_field where data_object_id = " + menu.getSonDataObjectId());
			String sqlString = "select ";
			for (int i = 0; i < fields.size(); i++) {
				if (i > 0) {
					sqlString += ",";
				}
				if (fields.get(i).getIsFictitious()) {
					sqlString += "(" + fields.get(i).getFictitiousSql() + ") as " + fields.get(i).getEn();
				} else {
					sqlString += fields.get(i).getEn();
				}
			}
			sqlString += " from " + model.getTableName() + " t where " + menu.getSonIdField() + " = "
					+ row.getIntValue(menu.getParentIdField()) + " ";
			sqlString += model.getWhereAttr() != null ? model.getWhereAttr() : "";

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

			sql.setSql(sqlString);
			List<Record> list = Db.find(sql);
			renderJson(Ret.ok("list", list));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(Ret.fail("msg", "失败：" + e.getMessage()));
		}
	}

	/**
	 * 新增数据
	 * @author Ray
	 * @contact 461812883@qq.com
	 * @time 2020年9月3日 下午3:25:55
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void new_() {
		try {
			DataObject object = DataObject.dao.findById(get("object_id"));
			Map map = FastJson.getJson().parse(get("form"), Map.class);
			Record model = new Record().setColumns(map);

			/**
			 * 上下文
			 */
			AopContext ac = new AopContext(ctrl, model);
			ac.object = object;
			intercept = TemplateUtil.initMetaObjectIntercept(object.getInterceptor());

			/**
			 * add before
			 */
			if (intercept != null) {
				String msg = intercept.addBefore(ac);
				if (!Commen.isEmptyy(msg)) {
					renderJson(Ret.fail("msg", msg));
					return;
				}
			}

			if ("son".equals(get("type"))) {
				Menu menu = Menu.dao.findById(get("menu_id"));
				JSONObject parent = JSONObject.parseObject(get("parent"));
				model.set(menu.getSonIdField(), parent.getIntValue(menu.getParentIdField()));
			}
			// 处理附件
			String[] fileArray = getParaValues("fileList[]");
			if (fileArray != null) {
				DataField file_field = new DataField();
				String fileIds = "";
				for (int i = 0; i < fileArray.length; i++) {
					JSONObject jb = JSONObject.parseObject(fileArray[i]);
					if (!"".equals(jb.getString("response")) && jb.getString("response") != null) {
						JSONObject response = JSONObject.parseObject(jb.getString("response"));
						File file = new File("D:/raybase/temp/" + response.getString("fileName"));
						file_field = DataField.dao.findFirst("select * from data_field where data_object_id = "
								+ get("object_id") + " and en='" + response.getString("column") + "'");
						File newfile = new File("D:/raybase/" + file_field.getTypeConfig().split("\\|")[0] + "/"
								+ UUID.randomUUID() + "." + response.getString("fileName").split("\\.")[1]);
						File fileParent = newfile.getParentFile();
						// 判断文件夹是否存在
						if (!fileParent.exists()) {
							fileParent.mkdirs();
						}
						try {
							// 从temp文件夹移动到正式文件夹
							if (file.renameTo(newfile)) {
								Record fileRecord = new Record();
								fileRecord.set("name", response.getString("fileName"));
								fileRecord.set("url",
										"/" + file_field.getTypeConfig().split("\\|")[0] + "/" + newfile.getName());
								Db.save("file", fileRecord);
								fileIds += fileRecord.getStr("id") + ",";
							} else {
								renderJson(Ret.fail("msg", "新增失败，原因：文件处理失败"));
								return;
							}
						} catch (Exception e) {
							e.printStackTrace();
							renderJson(Ret.fail("msg", "新增失败，原因：" + e));
							return;
						}
					}
				}
				model.set(file_field.getEn(), fileIds.substring(0, fileIds.length() - 1));
			}
			boolean add = Db.tx(() -> {
				Db.save(object.getTableName(), model);
				/**
				 * add after
				 */
				if (intercept != null) {
					try {
						String msg = intercept.addAfter(ac);
						if (!Commen.isEmptyy(msg)) {
							renderJson(Ret.fail("msg", msg));
							return false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return true;
			});
			if (add) {
				/**
				 * add succeed
				 */
				if (intercept != null) {
					try {
						String msg = intercept.addSucceed(ac);
						if (!Commen.isEmptyy(msg)) {
							renderJson(Ret.ok("msg", msg));
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				renderJson(Ret.ok("msg", "新增成功！"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(Ret.fail("msg", e.getMessage()));
		}
	}

	/**
	 * 修改数据
	 * @author Ray
	 * @contact 461812883@qq.com
	 * @time 2020年9月3日 下午3:26:06
	 */
	@SuppressWarnings("unchecked")
	public void edit() {
		try {
			DataObject object = DataObject.dao.findById(get("object_id"));
			Record temp = new Record().setColumns(FastJson.getJson().parse(get("row"), Map.class));
			Record model = new Record();
			List<Record> list = Db.use("sys")
					.find("select * from columns where table_schema = '" + PropKit.get("table_schema")
							+ "' and table_name = '" + object.getTableName() + "' order by ORDINAL_POSITION");
			for (int i = 0; i < list.size(); i++) {
				model.set(list.get(i).getStr("COLUMN_NAME"), temp.get(list.get(i).getStr("COLUMN_NAME")));
			}

			/**
			 * 上下文
			 */
			AopContext ac = new AopContext(ctrl, model);
			ac.object = object;
			intercept = TemplateUtil.initMetaObjectIntercept(object.getInterceptor());

			/**
			 * update before
			 */
			String msg = intercept.updateBefore(ac);
			if (!Commen.isEmptyy(msg)) {
				renderJson(Ret.fail("msg", msg));
				return;
			}

			boolean update = Db.tx(() -> {
				Db.update(object.getTableName(), model);
				/**
				 * update after
				 */
				if (intercept != null) {
					try {
						String msg1 = intercept.updateAfter(ac);
						if (!Commen.isEmptyy(msg1)) {
							renderJson(Ret.fail("msg", msg1));
							return false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return true;
			});
			if (update) {
				/**
				 * update succeed
				 */
				if (intercept != null) {
					try {
						String msg2 = intercept.updateSucceed(ac);
						if (!Commen.isEmptyy(msg2)) {
							renderJson(Ret.ok("msg", msg2));
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			renderJson(Ret.ok("msg", "修改成功！"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(Ret.fail("msg", e.getMessage()));
		}
	}

	/**
	 * 删除数据
	 * @author Ray
	 * @contact 461812883@qq.com
	 * @time 2020年9月3日 下午3:26:19
	 */
	public void del() {
		try {
			DataObject object = DataObject.dao.findById(get("object_id"));
			/**
			 * 上下文
			 */
			AopContext ac = new AopContext(ctrl, Db.findById(object.getTableName(), get("id")));
			ac.object = object;
			intercept = TemplateUtil.initMetaObjectIntercept(object.getInterceptor());

			/**
			 * delete before
			 */
			String msg = intercept.deleteBefore(ac);
			if (!Commen.isEmptyy(msg)) {
				renderJson(Ret.fail("msg", msg));
				return;
			}

			boolean delete = Db.tx(() -> {
				Db.deleteById(object.getTableName(), get("id"));
				/**
				 * delete after
				 */
				if (intercept != null) {
					try {
						String msg1 = intercept.deleteAfter(ac);
						if (!Commen.isEmptyy(msg1)) {
							renderJson(Ret.fail("msg", msg1));
							return false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return true;
			});
			if (delete) {
				/**
				 * delete succeed
				 */
				if (intercept != null) {
					try {
						String msg2 = intercept.deleteSucceed(ac);
						if (!Commen.isEmptyy(msg2)) {
							renderJson(Ret.ok("msg", msg2));
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			renderJson(Ret.ok("msg", "删除成功！"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(Ret.fail("msg", e.getMessage()));
		}
	}
}
