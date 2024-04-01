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
            (fractalCallProperties.startXcol - fractalCallProperties.pixelsPerTileWidth / 2) * fractalCallProperties.scale / fractalCallProperties.pixelsPerTileHeight + fractalCallProperties.centerX
        val y =
            (fractalCallProperties.startYrow - fractalCallProperties.pixelsPerTileHeight / 2) * fractalCallProperties.scale / fractalCallProperties.pixelsPerTileHeight + fractalCallProperties.centerY


        val color = isInMandelbrotSet(x, y, fractalCallProperties.maxIterations)
        return color
    }
}
