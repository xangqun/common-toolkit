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
 *由于平衡二叉树与红黑树都是二叉排序树，又红黑树是对平衡二叉树的一种改进实现，所以它的很多思想算法都来源于排序二叉或平衡二叉树，比如排序二叉树中的添加、删除、查找及查找直接后继节点等，平衡二叉树中的左旋与右旋等都是一样的，所以当看到这些方法时，要多参考以下两节：《二叉排序（搜索）树实现 》与《平衡二叉树实现 》

SortedMap 接口的基于红黑树的实现。此类保证了Map按照升序顺序排列关键字，根据使用的构造方法不同，可能会按照键的类的自然顺序进行排序(Comparable)，或者按照创建时所提供的比较(Comparator)进行排序。此实现不是同步的，多个线程同时访问一个同一个map时，一般通过对封装该map的对象进行同步操作来完成，或者使用  Map m = Collections.synchronizedMap(new TreeMap(...)) 方法来包装该map

RED-BLACK树的性质：
1、根元素永远是显色。
2、除根节点外，所有新插入的节点都是红色的。

3、Red规则：红色节点的子节点都是黑色的，不能有两个红色节点相邻。
4、Path规则：从根元素到某个子节点或是只有一个子节点的元素的所有路径中，黑色元素的个数必须相同。
 * @author xangqun
 *
 */
public class TreeMap extends AbstractMap implements SortedMap, Cloneable,
		java.io.Serializable {

	//比较器，用于TreeMap的key的排序，如果为null，则使用key的自然排序法
	private Comparator comparator = null;

	private transient Entry root = null;//根节点

	private transient int size = 0;//树中的节点数

	/*
	 * TreeMap结构被修改的次数，注：结构上修改是指添加或删除一个或多个映射关系的操作， 仅改变与
	 * 现有键关联的值不是结构上的修改。
	 *
	 * 由所有此类的 collection 视图方法（如keySet、values、entrySet三个方法） 所返回的迭代
	 * 器都是快速失效的：在迭代器创建之后，如果对map修改了结构，除非通过迭代器自身的 remove 或
	 *  add 方法，其他map自身任何时间任何方式的修改，迭代器都将抛出 ConcurrentModification
	 *  Exception。因此，面对并发的修改，迭代器很快就完全失效，而不是冒着在将来在不确定的时间点
	 *  上发生不确定问题的风险。
	 */
	private transient int modCount = 0;

	//增加节点后modCount与size都需递增
	private void incrementSize() {
		modCount++;
		size++;
	}

	private void decrementSize() {
		modCount++;
		size--;
	}

	/**
	 * 默认构造器，构造一个空的map，根据key的自然比较排序。所有插入到map中的节点的key必须实现
	 * 过自然比较器Comparable接口。
	 */
	public TreeMap() {
	}

	/**
	 * 构造一个新的空映射，该映射根据给定的比较器进行排序
	 */
	public TreeMap(Comparator c) {
		this.comparator = c;
	}

	/**
	 * 构造一个新map，所含的元素与给定的map相同，这个新map按照键的自然顺序进行关键字排序
	 */
	public TreeMap(Map m) {
		putAll(m);
	}

	/**
	 * 构造一个新的映射，所含的元素与给定的map相同，该映射按照SortedMap相同的排序方式进行排序
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

	//树中是否有value域值为null的节点
	private boolean valueSearchNull(Entry n) {
		if (n.value == null)
			return true;

		// 递归在左右子树中查找
		return (n.left != null && valueSearchNull(n.left))
				|| (n.right != null && valueSearchNull(n.right));
	}

	/*
	 * 查找指定节点的value值域的节点，因为树是按节的key关键字来排序的，而不是按value排序的，
	 * 所以在找value时在最坏的情况下遍历整个树
	 *
	 * 以二叉树的先序遍历查找
	 */
	private boolean valueSearchNonNull(Entry n, Object value) {
		// 先比较根
		if (value.equals(n.value))
			return true;

		// 再比较左，最后再比较右
		return (n.left != null && valueSearchNonNull(n.left, value))
				|| (n.right != null && valueSearchNonNull(n.right, value));
	}

	//获取指定key的值
	public Object get(Object key) {
		Entry p = getEntry(key);
		return (p == null ? null : p.value);
	}

	//返回树中第一个（最小的）键，最左边的节点
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
	 * 根据给定的key查找节点，因为 RED-BLACK树也是一种二叉排序树，所以采用在二叉排序树
	 * 中查找节点的算法来查找
	 */
	private Entry getEntry(Object key) {
		Entry p = root;
		while (p != null) {
			int cmp = compare(key, p.key);
			if (cmp == 0)
				return p;//找到则返回
			else if (cmp < 0)
				p = p.left;//如果关键字小于当前节点，则在当前节点的左子树中找
			else
				p = p.right;//如果关键字大于当前节点，则在当前节点的右子树中找
		}
		return null;
	}

	private static Object key(Entry e) {
		if (e == null)
			throw new NoSuchElementException();
		return e.key;
	}

	/**
	 * 如果key已存在，将替换原值，并返回原来key所对应的值；如果不存在，则返回 null（注，如果
	 * 新增key已存在，且为null，返回时也会为null，value为null并不代表key不存在）
	 */
	public Object put(Object key, Object value) {
		//插入根节点时不需要调整颜色
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
					//需要调整颜色
					fixAfterInsertion(t.left);
					return null;
				}
			} else { // cmp > 0
				if (t.right != null) {
					t = t.right;
				} else {
					incrementSize();
					t.right = new Entry(key, value, t);
					//需要调整颜色
					fixAfterInsertion(t.right);
					return null;
				}
			}
		}
	}

	//从map中删除指定的key节点，并返回value，如果不存在或value本身就是null时，返回null
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
			//调整父类的克隆方法
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
	 * key-value(键-值对)视图，即该set视图里是一个个的Entry实体节点
	 */
	private transient volatile Set entrySet = null;

	//以下两个字段是从父类AbstractMap拷贝过来的，因为在该类与父类不在一个包中，所以不可见
	transient volatile Set keySet = null;//key视图，set视图里是一个个Entry实体节点的key
	//value视图，collection视图里是一个个Entry实体节点的value
	transient volatile Collection values = null;

	public Set keySet() {
		if (keySet == null) {
			//keySet视图实现
			keySet = new AbstractSet() {
				public Iterator iterator() {
					//只能对key进行迭代
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

	//value视图中的value排列的顺序保持不变，实质上还是在entrySet视图上迭代的
	public Collection values() {
		if (values == null) {
			//value视图实现
			values = new AbstractCollection() {
				public Iterator iterator() {
					//只能迭代value
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

	//key-value 视图，即Entry节点视图
	public Set entrySet() {
		if (entrySet == null) {
			entrySet = new AbstractSet() {
				public Iterator iterator() {
					//对Entry节点迭代
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

	//Entry迭代器
	private class EntryIterator implements Iterator {
		private int expectedModCount = TreeMap.this.modCount;
		private Entry lastReturned = null;//指向最后一次next操作所返回的节点
		Entry next;//指向当前next()操作要返回的节点

		EntryIterator() {
			next = firstEntry();//开始时next指向最左边的节点，即中序遍历序列中的第一个节点
		}

		// Used by SubMapEntryIterator
		EntryIterator(Entry first) {
			next = first;
		}

		public boolean hasNext() {
			//如果next没有指向null，则表示当前next指向的节点还有
			return next != null;
		}

		//KeyIterator与ValueIterator两迭代器的next()方法实现都是调整此方法来实现的
		final Entry nextEntry() {
			if (next == null)
				throw new NoSuchElementException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			lastReturned = next;//先取
			next = successor(next);//next再下移
			return lastReturned;
		}

		public Object next() {
			//对Entry进行迭代
			return nextEntry();
		}

		public void remove() {
			if (lastReturned == null)
				throw new IllegalStateException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			//如果删除的节点左右子树都存在，则删除后next指针需后退到lastReturned的位置，
			//具体为什么，请参考二叉搜索树的实现 BinSearchTree.java 相应方法
			if (lastReturned.left != null && lastReturned.right != null)
				next = lastReturned;
			deleteEntry(lastReturned);
			expectedModCount++;
			lastReturned = null;
		}
	}

	//KeyIterator是在EntryIterator的基础上只对key进行迭代罢了
	private class KeyIterator extends EntryIterator {
		public Object next() {
			return nextEntry().key;
		}
	}

	//ValueIterator是在EntryIterator的基础上只对value进行迭代罢了
	private class ValueIterator extends EntryIterator {
		public Object next() {
			return nextEntry().value;
		}
	}

	//在这里决定是使用 Comparator 还 Comparable 比较器
	private int compare(Object k1, Object k2) {
		return (comparator == null ? ((Comparable) k1).compareTo(k2) : comparator
				.compare(k1, k2));
	}

	private static boolean valEquals(Object o1, Object o2) {
		return (o1 == null ? o2 == null : o1.equals(o2));
	}

	private static final boolean RED = false;//false代表红
	private static final boolean BLACK = true;//true代表黑

	/**
	 * 树中的节点结构体实现，实现了Map.Entry接口，它是双向的，即可以从子找到父，也可从
	 * 父到子节点
	 */

	static class Entry implements Map.Entry {
		Object key;//数据域key
		Object value;//数据域value
		Entry left = null;//左指针
		Entry right = null;//右指针
		Entry parent;//父指针
		boolean color = BLACK;//节点着色，相当于平衡二叉排序树中的平衡因子

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

	//取树中最左边的节点，即key最小的节点
	private Entry firstEntry() {
		Entry p = root;
		if (p != null)
			while (p.left != null)
				p = p.left;
		return p;
	}

	//取树中最右边的节点，即key最大的节点
	private Entry lastEntry() {
		Entry p = root;
		if (p != null)
			while (p.right != null)
				p = p.right;
		return p;
	}

	//查找某节点的中序遍历的直接后继节点，具体请参见BinSearchTree.java 相应方法
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

	//平衡操作
	private static boolean colorOf(Entry p) {
		//注，如果为null，则返回为黑色，即如果求某个节点的子节点颜色，但子节点不存在时也会返回黑色
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

	//左旋，具体请参见AVLTree.java相应方法
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

	//右旋，具体请参见AVLTree.java相应方法
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
		//插入的节点为红色
		x.color = RED;

		/*
		 * 添加节点时，因为在上面将新增节点设置成了红色，所以添加后是不会打破 Path规则 的，但是
		 * 有可能会打破 Red规则，又打破 Red规则 的前提是父节点是红色的才可能，所以这里的循环条
		 * 件就是父节点必须是红色的才需调整
		 *
		 * 1、如果x为根，则不需要重新染色与旋转，因为最后会将根再次染成BLACK
		 * 2、如果x的父节点color为黑色，也没必要进行重排了，因为red规则没有被破坏
		 * 如果上面两个条件不满足，则一直循环，这里x != null 条件也是不可少的，传递进来的值
		 * 与循环终止时都能可能为null
		 */
		while (x != null && x != root && x.parent.color == RED) {

			//一、x的父节点处于左子节点时
			if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {

				//y指向x的右叔，注，有可能为NULL
				Entry y = rightOf(parentOf(parentOf(x)));
				//如果y为红色
				if (colorOf(y) == RED) {//如果条件成立，则y不能可为空
					/*
					 * 情况1：colorOf(y) == RED
					 *
					 * 此情况下y不可能为null，因为 colorOf(null) == BLACK，所以此情况
					 * 下右叔一定存在，既然右叔是红色，所以爷爷节点50只能是黑色了，否则打破
					 * 了Red规则了。30也肯定是红色了，因为进行while循环前提条件就是父节点
					 * 一定是红色的
					 *                         着色
					 *           .              →              .
					 *           .                             .
					 *           50B                      x → 50R
					 *           /\                            /\
					 *        30R  90R ← y(右叔)             30B  90B ← y
					 *           \                            \
					 *      x → 40R                          40R
					 *
					 * 此情况下不需要旋转，完成着色后继续循环
					 */

					//将x节点的父节点的着黑
					setColor(parentOf(x), BLACK);
					//将x的右叔的着黑
					setColor(y, BLACK);
					//将x的爷爷着红
					setColor(parentOf(parentOf(x)), RED);

					/*
					 * 完成操作后，我们发现x向上移动了两层，因此循环的最大次数是树的高度的一
					 * 半，这也就是TreeMap中的put方法的最大执行时间为 O(logn) 原因所在了
					 */
					x = parentOf(parentOf(x));//再将x指向爷爷
				}//如果y为黑色
				else {

					if (x == rightOf(parentOf(x))) {
						/*
						* 情况2：colorOf(y) == BLACK，且x为父的右子节点
						*
						* 此情况下，y一定是null，为什么？因为插入的节点40的父节点30为红色
						* （又为什么？因为进行while循环的前提就是父为红色啊），所以30的父
						* 节点只能为黑色了（又为什么？因为如果是红色的话，早就打破了Red规则
						* 了），现进入到此分支时前提是y必须是黑色的，如果y真的存在，则Path
						* 规则又将被打破，所以此情况下的y一定是null，这样才满足Path规则
						*
						* ====实质上此种情况就是将问题2转换成转换成问题3来操作====
						*
						*                   绕30左旋
						*        .            →             .
						*        .                          .
						*       50B                        50B
						*        /                          /
						*      30R  (Y为NULL)             40R
						*        \                         /
						*   x → 40R                   x → 30R
						*/
						x = parentOf(x);
						rotateLeft(x);
					}

					/*
					 * 情况3：colorOf(y) == BLACK，且x为父的左子节点
					 *
					 * 如果x为父的右子节点，经过上面情况2的处理，已经将其转换成情况3了，此种
					 * 情况下的y也一定为null，与第2种情况道理是一样的
					 *
					 *                    着色               绕50右旋
					 *        .            →         .       →       .
					 *        .                      .               .
					 *       50B                    50R  　　　　      40B
					 *        /                      /               /\
					 *      40R  (Y为NULL)          40B        x → 30R 50R
					 *       /                      /
					 * x → 30R                 x → 30R
					 *
					 * 处理后，下次循环自动会终止，因为此情况处理后x的父节点总是黑色的，所以
					 * 循环一定会终止于下次
					 */
					setColor(parentOf(x), BLACK);//x的父着黑
					setColor(parentOf(parentOf(x)), RED);//x的爷着红
					if (parentOf(parentOf(x)) != null)
						rotateRight(parentOf(parentOf(x)));//绕爷爷右旋
				}
			}//二、x的父节点处于右子节点时，与第一种对称
			else {
				//x的左叔
				Entry y = leftOf(parentOf(parentOf(x)));
				//如果左叔为红色
				if (colorOf(y) == RED) {
					/*
					* 情况1：colorOf(y) == RED,即左叔为红色
					*
					* 此情况下y不可能为null，因为 colorOf(null) == BLACK，所以此情况
					* 下左叔一定存在，既然左叔是红色，所以爷爷节点50只能是黑色了，否则打破
					* 了Red规则了。90也肯定是红色了，因为进行while循环前提条件就是父节点
					* 一定是红色的
					*                         着色
					*           .              →              .
					*           .                             .
					*           50B                      x → 50R
					*           /\                            /\
					* y(左叔)→30R  90R                      30B 90B ← y
					*              \                             \
					*          x → 100R                          100R
					*
					* 此情况下不需要旋转，完成着色后继续循环
					*/
					setColor(parentOf(x), BLACK);
					setColor(y, BLACK);
					setColor(parentOf(parentOf(x)), RED);
					/*
					 * 完成操作后，我们发现x向上移动了两层，因此循环的最大次数是树的高度的一
					 * 半，这也就是TreeMap中的put方法的最大执行时间为 O(logn) 原因所在了
					 */
					x = parentOf(parentOf(x));//再将x指向爷爷
				}//如果y为黑色
				else {
					if (x == leftOf(parentOf(x))) {
						/*
						* 情况2：colorOf(y) == BLACK（左叔为黑），且x为父的左子节点
						*
						* 此情况下，y一定是null，为什么？因为插入的节点60的父节点90为红色
						* ，所以90的父节点只能为黑色了，现进入到此分支时前提是y必须是黑色的
						* ，如果y真的存在，则Path规则又将被打破，所以此情况下的y一定是null
						* ，这样才满足Path规则
						*
						* ====实质上此种情况就是将问题2转换成转换成问题3来操作====
						*
						*                   绕90右旋
						*          .            →             .
						*          .                          .
						*          50B                        50B
						*           \                          \
						*(Y为NULL)  90R                        60R
						*           /                           \
						*      x → 60R                      x → 90R
						*/
						x = parentOf(x);
						rotateRight(x);
					}

					/*
					 * 情况3：colorOf(y) == BLACK，且x为父的右子节点
					 *
					 * 如果x为父的左子节点，经过上面情况2的处理，已经将其转换成情况3了，此种
					 * 情况下的y也一定为null，与第2种情况道理是一样的
					 *
					 *                    着色               绕50左旋
					 *         .            →         .       →       .
					 *         .                      .               .
					 *        50B                    50R  　　　　      90B
					 *         \                      \               /\
					 *(Y为NULL) 90R                   90B       x → 50R 95R
					 *           \                     \
					 *       x → 95R               x → 95R
					 *
					 * 处理后，下次循环自动会终止，因为此情况处理后x的父节点总是黑色的，所以
					 * 循环一定会终止于下次
					 */
					setColor(parentOf(x), BLACK);//x的父着黑
					setColor(parentOf(parentOf(x)), RED);//x的爷着红
					if (parentOf(parentOf(x)) != null)
						rotateLeft(parentOf(parentOf(x)));//绕爷爷左旋
				}
			}
		}
		root.color = BLACK;//不管怎样调整，最后根节点都要着黑
	}

	//删除节点p，然后重新调整
	private void deleteEntry(Entry p) {
		decrementSize();

		/*
		* 如果删除的节点p有左右子树时，将问题转换成删除叶子节点或只有一个子节点的节点问题，具体
		* 过程：使用中序遍历直接后s继的数据域值（key与value）拷贝到p的相应数据域值，然后将p指
		* 向直接后继s，这样待删除的节点就变成了s，问题已转换成删除叶节点或只有一个子节点的问题了
		*/
		if (p.left != null && p.right != null) {
			Entry s = successor(p);
			p.key = s.key;
			p.value = s.value;
			p = s;
		}

		//replacement用来替换即将被删除节点p
		Entry replacement = (p.left != null ? p.left : p.right);

		//如果待删除的元素有子节点（如果有，则也有且仅有一个子节点）
		if (replacement != null) {
			// 重新设置替换节点的父
			replacement.parent = p.parent;
			//如果待删除节点的为根节点
			if (p.parent == null)
				root = replacement;//则替换元素将成为根
			else if (p == p.parent.left)
				//否则如果待删除节点为左节点，则使待删除节点的父的左指针指向替换节点
				p.parent.left = replacement;
			else
				//否则如果待删除节点为右节点，则使待删除节点的父的右指针指向替换节点
				p.parent.right = replacement;

			// 解除p与其他节点的关系
			p.left = p.right = p.parent = null;

			/*
			 * 因为被删除的节点只有两种节点： 叶子节点、只有一个子节点的节点
			 *
			 * 如果删除的是只有一个子节点的节点时，根据路径规则，被删除节点一定是黑色的，而子节点
			 * 一定是红色的，这时删除操作过程就是删除了一个黑红节点而在删除位置上又放置了一个红色
			 * 节点，因此，删除一个有一个子节点的节点时，Red与Path规则都有可能打破
			 */

			if (p.color == BLACK)//???这里的条件好像是多余的，因为p一定是黑，难道另有玄机?
				//删除后从替换节点replacement位置向根方向调整
				fixAfterDeletion(replacement);
		} else if (p.parent == null) { // 如果待删除节点为根，则置root为null
			root = null;
		} else { // 如果删除的是叶子节点

			/*
			 * 如果删除的是叶子节点，不管该叶子节点是红还是黑，则 Red规则 在删除后不可能被打破，
			 * 所以此情况下只有可能打破 Path规则 ，但打破的前提是被删除的节点是黑色的，因为只有
			 * 黑色节点才影响Path路径规则，所以调整的前提是 p.color == BLACK，即被删除的叶
			 * 节点是黑色的
			 */
			if (p.color == BLACK)
				//删除前从即将被删除节点p位置向根方向调整
				fixAfterDeletion(p);

			//???此条件好像多余，因为如果不成立，则走上面那个分支了，难道另藏玄机
			if (p.parent != null) {
				if (p == p.parent.left)
					p.parent.left = null;
				else if (p == p.parent.right)
					p.parent.right = null;
				p.parent = null;//解除p与其他节点的关系
			}
		}
	}

	//删除节点后调整
	private void fixAfterDeletion(Entry x) {

		//直循环到根节点或红色止
		while (x != root && colorOf(x) == BLACK) {
			//x为左子节点
			if (x == leftOf(parentOf(x))) {
				//x的同胞
				Entry sib = rightOf(parentOf(x));//有可能为null

				if (colorOf(sib) == RED) {//同胞不可能为null
					/*
					 * 情况1：colorOf(sib) == RED，即右叔存在且为红
					 *              通过执行上面deleteEntry后，x、p均指向后继65
					 *        p → 60B             →           65B
					 *            /  \                        /  \
					 *          50B   70B                   50B   70B
					 *          /\    /  \                  /\    /  \
					 *       45B 55B 65B 80R             45B 55B 65B 80R ← sib
					 *                   / \                     ↑   /  \
					 *                 75B 87B                 x、p 75B 87B
					 *  着色                         绕70左旋
					 *   →        65B               →           65B
					 *            /  \                          /  \
					 *          50B   70R                     50B   80B
					 *          /\    /  \                    /\    / \
					 *       45B 55B 65B 80B ← sib         45B 55B 70R 87B
					 *                ↑   / \                      /  \
					 *              x、p 75B 87B            x、p → 65B 75B ← sib
					 *  x现有一个新的同胞节点，该节点为黑，现将情况1转向其他3种情况
					 */

					setColor(sib, BLACK);//置同胞为黑
					setColor(parentOf(x), RED);//置父为红
					rotateLeft(parentOf(x));//左旋
					sib = rightOf(parentOf(x));//再处理同胞节点
				}

				if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
					/*
					* 情况2：
					* colorOf(leftOf(sib))==BLACK && colorOf(rightOf(sib))==BLACK
					* 即同胞不存在或同胞存在时为黑且没有子节点的情况
					*
					* 现接着上面情况1结束后的那棵树处理
					* 着色                       再将x指向父(70R)继续处理
					*  →      65B                   →           ...
					*         /  \
					*       50B   80B
					*       /\     / \
					*     45B 55B 70R 87B
					*             / \
					*     x、p → 65B 75R ← sib
					*
					* 处理完后，x为红，因此结束循环，然后设置x的颜色为黑，并从此返回
					* deleteEntry方法，此时p所指向的节点已被从其父节点解除链接
					*/
					setColor(sib, RED);
					x = parentOf(x);
				} else {
					if (colorOf(rightOf(sib)) == BLACK) {
						/*
						* 情况3：经过情况2后，此时同胞节点左子节点必为红
						*
						* 假设在开始while循环之前有如下树：
						*  →      65B
						*         /  \
						*       50B   80R
						*      / \    /  \
						*    45B 55B 70B  90B
						*            / \   / \
						*     x → 65B 75B 85R 95B
						*             ↗  / \
						*          sib  82B 87B
						*
						* 情况2被应用，因此将75的颜色设置为红，并令 x = parentOf(x)后第
						* 一遍循环结束。第二遍循环开始时，会应用情况3，此时同胞节点为90，树
						* 如下图：
						* 第一次循环完                  着色
						*    →    65B                →          65B
						*        /   \                          /  \
						*      50B    80R                    50B    80R
						*     / \     /  \                   / \    /  \
						*   45B 55B 70B   90B ← sib       45B 55B 70B   90R ← sib
						*         ↗/ \    / \                  ↗/ \     / \
						*       x 65B 75R 85R 95B             x 65B 75R 85B 95B
						*                 / \                           / \
						*               82B 87B                       82B 87B
						*
						* 绕90右旋后并修改sib指向           进行下一步循环，应用情况4
						*    →          65B                      → ...
						*              /   \
						*            50B    80R
						*           / \     /  \
						*         45B 55B 70B   85B ← sib
						*               ↗/ \    / \
						*             x 65B 75R 82B 90R
						*                           / \
						*                         87B 95B
						*
						* 一旦情况3被应用，则同胞节点的右子节点将设置为 RED，因此情况4在情况
						* 3之后应用，为了不在下一次循环中应用情况4，直接将情况4的代码放在了
						* 情况3的后面
						*/
						setColor(leftOf(sib), BLACK);
						setColor(sib, RED);
						rotateRight(sib);
						sib = rightOf(parentOf(x));
					}
					/*
					 * 情况4:经过情况3后剩下的情况4，此时同胞节点的右子节点是红
					 *
					 * 接着第3种情况处理：
					 *
					 *   着色                    绕80左旋
					 *    →       65B            →              65B
					 *           /   \                          /  \
					 *         50B    80B                     50B   85R ← sib
					 *        / \     /  \                   / \    /  \
					 *      45B 55B 70B   85R ← sib        45B 55B 80B 90B
					 *            ↗/ \    / \                     / \   \
					 *          x 65B 75R 82B 90B            x → 70B 87B 95B
					 *                        / \                 /
					 *                      87B 95B         p → 65B
					 *
					 * 循环结束后返回到 deleteEntry 方法，删除65节点即完成删除操作过程
					 */
					setColor(sib, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(rightOf(sib), BLACK);
					rotateLeft(parentOf(x));

					//情况3与4被应用后，x将被设置为根节点，同时，这也是循环的最后一次
					x = root;
				}
			} else { // 与上面对称
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
		//退出循环时x为根或为红色，直接将其值为黑色即可
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