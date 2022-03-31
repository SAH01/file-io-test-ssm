package com.reliable.dao;

import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

public interface Create1Mapper {
	public void dropTable1(Map<String,Object> tableInfo);
	public void createTable1(Map<String,Object> tableInfo);
	//调用
	public void createTable1Test(ArrayList<String[]> tableInfo);
}
