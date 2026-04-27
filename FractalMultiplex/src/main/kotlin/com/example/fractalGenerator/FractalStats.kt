package com.example.fractalGenerator

class FractalStats() {
    var timeThisImage: Long = 0
    var totalTime: Long = 0
    var totalImagesGenerated = 0
    var totalIterations = 0
    var totalTimeTask: Double = 0.0
    var totalTaskGenerated = 0

    fun addImage(generationTime: Long, iterations: Int) {
        timeThisImage = generationTime
        totalTime += generationTime
        totalImagesGenerated++
        totalIterations = iterations
    }

    fun addTask(generationTime: Double) {
        totalTimeTask += generationTime
        totalTaskGenerated++
    }

    fun averageGenerationTime(): Long {
        if (totalImagesGenerated == 0) return 0
        return totalTime / totalImagesGenerated
    }

    fun averageGenerationTimeTask(): Double {
        if (totalTaskGenerated == 0) return 0.0
        return totalTimeTask / totalTaskGenerated
    }
}
