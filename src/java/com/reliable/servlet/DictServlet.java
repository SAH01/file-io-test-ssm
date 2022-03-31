package com.reliable.servlet;

import com.reliable.bean.FileDict;
import com.reliable.dao.ReadFile;
import com.reliable.service.ReadFileImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/DictServlet")
public class DictServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		HttpSession session = req.getSession();
		System.out.println("DictServlet 访问成功！");
		String filePath=req.getParameter("filePath");
		System.out.println("读取的文件路径： " + filePath);
		filePath=filePath.replace("\\","\\\\");
		ReadFile readFile = new ReadFileImpl();
		if(filePath.split("\\.")[filePath.split("\\.").length-1].equals("xls")
				|| filePath.split("\\.")[filePath.split("\\.").length-1].equals("xlsx"))
		{
			try {
				ArrayList<ArrayList<String>> excelValue=readFile.readExcelFile(filePath);
				//-----------------------------------------------------
				session.setAttribute("excelValue", excelValue); //把excel文件的所有数据存到session
				//-----------------------------------------------------
				FileDict fileDict = readFile.getExcelDict(excelValue);
				System.out.println("-----------------------");
				System.out.println("DictServlet: "+fileDict.toString());
				System.out.println("-----------------------");
				req.setAttribute("dict",fileDict);
				req.getRequestDispatcher("new_main.jsp").forward(req, resp);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				ArrayList<ArrayList<String>> csvValue=readFile.readCsvFile(filePath);
				if(csvValue.get(1).get(0).split(",").length!=csvValue.get(2).get(0).split(",").length)
				{
					req.setAttribute("wrongData",csvValue);
					req.getRequestDispatcher("wrong.jsp").forward(req, resp);
				}
				else{
					//-----------------------------------------------------
					session.setAttribute("csvValue", csvValue); //把csv文件的所有数据存到session
					//-----------------------------------------------------
					FileDict fileDict = readFile.getCsvDict(csvValue);
					System.out.println("-----------------------");
					System.out.println("DictServlet: "+fileDict.toString());
					System.out.println("-----------------------");
					req.setAttribute("dict",fileDict);
					req.getRequestDispatcher("new_main.jsp").forward(req, resp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
