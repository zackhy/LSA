package com.priv.thesis.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TXTFileUtil {

	private EXCELFileUtil efu = null;
	private int r;
	private int c;

	public TXTFileUtil(String filename) throws Exception {
		efu = new EXCELFileUtil(filename);
		r = efu.getRows();
		c = efu.getColumns();
	}
	
	// Read TXT file
	public static String readTxtFile(File fileName) throws Exception {
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = "";
				while ((read = bufferedReader.readLine()) != null) {
					result = result + read + "\r\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		
		return result;
	}

	// Write tags to TXT file
	public void total_Tags() throws Exception {
		try {
			File file = new File("data/total_tags.txt");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			ArrayList ttag = new ArrayList();

			ttag = efu.readColumn(2);
			String tag = "";
			for (int i = 0; i < ttag.size(); i++) {
				tag += ttag.get(i) + " ";
			}
			bw.write(tag);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Categorize tags by resource id and user id, write to file
	public void id_Tags(String filename, int j) throws Exception {
		try {
			File file = new File(filename);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			Map<Integer, String> id = getID(j);

			String s = "";
			String str = "";
			String tag = "";
			for (int i = 0; i < id.size(); i++) {
				s = id.get(i);
				bw.write(s);
				bw.newLine();
				for (int k = 0; k < r; k++) {
					str = efu.readCell(k, j);
					if (str.equals(s)) {
						tag += efu.readCell(k, 2);
						tag += " ";
					}
				}
				bw.write(tag);
				bw.newLine();
				tag = "";
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Store user id and resource id in HashMap
	public Map<Integer, String> getID(int j) throws Exception {
		Map<Integer, String> id = new HashMap<Integer, String>();
		String s = "";
		int f = 0;
		for (int i = 0; i < r; i++) {
			s = efu.readCell(i, j);
			if (!id.containsValue(s)) {
				id.put(f, s);
				f++;
			}
		}
		return id;
	}

	// Create files
	public void txtWriter() throws Exception {
		System.out.println("***Create total_tags.txt***");
		total_Tags();
		System.out.println("***total_tags.txt created***");
		System.out.println();
		System.out.println("***Create resources_tags.txt***");
		id_Tags("data/resources_tags.txt", 1);
		System.out.println("***resources_tags.txt created***");
		System.out.println();
		System.out.println("***Create users_tags.txt***");
		id_Tags("data/users_tags.txt", 0);
		System.out.println("***users_tags.txt created***");
	}

//	Test
	public static void main(String[] args) throws Exception {
		TXTFileUtil tfu = new TXTFileUtil("data/test1.xls");
		tfu.txtWriter();
		File file = new File("data/resources_tags.txt");
		String s = tfu.readTxtFile(file);
		System.out.println(s);
	}

}
