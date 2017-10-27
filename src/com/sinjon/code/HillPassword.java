package com.sinjon.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 古典密码 希尔密码
 * 
 * @author sinjon
 *
 */
public class HillPassword {
	private static boolean flag = false;
	public ArrayList<Character> words = new ArrayList<>();// 存放26个字母
	public static int[][] keys = new int[][] { { 17, 17, 5 }, { 21, 18, 21 }, { 2, 2, 19 } };// 存放密钥矩阵
	public static int[][] antikeys = new int[3][3];// 存放密钥矩阵
	public static int[][] unitMatrix = new int[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };// 单位矩阵

	public ArrayList<String> groupText = new ArrayList<>();// 明文分组的文本
	public ArrayList<int[]> groupNumber = new ArrayList<>();// 分组的文本值(将字母转换成所在位置)
	public ArrayList<String> encrypGroupText = new ArrayList<>();// 明文分组加密后的文本
	public static int[] temp = { 1, 0, 0, };
	public ArrayList<String> cipherGroupText = new ArrayList<>();// 密文分组
	public ArrayList<String> decodeGroupText = new ArrayList<>();// 明文分组加密后的文本

	/**
	 * 初始化26个字母 用于加密矩阵的生成
	 */
	private void initData() {
		for (int i = 0; i <= 25; i++) {
			words.add((char) ('a' + i));
		}
	}

	/**
	 * 主方法
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		HillPassword hillPassword = new HillPassword();
		hillPassword.initData();
		// 控制台输出
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入要加密的字符串");
		String text = "";
		while (true) {
			flag = true;

			text = br.readLine().trim();
			// 去中间空格
			text = text.replaceAll(" ", "");
			// 判断字符串中间有无空格
			for (char c : text.toCharArray()) {
				if (!(c >= 'a' && c <= 'z')) {
					System.out.println("输入内容格式不规范，请重新输入(输入内容只能包含26个字母和空格)");
					flag = false;
					break;
				}

			}
			if (flag) {
				break;
			}

		} // while end

		// 分成三个三个一组
		System.out.println("将字符串拆分:");
		hillPassword.groupingText(text);
		// 将分组中的字母转换成文字
		hillPassword.convertGroupText(hillPassword.groupText);
		// 明文加密
		hillPassword.encrypOrDecodeText(keys);

		// 密文文本
		text = "LNSHDLEWMTRW";
		System.out.println("需要解密的密文："+text);
		// 密文解密
		String plainText = hillPassword.decodeCipherText(text);
		System.out.println("解密后的明文为：");
		System.out.println(plainText.toLowerCase());
	}

	/**
	 * 对密文解密
	 */
	private void encrypOrDecodeText(int[][] keys) {

		int i = 0;
		for (int[] number : groupNumber) {
			Character c1 = words
					.get((int) ((number[0] * keys[i][0] + number[1] * keys[i][1] + number[2] * keys[i][2]) % 26));
			i++;

			Character c2 = words
					.get((int) ((number[0] * keys[i][0] + number[1] * keys[i][1] + number[2] * keys[i][2]) % 26));
			i++;
			Character c3 = words
					.get((int) ((number[0] * keys[i][0] + number[1] * keys[i][1] + number[2] * keys[i][2]) % 26));
			i = 0;
			if (keys.equals(HillPassword.keys)) {
				encrypGroupText.add((c1 + " ").toUpperCase() + (c2 + " ").toUpperCase() + (c3 + "").toUpperCase());

			} else {
				decodeGroupText.add((c1 + " ") + (c2 + " ") + (c3 + ""));
			}

		}
		if (keys.equals(HillPassword.keys)) {
			System.out.println("加密结果:");
			for (String string : encrypGroupText) {
				System.out.println(string);
			}
		} else {
			System.out.println("解密结果:");
			for (String string : decodeGroupText) {
				System.out.println(string);
			}
		}

	}

	/**
	 * 将分组中的字母转换成数字
	 */
	private void convertGroupText(ArrayList<String> groupText) {
		groupNumber.clear();
		int j = 0;
		for (String str : groupText) {
			int[] temp = new int[3];
			for (char c : str.toCharArray()) {

				temp[j++] = words.indexOf(c);

				if (j == 3) {
					groupNumber.add(temp);
					j = 0;
				}

			}
		}
		System.out.println("将分组类型转换数值结果:");
		for (int[] number : groupNumber) {
			for (int i : number) {
				System.out.print(i + "  ");
			}
			System.out.println();
		}

	}

	/**
	 * 对明文进行分组 三个一组
	 * 
	 * @param text
	 */
	private void groupingText(String text) {
		int count = 1;
		String first = "";
		String second = "";
		int i = 0;
		for (char c : text.toCharArray()) {
			if (i == 3) {
				i = 0;

			}
			if (i == 0) {
				first = c + "";
				if (count == text.length()) {
					groupText.add(first + "xx");
				}

			} else if (i == 1) {
				second = c + "";
				if (count == text.length()) {
					groupText.add(first + second + "x");
				}
			} else {
				groupText.add("" + first + second + c);
			}
			i++;
			count++;
		}
		// 打印分组
		for (String str : groupText) {
			System.out.print(str + "  ");
		}
		System.out.println();
	}

	/**
	 * 
	 * @param ciphertext
	 *            密文
	 * @return
	 */
	private String decodeCipherText(String ciphertext) {
		/// 获得逆矩阵
		getReverseMartrix(keys);
		System.out.println("加密密钥逆矩阵为：");
		// 输出逆矩阵
		showMatrix(antikeys);
		return getDecryptText(antikeys, ciphertext);
	}

	/**
	 * 
	 * @param antiKeys
	 *            密钥矩阵
	 * @param ciphertext
	 *            密文
	 * @return 返回明文
	 */
	private String getDecryptText(int[][] antiKeys, String ciphertext) {
		int temp1, temp2, temp3;
		StringBuilder plainText = new StringBuilder(); // 存放明文
		for (int i = 0; i < ciphertext.length(); i += 3) {
			temp1 = antiKeys[0][0] * (ciphertext.charAt(i) - 'A') + antiKeys[0][1] * (ciphertext.charAt(i + 1) - 'A')
					+ antiKeys[0][2] * (ciphertext.charAt(i + 2) - 'A');
			temp2 = antiKeys[1][0] * (ciphertext.charAt(i) - 'A') + antiKeys[1][1] * (ciphertext.charAt(i + 1) - 'A')
					+ antiKeys[1][2] * (ciphertext.charAt(i + 2) - 'A');
			temp3 = antiKeys[2][0] * (ciphertext.charAt(i) - 'A') + antiKeys[2][1] * (ciphertext.charAt(i + 1) - 'A')
					+ antiKeys[2][2] * (ciphertext.charAt(i + 2) - 'A');
			plainText.append((char) ('A' + temp1 % 26));
			plainText.append((char) ('A' + temp2 % 26));
			plainText.append((char) ('A' + temp3 % 26));
		}

		return plainText.toString();
	}

	/**
	 * 使用穷举法获得矩阵的逆
	 * 
	 * @param antiKeys
	 *            存放逆矩阵
	 */
	private void getReverseMartrix(int[][] antiKeys) {

		for (int num = 0; num < 3; num++)
			for (int i = 0; i < 26; i++)
				for (int j = 0; j < 26; j++)
					for (int k = 0; k < 26; k++) {
						if ((i * 17 + j * 21 + k * 2) % 26 == temp[num % 3]
								&& ((i * 17 + j * 18 + k * 2) % 26 == temp[(num + 2) % 3])
								&& ((i * 5 + j * 21 + k * 19) % 26 == temp[(num + 1) % 3])) {
							antikeys[num][0] = i;
							antikeys[num][1] = j;
							antikeys[num][2] = k;
						}
					}

	}

	/**
	 * 打印矩阵
	 * 
	 * @param keys
	 */
	private void showMatrix(int[][] keys) {
		for (int i = 0; i < keys.length; i++) {
			for (int j = 0; j < keys[0].length; j++) {
				System.out.print(keys[i][j] + " ");
			}
			System.out.println();
		}
	}

}
