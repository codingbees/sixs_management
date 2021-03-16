package com.ray.common.interceptor;

import com.jfinal.kit.Kv;

/**
 * <pre>
 * 元对象拦截器
 *
 * 背景：业务不仅仅是单表的增删改查
 * 作用：对元对象持久化进行拦截切入，从而进行业务能力拓屿
 *
 * AOP如何弹窗提示＿
 * return null;// 逻辑正常，没有消息就是好消息
 * return info("弹出丿个提示消恿");
 * return warn("弹出丿个警告消恿");
 * return error("弹出丿个错误消恿");
 * return "弹出丿个默认提礿";
 * throw new Exception("抛出丿个业务异常！＿");// 事务回滚
 * </pre>
 *
 * @author ray
 * @date 2017-7-15
 */
public class MetaObjectIntercept {

    /**
	 * 查询前置任务(DIY复杂查询条件)
	 * 
	 * <pre>
	 * 用法：获取查询条件忿
	 * ac.ctrl.getPara("query_查询条件字段吿");
	 *
	 * 用法：追加条仿
	 * ac.condition = "and id < ?";
	 * ac.params.add(999);
	 *
	 * 用法：覆盖条仿
	 * ac.where = "where id < ?";
	 * ac.params.add(5);
	 *
	 * 用法：覆盖排庿
	 * ac.sort = "order by id desc";
	 * 
	 * 用法：覆盖整个SQL语句
	 * ac.sql = "select * from table";
	 * </pre>
	 */
    public String queryBefore(AopContext ac) throws Exception {
    	return ac.sql;
    }

    /**
     * 查询后置任务
     * <pre>
     * ac.records    获取查询数据集合
     * -------------
     * // 遍历数据集，进行数据操作
     * for (Record x : ac.records){
     *
     *    x.set("total", x.getInt("a") + x.getInt("b"));// 动濁计算，total为虚拟字殿
     *    x.set("price", String.format("%.2f", price)); // RMB格式匿
     *
     * }
     * </pre>
     */
    public void queryAfter(AopContext ac) throws Exception {
    }

	/**
	 * 查询汇濻合计行
	 * <pre>
	 * 
	 * ac.records    获取查询数据集合
	 * -------------
	 * double sum = 0;
	 * for (Record e : ac.records) {
	 *     sum += e.getDouble("num");
	 * }
	 * return new Kv().set("Grid的某刿", "汇濿:(单位/兿)").set("Grid的某刿", sum);
	 * 
	 * 友情提示＿
	 * 元字段格式化也会命中footer单元栿,如出现NaN 说明是JS计算异常,请濝迃每行的小计是利用格式化JS实现,还是AOP+虚拟字段实现?
	 * DIY_JS 利用 grid.datagrid('getData').rows;->计算吿->grid.datagrid('appendRow',{'某列', '汇濿', '某列', 100}); 也可以实现底部汇总行
	 * </pre>
	 */
	public Kv queryFooter(AopContext ac) throws Exception {
		return null;
	}

    /**
     * 新增页初始化
     * <pre>
     * ac.fixed        当前操作对象固定初始倿
     * -------------
     * 用法：初始化默认倿
     * ac.fixed("reply", "您好＿");// 回复内容给统丿前缀
     * </pre>
     */
    public void addInit(AopContext ac) throws Exception {
    	
    }

    /**
	 * 新增前置任务(事务冿)
	 * 
	 * <pre>
	 * ac.record 当前操作数据
	 * -------------
	 * 用法：自动赋倿
	 * ac.record.set("create_time", TimestampUtil.getNow());
	 * ac.record.set("create_uid", ac.UID);
	 *
	 * 用法：唯丿值判宿
	 * int count = Db.queryInt("select count(*) from users where name = ?", ac.record.getStr("name"));
	 * if (count > 0) {
	 *     return Easy.error("名字不能重复");
	 * }
	 * </pre>
	 */
    public String addBefore(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 新增后置任务(事务冿)
     * <pre>
     * ac.record 当前操作数据
     * -------------
     * 用法：级联新增，霿在同事务内完房
     * int id = ac.record.getInt("id");// 获取当前对象主键值，进行级联新增
     * </pre>
     */
    public String addAfter(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 新增成功之后(事务夿)
     * <pre>
     * ac.records 当前操作数据集：Form模式只有丿个对象，Grid模式会有多个对象
     * -------------
     * 用法：关联操使
     * 例：重置缓存，记录日忿...
     * </pre>
     */
    public String addSucceed(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 删除前置任务(事务冿)
     * <pre>
     * ac.record    当前删除对象数据
     * -------------
     * 用法：删除前置检柿
     * </pre>
     */
    public String deleteBefore(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 删除后置任务(事务冿)
     * <pre>
     * ac.record    当前删除对象数据
     * -------------
     * 用法：级联删除，父对象删除时级联删除子对象，同一个事务内
     * </pre>
     */
    public String deleteAfter(AopContext ac) throws Exception {
        return null;
    }

    /**
	 * 删除成功之后(事务夿)<br>
	 *
	 * ac.records 当前操作数据集：Form模式只有丿个对象，Grid模式会有多个对象
	 *
	 */
    public String deleteSucceed(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 修改页初始化
     * <pre>
     * ac.record    当前操作对象数据
     * ac.fixed        当前操作对象固定初始倿
     * -------------
     * 用法：初始化默认倿
     * ac.fixed("update_time", new Date());// 自动填写当前时间
     * </pre>
     */
    public void updateInit(AopContext ac) throws Exception {
    }

    /**
     * 更新前置任务(事务冿)
     * <pre>
     * ac.record 当前操作数据
     * -------------
     * 用法：自动赋倿
     * ac.record.set("update_time", new Date());
     * ac.record.set("user_id", ac.user.get("id"));
     *
     * 用法：唯丿值判宿
     * int count = Db.queryInt("select count(*) from users where name = ?", ac.record.getStr("name"));
     * if (count > 0) {
     *     return Easy.error("名字被占用了");
     * }
     * </pre>
     */
    public String updateBefore(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 更新后置任务(事务冿)<br>
     * <pre>
     * ac.record 当前操作数据
     * -------------
     * 用法：级联修改，霿在同事务内完房
     * </pre>
     */
    public String updateAfter(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 更新成功之后(事务夿)<br>
     * <pre>
     * ac.records 当前操作数据集：Form模式只有丿个对象，Grid模式会有多个对象
     * -------------
     * 用法：关联操使
     * 例：重置缓存，记录日忿...
     * </pre>
     */
    public String updateSucceed(AopContext ac) throws Exception {
        return null;
    }

	/**
	 * 详情页初始化
	 * <pre>
	 * ac.record    当前操作对象数据
	 * </pre>
	 */
	public void detailInit(AopContext ac) throws Exception {
	}

	protected String info(String msg) {
		return "info:" + msg;
	}

	protected static String warn(String msg) {
		return "warn:" + msg;
	}

	protected static String error(String msg) {
		return "error:" + msg;
	}

}