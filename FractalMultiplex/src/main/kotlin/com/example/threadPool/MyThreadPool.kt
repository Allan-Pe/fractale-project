package com.example.threadPool

import java.util.concurrent.*

interface MyExecutor {
    fun submit(callable: Callable<String>): Future<String>

}

class MyThreadPoolExecutor : MyExecutor {

    private val threadPool = MyThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)
    override fun submit(callable: Callable<String>): Future<String> {
        return threadPool.submit(callable)
    }
}

class MainExecutorKotlin : MyExecutor {

    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)
    override fun submit(callable: Callable<String>): Future<String> {
        return executor.submit(callable)
    }


}

class MyThreadPool(val nbThreads: Int) {
    private val linkedBlockingQueue = LinkedBlockingQueue<RunnableFuture<String>>()
    private val threadList = mutableListOf<Thread>()

    init {
        threadCreator()
    }

    fun addTaskToQueue(task: Callable<String>): Future<String> {
        val fTask = FutureTask<String>(task)
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

    fun submit(task: Callable<String>): Future<String> {
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

class Task2 : Callable<String> {
    override fun call(): String {
        return "CALL OK !!!!"
    }
}

fun main() {

    val task = Task2()
    val executor: MyExecutor = MyThreadPoolExecutor()

    val res = executor.submit(task)
    println(res.get())

}
