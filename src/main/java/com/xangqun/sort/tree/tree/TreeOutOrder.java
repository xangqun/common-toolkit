package com.xangqun.sort.tree.tree;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import com.xangqun.sort.queue.LinkedQueue;


public class TreeOutOrder {
	/**
	 * ������ȱ���������
	 *
	 * @author jzj
	 * @data 2009-12-17
	 */
	public static class DepthOrderIterator implements Iterator {
		//ջ,������ȱ������ڵ�,�Ա����
		Stack stack = new Stack();

		public DepthOrderIterator(TreeNode rootNode) {
			ArrayList list = new ArrayList();
			list.add(rootNode);

			// �����ڵ��������ջ
			stack.push(list.iterator());
		}

		//�Ƿ�����һԪ��
		public boolean hasNext() {
			// ���ջΪ���򷵻�,֤��û�пɱ�����Ԫ��
			if (stack.empty()) {
				return false;
			} else {
				// ���ջ��Ϊ��,��ȡ��ջ��Ԫ��(������)
				Iterator iterator = (Iterator) stack.peek();

				// ����ʹ�ü�Ԫ��(���������е�Ԫ��,��������״�ṹ��Ԫ��)�ķ�ʽ������
				if (!iterator.hasNext()) {
					// ���ȡ���������Ѿ��������,�򵯳�������,�Ա���˵���һ(��)����������������������ȷ�ʽ����
					stack.pop();

					// ͨ���ݹ鷽ʽ������������������δ�������Ľڵ�Ԫ��
					return hasNext();
				} else {
					// ����ҵ�����һ��Ԫ��,����true
					return true;
				}
			}
		}

		// ȡ��һԪ��
		public Object next() {
			// ���������һ��Ԫ��,����ȡ����Ԫ������Ӧ�ĵ���������,�Ա�ȡ�øýڵ�Ԫ��
			if (hasNext()) {
				Iterator iterator = (Iterator) stack.peek();
				// ��ȡ�ýڵ�Ԫ��
				TreeNode component = (TreeNode) iterator.next();

				//ֻ�з�֧�ڵ����һ�����ӽڵ���е���
				if (component instanceof TreeBranchNode) {
					stack.push(component.getSubNodes().iterator());
				}

				// ���ر����õ��Ľڵ�
				return component;
			} else {
				// ���ջΪ��
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * ��α���������
	 *
	 * @author jzj
	 * @data 2009-12-17
	 */
	public static class LevelOrderIterator implements Iterator {
		//���У�ʵ�ֲ�α���������ڵ������
		private LinkedQueue queue = new LinkedQueue();

		public LevelOrderIterator(TreeNode rootNode) {
			ArrayList list = new ArrayList();
			list.add(rootNode);

			// �����ڵ���������
			queue.enqueue(list.iterator());
		}

		//�Ƿ�����һԪ��
		public boolean hasNext() {
			// �������Ϊ���򷵻�
			if (queue.isEmpty()) {
				return false;
			} else {
				// ������в�Ϊ��,��ȡ������Ԫ��(������)
				Iterator iterator = (Iterator) queue.front();

				if (!iterator.hasNext()) {
					// ���ȡ���������Ѿ�������ɣ������
					queue.dequeue();

					// ͨ���ݹ鷽ʽ�����������������Ƿ���δ�������Ľڵ�Ԫ��
					return hasNext();
				} else {
					// ����ҵ�����һ��Ԫ��,����true
					return true;
				}
			}
		}

		// ȡ��һԪ��
		public Object next() {
			// ���������һ��Ԫ��
			if (hasNext()) {
				Iterator iterator = (Iterator) queue.front();
				// ��ȡ�ýڵ�Ԫ��
				TreeNode component = (TreeNode) iterator.next();

				//ֻ�з�֧�ڵ����һ�����ӽڵ���е���
				if (component instanceof TreeBranchNode) {
					queue.enqueue(component.getSubNodes().iterator());
				}

				// ���ر����õ��Ľڵ�
				return component;
			} else {
				// ���ջΪ��
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}