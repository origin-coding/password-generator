@file:Suppress("Unused")

package com.origincoding.benchmark

import com.origincoding.CharacterOptions
import com.origincoding.generatePassword
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.BenchmarkMode
import kotlinx.benchmark.Mode
import kotlinx.benchmark.State
import org.openjdk.jmh.annotations.Scope

@BenchmarkMode(Mode.Throughput, Mode.AverageTime)
@State(Scope.Benchmark)
internal class MultiplePasswordBenchmark {
    companion object {
        val characterOptions = CharacterOptions()
    }

    @Benchmark
    fun generateMultiplePassword(): List<String> =
        generatePassword(characterOptions = characterOptions, count = 20_0000)
}