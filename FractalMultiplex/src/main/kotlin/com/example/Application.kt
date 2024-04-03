package com.example

import com.example.cache.Cache
import com.example.fractalGenerator.standardPool.FractalGenerator
import com.example.fractalGenerator.customPool.PoolFractalGenerator
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.concurrent.Executors

fun main() {
//    System.setProperty("io.ktor.development", "true")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

}

fun Application.module() {
    val singleThreadValue = 1
    val multithreadValue = Runtime.getRuntime().availableProcessors() * 2 + 1
    val threadPool = Executors.newFixedThreadPool(multithreadValue)
    val poolFractalGenerator = PoolFractalGenerator()
    val fractalGenerator = FractalGenerator(threadPool)
    val cache = Cache<String,ByteArray>(100)

    configureHTTP()
    configureSerialization()
    configureTemplating()
    configureRouting(fractalGenerator, poolFractalGenerator, cache)
}

