/**
 * 一、数据结构分类

（一）按逻辑结构
集合(无辑关系)
线性结构(线性表)：数组、链表、栈、队列
非线性结构：树、图、多维数组
（二）按存储结构
顺序（数组）储结构、链式储结构、索引储结构、散列储结构
二、二叉树相关性质
结点的度：一个结点的子树的个数记为该结点的度．
树的度：所有节点中度数最大的结节的度数，叶子节点的度为零。
树的高度：一棵树的最大层次数记为树的高度(或深度)。
有序(无序)树：若将树中结点的各子树看成是从左到右具有次序的，即不能交换，则称该树为有序树。否则称为无序树。
二叉树第i层（i≥1）上至多有2^(i-1)个节点。
深度为k的二叉树至多有2^k-1个节点（k≥1）。
对任何一棵二叉，若叶子节点数为n0，度为2的节点数为n2，则n0=n2+1。
具有n个节点的完全二叉树的深度为 (㏒2^n)(向下取整)+1。
对一棵有n个节点的完全二叉树的节点按层次从上到下，自左至右进行编号，则对任一节点i(1≤i≤n)有：若 i=1，则节点i是二叉树的根，无双亲；若 i>1，则其双亲为 i/2(向下取整)。若2i>n，则节点i没有孩子节点，否则其左孩子为2i。若2i+1>n，则节点i没有右孩子，否则其右孩子为2i+1。
若深度为k的二叉树有2^k-1个节点，则称其为满二叉树。满二叉树是一棵完全二叉树。

对于完全二叉树中，度为1的节点个数只可能为1个或0个。
对于二叉树，如果叶子节点数为n0，度为1的节点数为n1，度为2的节点数为n2，则节点总数n = n0 + n1 + n2。
对于任意树，总节点数 = 每个节点度数和 + 1
二叉树的高度等于根与最远叶节点（具有最多祖先的节点）之间分支数目。空树的高度是-1。只有单个元素的二叉树，其高度为0。
  .
 */
package com.xangqun.sort.tree.bintree;

/**
 * 创建 非完全二叉树、完全二叉树、满二叉树
 *
 * 由于二叉树的节点增加没有什么规则，所以这里只是简单的使用了递一
 * 次性把整棵树创建出来，而没有设计出一个一个添加节点的方法与删除
 *
 * @author jzj
 * @date 2009-12-23
 */
public class BinTree {// Bin=Binary(二进位的, 二元的)

	protected Entry root;//根
	private int size;//树的节点数

	/**
	 * 树的节点结构
	 * @author jzj
	 * @date 2009-12-23
	 */
	protected static class Entry {
		int elem;//数据域，这里我们作为编号
		Entry left;//左子树
		Entry right;//右子树

		public Entry(int elem) {
			this.elem = elem;
		}

		public String toString() {
			return " number=" + elem;
		}
	}

	/**
	 * 根据给定的节点数创建一个完全二叉树或是满二叉树
	 * @param nodeCount 要创建节点总数
	 */
	public void createFullBiTree(int nodeCount) {
		root = recurCreateFullBiTree(1, nodeCount);
	}

	/**
	 * 递归创建完全二叉树
	 * @param num 节点编号
	 * @param nodeCount 节点总数
	 * @return TreeNode 返回创建的节点
	 */
	private Entry recurCreateFullBiTree(int num, int nodeCount) {
		size++;
		Entry rootNode = new Entry(num);//根节点
		//如果有左子树则创建左子树
		if (num * 2 <= nodeCount) {
			rootNode.left = recurCreateFullBiTree(num * 2, nodeCount);
			//如果还可以创建右子树，则创建
			if (num * 2 + 1 <= nodeCount) {
				rootNode.right = recurCreateFullBiTree(num * 2 + 1, nodeCount);
			}
		}
		return (Entry) rootNode;
	}

	/**
	 * 根据给定的数组创建一棵树，这个棵树可以是完全二叉树也可是普通二叉树
	 * 数组中为0的表示不创建该位置上的节点
	 * @param nums 数组中指定了要创建的节点的编号，如果为0，表示不创建
	 */
	public void createBinTree(int[] nums) {
		root = recurCreateBinTree(nums, 0);
	}

	/**
	 * 递归创建二叉树
	 * @param nums 数组中指定了要创建的节点的编号，如果为0，表示不创建
	 * @param index 需要使用数组中的哪个元素创建节点，如果为元素为0，则不创建
	 * @return TreeNode 返回创建的节点，最终会返回树的根节点
	 */
	private Entry recurCreateBinTree(int[] nums, int index) {
		//指定索引上的编号不为零上才需创建节点
		if (nums[index] != 0) {
			size++;
			Entry rootNode = new Entry(nums[index]);//根节点
			//如果有左子树则创建左子树
			if ((index + 1) * 2 <= nums.length) {
				rootNode.left = (Entry) recurCreateBinTree(nums, (index + 1) * 2 - 1);
				//如果还可以创建右子树，则创建
				if ((index + 1) * 2 + 1 <= nums.length) {
					rootNode.right = (Entry) recurCreateBinTree(nums, (index + 1) * 2);
				}
			}
			return (Entry) rootNode;
		}
		return null;

	}

	public int size() {
		return size;
	}

	//取树的最左边的节点
	public int getLast() {
		Entry e = root;
		while (e.right != null) {
			e = e.right;
		}
		return e.elem;
	}

	//测试
	public static void main(String[] args) {

		//创建一个满二叉树
		BinTree binTree = new BinTree();
		binTree.createFullBiTree(15);
		System.out.println(binTree.size());//15
		System.out.println(binTree.getLast());//15

		//创建一个完全二叉树
		binTree = new BinTree();
		binTree.createFullBiTree(14);
		System.out.println(binTree.size());//14
		System.out.println(binTree.getLast());//7

		//创建一棵非完全二叉树
		binTree = new BinTree();
		int[] nums = new int[] { 1, 2, 3, 4, 0, 0, 5, 0, 6, 0, 0, 0, 0, 7, 8 };
		binTree.createBinTree(nums);
		System.out.println(binTree.size());//8
		System.out.println(binTree.getLast());//8

	}
}
