/**
 * 哈夫曼树又称最优二叉树，是一种带权路径长最短的树。树的路径长度是从树根到每一个叶子之间的路径长度之和。节点的带树路径长度为从该节点到树根之间的路径长度与该节点权（比如字符在某串中的使用频率）的乘积。

比如有一串字符串如：3334444555556666667777777，它是由3、4、5、6、7这五个数字组成的，现要使用一种编码方式，让它编码存储最短，如何做？如果五个数使用3位的定长的

二进制就可表示，如：(3:000) (4:001) (5:010) (6:100) (7:101)，则编码后的存储空间需 3 * (3 + 4 + 5 + 6 + 7) = 75 比特位。能否有一种压缩的方法把存储空间缩小？这就是Huffman编码，它是一种不等长编码，这就要求一个字符编码的不是另一个字符编码的前缀，它是一种最优前缀编码。这需要一开始就需要统计出每个字符出现的频率，然后基于这些频率来设计出编码树，将可以节省大量的空间。利用字符出现的频率决定编码这一思想是Huffman编码的基础，Huffman编码是所有无前缀编码中最优的一种编码策略。Huffman编码是Unix中compress工具的基础，也是联合图的是专家组(JPEG)编码过程上的一部分。
人们在数据压缩领域使用了优先级队列。给定一段消息，可以对每个字符进行无前缀的编码，使其编码长度具有最少的比特位。使用Huffman树，可以得到这种最小编码。Huffman树是这样一棵完全的二叉树，它的每个叶节点都表示一个原消息中的不同字符，每个左分支都标为0，而每个右分支都标示为1。沿着根节点到叶节点字符的路径，将该路径中的分支标签依次组合起来，就可以得到该字符的Huffman编码。

下面给出二种编码的二叉树，但只有第二种是最优二叉树：

    (:25)
    0/  \1
   (:18) 7
  0/  \1
 (:7) (:11)
0/ \1 0/ \1
3   4 5   6
权值 = (3 + 4 + 5 + 6) * 3 + 7 * 1 = 61（非最优二叉树）

          (:25)
         0/   \1
        (:11) (:14)
       0/ \1  0/ \1
       5   6   7  (:7)
                  0/ \1
                  3   4
权值 = (3 + 4) * 3 + 7 * 2 + (5 + 6) * 2 = 57（最优二叉树）

因此，五个数的编码为 (3:000) (4:001) (7:01) (5:10) (6:11)，从这些不等长编码来看，不存在一个字符的编码是另一个字符编码的前缀。一个保证无前缀比特编码的方法是创建一棵二叉树，它的左分支通常使用0来表示，而右分支用1来表示。如果每个已编码的字符都在树的叶子上，那么该字符的编码就不可能是其它字符编码的前缀，换句话说，到达每个字符路径正好是一个无前缀编码。

哈夫曼树的构造过程：从原始元素集合T中拿出两个频度最小的元素组成一个二叉树，二叉树的根为这两个节点频度的和，然后从集合T中删除这两个元素，把根元素加入到T集合中，如此反复直集合T为空。

那么我说究竟如果实现上面叙述的思想呢？
在统计完每个字符出现的频率之后，按照频率递增的顺序将每个字符—频率对插入到一个优先级队列中，即优先队列中具有最高优先级的字符—频率对中的字符具有最小的出现频率，这些字符将在离Huffman树根最远的叶子节点外结束，因此它们的编码具有最多的比特位。相反，出现频率最高的字符将具有最小的比特位编码。

首先将下列字符—频率对插入到优先队列中：
（3:3）（4:4）（5:5）（6:6）（7:7）
形成的初始堆如下：
      3
     / \
    4   5
   / \
6    7

基于字符—频率对组成的优先级队列所构造的二叉树称作Huffman树，我们将自底向上构建Huffman树。现假设所有字符元素都已按使用频率添加到了优先级队列中去了，即初始堆已构造好（如上述所示），下面开始构建Huffman树：

首先调用两次优先级队列的removeMin方法，得到两个频率最低的字符。“3”是第一个被删除的元素，即第一个出队的元素，它成为二叉树的左叶子节点，而“4”成为右叶节点，它们两者的频率之和（:7）成为树的根节点，并又将根（:7）添加到优先级队列中，现在得到如下的Huffman树：
      (:7)
     0/ \1
     3   4
此时优先级队列中包含：
（5:5）（6:6）（7:7）（:7）
堆结构如下：
      5
     / \
    6   7
   /
 (:7)

然后，删除5、6，但它们不能直接连先前哈夫曼树中，因为它们元素都不在哈夫曼树中。因为它们成为另一棵树的左子叶节点和右子叶节点，且该树的根是它们的频率之后（:11），根将被插入到优先级队列中，现在有两棵Huffman树：
      (:11)    和        (:7)
     0/ \1             0/  \1
     5   6              3    4
此时，优先级队列中包含的元素如下：
(7:7) (:7) (:11)
堆结构如下：
      7
     /  \
 (:7) (:11)

再然后，当(:7)被删除时，它成为二叉树的左分支，而另一个被删除的7元素则是树的右分支，两者频率之和成为二叉树的根(:14)，被插入优先级队列中。由于(:7)在树中，所以这一次在原来已有的某树上进行扩充，这样就得到下面Huffman树：
  (:11)     和     (:14)
 0/ \1            0/ \1
5   6            7  (:7)
                      0/ \1
                      3   4
此时优先队列中包含：
(:11) (:14)
堆结构如下：
(:11)
   /
(:14)

最后，删除(:11)与(:14)两个节点，由于这两个节点都存在于已创建好的Huffman中，所以这次实质上这次是合并这两个Huffman树，最后形成最终的Huffman树：
          (:25)
         0/   \1
        (:11) (:14)
       0/ \1  0/ \1
       5   6   7  (:7)
                  0/ \1
                  3   4
 */
package com.xangqun.sort.huffman;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.xangqun.sort.priorityqueue.heap.Heap;


/**
 * 哈夫曼树与哈夫曼编解码
 *
 * @author jzj
 * @data 2010-1-8
 */
public class Huffman {

	//哈夫曼树节点
	private static class Entry implements Comparable<Entry> {

		int freq;//节点使用频率，优先级就是根据此决定
		String code;//节点huffman编码
		char c;//节点所对应的字符
		Entry left, right, parent;//哈夫树遍历相关字段

		//节点的优先级比较
		public int compareTo(Entry entry) {
			return freq - entry.freq;
		}

		public String toString() {
			return "(" + c + ":" + code + ")";
		}
	}

	//这里我们仅只对Unicodeue前256个字符编码，所以只能输入ISO8859-1字符串
	protected final int SIZE = 256;

	//哈夫编码表，用于快速查询某字符的哈夫编码
	protected Entry[] leafEntries;

	//堆，用来动态进行优先级排序
	protected Heap<Entry> pq;

	//要编码的输入串
	protected String input;

	public Huffman(String input) {
		this.input = input;
		createPQ();
		createHuffmanTree();
		calculateHuffmanCodes();
	}

	//创建初始堆
	public void createPQ() {

		//初始化哈夫编码表
		Entry entry;
		leafEntries = new Entry[SIZE];
		for (int i = 0; i < SIZE; i++) {
			leafEntries[i] = new Entry();
			leafEntries[i].freq = 0;//使用频率
			/*
			 * leafEntries哈夫编码表中的索引与字符的编码对应，这样在读取时
			 * 很方便
			 */

			leafEntries[i].c = (char) i;//节点点是对应的字符

		}

		//填充哈夫编码表
		fillLeafEntries();

		//开始创建初始堆
		pq = new Heap<Entry>();
		for (int i = 0; i < SIZE; i++) {
			entry = leafEntries[i];
			if (entry.freq > 0) {//如果被使用过，则放入堆中
				pq.add(entry);
			}
		}
	}

	//根据输入的字符串填充leafEntries哈夫编码表
	public void fillLeafEntries() {

		Entry entry;

		for (int i = 0; i < input.length(); i++) {

			entry = leafEntries[(int) (input.charAt(i))];
			entry.freq++;
			entry.left = null;
			entry.right = null;
			entry.parent = null;
		}
	}

	// 创建哈夫曼树
	public void createHuffmanTree() {

		Entry left, right, parent;

		//每次需从堆中取两个，所以需大于1，如果小于等于1时表示哈夫曼树已创建完毕
		while (pq.size() > 1) {

			// 使用贪婪法，每次从优先级队列中读取最小的两个元素
			left = (Entry) pq.removeMin();
			left.code = "0";//如果做为左子节点，则为路径编码为0

			right = (Entry) pq.removeMin();
			right.code = "1";//如果做为右子节点，则为路径编码为1

			parent = new Entry();
			parent.parent = null;

			//父节点的使用频度为两者之和
			parent.freq = left.freq + right.freq;
			parent.left = left;
			parent.right = right;
			left.parent = parent;
			right.parent = parent;

			//再把父节点放入堆中，将会进行重组堆结构
			pq.add(parent);
		}
	}

	// 计算输入串的每个字符的哈夫编码
	public void calculateHuffmanCodes() {

		String code;
		Entry entry;

		for (int i = 0; i < SIZE; i++) {

			code = "";
			entry = leafEntries[i];
			if (entry.freq > 0) {//如果使用过该字符时就需要求哈夫编码

				do {
					/*
					* 拼接从叶节点到根节点路径上各元素的路径编码，最后得到哈夫编码，
					* 注，这里倒着来的，所以不能有这样：code = code + entry.code;
					*/
					code = entry.code + code;
					entry = entry.parent; // 要一直循环到根
				} while (entry.parent != null);

				leafEntries[i].code = code;//设置最后真真的哈夫编码

			}
		}
	}

	//得到哈夫曼编码表
	public Map<String, String> getHuffmancodeTable() {

		Map<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < SIZE; i++) {
			Entry entry = leafEntries[i];
			if (entry.freq > 0) {//如果使用过该字符时就需求哈夫编码
				map.put(String.valueOf(entry.c), entry.code);
			}
		}

		return map;
	}

	//得到字符串所对应的哈夫曼编码
	public String getHuffmancodes() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			Entry entry = leafEntries[input.charAt(i)];
			sb.append(entry.code);
		}
		return sb.toString();
	}

	//将huffman消息串还原成字符串
	public static String huffmancodesToString(Map<String, String> map, String huffmanCodes) {
		Entry root = createTreeFromCode(map);
		return encoding(root, huffmanCodes);
	}

	//根据指定的哈夫曼编码创建哈夫曼树
	private static Entry createTreeFromCode(Map<String, String> map) {
		Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
		Map.Entry<String, String> mapEntry;
		Entry root = new Entry(), parent = root, tmp;

		while (itr.hasNext()) {
			mapEntry = itr.next();

			//从根开始创建树
			for (int i = 0; i < mapEntry.getValue().length(); i++) {

				if (mapEntry.getValue().charAt(i) == '0') {
					tmp = parent.left;
					if (tmp == null) {
						tmp = new Entry();
						parent.left = tmp;
						tmp.parent = parent;
						tmp.code = "0";
					}
				} else {
					tmp = parent.right;
					if (tmp == null) {
						tmp = new Entry();
						parent.right = tmp;
						tmp.parent = parent;
						tmp.code = "1";
					}
				}

				if (i == mapEntry.getValue().length() - 1) {
					tmp.c = mapEntry.getKey().charAt(0);
					tmp.code = mapEntry.getValue();
					parent = root;
				} else {
					parent = tmp;
				}
			}

		}
		return root;
	}

	//根据给定的哈夫曼编码解码成字符
	private static String encoding(Entry root, String huffmanCodes) {
		Entry tmp = root;
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < huffmanCodes.length(); i++) {
			if (huffmanCodes.charAt(i) == '0') {
				tmp = tmp.left;//找到与当前编码对应的节点
				//如果哈夫曼树左子树为空，则右子树也肯定为空，也就是说，分支节点一定是用两个节点的节点
				if (tmp.left == null) {//如果为叶子节点，则找到完整编码
					sb.append(tmp.c);
					tmp = root;//准备下解码下一个字符
				}
			} else {
				tmp = tmp.right;
				if (tmp.right == null) {
					sb.append(tmp.c);
					tmp = root;
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String inputStr = "3334444555556666667777777";
		Huffman hfm = new Huffman(inputStr);

		Map<String, String> map = hfm.getHuffmancodeTable();
		String huffmancodes = hfm.getHuffmancodes();
		System.out.println("输入字符串 - " + inputStr);
		System.out.println("哈夫曼编码对照表 - " + map);
		System.out.println("哈夫曼编码 - " + huffmancodes);
		String encodeStr = Huffman.huffmancodesToString(map, huffmancodes);
		System.out.println("哈夫曼解码 - " + encodeStr);
		/*
		 * output:
		 * 输入字符串 - 3334444555556666667777777
		 * 哈夫曼编码对照表 - {3=110, 5=00, 7=10, 4=111, 6=01}
		 * 哈夫曼编码 - 110110110111111111111000000000001010101010110101010101010
		 * 哈夫曼解码 - 3334444555556666667777777
		 */
	}
}
