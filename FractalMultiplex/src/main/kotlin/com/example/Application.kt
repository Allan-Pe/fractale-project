package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.concurrent.Executors
import java.util.concurrent.Future

fun main() {
//    System.setProperty("io.ktor.development", "true")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

}

fun Application.module() {
//    val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)
//    // 'Any' must be changed for the value type of our returned result
//    val fractalPixels = mutableListOf<Future<Any>>()

    configureHTTP()
    configureSerialization()
    configureTemplating()
    configureRouting()
}
