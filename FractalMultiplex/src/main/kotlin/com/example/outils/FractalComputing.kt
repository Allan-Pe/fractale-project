package com.example.outils

import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

// This script contains only pure functions

// This function checks if a given coordinate is inside the generated fractal
fun isInMandelbrotSet(x: Double, y: Double, maxIterations: Int): Boolean {
    // Represents the real numbers, on the x coordinate
    var real = x
    // Represents the imaginary numbers, on the y coordinate
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

fun convertImageToByteArray(image: BufferedImage): ByteArray {
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(image, "jpg", outputStream)
    return outputStream.toByteArray()
}