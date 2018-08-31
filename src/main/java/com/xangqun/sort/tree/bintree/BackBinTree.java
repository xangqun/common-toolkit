package com.xangqun.sort.tree.bintree;

/**
 *
 * �ɻ��ݵĶ�����
 * �������ķǵݹ����
 *
 * @author jzj
 * @date 2009-12-23
 */
public class BackBinTree {// Bin=Binary(����λ��, ��Ԫ��)

	protected Entry root;//��
	private int size;//���Ľڵ���

	/**
	 * ���Ľڵ�ṹ
	 * @author jzj
	 * @date 2009-12-23
	 */
	private static class Entry {
		int elem;//����������Ϊ�˲��ԣ�����Ϊ�ڵ��Ű�
		Entry paraent;//���ڵ�
		Entry left;//��ڵ�
		Entry right;//�ҽڵ�

		//���캯��ֻ���������������ҽڵ��ǵ���add����ʱ����
		public Entry(int elem, Entry parent) {
			this.elem = elem;
			this.paraent = parent;
		}
	}

	/**
	 * ����ǰ������������ң�ֱ�Ӻ�̽ڵ�
	 *
	 * �Էǵݹ� ������ �ķ�ʽ������
	 *
	 * @param e ��Ҫ�����ĸ��ڵ��ֱ�Ӻ�̽ڵ�
	 * @return Entry ǰ�����ֱ�Ӻ�̽ڵ�
	 */
	public Entry preOrderSuccessor(Entry e) {
		if (e == null) {
			return null;
		}//�����������Ϊ�գ���ֱ�Ӻ��Ϊ���ӽڵ�
		else if (e.left != null) {//�ȿ����ӽڵ��Ƿ�Ϊ��
			return e.left;//�����Ϊ�գ���ֱ�Ӻ��Ϊ���ӽڵ�
		}//���������������Ϊ�գ���ֱ�Ӻ��Ϊ���ӽڵ�
		else if (e.right != null) {//������ӽڵ�Ϊ�գ������ӽڵ��Ƿ�Ϊ��
			return e.right;//������ӽڵ㲻Ϊ�գ��򷵻�
		}//�����ӽڵ㶼Ϊ�յ������
		else {
			Entry s = e.paraent;
			Entry c = e;

			/*
			* һֱ���ϣ�ֱ��c��s������������s����������Ϊ�ա���������һ��36��68�ڵ��
			* ֱ�Ӻ�̽ڵ㣬36��Ӧ����75����68��û�к�̽ڵ���
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
			//�˳�ѭ��ʱ s ����Ϊnull��������� 68 �ڵ��ֱ�Ӻ��ʱ�˳�ѭ��ʱs=null
			if (s == null) {
				return null;
			} else {
				return s.right;
			}
		}
	}

	/**
	 * ����ǰ������������ң�ֱ��ǰ���ڵ�
	 *
	 * �Էǵݹ� ����� �ķ�ʽ������
	 *
	 * @param e ��Ҫ�����ĸ��ڵ��ֱ��ǰ���ڵ�
	 * @return Entry ǰ�����ֱ��ǰ���ڵ�
	 */
	public Entry preOrderAncestor(Entry e) {
		if (e == null) {
			return null;
		}//����ڵ�Ϊ���ڵ����ڵ㣬�򸸽ڵ����ֱ��ǰ���ڵ�
		else if (e.paraent != null && e == e.paraent.left) {
			return e.paraent;
		}//����ڵ�Ϊ���ڵ���ҽڵ�
		else if (e.paraent != null && e == e.paraent.right) {

			Entry s = e.paraent;//ǰ���ڵ�Ĭ��Ϊ���ڵ�
			if (s.left != null) {//������ڵ�û�����ӣ�ǰ���ڵ��Ϊ���ڵ�
				s = s.left;//������ڵ�����ӽڵ㲻�գ����ʼΪ���ڵ����ӽڵ�

				/*
				* ֻҪ���ڵ����ӽڵ㻹���ӽڵ㣬��ǰ���ڵ�Ҫ�����������ҡ���������
				* һ��75ֱ��ǰ���ڵ㣬Ӧ����36
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
					//�ڸ��ڵ�����ӽڵ�������в���ʱ��һ��Ҫ�����ұ߹�
					if (s.right != null) {
						s = s.right;
					} else {//����ұ�û�У�Ȼ���������߹�
						s = s.left;
					}
				}
			}
			return s;
		} else {//����Ǹ��ڵ㣬��û��ֱ��ǰ���ڵ���
			return null;
		}
	}

	/**
	 * ���Һ�����������Ҹ���ֱ�Ӻ�̽ڵ�
	 *
	 * �Էǵݹ� ���Ҹ� �ķ�ʽ������
	 *
	 * @param e ��Ҫ�����ĸ��ڵ��ֱ�Ӻ�̽ڵ�
	 * @return Entry �������ֱ�Ӻ�̽ڵ�
	 */
	public Entry postOrderSuccessor(Entry e) {
		if (e == null) {
			return null;
		}//����ڵ�Ϊ���ڵ�����ӽڵ㣬�򸸽ڵ����ֱ�Ӻ�̽ڵ�
		else if (e.paraent != null && e == e.paraent.right) {
			return e.paraent;
		}//����ڵ�Ϊ���ڵ�����ӽڵ�
		else if (e.paraent != null && e == e.paraent.left) {
			Entry s = e.paraent;//��̽ڵ�Ĭ��Ϊ���ڵ�
			if (s.right != null) {//������ڵ�û�����ӣ���̽ڵ��Ϊ���ڵ�
				s = s.right;//������ڵ�����ӽڵ㲻�գ����ʼΪ���ڵ����ӽڵ�
				/*
				 * ֻҪ���ڵ����ӽڵ㻹���ӽڵ㣬���Ͻڵ�Ҫ�����������ң�
				 * ��15�ĺ�̽ڵ�Ϊ28
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
					//�ڸ��ڵ�����ӽڵ�������в���ʱ��һ��Ҫ������߹�
					if (s.left != null) {
						s = s.left;
					} else {//������û�У�Ȼ��������ұ߹�
						s = s.right;
					}
				}
			}
			return s;
		} else {
			//����Ǹ��ڵ㣬��û�к�̽ڵ���
			return null;
		}
	}

	/**
	 * ���Һ�����������Ҹ���ֱ��ǰ���ڵ�
	 *
	 * �Էǵݹ� ������ �ķ�ʽ������
	 *
	 * @param e ��Ҫ�����ĸ��ڵ��ֱ��ǰ���ڵ�
	 * @return Entry �������ֱ��ǰ���ڵ�
	 */
	public Entry postOrderAncestor(Entry e) {

		if (e == null) {
			return null;
		}//�����������Ϊ�գ���ֱ��ǰ��Ϊ���ӽڵ�
		else if (e.right != null) {//�ȿ����ӽڵ��Ƿ�Ϊ��
			return e.right;//�����Ϊ�գ���ֱ�Ӻ��Ϊ���ӽڵ�
		}//���������������Ϊ�գ���ֱ��ǰ��Ϊ���ӽڵ�
		else if (e.left != null) {
			return e.left;
		}//�����ӽڵ㶼Ϊ�յ������
		else {
			Entry s = e.paraent;
			Entry c = e;

			/*
			* һֱ���ϣ�ֱ��c��s������������s����������Ϊ�ա���������һ��59��15�ڵ��
			* ֱ�Ӻ�̽ڵ㣬59��Ӧ����37����15��û�к�̽ڵ���
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
	 * �����������������ң�ֱ�Ӻ�̽ڵ�
	 *
	 * �Էǵݹ� ����� �ķ�ʽ������
	 *
	 * @param e ��Ҫ�����ĸ��ڵ��ֱ�Ӻ�̽ڵ�
	 * @return Entry �������ֱ�Ӻ�̽ڵ�
	 */
	public Entry inOrderSuccessor(Entry e) {
		if (e == null) {
			return null;
		}//������ҵĽڵ����������������������ϲ���
		else if (e.right != null) {
			//Ĭ�Ϻ�̽ڵ�Ϊ���ӽڵ㣨������ӽڵ�û��������ʱ��Ϊ�ýڵ㣩
			Entry p = e.right;
			while (p.left != null) {
				//ע��������ӽڵ����������Ϊ�գ������������в��ң��Һ�����ʱҪһֱ�����
				p = p.left;
			}
			return p;
		}//�������ڵ�û������������Ҫ�����ڽڵ��в��Һ�̽ڵ�
		else {
			//Ĭ�Ϻ�̽ڵ�Ϊ���ڵ㣨�������ڵ�Ϊ���ڵ��������������Ϊ���ڵ㣩
			Entry p = e.paraent;
			Entry current = e;//��ǰ�ڵ㣬����丸��Ϊ��̣����´�ָ�򸸽ڵ�
			//�������ڵ�Ϊ���ڵ���ҽڵ�ʱ�����������ң�һֱҪ�ҵ�currentΪ���ӽڵ㣬��s���Ǻ��
			while (p != null && current == p.right) {
				current = p;
				p = p.paraent;
			}
			return p;
		}
	}

	/**
	 * �����������������ң�ֱ��ǰ���ڵ�
	 *
	 * �Էǵݹ� �Ҹ��� �ķ�ʽ������
	 *
	 * @param e ��Ҫ�����ĸ��ڵ��ֱ��ǰ���ڵ�
	 * @return Entry �������ֱ��ǰ���ڵ�
	 */
	public Entry inOrderAncestor(Entry e) {
		if (e == null) {
			return null;
		}//������ҵĽڵ����������������������ϲ���
		else if (e.left != null) {
			//Ĭ��ֱ��ǰ���ڵ�Ϊ���ӽڵ㣨������ӽڵ�û��������ʱ��Ϊ�ýڵ㣩
			Entry p = e.left;
			while (p.right != null) {
				//ע��������ӽڵ����������Ϊ�գ������������в��ң��Һ�����ʱҪһֱ���ҹ�
				p = p.right;
			}
			return p;
		}//�������ڵ�û������������Ҫ�����ڽڵ��в���ǰ���ڵ�
		else {
			//Ĭ��ǰ���ڵ�Ϊ���ڵ㣨�������ڵ�Ϊ���ڵ������������ǰ��Ϊ���ڵ㣩
			Entry p = e.paraent;
			Entry current = e;//��ǰ�ڵ㣬����丸��Ϊǰ�������´�ָ�򸸽ڵ�
			//�������ڵ�Ϊ���ڵ����ڵ�ʱ�����������ң�һֱҪ�ҵ�currentΪp�����ӽڵ㣬��s����ǰ��
			while (p != null && current == p.left) {
				current = p;
				p = p.paraent;
			}
			return p;
		}
	}

	/**
	 * ����ָ���Ľڵ�
	 * @param num
	 * @return Entry
	 */
	public Entry getEntry(int num) {
		return getEntry(root, num);
	}

	/**
	 * ʹ��������������ݹ鷽ʽ����ָ���Ľڵ�
	 *
	 * @param entry
	 * @param num
	 * @return
	 */
	private Entry getEntry(Entry entry, int num) {

		//����ҵ�����ֹͣ�������������Ѳ��ҵ��Ľڵ㷵�ظ��ϲ������
		if (entry.elem == num) {//1�����븸�ڵ�ȶ�
			return entry;
		}

		Entry tmp = null;

		if (entry.left != null) {//2����������������
			tmp = getEntry(entry.left, num);
			//������������ҵ������ز�ֹͣ���ң���������ں����ڵ�����
			if (tmp != null) {
				//�ڵ������������ҵ������ظ��ϲ������
				return tmp;
			}
		}

		if (entry.right != null) {//3������������������
			tmp = getEntry(entry.right, num);
			//������������ҵ������ز�ֹͣ���ң���������ں����ڵ�����
			if (tmp != null) {
				//�ڵ������������ҵ������ظ��ϲ������
				return tmp;
			}
		}

		//��ǰ�ȽϽڵ� entry ����Ϊ�ջ�Ϊ��ʱû���ҵ���ֱ�ӷ��ظ��ϲ������null
		return null;
	}

	/**
	 * ���ݸ��������鴴��һ���������������������ȫ������Ҳ������ͨ������
	 * ������Ϊ0�ı�ʾ��������λ���ϵĽڵ�
	 * @param nums ������ָ����Ҫ�����Ľڵ�ı�ţ����Ϊ0����ʾ������
	 */
	public void createBinTree(int[] nums) {
		root = recurCreateBinTree(nums, null, 0);
	}

	/**
	 * �ݹ鴴��������
	 * @param nums ������ָ����Ҫ�����Ľڵ�ı�ţ����Ϊ0����ʾ������
	 * @param paraent ���ڵ�
	 * @param index ��Ҫʹ�������е��ĸ�Ԫ�ش����ڵ㣬���ΪԪ��Ϊ0���򲻴���
	 * @return Entry ���ش����Ľڵ㣬���ջ᷵�����ĸ��ڵ�
	 */
	private Entry recurCreateBinTree(int[] nums, Entry pararent, int index) {
		//ָ�������ϵı�Ų�Ϊ���ϲ��贴���ڵ�
		if (nums[index] != 0) {
			size++;
			Entry root = new Entry(nums[index], pararent);//���ڵ�
			//������������򴴽�������
			if ((index + 1) * 2 <= nums.length) {
				root.left = (Entry) recurCreateBinTree(nums, root, (index + 1) * 2 - 1);
				//��������Դ������������򴴽�
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

	//����
	public static void main(String[] args) {

		//����һ�÷���ȫ������
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
		System.out.print("�����ң����������- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.preOrderSuccessor(entry);
		}
		System.out.println();
		entry = binTree.getEntry(68);
		System.out.print("�������������棩- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.preOrderAncestor(entry);
		}
		System.out.println();
		entry = binTree.getEntry(15);
		System.out.print("���Ҹ������������- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.postOrderSuccessor(entry);
		}
		System.out.println();

		entry = binTree.getEntry(50);
		System.out.print("�����󣨺�����棩- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.postOrderAncestor(entry);
		}
		System.out.println();

		entry = binTree.getEntry(15);
		System.out.print("����ң����������- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.inOrderSuccessor(entry);
		}
		System.out.println();

		entry = binTree.getEntry(75);
		System.out.print("�Ҹ���������棩- ");
		while (entry != null) {
			System.out.print(entry.elem + " ");
			entry = binTree.inOrderAncestor(entry);
		}
		System.out.println();
		/*
		 * output:
		 * �����ң����������- 50 37 25 15 30 28 32 36 75 61 55 59 68
		 * �������������棩- 68 59 55 61 75 36 32 28 30 15 25 37 50
		 * ���Ҹ������������- 15 28 36 32 30 25 37 59 55 68 61 75 50
		 * �����󣨺�����棩- 50 75 61 68 55 59 37 25 30 32 36 28 15
		 * ����ң����������- 15 25 28 30 32 36 37 50 55 59 61 68 75
		 * �Ҹ���������棩- 75 68 61 59 55 50 37 36 32 30 28 25 15
		 */
	}
}
