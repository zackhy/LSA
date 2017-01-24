package com.priv.thesis.lsa;

import java.io.File;
import java.util.Map;

import com.priv.thesis.file.*;

public class WeightCaculator {
	
	private FreqCaculator fc = null;
	private String[] words = null;
	private Map<String, Integer> wordFreqs = null;
	private int[][] idWordFreqs = null;
	private int[] tic = null;
	
	public WeightCaculator(String filename, String s, String s1) throws Exception{
		fc = new FreqCaculator(filename);
		wordFreqs = fc.countTotalWords(s);
		words = fc.words(s);
		idWordFreqs = fc.countIdWords(words, s1);
		tic = fc.countIDTags(s1);
	}
	
	/**
	 * �����ǩ�ֲ�Ȩ��,��ʽΪ:
	 * Wp(i,j) = log2(Freq(i,j) + 1)
	 */
	public double[][] partialWeight(){
		int n;
		double[][] pw = new double[idWordFreqs.length][idWordFreqs[0].length];
		for(int i = 0; i < idWordFreqs.length; i++){
			for(int j = 0; j < idWordFreqs[0].length; j++){
				pw[i][j] = (double) log((idWordFreqs[i][j]+1), 2);
			}
		}
		return pw;
	}
	
	/**
	 * �����ǩȫ��Ȩ��
	 * ��ʽ���ӣ����ڴ�ָ��
	 */
	public double[] tagWeight(){
		int tfreq;
		double[] tw = new double[words.length];
		for(int i = 0; i < words.length; i++){
			tfreq = wordFreqs.get(words[i]);  //ȡ����ǩi���ܴ�Ƶ
			double w = 0;
			double w1 = 0;
			double w2 = 0;
			double w3 = 0;
			for(int j = 0; j < idWordFreqs[0].length; j++){
				if(idWordFreqs[i][j] != 0 && tfreq != 0){
					w1 = (double)idWordFreqs[i][j]/tfreq;
					w2 = (double)log(w1,2);
					w3 += w1*w2;
				}
				w = 1 - w3;
			}
			tw[i] = w;
		}
		
		return tw;
	}
	
	/**
	 * ������Դ���û�����docָ����ȫ��Ȩ��
	 * ��ʽ���ӣ����ڴ�ָ��
	 */
	public double[] docWeight(){
		int docfreq;
		double[] dw = new double[idWordFreqs[0].length];
		for(int i = 0; i < idWordFreqs[0].length; i++){
			docfreq = tic[i];  //ȡ����ǩi���ܴ�Ƶ
			double w = 0;
			double w1 = 0;
			double w2 = 0;
			double w3 = 0;
			for(int j = 0; j < idWordFreqs.length; j++){
				if(idWordFreqs[j][i] != 0 && docfreq != 0){
					w1 = (double)idWordFreqs[j][i]/docfreq;
					w2 = (double)log(w1,2);
					w3 += w1*w2;
				}
				w = 1 + w3;
			}
			dw[i] = w;
		}
		
		return dw;
	}
	
	/**
	 * ����Ȩ�أ���ʽΪ��
	 * Wg(i,j) = Wp(i,j) * Wt(i) * Wd(j)
	 * ���õ���-�ı����󣬴˾���Ԫ��Ϊ�����������Ȩ��
	 */
	public double[][] globalWeight(double[][] pw, double[] tw, double[] dw){
		double[][] gw = new double[idWordFreqs.length][idWordFreqs[0].length];
		System.out.println("***��ʼ������Ȩ��ΪԪ�صĴ�-�ı�����***");
		for(int i = 0; i < idWordFreqs.length; i++){
			for(int j = 0; j < idWordFreqs[0].length; j++){
				double temp = 0;
				temp = Math.abs(pw[i][j]*tw[i]*dw[j]);
				gw[i][j] = temp;
			}
		}
		System.out.println("***������Ȩ��ΪԪ�صĴ�-�ı�����ɹ�***");
		System.out.println();
		return gw;
	}
	
	//�ڲ�����������logֵ
	private double log(double value, double base){
		return Math.log(value)/Math.log(base);
	}
	
//	�������Ϸ���
//	public static void main(String[] args) throws Exception{
//		File file = new File("data/total_tags.txt");
//		File file1 = new File("data/resources_tags.txt");
//		TXTFileUtil tfu = new TXTFileUtil("data/test1.xls");
//		String s = tfu.readTxtFile(file);
//		String s1 = tfu.readTxtFile(file1);
//		WeightCaculator wc = new WeightCaculator("data/test1.xls", s, s1);
//		double[][] pw = wc.partialWeight();
//		double[] tw = wc.tagWeight();
//		double[] dw = wc.docWeight();
//		double[][] gw = wc.globalWeight(pw, tw, dw);
//		for(int i = 0; i < gw.length; i++){
//			System.out.println(gw[216][8]);
//		}
//		
//	}

}
