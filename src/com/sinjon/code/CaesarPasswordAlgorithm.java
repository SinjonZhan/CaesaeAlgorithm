package com.sinjon.code;

import java.util.Scanner;

/**
 * 古典密码 凯撒密码
 * 
 * @author sinjon
 *
 */
public class CaesarPasswordAlgorithm {
	public static boolean flag = true;
	public char[] words = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static void main(String[] args) {
		// 加密算法 密钥k
		CaesarPasswordAlgorithm caesarAlgorithm = new CaesarPasswordAlgorithm();
		System.out.println("请输入密钥k(int k,k>0&&k<26)");
		Scanner scanner = new Scanner(System.in);
		int k = scanner.nextInt();
		while (!(k > 0 && k < 26)) {
			System.out.println("密钥k不满足要求,请重新输入（int k,k>0&&k<26）");
			k = scanner.nextInt();
		} 

		System.out.println("密钥k：" + k);
		System.out.println("明文：");
		System.out.print(caesarAlgorithm.words);
		System.out.println();
		System.out.println("密文：");
		caesarAlgorithm.encryptWord(k, caesarAlgorithm.words);
		System.out.println();
		System.out.println("请选择加密/解密,输入0/1代表选择加密/解密");
		int nextInt = scanner.nextInt();
		while (nextInt != 0 && nextInt != 1) {
			System.out.println("输入内容格式不规范，请重新输入(0/1)");
			nextInt = scanner.nextInt();
		}
		char[] word = {};
		if (nextInt == 0) {
			System.out.println("请输入要加密的字符串");
		} else {
			System.out.println("请输入要解密的字符串");
		}

		while (true) {
			flag = true;
			word = scanner.next().toCharArray();
			for (char c : word) {
				if (!(c >= 'a' && c <= 'z')) {
					System.out.println("输入内容格式不规范，请重新输入(输入内容只能包含26个字母)");
					flag = false;
					break;

				}

			}
			if (flag) {
				break;
			}

		}

		if (nextInt == 0) {
			System.out.println("加密结果：");
			caesarAlgorithm.encryptWord(k, word);
			System.out.println();
		} else {
			System.out.println("解密结果：");
			caesarAlgorithm.decodeWord(k, word);
			System.out.println();
		}

	}

	/**
	 * 解密算法
	 * 
	 * @param k
	 *            密钥
	 * @param word
	 *            密文
	 */
	private void decodeWord(int k, char[] c) {
		for (char word : c) {
			// 密文满足条件
			if (word >= 'a' && word <= 'z') {

				word = (char) (word - k);
				if (!(word >= 'a' && word <= 'z')) {

					word = (char) (word + 26);
				}
				word = (char) Character.toUpperCase(word);
				System.out.print(word);
			}
		}
	}

	/**
	 * 加密算法
	 * 
	 * @param k
	 *            密钥
	 * @param word
	 *            明文
	 */
	private void encryptWord(int k, char[] c) {
		for (char word : c) {
			// 明文满足条件
			if (word >= 'a' && word <= 'z') {

				word = (char) (word + k);
				if (!(word >= 'a' && word <= 'z')) {

					word = (char) (word % 122 + 97 - 1);
				}
				word = (char) Character.toUpperCase(word);
				System.out.print(word);
			}
		}
	}
}
