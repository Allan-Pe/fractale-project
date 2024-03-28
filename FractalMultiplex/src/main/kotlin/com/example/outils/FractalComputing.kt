package com.example.outils

import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

// This script contains only pure functions
// This function checks if a given coordinate is inside the generated fractal
fun isInMandelbrotSet(x: Double, y: Double, maxIterations: Int): Int {
    // Represents the real numbers, on the x coordinate
    var real = 0.0
    var imag = 0.0
    var iter = 0

    while (iter < maxIterations && real * real + imag * imag < 4) {
        val tempReal = real * real - imag * imag + x
        imag = 2 * real * imag + y
        real = tempReal
        iter++
    }

    return iter
}

fun convertImageToByteArray(image: BufferedImage): ByteArray {
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(image, "jpg", outputStream)
    return outputStream.toByteArray()
}