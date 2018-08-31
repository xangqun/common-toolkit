package com.xangqun.sort.tree.avl;

import java.util.Iterator;
import java.util.Random;

public class AVLTreeTest {
	public static void main(String[] args) {
		AVLTree myTree = new AVLTree();
		Random random = new Random();
		System.out.print("��������Ľڵ�Ϊ��");
		int num = 0;
		//ֱ�����Ľڵ���Ϊnֹ
		while (myTree.size() < 10) {
			num = new Integer(random.nextInt(100));
			myTree.add(num);
			System.out.print(num + " ");
		}
		System.out.println("");
		if (myTree.isAVL()) {
			System.out.println("���ƽ����������ܽڵ�����" + myTree.size());
			System.out.println("���ƽ��������ĸ߶��ǣ�" + myTree.height());
			System.out.println("�����в��� " + num + " �ڵ�:"
					+ myTree.contains(new Integer(num)));
			System.out.println("�����в��� 100 �ڵ�:" + myTree.contains(new Integer(100)));
			System.out.print("���������");
			Iterator itr = myTree.iterator();
			while (itr.hasNext()) {
				System.out.print(itr.next() + " ");
			}
			System.out.println("");

			myTree.remove(num);
			System.out.print("ɾ���ڵ� " + num + " �������");
			itr = myTree.iterator();
			while (itr.hasNext()) {
				System.out.print(itr.next() + " ");
			}
			System.out.println("");

			System.out.println("ʹ�õ�����ɾ�����нڵ�");
			itr = myTree.iterator();
			while (itr.hasNext()) {
				itr.next();
				itr.remove();
			}
			System.out.println("ɾ���������ܽڵ�����" + myTree.size());
		} else {
			System.out.println("failure");
		}
	}
}
