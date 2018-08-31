package com.xangqun.sort.tree.search;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * ������������Ҳ�ж�����������ʵ��
 *
 * @author jzj
 * @data 2009-12-18
 * @param <E>
 */
public class BinSearchTree<E extends Comparable<E>> extends AbstractSet<E> implements
		TreeOrder<E> {
	private static class Entry<E> {
		/*
		 * ע���ڲ�����ֶ�һ�㶨���Ĭ�Ϸ������η�Ҫ��һ�㣬��Ϊ�����ⲿ�����ֱ���ֶ�
		 * ��������Ҫʹ��get��set������ֻ��Ϊ�˸���࣬�����ڲ�������˽�еģ���������
		 * ��ʹ��ͬ��Ҳ�ǲ�����ֱ�ӷ��ʵ���
		 */
		E elem;//������
		Entry<E> paraent;//���ڵ�
		Entry<E> left;//��ڵ�
		Entry<E> right;//�ҽڵ�

		//���캯��ֻ���������������ҽڵ��ǵ���add����ʱ����
		public Entry(E elem, Entry<E> parent) {
			this.elem = elem;
			this.paraent = parent;
		}
	}

	private Entry<E> root;//���ڵ�
	private int size;//���ڵ����

	public BinSearchTree() {
		root = null;
	}

	//ǰ�����
	public void preOrder(Visitor<E> v) {
		preOrder(root, v);
	}

	private final void preOrder(Entry<E> p, Visitor<E> v) {
		if (p != null) {
			v.visit(p.elem);
			preOrder(p.left, v);
			preOrder(p.right, v);
		}
	}

	//�������
	public void inOrder(Visitor<E> v) {
		inOrder(root, v);
	}

	private final void inOrder(Entry<E> p, Visitor<E> v) {
		if (p == null) {
			return;
		}
		inOrder(p.left, v);
		v.visit(p.elem);
		inOrder(p.right, v);

	}

	//�������
	public void postOrder(Visitor<E> v) {
		postOrder(root, v);

	}

	private final void postOrder(Entry<E> p, Visitor<E> v) {
		if (p == null) {
			return;
		}

		postOrder(p.left, v);
		postOrder(p.right, v);
		v.visit(p.elem);

	}

	//���
	public void levelOrder(Visitor<E> v) {
		if (root == null) {
			return;
		}
		LinkedList<Entry<E>> queue = new LinkedList<Entry<E>>();
		queue.addLast(root);
		while (!queue.isEmpty()) {
			Entry<E> p = queue.removeFirst();
			v.visit(p.elem);
			if (p.left != null) {
				queue.add(p.left);
			}
			if (p.right != null) {
				queue.add(p.right);
			}
		}
	}

	public int size() {
		return size;
	}

	/**
	 * �Ƿ���ĳԪ��
	 * @param e
	 * @return boolean
	 */
	public boolean contanins(E e) {
		Entry<E> tmp = root;
		int comp;
		while (tmp != null) {//�������Ϊ��
			comp = e.compareTo(tmp.elem);
			//�����tmpԪ����ȣ��򷵻�
			if (comp == 0) {
				return true;
			}//�����tmpС������tmp������������
			else if (comp < 0) {
				tmp = tmp.left;
			}//�����tmp������tmp������������
			else {
				tmp = tmp.right;
			}
		}
		//�������Ϊ�ջ�����Ϊ��ʱû���ҵ�ʱ
		return false;
	}

	/**
	 * ���������������ӽڵ�
	 * �������Ԫ�����Ǳ�����е�Ҷ��㣬�����ڲ���Ԫ�غ�û�б�Ҫ������֯��������AVL����
	 * RED-BLACK����̫һ��
	 * @param e
	 * @return boolean
	 */
	public boolean add(E e) {
		//1�������Ϊ�գ���ֱ�Ӽ���
		if (root == null) {
			//���ĸ��ڵ�Ϊnull��Ҳ����˵ֻҪ��parentΪnull�Ľڵ���Ǹ�Ԫ��
			root = new Entry<E>(e, null);
			size++;
			return true;
		}//�������Ϊ��
		else {
			Entry<E> tmp = root;
			int comp;
			while (true) {//��ѭ����ֱ���ڵ���뵽��ȷλ�û�Ԫ���Ѵ���
				comp = e.compareTo(tmp.elem);
				//2�������ӵ�Ԫ��e��tmp��ȣ����ʾԪ�ش��ڣ�ֱ�ӷ���ʧ��
				if (comp == 0) {
					return false;
				}//3�������ӵ�Ԫ��eС��tmp�ڵ㣬��Ҫ��ӵ�tmp���������е�ĳ��λ����
				else if (comp < 0) {
					//���tmp��������Ϊ��Ϊ�գ���Ҫ��������ӵ�
					if (tmp.left != null) {
						tmp = tmp.left;
					}//���tmpû����ڵ㣬��������Ԫ�����ó�tmp�����ӽڵ�
					else {
						tmp.left = new Entry<E>(e, tmp);
						size++;
						return true;
					}
				}//4��������tmp���������������λ��
				else {
					//���tmp��������Ϊ��Ϊ�գ���Ҫ��������ӵ�
					if (tmp.right != null) {
						tmp = tmp.right;
					}//���tmpû�����ӽڵ㣬��������Ԫ�����ó�tmp�����ӽڵ�
					else {
						tmp.right = new Entry<E>(e, tmp);
						size++;
						return true;
					}
				}
			}
		}
	}

	/**
	 * ɾ��ָ�����������Ԫ��
	 * @param p
	 * @return boolean
	 */
	public boolean remove(E p) {
		//������������Ҵ�ɾ����Ԫ��
		Entry<E> tmp = getEntry(p);
		if (tmp == null) {//���Ԫ��û���ҵ�����ɾ��ʧ��
			return false;
		} else {
			//ɾ��Ԫ��
			deleteEntry(tmp);
			return true;
		}
	}

	/**
	 * ɾ��ָ���Ľڵ�ʵ��
	 *
	 * �㷨˼�룺
	 *
	 * 1������ɾ���Ľڵ�p��Ҷ�ӽڵ㣬��ֱ��ɾ���ýڵ㣻
	 *
	 * 2������ɾ���Ľڵ�pֻ��һ���ӽڵ㣬��p���ӽڵ���p�ĸ��ڵ�ֱ�����ӣ�Ȼ��ɾ���ڵ�p��
	 * Ϊʲôֻ��һ���ӽڵ�ʱ����ֱ�ӽӵ�ɾ���ڵ�ĸ��ڵ�������?��Ϊֻ��һ���ӽڵ㣬ֱ�ӽ���
	 * ȥ����Ӱ�������ӽڵ㱾������򣬵�Ȼ������Ӱ������һ����������Ϊ��һ��������������!����
	 *
	 * 3������ɾ���ڵ�p�������ӽڵ�ʱ��Ӧ��ʹ�����������ʽ�õ���ֱ��ǰ�ýڵ�S��ֱ�Ӻ�̽ڵ�s
	 * ��ֵ�������s��ֵ��Ȼ��ɾ���ڵ�s����ע���ڵ�s�϶���������1��2���֮һ��������ֱ��ɾ��
	 * p���������Խ���ɾ������ת��������1��2���⣻
	 *
	 * @param p ָ��ɾ���Ľڵ�p
	 */
	private void deleteEntry(Entry<E> p) {
		//���ɾ���Ľڵ�p����������ʱ��������ת����ɾ��Ҷ�ӽڵ��ֻ��һ���ӽڵ�Ľڵ�����
		if (p.left != null && p.right != null) {
			/*
			* ɾ���������ӽڵ�Ľڵ�ʾ��ͼ(ת����ɾ��ֻ��һ���ӽڵ�Ľڵ��Ҷ�ӽڵ�����)��
			*
			* p �� 80         ��ֱ�Ӻ��Ԫ��s��elem�滻pԪ�ص�          90
			*     /\        elem��Ȼ��pָ��s������������ת��        /\
			*   15 110       ��ɾ��ֻ��һ���ӽڵ�Ľڵ�p������        15 110
			*       /                                             /
			*  s �� 90                   ��                 s��p �� 90
			*       \                                             \
			*      105                                           105
			*
			*/
			/*
			 * ���Ҵ�ɾ���ڵ�p����������ڵ��ֱ�Ӻ�̽ڵ㡣ע���ú�̽ڵ�ֻ������Ҷ�ӽڵ�
			 * ����Ҷ�ӽڵ�ֻ�������������֣�һ���Ǿ������ӽڵ㱾����Ϊ���ܴ�ɾ���ڵ�p��
			 * ���ӽڵ�û�����������ˣ���һ�־��Ǵ�ɾ���ڵ�p���ӽڵ��������������ߵ�Ҷ��
			 * �ڵ㣩����ֻ��һ�����ӽڵ�Ľڵ㣨�ýڵ�ֻ�������������ڶ���Ҷ�ӽڵ����϶���
			 * һ�����������ˣ�
			 */
			Entry<E> s = successor(p);
			/*
			 * ����ɾ���Ľڵ�p����������������ʱ�����ǲ�������ɾ��p����ڵ㣬�����ú�̽ڵ�s
			 * ���滻p�ڵ㣬������������ʲô��̽ڵ��������elem�滻��ɾ���ڵ�p��������elem��
			 * �滻֮�������������ɾ���Ľڵ�p��ɺ�̽ڵ�s������pָ��s������Ϊ����������ת
			 * ����ɾ��Ҷ�ӽڵ��ֻ��һ���ӽڵ㣨����ӽڵ��и��ص����������һ��Ϊ�գ��Ľڵ��
			 * �����ˣ�����Ҳ���ܹ�������������ɾ��Ҷ�ӽڵ���ɾ��ֻ��һ���ӽڵ�Ľڵ�߼���
			 */
			p.elem = s.elem;//ʹ�ú�̽ڵ�s�������滻p��������

			p = s;//��pָ��ֱ�Ӻ�̽ڵ�s������ɾ��pʱʵ������ɾ����ֱ�Ӻ�ڵ�
		}

		/*
		 * !! ע���������е�����ʱ�������ɾ���Ľڵ�p��������������ʱ���ѱ���������߼�ת������
		 * ɾ��Ҷ�ӽڵ��ֻ��һ���ӽڵ㣨һ�������ӽڵ㣩�Ľڵ������ˣ���Ȼ�����ɾ���߼�����ֻ��
		 * ����ɾ��ֻ��һ�����ӽڵ�Ľڵ㣬��������ɾ��ֻ��һ�����ӽڵ�Ľڵ㣬��֮��������ɾ��ֻ
		 * һ���ӽڵ�Ľڵ㣬����������ӽڵ��������С�
		 *
		 * �������ʼɾ��Ҷ�ӽڵ��ֻ��һ���ӽڵ�Ľڵ㣺
		 */

		//����ɾ���Ľڵ�p��Ҷ�ӽڵ㣬��ֱ��ɾ���ýڵ㣬�����ú�̽ڵ��
		if (p.left == null && p.right == null) {
			/*
			* ɾ��Ҷ�ӽڵ�ʾ��ͼ��
			*
			*         80               80
			*         /\               /\
			*       20 110     ��     20 110
			*        \   /               /
			*    p �� 50 90              90
			*            \               \
			*           105             105
			*
			* pָ��Ҫɾ���Ľڵ�50��Ҫ���ľ��ǽ�50�ĸ��ڵ�Entry����Ԫ��Ϊ20��Entry���󣩵�
			* ���ӽڵ��޸�Ϊnull
			*/
			//����ɾ���Ľڵ�p�Ǹ�Ԫ�أ�����û���ӽڵ�ʱ��ֱ��ɾ���ͷŽڵ�
			if (p.paraent == null) {
				root = null;
			}//�����ɾ���Ľڵ�pΪ��Ҷ�ӽڵ㣬��Ѹ��ڵ����ָ����Ϊnull
			else if (p == p.paraent.left) {
				p.paraent.left = null;
			}//����ɾ���Ľڵ�pΪ��Ҷ�ӽڵ㣬��Ѹ��ڵ����ָ����Ϊnull
			else {
				p.paraent.right = null;
			}

		}//����ɾ������ֻ��һ���ӽڵ㣨���������Ҷ��ɣ��Ľڵ�ʱ������ʹ�ú�̽ڵ��
		else {

			/*
			* !! ע�����ˣ�pֻ��һ���ӽڵ㣬�����ܼ������ӽڵ�ͬʱ�������ӽڵ㣬��Ϊ����У�
			* ǰ���߼�Ҳ���pת������ֻ����һ�����ӽڵ�Ľڵ�
			*/

			/*
			 * ɾ��ֻ��һ���ӽڵ��Ԫ��ʾ��ͼ��
			 *
			 *          80               80
			 *          /\               /\
			 *     p �� 20 110     ��    50 110
			 *          \  /               /
			 *    rep �� 50 90             90
			 *              \              \
			 *              105           105
			 *
			 * pָ��Ҫɾ���Ľڵ�20�������ڶ��������������¿ն������Ա���Ҫ��ĳ��Ԫ����ȡ��20��
			 * ��ôѡ���ĸ�Ԫ�غ�?�߼���ѡ��ɾ�����ӽڵ�15��������15����20�ĸ��ڵ��ϡ�
			 */
			Entry<E> rep;// ָ�������滻��ɾ���ڵ�p�ģ�ֻ���������ӽڵ�������ӽڵ�

			if (p.left != null) {
				//���ֻ�����ӽڵ�ʱ���������ӽڵ��滻Ҫɾ���Ľڵ�p
				rep = p.left;
			} else {//����ֻ�����������������ӽڵ��滻Ҫɾ���Ľڵ�p
				rep = p.right;
			}
			//--�޸��滻�ڵ�ĸ�ָ��ָ��
			/*
			 * ʹ�滻�ڵ�ĸ�ָ��ָ��ɾ���ڵ�p�ĸ��ڵ㣬ע�����ɾ�����Ǹ��ڵ㣬�������ӽڵ�
			 * �ĸ�ָ�붼��ָ��null�����ʱ�轫rootָ��������ӽڵ㣬��������ӽڵ㽫��Ϊ��
			 * �ڵ�
			 */
			rep.paraent = p.paraent;//�����滻Ԫ�صĸ�

			//--�޸ı�ɾ��Ԫ��p�ĸ��ڵ�������ָ��ָ��
			//���ɾ�����Ǹ�Ԫ�أ�������root
			if (p.paraent == null) {
				root = rep;
			}//���ɾ������ĳ�ڵ�����ӽڵ�
			else if (p == p.paraent.left) {
				p.paraent.left = rep;
			}//����ɾ������ĳ�ڵ�����ӽڵ�
			else {
				p.paraent.right = rep;
			}

		}
		//��ɾ���ڵ��Ϊ������
		p.paraent = null;
		p.left = null;
		p.right = null;

		size--;//ɾ���ڵ�����ڵ������һ
	}

	/**
	 * ����ָ�������������Ԫ��
	 * @param e
	 * @return Entry<E>
	 */
	private Entry<E> getEntry(E e) {
		Entry<E> tmp = root;
		int c;
		while (tmp != null) {//�������Ϊ��
			c = e.compareTo(tmp.elem);
			//�����tmpԪ����ȣ��򷵻�
			if (c == 0) {
				return tmp;
			}//�����tmpС������tmp������������
			else if (c < 0) {
				tmp = tmp.left;
			}//�����tmp������tmp������������
			else {
				tmp = tmp.right;
			}
		}
		//�������Ϊ�ջ�����Ϊ��ʱû���ҵ�ʱ
		return null;
	}

	/**
	 * ����ָ���ڵ������������е�ֱ�Ӻ�̽ڵ�
	 *
	 * ע�����������������ң���Ϊ�������ʱ���������ϵĽڵ㶼���ڸýڵ��ǰ�������
	 *
	 * 1����������ҵĽڵ��������������̽ڵ�һ�����������ϣ���ʱ�������ϵ�ĳ���ڵ���ܳ�Ϊ��
	 * �̽ڵ㣺һ���������ڵ��������û������������û������������ν��ʱ��ֱ�Ӿͷ��ظô���ڵ�
	 * �����ӽڵ㣻�����������ڵ�����ӽڵ�������������������ӽڵ������ߵ��������ڵ㣨ע��
	 * �ýڵ�һ������Ҷ�ӽڵ��ֻ��һ�����ӽڵ����ڵ㣬���ҹ���Ҫһֱ���󣬼�����ʱֻ����գ�
	 * �������ң�
	 *
	 * 2����������ҵĽڵ�û��������������Ҫ�Ӹýڵ�����ķ������������������ҹգ�����̽ڵ�ֻ
	 * ���������ڽڵ��в������������ڵ�����ڵ����ڣ�������������֣�һ�־��Ǵ���ڵ�Ϊĳ�ڵ����
	 * ���������ʱ�ĺ��Ϊ���ڵ㣻�ڶ��־��ǵ�����ڵ�Ϊĳ���ڵ��������ʱ�������ظ��ķ��������ң�
	 * һֱ�ҵ���һ���������������ڽڵ㼴Ϊ��̽ڵ㣬�򵽸�Ϊֹ��û���ҵ�����ýڵ�ֻ����Ϊ�����
	 * �������ڵ㣩��
	 *
	 * @param e ��Ҫ�����ĸ��ڵ��ֱ�Ӻ�̽ڵ�
	 * @return Entry<E> ֱ�Ӻ�̽ڵ�
	 */
	private Entry<E> successor(Entry<E> e) {
		if (e == null) {
			return null;
		}//������ҵĽڵ����������������������ϲ���
		else if (e.right != null) {
			/*
			* ����50�ڵ��ֱ�Ӻ�̣����ҽ��Ϊ55
			*            50
			*             \
			*             75
			*             /
			*            61
			*            /\
			*           55 68
			*            \
			*            59
			*/
			//Ĭ�Ϻ�̽ڵ�Ϊ���ӽڵ㣨������ӽڵ�û��������ʱ��Ϊ�ýڵ㣩
			Entry<E> p = e.right;
			while (p.left != null) {
				//ע��������ӽڵ����������Ϊ�գ������������в��ң��Һ�����ʱҪһֱ�����
				p = p.left;
			}
			return p;
		}//�������ڵ�û������������Ҫ�����ڽڵ��в��Һ�̽ڵ�
		else {

			/*
			* û���������Ľڵ���Ϊ���ڵ�����ӽڵ�36��ֱ�Ӻ��Ϊ37��ͬ���ڵ�68��ֱ�Ӻ��Ϊ75
			* û���������Ľڵ���Ϊ���ڵ�����ӽڵ�37��ֱ�Ӻ��Ϊ50��ͬ���ڵ�28��ֱ�Ӻ��Ϊ30
			* 75Ϊ���ڵ㣬����ֱ�Ӻ��Ϊnull
			*
			*                 50
			*                 /\
			*                37 75
			*                /   /
			*               25   61
			*               /\   /\
			*             15 30 55 68
			*                /\  \
			*              28 32 59
			*                  \
			*                  36
			*                   /
			*                  35
			*/
			//Ĭ�Ϻ�̽ڵ�Ϊ���ڵ㣨�������ڵ�Ϊ���ڵ��������������Ϊ���ڵ㣩
			Entry<E> p = e.paraent;
			Entry<E> c = e;//��ǰ�ڵ㣬����丸��Ϊ��̣����´�ָ�򸸽ڵ�
			//�������ڵ�Ϊ���ڵ���ҽڵ�ʱ�����������ң�һֱҪ�ҵ�cΪ���ӽڵ㣬��p���Ǻ��
			while (p != null && c == p.right) {
				c = p;
				p = p.paraent;
			}
			return p;
		}
	}

	/**
	 * ����ָ���ڵ������������е�ֱ��ǰ���ڵ�
	 *
	 * �����߼�����ֱ�Ӻ�̽ڵ�պ��෴��Գ�
	 * @param e
	 * @return
	 */
	private Entry<E> precursor(Entry<E> e) {
		if (e == null) {
			return null;
		}//������ҵĽڵ����������������������ϲ���
		else if (e.left != null) {
			//Ĭ��ֱ��ǰ���ڵ�Ϊ���ӽڵ㣨������ӽڵ�û��������ʱ��Ϊ�ýڵ㣩
			Entry<E> p = e.left;
			while (p.right != null) {
				//ע��������ӽڵ����������Ϊ�գ������������в��ң��Һ�����ʱҪһֱ���ҹ�
				p = p.right;
			}
			return p;
		}//�������ڵ�û������������Ҫ�����ڽڵ��в���ǰ���ڵ�
		else {
			//Ĭ��ǰ���ڵ�Ϊ���ڵ㣨�������ڵ�Ϊ���ڵ������������ǰ��Ϊ���ڵ㣩
			Entry<E> p = e.paraent;
			Entry<E> current = e;//��ǰ�ڵ㣬����丸��Ϊǰ�������´�ָ�򸸽ڵ�
			//�������ڵ�Ϊ���ڵ����ڵ�ʱ�����������ң�һֱҪ�ҵ�currentΪp�����ӽڵ㣬��s����ǰ��
			while (p != null && current == p.left) {
				current = p;
				p = p.paraent;
			}
			return p;
		}
	}

	/**
	 * �ṩ�������ӿ�
	 * @return
	 */
	public Iterator<E> iterator() {
		return new TreeItrator();
	}

	/**
	 * ���ĵ�����
	 * @author jzj
	 * @date 2009-12-19
	 */

	public class TreeItrator implements Iterator<E> {

		private Entry<E> lastRet;//���һ��next�������صĽڵ�
		private Entry<E> next;//��һ���ڵ�
		private Entry<E> endNode;//�����һ���ڵ�

		TreeItrator() {
			//��ʼ��ʱ����nextָ���ڵ㣬�����û��������ʱ�����Ϊ��
			next = root;
			if (next != null) {
				//���next����������ʱ����Ϊ���ӽڵ㣬ֱ������߽ڵ�Ϊֹ
				while (next.left != null) {
					next = next.left;//�Ӹ��ڵ㿪ʼ��һֱ�����
				}
			}
		}

		//�Ƿ�����һ���ڵ�
		public boolean hasNext() {
			return next != null;
		}

		//������һ���ڵ㣬��nextָ��Ľڵ�
		public E next() {
			if (next == null) {
				throw new NoSuchElementException();
			}
			lastRet = next;
			next = successor(next);//��һ��Ϊֱ�Ӻ�̽ڵ�

			//�����̽ڵ�Ϊnull����ʾ��nextָ��Ľڵ�Ϊ���е����ڵ�
			if (next == null) {
				/*
				 * ʹ��endNode��¼����ĩ�ڵ㣬�Ա� previous ʹ�ã���Ϊnext���ջ�ָ��null��
				 * ���ñ�ָ������ĩ�ڵ�ĺ��棬��ʱprevious��Ҫ������ĩ�ڵ�ģ�������Ҫ���
				 * ��洢����
				 */
				endNode = lastRet;
			}
			return lastRet.elem;
		}

		//�Ƿ���ǰ���ڵ�
		public boolean hasPrevious() {
			return (next != null && precursor(next) != null) || endNode != null;
		}

		//����ǰ���ڵ�
		public E previous() {
			if ((next != null && precursor(next) == null)) {
				throw new NoSuchElementException();
			}

			//����ѵ���������ĩ�ڵ�
			if (endNode != null) {
				//ʹ��lastReturned��next��ָ����ĩ�ڵ�
				lastRet = next = endNode;
				endNode = null;
			} else {//���lastReturnedָ��Ĳ�����ĩ�ڵ�ʱ
				lastRet = next = precursor(next);
			}

			return lastRet.elem;
		}

		//ɾ�����һ��next��previous�������صĽڵ�
		public void remove() {
			if (lastRet == null) {
				throw new IllegalStateException();
			}

			/*
			 * ע�����ɾ���Ľڵ㣨lastRetָ��Ľڵ㣩���������ӽڵ㣬���ڵ���
			 * deleteEntry����ɾ��������ʹ�������̽ڵ���������滻��ɾ���Ľڵ������
			 * ��nextָ��Ľڵ�ᱻ�Ƶ�lastRetλ�ã����������ʱ��ʹ��next����
			 * ��lastRet��λ�ã��� nextָ��Ľڵ㣨Entry��������һ�������������
			 * �Ľڵ㡣���ɾ������һ��Ҷ�ӽڵ��ֻ��һ���ڵ�Ľڵ�ʱ�������������⡣
			 *
			 * ɾ���������ӽڵ�Ľڵ�40��ɾ����nextָ��Ľڵ��ѱ��Ƶ�lastRet������
			 * next�����
			 *
			 *                    �Ⱥ���                 ��ɾ��50
			 * lastRet �� 40    next��lastRet �� 50             next �� 50
			 *           /\         ��         /\                    /\
			 *          20 75                20 75         ��       20 75
			 *             /\                   /\                     \
			 *     next �� 50 80               50 80                  50 80
			 *
			 * ɾ��ֻ��һ���ӽڵ�Ľڵ�20��ɾ����nextָ����Ҫ�ı䣬��Ϊnextָ���Ԫ��û��
			 * �����仯��ɾ��ǰ����ָ��ԭ����30
			 *                50                 50
			 *                /\                 /\
			 *     lastRet �� 20 75   ��   next �� 30 75
			 *                \                 /
			 *         next �� 30               28
			 *                /
			 *               28                20
			 */
			if (lastRet.left != null && lastRet.right != null) {
				next = lastRet;
			}

			deleteEntry(lastRet);//ɾ�����һ��next�������ص�Ԫ��

			lastRet = null;//��������ɾ����ֻ����ʹ��next�����ɾ��
		}
	}

	private static void fixText() {
		BinSearchTree<Integer> bst = new BinSearchTree<Integer>();
		bst.add(50);
		bst.add(37);
		bst.add(75);
		bst.add(25);
		bst.add(61);
		bst.add(15);
		bst.add(30);
		bst.add(55);
		bst.add(68);
		bst.add(28);
		bst.add(32);
		bst.add(59);
		bst.add(36);
		bst.add(36);//���һ���ظ�����������ӽ�ȥ

		//�Ƿ����
		System.out.println(bst.contanins(36));//true
		System.out.println(bst.contanins(38));//false

		//��С
		System.out.println(bst.size());//13

		//����
		Iterator<Integer> itr = bst.iterator();
		while (itr.hasNext()) {
			//15 25 28 30 32 36 37 50 55 59 61 68 75
			System.out.print(itr.next() + " ");
		}
		System.out.println();

		//�Ӻ���ǰ����
		BinSearchTree<Integer>.TreeItrator titr = (BinSearchTree<Integer>.TreeItrator) itr;
		while (titr.hasPrevious()) {
			//75 68 61 59 55 50 37 36 32 30 28 25 15
			System.out.print(titr.previous() + " ");
		}
		System.out.println();

		//���Ե������� previous
		titr = (BinSearchTree<Integer>.TreeItrator) bst.iterator();
		System.out.println(titr.hasPrevious());//false
		System.out.println(titr.next());//15
		System.out.println(titr.previous());//15
		System.out.println(titr.next());//15
		System.out.println(titr.next());//25
		System.out.println(titr.next());//28
		System.out.println(titr.previous());//28

		//ɾ����Ҷ�ӽڵ�36
		bst.remove(36);
		System.out.println(bst.size());//12
		itr = bst.iterator();
		while (itr.hasNext()) {
			//15 25 28 30 32 37 50 55 59 61 68 75
			System.out.print(itr.next() + " ");
		}
		System.out.println();

		//ɾ��ֻ��һ�����ӽڵ�Ľڵ�37
		bst.remove(37);
		System.out.println(bst.size());//11
		itr = bst.iterator();
		while (itr.hasNext()) {
			//15 25 28 30 32 50 55 59 61 68 75
			System.out.print(itr.next() + " ");
		}
		System.out.println();

		//ɾ��ֻ��һ�����ӽڵ�Ľڵ�55
		bst.remove(55);
		System.out.println(bst.size());//10
		itr = bst.iterator();
		while (itr.hasNext()) {
			//15 25 28 30 32 50 59 61 68 75
			System.out.print(itr.next() + " ");
		}
		System.out.println();

		//ɾ���������ӽڵ�ĸ��ڵ�50
		bst.remove(50);
		System.out.println(bst.size());//9
		itr = bst.iterator();
		while (itr.hasNext()) {
			//15 25 28 30 32 59 61 68 75
			System.out.print(itr.next() + " ");
		}
		System.out.println();

		//����ͨ��������ɾ���ڵ���ڵ�59
		itr = bst.iterator();
		while (itr.hasNext()) {
			if (itr.next() == 59) {
				itr.remove();//ɾ�����һ��next���صĽڵ�
				break;
			}
		}

		while (itr.hasNext()) {
			//61 68 75
			System.out.print(itr.next() + " ");
			itr.remove();
		}

		System.out.println();
		System.out.println(bst.size());//5
	}

	private static void randomTest() {
		BinSearchTree<Integer> myTree = new BinSearchTree<Integer>();
		Random random = new Random();
		System.out.print("��������Ľڵ�Ϊ��");
		int num = 0;
		//ֱ�����Ľڵ���Ϊnֹ
		while (myTree.size() < 21) {
			num = random.nextInt(100);
			myTree.add(num);
			System.out.print(num + " ");
		}
		System.out.println("");

		System.out.println("���ƽ����������ܽڵ�����" + myTree.size());
		System.out.println("�����в��� " + num + " �ڵ�:" + myTree.contains(new Integer(num)));
		System.out.println("�����в��� 100 �ڵ�:" + myTree.contains(new Integer(100)));
		System.out.print("�������(��ǰ����)��");
		BinSearchTree<Integer>.TreeItrator itr = (BinSearchTree<Integer>.TreeItrator) myTree
				.iterator();
		while (itr.hasNext()) {
			System.out.print(itr.next() + " ");
		}
		System.out.println("");

		System.out.print("�������(�Ӻ���ǰ)��");
		while (itr.hasPrevious()) {
			System.out.print(itr.previous() + " ");
		}
		System.out.println("");

		myTree.remove(num);
		System.out.print("ɾ���ڵ� " + num + " �������");
		itr = (BinSearchTree<Integer>.TreeItrator) myTree.iterator();
		while (itr.hasNext()) {
			System.out.print(itr.next() + " ");
		}
		System.out.println("");

		System.out.println("ʹ�õ�����ɾ�����нڵ�");
		itr = (BinSearchTree<Integer>.TreeItrator) myTree.iterator();
		while (itr.hasNext()) {
			itr.next();
			itr.remove();
		}
		System.out.println("ɾ���������ܽڵ�����" + myTree.size());

	}

	private static void order() {
		BinSearchTree<Integer> myTree = new BinSearchTree<Integer>();
		Random random = new Random();
		int num = 0;
		while (myTree.size() < 10) {
			num = random.nextInt(100);
			myTree.add(num);
		}

		System.out.print("ǰ����� - ");
		myTree.preOrder(new Visitor<Integer>() {

			public void visit(Integer e) {
				System.out.print(e + " ");

			}
		});
		System.out.println();

		System.out.print("������� - ");
		myTree.inOrder(new Visitor<Integer>() {

			public void visit(Integer e) {
				System.out.print(e + " ");

			}
		});
		System.out.println();

		System.out.print("������� - ");
		myTree.postOrder(new Visitor<Integer>() {

			public void visit(Integer e) {
				System.out.print(e + " ");

			}
		});
		System.out.println();

		System.out.print("��α��� - ");
		myTree.levelOrder(new Visitor<Integer>() {

			public void visit(Integer e) {
				System.out.print(e + " ");

			}
		});
		System.out.println();
	}

	//����
	public static void main(String[] args) {
		fixText();//�̶�����
		randomTest();//�������
		order();//����
	}
}
