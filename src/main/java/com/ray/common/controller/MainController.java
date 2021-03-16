package com.ray.common.controller;

import com.jfinal.core.NotAction;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Redis;
import com.taobao.api.ApiException;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class MainController extends BaseController{
	
	/**
	 * 开发用
	 */
	public void a() throws ApiException{ 
			UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			Record user = Db.findFirst("select * from user where username = 'admin'");
			Record record = Db.findFirst("SELECT GROUP_CONCAT(role_name) AS roles FROM roles WHERE id IN (SELECT role_id FROM user_role WHERE user_id = '"+user.get("id")+"')");
			user.set("roles", record.getStr("roles"));
			subject.getSession().setAttribute("user", user);
			redirect("/index");
	}
	
	public void dingLogin(){
		render("dingLogin.html");
	}
	public void index(){
//		setAttr("menu", getMenu());
		set("request",getRequest());
		render("index.html");
	}

	public void loginInit() {
		if(getPara("code")!=null){
			setAttr("code", getPara("code"));
			setAttr("icon", getPara("icon"));
		}else{
			setAttr("code", 0);
			setAttr("icon", 1);
		}
		render("login.html");
	}
	
	public void login() {
		UsernamePasswordToken token = new UsernamePasswordToken(getPara("username"), getPara("password"));
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			Record user = Db.findFirst("select * from user where username = '" + getPara("username") + "'");
			Record record = Db.findFirst("SELECT GROUP_CONCAT(role_name) AS roles FROM roles WHERE id IN (SELECT role_id FROM user_role WHERE user_id = '"+user.get("id")+"')");
			user.set("roles", record.getStr("roles"));
			subject.getSession().setAttribute("user", user);
			renderJson(Ret.ok("msg", "登录成功"));
		} catch (IncorrectCredentialsException ice) {
			renderJson(Ret.fail("msg", "密码错误"));
		} catch (UnknownAccountException uae) {
			renderJson(Ret.fail("msg", "用户不存在"));
		} catch (ExcessiveAttemptsException eae) {
			renderJson(Ret.fail("msg", "错误登录过多"));
		}
	}

	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		setAttr("code", 0);
		setAttr("icon", 1);
		render("login.html");
	}

	public void mainInfo() {
		render("sys/home/main.html");
	}
	
	public void menus() {
		try {
			List<Record> top_menu = Db.find("select * from menu where parent_menu = 0 and is_hide = 0 order by seq_num");
			top_menu = menuAuth(top_menu);
			List<Record> menu = getMenus(top_menu);
			renderJson(Ret.ok("menus", menu));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(Ret.fail("msg", "失败：" + e.getMessage()));
		}
	}

	/**
	 * 递归获取菜单
	 * @param menus
	 * @return
	 */
	public List<Record> getMenus(List<Record> menus){
		for (int i = 0; i < menus.size(); i++) {
			List<Record> children_menu = Db.find("select * from menu where parent_menu = "+menus.get(i).getInt("id")+" and is_hide = 0 order by seq_num");
			if(children_menu.size()>0) {
				children_menu = getMenus(menuAuth(children_menu));
			}
			menus.get(i).set("children", menuAuth(children_menu));
		}
		return menus;
	}
//	public List<Record> getMenu(){
//		List<Record> top_menu = Db.find("select * from menu where menu_level = 1 and is_hide = 0 order by seq_num");
//		top_menu = menuAuth(top_menu);
//		for (int i = 0; i < top_menu.size(); i++) {
//			List<Record> second_menu = Db.find("select * from menu where menu_level = 2 and is_hide = 0 and parent_menu = "
//				+ ((Record) top_menu.get(i)).getInt("id") + " order by seq_num");
//			second_menu = menuAuth(second_menu);
//			for (int j = 0; j < second_menu.size(); j++) {
//				List<Record> third_menu = Db.find("select * from menu where menu_level = 3 and is_hide=0 and parent_menu = "
//					+ ((Record) second_menu.get(j)).getInt("id") + " order by seq_num");
//				third_menu = menuAuth(third_menu);
//				if (third_menu.size() > 0) {
//					((Record) second_menu.get(j)).set("children", third_menu);
//				}
//			}
//			if (second_menu.size() > 0) {
//				((Record) top_menu.get(i)).set("children", second_menu);
//			}
//		}
//		return top_menu;
//	}
	
	@NotAction
	public List<Record> menuAuth(List<Record> menu){
		Record user = (Record)getSessionAttr("user");
		String sql = "SELECT gl_id FROM permissions WHERE id IN (SELECT permission_id FROM role_permission WHERE role_id IN (SELECT role_id FROM user_role WHERE user_id = '"
				+ user.get("id") + "')) AND TYPE = 1";
		List<Record> menuPermissions = Db.find(sql);
		for (int i = 0; i < menu.size(); i++) {
			boolean flag = true;
			for (int j = 0; j < menuPermissions.size(); j++) {
				if (((Record) menu.get(i)).get("id").equals(((Record) menuPermissions.get(j)).get("gl_id"))) {
					flag = false;
				}
			}
			if (flag) {
				menu.remove(i);
				i--;
			}
		}
		return menu;
	}

	@NotAction
	public static void main(String[] args) {
		Redis.use("test").lpush("ray", new Object[] { "1" });
	}
}