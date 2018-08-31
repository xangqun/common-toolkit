/**
 * ƽ�����������������
 *
 * ƽ�����������˫��ΪAVL������Ҳ��һ�ö������������ǶԶ�����������һ�ָĽ������Ǿ����������ʵĶ���������
 * ��������������������ƽ������������������������������֮��ľ���ֵ������1��
 *
 * ƽ�����ӣ�Balance Factor,BF������Ϊ�ýڵ������������ȼ�ȥ������������ȣ���ƽ������������нڵ��ƽ
 * ������ֻ������-1��0��1��ֻҪ������һ���ڵ��ƽ�����ӵľ���ֵ����1����ö��������ǲ�ƽ����ˡ�
 *
 * ʹ�ö�������������ƽ��Ļ���˼���ǣ�ÿ���ڶ����������в���һ���ڵ�ʱ�����ȼ���Ƿ��������ƻ���ƽ�⣬��
 * �ǣ����ҳ����е���С��ƽ����������ڱ��ֶ������������Ե�����£�������С��ƽ����s���нڵ�֮��Ĺ�ϵ���Դ�
 * ���µ�ƽ�⡣��ν��С��ƽ������ָ�����ڵ��������ƽ�����ӵľ���ֵ����1�Ľڵ���Ϊ����������
 *
 * ����ƽ���������������������ƽ��Ļ������ƾ�����ת����ת�Ƕ�����Ԫ��˳����е��ڡ���ת��Ŀ��������������
 * ʱ�����ɾ��������ƽ�������Ӱ�졣
 *
 * ��������ת��
 * 1)��ĳԪ������ת
 *          80 �� p                    90
 *          /\                        /\
 *         60 90 �� r        ��        80 120
 *            /\                     /\  /
 *          85 120                 60 85 100
 *              /
 *             100
 *
 * 2)��ĳԪ������ת
 *      p �� 100                        85
 *          /\                         /\
 *     l �� 85 120          ��         60 100
 *         /\                         \  /\
 *        60 90                      80 90 120
 *         \
 *         80
 *
 * 3)��ĳԪ�ص����ӽڵ�����ת���������Ƹ�Ԫ���Լ�����ת��������¾��� ���������� �Ľ�ϣ��������ʱ���Է�
 * ��������ֲ�����ֻ��Χ�Ƶ㲻һ������
 *
 * ����100�����ӽڵ�80����ת����������100����ת
 *
 *                ����                   ����
 *         100     ��     p �� 100         ��         90
 *         /\                 /\                   /\
 *    p �� 80 120        l �� 90 120                80 100
 *        /\                 /                    /\   \
 *       60 90 �� r          80                   60 85 120
 *          /               /\
 *         85              60 85
 *
 * 4)��ĳԪ�ص����ӽڵ�����ת���������Ƹ�Ԫ���Լ�����ת��������¾��� ���������� �Ľ�ϣ��������ʱ���Էֽ�
 * �������ֲ�����ֻ��Χ�Ƶ㲻һ������
 *
 * ����80�����ӽڵ�80����ת����������80����ת
 *                     ����               ����
 *          80          ��      80 �� p     ��       85
 *          /\                 /\                 /\
 *         60 100 �� p         60 85 �� r         80 100
 *            /\                 \              /  /\
 *       l �� 85 120               100          60 90 120
 *            \                   /\
 *            90                 90 120
 *
 * ƽ�������ʵ���� AVLTree �еĺܶ෽���������������һ�µģ���ϸ��ο� BinSearchTree ������Ӧ����
 *
 * AVLTree�е�Entry������BinSearchTree�е�Entry������������ģ�����AVLTree�಻����BinSearchTree��
 * ���࣬��Ȼ�ܶ�ķ�����һ���ģ��磺contains��getEntry��successor��iterator��������һЩ������add��de
 * leteEntry��ֻ���ڲ����������˽ڵ�ƽ�����ӵ���������������ֱ�Ӽ̳�������
 *
 * ������������ƽ�����������û����Java���Ͽ����ʵ�֣���RED-BLACK����TreeMapʵ�ֹ�����ȻTreeSetҲ�ǣ�
 * ��Ϊ���ǻ���TreeMapʵ�ֵģ�TreeSet��������Ͼ���һ��������ÿ��Ԫ��ֵ���ֵ�TreeMap����
 *
 */
package com.xangqun.sort.tree.avl;


import java.util.AbstractSet;
import java.util.Iterator;

/**
 * ƽ�����������������
 *
 * @author jzj
 * @data 2009-12-25
 */
public class AVLTree<E extends Comparable<E>> extends AbstractSet<E> {
	private Entry<E> root;//���ڵ�
	private int size;//���ڵ����

	private static class Entry<E> {
		E elem;//������
		Entry<E> parent;//���ڵ�
		Entry<E> left;//��ڵ�
		Entry<E> right;//�ҽڵ�
		/*
		 * ����ƽ�����ӣ��Ⱥű�ʾ����������������ͬ���ĸ߶ȡ����L������������������1�����ΪR������ζ����
		 * ���������1���մ���ʱ��ƽ��ģ�����Ϊ=
		 *             50
		 *             /R\
		 *            20  80
		 *            /L  /R\
		 *           10  70 100
		 *           =   =  /=\
		 *                 92 103
		 *                 =  =
		 */
		char balanceFactor = '=';

		//���캯��ֻ���������������ҽڵ��ǵ���add����ʱ����
		public Entry(E elem, Entry<E> parent) {
			this.elem = elem;
			this.parent = parent;
		}
	}

	private class TreeIterator implements Iterator<E> {

		private Entry<E> lastReturned = null;
		private Entry<E> next;

		TreeIterator() {

			next = root;
			if (next != null)
				while (next.left != null)
					next = next.left;

		}

		public boolean hasNext() {

			return next != null;

		}

		public E next() {

			lastReturned = next;
			next = successor(next);
			return lastReturned.elem;

		}

		public void remove() {

			if (lastReturned.left != null && lastReturned != null)
				next = lastReturned;
			deleteEntry(lastReturned);
			lastReturned = null;

		}
	}

	/**
	 * ����ת��������ǽ�p�Ƶ��������ӽڵ��λ�ã���p�����ӽڵ㱻�Ƶ���Ԫ��ԭ��λ��
	 * @param p ��תԪ��
	 */
	private void rotateLeft(Entry<E> p) {
		/*
		* Χ��50����ת:
		*p �� 50                 90
		*     \                 /\
		* r �� 90      ��       50 100
		*      \
		*     100
		*
		* Χ��80����ת:r�����������p������������Ϊλ��r���������ϵ�����һ��Ԫ��ֵ����p��С��r������r������
		* �����Գ�Ϊp������������û������ġ���ʵ����Ҳ������ͬ��������ֻ��r��������Ϊ�հ���
		*  p �� 80                  90
		*      /\                  /\
		*     60 90 �� r     ��     80 120
		*        /\               /\   /
		*      85 120           60 85 100
		*          /
		*         100
		*
		* Χ��80�ڸ���Χ����ת��Ԫ�ز���Χ�����ĸ���ת����תǰ��������Ƕ������������ұ���תԪ��80�ϵ���
		* ��Ԫ������ת�в��ƶ���50��30��20��40���ĸ�Ԫ�ػ���ԭ��λ�ã�
		*       50                         50
		*       / \                        / \
		*     30   80 �� p                 30  90
		*     /\   /\                     /\  / \
		*   20 40 60 90 �� r      ��      20 40 80 120
		*            /\                       /\   /
		*           85 120                  60 85 100
		*              /
		*             100
		*
		* �ڵ����ݽṹ:
		*  +------------------+
		*  | elem | p | l | r |
		*  +------------------+
		*
		*    +------------------+
		*    | 50 |NULL|NULL| r |
		*    +------------------+
		*                 �I�� �K��
		*         +---------------+
		*         |80| p | l  | r |   �� p
		*         +---------------+
		*         �J       �L     �I�� �K��
		*  +----------------+ +---------------+
		*  |60| p |NULL|NULL| |90| p |  l | r |   �� r
		*  +----------------+ +---------------+
		*                     �J��       �L��    �I�K
		*              +----------------+ +---------------+
		*              |85| p |NULL|NULL| |90| p | l |NULL|
		*              +----------------+ +---------------+
		*                                  �J      �L
		*                           +-----------------+
		*                           |100| p |NULL|NULL|
		*                           +-----------------+
		*/

		Entry<E> r = p.right;//��תԪ�ص����ӽڵ�
		//����תԪ�ص����ӽڵ�����ӽڵ�ӵ���תԪ�ص�������
		p.right = r.left;//��
		//�����תԪ�ص����ӽڵ�������ӽڵ�Ļ���ͬʱ�޸ĸýڵ�ĸ���ָ��ָ��
		if (r.left != null) {
			//����תԪ�ص����ӽڵ�����ӽڵ�ĸ����ó���תԪ��
			r.left.parent = p;//��
		}
		//����תԪ�ص����ӽڵ�ĸ����ó���תԪ�صĸ��������п���pΪ���ڵ㣬��ô��ת��p�ͳɸ��ڵ�
		r.parent = p.parent;//��

		//�����תԪ��Ϊ��Ԫ�أ�����ת����תԪ�ص����ӽڵ�r����Ϊ���ڵ�
		if (p.parent == null) {
			root = r;
		}//����p���Ǹ��ڵ㣬���p�������ڵ����ڵ�ʱ
		else if (p.parent.left == p) {
			//��p�ĸ��ڵ����ָ��ָ��r
			p.parent.left = r;
		}//�������p�������ڵ���ҽڵ�ʱ
		else {
			//��p�ĸ��ڵ����ָ��ָ��r
			p.parent.right = r;//��
		}
		//����תԪ�ص���ڵ�ָ����תԪ��p
		r.left = p;//��
		//����תԪ�صĸ��ڵ�ָ����תԪ���ҽڵ�r
		p.parent = r;//��
	}

	/**
	 * ����ת��������ǽ�p�Ƶ��������ӽڵ��λ�ã���p�����ӽڵ㱻�Ƶ���Ԫ��ԭ��λ��
	 * ������ת����ȫ�ԳƵģ�������ת�е�lef��rigth�������ɵõ�������Ͳ���ϸ������
	 * @param p ��תԪ��
	 */
	private void rotateRight(Entry<E> p) {

		/*
		* Χ��100����ת:
		*  p �� 100              90
		*       /               /\
		* l �� 90      ��       50 100
		*     /
		*    50
		*
		* Χ��80����ת:l�����������p������������Ϊλ��l���������ϵ�����һ��Ԫ��ֵС��p��С��l������l����
		* �������Գ�Ϊp������������û������ġ���ʵ����Ҳ������ͬ��������ֻ��l��������Ϊ�հ���
		*
		*  p �� 80                  60
		*      /\                  /\
		* l �� 60 90         ��     50 80
		*     /\                   \  /\
		*    50 70                55 70 90
		*     \
		*     55
		*/

		Entry<E> l = p.left;
		p.left = l.right;
		if (l.right != null) {
			l.right.parent = p;
		}
		l.parent = p.parent;

		if (p.parent == null) {
			root = l;
		} else if (p.parent.right == p) {
			p.parent.right = l;
		} else {
			p.parent.left = l;
		}
		l.right = p;
		p.parent = l;
	}

	/**
	 * AVLTree���add����������BinSerrchTree���add�������������Ÿ�����ǰ���������ʱ�����¼����һ������
	 * ��Entry������������ȣ������ȵ�ƽ������balanceFactorֵ��L��R(������)������ancestorָ��������Ƚ�
	 * �㣬�����Ƚ���ʲô���أ���ancestor���ӿ�ʼ�������ڵ�·���ϵ��������Ƚڵ㶼��ƽ�⣬��Щ���Ƚڵ����Ϊ
	 * �����ڵ����ò�ƽ���ˣ���Ҫ���µ���ƽ�����ӣ�����ֽ���ڵ���ƽ������ʱ�ǳ�����
	 *
	 * @param elem Ҫ����Ԫ�ص�������
	 * @return
	 */
	public boolean add(E elem) {
		//�����Ϊ�գ���ֱ�Ӳ���
		if (root == null) {
			root = new Entry<E>(elem, null);
			size++;
			return true;
		} else {
			Entry<E> tmp = root;//�����ڵ�ĸ��ڵ㣬�Ӹ��ڵ����濪ʼ�Ҳ����
			Entry<E> ancestor = null;//ƽ�����Ӳ�Ϊ = ��������Ƚڵ�
			int comp; //�ȽϽ��
			while (true) {//��ѭ����ֱ���ҵ�������˳�ѭ��
				comp = elem.compareTo(tmp.elem);
				//����Ѵ��ڸ�Ԫ�أ���ֱ�ӷ���ʧ��
				if (comp == 0) {
					return false;
				}

				//��¼��ƽ������Ƚڵ�
				if (tmp.balanceFactor != '=') {
					//����ĸ����Ƚڵ㲻ƽ�⣬���¼����ѭ�����ancestorָ��ľ������һ��
					//��ƽ������Ƚڵ�
					ancestor = tmp;
				}

				//���С�ڵ�ǰ�ȽϽڵ㣬��������߲���
				if (comp < 0) {

					//�����������Ϊ�գ�����ѭ��������Ҳ����
					if (tmp.left != null) {
						tmp = tmp.left;
					} else {//�������
						tmp.left = new Entry<E>(elem, tmp);
						//�����Ҫ����ƽ�����
						fixAfterInsertion(ancestor, tmp.left);
						size++;
						return true;
					}
				} else {//���ұ߲���

					//�����������Ϊ�գ�����ѭ�����ұ��Ҳ����
					if (tmp.right != null) {
						tmp = tmp.right;
					} else {//�������
						tmp.right = new Entry<E>(elem, tmp);
						//�����Ҫ����ƽ�����
						fixAfterInsertion(ancestor, tmp.right);
						size++;
						return true;
					}
				}

			}
		}
	}

	/**
	 * �������ڵ�󣬻�ı�ĳЩ�ڵ��ƽ�����ӣ�������ӽڵ�������µ���ƽ������
	 *
	 * ����ǰ���ǵķ������о����ɷ�Ϊ6�����
	 *
	 * @param ancestor ����Ԫ�ص����һ����ƽ������Ƚڵ�
	 * @param inserted ����Ԫ��
	 */
	protected void fixAfterInsertion(Entry<E> ancestor, Entry<E> inserted) {
		E elem = inserted.elem;

		if (ancestor == null) {
			/*
			 * ���1��ancestor��ֵΪnull����������Entry�����ÿ�����ȵ�ƽ�����Ӷ��� =����ʱ�����ڵ��
			 * �����ĸ߶Ȳ��ᷢ���仯�����Բ���Ҫ��ת������Ҫ���ľ��ǵ����Ӹ��ڵ㵽����ڵ��·���ϵ�����
			 * �ڵ��ƽ�����Ӱ���
			 *
			 *                       ����55�����
			 *             50            ��           50
			 *             /=\                       /R\
			 *           25   70                   25   70
			 *          /=\   /=\                 /=\   /L\
			 *         15 30 60 90               15 30 60 90
			 *         =  =  =   =               =  =  /L =
			 *                                        55
			 *                                        =
			 */
			//���ڵ��ƽ���������ǵ����ó�����������ΪadjustPath�Ĳ����ñ���һ�������䣬����������ͷ����
			//�������Ǵ�nserted.paraent��ʼ��to���ӽڵ�Ϊֹ�������赥��������������֧Ҳһ��
			if (elem.compareTo(root.elem) < 0) {
				root.balanceFactor = 'L';
			} else {
				root.balanceFactor = 'R';
			}
			//�ٵ���adjustPath�������������ڵ�ĸ��ڵ㵽root��ĳ�ӽڵ�·���ϵ��������Ƚڵ��
			//ƽ������
			adjustPath(root, inserted);
		} else if ((ancestor.balanceFactor == 'L' && elem.compareTo(ancestor.elem) > 0)
				|| (ancestor.balanceFactor == 'R' && elem.compareTo(ancestor.elem) < 0)) {
			/*
			 * ���2��
			 * ancestor.balanceFactor��ֵΪ L������ancestor��������������룬��ancestor.balanceFac
			 * tor��ֵΪ R������ancestor��������������룬�������뷽ʽ�������������ĸ߶ȷ����仯����������
			 * Ҳ����Ҫ��ת��ֱ�ӵ���ƽ�����Ӽ���
			 *                      ����55�����
			 *  ancestor �� 50           ��              50
			 *            /L\                         /=\
			 *          25   70                     25   70
			 *         /R\   /=\                   /R\   /L\
			 *        15 30 60 90                 15 30 60 90
			 *           /L                          /L /L
			 *          28                          28 55
			 *                      ����28�����
			 *  ancestor �� 50            ��             50
			 *            /R\                         /=\
			 *          25   70                     25   70
			 *         /=\   /L\                   /R\   /L\
			 *        15 30 60 90                 15 30 60 90
			 *              /L                       /L /L
			 *             55                       28 55
			 */
			//������ancestor��ƽ������Ϊ ƽ��
			ancestor.balanceFactor = '=';
			//Ȼ����һ����Զ�inserted��ancestor���Ԫ�ؽ��е���
			adjustPath(ancestor, inserted);
		} else if (ancestor.balanceFactor == 'R'
				&& elem.compareTo(ancestor.right.elem) > 0) {
			/*
			 * ���3��
			 * ancestor.balanceFactor��ֵΪ R�����ұ������Entryλ��ancestor�����������������ϣ� ��
			 * ������»��������Ĳ�ƽ�⣬����������ancestor������ת���ٽ���ƽ�����ӵĵ���
			 *
			 * ����93                          �ȵ�����������70����
			 *   ��         50                    ��           50
			 *            /R\                                /R\
			 *          25   70  �� ancestor                25  90
			 *         /L    /R\                          /L   /=\
			 *        15    60 90                        15  70   98
			 *        =     =  /=\                       =  /=\   /L
			 *                80  98                       60 80 93
			 *                =   /=                       =  =  =
			 *                   93
			 *                   =
			 */
			ancestor.balanceFactor = '=';
			//Χ��ancestorִ��һ������
			rotateLeft(ancestor);
			//�ٵ���ancestor.paraent��90���������ڵ�·�������Ƚڵ�ƽ��
			adjustPath(ancestor.parent, inserted);
		} else if (ancestor.balanceFactor == 'L'
				&& elem.compareTo(ancestor.left.elem) < 0) {
			/*
			 * ���4��
			 * ancestor.balanceFactor��ֵΪ L�����ұ������Entryλ��ancestor�����������������ϣ�
			 * ��������»��������Ĳ�ƽ�⣬����������ancestor������ת���ٽ���ƽ�����ӵĵ���
			 *
			 * ����13                         �ȵ�����������50����
			 *   ��         50 �� ancestor        ��            20
			 *            /L\                                /=\
			 *          20   70                            10   50
			 *         /=\   /=\                          /R\   /=\
			 *        10 30 60 90                        5  15 30  70
			 *       /=\ /=\=  =                         = /L /=\  /=\
			 *      5 15 25 35                            13 25 35 60 90
			 *      = /= = =                              =  =  =  =  =
			 *       13
			 *       =
			 */
			ancestor.balanceFactor = '=';
			//Χ��ancestorִ��һ������
			rotateRight(ancestor);
			//�ٵ���ancestor.paraent��20���������ڵ�·�������Ƚڵ�ƽ��
			adjustPath(ancestor.parent, inserted);
		} else if (ancestor.balanceFactor == 'L'
				&& elem.compareTo(ancestor.left.elem) > 0) {
			/*
			 * ���5��
			 * ancestor.balanceFactor��ֵΪ L�����ұ������Entryλ��ancestor�����������������ϡ���
			 * �����Ҳ�ᵼ������ƽ�⣬�������6��һ������Ҫִ��������ת��ִ��һ����ancestor�����ӽڵ���
			 * ��������ִ��һ����ancestorִ��һ��������������ƽ�⣬�������� ��-���� ר�з�������ƽ��
			 * ���ӵĵ���
			 */
			rotateLeft(ancestor.left);
			rotateRight(ancestor);
			//��ת����� ��-���� ר�з�������ƽ�����ӵĵ���
			adjustLeftRigth(ancestor, inserted);
		} else if (ancestor.balanceFactor == 'R'
				&& elem.compareTo(ancestor.right.elem) < 0) {

			/*
			 * ���6��
			 * ancestor.balanceFactor��ֵΪ R�����ұ������Entryλ��ancestor���������� �������ϣ���
			 * �����Ҳ�ᵼ������ƽ�⣬�������5��һ������Ҫִ��������ת��ִ��һ����ancestor�����ӽڵ�����
			 * ������ִ��һ����ancestorִ��һ��������������ƽ�⣬�������� ��-���� ר�з�������ƽ����
			 * �ӵĵ���
			 */
			rotateRight(ancestor.right);
			rotateLeft(ancestor);
			//��ת����� ��-���� ר�з�������ƽ�����ӵĵ���
			adjustRigthLeft(ancestor, inserted);
		}
	}
	/**
	 * ����ָ��·���ϵĽڵ��ƽ������
	 *
	 * ע��ָ����·���ϵ����нڵ�һ����ƽ��ģ�������������Ԫ��С��ĳ�����Ƚڵ㣬
	 * ��������Ƚڵ��µ�ƽ�������� L����֮Ϊ R��
	 *
	 * @param inserted ������Ԫ�ؿ�ʼ���ϵ��������������ã����Ӹ���ʼ��
	 * @param to ֱ���ĸ�Ԫ�ؽ���,����������Ԫ�أ�һ�㴫������Ϊancestor
	 */
	protected void adjustPath(Entry<E> to, Entry<E> inserted) {
		E elem = inserted.elem;
		Entry<E> tmp = inserted.parent;
		//�������ڵ�ĸ��ڵ㿪ʼ���ϻ��ݵ�����ֱ����β�ڵ�toֹ
		while (tmp != to) {
			/*
			 * ����30������25�ұ߲��룬�������ڵ�ƽ��ᱻ���ƣ��������ͻ����������1������ƽ������ΪR����
			 * �Ƚڵ�50��ƽ������Ҳ�����ƣ���Ϊ��50���������ϲ���ģ����Զ�50��˵�������������������1����
			 * ����ƽ������ΪL
			 *    50                      50
			 *    /=\       ����30        /L\
			 *   25  70       ��         25  70
			 *   =   =                   R\  =
			 *                            30
			 *                            =
			 */
			//�������Ԫ�ر����Ƚڵ�С�����������Ƚڵ����߲��룬����Ȼ�����ȵ�������������1
			if (elem.compareTo(tmp.elem) < 0) {

				tmp.balanceFactor = 'L';
			} else {
				//�����嵽�ұߣ���ô���Ƚڵ���Ҿͻ����������1
				tmp.balanceFactor = 'R';
			}
			tmp = tmp.parent;//�ٵ������ȵ�����
		}
	}

	/**
	 * ���� ��-����ת ��ƽ�����ӵ���
	 * ���������
	 * @param ancestor
	 * @param inserted
	 */
	protected void adjustLeftRigth(Entry<E> ancestor, Entry<E> inserted) {
		E elem = inserted.elem;
		if (ancestor.parent == inserted) {
			/*
			 * ��1�֣������Ľڵ�����ת��ɺ�Ϊancestor���ڵ������
			 *
			 * ����40                          ��30����                ��50����
			 *   ��      50 �� ancestor         ��         50         ��
			 *          /L                              /L
			 *         30                              40
			 *         =\                             /=
			 *          40                           30
			 *          =                            =
			 *
			 *                    ����ƽ������
			 *          40            ��            40
			 *          /=\                        /=\
			 *         30 50                      30 50
			 *         =  L                       =   =
			 *
			 * ע������� ��-���� ����fixAfterInsertion�����еĵ�5���������ɵģ�������ֻ��ƽ�����ӵ�
			 * ������ͼ��Ϊ�˺�˵�������������������еģ������������֧Ҳ��һ��
			 */
			ancestor.balanceFactor = '=';
		} else if (elem.compareTo(ancestor.parent.elem) < 0) {
			/*
			 * ��2�֣������Ľڵ�����ת��ɺ�Ϊ��Ϊancestor���ڵ㣬�������ڵ����ת��ancestor�ĸ��ڵ�ҪС
			 * �����
			 *
			 * ���ڲ���Ԫ��(35)����ת��ancestor(50)�ĸ��ڵ�(40)ҪС�� ���������ڵ��������������
			 *
			 * ����35                         ��20����
			 *   ��      50 �� ancestor        ��                 50
			 *          /L\                                    /L\
			 *        20   90                                40   90
			 *       /=\   /=\                              /=\   /=\
			 *     10  40 70  100                          20 45 70 100
			 *    /=\  /=\=   =                            /=\
			 *   5  15 30 45                              10  30
			 *   =  =  =\ =                              /=\   =\
			 *          35                              5  15   35
			 *          =                               =  =    =
			 *
			 *  ��50����                      ����ƽ������
			 *     ��        40                ��                40
			 *              /=\                                /=\
			 *             20  50                            20   50
			 *            /=\  /L\                          /=\   /R\
			 *          10 30 45 90                        10 30 45  90
			 *         /=\  =\   /=\                      /=\  R\    /=\
			 *        5  15  35 70 100                   5  15  35  70  100
			 *        =  =   =  =  =                     =  =   =   =   =
			 *
			 */
			ancestor.balanceFactor = 'R';
			//����ancestor�ֵܽڵ㵽�����·���Ͻڵ�ƽ������
			adjustPath(ancestor.parent.left, inserted);
		} else {
			/*
			 * ��2�֣������Ľڵ�����ת��ɺ�Ϊ��Ϊancestor���ڵ㣬�������ڵ����ת��ancestor�ĸ��ڵ�Ҫ���
			 * ���
			 *
			 * ���ڲ���Ԫ��(42)����ת��ancestor(50)�ĸ��ڵ�(40)Ҫ�����������ڵ��������������
			 *
			 * ����42                           ��20����
			 *   ��      50 �� ancestor          ��               50
			 *          /L\                                    /L\
			 *        20   90                                40   90
			 *       /=\   /=\                              /=\    /=\
			 *     10  40 70  100                          20  45 70 100
			 *    /=\  /=\=   =                           /=\  /=
			 *   5  15 30 45                             10 30 42
			 *   =  =  =  /=                             /=\=  =
			 *           42                             5  15
			 *           =                              =  =
			 *
			 * ��50����                        ����ƽ������
			 *   ��          40                 ��               40
			 *              /=\                                /=\
			 *             20  50                            20   50
			 *            /=\  /L\                          /L\   /=\
			 *          10 30 45 90                        10 30 45  90
			 *         /=\   /=  /=\                      /=\    /L  /=\
			 *        5  15 42  70 100                    5 15  42  70 100
			 *        =  =  =   =  =                      =  =  =   =  =
			 *
			 */
			ancestor.parent.left.balanceFactor = 'L';

			ancestor.balanceFactor = '=';
			//����ancestor�ڵ㵽�����·���Ͻڵ�ƽ������
			adjustPath(ancestor, inserted);
		}
	}

	/**
	 * ���� ��-����ת ��ƽ�����ӵ���
	 *
	 * ��adjustLeftRigth����һ����Ҳ����������������������ǶԳƵ�
	 * @param ancestor
	 * @param inserted
	 */
	protected void adjustRigthLeft(Entry<E> ancestor, Entry<E> inserted) {
		E elem = inserted.elem;
		if (ancestor.parent == inserted) {
			/*
			 * ��1�֣������Ľڵ�����ת��ɺ�Ϊancestor���ڵ������
			 *
			 * ����40                          ��50����                ��30����
			 *   ��       30 �� ancestor        ��        30          ��
			 *           R\                            R\
			 *            50                            40
			 *           /=                              =\
			 *          40                                50
			 *          =                                 =
			 *
			 *          40         ����ƽ������         40
			 *          /=\           ��            /=\
			 *         30 50                      30  50
			 *         R  =                       =   =
			 *
			 * ע������� ��-���� ����fixAfterInsertion�����еĵ�6���������ɵģ�����ֻ�� ƽ�����ӵĵ�
			 * ����ͼ��Ϊ�˺�˵�������������������еģ������������֧Ҳ��һ��
			 */
			ancestor.balanceFactor = '=';
		} else if (elem.compareTo(ancestor.parent.elem) > 0) {
			/*
			 * ��2�֣������Ľڵ�����ת��ɺ�Ϊ��Ϊancestor���ڵ㣬�������ڵ����ת��
			 * ancestor�ĸ��ڵ�Ҫ������
			 *
			 * ���ڲ���Ԫ��(73)����ת��ancestor(50)�ĸ��ڵ�(70)Ҫ�� ���������ڵ��
			 * ������������
			 *
			 * ����73                          ��90����
			 *   ��       50 �� ancestor       ��                  50
			 *          /R\                                    /R\
			 *        20   90                                20   70
			 *       /=\   /=\                              /=\   /=\
			 *     10  40 70  95                          10  40 65 90
			 *     =   = /=\  /=\                         =   =  =  /=\
			 *          65 75 93 97                                75  95
			 *          =  /= =  =                                 /=  /=\
			 *            73                                      73  93 97
			 *            =
			 *
			 * ��50����                       ����ƽ������
			 *   ��          70                ��                70
			 *              /=\                                /=\
			 *            50   90                            50   90
			 *           /R\   /=\                          /L\   /=\
			 *          20 65 75  95                       20 65 75  95
			 *         /=\ =  /=  /=\                     /=\ =  /L  /=\
			 *        10  40 73  93 97                   10  40 73  93 97
			 *        =   =   =   =  =                   =   =  =   =   =
			 *
			 */
			ancestor.balanceFactor = 'L';
			adjustPath(ancestor.parent.right, inserted);
		} else {
			/*
			 * ��2�֣������Ľڵ�����ת��ɺ�Ϊ��Ϊancestor���ڵ㣬�������ڵ����ת��ancestor�ĸ��ڵ�ҪС
			 * �����
			 *
			 * ���ڲ���Ԫ��(73)����ת��ancestor(50)�ĸ��ڵ�(70)Ҫ�� ���������ڵ��������������
			 *
			 * ����63                          ��90����
			 *   ��       50 �� ancestor       ��                 50
			 *          /R\                                    /R\
			 *        20   90                                20   70
			 *       /=\   /=\                              /=\   /=\
			 *     10  40 70  95                          10  40 65 90
			 *     =   = /=\  /=\                         =   =  /= /=\
			 *          65 75 93 97                             63 75 95
			 *          /= =  =  =                              =  =  /=\
			 *         63                                            93 97
			 *         =                                             =  =
			 *
			 * ��50����                       ����ƽ������
			 *   ��          70                ��                70
			 *              /=\                                /=\
			 *            50   90                            50   90
			 *           /R\   /=\                          /=\   /R\
			 *         20  65 75 95                       20  65 75 95
			 *        /=\  /= =  /=\                     /=\  /L    /=\
			 *       10 40 63   93 97                   10 40 63   93 97
			 *       =  =  =    =  =                    =  =  =    =  =
			 */
			ancestor.parent.right.balanceFactor = 'R';
			ancestor.balanceFactor = '=';
			adjustPath(ancestor, inserted);
		}
	}

	/**
	 * ɾ��ָ���ڵ�
	 *
	 * @param elem
	 * @return boolean
	 */
	public boolean remove(E elem) {
		Entry<E> e = getEntry(elem);
		if (e == null)
			return false;
		deleteEntry(e);
		return true;
	}

	private Entry<E> getEntry(E e) {
		Entry<E> tmp = root;
		int c;
		while (tmp != null) {
			c = e.compareTo(tmp.elem);
			if (c == 0) {
				return tmp;
			} else if (c < 0) {
				tmp = tmp.left;
			} else {
				tmp = tmp.right;
			}
		}
		return null;
	}

	private void deleteEntry(Entry<E> p) {
		if (p.left != null && p.right != null) {

			Entry<E> s = successor(p);

			p.elem = s.elem;

			p = s;
		}

		if (p.left == null && p.right == null) {

			if (p.parent == null) {
				root = null;
			} else if (p == p.parent.left) {
				p.parent.left = null;
			} else {
				p.parent.right = null;
			}

		} else {

			Entry<E> rep;
			if (p.left != null) {
				rep = p.left;
			} else {
				rep = p.right;
			}

			rep.parent = p.parent;

			if (p.parent == null) {
				root = rep;
			} else if (p == p.parent.left) {
				p.parent.left = rep;
			} else {
				p.parent.right = rep;
			}

		}
		fixAfterDeletion(p.elem, p.parent);

		p.parent = null;
		p.left = null;
		p.right = null;

		size--;
	}

	/**
	 * ����ָ���ڵ������������е�ֱ�Ӻ�̽ڵ㣬�˷�����ʵ��������������ࣨBinSearchTree.java���Ĵ˷�����
	 * һ���ģ������˼·��ο��������������е���Ӧ����
	 *
	 * @param e ��Ҫ�����ĸ��ڵ��ֱ�Ӻ�̽ڵ�
	 * @return Entry<E> ֱ�Ӻ�̽ڵ�
	 */
	private Entry<E> successor(Entry<E> e) {
		if (e == null) {
			return null;
		}//������ҵĽڵ����������������������ϲ���
		else if (e.right != null) {
			//Ĭ�Ϻ�̽ڵ�Ϊ���ӽڵ㣨������ӽڵ�û��������ʱ��Ϊ�ýڵ㣩
			Entry<E> p = e.right;
			while (p.left != null) {
				//ע��������ӽڵ����������Ϊ�գ������������в��ң��Һ�����ʱҪһֱ�����
				p = p.left;
			}
			return p;
		}//�������ڵ�û������������Ҫ�����ڽڵ��в��Һ�̽ڵ�
		else {

			//Ĭ�Ϻ�̽ڵ�Ϊ���ڵ㣨�������ڵ�Ϊ���ڵ��������������Ϊ���ڵ㣩
			Entry<E> p = e.parent;
			Entry<E> c = e;//��ǰ�ڵ㣬����丸��Ϊ��̣����´�ָ�򸸽ڵ�
			//�������ڵ�Ϊ���ڵ���ҽڵ�ʱ�����������ң�һֱҪ�ҵ�cΪ���ӽڵ㣬��p���Ǻ��
			while (p != null && c == p.right) {
				c = p;
				p = p.parent;
			}
			return p;
		}
	}
	/**
	 * ɾ���ڵ��ƽ�����ʵ��
	 *
	 * @param elem ��ɾ���ڵ��������
	 * @param ancestor ��ɾ���ڵ�����Ƚڵ㣬�Ӹ��ڵ����ϵ���
	 */
	protected void fixAfterDeletion(E elem, Entry<E> ancestor) {

		boolean heightHasDecreased = true;//���ĸ߶��Ƿ���Ҫ��С

		/*
		 * 1�����ɾ�����Ǹ��ڵ㣬��ancestorΪ�գ���ʱ��������ˣ�ֱ���˳�
		 * 2�����ɾ���Ĳ��Ǹ��ڵ㣬�Ҹ��ڵ㶼�ѵ��������˳�
		 * 3�����ɾ���Ĳ��Ǹ��ڵ㣬�����ĸ߶Ȳ����ټ�С��heightHasDecreasedΪfalse�����˳�
		 */
		while (ancestor != null && heightHasDecreased) {

			int comp = elem.compareTo(ancestor.elem);

			/*
			 * ��Ҫɾ���Ľڵ�����������ʱ��comp�ͻ����0����������Ҫɾ��33����ڵ㣬ɾ������deleteEntry
			 * ����36�滻��33�ڵ��е����ݵ�elem��ɾ��������fixAfterDeletion(p.elem, p.parent)��
			 * ��������ƽ�����ӣ�p����ָ���36������p.elem��p.parent.elem����ȵģ�����36
			 *
			 *            82
			 *           /L\
			 *         42   95
			 *        /=\   R\
			 *       33 48   96
			 *      /=\  /=\
			 *     29 36 43 75
			 */

			//��ancestor����������ɾ���ڵ�
			if (comp >= 0) {
				// ancestor ��ƽ������Ϊ '='
				if (ancestor.balanceFactor == '=') {

					/* ɾ��15       ��������
					 *      20       ��           20
					 *      /L\                  /L\
					 *   �� 10 50                10 50
					 *     /=\                  /L
					 *    5  15                5
					 */
					ancestor.balanceFactor = 'L';
					heightHasDecreased = false;

				} // ancestor ��ƽ������Ϊ 'R'
				else if (ancestor.balanceFactor == 'R') {
					/* ɾ��15       ��������                    �´�ѭ������20������
					 *      20       ��         �� 20 �� ancestor   �� ...
					 *      /L\                  /L\
					 *   �� 10 50                10 50
					 *     /R\ R\               /=\ R\
					 *    5  15 60              5 18 60
					 *        R\
					 *         18
					 */
					ancestor.balanceFactor = '=';
					ancestor = ancestor.parent;

				}// ancestor ��ƽ������Ϊ 'L'
				else if (ancestor.balanceFactor == 'L') {
					// ancestor �����ӽڵ�ƽ������Ϊ '='
					if (ancestor.left.balanceFactor == '=') {

						/* ɾ��60       ��������              ��50����
						 *      20       ��     �� 20         ��        20
						 *      /R\              / \                 /R\
						 *     10 50 �� ancestor 10 50 ��             10 45
						 *     /L /L\           /  /L\              /L /R\
						 *    5  45 60         5  45 60            5  35 50 ��
						 *      /=\              /R\                     /L
						 *     35 48            35 48                   48
						 */
						ancestor.left.balanceFactor = 'R';
						ancestor.balanceFactor = 'L';
						rotateRight(ancestor);
						heightHasDecreased = false;

					}// ancestor �����ӽڵ�ƽ������Ϊ 'L'
					else if (ancestor.left.balanceFactor == 'L') {

						/* ɾ��60       ��������           ��50����   �´�ѭ������20������
						 *      20       ��     �� 20     ��    20 �� p     �� ...
						 *      /R\              / \         /R\
						 *     10 50 �� ancestor 10 50 ��     10 45
						 *     /L /L\           /  /=\      /L /=\
						 *    5  45 60         5  45 60    5  35 50 �� ancestor
						 *      /L               /=              =
						 *     35               35
						 */
						Entry<E> p = ancestor.parent;
						ancestor.balanceFactor = '=';
						ancestor.left.balanceFactor = '=';
						rotateRight(ancestor);
						ancestor = p;

					} // ancestor �����ӽڵ�ƽ������Ϊ 'R'
					else if (ancestor.left.balanceFactor == 'R') {

						Entry<E> p = ancestor.parent;

						// ancestor �����ӽڵ�����ӽڵ��ƽ������Ϊ 'L'
						if (ancestor.left.right.balanceFactor == 'L') {

							/* ɾ��60        ��������
							 *      20        ��       20
							 *      /R\               / \
							 *    10   50 �� ancestor 10  50 �� ancestor
							 *    /L\   /L\          / \  /R\
							 *   5  12 45 60        5  12 45 70
							 *  /L    /R\  R\      /     /=\
							 * 3     42 48  70    3     42 48
							 *          /L                 /=
							 *         46                 46
							 *
							 *  ��45����         ��50����           �´�ѭ������20������
							 *    ��     20       ��         20 �� p      �� ...
							 *          /R\                /R\
							 *         10  50 ��          10   48
							 *         /L\  /R\          /L\   /=\
							 *        5 12 48 70        5  12 45 50 �� ancestor
							 *       /L   /=           /L    /=\  R\
							 *      3    45           3     42 46  70
							 *          /=\
							 *         42 46
							 */
							ancestor.balanceFactor = 'R';
							ancestor.left.balanceFactor = '=';

						}// ancestor �����ӽڵ�����ӽڵ��ƽ������Ϊ 'R'
						else if (ancestor.left.right.balanceFactor == 'R') {

							/* ɾ��60        ��������
							 *      20        ��       20
							 *      /R\               / \
							 *    10   50 �� ancestor 10  50 ��
							 *    /L\   /L\          / \  /=\
							 *   5  12 45 60        5  12 45 70
							 *  /L    /R\  R\      /     /L\
							 * 3     42 48  70    3     42 48
							 *           R\                 =\
							 *            49                 49
							 *
							 *  ��45����         ��50����           �´�ѭ������20������
							 *    ��     20       ��         20 �� p      �� ...
							 *          /R\                /R\
							 *         10  50 ��          10   48
							 *         /L\  /=\          /L\   /=\
							 *        5 12 48 70        5  12 45 50 �� ancestor
							 *       /L   /=\          /L    /L  /=\
							 *      3    45 49        3     42  49 70
							 *          /L
							 *         42
							 */
							ancestor.balanceFactor = '=';
							ancestor.left.balanceFactor = 'L';

						}// ancestor �����ӽڵ�����ӽڵ��ƽ������Ϊ '='
						else {
							/* ɾ��60        ��������
							 *      20        ��       20
							 *      /R\               / \
							 *    10   50 �� ancestor 10  50 ��
							 *    /L\   /L\          / \  /=\
							 *   5  12 45 60        5  12 45 70
							 *  /L    /R\  R\      /     /=\
							 * 3     42 48  70    3     42 48
							 *          /=\                /=\
							 *         46 49              46 49
							 *
							 *  ��45����         ��50����           �´�ѭ������20������
							 *    ��     20       ��         20 �� p      �� ...
							 *          /R\                /R\
							 *         10  50 ��          10   48
							 *         /L\  /=\          /L\   /=\
							 *        5 12 48 70        5  12 45  50 �� ancestor
							 *       /L   /=\          /L    /=\   /=\
							 *      3    45 49        3     42 46 49 70
							 *          /=\
							 *         42 46
							 */
							ancestor.balanceFactor = '=';
							ancestor.left.balanceFactor = '=';

						}
						ancestor.left.right.balanceFactor = '=';
						rotateLeft(ancestor.left);
						rotateRight(ancestor);
						ancestor = p;
					}
				}

			}
			//��ancestor����������ɾ���ڵ�,�������ǶԳƵ�
			else if (comp < 0) {

				if (ancestor.balanceFactor == '=') {

					ancestor.balanceFactor = 'R';
					heightHasDecreased = false;
				} else if (ancestor.balanceFactor == 'L') {

					ancestor.balanceFactor = '=';
					ancestor = ancestor.parent;

				} else if (ancestor.balanceFactor == 'R') {

					if (ancestor.right.balanceFactor == '=') {

						ancestor.balanceFactor = 'R';
						ancestor.right.balanceFactor = 'L';
						rotateLeft(ancestor);
						heightHasDecreased = false;

					} else if (ancestor.right.balanceFactor == 'R') {

						Entry<E> p = ancestor.parent;
						ancestor.balanceFactor = '=';
						ancestor.right.balanceFactor = '=';
						rotateLeft(ancestor);
						ancestor = p;

					} else if (ancestor.right.balanceFactor == 'L') {

						Entry<E> p = ancestor.parent;
						if (ancestor.right.left.balanceFactor == 'R') {

							ancestor.balanceFactor = 'L';
							ancestor.right.balanceFactor = '=';

						} else if (ancestor.right.left.balanceFactor == 'L') {

							ancestor.balanceFactor = '=';
							ancestor.right.balanceFactor = 'R';

						} else {

							ancestor.balanceFactor = '=';
							ancestor.right.balanceFactor = '=';

						}
						ancestor.right.left.balanceFactor = '=';
						rotateRight(ancestor.right);
						rotateLeft(ancestor);
						ancestor = p;

					}
				}
			}
		}
	}

	public boolean contains(E o) {

		Entry<E> e = root;

		int comp;

		while (e != null) {

			comp = o.compareTo(e.elem);
			if (comp == 0)
				return true;
			else if (comp < 0)
				e = e.left;
			else
				e = e.right;

		}
		return false;
	}

	//��֤���Ƿ���ƽ�������
	public boolean isAVL() {

		return checkAVL(root);

	}

	/**
	 * ��ָ֤�������Ƿ���ƽ�������
	 * @param p
	 * @return
	 */
	public static <E extends Comparable<E>> boolean checkAVL(Entry<E> p) {

		if (p == null)
			return true;
		//������������������ֵ���ܳ��� 1��������������Ҳ��ƽ�������
		return (Math.abs(h(p.left) - h(p.right)) <= 1 && checkAVL(p.left) && checkAVL(p.right));

	}

	/**
	 * ��ָ���ڵ�ĸ߶�
	 * @param <E>
	 * @param p
	 * @return
	 */
	protected static <E extends Comparable<E>> int h(Entry<E> p) {

		if (p == null)
			return -1;
		return 1 + Math.max(h(p.left), h(p.right));
	}

	/**
	 * ���ĸ߶�
	 * @return
	 */
	public int height() {

		return h(root);

	}

	//���ĸ߶ȷǵݹ���
	public int heightIter() {

		int height = -1;
		for (Entry<E> temp = root; temp != null; height++)
			if (temp.balanceFactor == 'L')
				temp = temp.left;
			else
				temp = temp.right;
		return height;
	}

	@Override
	public Iterator<E> iterator() {
		return new TreeIterator();
	}

	@Override
	public int size() {
		return size;
	}
}
