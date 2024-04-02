package com.example.fractalGenerator

import com.example.fractalGenerator.outil.FractalProperties
import com.example.fractalGenerator.outil.FractalTileProperties
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import javax.imageio.ImageIO

class FractalGenerator(private val threadPool: ExecutorService) {
    fun generateFractal(fractalProperties: FractalProperties): BufferedImage {
        val fractalImage =
            BufferedImage(fractalProperties.width, fractalProperties.height, BufferedImage.TYPE_INT_RGB)
        val fractalTiles = mutableListOf<Future<Color>>()

        for (row in 0 until fractalProperties.width) {
            for (col in 0 until fractalProperties.height) {
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
            }
        }

        for ((index, future) in fractalTiles.withIndex()) {
            val colorResult = future.get()
            // Calculate the position in the final image for the current tile
            val startX = index % fractalProperties.height
            val startY = index / fractalProperties.width
            fractalImage.setRGB(startX, startY , colorResult.rgb)
        }

        return fractalImage
    }

    fun saveFractalasJpeg(fractalProperties: FractalProperties) {
        val outputFile =
            File("fractalTests/fractalimg-r${fractalProperties.width}-s${fractalProperties.scale}-newImg.jpg")
        val imageToSave = generateFractal(fractalProperties)
        ImageIO.write(imageToSave, "jpg", outputFile)
    }
}
