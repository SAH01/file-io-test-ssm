package com.reliable.service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.reliable.dao.ReadDB;
import com.reliable.util.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReadDBImpl implements ReadDB {
	private PreparedStatement preparedStatement=null;
	private Connection conn =null;
	private ResultSet resultSet=null;
	@Override
	public void getAllDbData() throws SQLException {
		HashMap<String, String> map = new HashMap<>();
		ArrayList<String> list = new ArrayList<String>();
		conn= JDBCUtil.getConnection();
		if(conn!=null)
		{
			System.out.println("数据库连接成功！");
		}
		preparedStatement=conn.prepareStatement("select * from hello;");
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			String name= resultSet.getString(1);
			String sex =resultSet.getString(2);
			String age= resultSet.getString(3);
			list.add(name+","+sex+" "+age);
		}
		for (int i=0;i<list.size();i++)
		{
			map.put(String.valueOf(i),list.get(i));
		}
		JSONObject jo = JSONObject.fromObject(map);
//		System.out.println(jo);

		//遍历 01
		Set<String> keys = map.keySet();  //map.keySet()返回key的集合
		for(String key:keys) {
			System.out.println(key+":"+map.get(key));  //map.get(key)返回key所对应的value值
		}
		System.out.println("--------------------------");
		//遍历 02
		Iterator<String> it02 = keys.iterator();
		String key;
		while (it02.hasNext()) {
			key = it02.next();
			System.out.println(key+":"+map.get(key));
		}
		System.out.println("--------------------------");
		//遍历 03
		Set<Map.Entry<String,String>> entrySet = map.entrySet();  //map.entrySet()返回<key,value>键值对的集合
		for (Map.Entry<String,String> entry:entrySet) {
			System.out.println(entry.getKey()+":"+entry.getValue());  //entry.getKey()返回key,entry.getValue()返回value
		}
		//遍历 04
		System.out.println("--------------------------");
		Iterator<Map.Entry<String,String>> it04 = entrySet.iterator();
		Map.Entry<String,String> entry;
		while (it04.hasNext()) {
			entry = it04.next();
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		JDBCUtil.release(conn,preparedStatement);
	}
}
