package com.example.fractalGenerator.outil

import kotlinx.serialization.Serializable

@Serializable
data class FractalProperties (
    var centerX: Double,
    var centerY: Double,
    var scale: Double,
    var width: Int,
    var height: Int,
    var maxIterations: Int
)