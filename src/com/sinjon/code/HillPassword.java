package com.sinjon.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * �ŵ����� ϣ������
 * 
 * @author sinjon
 *
 */
public class HillPassword {
	private static boolean flag = false;
	public ArrayList<Character> words = new ArrayList<>();// ���26����ĸ
	public static int[][] keys = new int[][] { { 17, 17, 5 }, { 21, 18, 21 }, { 2, 2, 19 } };// �����Կ����
	public static int[][] antikeys = new int[3][3];// �����Կ����
	public static int[][] unitMatrix = new int[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };// ��λ����

	public ArrayList<String> groupText = new ArrayList<>();// ���ķ�����ı�
	public ArrayList<int[]> groupNumber = new ArrayList<>();// ������ı�ֵ(����ĸת��������λ��)
	public ArrayList<String> encrypGroupText = new ArrayList<>();// ���ķ�����ܺ���ı�
	public static int[] temp = { 1, 0, 0, };
	public ArrayList<String> cipherGroupText = new ArrayList<>();// ���ķ���
	public ArrayList<String> decodeGroupText = new ArrayList<>();// ���ķ�����ܺ���ı�

	/**
	 * ��ʼ��26����ĸ ���ڼ��ܾ��������
	 */
	private void initData() {
		for (int i = 0; i <= 25; i++) {
			words.add((char) ('a' + i));
		}
	}

	/**
	 * ������
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		HillPassword hillPassword = new HillPassword();
		hillPassword.initData();
		// ����̨���
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("������Ҫ���ܵ��ַ���");
		String text = "";
		while (true) {
			flag = true;

			text = br.readLine().trim();
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

		// �ֳ���������һ��
		System.out.println("���ַ������:");
		hillPassword.groupingText(text);
		// �������е���ĸת��������
		hillPassword.convertGroupText(hillPassword.groupText);
		// ���ļ���
		hillPassword.encrypOrDecodeText(keys);

		// �����ı�
		text = "LNSHDLEWMTRW";
		System.out.println("��Ҫ���ܵ����ģ�"+text);
		// ���Ľ���
		String plainText = hillPassword.decodeCipherText(text);
		System.out.println("���ܺ������Ϊ��");
		System.out.println(plainText.toLowerCase());
	}

	/**
	 * �����Ľ���
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
			System.out.println("���ܽ��:");
			for (String string : encrypGroupText) {
				System.out.println(string);
			}
		} else {
			System.out.println("���ܽ��:");
			for (String string : decodeGroupText) {
				System.out.println(string);
			}
		}

	}

	/**
	 * �������е���ĸת��������
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
		System.out.println("����������ת����ֵ���:");
		for (int[] number : groupNumber) {
			for (int i : number) {
				System.out.print(i + "  ");
			}
			System.out.println();
		}

	}

	/**
	 * �����Ľ��з��� ����һ��
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
		// ��ӡ����
		for (String str : groupText) {
			System.out.print(str + "  ");
		}
		System.out.println();
	}

	/**
	 * 
	 * @param ciphertext
	 *            ����
	 * @return
	 */
	private String decodeCipherText(String ciphertext) {
		/// ��������
		getReverseMartrix(keys);
		System.out.println("������Կ�����Ϊ��");
		// ��������
		showMatrix(antikeys);
		return getDecryptText(antikeys, ciphertext);
	}

	/**
	 * 
	 * @param antiKeys
	 *            ��Կ����
	 * @param ciphertext
	 *            ����
	 * @return ��������
	 */
	private String getDecryptText(int[][] antiKeys, String ciphertext) {
		int temp1, temp2, temp3;
		StringBuilder plainText = new StringBuilder(); // �������
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
	 * ʹ����ٷ���þ������
	 * 
	 * @param antiKeys
	 *            ��������
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
	 * ��ӡ����
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
