package com.xangqun.sort.tree.tree;

/**
 * �������� �ڲ� ������ʽ��ǰ��������������
 *
 * @author jzj
 * @data 2009-12-17
 */
public class TreeInOrder {

	/**
	 * ����ǰ��ݹ���� pre=prefix(ǰ׺)
	 * @param node Ҫ�����Ľڵ�
	 */
	public static void preOrder(TreeNode node) {
		//����������Ľڵ㲻Ϊ�գ��������ע��Ҷ�ӽڵ���ӽڵ�Ϊnull
		if (node != null) {
			System.out.print(node.getNodeId() + " ");//�ȱ������ڵ�

			if (node instanceof TreeBranchNode) {
				for (int i = 0; i < node.getSubNodes().size(); i++) {
					preOrder((TreeNode) node.getSubNodes().get(i));//�ٱ����ӽڵ�
				}
			}

		}
	}

	/**
	 * ���ĺ���ݹ���� post=postfix(��׺)
	 * @param node Ҫ�����Ľڵ�
	 */
	public static void postOrder(TreeNode node) {
		//����������Ľڵ㲻Ϊ�գ������
		if (node != null) {
			//���Ϊ��֧�ڵ㣬������ӽڵ�
			if (node instanceof TreeBranchNode) {

				for (int i = 0; i < node.getSubNodes().size(); i++) {
					postOrder((TreeNode) node.getSubNodes().get(i));//�ȱ����ӽڵ�
				}
			}
			System.out.print(node.getNodeId() + " ");//��������ڵ�
		}
	}
}
