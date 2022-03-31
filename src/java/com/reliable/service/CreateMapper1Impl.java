package com.reliable.service;

import com.reliable.dao.Create1Mapper;
import com.reliable.dao.Create2Mapper;
import com.reliable.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateMapper1Impl implements Create1Mapper {
	@Override
	public void createTable1(Map<String, Object> tableInfo) {

	}

	@Override
	public void dropTable1(Map<String, Object> tableInfo) {

	}

	@Override
	public void createTable1Test(ArrayList<String[]> tableInfo) {
		SqlSession sqlSession = MybatisUtils.getSession();
		Create1Mapper mapper = sqlSession.getMapper(Create1Mapper.class);
		Map<String, Object > condition  = new HashMap<String, Object>();
		String[] tableName = tableInfo.get(0);
		String[] fieldName = tableInfo.get(1);
		String[] fieldDescribe = tableInfo.get(2);
		String[] fieldUnit = tableInfo.get(5);
		ArrayList<String[]> outer = new ArrayList<>();    //外层
		for(int i=0;i<fieldName.length;i++)
		{
			String[] temp=new String[3];
			temp[0]=fieldName[i];
			temp[1]=fieldDescribe[i]+","+fieldUnit[i];
			outer.add(temp);
		}
		for (String[] strings : outer) {
			for (String string : strings) {
				System.out.println(string);
			}
		}
		condition.put("tableName",tableName[0]);
		condition.put("tableField",outer);
		mapper.dropTable1(condition);
		mapper.createTable1(condition);
	}
}
