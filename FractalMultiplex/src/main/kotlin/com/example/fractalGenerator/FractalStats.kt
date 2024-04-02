package com.example.fractalGenerator

class FractalStats() {
    var timeThisImage: Long = 0
    var totalTime: Long = 0
    var totalImagesGenerated = 0
    var totalIterations = 0
    var totalTimeTask: Long = 0
    var totalTaskGenerated = 0

    fun addImage(generationTime: Long, iterations: Int) {
        timeThisImage = generationTime
        totalTime += generationTime
        totalImagesGenerated++
        totalIterations = iterations
    }

    fun addTask(generationTime: Long) {
        totalTimeTask += generationTime
        totalTaskGenerated++
    }

    fun averageGenerationTime(): Long {
        return totalTime / totalImagesGenerated
    }

    fun averageGenerationTimeTask(): Long {
        return totalTimeTask / totalTaskGenerated
    }
}
