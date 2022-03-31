package com.reliable.dao;

import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

public interface Create2Mapper {
	//删除表
	public void dropTable2(Map<String,Object> tableInfo);
	//创建表
	public void createTable2( Map<String,Object> tableInfo);
	//调用
	public void createTable2Test(ArrayList<String[]> tableInfo);
}
