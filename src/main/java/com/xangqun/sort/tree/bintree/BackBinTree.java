package com.xangqun.sort.tree.bintree;

/**
 *
 * 可回溯的二叉树
 * 二叉树的非递归遍历
 *
 * @author jzj
 * @date 2009-12-23
 */
public class BackBinTree {// Bin=Binary(二进位的, 二元的)

	protected Entry root;//根
	private int size;//树的节点数

	/**
	 * 树的节点结构
	 * @author jzj
	 * @date 2009-12-23
	 */
	private static class Entry {
		int elem;//数据域，这里为了测试，就作为节点编号吧
		Entry paraent;//父节点
		Entry left;//左节点
		Entry right;//右节点

		//构造函数只有两个参数，左右节点是调用add方法时设置
		public Entry(int elem, Entry parent) {
			this.elem = elem;
			this.paraent = parent;
		}
	}

	/**
	 * 查找前序遍历（根左右）直接后继节点
	 *
	 * 以非递归 根左右 的方式遍历树
	 *
	 * @param e 需要查找哪个节点的直接后继节点
	 * @return Entry 前序遍历直接后继节点
	 */
	public Entry preOrderSuccessor(Entry e) {
		if (e == null) {
			return null;
		}//如果左子树不为空，则直接后继为左子节点
		else if (e.left != null) {//先看左子节点是否为空
			return e.left;//如果不为空，则直接后继为左子节点
		}//否则如果右子树不为空，则直接后继为右子节点
		else if (e.right != null) {//如果左子节点为空，则看右子节点是否为空
			return e.right;//如果右子节点不为空，则返回
		}//左右子节点都为空的情况下
		else {
			Entry s = e.paraent;
			Entry c = e;

			/*
			* 一直向上，直到c是s的左子树，且s的右子树不为空。请试着找一下36与68节点的
			* 直接后继节点，36的应该是75，而68则没有后继节点了
			*
			*                            50
			*                            /\
			*                          37  75
			*                         /    /
			*                       25    61
			*                      /\     /\
			*                    15 30   55 68
			*                       /\    \
			*                     28 32   59
			*                         \
			*                         36
			*/
			while (s != null && (c == s.right || s.right == null)) {
				c = s;
				s = s.paraent;
			}
			//退出循环时 s 可以为null，比如查找 68 节点的直接后继时退出循环时s=null
			if (s == null) {
				return null;
			} else {
				return s.right;
			}
		}
	}

	/**
	 * 查找前序遍历（根左右）直接前驱节点
	 *
	 * 以非递归 右左根 的方式遍历树
	 *
	 * @param e 需要查找哪个节点的直接前驱节点
	 * @return Entry 前序遍历直接前驱节点
	 */
	public Entry preOrderAncestor(Entry e) {
		if (e == null) {
			return null;
		}//如果节点为父节点的左节点，则父节点就是直接前驱节点
		else if (e.paraent != null && e == e.paraent.left) {
			return e.paraent;
		}//如果节点为父节点的右节点
		else if (e.paraent != null && e == e.paraent.right) {

			Entry s = e.paraent;//前驱节点默认为父节点
			if (s.left != null) {//如果父节点没有左子，前驱节点就为父节点
				s = s.left;//如果父节点的左子节点不空，则初始为父节点左子节点

				/*
				* 只要父节点左子节点还有子节点，则前驱节点要从其子树中找。请试着找
				* 一下75直接前驱节点，应该是36
				*
				*                            50
				*                            /\
				*                          37  75
				*                         /    /
				*                       25    61
				*                      /\     /\
				*                    15 30   55 68
				*                       /\    \
				*                     28 32   59
				*                         \
				*                         36
				*/
				while (s.left != null || s.right != null) {
					//在父节点的左子节点的子树中查找时，一定要先向右边拐
					if (s.right != null) {
						s = s.right;
					} else {//如果右边没有，然后才能向左边拐
						s = s.left;
					}
				}
			}
			return s;
		} else {//如果是根节点，则没有直接前驱节点了
			return null;
		}
	}

	/**
	 * 查找后序遍历（左右根）直接后继节点
	 *
	 * 以非递归 左右根 的方式遍历树
	 *
	 * @param e 需要查找哪个节点的直接后继节点
	 * @return Entry 后序遍历直接后继节点
	 */
	public Entry postOrderSuccessor(Entry e) {
		if (e == null) {
			return null;
		}//如果节点为父节点的右子节点，则父节点就是直接后继节点
		else if (e.paraent != null && e == e.paraent.right) {
			return e.paraent;
		}//如果节点为父节点的左子节点
		else if (e.paraent != null && e == e.paraent.left) {
			Entry s = e.paraent;//后继节点默认为父节点
			if (s.right != null) {//如果父节点没有右子，后继节点就为父节点
				s = s.right;//如果父节点的右子节点不空，则初始为父节点右子节点
				/*
				 * 只要父节点右子节点还有子节点，则后断节点要从其子树中找，
				 * 如15的后继节点为28
				 *                            50
				 *                            /\
				 *                          37  75
				 *                         /    /
				 *                       25    61
				 *                      /\     /\
				 *                    15 30   55 68
				 *                       /\    \
				 *                     28 32   59
				 *                         \
				 *                         36
				 */

				while (s.left != null || s.right != null) {
					//在父节点的右子节点的子树中查找时，一定要先向左边拐
					if (s.left != null) {
						s = s.left;
					} else {//如果左边没有，然后才能向右边拐
						s = s.right;
					}
				}
			}
			return s;
		} else {
			//如果是根节点，则没有后继节点了
			return null;
		}
	}

	/**
	 * 查找后序遍历（左右根）直接前驱节点
	 *
	 * 以非递归 根右左 的方式遍历树
	 *
	 * @param e 需要查找哪个节点的直接前驱节点
	 * @return Entry 后序遍历直接前驱节点
	 */
	public Entry postOrderAncestor(Entry e) {

		if (e == null) {
			return null;
		}//如果右子树不为空，则直接前驱为右子节点
		else if (e.right != null) {//先看右子节点是否为空
			return e.right;//如果不为空，则直接后继为右子节点
		}//否则如果左子树不为空，则直接前驱为左子节点
		else if (e.left != null) {
			return e.left;
		}//左右子节点都为空的情况下
		else {
			Entry s = e.paraent;
			Entry c = e;

			/*
			* 一直向上，直到c是s的右子树，且s的左子树不为空。请试着找一下59与15节点的
			* 直接后继节点，59的应该是37，而15则没有后继节点了
			*
			*                            50
			*                            /\
			*                          37  75
			*                         /    /
			*                       25    61
			*                      /\     /\
			*                    15 30   55 68
			*                       /\    \
			*                     28 32   59
			*                         \
			*                         36
			*/
			while (s != null && (c == s.left || s.left == null)) {
				c = s;
				s = s.paraent;
			}
			if (s == null) {
				return null;
			} else {
				return s.left;
			}
		}
	}

	/**
	 * 查找中序遍历（左根右）直接后继节点
	 *
	 * 以非递归 左根右 的方式遍历树
	 *
	 * @param e 需要查找哪个节点的直接后继节点
	 * @return Entry 中序遍历直接后继节点
	 */
	public Entry inOrderSuccessor(Entry e) {
		if (e == null) {
			return null;
		}//如果待找的节点有右子树，则在右子树上查找
		else if (e.right != null) {
			//默认后继节点为右子节点（如果右子节点没有左子树时即为该节点）
			Entry p = e.right;
			while (p.left != null) {
				//注，如果右子节点的左子树不为空，则在左子树中查找，且后面找时要一直向左拐
				p = p.left;
			}
			return p;
		}//如果待查节点没有右子树，则要在祖宗节点中查找后继节点
		else {
			//默认后继节点为父节点（如果待查节点为父节点的左子树，则后继为父节点）
			Entry p = e.paraent;
			Entry current = e;//当前节点，如果其父不为后继，则下次指向父节点
			//如果待查节点为父节点的右节点时，继续向上找，一直要找到current为左子节点，则s才是后继
			while (p != null && current == p.right) {
				current = p;
				p = p.paraent;
			}
			return p;
		}
	}

	/**
	 * 查找中序遍历（左根右）直接前驱节点
	 *
	 * 以非递归 右根左 的方式遍历树
	 *
	 * @param e 需要查找哪个节点的直接前驱节点
	 * @return Entry 中序遍历直接前驱节点
	 */
	public Entry inOrderAncestor(Entry e) {
		if (e == null) {
			return null;
		}//如果待找的节点有左子树，则在在子树上查找
		else if (e.left != null) {
			//默认直接前驱节点为左子节点（如果左子节点没有右子树时即为该节点）
			Entry p = e.left;
			while (p.right != null) {
				//注，如果左子节点的右子树不为空，则在右子树中查找，且后面找时要一直向右拐
				p = p.right;
			}
			return p;
		}//如果待查节点没有左子树，则要在祖宗节点中查找前驱节点
		else {
			//默认前驱节点为父节点（如果待查节点为父节点的右子树，则前驱为父节点）
			Entry p = e.paraent;
			Entry current = e;//当前节点，如果其父不为前驱，则下次指向父节点
			//如果待查节点为父节点的左节点时，继续向上找，一直要找到current为p的右子节点，则s才是前驱
			while (p != null && current == p.left) {
				current = p;
				p = p.paraent;
			}
			return p;
		}
	}

	/**
	 * 查找指定的节点
	 * @param num
	 * @return Entry
	 */
	public Entry getEntry(int num) {
		return getEntry(root, num);
	}

	/**
	 * 使用树的先序遍历递归方式查找指定的节点
	 *
	 * @param entry
	 * @param num
	 * @return
	 */
	private Entry getEntry(Entry entry, int num) {

		//如果找到，则停止后续搜索，并把查找到的节点返回给上层调用者
		if (entry.elem == num) {//1、先与父节点比对
			return entry;
		}

		Entry tmp = null;

		if (entry.left != null) {//2、再在左子树上找
			tmp = getEntry(entry.left, num);
			//如果左子树上找到，返回并停止查找，否则继续在后续节点中找
			if (tmp != null) {
				//节点在左子树中找到，返回给上层调用者
				return tmp;
			}
		}

		if (entry.right != null) {//3、否则在右子树上找
			tmp = getEntry(entry.right, num);
			//如果右子树上找到，返回并停止查找，否则继续在后续节点中找
			if (tmp != null) {
				//节点在右子树中找到，返回给上层调用者
				return tmp;
			}
		}

		//当前比较节点 entry 子树为空或不为空时没有找到，直接返回给上层调用者null
		return null;
	}

	/**
	 * 根据给定的数组创建一棵树，这个棵树可以是完全二叉树也可是普通二叉树
	 * 数组中为0的表示不创建该位置上的节点
	 * @param nums 数组中指定了要创建的节点的编号，如果为0，表示不创建
	 */
	public void createBinTree(int[] nums) {
		root = recurCreateBinTree(nums, null, 0);
	}

	/**
	 * 递归创建二叉树
	 * @param nums 数组中指定了要创建的节点的编号，如果为0，表示不创建
	 * @param paraent 父节点
	 * @param index 需要使用数组中的哪个元素创建节点，如果为元素为0，则不创建
	 * @return Entry 返回创建的节点，最终会返回树的根节点
	 */
	private Entry recurCreateBinTree(int[] nums, Entry pararent, int index) {
		//指定索引上的编号不为零上才需创建节点
		if (nums[index] != 0) {
			size++;
			Entry root = new Entry(nums[index], pararent);//根节点
			//如果有左子树则创建左子树
			if ((index + 1) * 2 <= nums.length) {
				root.left = (Entry) recurCreateBinTree(nums, root, (index + 1) * 2 - 1);
				//如果还可以创建右子树，则创建
				if ((index + 1) * 2 + 1 <= nums.length) {
					root.right = (Entry) recurCreateBinTree(nums, root, (index + 1) * 2);
				}
			}
			return (Entry) root;
		}
		return null;

	}

	public int size() {
		return size;
	}

	//测试
	public static void main(String[] args) {

		//创建一棵非完全二叉树
		BackBinTree binTree = new BackBinTree();
		/*
		*                            50
		*                            /\
		*                          37  75
		*                         /    /
		*                       25    61
		*                      /\     /\
		*                    15 30   55 68
		*                       /\    \
		*                     28 32   59
		*                         \
		*                         36
		*/
		int[] nums = new int[] { 50, 37, 75, 25, 0, 61, 0, 15, 30, 0, 0, 55, 68, 0, 0, 0,
				0, 28, 32, 0, 0, 0, 0, 0, 59, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36 };
		binTree.createBinTree(nums);

		Entry entry = binTree.getEntry(50);
		System.out.print("根左右（先序遍历）- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.preOrderSuccessor(entry);
		}
		System.out.println();
		entry = binTree.getEntry(68);
		System.out.print("右左根（先序的逆）- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.preOrderAncestor(entry);
		}
		System.out.println();
		entry = binTree.getEntry(15);
		System.out.print("左右根（后序遍历）- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.postOrderSuccessor(entry);
		}
		System.out.println();

		entry = binTree.getEntry(50);
		System.out.print("根右左（后序的逆）- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.postOrderAncestor(entry);
		}
		System.out.println();

		entry = binTree.getEntry(15);
		System.out.print("左根右（中序遍历）- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.inOrderSuccessor(entry);
		}
		System.out.println();

		entry = binTree.getEntry(75);
		System.out.print("右根左（中序的逆）- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.inOrderAncestor(entry);
		}
		System.out.println();
		/*
		 * output:
		 * 根左右（先序遍历）- 50 37 25 15 30 28 32 36 75 61 55 59 68
		 * 右左根（先序的逆）- 68 59 55 61 75 36 32 28 30 15 25 37 50
		 * 左右根（后序遍历）- 15 28 36 32 30 25 37 59 55 68 61 75 50
		 * 根右左（后序的逆）- 50 75 61 68 55 59 37 25 30 32 36 28 15
		 * 左根右（中序遍历）- 15 25 28 30 32 36 37 50 55 59 61 68 75
		 * 右根左（中序的逆）- 75 68 61 59 55 50 37 36 32 30 28 25 15
		 */
	}
}
