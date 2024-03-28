package com.example.plugins

import com.example.fractalGenerator.FractalCallable
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
    val right: Boolean,
    val left: Boolean,
    val up: Boolean,
    val down: Boolean,
    val zoomin: Boolean,
    val zoomout: Boolean
)


fun Application.configureRouting() {
    val fractalGenerator = FractalCallable()
    routing {
        get("/generatefractal") {
            val fullFractalImage = fractalGenerator.generateFractal()
            val byteArray = convertImageToByteArray(fullFractalImage)
            call.respondBytes(byteArray, ContentType.Image.JPEG)
        }

        post("/generatefractal") {
            // TODO !!
            // Interpret dto
            val requestDirections: FractalMovementDto = Json.decodeFromString(call.receiveText())
            // Set values to move in fractal
            // Regenerate fractal img
            // send new byte array
        }
    }
}
