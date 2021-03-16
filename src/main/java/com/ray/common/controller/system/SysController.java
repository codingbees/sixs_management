package com.ray.common.controller.system;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.jfinal.aop.Before;
import com.jfinal.core.NotAction;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ray.common.model.UserRole;
import com.ray.common.model.Roles;
import com.ray.common.controller.BaseController;
import com.ray.common.model.DataObject;
import com.ray.common.model.Menu;
import java.util.List;

/**
 * @author Ray
 *
 */
/**
 * @author Ray
 *
 */
public class SysController extends BaseController
{
  public void user()
  {
    render("user.html");
  }
  

  public void getUser()
  {
    String username = getPara("username");
    String nickname = getPara("nickname");
    String strSql = "select * from user where 1=1";
    if ((username != null) && (!"".equals(username))) {
      strSql = strSql + " and username like '%" + username + "%'";
    }
    if ((nickname != null) && (!"".equals(nickname))) {
      strSql = strSql + " and nickname like '%" + nickname + "%'";
    }
    SqlPara sql = new SqlPara();
    sql.setSql(strSql);
    Page<Record> users = Db.paginate(getParaToInt("page").intValue(), getParaToInt("limit").intValue(), sql);
    List<Record> userList = users.getList();
    
    for (int i = 0; i < userList.size(); i++) {
      StringBuffer roles = new StringBuffer();
      List<Record> roleIds = Db.find("select role_id from user_role where user_id = " + ((Record)userList.get(i)).get("id"));
      for (int j = 0; j < roleIds.size(); j++) {
        Record role = Db.findById("roles", ((Record)roleIds.get(j)).get("role_id"));
        roles.append(role.getStr("role_nick_name"));
        roles.append(",");
      }
      ((Record)userList.get(i)).set("roles", roles.toString().substring(0, roles.toString().length() - 1));
    }
    Record layTableRes = new Record();
    layTableRes.set("code", Integer.valueOf(0));
    layTableRes.set("msg", "");
    layTableRes.set("count", Integer.valueOf(users.getTotalRow()));
    layTableRes.set("data", users.getList());
    renderJson(layTableRes);
  }
  
  public void userform() {
    int userId = getParaToInt(0).intValue();
    setAttr("user", new Record().set("id", Integer.valueOf(0)));
    if (userId != 0) {
      Record user = Db.findById("user", Integer.valueOf(userId));
      setAttr("user", user);
      Record roles = Db.findFirst("SELECT GROUP_CONCAT(role_id) role_ids FROM user_role WHERE user_id = '" + userId+"'");
      setAttr("roleIds", roles.get("role_ids"));
    }
    render("userform.html");
  }
  


  public void getSelectRole()
  {
    List<Record> roles = Db.find("select id,role_nick_name name from roles");
    renderJson(roles);
  }
  


  @Before({Tx.class})
  public void userUpdate()
  {
    String userId = getPara("userid");
    
    Db.delete("delete from user_role where user_id = '" + userId+"'");
    
    String[] roleIdArray = getPara("rolesId").split(",");
    for (int i = 0; i < roleIdArray.length; i++) {
      Record user_role = new Record();
      user_role.set("role_id", roleIdArray[i]);
      user_role.set("user_id", userId);
      Db.save("user_role", user_role);
    }
    Record rsp = new Record();
    rsp.set("code", Integer.valueOf(0));
    rsp.set("msg", "员工角色修改成功");
    renderJson(rsp);
  }
  
  public void role() {
    render("role.html");
  }
  
  public void roleform() {
    int roleId = getParaToInt(0).intValue();
    setAttr("role", new Record().set("id", Integer.valueOf(0)));
    if (roleId != 0) {
      Record role = Db.findById("roles", Integer.valueOf(roleId));
      setAttr("role", role);
    }
    render("roleform.html");
  }
  
  public void getRole() {
	  Record resp = new Record();
		SqlPara sqlPara = new SqlPara();
		sqlPara.setSql("select * from roles");
		Page<Roles> list = Roles.dao.paginate(getCurrentPage(), getPageSize(), sqlPara);
		resp.set("list", list.getList());
		resp.set("totalPage", list.getTotalPage());
		renderJson(resp);
  }
  
  /**
	 * 获取角色人员穿梭框列表
	 */
	public void getRoleUser(){
		Record resp = new Record();
		List<Record> list = Db.find("select * from user");
		List<Record> selectList = Db.find("select * from user_role where role_id = "+get("role_id"));
		int[] select = new int[selectList.size()];
		for (int i = 0; i < selectList.size(); i++) {
			select[i] = selectList.get(i).get("user_id");
		}
		resp.set("list", list);
		resp.set("select", select);
		renderJson(resp);
	}
	
	/**
	 * 设置角色人员
	 */
	public void setUserRole() {
		Record res = new Record();
		try {
			//先清空当前角色所有人员
			List<UserRole> list = UserRole.dao.find("select * from user_role where role_id = "+get("role_id"));
			for (int i = 0; i < list.size(); i++) {
				list.get(i).delete();
			}
			Integer[] user_ids= getParaValuesToInt("user_ids[]");
			if(user_ids!=null) {
				for (int i = 0; i < user_ids.length; i++) {
					UserRole ur = new UserRole();
					ur.setUserId(user_ids[i]);
					ur.setRoleId(getInt("role_id"));
					ur.save();
				}
			}
			res.set("msg", "设置成功！");
			res.set("code", 1);
		} catch (Exception e) {
			e.getMessage();
			res.set("code", 0);
			res.set("msg", "设置失败，原因："+e);
		}
		renderJson(res);
	}
  
  public void getMenuTree() {
    Record rsp = new Record();
    rsp.set("code", Integer.valueOf(0));
    rsp.set("msg", "获取成功");
    Record trees = new Record();
    trees.set("trees", getMenu(getParaToInt(0).intValue()));
    rsp.set("data", trees);
    renderJson(rsp);
  }
  


  @Before({Tx.class})
  public void roleUpdate()
  {
    String menuIds = getPara("role_menu_auth");
    int roleId = getParaToInt("role_id").intValue();
    Record role = new Record();
    if (roleId != 0) {
      role = Db.findById("roles", Integer.valueOf(roleId));
    }
    role.set("role_name", getPara("role_name"));
    role.set("role_nick_name", getPara("role_nick_name"));
    role.set("role_desc", getPara("role_desc"));
    if (roleId != 0) {
      Db.update("roles", role);
    } else {
      Db.save("roles", role);
    }
    
    clearMenuAuth(role.getInt("id").intValue());
    
    String[] menuIdArray = menuIds.split(",");
    for (int i = 0; i < menuIdArray.length; i++) {
      Record permission = new Record();
      permission.set("type", Integer.valueOf(1));
      permission.set("gl_id", menuIdArray[i]);
      Db.save("permissions", permission);
      Record rolePermission = new Record();
      rolePermission.set("role_id", role.getInt("id"));
      rolePermission.set("permission_id", permission.get("id"));
      Db.save("role_permission", rolePermission);
    }
    Record rsp = new Record();
    rsp.set("code", Integer.valueOf(0));
    rsp.set("msg", "角色及权限新增或修改成功");
    renderJson(rsp);
  }
			/**
			 * 获取已经拥有该角色的人员
			 */
			public void getRoleUsers(){
				Record rsp = new Record();
				try {
					int roleId = getInt("role_id");
					List<Record> roleUsers = Db.find("select user_id from user_role where role_id = "+roleId);
					String[] rspUserArray = new String[roleUsers.size()];
					for (int i = 0; i < roleUsers.size(); i++) {
						rspUserArray[i] = roleUsers.get(i).getStr("user_id");
					}
					System.out.println(rspUserArray);
					rsp.set("code", Integer.valueOf(0));
					rsp.set("msg", "获取角色成员成功");
					rsp.set("rspUserArray", rspUserArray);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					rsp.set("code", Integer.valueOf(200));
					rsp.set("msg", "获取角色成员失败");
				}
				renderJson(rsp);
			}

			/**
			 * 重新设置该角色人员
			 */
			public void roleSetUser(){
				Record rsp = new Record();
				try {
					int roleId = getInt("role_id");
					Db.delete("delete from user_role where role_id = "+roleId);
					JSONArray users = JSONArray.parseArray(get("users"));
					for (int i = 0; i < users.size(); i++) {
						JSONObject user = users.getJSONObject(i);
						Record newUserRole = new Record();
						newUserRole.set("user_id", user.getString("emplId"));
						newUserRole.set("role_id", roleId);
						Db.save("user_role", newUserRole);
					}
					rsp.set("code", Integer.valueOf(0));
					rsp.set("msg", "设置人员角色成功");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					rsp.set("code", Integer.valueOf(200));
					rsp.set("msg", "设置人员角色失败");
				}
				renderJson(rsp);
			}
  

  @NotAction
  public void clearMenuAuth(int roleId)
  {
    List<Record> permissions = Db.find("SELECT * FROM permissions WHERE TYPE = 1 AND id IN (SELECT permission_id FROM role_permission WHERE role_id = " + roleId + ")");
    for (int i = 0; i < permissions.size(); i++) {
      int perId = ((Integer)((Record)permissions.get(i)).get("id")).intValue();
      Db.deleteById("permissions", Integer.valueOf(perId));
      Db.delete("delete from role_permission where role_id = ? and permission_id = ?", new Object[] { Integer.valueOf(roleId), Integer.valueOf(perId) });
    }
  }
  


  @NotAction
  public List<Record> getMenu(int roleId)
  {
    List<Record> top_menu = Db.find("select * from menu where menu_level = 1 and is_hide = 0");
    top_menu = menuAuth(top_menu, roleId);
    for (int i = 0; i < top_menu.size(); i++) {
      List<Record> second_menu = Db.find("select * from menu where menu_level = 2 and is_hide = 0 and parent_menu = " + ((Record)top_menu.get(i)).getInt("id") + " order by seq_num");
      second_menu = menuAuth(second_menu, roleId);
      /*for (int j = 0; j < second_menu.size(); j++) {
        List<Record> third_menu = Db.find("select * from menu where menu_level = 3 and is_hide=0 and parent_menu = " + ((Record)second_menu.get(j)).getInt("id") + " order by seq_num");
        third_menu = menuAuth(third_menu, roleId);
        if (third_menu.size() > 0) {
          ((Record)second_menu.get(j)).set("list", third_menu);
        }
      }*/
      if (second_menu.size() > 0) {
        ((Record)top_menu.get(i)).set("list", second_menu);
      }
    }
    return top_menu;
  }
  


  @NotAction
  public List<Record> menuAuth(List<Record> menu, int roleId)
  {
    String sql = "";
    if (roleId != 0) {
      sql = "SELECT gl_id FROM permissions WHERE id IN (SELECT permission_id FROM role_permission WHERE role_id = " + roleId + ") AND TYPE = 1";
    } else {
      sql = "SELECT gl_id FROM permissions WHERE id IN (SELECT permission_id FROM role_permission) AND TYPE = 1";
    }
    List<Record> menuPermissions = Db.find(sql);
    for (int i = 0; i < menu.size(); i++) {
      Record thismenu = menu.get(i);
      boolean flag = false;
      for (int j = 0; j < menuPermissions.size(); j++) {
    	  Record thisPermissions = menuPermissions.get(j);
        if (thismenu.get("id").equals(thisPermissions.get("gl_id"))) {
          flag = true;
        }
      }
      if (flag) {
    	  thismenu.set("checked", Boolean.valueOf(true));
      } else {
    	  thismenu.set("checked", Boolean.valueOf(false));
      }
      thismenu.set("name", thismenu.get("title"));
      thismenu.set("value", thismenu.get("id"));
    }
    return menu;
  }
  


  public void menu()
  {
    render("menu.html");
  }
  


  public void getTreeMenu()
  {
    List<Record> menu = Db.find("select * from menu order by seq_num");
    Record rsp = new Record();
    rsp.set("code", Integer.valueOf(0));
    rsp.set("count", Integer.valueOf(menu.size()));
    rsp.set("data", menu);
    renderJson(rsp);
  }
  


  public void menuAddOrEdit()
  {
	  List<DataObject> list = DataObject.dao.findAll();
	  set("data_object", list);
    int menuId = getParaToInt(0).intValue();
    int parent_menu = getParaToInt(1).intValue();
    setAttr("menu", new Record());
    if (menuId != 0) {
      Record menu = Db.findById("menu", Integer.valueOf(menuId));
      setAttr("menu", menu);
    }
    if (parent_menu == 0) {
      setAttr("menu_level", Integer.valueOf(1));
    } else {
      Record parentMenu = Db.findById("menu", Integer.valueOf(parent_menu));
      setAttr("menu_level", Integer.valueOf(parentMenu.getInt("menu_level").intValue() + 1));
    }
    setAttr("parent_menu", Integer.valueOf(parent_menu));
    render("menuform.html");
  }
  


  public void doMenuAddOrEdit()
  {
    Menu menu = (Menu)getModel(Menu.class, "");
    menu.set("is_hide", Integer.valueOf(0));
    if (menu.getInt("id") != null) {
      menu.update();
    } else {
      menu.save();
    }
    Record rsp = new Record();
    rsp.set("code", Integer.valueOf(0));
    rsp.set("msg", "添加或修改菜单成功");
    renderJson(rsp);
  }
  


  public void delMenu()
  {
    deleteMenu(getParaToInt("id").intValue());
    Record rsp = new Record();
    rsp.set("code", Integer.valueOf(0));
    rsp.set("msg", "删除菜单及子菜单成功");
    renderJson(rsp);
  }
  

  @NotAction
  public void deleteMenu(int parent_menu)
  {
    List<Menu> sonMenus = Menu.dao.find("select * from menu where parent_menu = " + parent_menu);
    for (int i = 0; i < sonMenus.size(); i++) {
      deleteMenu(((Menu)sonMenus.get(i)).getInt("id").intValue());
    }
    Menu.dao.deleteById(Integer.valueOf(parent_menu));
  }
}

