package com.rsanap.concurrency.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		for (int i = 0; i < 15; i++) {

			Runnable runnable = new Runnable() {
				public void run() {

					for (int i = 1; i < 2; i++)
						synchronized (count) {
							System.out.println(Thread.currentThread().getName()
									+ " count " + count++);
						}
					/*System.out.println("Thread exiting "
							+ Thread.currentThread().getName());*/
				}
			};
			executors.submit(runnable);
			// System.out.println("Exiting now!!");
		}
		// Initiates orderly shutdown, executes already submitted tasks.
		// Executor will not accept new tasks

		executors.shutdown();
		
	}

	private static ExecutorService executors = Executors.newFixedThreadPool(5);
	private static volatile Integer count = 0;
}
