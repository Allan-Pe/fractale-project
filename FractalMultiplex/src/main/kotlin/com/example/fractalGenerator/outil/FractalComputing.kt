package com.example.fractalGenerator.outil

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

// This script contains only pure functions
fun getColor(iterations: Int, maxIterations: Int): Color {
    return when (iterations) {
        in 0 until maxIterations -> {
            val hueStart = 1.75f
            val hueEnd = 7.2f
            val hue = hueStart + (hueEnd - hueStart) * (iterations.toFloat() / maxIterations)
            val saturation = 7.9f
            val brightness = 9.3f
            Color.getHSBColor(hue, saturation, brightness)
        }
        else -> Color.BLACK
    }
}

// This function checks if a given coordinate is inside the generated fractal
fun isInMandelbrotSet(x: Double, y: Double, maxIterations: Int): Color {
    var real = 0.0
    var imag = 0.0
    var currentIteration = 0

    while (currentIteration < maxIterations && real * real + imag * imag < 4) {
        val tempReal = real * real - imag * imag + x
        imag = 2 * real * imag + y
        real = tempReal
        currentIteration++
    }

    val color = getColor(currentIteration, maxIterations)
    return color
}

fun convertImageToByteArray(image: BufferedImage): ByteArray {
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(image, "jpg", outputStream)
    return outputStream.toByteArray()
}

fun juliaColor(x: Double, y: Double, maxIterations: Int): Color {
    val cReal = -0.7
    val cImaginary = 0.27015
    var zx = x
    var zy = y
    var iteration = 0

    while (zx * zx + zy * zy < 4 && iteration < maxIterations) {
        val temp = zx * zx - zy * zy + cReal
        zy = 2.0 * zx * zy + cImaginary
        zx = temp
        iteration++
    }

    return Color(iteration % 256, iteration % 256, iteration % 256)
}
