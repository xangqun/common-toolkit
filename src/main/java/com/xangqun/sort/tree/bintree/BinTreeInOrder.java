package com.xangqun.sort.tree.bintree;

/**
 * ������������ �ڲ� ������ǰ�����򡢺���
 * �����������ַ�ʽ���������ı������������ı���֮ǰ�������������ֱ�����ʽ��
 * ������ѭ��Լ��
 * @author jzj
 * @date 2009-12-23
 */
public class BinTreeInOrder extends BinTree {

	/**
	 * �ڵ�����ߣ��ɸ�����Ҫ��дvisit����
	 */
	static abstract class Visitor {
		void visit(Object ele) {
			System.out.print(ele + " ");
		}
	}

	public void preOrder(Visitor v) {
		preOrder(v, root);
	}

	/**
	 * ����ǰ��ݹ���� pre=prefix(ǰ׺)
	 * @param node Ҫ�����Ľڵ�
	 */
	private void preOrder(Visitor v, Entry node) {
		//����������Ľڵ㲻Ϊ�գ��������ע��Ҷ�ӽڵ���ӽڵ�Ϊnull
		if (node != null) {
			v.visit(node.elem);//�ȱ������ڵ�
			preOrder(v, node.left);//�ٱ�����ڵ�
			preOrder(v, node.right);//�������ҽڵ�
		}
	}

	public void inOrder(Visitor v) {
		inOrder(v, root);
	}

	/**
	 * ��������ݹ���� in=infix(��׺)
	 * @param node Ҫ�����Ľڵ�
	 */
	private void inOrder(Visitor v, Entry node) {
		//����������Ľڵ㲻Ϊ�գ��������ע��Ҷ�ӽڵ���ӽڵ�Ϊnull
		if (node != null) {
			inOrder(v, node.left);//�ȱ�����ڵ�
			v.visit(node.elem);//�ٱ������ڵ�
			inOrder(v, node.right);//�������ҽڵ�
		}
	}

	public void postOrder(Visitor v) {
		postOrder(v, root);
	}

	/**
	 * ���ĺ���ݹ���� post=postfix(��׺)
	 * @param node Ҫ�����Ľڵ�
	 */
	private void postOrder(Visitor v, Entry node) {
		//����������Ľڵ㲻Ϊ�գ��������ע��Ҷ�ӽڵ���ӽڵ�Ϊnull
		if (node != null) {
			postOrder(v, node.left);//�ȱ�����ڵ�
			postOrder(v, node.right);//�ٱ����ҽڵ�
			v.visit(node.elem);//���������ڵ�
		}
	}

	//����
	public static void main(String[] args) {

		//����������
		int[] nums = new int[] { 1, 2, 3, 4, 0, 0, 5, 0, 6, 0, 0, 0, 0, 7, 8 };
		BinTreeInOrder treeOrder = new BinTreeInOrder();
		treeOrder.createBinTree(nums);
		System.out.print("ǰ����� - ");
		treeOrder.preOrder(new Visitor() {
		});
		System.out.println();
		System.out.print("������� - ");
		treeOrder.inOrder(new Visitor() {
		});
		System.out.println();
		System.out.print("������� - ");
		treeOrder.postOrder(new Visitor() {
		});
		/*
		 * output:
		 * ǰ����� - 1 2 4 6 3 5 7 8
		 * ������� - 4 6 2 1 3 7 5 8
		 * ������� - 6 4 2 7 8 5 3 1
		 */
	}
}
