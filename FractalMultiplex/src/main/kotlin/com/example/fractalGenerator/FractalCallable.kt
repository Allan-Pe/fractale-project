package com.example.fractalGenerator

import java.awt.Color
import java.util.concurrent.Callable

class FractalCallable(
    val row: Int,
    val col: Int,
    val resolution: Int,
    val scale: Double,
    val centerX: Double,
    val centerY: Double,
    val maxIterations: Int
) : Callable<Color> {
    override fun call(): Color {
        val x = (col - resolution / 2) * scale / resolution + centerX
        val y = (row - resolution / 2) * scale / resolution + centerY
        val color = isInMandelbrotSet(x, y, maxIterations)
        return color
    }
}
