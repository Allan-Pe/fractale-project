package com.example.fractalGenerator

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import javax.imageio.ImageIO

class FractalGenerator(val threadPool: ExecutorService) {
    val fractalPixels = mutableListOf<Future<Color>>()

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
    var resolution = 1000
        set(value) {
            field = value
        }

    // With this value we set the gradiant outside the fractal
    var maxIterations = 100
        set(value) {
            field = value
        }

    var value: Float = 1f

    val stats = FractalStats()

    private val image = BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB)
    fun generateFractal(): BufferedImage {
        val start = System.currentTimeMillis()
        val fractalPixels = mutableListOf<Future<Color>>()

        for (row in 0 until resolution) {
            for (col in 0 until resolution) {
                val startTask = System.currentTimeMillis()
                val newCallableFractal = FractalCallable(row, col, resolution, scale, centerX, centerY, maxIterations)
                val fractalFuture: Future<Color> = threadPool.submit(newCallableFractal)
                fractalPixels.add(fractalFuture)
                val endTimeTask = System.currentTimeMillis()
                val generationTimeTask = endTimeTask - start
                stats.addTask(generationTimeTask)
            }
        }

        for ((index, future) in fractalPixels.withIndex()) {
            val colorResult = future.get()
            val col = index % resolution
            val row = index / resolution
            image.setRGB(col, row, colorResult.rgb)
        }
        val endTime = System.currentTimeMillis()
        val generationTime = endTime - start
        stats.addImage(generationTime, resolution)

        println("TIME THIS IMG : ${stats.timeThisImage}ms")
        println("AVERAGE TIME : ${stats.averageGenerationTime()}ms")
        println("TOTAL IMAGES GENERATED : ${stats.totalImagesGenerated}")
        println("TOTAL ITERATION : ${stats.totalIterations}")
        println("==========================================================")
        println("TOTAL TIME TASK : ${stats.totalTimeTask}ms")
        println("TOTAL TASK GENERATED : ${stats.totalTaskGenerated}")
        println("AVERAGE TIME : ${stats.averageGenerationTimeTask()}ms")

        return image
    }

    fun updateFractalPosition(direction: String) {
        if (direction == "zoomin") {
            value /= 2;
        } else if (direction == "zoomout") {
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
