package com.priv.thesis.lsa;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

import com.priv.thesis.file.TXTFileUtil;

public class main {
	
	public static void main(String[] args) throws Exception{
		File file = new File("data/total_tags.txt");
		File file1 = new File("data/resources_tags.txt");
		File file2 = new File("data/users_tags.txt");
		TXTFileUtil tfu = new TXTFileUtil("data/test1.xls");
		FreqCaculator fc = new FreqCaculator("data/test1.xls");
		String s = tfu.readTxtFile(file);
		String s1 = tfu.readTxtFile(file1);
		String s2 = tfu.readTxtFile(file2);
		String[] s3 = s1.split("\\r?\\n");
		String[] s4 = s2.split("\\r?\\n");
		String[] words = fc.words(s);
		WeightCaculator wc = new WeightCaculator("data/test1.xls", s, s1);
		WeightCaculator wc2 = new WeightCaculator("data/test1.xls", s, s2);
		SvdDecompositions sd = new SvdDecompositions("data/test1.xls", s, s1);
		SvdDecompositions sd1 = new SvdDecompositions("data/test1.xls", s, s2);
		cosineCaculator cc = new cosineCaculator("data/test1.xls", s, s1);
		double[][] ct = cc.cosineTag();
//		System.out.println(ct[553][216]);
		System.out.println("输入想要导航的标签：");
		Scanner scan = new Scanner(System.in);
		String read = scan.nextLine();
		double[][] cr = sd.gw;
		double[][] cu = sd1.gw;
		int k = 0;
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(read)) {
				k = i;
				i += 1;
			}
		}
		System.out.println("相关标签包括:");
		for(int i = 0; i < ct.length; i++){
			if(ct[k][i] > 0.7){
				System.out.print(words[i] + " ");
			}
		}
		System.out.println();
		System.out.println("相关资源包括：");
		for(int i = 0; i < cr[0].length; i ++){
			if(cr[k][i] > 0.7){
				System.out.print(s3[i*2] + " ");
			}
		}
		System.out.println();
		System.out.println("相关用户包括：");
		for(int i = 0; i < cu[0].length; i ++){
			if(cu[k][i] > 0.7){
				System.out.print(s4[i*2] + " ");
			}
		}
	}
	
}