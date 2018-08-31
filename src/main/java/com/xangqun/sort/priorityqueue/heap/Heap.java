/**
 * ���ȼ�������һ�����ݽṹ�������ܱ����ʺ�ɾ�����ǽ�����������ȼ���Ԫ�ء���ν���ȼ���ͨ��һЩ������Ԫ�ؽ��бȽϵó��ġ�������˼·�Ƕ����д��ڵȴ������Ԫ�ء���Ԫ�ص�ѡȡ�����ϸ���������ȷ���ԭ��

�������£����ȼ�������������һ�����ݽṹ�������ķ��ʻ���ɾ������ֻ�ܶԼ�����ͨ��ָ�����ȼ������ó���������ȼ�Ԫ�ؽ��С�

���ȼ������ǹ�ƽ�ģ������κ�����������ͬ���ȼ���Ԫ�أ����ȱ�ɾ�������Ǹ��ڶ����д���ʱ�����Ԫ�ء����Ԫ����Integer�����Ұ��������е�˳����бȽϣ���ô����������ȼ���Ԫ�ؾ������ȼ���������Ӧ��intֵ��С���Ǹ�Ԫ�ء����Ԫ����Integer���ͣ����ǱȽϷ�����ԭ����˳���෴����ô����������ȼ���Ԫ�ؾ������ȼ���������Ӧ��intֵ����Ԫ�ء���������С�Ļ�������Ԫ�����ȣ���������ȱȽ��㷨��ʵ��PriorityQueue�ӿڵĳ���Ա��������

��ô�������ȼ���������ʲô��ϵ�أ�ʵ���϶ѿ�����ʵ�����ȼ����У�����˵�Ѿ���һ�����ȼ����С����ڶѵ����Ԫ����ɾ��Ԫ��ʱ�����ƻ��ѽṹ�����������ɾ������Ҫ���нṹ������һ����ͨ�Ķ���������������룬�����ȼ�����ȴ��һ����ӵ�������ᰴ�����ȼ��㷨�������뵽���е���ȥ������ʱ���Ǵӵ�һ����Ҳ����СԪ�أ����ȼ���ߣ���ʼ����ȡ��Ԫ�أ�������֤�˶��������ȼ��ߵ��ȷ��񣬶����������ȷ����ˡ�
Heap���Ƕ�PriorityQueue�ӿڵ�һ�ָ�Чʵ�֡�����һ����ȫ������������ʹ�û����������ȫ�������ı�ʾ�����Ը����ӽڵ���������ټ�����㸸�ڵ����������֮��Ȼ�����ԣ�ʹ����������ʾ�ѣ�����������������Ը��ݸ��������������Ԫ�ص����ԡ�
 */
package com.xangqun.sort.priorityqueue.heap;


import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

import com.xangqun.sort.priorityqueue.PriorityQueue;


/**
 * ���ڶѵ����ȼ�����ʵ��
 *
 * �ѣ�heap����һ����ʹ�ö�����������һ�ÿ�����
 * 1����Ԫ�ش��������ӽڵ㣨��ʱ�д󶥶ѣ�
 * 2�������ӽڵ����Ƕ�
 *
 * ����һ����ȫ������������������Ҫ��ϤҪ�õĶ������������ʣ�
 * N��N>1�����ڵ�ĵ���ȫ�������Ӳ�δ������ұ�ţ����һ����֦�ڵ㣨��Ҷ�ӽڵ㣩�ı��Ϊ N/2 ȡ
 * �����Ҷ��ڱ�� i��1<=i<=N ��Ŵ�1��ʼ���У����ڵ�Ϊ i/2 ����ȡ������2i>N����ڵ�iû������
 * ������������Ϊ2i����2i+1>N����û���Һ��ӣ��������Һ���Ϊ2i+1��
 * ע������ʹ����ȫ������ֻ��Ϊ�˺������㷨����ֻ��һ���߼��ṹ��������ʵ��ʱ���ǻ���ʹ����������
 * ����ö������ģ���Ϊ��ȫ���������������������һһ��Ӧ�Ͽ���
 *
 * ������е�����������Զ��ڶѵĴ���ܷ��㣺����Ԫ�ص�����ֵ���ܿ���ܵõ���Ԫ�ص��ӽڵ�Ԫ�ء���
 * �磬�ڵ���Ϊi��Ԫ�ص��ӽڵ�ֱ�Ϊ 2i �� 2i+1����ô��Ӧ������������žͷֱ�Ϊ 2i-1 �� 2i��
 * �ڵ���Ϊj��Ԫ�صĸ��ڵ�Ϊ j/2 ����ȡ������Ӧ������Ԫ�������ڸ��ڵ��Ż����ϼ�һ���ɵõ���
 * ���ԣ��ѿ��Կ��ٽ������ڵ��С�������ӽڵ��ֵ����ʹ�öѳ�Ϊʵ��PriorityQueue�ӿڵ�һ�ָ�Ч
 * ���ݽṹ��
 *
 * @author jzj
 * @data 2010-1-5
 * @param <E>
 */
public class Heap<E extends Comparable<E>> implements PriorityQueue<E> {
	private E[] heap;//ʹ��������ʵ�ֶѴ洢
	private Comparator<E> comp;
	private int size;

	public Heap() {
		heap = (E[]) new Comparable[5];
	}

	public Heap(Comparator<E> c) {
		this();
		comp = c;
	}

	//������TreeMap�е�˽�з��� compare(Object k1, Object k2)
	private int compare(E elem1, E elem2) {
		return comp == null ? elem1.compareTo(elem2) : comp.compare(elem1, elem2);
	}

	//���Ԫ�أ�
	public void add(E elem) {
		if (++size == heap.length) {//Ԥ�жϷ�����Ƿ�����������������ݺ��ټ�
			E[] newHeap = (E[]) new Comparable[2 * heap.length];
			System.arraycopy(heap, 0, newHeap, 0, size);
			heap = newHeap;
		}
		heap[size - 1] = elem;
		adjustUp();//��Ӻ�ѹ�����ܴ��ƣ����������µ����ѽṹ
	}

	//���Ԫ�غ����ϵ����ѽṹ������С���ѣ�����ӵ�С��Ԫ������(��)��
	private void adjustUp() {
		/* ���28               28С��50������
		 *   ��        26            ��                 26
		 *           /  \                            /  \
		 *         32    30                        32    30
		 *        /  \   / \                      /  \   / \
		 *       48  50 85 36                    48  28 85 36
		 *      / \  / \                        / \  / \
		 *    90 80 55 28                     90 80 55 50
		 *
		 * ��28С�ڸ������轻��           ��28���ڸ�26�����Ե������
		 *   ��        26
		 *           /  \
		 *         28    30
		 *        /  \   / \
		 *       48  32 85 36
		 *      / \  / \
		 *    90 80 55 50
		 *
		 */
		int child = size;//�¼����Ҷ�ڵ��ţ������һ���ڵ�
		int parent;//���ڵ���
		while (child > 1) {//����������˸��ڵ���ֱ���˳�
			parent = child / 2;//����Ҷ�ӽڵ�ĸ��ڵ���
			//������ڵ�С�ڵ����ӽڵ㣨�����ڵ㣩�����˳�
			if (compare(heap[parent - 1], heap[child - 1]) <= 0) {
				break;
			}
			//��������ڵ�С�����ĸ��ڵ�ʱ���򽻻�
			E tmp = heap[parent - 1];
			heap[parent - 1] = heap[child - 1];
			heap[child - 1] = tmp;
			child = parent;//�����ڵ��Ƶ����ڵ�λ�ã��Ա��´�ѭ��
		}
	}

	public E getMin() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return heap[0];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	//ɾ���Ѷ�Ԫ�أ�ʹ�����һ��Ԫ���滻��Ԫ�أ�Ȼ���ٽ��ṹ����
	public E removeMin() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		/*
		 * ɾ����Ԫ�أ���Ԫ������СԪ�أ�ɾ����ʹ���������Ҷ�ڵ��滻������ʱ�ѽṹ���ƻ���
		 * ��Ӹ��ڵ��������µ����ѽṹ
		 *
		 * ɾ��26             ������26�����Ҷ�ڵ�55          55����С�ӽڵ�30���轻��
		 *   ��      26              ��            55                ��
		 *         /  \                         /  \
		 *       32    30                     32    30
		 *      / \    / \                   / \    / \
		 *     48  50 85 36                 48 50  85 36
		 *    / \  /                        /\
		 *  90 80 55                       90 80
		 *
		 *                   55����С�ӽڵ�36���轻��
		 *          30               ��                  30     �ѽṹ�ָ�����������
		 *         /  \                                /  \
		 *       32    55                            32    36
		 *      / \    / \                          / \    / \
		 *     48  50 85 36                       48  50  85 55
		 *    / \  /                             / \   /
		 *  90 80 55                            90 80 55
		 */
		//����������Ԫ��λ��
		E minElem = heap[0];//�Ѷ�Ԫ��
		heap[0] = heap[size - 1];
		//���ܰ�heap[--size]��Ϊnull����Ϊ����Ķ����򷽷�heapSortҪ��
		heap[--size] = minElem;
		adjustDown(1);//ɾ����Ӹ���ʼ���µ���
		return minElem;
	}

	//�ѽṹ��������ָ���Ľڵ����¿�ʼ����
	private void adjustDown(int nodeNum) {

		int parent = nodeNum;//��ָ���ڵ㿪ʼ���µ���
		int child = 2 * parent;//ָ���ڵ����ӽڵ���
		//������Ӵ���
		while (child <= size) {
			int minNum = parent;//���踸������С��
			//�����ӱȣ���������Ӵ�����С����Ϊ����
			if (compare(heap[parent - 1], heap[child - 1]) > 0) {
				minNum = child;
			}

			//����Һ��Ӵ��ڣ����Сʱ
			if ((child + 1) <= size && compare(heap[minNum - 1], heap[child]) > 0) {
				minNum = child + 1;
			}

			//���������СԪ��Ϊ�ӽڵ�ʱ�轻��
			if (minNum != parent) {
				E tmp = heap[minNum - 1];
				heap[minNum - 1] = heap[parent - 1];
				heap[parent - 1] = tmp;
				parent = minNum;
				child = 2 * minNum;
			} else {//�����˳�
				break;
			}
		}
	}

	/**
	 * ������
	 * ʹ�öѽṹ��ĳ�������������
	 * @param elems
	 */
	public E[] heapSort(E[] elems) {

		int length = elems.length;

		heap = elems;
		size = length;
		/*
		* ������ʼ�ѣ������һ����Ҷ�ӽڵ㿪ʼ�������еķ�Ҷ�ӽڵ㣬ֱ�����ڵ㣬
		* ���еĽڵ�������������µ����ķ���
		*/
		for (int i = length / 2; i >= 1; i--) {
			adjustDown(i);
		}
		//�ٶԳ�ʼ�ѽ�������
		while (size > 0) {
			//ɾ���Ĺ���ʵ���Ͼ����������
			removeMin();
		}
		return elems;
	}

	//���Ĳ�α���
	private void levelOrder() {
		if (size == 0) {
			return;
		}
		LinkedList queue = new LinkedList();
		queue.add(1);
		System.out.print("��α��� - ");
		while (!queue.isEmpty()) {
			int num = (Integer) queue.removeFirst();
			System.out.print(heap[num - 1] + " ");

			if (num * 2 <= size) {
				queue.add(num * 2);
				if (num * 2 + 1 <= size) {
					queue.add(num * 2 + 1);
				}
			}
		}
		System.out.println();
	}

	public int size() {

		return size;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Heap<Integer> h = new Heap<Integer>();

		Random rr = new Random(System.currentTimeMillis());
		Integer[] itg = new Integer[rr.nextInt(20)];
		for (int i = 0; i < itg.length; i++) {
			Integer tmp = new Integer(rr.nextInt(100));
			h.add(tmp);
			itg[i] = tmp;
		}
		h.levelOrder();
		System.out.print("���ȶ��� - ");
		while (h.isEmpty() == false) {
			System.out.print(h.removeMin() + " ");
		}

		System.out.println();
		itg = h.heapSort(itg);
		System.out.print("������ - ");
		System.out.println(Arrays.toString(itg));
	}
}

