package com.example.fractalGenerator

import com.example.fractalGenerator.outil.FractalProperties
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import javax.imageio.ImageIO

class FractalGenerator(val threadPool: ExecutorService) {
    fun generateFractal(fractalProperties: FractalProperties): BufferedImage {
        val fractalImage =
            BufferedImage(fractalProperties.resolution, fractalProperties.resolution, BufferedImage.TYPE_INT_RGB)
        val fractalPixels = mutableListOf<Future<Color>>()

        // width & height equal = 100
        // th = 10 pixels by tile
        // tw = 10
        // nb widht = w/tw -> tasks
        // nb heigh = h/th
        // for (row = 0, rom +=nbwidth)
        // for (col= 0, col += nbheigh...)
        // once in the task -> task(row, col, tw, th, et le reste)

        for (row in 0 until fractalProperties.resolution) {
            for (col in 0 until fractalProperties.resolution) {
                val newCallableFractal = FractalCallable(
                    row,
                    col,
                    fractalProperties.resolution,
                    fractalProperties.scale,
                    fractalProperties.centerX,
                    fractalProperties.centerY,
                    fractalProperties.maxIterations
                )

                val fractalFuture: Future<Color> = threadPool.submit(newCallableFractal)
                fractalPixels.add(fractalFuture)
            }
        }

        for ((index, future) in fractalPixels.withIndex()) {
            val colorResult = future.get()
            val col = index % fractalProperties.resolution
            val row = index / fractalProperties.resolution
            fractalImage.setRGB(col, row, colorResult.rgb)
        }

        return fractalImage
    }

    fun saveFractalasJpeg(fractalProperties: FractalProperties) {
        val outputFile =
            File("fractalTests/fractalimg-r${fractalProperties.resolution}-s${fractalProperties.scale}-newImg.jpg")
        val imageToSave = generateFractal(fractalProperties)
        ImageIO.write(imageToSave, "jpg", outputFile)
    }
}
