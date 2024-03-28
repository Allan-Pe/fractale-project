package com.example.fractalGenerator

import com.example.outils.isInMandelbrotSet
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class FractalGenerator {
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

    var value : Float = 1f;

    private val image = BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB)
    fun generateFractal(): BufferedImage {

        while (true) {
            for (row in 0 until resolution) {
                for (col in 0 until resolution) {
                    val x = (col - resolution / 2) * scale / resolution + centerX
                    val y = (row - resolution / 2) * scale / resolution + centerY
                    val iterations = (isInMandelbrotSet(x, y, maxIterations))
                    val color = getColor(iterations)
                    image.setRGB(col, row, color.rgb)
                }
            }

            return image
        }
    }

    fun getColor(iterations: Int): Color {
        return when (iterations) {
            in 0 until maxIterations -> Color.getHSBColor(iterations.toFloat() / maxIterations, 1f, 1f)
            else -> Color.BLACK
        }
    }

    fun updateFractalPosition(direction: String) {
        if (direction == "zoomin"){
            value /= 2;
        } else if (direction == "zoomout"){
            value *= 2;
        }
        when (direction) {
            "left" -> this.centerX += value
            "right" -> this.centerX -= value
            "up" -> this.centerY += value
            "down" -> this.centerY -= value
            "zoomin" -> this.scale /= 2
            "zoomout" -> this.scale *= 2
            else -> println("No valid direction !")
        }
    }

    fun saveFractalasJpeg() {
        val outputFile = File("fractalTests/fractalimg-r$resolution-s$scale-newImg.jpg")
        ImageIO.write(image, "jpg", outputFile)
    }
}