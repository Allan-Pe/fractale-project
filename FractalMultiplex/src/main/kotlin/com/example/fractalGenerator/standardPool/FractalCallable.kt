package com.example.fractalGenerator.standardPool

import com.example.fractalGenerator.outil.FractalTileProperties
import com.example.fractalGenerator.outil.isInMandelbrotSet
import java.awt.Color
import java.util.concurrent.Callable

class FractalCallable(
    private val fractalTileProperties: FractalTileProperties
) : Callable<Color> {
    override fun call(): Color {
        val x =
            (fractalTileProperties.col - fractalTileProperties.height / 2) * fractalTileProperties.scale / fractalTileProperties.height + fractalTileProperties.centerX
        val y =
            (fractalTileProperties.row - fractalTileProperties.height / 2) * fractalTileProperties.scale / fractalTileProperties.height + fractalTileProperties.centerY
        val color = isInMandelbrotSet(x, y, fractalTileProperties.maxIterations)
        return color
    }
}
