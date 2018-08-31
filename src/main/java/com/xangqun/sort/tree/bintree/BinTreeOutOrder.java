package com.xangqun.sort.tree.bintree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * ���������ⲿ������������ȣ��ȸ�������ȣ���Σ����ȱ���
 *
 * @author jzj
 * @date 2009-12-23
 */
public class BinTreeOutOrder extends BinTree {

	/**
	 * �������������ȱ����������������ȸ����������������ⲿ����ʹ�øõ�����
	 * ���зǵݹ�ı���������һ���ڶ������ṹ�ⲿ��һ�ֱ����㷨����û��ʹ��
	 * ����������Ľṹ�ص㣨���������ݹ飩���еݹ����
	 * @author jzj
	 */
	private class DepthOrderIterator implements Iterator {
		//ջ���ŵ���ÿ���ڵ�
		private Stack stack = new Stack();

		public DepthOrderIterator(Entry node) {

			//����ջ�����ڷ��������ӽڵ�ǰ�����ϳ�ջ�����������������ӽڵ����
			stack.push(node);

		}

		//�Ƿ�����һ��Ԫ��
		public boolean hasNext() {
			if (stack.isEmpty()) {
				return false;
			}
			return true;
		}

		//ȡ��һ��Ԫ��
		public Entry next() {
			if (hasNext()) {
				//ȡջ��Ԫ��
				Entry treeNode = (Entry) stack.pop();//�ȷ��ʸ�

				if (treeNode.right != null) {
					stack.push(treeNode.right);//�ٷ������ӽڵ�
				}

				if (treeNode.left != null) {
					stack.push(treeNode.left);//���������ӽڵ㣬�����������ҽڵ�
				}

				// ���ر����õ��Ľڵ�
				return treeNode;

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
	 * ������ṩ�ȸ�����������
	 * @return Iterator �����ȸ�����������
	 */
	public Iterator createPreOrderItr() {
		return new DepthOrderIterator(root);
	}

	/**
	 * ������������ȱ������������ⲿ����ʹ�øõ�����
	 * ���зǵݹ�ı���������һ���ڶ������ṹ�ⲿ��һ�ֱ����㷨����û��ʹ��
	 * ����������Ľṹ�ص㣨���������ݹ飩���еݹ����
	 * @author jzj
	 */
	private class LevelOrderIterator implements Iterator {
		//ʹ�ö��нṹʵ�ֲ�α�����������洢��Ϊ�ڵ�
		private LinkedList queue = new LinkedList();

		public LevelOrderIterator(Entry node) {

			if (node != null) {
				//�������
				queue.addLast(node);
			}
		}

		//�Ƿ�����һ��Ԫ��
		public boolean hasNext() {
			if (queue.isEmpty()) {
				return false;
			}
			return true;
		}

		//ȡ��һ��Ԫ��
		public Entry next() {
			if (hasNext()) {
				//ȡջ����Ԫ��
				Entry treeNode = (Entry) queue.removeFirst();

				if (treeNode.left != null) {//����������������������б���
					queue.addLast(treeNode.left);
				}
				if (treeNode.right != null) {//����������������������б���
					queue.addLast(treeNode.right);
				}

				// ���ر����õ��Ľڵ�
				return treeNode;

			} else {
				// �������Ϊ��
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * ������ṩ������ȵ�����
	 * @return Iterator ���ر���������
	 */
	public Iterator createLayerOrderItr() {
		return new LevelOrderIterator(root);
	}

	public static void main(String[] args) {
		//����һ����������
		BinTreeOutOrder treeOrder = new BinTreeOutOrder();
		treeOrder.createFullBiTree(15);
		Iterator preOrderItr = treeOrder.createPreOrderItr();
		System.out.print("������ȣ��ȸ������� - ");
		while (preOrderItr.hasNext()) {
			System.out.print(((Entry) preOrderItr.next()).elem + " ");
		}
		System.out.println();
		System.out.print("������ȣ���Σ����� - ");
		Iterator layerOrderItr = treeOrder.createLayerOrderItr();
		while (layerOrderItr.hasNext()) {
			System.out.print(((Entry) layerOrderItr.next()).elem + " ");
		}
		/*
		 * output:
		 * ������ȣ��ȸ������� - 1 2 4 8 9 5 10 11 3 6 12 13 7 14 15
		 * ������ȣ���Σ����� - 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
		 */
	}
}
