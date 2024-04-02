package com.example.plugins

import com.example.fractalGenerator.FractalGenerator
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


fun Application.configureRouting(fractalGenerator: FractalGenerator) {
    routing {
        post("/generatenewfractal") {
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            val fullFractalImage = fractalGenerator.generateFractal(fractalProperties)
            val byteArray = convertImageToByteArray(fullFractalImage)
            call.respondBytes(byteArray, ContentType.Image.JPEG)
        }

        post("/updatefractal") {
            val fractalProperties: FractalProperties = Json.decodeFromString(call.receiveText())
            val updatedFractalImage =
                fractalGenerator.generateFractal(fractalProperties)
            val byteArray = convertImageToByteArray(updatedFractalImage)
            call.respondBytes(byteArray, ContentType.Image.JPEG)
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
    }
}
