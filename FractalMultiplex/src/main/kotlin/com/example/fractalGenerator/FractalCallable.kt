package com.example.fractalGenerator

import com.example.fractalGenerator.outil.FractalTileProperties
import com.example.fractalGenerator.outil.isInMandelbrotSet
import java.awt.Color
import java.util.concurrent.Callable

class FractalCallable(
    private val fractalTaileProperties: FractalTileProperties
) : Callable<Color> {
    override fun call(): Color {
        val x =
            (fractalTaileProperties.startXcol - fractalTaileProperties.pixelsPerTileWidth / 2) * fractalTaileProperties.scale / fractalTaileProperties.pixelsPerTileHeight + fractalTaileProperties.centerX
        val y =
            (fractalTaileProperties.startYrow - fractalTaileProperties.pixelsPerTileHeight / 2) * fractalTaileProperties.scale / fractalTaileProperties.pixelsPerTileHeight + fractalTaileProperties.centerY
        val color = isInMandelbrotSet(x, y, fractalTaileProperties.maxIterations)
        return color
    }
}
