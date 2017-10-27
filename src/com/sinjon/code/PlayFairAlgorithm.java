package com.sinjon.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 古典密码 playfair算法
 * 
 * @author sinjon
 * 
 */
public class PlayFairAlgorithm {

	private static boolean flag = true;
	public CopyOnWriteArrayList<Character> words = new CopyOnWriteArrayList<>();// 存放26个字母
	public String[][] datas = new String[6][6];// 存放加密矩阵

	public ArrayList<String> groupText = new ArrayList<>();// 分组的文本
	public ArrayList<String> encrypText = new ArrayList<>();// 分组加密/解密后的文本

	public static void main(String[] args) throws IOException {
		PlayFairAlgorithm playFairAlgorithm = new PlayFairAlgorithm();
		// 初始化数据
		playFairAlgorithm.initData();

		// 控制台输出
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("请输入关键字(关键字只能由26个字母组成),系统会根据所提供关键字生成加密矩阵");
		char[] keyword;
		while (true) {
			flag = true;
			keyword = br.readLine().trim().toCharArray();

			for (char c : keyword) {
				if (!(c >= 'a' && c <= 'z')) {
					System.out.println("输入内容格式不规范，请重新输入(输入内容只能包含26个字母)");
					flag = false;
					break;

				}

			}
			if (flag) {
				break;
			}

		} // while end

		playFairAlgorithm.createEncrypMatrix(keyword);// 创建加密矩阵
		System.out.println("加密矩阵");
		playFairAlgorithm.LogStringMatrix();// 打印加密矩阵
		System.out.println();
		System.out.println("请选择加密/解密,输入0/1代表选择加密/解密");
		int nextInt = Integer.parseInt(br.readLine().trim());
		while (nextInt != 0 && nextInt != 1) {
			System.out.println("输入内容格式不规范，请重新输入(0/1)");
			nextInt = Integer.parseInt(br.readLine().trim());
		}
		char[] word = {};
		if (nextInt == 0) {
			System.out.println("请输入要加密的字符串");
			String text = "";
			while (true) {
				flag = true;

				text = br.readLine();
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
			playFairAlgorithm.groupingText(text.toCharArray());// 对文本分组 两两一组
			System.out.println("加密后的文本");
			playFairAlgorithm.encrypGounpText();
		} else {
			System.out.println("请输入要解密的字符串(两位一组的字符串)");

			String text = "";
			while (true) {
				flag = true;

				text = br.readLine();
				// 去中间空格
				text = text.replaceAll(" ", "");
				System.out.println(text);
				// 判断字符串中间有无空格
				if ((text.length() % 2) != 0) {
					System.out.println("输入内容格式不规范，请重新输入(输入内容只能包含26个字母和空格,字母总数为偶数)");

					flag = false;

				}

				for (char c : text.toCharArray()) {
					if (!(c >= 'A' && c <= 'Z')) {
						System.out.println("输入内容格式不规范，请重新输入(输入内容只能包含26个字母和空格,字母总数为偶数)");
						flag = false;
						break;
					}

				}
				if (flag) {
					break;
				}

			} // while end

			// 分组
			playFairAlgorithm.groupingText(text);

			// 解密
			playFairAlgorithm.decodeGroupText();
		}

	}

	/**
	 * 对解密字符串分组
	 * 
	 * @param text
	 *            解密的字符串
	 */
	private void groupingText(String text) {

		int count = 0;
		String prev = "";
		for (char c : text.toCharArray()) {
			if (count == 2) {
				count = 0;

			}
			if (count == 0) {
				prev = c + "";

			} else {
				groupText.add("" + prev + c);
			}
			count++;
		}
		// 打印分组
		for (String str : groupText) {
			System.out.print(str + "  ");
		}
		System.out.println();
	}

	/**
	 * 对分好组的文本解密算法
	 */
	private void decodeGroupText() {
		encrypText.clear();
		for (String text : groupText) {

			String str1 = text.substring(0, 1);// 第一个字母
			String str2 = text.substring(1, 2);// 第二个字母
			if (str1.equals("I") || str1.equals("J")) {
				str1 = "IJ";
			}
			if (str2.equals("I") || str2.equals("J")) {
				str2 = "IJ";
			}
			int index1X = 0;// 第一个字母位置横坐标
			int index1Y = 0;// 第一个字母位置纵坐标
			int index2X = 0;// 第二个字母位置横坐标
			int index2Y = 0;// 第二个字母位置纵坐标
			boolean flag1 = false;
			boolean flag2 = false;
			// 搜索两个字母的位置
			i: for (int i = 0; i < 5; i++) {

				for (int j = 0; j < 5; j++) {
					if (datas[i][j].equals(str1)) {
						index1X = i;
						index1Y = j;
						flag1 = true;
					} else if (datas[i][j].equals(str2)) {
						index2X = i;
						index2Y = j;
						flag2 = true;
					}
					if (flag1 && flag2) {
						break i;
					}

				}

			} // for end

			// 根据字母位置进行解密
			if (index1X == index2X) {// 同一行
				// 取右边值作为密文
				if (index1Y == 0) {
					index1Y = 4;
					index2Y -= 1;
				} else if (index2Y == 0) {
					index2Y = 4;
					index1Y -= 1;
				} else {
					index1Y -= 1;
					index2Y -= 1;
				}
			} else if (index1Y == index2Y) {// 同一列
				// 去下边值作为密文
				if (index1X == 0) {
					index1X = 4;
					index2X -= 1;
				} else if (index2X == 0) {
					index2X = 4;
					index1X -= 1;
				} else {
					index1X -= 1;
					index2X -= 1;
				}

			} else {// 不同行不同列
				// 取对角值作为密文
				int temp = index1Y;
				index1Y = index2Y;
				index2Y = temp;
			}
			str1 = datas[index1X][index1Y];
			str2 = datas[index2X][index2Y];
			if (str1.equals("IJ")) {
				str1 = "I";
			}
			if (str2.equals("IJ")) {
				str2 = "I";
			}
			// 将密文添加到新的列表中

			encrypText.add(str1.toLowerCase() + str2.toLowerCase());
		}

		// 打印加密后的文本
		for (

		String str : encrypText) {
			System.out.print(str + "  ");
		}
	}

	/**
	 * 对分好组的text加密
	 */
	private void encrypGounpText() {
		for (String text : groupText) {

			String str1 = text.substring(0, 1).toUpperCase();// 第一个字母
			String str2 = text.substring(1, 2).toUpperCase();// 第二个字母
			if (str1.equals("I") || str1.equals("J")) {
				str1 = "IJ";
			}
			if (str2.equals("I") || str2.equals("J")) {
				str2 = "IJ";
			}
			int index1X = 0;// 第一个字母位置横坐标
			int index1Y = 0;// 第一个字母位置纵坐标
			int index2X = 0;// 第二个字母位置横坐标
			int index2Y = 0;// 第二个字母位置纵坐标
			boolean flag1 = false;
			boolean flag2 = false;
			// 搜索两个字母的位置
			i: for (int i = 0; i < 5; i++) {

				for (int j = 0; j < 5; j++) {
					if (datas[i][j].equals(str1)) {
						index1X = i;
						index1Y = j;
						flag1 = true;
					} else if (datas[i][j].equals(str2)) {
						index2X = i;
						index2Y = j;
						flag2 = true;
					}
					if (flag1 && flag2) {
						break i;
					}

				}

			} // for end

			// 根据字母位置进行加密
			if (index1X == index2X) {// 同一行
				// 取右边值作为密文
				index1Y += 1;
				index2Y += 1;
			} else if (index1Y == index2Y) {// 同一列
				// 去下边值作为密文
				index1X += 1;
				index2X += 1;
			} else {// 不同行不同列
				// 取对角值作为密文
				int temp = index1Y;
				index1Y = index2Y;
				index2Y = temp;
			}
			str1 = datas[index1X][index1Y];
			str2 = datas[index2X][index2Y];
			if (str1.equals("IJ")) {
				str1 = "I";
			}
			if (str2.equals("IJ")) {
				str2 = "I";
			}
			// 将密文添加到新的列表中
			encrypText.add(str1 + str2);
		}

		// 打印加密后的文本
		for (String str : encrypText) {
			System.out.print(str + "  ");
		}
	}

	/**
	 * 对文本进行两两分组 若两两相邻文本相同 则用k分开
	 */
	private void groupingText(char[] text) {

		int i = 0;
		int count = 1;
		char prev = 0;

		for (char c : text) {
			if (i == 2) {
				i = 0;
			}
			if (i == 0) {
				prev = c;
				if (text.length == count) {
					groupText.add(prev + "k");
				}
				i++;
			} else if (i == 1) {
				if (c != prev) {
					groupText.add("" + prev + c);
					i++;
				} else {
					groupText.add(prev + "k");

				}
			}
			count++;
		} // for end
		for (String str : groupText) {
			System.out.print(str + "  ");
		}
		System.out.println();
	}

	/**
	 * 打印加密矩阵
	 */
	private void LogStringMatrix() {
		int count = 0;
		for (String[] strings : datas) {
			for (String string : strings) {

				if (count == 6) {
					System.out.println();
					count = 0;
				}
				if (string == "IJ") {
					System.out.print(string + "    ");
				} else {
					System.out.print(string + "     ");
				}

				count += 1;
			}
		}
	}

	/**
	 * 初始化26个字母 用于加密矩阵的生成
	 */
	private void initData() {
		for (int i = 0; i <= 25; i++) {
			words.add((char) ('a' + i));
		}
	}

	/**
	 * 生成加密矩阵
	 * 
	 * @param keyword
	 *            关键字
	 */
	private void createEncrypMatrix(char[] keyword) {

		int i = 0;
		int j = 0;
		String temp = "";
		// 根据关键字生成矩阵
		for (char c : keyword) {
			if (words.contains(c)) {

				if (j == 5) {
					j = 0;
					i += 1;
				}
				if (c == 'i' || c == 'j') {
					datas[i][j] = "IJ";

					words.remove(words.indexOf('i'));

					words.remove(words.indexOf('j'));

				} else {
					temp = c + "";
					datas[i][j] = temp.toUpperCase();

					words.remove(words.indexOf(c));

				}
				j += 1;
			}

		}

		// 将关键字不存在的字母依次加入矩阵
		for (Character c : words) {

			if (j == 5) {
				j = 0;
				i += 1;
			}
			if (c == 'i') {
				datas[i][j] = "IJ";

			} else {
				if (c == 'j') {
					continue;
				}
				temp = c + "";
				datas[i][j] = temp.toUpperCase();

			}

			j += 1;
		} // for end

		// 将第一行复制到第六行 第一列复制到第六列
		for (int k = 0; k < 5; k++) {
			datas[k][5] = datas[k][0];
			datas[5][k] = datas[0][k];
		}
		datas[5][5] = datas[4][4];

	}

}
