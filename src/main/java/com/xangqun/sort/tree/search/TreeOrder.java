package com.xangqun.sort.tree.search;

/**
 * ���ڵ���ʽӿ�
 * @author jzj
 * @date 2010-1-3
 * @param <E>
 */
public interface TreeOrder<E extends Comparable<E>> {

	static interface Visitor<E extends Comparable<E>> {
		void visit(E ele);
	}

	//ǰ�����
	void preOrder(Visitor<E> v);

	//�������
	void inOrder(Visitor<E> v);

	//�������
	void postOrder(Visitor<E> v);

	//��α���
	void levelOrder(Visitor<E> v);
}