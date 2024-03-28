package com.example.plugins

import com.example.fractalGenerator.FractalGenerator
import com.example.outils.convertImageToByteArray
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class FractalMovementDto(
    val direction: String
)


fun Application.configureRouting(fractalGenerator: FractalGenerator) {
//    val fractalGenerator = FractalCallable()
    routing {
        get("/generatefractal") {
            val fullFractalImage = fractalGenerator.generateFractal()
            val byteArray = convertImageToByteArray(fullFractalImage)
            call.respondBytes(byteArray, ContentType.Image.JPEG)
        }

        post("/generatefractal") {
            val requestDirections: FractalMovementDto = Json.decodeFromString(call.receiveText())
            println(requestDirections)
            fractalGenerator.updateFractalPosition(requestDirections.direction)
            val fullFractalImage = fractalGenerator.generateFractal()
            val byteArray = convertImageToByteArray(fullFractalImage)
            call.respondBytes(byteArray, ContentType.Image.JPEG)

        }
    }
}
