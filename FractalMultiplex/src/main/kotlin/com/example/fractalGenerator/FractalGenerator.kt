package com.example.fractalGenerator

import com.example.fractalGenerator.outil.FractalProperties
import com.example.fractalGenerator.outil.FractalTileProperties
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import javax.imageio.ImageIO
import kotlin.math.log

class FractalGenerator(val threadPool: ExecutorService) {
    fun generateFractal(fractalProperties: FractalProperties): BufferedImage {
        val fractalImage =
            BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
        val fractalTiles = mutableListOf<Future<Color>>()

        val width = 100
        val height = 100
        val pixelsPerTileWidth = 10
        val pixelsPerTileHeight = 10
        val numberOfTilesWidth = width / pixelsPerTileWidth
        val numberOfTilesHeight = height / pixelsPerTileHeight

        for (row in 0 until numberOfTilesHeight) {
            val startYrow = row * pixelsPerTileHeight
            for (col in 0 until numberOfTilesWidth) {
                val startXcol = col * pixelsPerTileWidth
                val newCallableFractal = FractalCallable(
                    FractalTileProperties(
                        startYrow,
                        startXcol,
                        0.0,
                        0.0,
                        4.0,
                        pixelsPerTileWidth,
                        pixelsPerTileHeight,
                        100
                    )
                )

                val fractalFuture: Future<Color> = threadPool.submit(newCallableFractal)
                fractalTiles.add(fractalFuture)
            }
        }

        for ((index, future) in fractalTiles.withIndex()) {
            val colorResult = future.get()
            // Calculate the position in the final image for the current tile
            val startX = (index % numberOfTilesWidth) * pixelsPerTileWidth
            val startY = (index / numberOfTilesWidth) * pixelsPerTileHeight

            println("startX value: $startX, startY value: $startY")

            // Set the color of each pixel in the tile to the corresponding position in the final image
            for (x in 0 until pixelsPerTileWidth) {
                for (y in 0 until pixelsPerTileHeight) {
                    fractalImage.setRGB(startX + x, startY + y, colorResult.rgb)
                }
            }
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

// Pseudo code
// width & height equal = 100
// th = 10 pixels by tile
// tw = 10
// nb widht = w/tw -> tasks
// nb heigh = h/th
// for (row = 0, row +=nbwidth)
// for (col= 0, col += nbheigh...)
// once in the task -> task(row, col, tw, th, et le reste)



//        for ((index, future) in fractalPixels.withIndex()) {
//            val colorResult = future.get()
//            val col = (index % numberOfTilesWidth) * pixelsPerTileWidth
//            val row = (index / numberOfTilesWidth) * pixelsPerTileHeight
//            fractalImage.setRGB(col, row, colorResult.rgb)
//        }
