package com.example.fractalGenerator

import com.example.outils.isInMandelbrotSet
import java.awt.Color
import java.awt.image.BufferedImage

class FractalCallable() {
    // Add to move right, subtract to move left
    var centerX = 0.0
        set(value) {
            field = value
        }
    // Add to move up, subtract to move down
    var centerY = 0.0
        set(value) {
            field = value
        }
    // /2 to zoom in, *2 to zoom out
    var scale = 4.0
        set(value) {
            field = value
        }
    var resolution = 1080
        set(value) {
            field = value
        }

    // With this value we set the gradiant outside the fractal
    var maxIterations = 1000
        set(value) {
            field = value
        }

    val image = BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB)
    fun generateFractal(): BufferedImage {

        while (true) {
            for (row in 0 until resolution) {
                for (col in 0 until resolution) {
                    val x = (col - resolution / 2) * scale / resolution + centerX
                    val y = (row - resolution / 2) * scale / resolution + centerY
                    val color = if (isInMandelbrotSet(x, y, maxIterations)) Color.BLACK else Color.WHITE
                    image.setRGB(col, row, color.rgb)
                }
            }

            return image
        }
    }

//    override fun call(): Int? {
//        TODO("Not yet implemented")
//    }
}

//            val outputFile = File("fractalTests/fractalimg-r$resolution-s$scale-$newName.jpg")
//            ImageIO.write(image, "jpg", outputFile)
