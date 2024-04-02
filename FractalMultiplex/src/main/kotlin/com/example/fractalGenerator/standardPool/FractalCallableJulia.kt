package com.example.fractalGenerator.standardPool

import com.example.fractalGenerator.outil.FractalTileProperties
import java.awt.Color
import java.util.concurrent.Callable

class FractalCallableJulia(
    private val fractalTileProperties: FractalTileProperties
) : Callable<Color> {
    override fun call(): Color {
        val x =
            (fractalTileProperties.col - fractalTileProperties.width / 2) * fractalTileProperties.scale / fractalTileProperties.width + fractalTileProperties.centerX
        val y =
            (fractalTileProperties.row - fractalTileProperties.height / 2) * fractalTileProperties.scale / fractalTileProperties.height + fractalTileProperties.centerY
        val color = juliaColor(x, y, fractalTileProperties.maxIterations)
        return color
    }
}
fun juliaColor(x: Double, y: Double, maxIterations: Int): Color {
    val cReal = -0.7 // Valeur réelle pour le fractal de Julia
    val cImaginary = 0.27015 // Valeur imaginaire pour le fractal de Julia
    var zx = x
    var zy = y
    var iteration = 0

    while (zx * zx + zy * zy < 4 && iteration < maxIterations) {
        val temp = zx * zx - zy * zy + cReal
        zy = 2.0 * zx * zy + cImaginary
        zx = temp
        iteration++
    }

    return Color(iteration % 256, iteration % 256, iteration % 256)
}
