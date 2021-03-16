package com.ray.common.interceptor;

/**
 * 公共元对象业务拦截器
 * <pre>
 * 使用场景:
 * 全局业务 例:增删改查日志记录
 * 高频字段统一处理 例:create_time update_time ...
 * 其它更多高端玩法,请尽情的发挥想象吧!
 * </pre>
 * 
 * @author ray
 * @date 2017-7-15
 *
 */
public class BaseMetaObjectIntercept extends MetaObjectIntercept {

	@Override
	public String updateBefore(AopContext ac) throws Exception {
		System.out.println("公共元对象业务拦截器");
		return null;
	}

}
