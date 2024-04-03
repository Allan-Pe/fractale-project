package com.example.threadPool

import java.util.concurrent.*

interface MyExecutor<T> {
    fun submit(callable: Callable<T>): Future<T>
}

class MyThreadPoolExecutor<T> : MyExecutor<T> {
    private val threadPool = MyThreadPool<T>(Runtime.getRuntime().availableProcessors() * 2 + 1)
    override fun submit(callable: Callable<T>): Future<T> {
        return threadPool.submit(callable)
    }
}

class MainExecutorKotlin<T> : MyExecutor<T> {
    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)
    override fun submit(callable: Callable<T>): Future<T> {
        return executor.submit(callable)
    }
}

class MyThreadPool<T>(val nbThreads: Int) {
    private val linkedBlockingQueue = LinkedBlockingQueue<RunnableFuture<T>>()
    private val threadList = mutableListOf<Thread>()

    init {
        threadCreator()
    }

    fun addTaskToQueue(task: Callable<T>): Future<T> {
        val fTask = FutureTask(task)
        linkedBlockingQueue.add(fTask)
        return fTask
    }

    fun threadCreator() {
        for (t in 0..nbThreads) {
            val thread = MyThread()
            thread.start()
            threadList.add(thread)
        }
    }

    fun submit(task: Callable<T>): Future<T> {
        return addTaskToQueue(task)
    }

    inner class MyThread(
    ) : Thread() {

        override fun run() {
            while (true) {
                val task = linkedBlockingQueue.take()
                task.run()
            }
        }
    }
}
