package com.xangqun.sort.stack;

/**
 * ջ�ӿ�
 * @author jzj
 *
 * @param <E>
 */
public interface Stack<E> {
	//��ջ
	public void push(E e);

	//��ջ
	public E pop();

	//ȡջ��Ԫ��
	public E peek();

	//��ǰջ��С
	public int size();

	//ջ�Ƿ�Ϊ��
	public boolean isEmpty();
}