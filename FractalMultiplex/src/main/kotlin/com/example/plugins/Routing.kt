package com.example.plugins

import com.example.fractalGenerator.FractalGenerator
import com.example.fractalGenerator.outil.FractalProperties
import com.example.fractalGenerator.outil.convertImageToByteArray
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json


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
    }
}
