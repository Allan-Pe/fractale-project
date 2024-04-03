package com.example

import com.example.cache.HistoryHandler
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
    val threadPool = Executors.newFixedThreadPool(1)
    val poolFractalGenerator = PoolFractalGenerator()
    val fractalGenerator = FractalGenerator(threadPool)
    val historique = HistoryHandler()

    configureHTTP()
    configureSerialization()
    configureTemplating()
    configureRouting(fractalGenerator, poolFractalGenerator, historique)
}
