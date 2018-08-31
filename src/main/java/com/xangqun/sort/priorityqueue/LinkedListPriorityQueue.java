package com.xangqun.sort.priorityqueue;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * ���� LinkedList ���ȼ����е�ʵ��
 *
 * @author jzj
 * @data 2010-1-5
 * @param <E>
 */
public class LinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	//LinkedListֻ�����洢����������������������ʵ��
	private LinkedList<E> list;
	private Comparator<E> comp;

	public LinkedListPriorityQueue() {
		list = new LinkedList<E>();
	}

	public LinkedListPriorityQueue(Comparator<E> c) {
		this();
		comp = c;
	}

	private int compare(E elem1, E elem2) {
		return comp == null ? elem1.compareTo(elem2) : comp.compare(elem1, elem2);
	}

	/**
	 * ������ȼ�ʵ��
	 * @param elem
	 */
	public void add(E elem) {
		/*
		 * ���list����Ϊ�գ�����Ԫ�ص�ֵ���ڵ���list�����һ��Ԫ�ص�ֵ��add�����Ͱ�Ԫ�ض���
		 * ׷�ӵ�list��ĩβ�����򣬴�ͷ���������ֱ���ҵ�ĳ��Ԫ�أ���Ԫ�ص�ֵ��������Ԫ�ص�ֵ��
		 * Ȼ�󽫸�����Ԫ�ز��뵽��Ԫ�ص�ǰ�档
		 */
		if (list.isEmpty() || compare(elem, list.get(list.size() - 1)) >= 0) {
			list.addLast(elem);
			//�� list.add(elem)��add������ʵ������ѭ�������headerͷ�ڵ�ǰ���Ԫ��
		} else {
			ListIterator<E> itr = list.listIterator();
			//�ҵ���һ���������Ԫ��
			while (itr.hasNext() && compare(elem, itr.next()) >= 0)
				;
			itr.previous();//��Ϊ��������next����ȡ֮������ƣ�����Ҫ����һλ
			itr.add(elem);//�ڱ���Ԫ�ش�Ľڵ�ǰ����Ԫ��
		}

	}

	public E getMin() {
		return list.getFirst();
	}

	public boolean isEmpty() {

		return list.isEmpty();
	}

	public E removeMin() {
		return list.removeFirst();
	}

	public int size() {
		return list.size();
	}
}