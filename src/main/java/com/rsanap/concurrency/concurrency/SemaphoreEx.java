package com.rsanap.concurrency.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreEx {
	public static void main(String[] args) {
		final PoolEx pool = new PoolEx();
		Runnable runnable = new Runnable() {

			public void run() {
				String threadName = Thread.currentThread().getName();
				try {
					while (true) {
						String item;

						System.out.printf("%s acquiring %s%n", threadName,
								item = pool.getAvailableItem());
						Thread.sleep((long) (5000 * Math.random()));
						pool.releaseItem(item);
						Thread.sleep((long) (5000 * Math.random()));
						System.out.printf("%s releasing item %s%n", threadName,
								item);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ExecutorService[] executorService = new ExecutorService[PoolEx.MAX_LIMIT + 1];
		for (int i = 0; i < PoolEx.MAX_LIMIT; i++) {
			executorService[i] = Executors.newSingleThreadExecutor();
			executorService[i].execute(runnable);
		}
	}
}

class PoolEx {
	public static final int MAX_LIMIT = 10;
	private Semaphore semaphore = new Semaphore(MAX_LIMIT);
	private boolean[] used = new boolean[MAX_LIMIT];
	private String[] items = new String[MAX_LIMIT];

	PoolEx() {
		for (int index = 0; index < MAX_LIMIT; index++)
			items[index] = "Item " + index;
	}

	public String getAvailableItem() throws InterruptedException {
		semaphore.acquire();
		for (int i = 0; i < MAX_LIMIT; i++) {
			if (!used[i]) {
				used[i] = true;
				return items[i];
			}
		}
		return null;
	}

	public void releaseItem(String item) {
		for (int i = 0; i < MAX_LIMIT; i++) {
			if (items[i] == item) {
				if (used[i]) {
					used[i] = false;
					semaphore.release();
				}

			}
		}
	}
}