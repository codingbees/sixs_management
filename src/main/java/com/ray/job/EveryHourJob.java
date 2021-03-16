package com.ray.job;

import org.quartz.JobExecutionContext;

import com.ray.common.quartz.AbsJob;

/**
 * 每小时执行
 *
 * @author Ray
 * @date 2010-08-03
 */
public class EveryHourJob extends AbsJob {

	@Override
	protected void process(JobExecutionContext context) {

		System.out.println("每小时任务");
	}

}
