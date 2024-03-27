package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

fun isInMandelbrotSet(x: Double, y: Double, maxIterations: Int): Boolean {
    var real = x
    var imag = y
    repeat(maxIterations) {
        val realTemp = real * real - imag * imag + x
        imag = 2 * real * imag + y
        real = realTemp
        if (real * real + imag * imag > 4) {
            return false
        }
    }

    return true
}

fun generateFractal() {
    val scanner = Scanner(System.`in`)
    var centerX = 0.0
    var centerY = 0.0
    var scale = 4.0
    val resolution = 1080
    // With this value we set the gradiant outside the fractal
    val maxIterations = 1000

    val image = BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB)
    var newName = 0

    while (true) {
        for (row in 0 until resolution) {
            for (col in 0 until resolution) {
                val x = (col - resolution / 2) * scale / resolution + centerX
                val y = (row - resolution / 2) * scale / resolution + centerY

//                if (isInMandelbrotSet(x, y, maxIterations)) {
//                    print("*")
//                } else {
//                    print(" ")
//                }

                val color = if (isInMandelbrotSet(x, y, maxIterations)) Color.BLACK else Color.WHITE
                image.setRGB(col, row, color.rgb)
            }

            println()
        }

        newName++
        val outputFile = File("fractalTests/fractalimg-r$resolution-s$scale-$newName.jpg")
        ImageIO.write(image, "jpg", outputFile)

        println("Enter navigation command: (w: up, s: down, a: left, d: right, z: zoom in, x: zoom out, q: quit)")
        val input = scanner.next()
        when (input) {
            "w" -> centerY += scale / resolution
            "s" -> centerY -= scale / resolution
            "a" -> centerX -= scale / resolution
            "d" -> centerX += scale / resolution
            "z" -> scale /= 2
            "x" -> scale *= 2
            "q" -> return
        }

        println("\u001b[H\u001b[2J") // Clears the console
    }
}

fun Application.configureRouting() {
    routing {
        get("/") {
            generateFractal()
            call.respondText("Hello World! And look at the console!")
        }
    }
}
