package com.rsanap.concurrency.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableEx {

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		//Creating executors for max 5 threads
		ExecutorService executors = Executors.newFixedThreadPool(5);
		
		Callable<List<String>> callable = new Callable<List<String>>() {
			public List<String> call() throws Exception {
				List<String> listOfStrings = new ArrayList<String>();
				for (int i = 0; i < 5; i++) {
					listOfStrings.add(Thread.currentThread().getName() + " "
							+ i);
				}
				return listOfStrings;
			}
		};
		Future<List<String>> result = executors.submit(callable);
		System.out.println(result.get().toString());
		// Shutting down Executors
		executors.shutdown();
	}
}
