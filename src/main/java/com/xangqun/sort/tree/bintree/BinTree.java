/**
 * һ�����ݽṹ����

��һ�����߼��ṹ
����(�޼���ϵ)
���Խṹ(���Ա�)�����顢����ջ������
�����Խṹ������ͼ����ά����
���������洢�ṹ
˳�����飩���ṹ����ʽ���ṹ���������ṹ��ɢ�д��ṹ
�����������������
���Ķȣ�һ�����������ĸ�����Ϊ�ý��Ķȣ�
���Ķȣ����нڵ��ж������Ľ�ڵĶ�����Ҷ�ӽڵ�Ķ�Ϊ�㡣
���ĸ߶ȣ�һ���������������Ϊ���ĸ߶�(�����)��
����(����)�����������н��ĸ����������Ǵ����Ҿ��д���ģ������ܽ�������Ƹ���Ϊ�������������Ϊ��������
��������i�㣨i��1����������2^(i-1)���ڵ㡣
���Ϊk�Ķ�����������2^k-1���ڵ㣨k��1����
���κ�һ�ö��棬��Ҷ�ӽڵ���Ϊn0����Ϊ2�Ľڵ���Ϊn2����n0=n2+1��
����n���ڵ����ȫ�����������Ϊ (�S2^n)(����ȡ��)+1��
��һ����n���ڵ����ȫ�������Ľڵ㰴��δ��ϵ��£��������ҽ��б�ţ������һ�ڵ�i(1��i��n)�У��� i=1����ڵ�i�Ƕ������ĸ�����˫�ף��� i>1������˫��Ϊ i/2(����ȡ��)����2i>n����ڵ�iû�к��ӽڵ㣬����������Ϊ2i����2i+1>n����ڵ�iû���Һ��ӣ��������Һ���Ϊ2i+1��
�����Ϊk�Ķ�������2^k-1���ڵ㣬�����Ϊ��������������������һ����ȫ��������

������ȫ�������У���Ϊ1�Ľڵ����ֻ����Ϊ1����0����
���ڶ����������Ҷ�ӽڵ���Ϊn0����Ϊ1�Ľڵ���Ϊn1����Ϊ2�Ľڵ���Ϊn2����ڵ�����n = n0 + n1 + n2��
�������������ܽڵ��� = ÿ���ڵ������ + 1
�������ĸ߶ȵ��ڸ�����ԶҶ�ڵ㣨����������ȵĽڵ㣩֮���֧��Ŀ�������ĸ߶���-1��ֻ�е���Ԫ�صĶ���������߶�Ϊ0��
  .
 */
package com.xangqun.sort.tree.bintree;

/**
 * ���� ����ȫ����������ȫ����������������
 *
 * ���ڶ������Ľڵ�����û��ʲô������������ֻ�Ǽ򵥵�ʹ���˵�һ
 * ���԰�������������������û����Ƴ�һ��һ����ӽڵ�ķ�����ɾ��
 *
 * @author jzj
 * @date 2009-12-23
 */
public class BinTree {// Bin=Binary(����λ��, ��Ԫ��)

	protected Entry root;//��
	private int size;//���Ľڵ���

	/**
	 * ���Ľڵ�ṹ
	 * @author jzj
	 * @date 2009-12-23
	 */
	protected static class Entry {
		int elem;//����������������Ϊ���
		Entry left;//������
		Entry right;//������

		public Entry(int elem) {
			this.elem = elem;
		}

		public String toString() {
			return " number=" + elem;
		}
	}

	/**
	 * ���ݸ����Ľڵ�������һ����ȫ������������������
	 * @param nodeCount Ҫ�����ڵ�����
	 */
	public void createFullBiTree(int nodeCount) {
		root = recurCreateFullBiTree(1, nodeCount);
	}

	/**
	 * �ݹ鴴����ȫ������
	 * @param num �ڵ���
	 * @param nodeCount �ڵ�����
	 * @return TreeNode ���ش����Ľڵ�
	 */
	private Entry recurCreateFullBiTree(int num, int nodeCount) {
		size++;
		Entry rootNode = new Entry(num);//���ڵ�
		//������������򴴽�������
		if (num * 2 <= nodeCount) {
			rootNode.left = recurCreateFullBiTree(num * 2, nodeCount);
			//��������Դ������������򴴽�
			if (num * 2 + 1 <= nodeCount) {
				rootNode.right = recurCreateFullBiTree(num * 2 + 1, nodeCount);
			}
		}
		return (Entry) rootNode;
	}

	/**
	 * ���ݸ��������鴴��һ���������������������ȫ������Ҳ������ͨ������
	 * ������Ϊ0�ı�ʾ��������λ���ϵĽڵ�
	 * @param nums ������ָ����Ҫ�����Ľڵ�ı�ţ����Ϊ0����ʾ������
	 */
	public void createBinTree(int[] nums) {
		root = recurCreateBinTree(nums, 0);
	}

	/**
	 * �ݹ鴴��������
	 * @param nums ������ָ����Ҫ�����Ľڵ�ı�ţ����Ϊ0����ʾ������
	 * @param index ��Ҫʹ�������е��ĸ�Ԫ�ش����ڵ㣬���ΪԪ��Ϊ0���򲻴���
	 * @return TreeNode ���ش����Ľڵ㣬���ջ᷵�����ĸ��ڵ�
	 */
	private Entry recurCreateBinTree(int[] nums, int index) {
		//ָ�������ϵı�Ų�Ϊ���ϲ��贴���ڵ�
		if (nums[index] != 0) {
			size++;
			Entry rootNode = new Entry(nums[index]);//���ڵ�
			//������������򴴽�������
			if ((index + 1) * 2 <= nums.length) {
				rootNode.left = (Entry) recurCreateBinTree(nums, (index + 1) * 2 - 1);
				//��������Դ������������򴴽�
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

	//ȡ��������ߵĽڵ�
	public int getLast() {
		Entry e = root;
		while (e.right != null) {
			e = e.right;
		}
		return e.elem;
	}

	//����
	public static void main(String[] args) {

		//����һ����������
		BinTree binTree = new BinTree();
		binTree.createFullBiTree(15);
		System.out.println(binTree.size());//15
		System.out.println(binTree.getLast());//15

		//����һ����ȫ������
		binTree = new BinTree();
		binTree.createFullBiTree(14);
		System.out.println(binTree.size());//14
		System.out.println(binTree.getLast());//7

		//����һ�÷���ȫ������
		binTree = new BinTree();
		int[] nums = new int[] { 1, 2, 3, 4, 0, 0, 5, 0, 6, 0, 0, 0, 0, 7, 8 };
		binTree.createBinTree(nums);
		System.out.println(binTree.size());//8
		System.out.println(binTree.getLast());//8

	}
}
