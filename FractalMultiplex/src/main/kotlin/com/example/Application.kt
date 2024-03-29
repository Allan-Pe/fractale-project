package com.example

import com.example.fractalGenerator.FractalGenerator
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
    val threadPool = Executors.newFisqxedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)
    val fractalGenerator = FractalGenerator(threadPool)

    configureHTTP()
    configureSerialization()
    configureTemplating()
    configureRouting(fractalGenerator)
}
