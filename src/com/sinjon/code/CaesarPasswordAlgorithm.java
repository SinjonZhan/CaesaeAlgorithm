package com.sinjon.code;

import java.util.Scanner;

/**
 * �ŵ����� ��������
 * 
 * @author sinjon
 *
 */
public class CaesarPasswordAlgorithm {
	public static boolean flag = true;
	public char[] words = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static void main(String[] args) {
		// �����㷨 ��Կk
		CaesarPasswordAlgorithm caesarAlgorithm = new CaesarPasswordAlgorithm();
		System.out.println("��������Կk(int k,k>0&&k<26)");
		Scanner scanner = new Scanner(System.in);
		int k = scanner.nextInt();
		while (!(k > 0 && k < 26)) {
			System.out.println("��Կk������Ҫ��,���������루int k,k>0&&k<26��");
			k = scanner.nextInt();
		} 

		System.out.println("��Կk��" + k);
		System.out.println("���ģ�");
		System.out.print(caesarAlgorithm.words);
		System.out.println();
		System.out.println("���ģ�");
		caesarAlgorithm.encryptWord(k, caesarAlgorithm.words);
		System.out.println();
		System.out.println("��ѡ�����/����,����0/1����ѡ�����/����");
		int nextInt = scanner.nextInt();
		while (nextInt != 0 && nextInt != 1) {
			System.out.println("�������ݸ�ʽ���淶������������(0/1)");
			nextInt = scanner.nextInt();
		}
		char[] word = {};
		if (nextInt == 0) {
			System.out.println("������Ҫ���ܵ��ַ���");
		} else {
			System.out.println("������Ҫ���ܵ��ַ���");
		}

		while (true) {
			flag = true;
			word = scanner.next().toCharArray();
			for (char c : word) {
				if (!(c >= 'a' && c <= 'z')) {
					System.out.println("�������ݸ�ʽ���淶������������(��������ֻ�ܰ���26����ĸ)");
					flag = false;
					break;

				}

			}
			if (flag) {
				break;
			}

		}

		if (nextInt == 0) {
			System.out.println("���ܽ����");
			caesarAlgorithm.encryptWord(k, word);
			System.out.println();
		} else {
			System.out.println("���ܽ����");
			caesarAlgorithm.decodeWord(k, word);
			System.out.println();
		}

	}

	/**
	 * �����㷨
	 * 
	 * @param k
	 *            ��Կ
	 * @param word
	 *            ����
	 */
	private void decodeWord(int k, char[] c) {
		for (char word : c) {
			// ������������
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
	 * �����㷨
	 * 
	 * @param k
	 *            ��Կ
	 * @param word
	 *            ����
	 */
	private void encryptWord(int k, char[] c) {
		for (char word : c) {
			// ������������
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
