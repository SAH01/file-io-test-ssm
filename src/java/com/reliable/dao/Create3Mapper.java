package com.reliable.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Create3Mapper {
	//删除字段状态表
	public void dropTable3(Map<String,Object> tableInfo);
	//新建字段状态表
	public void createTable3(Map<String,Object> tableInfo);
	//调用测试
	public void createTable3Test(ArrayList<String[]> tableInfo);

	//**********************************************************

}
