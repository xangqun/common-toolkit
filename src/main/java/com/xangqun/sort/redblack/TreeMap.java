package com.xangqun.sort.redblack;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
/**
 *
 *
 *����ƽ����������������Ƕ������������ֺ�����Ƕ�ƽ���������һ�ָĽ�ʵ�֣��������ĺܶ�˼���㷨����Դ����������ƽ�����������������������е���ӡ�ɾ�������Ҽ�����ֱ�Ӻ�̽ڵ�ȣ�ƽ��������е������������ȶ���һ���ģ����Ե�������Щ����ʱ��Ҫ��ο��������ڣ�������������������ʵ�� ���롶ƽ�������ʵ�� ��

SortedMap �ӿڵĻ��ں������ʵ�֡����ౣ֤��Map��������˳�����йؼ��֣�����ʹ�õĹ��췽����ͬ�����ܻᰴ�ռ��������Ȼ˳���������(Comparable)�����߰��մ���ʱ���ṩ�ıȽ�(Comparator)�������򡣴�ʵ�ֲ���ͬ���ģ�����߳�ͬʱ����һ��ͬһ��mapʱ��һ��ͨ���Է�װ��map�Ķ������ͬ����������ɣ�����ʹ��  Map m = Collections.synchronizedMap(new TreeMap(...)) ��������װ��map

RED-BLACK�������ʣ�
1����Ԫ����Զ����ɫ��
2�������ڵ��⣬�����²���Ľڵ㶼�Ǻ�ɫ�ġ�

3��Red���򣺺�ɫ�ڵ���ӽڵ㶼�Ǻ�ɫ�ģ�������������ɫ�ڵ����ڡ�
4��Path���򣺴Ӹ�Ԫ�ص�ĳ���ӽڵ����ֻ��һ���ӽڵ��Ԫ�ص�����·���У���ɫԪ�صĸ���������ͬ��
 * @author xangqun
 *
 */
public class TreeMap extends AbstractMap implements SortedMap, Cloneable,
		java.io.Serializable {

	//�Ƚ���������TreeMap��key���������Ϊnull����ʹ��key����Ȼ����
	private Comparator comparator = null;

	private transient Entry root = null;//���ڵ�

	private transient int size = 0;//���еĽڵ���

	/*
	 * TreeMap�ṹ���޸ĵĴ�����ע���ṹ���޸���ָ��ӻ�ɾ��һ������ӳ���ϵ�Ĳ����� ���ı���
	 * ���м�������ֵ���ǽṹ�ϵ��޸ġ�
	 *
	 * �����д���� collection ��ͼ��������keySet��values��entrySet���������� �����صĵ���
	 * �����ǿ���ʧЧ�ģ��ڵ���������֮�������map�޸��˽ṹ������ͨ������������� remove ��
	 *  add ����������map�����κ�ʱ���κη�ʽ���޸ģ������������׳� ConcurrentModification
	 *  Exception����ˣ���Բ������޸ģ��������ܿ����ȫʧЧ��������ð���ڽ����ڲ�ȷ����ʱ���
	 *  �Ϸ�����ȷ������ķ��ա�
	 */
	private transient int modCount = 0;

	//���ӽڵ��modCount��size�������
	private void incrementSize() {
		modCount++;
		size++;
	}

	private void decrementSize() {
		modCount++;
		size--;
	}

	/**
	 * Ĭ�Ϲ�����������һ���յ�map������key����Ȼ�Ƚ��������в��뵽map�еĽڵ��key����ʵ��
	 * ����Ȼ�Ƚ���Comparable�ӿڡ�
	 */
	public TreeMap() {
	}

	/**
	 * ����һ���µĿ�ӳ�䣬��ӳ����ݸ����ıȽ�����������
	 */
	public TreeMap(Comparator c) {
		this.comparator = c;
	}

	/**
	 * ����һ����map��������Ԫ���������map��ͬ�������map���ռ�����Ȼ˳����йؼ�������
	 */
	public TreeMap(Map m) {
		putAll(m);
	}

	/**
	 * ����һ���µ�ӳ�䣬������Ԫ���������map��ͬ����ӳ�䰴��SortedMap��ͬ������ʽ��������
	 */
	public TreeMap(SortedMap m) {
		comparator = m.comparator();
		//...
	}

	public int size() {
		return size;
	}

	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	public boolean containsValue(Object value) {
		return (root == null ? false : (value == null ? valueSearchNull(root)
				: valueSearchNonNull(root, value)));
	}

	//�����Ƿ���value��ֵΪnull�Ľڵ�
	private boolean valueSearchNull(Entry n) {
		if (n.value == null)
			return true;

		// �ݹ������������в���
		return (n.left != null && valueSearchNull(n.left))
				|| (n.right != null && valueSearchNull(n.right));
	}

	/*
	 * ����ָ���ڵ��valueֵ��Ľڵ㣬��Ϊ���ǰ��ڵ�key�ؼ���������ģ������ǰ�value����ģ�
	 * ��������valueʱ���������±���������
	 *
	 * �Զ������������������
	 */
	private boolean valueSearchNonNull(Entry n, Object value) {
		// �ȱȽϸ�
		if (value.equals(n.value))
			return true;

		// �ٱȽ�������ٱȽ���
		return (n.left != null && valueSearchNonNull(n.left, value))
				|| (n.right != null && valueSearchNonNull(n.right, value));
	}

	//��ȡָ��key��ֵ
	public Object get(Object key) {
		Entry p = getEntry(key);
		return (p == null ? null : p.value);
	}

	//�������е�һ������С�ģ���������ߵĽڵ�
	public Object firstKey() {
		return key(firstEntry());
	}

	/**
	 * Returns the last (highest) key currently in this sorted map.
	 *
	 * @return the last (highest) key currently in this sorted map.
	 * @throws    NoSuchElementException Map is empty.
	 */
	public Object lastKey() {
		return key(lastEntry());
	}

	/*
	 * ���ݸ�����key���ҽڵ㣬��Ϊ RED-BLACK��Ҳ��һ�ֶ��������������Բ����ڶ���������
	 * �в��ҽڵ���㷨������
	 */
	private Entry getEntry(Object key) {
		Entry p = root;
		while (p != null) {
			int cmp = compare(key, p.key);
			if (cmp == 0)
				return p;//�ҵ��򷵻�
			else if (cmp < 0)
				p = p.left;//����ؼ���С�ڵ�ǰ�ڵ㣬���ڵ�ǰ�ڵ������������
			else
				p = p.right;//����ؼ��ִ��ڵ�ǰ�ڵ㣬���ڵ�ǰ�ڵ������������
		}
		return null;
	}

	private static Object key(Entry e) {
		if (e == null)
			throw new NoSuchElementException();
		return e.key;
	}

	/**
	 * ���key�Ѵ��ڣ����滻ԭֵ��������ԭ��key����Ӧ��ֵ����������ڣ��򷵻� null��ע�����
	 * ����key�Ѵ��ڣ���Ϊnull������ʱҲ��Ϊnull��valueΪnull��������key�����ڣ�
	 */
	public Object put(Object key, Object value) {
		//������ڵ�ʱ����Ҫ������ɫ
		Entry t = root;

		if (t == null) {
			incrementSize();
			root = new Entry(key, value, null);
			return null;
		}

		while (true) {
			int cmp = compare(key, t.key);
			if (cmp == 0) {
				return t.setValue(value);
			} else if (cmp < 0) {
				if (t.left != null) {
					t = t.left;
				} else {
					incrementSize();
					t.left = new Entry(key, value, t);
					//��Ҫ������ɫ
					fixAfterInsertion(t.left);
					return null;
				}
			} else { // cmp > 0
				if (t.right != null) {
					t = t.right;
				} else {
					incrementSize();
					t.right = new Entry(key, value, t);
					//��Ҫ������ɫ
					fixAfterInsertion(t.right);
					return null;
				}
			}
		}
	}

	//��map��ɾ��ָ����key�ڵ㣬������value����������ڻ�value�������nullʱ������null
	public Object remove(Object key) {
		Entry p = getEntry(key);
		if (p == null)
			return null;

		Object oldValue = p.value;
		deleteEntry(p);
		return oldValue;
	}

	public void clear() {
		modCount++;
		size = 0;
		root = null;
	}

	public Object clone() {
		TreeMap clone = null;
		try {
			//��������Ŀ�¡����
			clone = (TreeMap) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}

		// Put clone into "virgin" state (except for comparator)
		clone.root = null;
		clone.size = 0;
		clone.modCount = 0;
		clone.entrySet = null;
		//...
		return clone;
	}

	// Views

	/**
	 * key-value(��-ֵ��)��ͼ������set��ͼ����һ������Entryʵ��ڵ�
	 */
	private transient volatile Set entrySet = null;

	//���������ֶ��ǴӸ���AbstractMap���������ģ���Ϊ�ڸ����븸�಻��һ�����У����Բ��ɼ�
	transient volatile Set keySet = null;//key��ͼ��set��ͼ����һ����Entryʵ��ڵ��key
	//value��ͼ��collection��ͼ����һ����Entryʵ��ڵ��value
	transient volatile Collection values = null;

	public Set keySet() {
		if (keySet == null) {
			//keySet��ͼʵ��
			keySet = new AbstractSet() {
				public Iterator iterator() {
					//ֻ�ܶ�key���е���
					return new KeyIterator();
				}

				public int size() {
					return TreeMap.this.size();
				}
				//...
			};
		}
		return keySet;
	}

	//value��ͼ�е�value���е�˳�򱣳ֲ��䣬ʵ���ϻ�����entrySet��ͼ�ϵ�����
	public Collection values() {
		if (values == null) {
			//value��ͼʵ��
			values = new AbstractCollection() {
				public Iterator iterator() {
					//ֻ�ܵ���value
					return new ValueIterator();
				}

				public int size() {
					return TreeMap.this.size();
				}
				//...
			};
		}
		return values;
	}

	//key-value ��ͼ����Entry�ڵ���ͼ
	public Set entrySet() {
		if (entrySet == null) {
			entrySet = new AbstractSet() {
				public Iterator iterator() {
					//��Entry�ڵ����
					return new EntryIterator();
				}

				//...
				public int size() {
					return TreeMap.this.size();
				}

			};
		}
		return entrySet;
	}

	//Entry������
	private class EntryIterator implements Iterator {
		private int expectedModCount = TreeMap.this.modCount;
		private Entry lastReturned = null;//ָ�����һ��next���������صĽڵ�
		Entry next;//ָ��ǰnext()����Ҫ���صĽڵ�

		EntryIterator() {
			next = firstEntry();//��ʼʱnextָ������ߵĽڵ㣬��������������еĵ�һ���ڵ�
		}

		// Used by SubMapEntryIterator
		EntryIterator(Entry first) {
			next = first;
		}

		public boolean hasNext() {
			//���nextû��ָ��null�����ʾ��ǰnextָ��Ľڵ㻹��
			return next != null;
		}

		//KeyIterator��ValueIterator����������next()����ʵ�ֶ��ǵ����˷�����ʵ�ֵ�
		final Entry nextEntry() {
			if (next == null)
				throw new NoSuchElementException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			lastReturned = next;//��ȡ
			next = successor(next);//next������
			return lastReturned;
		}

		public Object next() {
			//��Entry���е���
			return nextEntry();
		}

		public void remove() {
			if (lastReturned == null)
				throw new IllegalStateException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			//���ɾ���Ľڵ��������������ڣ���ɾ����nextָ������˵�lastReturned��λ�ã�
			//����Ϊʲô����ο�������������ʵ�� BinSearchTree.java ��Ӧ����
			if (lastReturned.left != null && lastReturned.right != null)
				next = lastReturned;
			deleteEntry(lastReturned);
			expectedModCount++;
			lastReturned = null;
		}
	}

	//KeyIterator����EntryIterator�Ļ�����ֻ��key���е�������
	private class KeyIterator extends EntryIterator {
		public Object next() {
			return nextEntry().key;
		}
	}

	//ValueIterator����EntryIterator�Ļ�����ֻ��value���е�������
	private class ValueIterator extends EntryIterator {
		public Object next() {
			return nextEntry().value;
		}
	}

	//�����������ʹ�� Comparator �� Comparable �Ƚ���
	private int compare(Object k1, Object k2) {
		return (comparator == null ? ((Comparable) k1).compareTo(k2) : comparator
				.compare(k1, k2));
	}

	private static boolean valEquals(Object o1, Object o2) {
		return (o1 == null ? o2 == null : o1.equals(o2));
	}

	private static final boolean RED = false;//false�����
	private static final boolean BLACK = true;//true�����

	/**
	 * ���еĽڵ�ṹ��ʵ�֣�ʵ����Map.Entry�ӿڣ�����˫��ģ������Դ����ҵ�����Ҳ�ɴ�
	 * �����ӽڵ�
	 */

	static class Entry implements Map.Entry {
		Object key;//������key
		Object value;//������value
		Entry left = null;//��ָ��
		Entry right = null;//��ָ��
		Entry parent;//��ָ��
		boolean color = BLACK;//�ڵ���ɫ���൱��ƽ������������е�ƽ������

		Entry(Object key, Object value, Entry parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		public Object getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

		public Object setValue(Object value) {
			Object oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		//...
		public String toString() {
			return key + "=" + value;
		}
	}

	//ȡ��������ߵĽڵ㣬��key��С�Ľڵ�
	private Entry firstEntry() {
		Entry p = root;
		if (p != null)
			while (p.left != null)
				p = p.left;
		return p;
	}

	//ȡ�������ұߵĽڵ㣬��key���Ľڵ�
	private Entry lastEntry() {
		Entry p = root;
		if (p != null)
			while (p.right != null)
				p = p.right;
		return p;
	}

	//����ĳ�ڵ�����������ֱ�Ӻ�̽ڵ㣬������μ�BinSearchTree.java ��Ӧ����
	private Entry successor(Entry t) {
		if (t == null)
			return null;
		else if (t.right != null) {
			Entry p = t.right;
			while (p.left != null)
				p = p.left;
			return p;
		} else {
			Entry p = t.parent;
			Entry ch = t;
			while (p != null && ch == p.right) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
	}

	//ƽ�����
	private static boolean colorOf(Entry p) {
		//ע�����Ϊnull���򷵻�Ϊ��ɫ���������ĳ���ڵ���ӽڵ���ɫ�����ӽڵ㲻����ʱҲ�᷵�غ�ɫ
		return (p == null ? BLACK : p.color);
	}

	private static Entry parentOf(Entry p) {
		return (p == null ? null : p.parent);
	}

	private static void setColor(Entry p, boolean c) {
		if (p != null)
			p.color = c;
	}

	private static Entry leftOf(Entry p) {
		return (p == null) ? null : p.left;
	}

	private static Entry rightOf(Entry p) {
		return (p == null) ? null : p.right;
	}

	//������������μ�AVLTree.java��Ӧ����
	private void rotateLeft(Entry p) {
		Entry r = p.right;
		p.right = r.left;
		if (r.left != null)
			r.left.parent = p;
		r.parent = p.parent;
		if (p.parent == null)
			root = r;
		else if (p.parent.left == p)
			p.parent.left = r;
		else
			p.parent.right = r;
		r.left = p;
		p.parent = r;
	}

	//������������μ�AVLTree.java��Ӧ����
	private void rotateRight(Entry p) {
		Entry l = p.left;
		p.left = l.right;
		if (l.right != null)
			l.right.parent = p;
		l.parent = p.parent;
		if (p.parent == null)
			root = l;
		else if (p.parent.right == p)
			p.parent.right = l;
		else
			p.parent.left = l;
		l.right = p;
		p.parent = l;
	}

	/** From CLR **/
	private void fixAfterInsertion(Entry x) {
		//����Ľڵ�Ϊ��ɫ
		x.color = RED;

		/*
		 * ��ӽڵ�ʱ����Ϊ�����潫�����ڵ����ó��˺�ɫ��������Ӻ��ǲ������ Path���� �ģ�����
		 * �п��ܻ���� Red�����ִ��� Red���� ��ǰ���Ǹ��ڵ��Ǻ�ɫ�Ĳſ��ܣ����������ѭ����
		 * �����Ǹ��ڵ�����Ǻ�ɫ�Ĳ������
		 *
		 * 1�����xΪ��������Ҫ����Ⱦɫ����ת����Ϊ���Ὣ���ٴ�Ⱦ��BLACK
		 * 2�����x�ĸ��ڵ�colorΪ��ɫ��Ҳû��Ҫ���������ˣ���Ϊred����û�б��ƻ�
		 * ��������������������㣬��һֱѭ��������x != null ����Ҳ�ǲ����ٵģ����ݽ�����ֵ
		 * ��ѭ����ֹʱ���ܿ���Ϊnull
		 */
		while (x != null && x != root && x.parent.color == RED) {

			//һ��x�ĸ��ڵ㴦�����ӽڵ�ʱ
			if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {

				//yָ��x�����壬ע���п���ΪNULL
				Entry y = rightOf(parentOf(parentOf(x)));
				//���yΪ��ɫ
				if (colorOf(y) == RED) {//���������������y���ܿ�Ϊ��
					/*
					 * ���1��colorOf(y) == RED
					 *
					 * �������y������Ϊnull����Ϊ colorOf(null) == BLACK�����Դ����
					 * ������һ�����ڣ���Ȼ�����Ǻ�ɫ������үү�ڵ�50ֻ���Ǻ�ɫ�ˣ��������
					 * ��Red�����ˡ�30Ҳ�϶��Ǻ�ɫ�ˣ���Ϊ����whileѭ��ǰ���������Ǹ��ڵ�
					 * һ���Ǻ�ɫ��
					 *                         ��ɫ
					 *           .              ��              .
					 *           .                             .
					 *           50B                      x �� 50R
					 *           /\                            /\
					 *        30R  90R �� y(����)             30B  90B �� y
					 *           \                            \
					 *      x �� 40R                          40R
					 *
					 * ������²���Ҫ��ת�������ɫ�����ѭ��
					 */

					//��x�ڵ�ĸ��ڵ���ź�
					setColor(parentOf(x), BLACK);
					//��x��������ź�
					setColor(y, BLACK);
					//��x��үү�ź�
					setColor(parentOf(parentOf(x)), RED);

					/*
					 * ��ɲ��������Ƿ���x�����ƶ������㣬���ѭ���������������ĸ߶ȵ�һ
					 * �룬��Ҳ����TreeMap�е�put���������ִ��ʱ��Ϊ O(logn) ԭ��������
					 */
					x = parentOf(parentOf(x));//�ٽ�xָ��үү
				}//���yΪ��ɫ
				else {

					if (x == rightOf(parentOf(x))) {
						/*
						* ���2��colorOf(y) == BLACK����xΪ�������ӽڵ�
						*
						* ������£�yһ����null��Ϊʲô����Ϊ����Ľڵ�40�ĸ��ڵ�30Ϊ��ɫ
						* ����Ϊʲô����Ϊ����whileѭ����ǰ����Ǹ�Ϊ��ɫ����������30�ĸ�
						* �ڵ�ֻ��Ϊ��ɫ�ˣ���Ϊʲô����Ϊ����Ǻ�ɫ�Ļ�����ʹ�����Red����
						* �ˣ����ֽ��뵽�˷�֧ʱǰ����y�����Ǻ�ɫ�ģ����y��Ĵ��ڣ���Path
						* �����ֽ������ƣ����Դ�����µ�yһ����null������������Path����
						*
						* ====ʵ���ϴ���������ǽ�����2ת����ת��������3������====
						*
						*                   ��30����
						*        .            ��             .
						*        .                          .
						*       50B                        50B
						*        /                          /
						*      30R  (YΪNULL)             40R
						*        \                         /
						*   x �� 40R                   x �� 30R
						*/
						x = parentOf(x);
						rotateLeft(x);
					}

					/*
					 * ���3��colorOf(y) == BLACK����xΪ�������ӽڵ�
					 *
					 * ���xΪ�������ӽڵ㣬�����������2�Ĵ����Ѿ�����ת�������3�ˣ�����
					 * ����µ�yҲһ��Ϊnull�����2�����������һ����
					 *
					 *                    ��ɫ               ��50����
					 *        .            ��         .       ��       .
					 *        .                      .               .
					 *       50B                    50R  ��������      40B
					 *        /                      /               /\
					 *      40R  (YΪNULL)          40B        x �� 30R 50R
					 *       /                      /
					 * x �� 30R                 x �� 30R
					 *
					 * ������´�ѭ���Զ�����ֹ����Ϊ����������x�ĸ��ڵ����Ǻ�ɫ�ģ�����
					 * ѭ��һ������ֹ���´�
					 */
					setColor(parentOf(x), BLACK);//x�ĸ��ź�
					setColor(parentOf(parentOf(x)), RED);//x��ү�ź�
					if (parentOf(parentOf(x)) != null)
						rotateRight(parentOf(parentOf(x)));//��үү����
				}
			}//����x�ĸ��ڵ㴦�����ӽڵ�ʱ�����һ�ֶԳ�
			else {
				//x������
				Entry y = leftOf(parentOf(parentOf(x)));
				//�������Ϊ��ɫ
				if (colorOf(y) == RED) {
					/*
					* ���1��colorOf(y) == RED,������Ϊ��ɫ
					*
					* �������y������Ϊnull����Ϊ colorOf(null) == BLACK�����Դ����
					* ������һ�����ڣ���Ȼ�����Ǻ�ɫ������үү�ڵ�50ֻ���Ǻ�ɫ�ˣ��������
					* ��Red�����ˡ�90Ҳ�϶��Ǻ�ɫ�ˣ���Ϊ����whileѭ��ǰ���������Ǹ��ڵ�
					* һ���Ǻ�ɫ��
					*                         ��ɫ
					*           .              ��              .
					*           .                             .
					*           50B                      x �� 50R
					*           /\                            /\
					* y(����)��30R  90R                      30B 90B �� y
					*              \                             \
					*          x �� 100R                          100R
					*
					* ������²���Ҫ��ת�������ɫ�����ѭ��
					*/
					setColor(parentOf(x), BLACK);
					setColor(y, BLACK);
					setColor(parentOf(parentOf(x)), RED);
					/*
					 * ��ɲ��������Ƿ���x�����ƶ������㣬���ѭ���������������ĸ߶ȵ�һ
					 * �룬��Ҳ����TreeMap�е�put���������ִ��ʱ��Ϊ O(logn) ԭ��������
					 */
					x = parentOf(parentOf(x));//�ٽ�xָ��үү
				}//���yΪ��ɫ
				else {
					if (x == leftOf(parentOf(x))) {
						/*
						* ���2��colorOf(y) == BLACK������Ϊ�ڣ�����xΪ�������ӽڵ�
						*
						* ������£�yһ����null��Ϊʲô����Ϊ����Ľڵ�60�ĸ��ڵ�90Ϊ��ɫ
						* ������90�ĸ��ڵ�ֻ��Ϊ��ɫ�ˣ��ֽ��뵽�˷�֧ʱǰ����y�����Ǻ�ɫ��
						* �����y��Ĵ��ڣ���Path�����ֽ������ƣ����Դ�����µ�yһ����null
						* ������������Path����
						*
						* ====ʵ���ϴ���������ǽ�����2ת����ת��������3������====
						*
						*                   ��90����
						*          .            ��             .
						*          .                          .
						*          50B                        50B
						*           \                          \
						*(YΪNULL)  90R                        60R
						*           /                           \
						*      x �� 60R                      x �� 90R
						*/
						x = parentOf(x);
						rotateRight(x);
					}

					/*
					 * ���3��colorOf(y) == BLACK����xΪ�������ӽڵ�
					 *
					 * ���xΪ�������ӽڵ㣬�����������2�Ĵ����Ѿ�����ת�������3�ˣ�����
					 * ����µ�yҲһ��Ϊnull�����2�����������һ����
					 *
					 *                    ��ɫ               ��50����
					 *         .            ��         .       ��       .
					 *         .                      .               .
					 *        50B                    50R  ��������      90B
					 *         \                      \               /\
					 *(YΪNULL) 90R                   90B       x �� 50R 95R
					 *           \                     \
					 *       x �� 95R               x �� 95R
					 *
					 * ������´�ѭ���Զ�����ֹ����Ϊ����������x�ĸ��ڵ����Ǻ�ɫ�ģ�����
					 * ѭ��һ������ֹ���´�
					 */
					setColor(parentOf(x), BLACK);//x�ĸ��ź�
					setColor(parentOf(parentOf(x)), RED);//x��ү�ź�
					if (parentOf(parentOf(x)) != null)
						rotateLeft(parentOf(parentOf(x)));//��үү����
				}
			}
		}
		root.color = BLACK;//�������������������ڵ㶼Ҫ�ź�
	}

	//ɾ���ڵ�p��Ȼ�����µ���
	private void deleteEntry(Entry p) {
		decrementSize();

		/*
		* ���ɾ���Ľڵ�p����������ʱ��������ת����ɾ��Ҷ�ӽڵ��ֻ��һ���ӽڵ�Ľڵ����⣬����
		* ���̣�ʹ���������ֱ�Ӻ�s�̵�������ֵ��key��value��������p����Ӧ������ֵ��Ȼ��pָ
		* ��ֱ�Ӻ��s��������ɾ���Ľڵ�ͱ����s��������ת����ɾ��Ҷ�ڵ��ֻ��һ���ӽڵ��������
		*/
		if (p.left != null && p.right != null) {
			Entry s = successor(p);
			p.key = s.key;
			p.value = s.value;
			p = s;
		}

		//replacement�����滻������ɾ���ڵ�p
		Entry replacement = (p.left != null ? p.left : p.right);

		//�����ɾ����Ԫ�����ӽڵ㣨����У���Ҳ���ҽ���һ���ӽڵ㣩
		if (replacement != null) {
			// ���������滻�ڵ�ĸ�
			replacement.parent = p.parent;
			//�����ɾ���ڵ��Ϊ���ڵ�
			if (p.parent == null)
				root = replacement;//���滻Ԫ�ؽ���Ϊ��
			else if (p == p.parent.left)
				//���������ɾ���ڵ�Ϊ��ڵ㣬��ʹ��ɾ���ڵ�ĸ�����ָ��ָ���滻�ڵ�
				p.parent.left = replacement;
			else
				//���������ɾ���ڵ�Ϊ�ҽڵ㣬��ʹ��ɾ���ڵ�ĸ�����ָ��ָ���滻�ڵ�
				p.parent.right = replacement;

			// ���p�������ڵ�Ĺ�ϵ
			p.left = p.right = p.parent = null;

			/*
			 * ��Ϊ��ɾ���Ľڵ�ֻ�����ֽڵ㣺 Ҷ�ӽڵ㡢ֻ��һ���ӽڵ�Ľڵ�
			 *
			 * ���ɾ������ֻ��һ���ӽڵ�Ľڵ�ʱ������·�����򣬱�ɾ���ڵ�һ���Ǻ�ɫ�ģ����ӽڵ�
			 * һ���Ǻ�ɫ�ģ���ʱɾ���������̾���ɾ����һ���ں�ڵ����ɾ��λ�����ַ�����һ����ɫ
			 * �ڵ㣬��ˣ�ɾ��һ����һ���ӽڵ�Ľڵ�ʱ��Red��Path�����п��ܴ���
			 */

			if (p.color == BLACK)//???��������������Ƕ���ģ���Ϊpһ���Ǻڣ��ѵ���������?
				//ɾ������滻�ڵ�replacementλ������������
				fixAfterDeletion(replacement);
		} else if (p.parent == null) { // �����ɾ���ڵ�Ϊ��������rootΪnull
			root = null;
		} else { // ���ɾ������Ҷ�ӽڵ�

			/*
			 * ���ɾ������Ҷ�ӽڵ㣬���ܸ�Ҷ�ӽڵ��Ǻ컹�Ǻڣ��� Red���� ��ɾ���󲻿��ܱ����ƣ�
			 * ���Դ������ֻ�п��ܴ��� Path���� �������Ƶ�ǰ���Ǳ�ɾ���Ľڵ��Ǻ�ɫ�ģ���Ϊֻ��
			 * ��ɫ�ڵ��Ӱ��Path·���������Ե�����ǰ���� p.color == BLACK������ɾ����Ҷ
			 * �ڵ��Ǻ�ɫ��
			 */
			if (p.color == BLACK)
				//ɾ��ǰ�Ӽ�����ɾ���ڵ�pλ������������
				fixAfterDeletion(p);

			//???������������࣬��Ϊ��������������������Ǹ���֧�ˣ��ѵ��������
			if (p.parent != null) {
				if (p == p.parent.left)
					p.parent.left = null;
				else if (p == p.parent.right)
					p.parent.right = null;
				p.parent = null;//���p�������ڵ�Ĺ�ϵ
			}
		}
	}

	//ɾ���ڵ�����
	private void fixAfterDeletion(Entry x) {

		//ֱѭ�������ڵ���ɫֹ
		while (x != root && colorOf(x) == BLACK) {
			//xΪ���ӽڵ�
			if (x == leftOf(parentOf(x))) {
				//x��ͬ��
				Entry sib = rightOf(parentOf(x));//�п���Ϊnull

				if (colorOf(sib) == RED) {//ͬ��������Ϊnull
					/*
					 * ���1��colorOf(sib) == RED�������������Ϊ��
					 *              ͨ��ִ������deleteEntry��x��p��ָ����65
					 *        p �� 60B             ��           65B
					 *            /  \                        /  \
					 *          50B   70B                   50B   70B
					 *          /\    /  \                  /\    /  \
					 *       45B 55B 65B 80R             45B 55B 65B 80R �� sib
					 *                   / \                     ��   /  \
					 *                 75B 87B                 x��p 75B 87B
					 *  ��ɫ                         ��70����
					 *   ��        65B               ��           65B
					 *            /  \                          /  \
					 *          50B   70R                     50B   80B
					 *          /\    /  \                    /\    / \
					 *       45B 55B 65B 80B �� sib         45B 55B 70R 87B
					 *                ��   / \                      /  \
					 *              x��p 75B 87B            x��p �� 65B 75B �� sib
					 *  x����һ���µ�ͬ���ڵ㣬�ýڵ�Ϊ�ڣ��ֽ����1ת������3�����
					 */

					setColor(sib, BLACK);//��ͬ��Ϊ��
					setColor(parentOf(x), RED);//�ø�Ϊ��
					rotateLeft(parentOf(x));//����
					sib = rightOf(parentOf(x));//�ٴ���ͬ���ڵ�
				}

				if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
					/*
					* ���2��
					* colorOf(leftOf(sib))==BLACK && colorOf(rightOf(sib))==BLACK
					* ��ͬ�������ڻ�ͬ������ʱΪ����û���ӽڵ�����
					*
					* �ֽ����������1��������ǿ�������
					* ��ɫ                       �ٽ�xָ��(70R)��������
					*  ��      65B                   ��           ...
					*         /  \
					*       50B   80B
					*       /\     / \
					*     45B 55B 70R 87B
					*             / \
					*     x��p �� 65B 75R �� sib
					*
					* �������xΪ�죬��˽���ѭ����Ȼ������x����ɫΪ�ڣ����Ӵ˷���
					* deleteEntry��������ʱp��ָ��Ľڵ��ѱ����丸�ڵ�������
					*/
					setColor(sib, RED);
					x = parentOf(x);
				} else {
					if (colorOf(rightOf(sib)) == BLACK) {
						/*
						* ���3���������2�󣬴�ʱͬ���ڵ����ӽڵ��Ϊ��
						*
						* �����ڿ�ʼwhileѭ��֮ǰ����������
						*  ��      65B
						*         /  \
						*       50B   80R
						*      / \    /  \
						*    45B 55B 70B  90B
						*            / \   / \
						*     x �� 65B 75B 85R 95B
						*             �J  / \
						*          sib  82B 87B
						*
						* ���2��Ӧ�ã���˽�75����ɫ����Ϊ�죬���� x = parentOf(x)���
						* һ��ѭ���������ڶ���ѭ����ʼʱ����Ӧ�����3����ʱͬ���ڵ�Ϊ90����
						* ����ͼ��
						* ��һ��ѭ����                  ��ɫ
						*    ��    65B                ��          65B
						*        /   \                          /  \
						*      50B    80R                    50B    80R
						*     / \     /  \                   / \    /  \
						*   45B 55B 70B   90B �� sib       45B 55B 70B   90R �� sib
						*         �J/ \    / \                  �J/ \     / \
						*       x 65B 75R 85R 95B             x 65B 75R 85B 95B
						*                 / \                           / \
						*               82B 87B                       82B 87B
						*
						* ��90�������޸�sibָ��           ������һ��ѭ����Ӧ�����4
						*    ��          65B                      �� ...
						*              /   \
						*            50B    80R
						*           / \     /  \
						*         45B 55B 70B   85B �� sib
						*               �J/ \    / \
						*             x 65B 75R 82B 90R
						*                           / \
						*                         87B 95B
						*
						* һ�����3��Ӧ�ã���ͬ���ڵ�����ӽڵ㽫����Ϊ RED��������4�����
						* 3֮��Ӧ�ã�Ϊ�˲�����һ��ѭ����Ӧ�����4��ֱ�ӽ����4�Ĵ��������
						* ���3�ĺ���
						*/
						setColor(leftOf(sib), BLACK);
						setColor(sib, RED);
						rotateRight(sib);
						sib = rightOf(parentOf(x));
					}
					/*
					 * ���4:�������3��ʣ�µ����4����ʱͬ���ڵ�����ӽڵ��Ǻ�
					 *
					 * ���ŵ�3���������
					 *
					 *   ��ɫ                    ��80����
					 *    ��       65B            ��              65B
					 *           /   \                          /  \
					 *         50B    80B                     50B   85R �� sib
					 *        / \     /  \                   / \    /  \
					 *      45B 55B 70B   85R �� sib        45B 55B 80B 90B
					 *            �J/ \    / \                     / \   \
					 *          x 65B 75R 82B 90B            x �� 70B 87B 95B
					 *                        / \                 /
					 *                      87B 95B         p �� 65B
					 *
					 * ѭ�������󷵻ص� deleteEntry ������ɾ��65�ڵ㼴���ɾ����������
					 */
					setColor(sib, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(rightOf(sib), BLACK);
					rotateLeft(parentOf(x));

					//���3��4��Ӧ�ú�x��������Ϊ���ڵ㣬ͬʱ����Ҳ��ѭ�������һ��
					x = root;
				}
			} else { // ������Գ�
				Entry sib = leftOf(parentOf(x));

				if (colorOf(sib) == RED) {
					setColor(sib, BLACK);
					setColor(parentOf(x), RED);
					rotateRight(parentOf(x));
					sib = leftOf(parentOf(x));
				}

				if (colorOf(rightOf(sib)) == BLACK && colorOf(leftOf(sib)) == BLACK) {
					setColor(sib, RED);
					x = parentOf(x);
				} else {
					if (colorOf(leftOf(sib)) == BLACK) {
						setColor(rightOf(sib), BLACK);
						setColor(sib, RED);
						rotateLeft(sib);
						sib = leftOf(parentOf(x));
					}
					setColor(sib, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(leftOf(sib), BLACK);
					rotateRight(parentOf(x));
					x = root;
				}
			}
		}
		//�˳�ѭ��ʱxΪ����Ϊ��ɫ��ֱ�ӽ���ֵΪ��ɫ����
		setColor(x, BLACK);
	}

	public Comparator comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	public SortedMap subMap(Object fromKey, Object toKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public SortedMap headMap(Object toKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public SortedMap tailMap(Object fromKey) {
		// TODO Auto-generated method stub
		return null;
	}

}