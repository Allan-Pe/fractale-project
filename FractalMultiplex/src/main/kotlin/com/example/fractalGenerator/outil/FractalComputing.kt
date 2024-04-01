package com.example.fractalGenerator.outil

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

// This script contains only pure functions
fun getColor(iterations: Int, maxIterations: Int): Color {
    return when (iterations) {
        in 0 until maxIterations -> Color.getHSBColor(iterations.toFloat() / maxIterations, 1f, 1f)
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

    println(currentIteration)

    val color = getColor(currentIteration, maxIterations)
    return color
}

fun convertImageToByteArray(image: BufferedImage): ByteArray {
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(image, "jpg", outputStream)
    return outputStream.toByteArray()
}
