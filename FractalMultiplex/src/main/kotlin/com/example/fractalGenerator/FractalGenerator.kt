package com.example.fractalGenerator

import com.example.fractalGenerator.outil.FractalCallProperties
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
            BufferedImage(fractalProperties.width, fractalProperties.height, BufferedImage.TYPE_INT_RGB)
        val fractalPixels = mutableListOf<Future<Color>>()

        val width = fractalProperties.width
        val height = fractalProperties.height
        val pixelsPerTileWidth = 10
        val pixelsPerTileHeight = 10
        val numberOfTilesWidth = width / pixelsPerTileWidth
        val numberOfTilesHeight = height / pixelsPerTileHeight

//        println("Number of tiles: $numberOfTilesWidth x $numberOfTilesHeight")

        for (row in 0 until numberOfTilesHeight) {
            for (col in 0 until numberOfTilesWidth) {
                val startXcol = col * pixelsPerTileWidth
                val startYrow = row * pixelsPerTileHeight

//                println("Tile ($col, $row): StartX=$startXcol, StartY=$startYrow")

                val newCallableFractal = FractalCallable(
                    FractalCallProperties(
                        startXcol,
                        startYrow,
                        fractalProperties.centerX,
                        fractalProperties.centerY,
                        fractalProperties.scale,
                        pixelsPerTileWidth,
                        pixelsPerTileHeight,
                        fractalProperties.maxIterations
                    )
                )

                val fractalFuture: Future<Color> = threadPool.submit(newCallableFractal)
                fractalPixels.add(fractalFuture)
            }
        }

        for ((index, future) in fractalPixels.withIndex()) {
            val colorResult = future.get()

            // Calculate the position in the final image for the current tile
            val startX = (index % numberOfTilesWidth) * pixelsPerTileWidth
            val startY = (index / numberOfTilesWidth) * pixelsPerTileHeight

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