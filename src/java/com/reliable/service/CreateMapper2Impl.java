package com.reliable.service;

import com.reliable.dao.Create1Mapper;
import com.reliable.dao.Create2Mapper;
import com.reliable.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateMapper2Impl implements Create2Mapper  {
	@Override
	public void dropTable2(Map<String, Object> tableInfo) {

	}

	@Override
	public void createTable2(Map<String, Object> tableInfo) {

	}

	@Override
	public void createTable2Test(ArrayList<String[]> tableInfo) {
		SqlSession sqlSession = MybatisUtils.getSession();
		Create2Mapper mapper = sqlSession.getMapper(Create2Mapper.class);
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
		condition.put("tableName",tableName[1]);
		condition.put("tableField",outer);
		mapper.dropTable2(condition);
		mapper.createTable2(condition);
	}
}
