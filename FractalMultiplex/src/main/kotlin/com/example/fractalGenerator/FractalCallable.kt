package com.example.fractalGenerator

import com.example.fractalGenerator.outil.FractalTileProperties
import com.example.fractalGenerator.outil.isInMandelbrotSet
import java.awt.Color
import java.util.concurrent.Callable

class FractalCallable(
    private val fractalTileProperties: FractalTileProperties
) : Callable<Color> {
    override fun call(): Color {
        val x =
            (fractalTileProperties.startXcol - fractalTileProperties.pixelsPerTileWidth / 2) * fractalTileProperties.scale / fractalTileProperties.pixelsPerTileHeight + fractalTileProperties.centerX
        val y =
            (fractalTileProperties.startYrow - fractalTileProperties.pixelsPerTileHeight / 2) * fractalTileProperties.scale / fractalTileProperties.pixelsPerTileHeight + fractalTileProperties.centerY
        val color = isInMandelbrotSet(x, y, fractalTileProperties.maxIterations)
        return color
    }
}
