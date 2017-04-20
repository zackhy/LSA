package com.priv.thesis.lsa;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.io.File;

import com.priv.thesis.file.*;

public class cosineCaculator {
	
	private SvdDecompositions sd = null;
	private SingularValueDecomposition svd = null;
	private Matrix U = null;
	private Matrix Sigma = null;
	private Matrix V = null;
	private int k;
	private Matrix Mk = null;
	private Matrix Uk = null;
	private Matrix Sk = null;
	private Matrix Vk = null;
	
	public cosineCaculator(String filename, String s, String s1) throws Exception{
		sd = new SvdDecompositions(filename, s, s1);
		svd = sd.svdCaculator();
		U = sd.getLeft(svd);
		Sigma = sd.getSigma(svd);
		V = sd.getRight(svd);
		k = sd.getK(Sigma);
		Uk = sd.getUk(k, U);
		Sk = sd.getSk(k, Sigma);
		Vk = sd.getVk(k, V);
		Mk = sd.getSemanticSpace(Uk, Sk, Vk);
	}
	
	public Matrix cosineT(){
		double[][] uk = Uk.getArrayCopy();
		double[][] ct = new double[uk.length][uk.length];
		double c = 0;
		int i;
		int j;
		for(i = 0; i < uk.length; i++){
			double[] tempu = uk[i];
			for(j = 0; j < uk.length; j++){
				c = cosine(uk[i], uk[j]);
				ct[i][j] = c;
			}
		}
		Matrix CT = new Matrix(ct);
		return CT;
	}
	
	public Matrix cosineRU(){
		double[][] vk = Vk.getArrayCopy();
		double[][] cru = new double[vk.length][vk.length];
		double c = 0;
		for(int i = 0; i < vk.length; i++){
			double[] tempu = vk[i];
			for(int j = 0; j < vk.length; j++){
				c = cosine(vk[i], vk[j]);
				cru[i][j] = c;
			}
		}
		Matrix CRU = new Matrix(cru);
		return CRU;
	}
	
	public double[][] cosineTag(){
		double[][] ct = new double[sd.gw.length][sd.gw.length];
		double c = 0;
		int i;
		int j;
		for(i = 0; i < sd.gw.length; i++){
			double[] tempu = sd.gw[i];
			for(j = 0; j < sd.gw.length; j++){
				c = cosine(sd.gw[i], sd.gw[j]);
				ct[i][j] = c;
			}
		}
		return ct;
	}
	
	// Calculate cosine
	private double cosine(double[] a, double[] b){
		double ma = 0;
		double mb = 0;
		double mab = 0;
		for(int i = 0; i < a.length; i++){
			ma += a[i]*a[i];
			mb += b[i]*b[i];
			mab += a[i]*b[i];
		}
		double moduloa = Math.sqrt(ma);
		double modulob = Math.sqrt(mb);
		double cosine = 0;
		if((moduloa*modulob) != 0){
			cosine = (mab)/(moduloa*modulob);
		}
		else{
			cosine = 0;
		}
		return Math.abs(cosine);
	}
}
