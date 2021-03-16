package com.ray.common.shiro;

/**
 * 自定义权限控制类
 * @author Ray
 *
 */
public class CustomAuth {
	
	/**
	 * 判断是否包含
	 * @return
	 */
	public static boolean contain(String a,String b){
		if(a!=null && !"".equals(a)){
			if(a.contains(b)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
