package com.example.threadPool

import java.util.concurrent.*

interface MyCallable<T> : Callable<T> {
    override fun call(): T
}

interface MyExecutor<T> {
    fun submit(callable: MyCallable<T>): Future<T>
}

class MyThreadPoolExecutor<T> : MyExecutor<T> {
    private val threadPool = MyThreadPool<T>(Runtime.getRuntime().availableProcessors() * 2 + 1)
    override fun submit(callable: MyCallable<T>): Future<T> {
        return threadPool.submit(callable)
    }
}

class MainExecutorKotlin<T> : MyExecutor<T> {
    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)
    override fun submit(callable: MyCallable<T>): Future<T> {
        return executor.submit(callable)
    }
}

class MyThreadPool<T>(val nbThreads: Int) {
    private val linkedBlockingQueue = LinkedBlockingQueue<RunnableFuture<T>>()
    private val threadList = mutableListOf<Thread>()

    init {
        threadCreator()
    }

    fun addTaskToQueue(task: MyCallable<T>): Future<T> {
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

    fun submit(task: MyCallable<T>): Future<T> {
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


class Task : Runnable {
    override fun run() {
        println("RUN OK !!!!")
    }
}

class Task2 : MyCallable<String> {
    override fun call(): String {
        return "CALL OK !!!!"
    }
}

fun main() {

    val task = Task2()
    val executor: MyExecutor<String> = MyThreadPoolExecutor()

    val res = executor.submit(task)
    println(res.get())
}
