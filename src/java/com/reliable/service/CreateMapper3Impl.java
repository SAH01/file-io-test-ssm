package com.reliable.service;

import com.reliable.dao.Create1Mapper;
import com.reliable.dao.Create3Mapper;
import com.reliable.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateMapper3Impl implements Create3Mapper {
	@Override
	public void dropTable3(Map<String, Object> tableInfo) {

	}

	@Override
	public void createTable3Test(ArrayList<String[]> tableInfo) {
		String tableName=tableInfo.get(0)[0]+"_state";
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("tableName",tableName);
		condition.put("comment_1","字段名");
		condition.put("comment_2","字段状态");
		System.out.println("第三张表名： "+condition.get("tableName"));
//		SqlSession sqlSession = MybatisUtils.getSession();
//		Create3Mapper mapper = sqlSession.getMapper(Create3Mapper.class);
		SqlSession sqlSession = MybatisUtils.getSession();
		Create3Mapper mapper = sqlSession.getMapper(Create3Mapper.class);
		mapper.dropTable3(condition);
		mapper.createTable3(condition);
	}

	@Override
	public void createTable3(Map<String, Object> tableInfo) {

	}
}
