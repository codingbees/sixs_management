package com.ray.common.shiro;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

public class ShiroDbRealm extends AuthorizingRealm {

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Record user = Db.findFirst("select * from user where username = ?", new Object[] { token.getUsername() });
		if (user != null) {
			return new SimpleAuthenticationInfo(user.get("username"), user.get("password"), getName());
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Collection<String> roleNameList = new ArrayList();
		Collection<String> permissionNameList = new ArrayList();
		String loginName = (String) principals.fromRealm(getName()).iterator().next();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<Record> roleList = Db.find("select * from user_role where user_id = ?", new Object[] { loginName });
		for (int i = 0; i < roleList.size(); i++) {
			Record role = Db.findById("roles", ((Record) roleList.get(i)).get("role_id"));
			roleNameList.add(role.getStr("role_name"));
			List<Record> permissionList = Db.find("select * from role_permission where role_id = ?",
					new Object[] { ((Record) roleList.get(i)).getInt("role_id") });
			for (int j = 0; j < permissionList.size(); j++) {
				Record permission = Db.findById("permissions",
						((Record) permissionList.get(j)).getInt("permission_id"));

				if (permission.getInt("type").intValue() == 2) {
					permissionNameList.add(permission.getStr("permission_name"));
				}
			}
		}
		info.addRoles(roleNameList);
		info.addStringPermissions(permissionNameList);
		return info;
	}

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
}

/*
 * Location:
 * C:\Users\Ray\Desktop\ERP.war!\WEB-INF\classes\com\ray\shiro\ShiroDbRealm.
 * class Java compiler version: 7 (51.0) JD-Core Version: 0.7.1
 */