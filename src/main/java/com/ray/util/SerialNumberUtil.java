package com.ray.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ray.common.model.SerialNumber;

/**
 * 生成流水号工具类
 * @author Ray
 *
 */
public class SerialNumberUtil {
	
	public static String generator(String field){
		SerialNumber serialNumber = SerialNumber.dao.findById(1);
		int sn = serialNumber.get(field);
		String result = new SimpleDateFormat("yyMMdd").format(new Date());
		if(sn<10){
			result += "00"+sn;
		}else if(sn>=10 && sn<100){
			result += "0"+sn;
		}else if(sn>100){
			result += String.valueOf(sn);
		}
		serialNumber.set(field, sn+1);
		serialNumber.update();
		return result;
	}
}
