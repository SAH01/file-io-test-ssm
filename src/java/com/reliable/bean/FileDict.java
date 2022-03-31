package com.reliable.bean;

import java.util.ArrayList;

public class FileDict {
	ArrayList<String> tableName;    //里面存备份表和操作表的名字
	ArrayList<String> fieldName;    //里面存表格第一行、也就是表格的表头作为建立数据表的字段
	ArrayList<String> newFieldName; //这个变量是如果用户在文件入库之后需要修改数据表的结构，存放新修改的字段名
	ArrayList<String> fieldDescribe;    //存放每个字段的描述信息
	ArrayList<String> fieldType;        //存放字段类型
	ArrayList<String> fieldSize;        //存放字段长度大小
	ArrayList<String> fieldUnit;        //存放字段单位

	public FileDict() {

	}

	public FileDict(ArrayList<String> tableName, ArrayList<String> fieldName, ArrayList<String> newFieldName, ArrayList<String> fieldDescribe, ArrayList<String> fieldType, ArrayList<String> fieldSize, ArrayList<String> fieldUnit) {
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.newFieldName = newFieldName;
		this.fieldDescribe = fieldDescribe;
		this.fieldType = fieldType;
		this.fieldSize = fieldSize;
		this.fieldUnit = fieldUnit;
	}

	public ArrayList<String> getTableName() {
		return tableName;
	}

	public void setTableName(ArrayList<String> tableName) {
		this.tableName = tableName;
	}
	public ArrayList<String> getFieldName() {
		return fieldName;
	}

	public void setFieldName(ArrayList<String> fieldName) {
		this.fieldName = fieldName;
	}

	public ArrayList<String> getNewFieldName() {
		return newFieldName;
	}

	public void setNewFieldName(ArrayList<String> newFieldName) {
		this.newFieldName = newFieldName;
	}

	public ArrayList<String> getFieldDescribe() {
		return fieldDescribe;
	}

	@Override
	public String toString() {
		return "FileDict{" +
				"tableName=" + tableName.toString() +
				", fieldName=" + fieldName.toString() +
				", newFieldName=" + newFieldName.toString() +
				", fieldDescribe=" + fieldDescribe.toString() +
				", fieldType=" + fieldType.toString() +
				", fieldSize=" + fieldSize.toString() +
				'}';
	}

	public ArrayList<String> getFieldUnit() {
		return fieldUnit;
	}
	public void setFieldUnit(ArrayList<String> fieldUnit) {
		this.fieldUnit = fieldUnit;
	}
	public void setFieldDescribe(ArrayList<String> fieldDescribe) {
		this.fieldDescribe = fieldDescribe;
	}

	public ArrayList<String> getFieldType() {
		return fieldType;
	}

	public void setFieldType(ArrayList<String> fieldType) {
		this.fieldType = fieldType;
	}

	public ArrayList<String> getFieldSize() {
		return fieldSize;
	}

	public void setFieldSize(ArrayList<String> fieldSize) {
		this.fieldSize = fieldSize;
	}
}
