/**
 * ���������ֳ����Ŷ���������һ�ִ�Ȩ·������̵���������·�������Ǵ�������ÿһ��Ҷ��֮���·������֮�͡��ڵ�Ĵ���·������Ϊ�Ӹýڵ㵽����֮���·��������ýڵ�Ȩ�������ַ���ĳ���е�ʹ��Ƶ�ʣ��ĳ˻���

������һ���ַ����磺3334444555556666667777777��������3��4��5��6��7�����������ɵģ���Ҫʹ��һ�ֱ��뷽ʽ����������洢��̣����������������ʹ��3λ�Ķ�����

�����ƾͿɱ�ʾ���磺(3:000) (4:001) (5:010) (6:100) (7:101)��������Ĵ洢�ռ��� 3 * (3 + 4 + 5 + 6 + 7) = 75 ����λ���ܷ���һ��ѹ���ķ����Ѵ洢�ռ���С�������Huffman���룬����һ�ֲ��ȳ����룬���Ҫ��һ���ַ�����Ĳ�����һ���ַ������ǰ׺������һ������ǰ׺���롣����Ҫһ��ʼ����Ҫͳ�Ƴ�ÿ���ַ����ֵ�Ƶ�ʣ�Ȼ�������ЩƵ������Ƴ��������������Խ�ʡ�����Ŀռ䡣�����ַ����ֵ�Ƶ�ʾ���������һ˼����Huffman����Ļ�����Huffman������������ǰ׺���������ŵ�һ�ֱ�����ԡ�Huffman������Unix��compress���ߵĻ�����Ҳ������ͼ����ר����(JPEG)��������ϵ�һ���֡�
����������ѹ������ʹ�������ȼ����С�����һ����Ϣ�����Զ�ÿ���ַ�������ǰ׺�ı��룬ʹ����볤�Ⱦ������ٵı���λ��ʹ��Huffman�������Եõ�������С���롣Huffman��������һ����ȫ�Ķ�����������ÿ��Ҷ�ڵ㶼��ʾһ��ԭ��Ϣ�еĲ�ͬ�ַ���ÿ�����֧����Ϊ0����ÿ���ҷ�֧����ʾΪ1�����Ÿ��ڵ㵽Ҷ�ڵ��ַ���·��������·���еķ�֧��ǩ��������������Ϳ��Եõ����ַ���Huffman���롣

����������ֱ���Ķ���������ֻ�еڶ��������Ŷ�������

    (:25)
    0/  \1
   (:18) 7
  0/  \1
 (:7) (:11)
0/ \1 0/ \1
3   4 5   6
Ȩֵ = (3 + 4 + 5 + 6) * 3 + 7 * 1 = 61�������Ŷ�������

          (:25)
         0/   \1
        (:11) (:14)
       0/ \1  0/ \1
       5   6   7  (:7)
                  0/ \1
                  3   4
Ȩֵ = (3 + 4) * 3 + 7 * 2 + (5 + 6) * 2 = 57�����Ŷ�������

��ˣ�������ı���Ϊ (3:000) (4:001) (7:01) (5:10) (6:11)������Щ���ȳ�����������������һ���ַ��ı�������һ���ַ������ǰ׺��һ����֤��ǰ׺���ر���ķ����Ǵ���һ�ö��������������֧ͨ��ʹ��0����ʾ�����ҷ�֧��1����ʾ�����ÿ���ѱ�����ַ���������Ҷ���ϣ���ô���ַ��ı���Ͳ������������ַ������ǰ׺�����仰˵������ÿ���ַ�·��������һ����ǰ׺���롣

���������Ĺ�����̣���ԭʼԪ�ؼ���T���ó�����Ƶ����С��Ԫ�����һ�����������������ĸ�Ϊ�������ڵ�Ƶ�ȵĺͣ�Ȼ��Ӽ���T��ɾ��������Ԫ�أ��Ѹ�Ԫ�ؼ��뵽T�����У���˷���ֱ����TΪ�ա�

��ô��˵�������ʵ������������˼���أ�
��ͳ����ÿ���ַ����ֵ�Ƶ��֮�󣬰���Ƶ�ʵ�����˳��ÿ���ַ���Ƶ�ʶԲ��뵽һ�����ȼ������У������ȶ����о���������ȼ����ַ���Ƶ�ʶ��е��ַ�������С�ĳ���Ƶ�ʣ���Щ�ַ�������Huffman������Զ��Ҷ�ӽڵ��������������ǵı���������ı���λ���෴������Ƶ����ߵ��ַ���������С�ı���λ���롣

���Ƚ������ַ���Ƶ�ʶԲ��뵽���ȶ����У�
��3:3����4:4����5:5����6:6����7:7��
�γɵĳ�ʼ�����£�
      3
     / \
    4   5
   / \
6    7

�����ַ���Ƶ�ʶ���ɵ����ȼ�����������Ķ���������Huffman�������ǽ��Ե����Ϲ���Huffman�����ּ��������ַ�Ԫ�ض��Ѱ�ʹ��Ƶ����ӵ������ȼ�������ȥ�ˣ�����ʼ���ѹ���ã���������ʾ�������濪ʼ����Huffman����

���ȵ����������ȼ����е�removeMin�������õ�����Ƶ����͵��ַ�����3���ǵ�һ����ɾ����Ԫ�أ�����һ�����ӵ�Ԫ�أ�����Ϊ����������Ҷ�ӽڵ㣬����4����Ϊ��Ҷ�ڵ㣬�������ߵ�Ƶ��֮�ͣ�:7����Ϊ���ĸ��ڵ㣬���ֽ�����:7����ӵ����ȼ������У����ڵõ����µ�Huffman����
      (:7)
     0/ \1
     3   4
��ʱ���ȼ������а�����
��5:5����6:6����7:7����:7��
�ѽṹ���£�
      5
     / \
    6   7
   /
 (:7)

Ȼ��ɾ��5��6�������ǲ���ֱ������ǰ���������У���Ϊ����Ԫ�ض����ڹ��������С���Ϊ���ǳ�Ϊ��һ����������Ҷ�ڵ������Ҷ�ڵ㣬�Ҹ����ĸ������ǵ�Ƶ��֮��:11�������������뵽���ȼ������У�����������Huffman����
      (:11)    ��        (:7)
     0/ \1             0/  \1
     5   6              3    4
��ʱ�����ȼ������а�����Ԫ�����£�
(7:7) (:7) (:11)
�ѽṹ���£�
      7
     /  \
 (:7) (:11)

��Ȼ�󣬵�(:7)��ɾ��ʱ������Ϊ�����������֧������һ����ɾ����7Ԫ�����������ҷ�֧������Ƶ��֮�ͳ�Ϊ�������ĸ�(:14)�����������ȼ������С�����(:7)�����У�������һ����ԭ�����е�ĳ���Ͻ������䣬�����͵õ�����Huffman����
  (:11)     ��     (:14)
 0/ \1            0/ \1
5   6            7  (:7)
                      0/ \1
                      3   4
��ʱ���ȶ����а�����
(:11) (:14)
�ѽṹ���£�
(:11)
   /
(:14)

���ɾ��(:11)��(:14)�����ڵ㣬�����������ڵ㶼�������Ѵ����õ�Huffman�У��������ʵ��������Ǻϲ�������Huffman��������γ����յ�Huffman����
          (:25)
         0/   \1
        (:11) (:14)
       0/ \1  0/ \1
       5   6   7  (:7)
                  0/ \1
                  3   4
 */
package com.xangqun.sort.huffman;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.xangqun.sort.priorityqueue.heap.Heap;


/**
 * ��������������������
 *
 * @author jzj
 * @data 2010-1-8
 */
public class Huffman {

	//���������ڵ�
	private static class Entry implements Comparable<Entry> {

		int freq;//�ڵ�ʹ��Ƶ�ʣ����ȼ����Ǹ��ݴ˾���
		String code;//�ڵ�huffman����
		char c;//�ڵ�����Ӧ���ַ�
		Entry left, right, parent;//��������������ֶ�

		//�ڵ�����ȼ��Ƚ�
		public int compareTo(Entry entry) {
			return freq - entry.freq;
		}

		public String toString() {
			return "(" + c + ":" + code + ")";
		}
	}

	//�������ǽ�ֻ��Unicodeueǰ256���ַ����룬����ֻ������ISO8859-1�ַ���
	protected final int SIZE = 256;

	//�����������ڿ��ٲ�ѯĳ�ַ��Ĺ������
	protected Entry[] leafEntries;

	//�ѣ�������̬�������ȼ�����
	protected Heap<Entry> pq;

	//Ҫ��������봮
	protected String input;

	public Huffman(String input) {
		this.input = input;
		createPQ();
		createHuffmanTree();
		calculateHuffmanCodes();
	}

	//������ʼ��
	public void createPQ() {

		//��ʼ����������
		Entry entry;
		leafEntries = new Entry[SIZE];
		for (int i = 0; i < SIZE; i++) {
			leafEntries[i] = new Entry();
			leafEntries[i].freq = 0;//ʹ��Ƶ��
			/*
			 * leafEntries���������е��������ַ��ı����Ӧ�������ڶ�ȡʱ
			 * �ܷ���
			 */

			leafEntries[i].c = (char) i;//�ڵ���Ƕ�Ӧ���ַ�

		}

		//����������
		fillLeafEntries();

		//��ʼ������ʼ��
		pq = new Heap<Entry>();
		for (int i = 0; i < SIZE; i++) {
			entry = leafEntries[i];
			if (entry.freq > 0) {//�����ʹ�ù�����������
				pq.add(entry);
			}
		}
	}

	//����������ַ������leafEntries��������
	public void fillLeafEntries() {

		Entry entry;

		for (int i = 0; i < input.length(); i++) {

			entry = leafEntries[(int) (input.charAt(i))];
			entry.freq++;
			entry.left = null;
			entry.right = null;
			entry.parent = null;
		}
	}

	// ������������
	public void createHuffmanTree() {

		Entry left, right, parent;

		//ÿ����Ӷ���ȡ���������������1�����С�ڵ���1ʱ��ʾ���������Ѵ������
		while (pq.size() > 1) {

			// ʹ��̰������ÿ�δ����ȼ������ж�ȡ��С������Ԫ��
			left = (Entry) pq.removeMin();
			left.code = "0";//�����Ϊ���ӽڵ㣬��Ϊ·������Ϊ0

			right = (Entry) pq.removeMin();
			right.code = "1";//�����Ϊ���ӽڵ㣬��Ϊ·������Ϊ1

			parent = new Entry();
			parent.parent = null;

			//���ڵ��ʹ��Ƶ��Ϊ����֮��
			parent.freq = left.freq + right.freq;
			parent.left = left;
			parent.right = right;
			left.parent = parent;
			right.parent = parent;

			//�ٰѸ��ڵ������У������������ѽṹ
			pq.add(parent);
		}
	}

	// �������봮��ÿ���ַ��Ĺ������
	public void calculateHuffmanCodes() {

		String code;
		Entry entry;

		for (int i = 0; i < SIZE; i++) {

			code = "";
			entry = leafEntries[i];
			if (entry.freq > 0) {//���ʹ�ù����ַ�ʱ����Ҫ��������

				do {
					/*
					* ƴ�Ӵ�Ҷ�ڵ㵽���ڵ�·���ϸ�Ԫ�ص�·�����룬���õ�������룬
					* ע�����ﵹ�����ģ����Բ�����������code = code + entry.code;
					*/
					code = entry.code + code;
					entry = entry.parent; // Ҫһֱѭ������
				} while (entry.parent != null);

				leafEntries[i].code = code;//�����������Ĺ������

			}
		}
	}

	//�õ������������
	public Map<String, String> getHuffmancodeTable() {

		Map<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < SIZE; i++) {
			Entry entry = leafEntries[i];
			if (entry.freq > 0) {//���ʹ�ù����ַ�ʱ������������
				map.put(String.valueOf(entry.c), entry.code);
			}
		}

		return map;
	}

	//�õ��ַ�������Ӧ�Ĺ���������
	public String getHuffmancodes() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			Entry entry = leafEntries[input.charAt(i)];
			sb.append(entry.code);
		}
		return sb.toString();
	}

	//��huffman��Ϣ����ԭ���ַ���
	public static String huffmancodesToString(Map<String, String> map, String huffmanCodes) {
		Entry root = createTreeFromCode(map);
		return encoding(root, huffmanCodes);
	}

	//����ָ���Ĺ��������봴����������
	private static Entry createTreeFromCode(Map<String, String> map) {
		Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
		Map.Entry<String, String> mapEntry;
		Entry root = new Entry(), parent = root, tmp;

		while (itr.hasNext()) {
			mapEntry = itr.next();

			//�Ӹ���ʼ������
			for (int i = 0; i < mapEntry.getValue().length(); i++) {

				if (mapEntry.getValue().charAt(i) == '0') {
					tmp = parent.left;
					if (tmp == null) {
						tmp = new Entry();
						parent.left = tmp;
						tmp.parent = parent;
						tmp.code = "0";
					}
				} else {
					tmp = parent.right;
					if (tmp == null) {
						tmp = new Entry();
						parent.right = tmp;
						tmp.parent = parent;
						tmp.code = "1";
					}
				}

				if (i == mapEntry.getValue().length() - 1) {
					tmp.c = mapEntry.getKey().charAt(0);
					tmp.code = mapEntry.getValue();
					parent = root;
				} else {
					parent = tmp;
				}
			}

		}
		return root;
	}

	//���ݸ����Ĺ��������������ַ�
	private static String encoding(Entry root, String huffmanCodes) {
		Entry tmp = root;
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < huffmanCodes.length(); i++) {
			if (huffmanCodes.charAt(i) == '0') {
				tmp = tmp.left;//�ҵ��뵱ǰ�����Ӧ�Ľڵ�
				//�����������������Ϊ�գ���������Ҳ�϶�Ϊ�գ�Ҳ����˵����֧�ڵ�һ�����������ڵ�Ľڵ�
				if (tmp.left == null) {//���ΪҶ�ӽڵ㣬���ҵ���������
					sb.append(tmp.c);
					tmp = root;//׼���½�����һ���ַ�
				}
			} else {
				tmp = tmp.right;
				if (tmp.right == null) {
					sb.append(tmp.c);
					tmp = root;
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String inputStr = "3334444555556666667777777";
		Huffman hfm = new Huffman(inputStr);

		Map<String, String> map = hfm.getHuffmancodeTable();
		String huffmancodes = hfm.getHuffmancodes();
		System.out.println("�����ַ��� - " + inputStr);
		System.out.println("������������ձ� - " + map);
		System.out.println("���������� - " + huffmancodes);
		String encodeStr = Huffman.huffmancodesToString(map, huffmancodes);
		System.out.println("���������� - " + encodeStr);
		/*
		 * output:
		 * �����ַ��� - 3334444555556666667777777
		 * ������������ձ� - {3=110, 5=00, 7=10, 4=111, 6=01}
		 * ���������� - 110110110111111111111000000000001010101010110101010101010
		 * ���������� - 3334444555556666667777777
		 */
	}
}
