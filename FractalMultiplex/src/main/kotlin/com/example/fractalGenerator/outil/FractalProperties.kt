package com.example.fractalGenerator.outil

import kotlinx.serialization.Serializable

@Serializable
data class FractalProperties (
    var centerX: Double = 0.0,
    var centerY: Double = 0.0,
    var scale: Double = 4.0,
    var resolution: Int = 1000,
    var maxIterations: Int = 100,
)