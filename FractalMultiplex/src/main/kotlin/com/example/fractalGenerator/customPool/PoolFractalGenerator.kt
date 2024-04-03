package com.example.fractalGenerator.customPool

import com.example.fractalGenerator.FractalStats
import com.example.fractalGenerator.outil.FractalProperties
import com.example.fractalGenerator.outil.FractalTileProperties
import com.example.threadPool.MyExecutor
import com.example.threadPool.MyThreadPoolExecutor
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.concurrent.Future

class PoolFractalGenerator {
    val customThreadPool: MyExecutor<Color> = MyThreadPoolExecutor()
    val stats = FractalStats()
    fun generateFractal(fractalProperties: FractalProperties): BufferedImage {
        val start = System.currentTimeMillis()
        val fractalImage =
            BufferedImage(fractalProperties.width, fractalProperties.height, BufferedImage.TYPE_INT_RGB)
        val fractalTiles = mutableListOf<Future<Color>>()

        for (row in 0 until fractalProperties.width) {
            for (col in 0 until fractalProperties.height) {
                val startTask = System.nanoTime()


                val newCallableFractal = PoolFractalCallable(
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

                val fractalFuture: Future<Color> = customThreadPool.submit(newCallableFractal)

                fractalTiles.add(fractalFuture)
                val endTimeTask = System.nanoTime()
                val generationTimeTask = (endTimeTask - startTask) / 1000000.0
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

        val endTime = System.currentTimeMillis()
        val generationTime = endTime - start
        stats.addImage(generationTime, fractalProperties.width)
        return fractalImage
    }
}
