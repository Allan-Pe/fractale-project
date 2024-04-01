package com.example.fractalGenerator

import com.example.fractalGenerator.outil.FractalCallProperties
import com.example.fractalGenerator.outil.isInMandelbrotSet
import java.awt.Color
import java.util.concurrent.Callable

class FractalCallable(
    private val fractalCallProperties: FractalCallProperties
) : Callable<Color> {
    override fun call(): Color {
        val x =
            (fractalCallProperties.col - fractalCallProperties.pixelsPerTileWidth / 2) * fractalCallProperties.scale / fractalCallProperties.pixelsPerTileHeight + fractalCallProperties.centerX
        val y =
            (fractalCallProperties.row - fractalCallProperties.pixelsPerTileHeight / 2) * fractalCallProperties.scale / fractalCallProperties.pixelsPerTileHeight + fractalCallProperties.centerY
        val color = isInMandelbrotSet(x, y, fractalCallProperties.maxIterations)
//        println(color)
        return color
    }
}

// val x = (col - resolution / 2) * scale / resolution + centerX
// val y = (row - resolution / 2) * scale / resolution + centerY
// val color = isInMandelbrotSet(x, y, maxIterations)
// return color