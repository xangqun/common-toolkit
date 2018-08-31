package com.xangqun.sort.tree.bintree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 二叉树的外部遍历：深度优先（先根）、广度（层次）优先遍历
 *
 * @author jzj
 * @date 2009-12-23
 */
public class BinTreeOutOrder extends BinTree {

	/**
	 * 二叉树深序优先遍历（即二叉树的先根遍历）迭代器，外部可以使用该迭代器
	 * 进行非递归的遍历，这是一种在二叉树结构外部的一种遍历算法，它没有使用
	 * 二叉树本身的结构特点（左右子树递归）进行递归遍历
	 * @author jzj
	 */
	private class DepthOrderIterator implements Iterator {
		//栈里存放的是每个节点
		private Stack stack = new Stack();

		public DepthOrderIterator(Entry node) {

			//根入栈，但在放入左右子节点前会马上出栈，即根先优于左右子节点访问
			stack.push(node);

		}

		//是否还有下一个元素
		public boolean hasNext() {
			if (stack.isEmpty()) {
				return false;
			}
			return true;
		}

		//取下一个元素
		public Entry next() {
			if (hasNext()) {
				//取栈顶元素
				Entry treeNode = (Entry) stack.pop();//先访问根

				if (treeNode.right != null) {
					stack.push(treeNode.right);//再放入右子节点
				}

				if (treeNode.left != null) {
					stack.push(treeNode.left);//最后放入左子节点，但访问先于右节点
				}

				// 返回遍历得到的节点
				return treeNode;

			} else {
				// 如果栈为空
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * 向外界提供先根遍历迭代器
	 * @return Iterator 返回先根遍历迭代器
	 */
	public Iterator createPreOrderItr() {
		return new DepthOrderIterator(root);
	}

	/**
	 * 二叉树广度优先遍历迭代器，外部可以使用该迭代器
	 * 进行非递归的遍历，这是一种在二叉树结构外部的一种遍历算法，它没有使用
	 * 二叉树本身的结构特点（左右子树递归）进行递归遍历
	 * @author jzj
	 */
	private class LevelOrderIterator implements Iterator {
		//使用队列结构实现层次遍历，队列里存储的为节点
		private LinkedList queue = new LinkedList();

		public LevelOrderIterator(Entry node) {

			if (node != null) {
				//将根入队
				queue.addLast(node);
			}
		}

		//是否还有下一个元素
		public boolean hasNext() {
			if (queue.isEmpty()) {
				return false;
			}
			return true;
		}

		//取下一个元素
		public Entry next() {
			if (hasNext()) {
				//取栈顶迭元素
				Entry treeNode = (Entry) queue.removeFirst();

				if (treeNode.left != null) {//如果有左子树，加入有序列表中
					queue.addLast(treeNode.left);
				}
				if (treeNode.right != null) {//如果有右子树，加入有序列表中
					queue.addLast(treeNode.right);
				}

				// 返回遍历得到的节点
				return treeNode;

			} else {
				// 如果队列为空
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * 向外界提供广度优先迭代器
	 * @return Iterator 返回遍历迭代器
	 */
	public Iterator createLayerOrderItr() {
		return new LevelOrderIterator(root);
	}

	public static void main(String[] args) {
		//创建一棵满二叉树
		BinTreeOutOrder treeOrder = new BinTreeOutOrder();
		treeOrder.createFullBiTree(15);
		Iterator preOrderItr = treeOrder.createPreOrderItr();
		System.out.print("深度优先（先根）遍历 - ");
		while (preOrderItr.hasNext()) {
			System.out.print(((Entry) preOrderItr.next()).elem + " ");
		}
		System.out.println();
		System.out.print("广度优先（层次）遍历 - ");
		Iterator layerOrderItr = treeOrder.createLayerOrderItr();
		while (layerOrderItr.hasNext()) {
			System.out.print(((Entry) layerOrderItr.next()).elem + " ");
		}
		/*
		 * output:
		 * 深度优先（先根）遍历 - 1 2 4 8 9 5 10 11 3 6 12 13 7 14 15
		 * 广度优先（层次）遍历 - 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
		 */
	}
}
