package com.ray.common.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.ray.common.interceptor.AopContext;
import com.ray.common.interceptor.MetaObjectIntercept;
import com.ray.common.model.DataTask;

public class TaskIntercept extends MetaObjectIntercept {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String addAfter(AopContext ac) throws Exception {
		String className = ac.record.getStr("clazz");
		String exp = ac.record.getStr("exp");

		Class clazz = Class.forName(className);
		JobDetail job = JobBuilder.newJob(clazz).withIdentity(className, className).build();
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(className, className).withSchedule(CronScheduleBuilder.cronSchedule(exp)).build();

		QuartzPlugin.scheduler.scheduleJob(job, trigger);
		QuartzPlugin.scheduler.pauseJob(job.getKey());
		
		return null;
	}

	@Override
	public String deleteAfter(AopContext ac) throws Exception {
		String className = ac.record.getStr("clazz");

		JobKey jobKey = JobKey.jobKey(className, className);

		QuartzPlugin.scheduler.deleteJob(jobKey);
		
		return null;
	}

	@Override
	public String updateAfter(AopContext ac) throws Exception {
		String className = ac.record.getStr("clazz");
		String exp = ac.record.getStr("exp");

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(exp);

		TriggerKey triggerKey = TriggerKey.triggerKey(className, className);
		CronTrigger trigger = (CronTrigger) QuartzPlugin.scheduler.getTrigger(triggerKey);
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		
		QuartzPlugin.scheduler.rescheduleJob(triggerKey, trigger);
		
		QuartzPlugin.scheduler.pauseTrigger(triggerKey);
		
		DataTask.dao.updateState(ac.record.getInt("id"), DataTask.STATE_STOP);
		
		return null;
	}
	
	@Override
	public String addBefore(AopContext ac) throws Exception {
		String cs = ac.record.getStr("clazz");
		DataTask task = DataTask.dao.findFirst("select * from data_task where clazz = ?", cs);
		if (task!=null) {
			throw new Exception("Job实现类已经存在:" + cs);
		}
		return null;
	}
	
}