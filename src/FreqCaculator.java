package com.priv.thesis.lsa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.priv.thesis.file.*;

public class FreqCaculator {
	
	private TXTFileUtil tfu = null;
	
	public FreqCaculator(String filename) throws Exception{
		tfu = new TXTFileUtil(filename);
		tfu.txtWriter();
	}
	
	public String[] splitWord(String s){
		String[] words = s.split("\\s+");
		return words;
	}
	
	//�ĵ��ִ�
	public String[] words(String s){
		
		String[] words = splitWord(s);
		Set<String> set = new TreeSet<String>();
		for(String word: words){
			set.add(word);
		}
		Iterator it = set.iterator();
		
		ArrayList<String> wordsList = new ArrayList<String>();
		ArrayList<Integer> freqList = new ArrayList<Integer>();
		
		while(it.hasNext()){
			String word = (String)it.next();
			int count = 0;
			for(String str: words){
				if(str.equals(word)){
					count++;
				}
			}
			
			wordsList.add(word);
			freqList.add(count++);
		}
		
		words = wordsList.toArray(new String[0]);
		return words;
	}
	
	//�����ܴ�Ƶ
	public Map<String, Integer> countTotalWords(String s){
		String[] words = splitWord(s);
		Set<String> set = new TreeSet<String>();
		for(String word: words){
			set.add(word);
		}
		Iterator it = set.iterator();
		
		ArrayList<String> wordsList = new ArrayList<String>();
		ArrayList<Integer> freqList = new ArrayList<Integer>();
		
		System.out.println();
		System.out.println("***��ʼ�����ܴ�Ƶ***");
		
		//��ʼ�����ܴ�Ƶ
		while(it.hasNext()){
			String word = (String)it.next();
			int count = 0;
			for(String str: words){
				if(str.equals(word)){
					count++;
				}
			}
			
			wordsList.add(word);
			freqList.add(count++);
		}
		
		words = wordsList.toArray(new String[0]);
		int[] wf = new int[freqList.size()];
		for(int i = 0; i < freqList.size(); i++){
			wf[i] = freqList.get(i);
		}
		Map<String, Integer> wordFreqs = new HashMap<String, Integer>();
		for(int k = 0; k < words.length; k++){
			wordFreqs.put(words[k], wf[k]);
		}
		System.out.println("***�ܴ�Ƶ����ɹ�***");
		System.out.println();
		return wordFreqs;
	}
	
	//������Դ���û���ӵ�еı�ǩ����
	public int[] countIDTags(String s){
		String[] s1 = s.split("\\r?\\n");
		int[] cit = new int[s1.length/2];
		String[] tempstr = null;
		int n = 0;
		for(int i = 0; i < cit.length; i++){
			if((i%2) != 0){
				tempstr = s1[i].split("\\s+");
				cit[n - 1] = tempstr.length;
			}
			else{
				n++;
			}
		}
		
		return cit;
	}
	
	//����Դid���û�id�����Ƶ
	public int[][] countIdWords(String[] words, String s){
		String[] s1 = s.split("\\r?\\n");
		//����Ϊ��ǩ����������Ϊ��Դ���û�����
		int[][] idWordFreqs = new int[words.length][s1.length/2];
		String[] tempstr = null;
		int[] tempfreq = new int[words.length];
		int n = 0;
		System.out.println("***��ʼ�����Դ�ƵΪԪ�صĴ�-�ı�����***");
		for(int i = 0; i <= (s1.length/2); i ++){
			/**
			 * ż��ʱͳ�ƴ�Ƶ����ʼ����-�ı�����
			 * ����ʱ������ֵ
			 * iΪż��ʱ�����ǩ��iΪ����ʱ������Դ���û�id
			 * ��ʱԪ�ؽ�Ϊ��Ƶ
			 */
			if((i%2) != 0){
				for(int j = 0; j < words.length; j++){
					tempfreq[j] = 0;
				}
				tempstr = s1[i].split("\\s+");
				for(int j = 0; j < tempstr.length; j++){
					for(int k = 0; k < words.length; k++)
						if(tempstr[j].equals(words[k])){
							tempfreq[k] += 1;
						}
				}
				for(int j = 0; j < words.length; j++){
					idWordFreqs[j][i-n] = tempfreq[j];
				}
			}
			else{
				n++;
			}
		}
		System.out.println("***�����Դ�ƵΪԪ�صĴ�-�ı�����ɹ�***");
		System.out.println();
		return idWordFreqs;
	}
	
	//�������Ϸ���
//	public static void main(String args[]) throws Exception{
//		FreqCaculator fc = new FreqCaculator("data/test1.xls");
//		File file = new File("data/total_tags.txt");
//		File file1 = new File("data/resources_tags.txt");
//		String s = fc.tfu.readTxtFile(file);
//		String s1 = fc.tfu.readTxtFile(file1);
//		Map<String, Integer> wf = fc.countTotalWords(s);
//		String[] words = fc.words(s);
//		int[][] idWordFreqs = fc.countIdWords(words, s1);
////		for(int i = 0; i < words.length; i++){
////			if(words[i].equals("design")){
////				System.out.println(i);
////			}
////		}
//		System.out.println(words[5]);
//	}
}