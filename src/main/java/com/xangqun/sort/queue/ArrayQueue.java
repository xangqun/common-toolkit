package com.xangqun.sort.queue;

/**
 * ѭ�����У�ʹ������ʵ��
 * @author jzj
 */
public class ArrayQueue<E> implements Queue<E> {

	private Object lock = new Object();

	// ���д�С
	private int size = 1000;

	// ����
	private E[] arrStr = (E[]) new Object[size];

	// ����ָ��
	private int head = 0;

	// ����βָ��
	private int tail = 0;

	/**
	 * ���
	 * @param o
	 */
	public void enqueue(E o) {
		synchronized (lock) {

			// �����������
			while ((tail + 1) % size == head) {
				try {
					System.out.println("����������" + Thread.currentThread().getName()
							+ " �߳�����...");
					// ������ʱ�߳�����
					lock.wait();//ע������һ��Ҫ����while�������Ϊ��ȡ����������һ��������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// �������δ��
			arrStr[tail] = o;
			// ָ������
			tail = (tail + 1) % size;
			// ��Ӻ�֪ͨ�����߳�
			lock.notifyAll();
		}
	}

	/**
	 * ����
	 * @return
	 */
	public E dequeue() {
		synchronized (lock) {
			// �������Ϊ��
			while (head == tail) {
				try {
					System.out.println("����Ϊ�գ�" + Thread.currentThread().getName()
							+ " �߳�����...");
					// ���п�ʱ�߳�����
					lock.wait();//ע������һ��Ҫ����while�������Ϊ��ȡ����������һ��������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// ���зǿ�
			E tempStr = arrStr[head];

			arrStr[head] = null;//ע��������Ӻ��ͷŶ��󣬼ӿ���գ���Ȼ��Ķ���������ڴ�й¶
			head = (head + 1) % size;

			// ���Ӻ�֪ͨ������
			lock.notifyAll();
			return tempStr;

		}
	}

	//ȡ���е�һ��
	public E front() {
		synchronized (lock) {
			// �������Ϊ��
			while (head == tail) {
				try {
					System.out.println("����Ϊ�գ�" + Thread.currentThread().getName()
							+ " �߳�����...");
					// ���п�ʱ�߳�����
					lock.wait();//ע������һ��Ҫ����while�������Ϊ��ȡ����������һ��������
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// ���зǿ�
			return arrStr[head];
		}
	}

	//�����Ƿ�Ϊ��
	public boolean isEmpty() {
		return head == tail;
	}

	//���д�С
	public int size() {

		return Math.abs(tail - head);
	}
}
