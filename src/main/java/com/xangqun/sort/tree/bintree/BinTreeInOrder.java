package com.xangqun.sort.tree.bintree;

/**
 * 二叉树的三种 内部 遍历：前序、中序、后序
 * 但不管是哪种方式，左子树的遍历在右子树的遍历之前遍历是这有三种遍历方式都
 * 必须遵循的约定
 * @author jzj
 * @date 2009-12-23
 */
public class BinTreeInOrder extends BinTree {

	/**
	 * 节点访问者，可根据需要重写visit方法
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
	 * 树的前序递归遍历 pre=prefix(前缀)
	 * @param node 要遍历的节点
	 */
	private void preOrder(Visitor v, Entry node) {
		//如果传进来的节点不为空，则遍历，注，叶子节点的子节点为null
		if (node != null) {
			v.visit(node.elem);//先遍历父节点
			preOrder(v, node.left);//再遍历左节点
			preOrder(v, node.right);//最后遍历右节点
		}
	}

	public void inOrder(Visitor v) {
		inOrder(v, root);
	}

	/**
	 * 树的中序递归遍历 in=infix(中缀)
	 * @param node 要遍历的节点
	 */
	private void inOrder(Visitor v, Entry node) {
		//如果传进来的节点不为空，则遍历，注，叶子节点的子节点为null
		if (node != null) {
			inOrder(v, node.left);//先遍历左节点
			v.visit(node.elem);//再遍历父节点
			inOrder(v, node.right);//最后遍历右节点
		}
	}

	public void postOrder(Visitor v) {
		postOrder(v, root);
	}

	/**
	 * 树的后序递归遍历 post=postfix(后缀)
	 * @param node 要遍历的节点
	 */
	private void postOrder(Visitor v, Entry node) {
		//如果传进来的节点不为空，则遍历，注，叶子节点的子节点为null
		if (node != null) {
			postOrder(v, node.left);//先遍历左节点
			postOrder(v, node.right);//再遍历右节点
			v.visit(node.elem);//最后遍历父节点
		}
	}

	//测试
	public static void main(String[] args) {

		//创建二叉树
		int[] nums = new int[] { 1, 2, 3, 4, 0, 0, 5, 0, 6, 0, 0, 0, 0, 7, 8 };
		BinTreeInOrder treeOrder = new BinTreeInOrder();
		treeOrder.createBinTree(nums);
		System.out.print("前序遍历 - ");
		treeOrder.preOrder(new Visitor() {
		});
		System.out.println();
		System.out.print("中序遍历 - ");
		treeOrder.inOrder(new Visitor() {
		});
		System.out.println();
		System.out.print("后序遍历 - ");
		treeOrder.postOrder(new Visitor() {
		});
		/*
		 * output:
		 * 前序遍历 - 1 2 4 6 3 5 7 8
		 * 中序遍历 - 4 6 2 1 3 7 5 8
		 * 后序遍历 - 6 4 2 7 8 5 3 1
		 */
	}
}
