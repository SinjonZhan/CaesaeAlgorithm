package com.sinjon.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * �ŵ����� playfair�㷨
 * 
 * @author sinjon
 * 
 */
public class PlayFairAlgorithm {

	private static boolean flag = true;
	public CopyOnWriteArrayList<Character> words = new CopyOnWriteArrayList<>();// ���26����ĸ
	public String[][] datas = new String[6][6];// ��ż��ܾ���

	public ArrayList<String> groupText = new ArrayList<>();// ������ı�
	public ArrayList<String> encrypText = new ArrayList<>();// �������/���ܺ���ı�

	public static void main(String[] args) throws IOException {
		PlayFairAlgorithm playFairAlgorithm = new PlayFairAlgorithm();
		// ��ʼ������
		playFairAlgorithm.initData();

		// ����̨���
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("������ؼ���(�ؼ���ֻ����26����ĸ���),ϵͳ��������ṩ�ؼ������ɼ��ܾ���");
		char[] keyword;
		while (true) {
			flag = true;
			keyword = br.readLine().trim().toCharArray();

			for (char c : keyword) {
				if (!(c >= 'a' && c <= 'z')) {
					System.out.println("�������ݸ�ʽ���淶������������(��������ֻ�ܰ���26����ĸ)");
					flag = false;
					break;

				}

			}
			if (flag) {
				break;
			}

		} // while end

		playFairAlgorithm.createEncrypMatrix(keyword);// �������ܾ���
		System.out.println("���ܾ���");
		playFairAlgorithm.LogStringMatrix();// ��ӡ���ܾ���
		System.out.println();
		System.out.println("��ѡ�����/����,����0/1����ѡ�����/����");
		int nextInt = Integer.parseInt(br.readLine().trim());
		while (nextInt != 0 && nextInt != 1) {
			System.out.println("�������ݸ�ʽ���淶������������(0/1)");
			nextInt = Integer.parseInt(br.readLine().trim());
		}
		char[] word = {};
		if (nextInt == 0) {
			System.out.println("������Ҫ���ܵ��ַ���");
			String text = "";
			while (true) {
				flag = true;

				text = br.readLine();
				// ȥ�м�ո�
				text = text.replaceAll(" ", "");
				// �ж��ַ����м����޿ո�
				for (char c : text.toCharArray()) {
					if (!(c >= 'a' && c <= 'z')) {
						System.out.println("�������ݸ�ʽ���淶������������(��������ֻ�ܰ���26����ĸ�Ϳո�)");
						flag = false;
						break;
					}

				}
				if (flag) {
					break;
				}

			} // while end
			playFairAlgorithm.groupingText(text.toCharArray());// ���ı����� ����һ��
			System.out.println("���ܺ���ı�");
			playFairAlgorithm.encrypGounpText();
		} else {
			System.out.println("������Ҫ���ܵ��ַ���(��λһ����ַ���)");

			String text = "";
			while (true) {
				flag = true;

				text = br.readLine();
				// ȥ�м�ո�
				text = text.replaceAll(" ", "");
				System.out.println(text);
				// �ж��ַ����м����޿ո�
				if ((text.length() % 2) != 0) {
					System.out.println("�������ݸ�ʽ���淶������������(��������ֻ�ܰ���26����ĸ�Ϳո�,��ĸ����Ϊż��)");

					flag = false;

				}

				for (char c : text.toCharArray()) {
					if (!(c >= 'A' && c <= 'Z')) {
						System.out.println("�������ݸ�ʽ���淶������������(��������ֻ�ܰ���26����ĸ�Ϳո�,��ĸ����Ϊż��)");
						flag = false;
						break;
					}

				}
				if (flag) {
					break;
				}

			} // while end

			// ����
			playFairAlgorithm.groupingText(text);

			// ����
			playFairAlgorithm.decodeGroupText();
		}

	}

	/**
	 * �Խ����ַ�������
	 * 
	 * @param text
	 *            ���ܵ��ַ���
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
		// ��ӡ����
		for (String str : groupText) {
			System.out.print(str + "  ");
		}
		System.out.println();
	}

	/**
	 * �Էֺ�����ı������㷨
	 */
	private void decodeGroupText() {
		encrypText.clear();
		for (String text : groupText) {

			String str1 = text.substring(0, 1);// ��һ����ĸ
			String str2 = text.substring(1, 2);// �ڶ�����ĸ
			if (str1.equals("I") || str1.equals("J")) {
				str1 = "IJ";
			}
			if (str2.equals("I") || str2.equals("J")) {
				str2 = "IJ";
			}
			int index1X = 0;// ��һ����ĸλ�ú�����
			int index1Y = 0;// ��һ����ĸλ��������
			int index2X = 0;// �ڶ�����ĸλ�ú�����
			int index2Y = 0;// �ڶ�����ĸλ��������
			boolean flag1 = false;
			boolean flag2 = false;
			// ����������ĸ��λ��
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

			// ������ĸλ�ý��н���
			if (index1X == index2X) {// ͬһ��
				// ȡ�ұ�ֵ��Ϊ����
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
			} else if (index1Y == index2Y) {// ͬһ��
				// ȥ�±�ֵ��Ϊ����
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

			} else {// ��ͬ�в�ͬ��
				// ȡ�Խ�ֵ��Ϊ����
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
			// ��������ӵ��µ��б���

			encrypText.add(str1.toLowerCase() + str2.toLowerCase());
		}

		// ��ӡ���ܺ���ı�
		for (

		String str : encrypText) {
			System.out.print(str + "  ");
		}
	}

	/**
	 * �Էֺ����text����
	 */
	private void encrypGounpText() {
		for (String text : groupText) {

			String str1 = text.substring(0, 1).toUpperCase();// ��һ����ĸ
			String str2 = text.substring(1, 2).toUpperCase();// �ڶ�����ĸ
			if (str1.equals("I") || str1.equals("J")) {
				str1 = "IJ";
			}
			if (str2.equals("I") || str2.equals("J")) {
				str2 = "IJ";
			}
			int index1X = 0;// ��һ����ĸλ�ú�����
			int index1Y = 0;// ��һ����ĸλ��������
			int index2X = 0;// �ڶ�����ĸλ�ú�����
			int index2Y = 0;// �ڶ�����ĸλ��������
			boolean flag1 = false;
			boolean flag2 = false;
			// ����������ĸ��λ��
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

			// ������ĸλ�ý��м���
			if (index1X == index2X) {// ͬһ��
				// ȡ�ұ�ֵ��Ϊ����
				index1Y += 1;
				index2Y += 1;
			} else if (index1Y == index2Y) {// ͬһ��
				// ȥ�±�ֵ��Ϊ����
				index1X += 1;
				index2X += 1;
			} else {// ��ͬ�в�ͬ��
				// ȡ�Խ�ֵ��Ϊ����
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
			// ��������ӵ��µ��б���
			encrypText.add(str1 + str2);
		}

		// ��ӡ���ܺ���ı�
		for (String str : encrypText) {
			System.out.print(str + "  ");
		}
	}

	/**
	 * ���ı������������� �����������ı���ͬ ����k�ֿ�
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
	 * ��ӡ���ܾ���
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
	 * ��ʼ��26����ĸ ���ڼ��ܾ��������
	 */
	private void initData() {
		for (int i = 0; i <= 25; i++) {
			words.add((char) ('a' + i));
		}
	}

	/**
	 * ���ɼ��ܾ���
	 * 
	 * @param keyword
	 *            �ؼ���
	 */
	private void createEncrypMatrix(char[] keyword) {

		int i = 0;
		int j = 0;
		String temp = "";
		// ���ݹؼ������ɾ���
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

		// ���ؼ��ֲ����ڵ���ĸ���μ������
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

		// ����һ�и��Ƶ������� ��һ�и��Ƶ�������
		for (int k = 0; k < 5; k++) {
			datas[k][5] = datas[k][0];
			datas[5][k] = datas[0][k];
		}
		datas[5][5] = datas[4][4];

	}

}
