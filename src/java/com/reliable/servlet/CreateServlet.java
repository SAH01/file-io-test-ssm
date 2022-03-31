package com.reliable.servlet;

import com.reliable.dao.Create1Mapper;
import com.reliable.dao.Create2Mapper;
import com.reliable.dao.Create3Mapper;
import com.reliable.dao.ReadFile;
import com.reliable.service.CreateMapper1Impl;
import com.reliable.service.CreateMapper2Impl;
import com.reliable.service.CreateMapper3Impl;
import com.reliable.service.ReadFileImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		System.out.println("CreateServlet servlet访问成功！");
		//获取session对象
		HttpSession session = req.getSession();
		//获取前台数据
		String[] tableName=req.getParameterValues("tableName");
		String[] fieldName=req.getParameterValues("fieldName");
		String[] fieldDescribe=req.getParameterValues("fieldDescribe");
		String[] fieldType=req.getParameterValues("fieldType");
		String[] fieldSize=req.getParameterValues("fieldSize");
		String[] fieldUnit=req.getParameterValues("fieldUnit");
		//封装前台数据
		ArrayList<String[]> tableInfo= new ArrayList<>();
		tableInfo.add(tableName);
		tableInfo.add(fieldName);
		tableInfo.add(fieldDescribe);
		tableInfo.add(fieldUnit);
		tableInfo.add(fieldType);
		tableInfo.add(fieldSize);
		System.out.println("前台数据：   "+tableInfo.get(0)[0]);
		ReadFile readFile = new ReadFileImpl();
		Create1Mapper create1Mapper = new CreateMapper1Impl();
		Create2Mapper create2Mapper = new CreateMapper2Impl();
		Create3Mapper create3Mapper = new CreateMapper3Impl();
		//把前台数据传给后台建表
//		readFile.createTable(tableInfo);
		create1Mapper.createTable1Test(tableInfo);
		create2Mapper.createTable2Test(tableInfo);
		create3Mapper.createTable3Test(tableInfo);
		//获取当前数据表的所有值,首先判断表的格式
		ArrayList<ArrayList<String>> tableValue=new ArrayList<ArrayList<String>>();
		//判断文件格式，调用对应的方法导入数据库
		if("xls".equals(tableName[1].substring(tableName[1].length()-3))){
			tableValue= (ArrayList<ArrayList<String>>) session.getAttribute("excelValue");
			readFile.insertExcelTable(tableValue);
		}
		if("csv".equals(tableName[1].substring((tableName[1].length()-3)))){
			tableValue= (ArrayList<ArrayList<String>>) session.getAttribute("csvValue");
			readFile.insertCsvTable(tableValue);
		}
		if("xlsx".equals(tableName[1].substring((tableName[1].length()-4)))){
			tableValue= (ArrayList<ArrayList<String>>) session.getAttribute("excelValue");
			readFile.insertExcelTable(tableValue);
		}
		String msg=new String();
		msg="备份成功！";
		req.setAttribute("msg",msg);
		req.getRequestDispatcher("new_main_1.jsp").forward(req, resp);
	}
}
