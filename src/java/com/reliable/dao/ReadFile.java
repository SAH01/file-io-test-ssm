package com.reliable.dao;

import com.reliable.bean.FileDict;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ReadFile {
	//返回Excel文件（*.xls/*.xlsx）的所有数据(列表的第一个值是两个表名，后面开始是第一行的数据)
	public ArrayList<ArrayList<String>> readExcelFile(String path) throws IOException, SQLException;
	//返回Csv文件的所有数据(列表的第一个值是两个表名，后面开始是第一行的数据)
	public ArrayList<ArrayList<String>> readCsvFile(String path) throws IOException, SQLException;
	//返回excel文件的数据字典类
	public FileDict getExcelDict(ArrayList<ArrayList<String>> ExcelValue);
	//返回csv文件的数据字典类
	public FileDict getCsvDict(ArrayList<ArrayList<String>> CsvValue);
	//创建表，参数为用户在页面修改后的数据，整合成一个个字符串数组并存放到一个arraylist
	public void createTable(ArrayList<String[]> tableInfo);
	//入库，注意参数是所有的数据，其中的表名和表头是不需要入库的
	public void insertExcelTable(ArrayList<ArrayList<String>> tableValue);
	public void insertCsvTable(ArrayList<ArrayList<String>> tableValue);
}
