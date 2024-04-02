package com.example.plugins

import com.example.cache.HistoryHandler
import com.example.fractalGenerator.standardPool.FractalGenerator
import com.example.fractalGenerator.customPool.PoolFractalGenerator
import com.example.fractalGenerator.outil.FractalProperties
import com.example.fractalGenerator.outil.convertImageToByteArray
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class FractalStatsDto(
    val timeImage: Long,
    val averageTimeImage: Long,
    val averageTimeTask: Long,
    val iteration: Int
)


fun Application.configureRouting(fractalGenerator: FractalGenerator, poolFractalGenerator: PoolFractalGenerator, historyHandler: HistoryHandler) {
    routing {
        post("/generatenewfractal") {
            println("generate called")
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            val fullFractalImage = fractalGenerator.generateFractal(fractalProperties)
            val byteArray = convertImageToByteArray(fullFractalImage)
            historyHandler.history.cache.clear()
            historyHandler.addElementToCache(byteArray)
            call.respondBytes(historyHandler.history.cache.getLastElement()!!, ContentType.Image.JPEG)
        }

        post("/updatefractal") {
            println("update called")
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            val updatedFractalImage =
                fractalGenerator.generateFractal(fractalProperties)
            val byteArray = convertImageToByteArray(updatedFractalImage)
            historyHandler.addElementToCache(byteArray)
            call.respondBytes( historyHandler.history.cache.getLastElement()!!, ContentType.Image.JPEG)
        }

        get("/undo") {
            println("undo called")
            historyHandler.undo()
            println(historyHandler.history.cache.getLastElement())
            call.respondBytes( historyHandler.history.cache.getLastElement()!!, ContentType.Image.JPEG)
        }

        get("/redo") {
            println("redo called")
            historyHandler.redo()
            println(historyHandler.history.cache.getLastElement())
            call.respondBytes( historyHandler.history.cache.getLastElement()!!, ContentType.Image.JPEG)
        }

        post("/savefractal") {
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            fractalGenerator.saveFractalasJpeg(fractalProperties)
            call.respondText("image save")
        }

        get("/getstats") {

            val responseStats = FractalStatsDto(
                timeImage = fractalGenerator.stats.timeThisImage,
                averageTimeImage = fractalGenerator.stats.averageGenerationTime(),
                averageTimeTask = fractalGenerator.stats.averageGenerationTimeTask(),
                iteration = fractalGenerator.stats.totalIterations,
            )

            val jsonStats = Json.encodeToString(responseStats)

            call.respondText(jsonStats, ContentType.Application.Json)
        }

        post("/generatenewfractalthreadpool") {
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            val fullFractalImage = poolFractalGenerator.generateFractal(fractalProperties)
            val byteArray = convertImageToByteArray(fullFractalImage)
            call.respondBytes(byteArray, ContentType.Image.JPEG)
        }

        get("/julia") {
            fractalGenerator.isJuliaTrue= !fractalGenerator.isJuliaTrue
            call.respondText(fractalGenerator.isJuliaTrue.toString())
        }
    }
}
