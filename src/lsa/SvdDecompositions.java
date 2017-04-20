package com.priv.thesis.lsa;

import java.io.File;
import com.priv.thesis.file.*;

import com.priv.thesis.file.*;
import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class SvdDecompositions {
	
	private WeightCaculator wc = null;
	private double[][] pw = null;
	private double[] tw = null;
	private double[] dw = null;
	public double[][] gw = null;
	
	public SvdDecompositions(String filename, String s, String s1) throws Exception{
		wc = new WeightCaculator(filename, s, s1);
		pw = wc.partialWeight();
		tw = wc.tagWeight();
		dw = wc.docWeight();
		gw = wc.globalWeight(pw, tw, dw);
	}
	
	// Singular Value Decomposition
	public SingularValueDecomposition svdCaculator(){
		System.out.println("***开始进行奇异值分解***");
		Matrix m =new Matrix(gw);
		SingularValueDecomposition svd = m.svd();
//		m.print(0, 5);
		System.out.println("***奇异值分解成功***");
		System.out.println();
		return svd;
	}
	
	// Left-singular vectors
	public Matrix getLeft(SingularValueDecomposition svd){
		Matrix U = svd.getU();
		return U;
	}
	
	// Right-singular vectors
	public Matrix getRight(SingularValueDecomposition svd){
		Matrix V = svd.getV();
		return V;
	}
	
	// Singular value
	public Matrix getSigma(SingularValueDecomposition svd){
		Matrix S = svd.getS();
		return S;
	}
	
	// K value
	public int getK(Matrix Sigma){
		double[][] sigma = Sigma.getArrayCopy();
		int k = 0;
		for(int i = 0; i < sigma.length; i++){
			for(int j = 0; j < sigma[0].length; j++){
				if(i == j && sigma[i][j] != 0){
					k = i+1;
					break;
				}
			}
		}
		return k;
	}
	
	// K-order singular vectors
	public Matrix getSk(int k, Matrix Sigma){
		double[][] sigma = Sigma.getArrayCopy();
		double[][] sk = new double[k][k];
		for(int i = 0; i < k; i++){
			for(int j = 0; j < k; j++){
				sk[i][j] = sigma[i][j];
			}
		}
		Matrix Sk = new Matrix(sk);
		return Sk;
	}
	
	// K-order left-singular vectors
	public Matrix getVk(int k, Matrix V){
		double[][] v = V.getArrayCopy();
		double[][] vk = new double[k][v.length]; 
		for(int i = 0; i < k; i++){
			for(int j = 0; j < v.length; j++){
				vk[i][j] = v[i][j];
			}
		}
		Matrix Vk = new Matrix(vk);
		return Vk;
	}
	
	// K-order right-singular vectors
		public Matrix getUk(int k, Matrix U){
			double[][] u = U.getArrayCopy();
			double[][] uk = new double[u.length][k]; 
			for(int i = 0; i < u.length; i++){
				for(int j = 0; j < k; j++){
					uk[i][j] = u[i][j];
				}
			}
			Matrix Uk = new Matrix(uk);
			return Uk;
		}
	
	
	// Latent semantic space
	public Matrix getSemanticSpace(Matrix Uk, Matrix Sk, Matrix Vk){
		System.out.println("***Building latent semantic space***");
		Matrix Mk = Uk.times(Sk).times(Vk);//k阶近似矩阵，即语义空间
		System.out.println("***Latent semantic space built***");
		System.out.println();
		return Mk;
	}

}
