package com.example.plugins

import com.example.fractalGenerator.FractalGenerator
import com.example.fractalGenerator.FractalStats
import com.example.fractalGenerator.convertImageToByteArray
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class FractalMovementDto(
    val direction: String
)

@Serializable
data class FractalStatsDto(
    val timeImage: Long,
    val averageTimeImage: Long,
    val averageTimeTask: Long,
    val iteration: Int
)


fun Application.configureRouting(fractalGenerator: FractalGenerator) {
    routing {
        get("/generatefractal") {
            val fullFractalImage = fractalGenerator.generateFractal()
            val byteArray = convertImageToByteArray(fullFractalImage)
            call.respondBytes(byteArray, ContentType.Image.JPEG)
        }

        post("/generatefractal") {
            val requestDirections: FractalMovementDto = Json.decodeFromString(call.receiveText())
            fractalGenerator.updateFractalPosition(requestDirections.direction)
            val fullFractalImage = fractalGenerator.generateFractal()
            val byteArray = convertImageToByteArray(fullFractalImage)
            call.respondBytes(byteArray, ContentType.Image.JPEG)
        }

        get("/savefractal") {
            fractalGenerator.saveFractalasJpeg()
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
