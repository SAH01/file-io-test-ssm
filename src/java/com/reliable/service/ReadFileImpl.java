package com.reliable.service;

import com.csvreader.CsvReader;
import com.reliable.bean.FileDict;
import com.reliable.dao.ReadFile;
import com.reliable.util.JDBCUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReadFileImpl implements ReadFile {
	private int MAX_CELL_NUM;        //文件中最长的一行具有的单元格数
	private PreparedStatement preparedStatement=null;
	private Connection conn =null;
	//--------------------------------------------------
	public ArrayList<ArrayList<String>> readExcelFile(String path) throws IOException, SQLException {
		System.out.println("excel文件路径："+path);       //输出文件路径
		ArrayList<String> tableName = new ArrayList<String>();
		String tableName_1="";                                      //备份表名
		String tableName_2="";                                      //操作表名
		ArrayList<ArrayList<String>> tableValue = new ArrayList<ArrayList<String>>();  //数据表的值
		File excel=new File(path);
		String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！
		System.out.println(split[0]+" "+split[1]);
		tableName_1=split[0];               //给备份数据表名赋值
		tableName_2=split[0]+"_"+split[1];      //给操作表名赋值
		System.out.println("备份表名：" + tableName_1);
		System.out.println("操作表名： " + tableName_2);
		tableName.add(tableName_1);
		tableName.add(tableName_2);
		tableValue.add(tableName);
		Workbook wb;                                                //新建文件
		FileInputStream fileStream = new FileInputStream(excel);   //文件流对象
		//根据文件后缀（xls/xlsx）进行判断
		if ("xls".equals(split[1])){
			//xls文件
			wb = new HSSFWorkbook(fileStream);
		}else{
			//xlsx文件
			wb = new XSSFWorkbook(fileStream);
		}
		//开始解析
		Sheet sheet = wb.getSheetAt(0);     //读取sheet 0
		MAX_CELL_NUM=getMaxCellNumExcel(sheet);
		int firstRowIndex = sheet.getFirstRowNum();   //获取第一行索引
		int lastRowIndex = sheet.getLastRowNum();     //获取最后一行索引
		for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
			Row row = sheet.getRow(rIndex);         //获取行索引
			ArrayList<String> tempTableValue = new ArrayList<String>();     //暂存一行的值，之后放到tableValue
			if (row != null) {
				int lastCellIndex = MAX_CELL_NUM;                           //行的最后一个单元格
				for (int cIndex = 0; cIndex < lastCellIndex; cIndex++) {   //遍历列（单元格）
					Cell cell = row.getCell(cIndex,Row.RETURN_BLANK_AS_NULL);   //允许读空的单元格
					if ((cell == null)) {
						tempTableValue.add("NULL");
					}else {
						cell.setCellType(Cell.CELL_TYPE_STRING);                //转换单元格数据格式为String
						tempTableValue.add(cell.getStringCellValue());
					}
				}
				tableValue.add(tempTableValue);
			}
		}
		System.out.println("读出Excel文件的数据： "+tableValue);         //输出表格的所有值
		wb.close();
		//---------------------------------------------------------------------
		return tableValue;
	}
	//读CSV文件
	public ArrayList<ArrayList<String>> readCsvFile(String path) throws SQLException, IOException {
		System.out.println("CSV文件的路径： "+path);
		//首先判断编码格式
		String code=new String();
		ArrayList<ArrayList<String>> WrongData=new ArrayList<ArrayList<String>>();
		ArrayList<String> tableField = new ArrayList<String>();         //数据表字段名
		ArrayList<ArrayList<String>> tableValue = new ArrayList<ArrayList<String>>();  //数据表的值
		String tableName_1="";                                      //备份表名
		String tableName_2="";                                      //操作表名
		ArrayList<String> tableName = new ArrayList<String>();         //存放两个表名
		File file = new File(path);
		InputStream in= new FileInputStream(file);
		byte[] b = new byte[3];
		in.read(b);
		in.close();
		if (b[0] == -17 && b[1] == -69 && b[2] == -65)
		{
			code="UTF-8";
			System.out.println(file.getName() + "：编码为UTF-8");
		}
		else
		{
			code="GBK";
			System.out.println(file.getName() + "：可能是GBK，也可能是其他编码");
		}
		try {
			// 创建CSV读对象
			CsvReader csvReader = new CsvReader(path,',', Charset.forName(code));
			String[] split1 = path.split("\\.");  //.是特殊字符，需要转义！
//			System.out.println(split1[0]+" "+split1[1]);
			String[] split2 = split1[0].split("\\\\");
//			System.out.println(split2[split2.length-1]);
			tableName_1=split2[split2.length-1];
			tableName_2=split2[split2.length-1]+"_"+split1[1];
			System.out.println("备份表名："+tableName_1);
			System.out.println("操作表名："+tableName_2);
			tableName.add(tableName_1);
			tableName.add(tableName_2);
			tableValue.add(tableName);
			WrongData.add(tableName);
			int rIndex=0;
			// 跳过表头
//			csvReader.readHeaders();
			boolean flag=true;
			while (csvReader.readRecord()){
				// 读一整行
				String resString="";
				resString = csvReader.getRawRecord();
				resString=resString.replace(","," , ");  //替换， 为,空格
				System.out.println(resString);
				if(flag==true){
					tableField.add(resString);
					tableValue.add(tableField);
					WrongData.add(tableField);
					flag=false;
					continue;       //存放表头数据
				}else{

				}
				String[] resString_list=resString.split(",");
				String[] tableField_list=tableField.get(0).split(",");
				ArrayList<String> tempTableValue = new ArrayList<String>();     //暂存一行的值，之后放到tableValue
				if(resString_list.length != tableField_list.length){        //判断这一行的长度和第一行是否一样
					tempTableValue.add(resString);
					WrongData.add(tempTableValue);                          //如果不一样那就是错误数据
				}else {
					tempTableValue.add(resString);
					tableValue.add(tempTableValue);
				}
				rIndex++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//name1,sex1,age1
//		System.out.println(tableValue.get(0).get(0));
		if (WrongData.size()!=2){
			System.out.println("CSV文件的错误数据： "+WrongData);
			return WrongData;
		}else {
			System.out.println("CSV文件的数据： "+tableValue);
			return tableValue;
		}
	}

	//--------------------------------------------------
	//插入数据库 xls和xlsx文件
	public void insertExcelTable(ArrayList<ArrayList<String>> tableValue){
		conn=JDBCUtil.getConnection();
		ArrayList<String> tableName = tableValue.get(0);
		tableValue.remove(0);       //删除第一行的值，表头信息不需要插入数据库
		tableValue.remove(0);       //再次删除掉字段值
		for(int j=0;j<tableName.size();j++)
		{
			for (ArrayList<String> item1 : tableValue){
				String INSERT_TABLE_SQL="INSERT INTO "+tableName.get(j)+" VALUES(";
				String tempString=new String();         //组装SQL语句
				for (int i =0 ;i<item1.size();i++){
					if(i!=item1.size()-1){
						tempString=tempString+"\""+item1.get(i)+"\""+",";
					}
					else{
						tempString=tempString+"\""+item1.get(i)+"\"";
					}
				}
				INSERT_TABLE_SQL=INSERT_TABLE_SQL+tempString+");";
				System.out.println(INSERT_TABLE_SQL);
				try{
					preparedStatement= conn.prepareStatement(INSERT_TABLE_SQL);
					preparedStatement.executeUpdate();
					conn.setAutoCommit(false);
					conn.commit();
					System.out.println("\n");
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	public void insertCsvTable(ArrayList<ArrayList<String>> tableValue){
		ArrayList<String> tableName = tableValue.get(0);
		tableName=tableValue.get(0);
		tableValue.remove(0);
		tableValue.remove(0);
		conn=JDBCUtil.getConnection();
		System.out.println("数据表长度： " + tableValue.size());
		//插入
		for(int j=0;j<tableName.size();j++){
			for (int i=0 ;i<tableValue.size();i++){
				String INSERT_TABLE_SQL="INSERT INTO "+tableName.get(j)+" VALUES( ";
				String tempString=new String();
				String[] tempValue={};
				tempValue=tableValue.get(i).get(0).split("\\,");
//			System.out.println(tempValue[tempValue.length-1]);
				for (int k=0 ; k<tempValue.length; k++)
				{
					if(k!=tempValue.length-1){
						tempString=tempString +"\"" +tempValue[k]+"\"" +",";
					}
					else{
						tempString=tempString+"\"" +tempValue[k]+"\"";
					}
				}
				INSERT_TABLE_SQL=INSERT_TABLE_SQL+tempString+");";
				System.out.println(INSERT_TABLE_SQL);
				try{
					preparedStatement= conn.prepareStatement(INSERT_TABLE_SQL);
					preparedStatement.executeUpdate();
					conn.setAutoCommit(false);
					conn.commit();
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					//关闭数据库连接
				}
			}
		}
	}
	//--------------------------------------------------
	//获取excel表一行最大的单元格数目的方法
	public int getMaxCellNumExcel(Sheet sheet){
		int resNum=0;
		int firstRowIndex = sheet.getFirstRowNum();   //获取第一行索引
		int lastRowIndex = sheet.getLastRowNum();     //获取最后一行索引
		for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
			Row row = sheet.getRow(rIndex);         //获取行索引
			if(row.getLastCellNum()>resNum){
				resNum=row.getLastCellNum();
			}
		}
		return resNum;
	}

	//在DictServlet中被调用，同时调用read文件的方法给这个方法传参数
	@Override
	public FileDict getExcelDict(ArrayList<ArrayList<String>> ExcelValue) {
		//根据传过来的字段名建立数据字典
		//描述默认 ""
		ArrayList<String> fieldDescribe=new ArrayList<>();
		//字段类型默认text
		ArrayList<String> fieldType=new ArrayList<>();
		//大小默认显示 256
		ArrayList<String> fieldSize=new ArrayList<>();
		//单位默认 ""
		ArrayList<String> fieldUnit=new ArrayList<>();
		fieldSize.add("256");
		fieldType.add("text");
		fieldDescribe.add("无");
		fieldUnit.add("无");
		FileDict fileDict = new FileDict();
		fileDict.setFieldDescribe(fieldDescribe);
		fileDict.setFieldType(fieldType);
		fileDict.setFieldSize(fieldSize);
		fileDict.setTableName(ExcelValue.get(0));
		fileDict.setNewFieldName(ExcelValue.get(1));
		fileDict.setFieldName(ExcelValue.get(1));
		fileDict.setFieldUnit(fieldUnit);
		return fileDict;
	}
	@Override
	public FileDict getCsvDict(ArrayList<ArrayList<String>> CsvValue) {
		FileDict fileDict = new FileDict();
		String[] fieldNameList=CsvValue.get(1).get(0).split("\\,");
		String[] newFieldNameList=CsvValue.get(1).get(0).split("\\,");
		ArrayList<String> fieldName=new ArrayList<>();
		ArrayList<String> newFieldName=new ArrayList<>();
		for (int i = 0;i<fieldNameList.length;i++)
		{
			fieldName.add(fieldNameList[i]);
		}
		for (int i = 0;i<newFieldNameList.length;i++)
		{
			newFieldName.add(newFieldNameList[i]);
		}
		//描述默认 ""
		ArrayList<String> fieldDescribe=new ArrayList<>();
		//字段类型默认text
		ArrayList<String> fieldType=new ArrayList<>();
		//大小默认显示 256
		ArrayList<String> fieldSize=new ArrayList<>();
		//单位默认 ""
		ArrayList<String> fieldUnit=new ArrayList<>();
		fieldSize.add("256");
		fieldType.add("text");
		fieldDescribe.add("无");
		fieldUnit.add("无");
		fileDict.setFieldDescribe(fieldDescribe);
		fileDict.setFieldType(fieldType);
		fileDict.setFieldSize(fieldSize);
		fileDict.setFieldName(fieldName);
		fileDict.setNewFieldName(newFieldName);
		fileDict.setTableName(CsvValue.get(0));
		fileDict.setFieldUnit(fieldUnit);
		return fileDict;
	}

	@Override
	public void createTable(ArrayList<String[]> tableInfo) {
		//获取Sqlsession
		String DROP_TABLE_1="";
		String DROP_TABLE_2="";
		String CREATE_TABLE_1_SQL="";
		String CREATE_TABLE_2_SQL="";
		DROP_TABLE_1="DROP TABLE IF EXISTS "+ tableInfo.get(0)[0] +";";
		DROP_TABLE_2="DROP TABLE IF EXISTS "+tableInfo.get(0)[1] +";";
		CREATE_TABLE_1_SQL="CREATE TABLE "+ tableInfo.get(0)[0] +"(" ;
		CREATE_TABLE_2_SQL="CREATE TABLE "+ tableInfo.get(0)[1] +"(" ;
		//生成备份表建表语句 （字段、描述、类型）
		CREATE_TABLE_1_SQL=getTableSQL(CREATE_TABLE_1_SQL, tableInfo.get(1) , tableInfo.get(2),tableInfo.get(3),tableInfo.get(4));
		//生成操作表建表语句
		CREATE_TABLE_2_SQL=getTableSQL(CREATE_TABLE_2_SQL, tableInfo.get(1) , tableInfo.get(2),tableInfo.get(3),tableInfo.get(4));
		System.out.println(CREATE_TABLE_1_SQL);
		System.out.println(CREATE_TABLE_2_SQL);
		try {
			conn=JDBCUtil.getConnection();
			preparedStatement = conn.prepareStatement(DROP_TABLE_1);
			preparedStatement.executeUpdate();
			preparedStatement= conn.prepareStatement(CREATE_TABLE_1_SQL);
			preparedStatement.executeUpdate();
			preparedStatement = conn.prepareStatement(DROP_TABLE_2);
			preparedStatement.executeUpdate();
			preparedStatement= conn.prepareStatement(CREATE_TABLE_2_SQL);
			preparedStatement.executeUpdate();
			conn.setAutoCommit(false);
			conn.commit();
			JDBCUtil.release(conn,preparedStatement);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("出现问题！建表失败！");
		}
	}
	//获取文件的建表sql语句
	public String getTableSQL(String SQL,String[] tableField, String[]fieldDescribe,String[] fieldUnit, String[] fieldType ){
		for (int i =0 ;i<tableField.length;i++)
		{
			String item= tableField[i];
			if(i!=tableField.length-1){
				SQL=SQL +  "`" + item + "`" +" text COMMENT "  +"\'" +fieldDescribe[i]+","+fieldUnit[i]+"\'" +" COLLATE utf8_general_ci,"+"\n";
			}else{
				SQL=SQL +  "`" + item + "`" +" text COMMENT "+"\'" +fieldDescribe[i]+","+fieldUnit[i]+"\'" + " COLLATE utf8_general_ci "+"\n";
			}
		}
		return SQL+") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;";
	}
}

