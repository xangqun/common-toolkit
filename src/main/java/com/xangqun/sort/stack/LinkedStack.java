package com.xangqun.sort.stack;

/**
 * ʹ�� ������ ʵ��ջ
 * @author jzj
 *
 * @param <E>
 */
public class LinkedStack<E> implements Stack<E> {
	private class Node {
		E data;//������
		Node next;//next��

		Node() {//ջ��Ԫ�ع��캯��
			data = null;
			next = null;
		}

		/**
		 * ��ջ��Ԫ�ع��캯��
		 * @param data ������
		 * @param next �½��ڵ�next��ָ��
		 */
		Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}

		//�Ƿ�ջ��Ԫ��
		boolean isEnd() {
			return data == null && next == null;
		}
	}

	/*
	 * ջ��Ԫ�أ��տ�ʼʱ����һ��data��next��Ϊnull�Ľڵ㣬����һ����־�Խڵ㣬
	 * Ҳ�൱��ջ�ף����ж�ջ�Ƿ�Ϊ�վͿ���ʹ���������topָ��������ڵ㣬�ͱ�����
	 * ��ջ���ˣ���ջΪ��
	 */
	private Node top = new Node();

	//��ջ
	public void push(E data) {
		//��ջ��ָ���½��ڵ�
		top = new Node(data, top);
	}

	//��ջ
	public E pop() {
		//�ýڵ������ջ��Ԫ�أ�������򷵻�Ϊnull
		E data = top.data;
		if (!top.isEnd()) {//�������ջ�ף���ָ����һ��Ԫ��
			top = top.next;
		}
		return data;
	}

	//ȡջ��Ԫ��
	public E peek() {
		//�ýڵ������ջ��Ԫ�أ�������򷵻�Ϊnull
		return top.data;
	}

	//ջ��С
	public int size() {
		int size = 0;
		Node tmpTop = top;
		while (tmpTop.next != null) {
			size++;
			tmpTop = tmpTop.next;
		}
		return size;
	}

	//ջ�Ƿ�Ϊ��
	public boolean isEmpty() {
		return top.isEnd();
	}
}
