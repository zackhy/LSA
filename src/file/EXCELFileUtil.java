package com.priv.thesis.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

public class EXCELFileUtil {
	
	private jxl.Workbook readwb = null;
	private InputStream is = null;
    private Sheet readst = null;
    
    public EXCELFileUtil(String filename)throws Exception{
    	is = new FileInputStream(filename);
    	readwb = Workbook.getWorkbook(is);
    	readst = readwb.getSheet(0);
    }
    
    
    // Read EXCEL file by row, return ArrayList
    public ArrayList readRow(int i){
    	int t = getColumns();
    	ArrayList arr = new ArrayList();
    	for(int j = 0; j < t; j++){
    		Cell cell = readst.getCell(j,i);
    		arr.add(cell.getContents());
//    		System.out.println(cell.getContents().toString());
    	}
    	return arr;
    }
    
    // Read EXCEL file by column, return ArrayList
    public ArrayList readColumn(int j) throws Exception{
    	int t = getRows();
    	ArrayList arr = new ArrayList();
    	for(int i = 0; i < t; i++){
    		Cell cell = readst.getCell(j,i);
    		arr.add(cell.getContents());
//    		System.out.println(cell.getContents().toString());
    	}
    	return arr;
    }
    
    // Read EXCEL file by cell, return String
    public String readCell(int i, int j) throws Exception{
    	String s = "";
    	Cell cell = readst.getCell(j, i);
    	s = cell.getContents().toString();
    	return s;
    }
    
    // Get
    public int getRows(){
    	return readst.getRows();
    }
    
    public int getColumns(){
    	return readst.getColumns();
    }
    
}
