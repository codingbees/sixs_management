/**
 * Copyright (c) 2020-2030, Ray. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.ray.common.interceptor;

import com.ray.common.config.MainConfig;
import com.ray.util.Commen;

public class TemplateUtil {

	/**
	 * 初始化业务拦截器
	 * 
	 * @param bizIntercept
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T initIntercept(String bizIntercept) throws Exception {
		if (!Commen.isEmptyy(bizIntercept)) {
			try {
				// 实例化自定义拦截
				return (T) Class.forName(bizIntercept).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("实例化拦截器异常，请检测类是否存在：" + bizIntercept);
			}
		}
		return null;
	}
	
	/**
	 * 初始化元对象拦截
	 * 
	 * @param bizIntercept
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T initMetaObjectIntercept(String bizIntercept) throws Exception {
		Object o = initIntercept(bizIntercept);
		if (o == null) {
			// 命中默认拦截
			return (T) MainConfig.getDefaultMetaObjectIntercept();
		}
		return (T)o;
	}
}