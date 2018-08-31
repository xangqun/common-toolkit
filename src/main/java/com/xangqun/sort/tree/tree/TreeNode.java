package com.xangqun.sort.tree.tree;

import java.util.Iterator;
import java.util.List;

/**
 * ���ڵ����ӿ�
 *
 * @author jzj
 * @data 2009-12-17
 */
public abstract class TreeNode implements Comparable {

	//���ڵ�
	private TreeNode pNode;

	//�����򣬽ڵ��ţ������޸�
	private int nodeId;

	//�����򣬽ڵ����֣����޸�
	private String nodeName;

	//�ڵ���ȣ���Ĭ��Ϊ0
	private int depth;

	public TreeNode getPMenuComponent() {
		return pNode;
	}

	public void setPMenuComponent(TreeNode menuComponent) {
		pNode = menuComponent;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	//����ӽڵ� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public void addSubNode(TreeNode menuComponent) {
		throw new UnsupportedOperationException();
	}

	//ɾ���ӽڵ� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public void removeSubNode(TreeNode menuComponent) {
		throw new UnsupportedOperationException();
	}

	//�޸Ľڵ���Ϣ
	public void modiNodeInfo(String nodeName) {
		this.setNodeName(nodeName);
	}

	//��ȡ�ӽڵ� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public List getSubNodes() {
		throw new UnsupportedOperationException();
	}

	//��ӡ�ڵ���Ϣ
	public void print() {
		throw new UnsupportedOperationException();
	}

	//��ȡ�ڵ���Ϣ
	protected abstract StringBuffer getNodeInfo();

	//�ṩ��ȵ����� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public Iterator createDepthOrderIterator() {
		throw new UnsupportedOperationException();
	}

	//�ṩ������ȵ����� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public Iterator createLayerOrderIterator() {
		throw new UnsupportedOperationException();
	}

	/**
	 * �������ڵ�id���ڵ�Ȼ�ڵ����ӽڵ�������ָ���Ľڵ�
	 * @param treeId
	 * @return TreeNode
	 */
	public TreeNode getTreeNode(int treeId) {
		return getNode(this, treeId);
	}

	/**
	 * ʹ��������������ݹ鷽ʽ����ָ���Ľڵ�
	 *
	 * @param treeNode ���ҵ���ʼ�ڵ�
	 * @param treeId �ڵ���
	 * @return
	 */
	protected TreeNode getNode(TreeNode treeNode, int treeId) {
		throw new UnsupportedOperationException();
	}

	public int compareTo(Object o) {

		TreeNode temp = (TreeNode) o;

		return this.getNodeId() > temp.getNodeId() ? 1 : (this.getNodeId() < temp
				.getNodeId() ? -1 : 0);
	}

	public boolean equals(Object menuComponent) {

		if (!(menuComponent instanceof TreeNode)) {
			return false;
		}
		TreeNode menu = (TreeNode) menuComponent;

		// ��������ڵ��nodeID��Ӧ����Ϊ��ͬһ�ڵ�
		return this.getNodeId() == menu.getNodeId();
	}
}
