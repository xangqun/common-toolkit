package com.xangqun.sort.priorityqueue;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * 基于 LinkedList 优先级队列的实现
 *
 * @author jzj
 * @data 2010-1-5
 * @param <E>
 */
public class LinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	//LinkedList只用来存储，并不排序，排序操作由外界实现
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
	 * 插队优先级实现
	 * @param elem
	 */
	public void add(E elem) {
		/*
		 * 如果list本身为空，或者元素的值大于等于list中最后一个元素的值，add方法就把元素对象
		 * 追加到list的末尾。否则，从头部逐个访问直到找到某个元素，该元素的值大于新增元素的值，
		 * 然后将给定的元素插入到该元素的前面。
		 */
		if (list.isEmpty() || compare(elem, list.get(list.size() - 1)) >= 0) {
			list.addLast(elem);
			//或 list.add(elem)，add方法其实就是在循环链最后即header头节点前添加元素
		} else {
			ListIterator<E> itr = list.listIterator();
			//找到第一个比它大的元素
			while (itr.hasNext() && compare(elem, itr.next()) >= 0)
				;
			itr.previous();//因为迭代器的next方法取之后会下移，所以要后移一位
			itr.add(elem);//在比新元素大的节点前插入元素
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