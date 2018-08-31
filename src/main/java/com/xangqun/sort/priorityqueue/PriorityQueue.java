package com.xangqun.sort.priorityqueue;

/**
 * 优先级队列接口
 *
 * @author jzj
 * @data 2010-1-5
 * @param <E>
 */
public interface PriorityQueue<E extends Comparable<E>> {
	int size();

	boolean isEmpty();

	//向队列加入元素，添加时会按照优先级别排序
	void add(E elem);

	//取优先高的元素
	E getMin();

	//从优先级队列中删除优先级最高的元素
	E removeMin();
}

