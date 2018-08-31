package com.xangqun.sort.tree.tree;

/**
 * ����Ҷ�ӽڵ�
 *
 * @author jzj
 * @data 2009-12-17
 *
 */
public class TreeLeafNode extends TreeNode {
	public TreeLeafNode(int nodeId, String nodeName) {
		this.setNodeId(nodeId);
		this.setNodeName(nodeName);
	}

	// ��ȡҶ�ӽڵ���Ϣ
	protected StringBuffer getNodeInfo() {
		StringBuffer sb = new StringBuffer();

		// ��ӡ����
		for (int i = 0; i < this.getDepth(); i++) {
			sb.append(' ');
		}
		sb.append("---");

		sb.append("[nodeId=");
		sb.append(this.getNodeId());
		sb.append(" nodeName=");

		sb.append(this.getNodeName());
		sb.append(']');

		return sb;
	}

	public String toString() {
		return getNodeInfo().toString();
	}
}
