package com.example.fractalGenerator.standardPool

import com.example.fractalGenerator.FractalStats
import com.example.fractalGenerator.outil.FractalProperties
import com.example.fractalGenerator.outil.FractalTileProperties
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.locks.ReentrantLock
import javax.imageio.ImageIO

class FractalGenerator(private val threadPool: ExecutorService) {
    val lock = ReentrantLock()
    val stats = FractalStats()
    fun generateFractal(fractalProperties: FractalProperties): BufferedImage {
        val start = System.currentTimeMillis()
        lock.lock()
        val fractalImage =
            BufferedImage(fractalProperties.width, fractalProperties.height, BufferedImage.TYPE_INT_RGB)
        val fractalTiles = mutableListOf<Future<Color>>()

        for (row in 0 until fractalProperties.width) {
            for (col in 0 until fractalProperties.height) {
                val startTask = System.currentTimeMillis()
                val newCallableFractal = FractalCallable(
                    FractalTileProperties(
                        row,
                        col,
                        fractalProperties.width,
                        fractalProperties.height,
                        fractalProperties.centerX,
                        fractalProperties.centerY,
                        fractalProperties.scale,
                        fractalProperties.maxIterations
                    )
                )

                val fractalFuture: Future<Color> = threadPool.submit(newCallableFractal)
                fractalTiles.add(fractalFuture)
                val endTimeTask = System.currentTimeMillis()
                val generationTimeTask = endTimeTask - start
                stats.addTask(generationTimeTask)
            }
        }

        for ((index, future) in fractalTiles.withIndex()) {
            val colorResult = future.get()
            // Calculate the position in the final image for the current tile
            val startX = index % fractalProperties.height
            val startY = index / fractalProperties.width
            fractalImage.setRGB(startX, startY , colorResult.rgb)
        }
        lock.unlock()
        val endTime = System.currentTimeMillis()
        val generationTime = endTime - start
        stats.addImage(generationTime, fractalProperties.width)

        return fractalImage
    }

    fun saveFractalasJpeg(fractalProperties: FractalProperties) {
        val outputFile =
            File("fractalTests/fractalimg-r${fractalProperties.width}-s${fractalProperties.scale}-newImg.jpg")
        val imageToSave = generateFractal(fractalProperties)
        ImageIO.write(imageToSave, "jpg", outputFile)
    }
}
