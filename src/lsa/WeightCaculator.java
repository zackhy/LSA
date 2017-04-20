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
	
	public double[] tagWeight(){
		int tfreq;
		double[] tw = new double[words.length];
		for(int i = 0; i < words.length; i++){
			tfreq = wordFreqs.get(words[i]);  
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
	
	public double[] docWeight(){
		int docfreq;
		double[] dw = new double[idWordFreqs[0].length];
		for(int i = 0; i < idWordFreqs[0].length; i++){
			docfreq = tic[i];
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
	
	// Wg(i,j) = Wp(i,j) * Wt(i) * Wd(j)
	public double[][] globalWeight(double[][] pw, double[] tw, double[] dw){
		double[][] gw = new double[idWordFreqs.length][idWordFreqs[0].length];
		System.out.println("***Building weighted document-term matrix***");
		for(int i = 0; i < idWordFreqs.length; i++){
			for(int j = 0; j < idWordFreqs[0].length; j++){
				double temp = 0;
				temp = Math.abs(pw[i][j]*tw[i]*dw[j]);
				gw[i][j] = temp;
			}
		}
		System.out.println("***Weighted document-term matrix built***");
		System.out.println();
		return gw;
	}
	
	// log
	private double log(double value, double base){
		return Math.log(value)/Math.log(base);
	}

}
