package com.example.demo.util;

import java.util.ResourceBundle;

/**
 * @author zhuyc
 * @Description TODO
 * @Classname ResourceUtil
 * @PackageName org.example.util
 * @Date 2020/1/17 10:51
 */
public class ResourceUtil {
	private static  final ResourceBundle resourceBundle;
	static{
		resourceBundle=ResourceBundle.getBundle("rabbitmq");
	}

	public  static String getKey(String key){
		return resourceBundle.getString(key);
	}
}
