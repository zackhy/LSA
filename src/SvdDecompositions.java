package com.priv.thesis.lsa;

import java.io.File;
import com.priv.thesis.file.*;

import com.priv.thesis.file.*;
import Jama.Matrix;
import Jama.SingularValueDecomposition;
/**
 * Print the matrix to the output stream
 * @param 1 Column width.
 * @param 2 Number of digits after the decimal. 
 * m.print(0,5)
 */
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
	
	//奇异值分解，使用JAMA包,方便许多
	public SingularValueDecomposition svdCaculator(){
		System.out.println("***开始进行奇异值分解***");
		Matrix m =new Matrix(gw);
		SingularValueDecomposition svd = m.svd();
//		m.print(0, 5);
		System.out.println("***奇异值分解成功***");
		System.out.println();
		return svd;
	}
	
	//得到左奇异向量U
	public Matrix getLeft(SingularValueDecomposition svd){
		Matrix U = svd.getU();
		return U;
	}
	
	//得到右奇异向量V
	public Matrix getRight(SingularValueDecomposition svd){
		Matrix V = svd.getV();
		return V;
	}
	
	//得到奇异值Sigma
	public Matrix getSigma(SingularValueDecomposition svd){
		Matrix S = svd.getS();
		return S;
	}
	
	//得到k
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
	
	//得到k阶奇异值向量
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
	
	//得到k阶右奇异值向量
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
	
	//得到k阶左奇异值向量
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
	
	
	//得到Mk阶近似矩阵，即LSA潜在语义空间	
	public Matrix getSemanticSpace(Matrix Uk, Matrix Sk, Matrix Vk){
		System.out.println("***开始构建语义空间***");
		Matrix Mk = Uk.times(Sk).times(Vk);//k阶近似矩阵，即语义空间
		System.out.println("***语义空间构建成功***");
		System.out.println();
		return Mk;
	}
	
	//测试以上方法
//	public static void main(String[] args) throws Exception{
//		File file = new File("data/total_tags.txt");
//		File file1 = new File("data/resources_tags.txt");
//		TXTFileUtil tfu = new TXTFileUtil("data/test1.xls");
//		String s = tfu.readTxtFile(file);
//		String s1 = tfu.readTxtFile(file1);
//		SvdDecompositions sd = new SvdDecompositions("data/test1.xls", s, s1);
//		SingularValueDecomposition svd = sd.svdCaculator();
//		Matrix U = sd.getLeft(svd);
//		Matrix Sigma = sd.getSigma(svd
//		Matrix V = sd.getRight(svd);
//		int k = sd.getK(Sigma);
//		Matrix Uk = sd.getUk(k, U);
////		Uk.print(0,5);
////		System.out.println(Uk.get(1, 1));
//		Matrix Sk = sd.getSk(k, Sigma);
////		Sk.print(0, 5);
//		Matrix Vk = sd.getVk(k, V);
////		Vk.print(0, 5);
//		Matrix m = sd.getSemanticSpace(Uk, Sk, Vk);
////		m.print(0, 5);
//	}

}
