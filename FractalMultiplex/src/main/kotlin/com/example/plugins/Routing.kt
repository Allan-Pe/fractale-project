package com.example.plugins

import com.example.cache.Cache
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


fun Application.configureRouting(fractalGenerator: FractalGenerator, poolFractalGenerator: PoolFractalGenerator, cache: Cache<String, ByteArray>) {
    routing {
        post("/generatenewfractal") {
            println("generate called")
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            val fullFractalImage = fractalGenerator.generateFractal(fractalProperties)
            val byteArray = convertImageToByteArray(fullFractalImage)
            val key = cache.changeToKey(fractalProperties)
            cache.put(key, byteArray)
            call.respondBytes(cache.getLastElement()!!, ContentType.Image.JPEG)
        }

        post("/updatefractal") {
            println("update called")
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            val updatedFractalImage =
                fractalGenerator.generateFractal(fractalProperties)
            val byteArray = convertImageToByteArray(updatedFractalImage)
            val key = cache.changeToKey(fractalProperties)
            cache.put(key, byteArray)
            call.respondBytes(cache.getLastElement()!!, ContentType.Image.JPEG)
        }

        post("/undo") {
            println("undo called")
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            println("IN UNDO ////////////////////////////:")
            println(fractalProperties)
            val key = cache.changeToKey(fractalProperties)
            val byteArray = cache.get(key)
            call.respondBytes(byteArray!!, ContentType.Image.JPEG)

        }

        post("/redo") {
            println("redo called")
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            val key = cache.changeToKey(fractalProperties)
            val byteArray = cache.get(key)
            call.respondBytes(byteArray!!, ContentType.Image.JPEG)
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
