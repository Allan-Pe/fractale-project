package com.example.threadPool

import java.util.concurrent.*

class MyThreadPool(val nbThreads: Int) : MyExecutorService {
    private val linkedBlockingQueue = LinkedBlockingQueue<Runnable>()
    private val threadList = mutableListOf<Thread>()

    init {
        threadCreator()
    }

    fun addTaskToQueue(task: Runnable) {
        linkedBlockingQueue.add(task);
    }

    fun threadCreator() {
        for (t in 0..nbThreads) {
            val thread = MyThread()
            thread.start()
            threadList.add(thread)
        }
    }

    override fun execute(task: Runnable) {
        addTaskToQueue(task)
    }


    inner class MyThread(
    ) : Thread() {

        override fun run() {
            while (true) {
                val task: Runnable = linkedBlockingQueue.take()
                task.run()
            }
        }
    }
}

interface MyExecutorService : Executor {
    override fun execute(task: Runnable)
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

    val task = Task()
    val threadPool = MyThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)

    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)
    threadPool.execute(task)


}
