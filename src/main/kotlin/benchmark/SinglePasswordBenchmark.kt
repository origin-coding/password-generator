@file:Suppress("Unused")

package com.origincoding.benchmark

import com.origincoding.CharacterOptions
import com.origincoding.generatePassword
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Mode
import kotlinx.benchmark.State
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Scope

@BenchmarkMode(Mode.Throughput, Mode.AverageTime)
@State(Scope.Benchmark)
internal class SinglePasswordBenchmark {
    companion object {
        val characterOptions = CharacterOptions()
    }

    @Benchmark
    fun generateSinglePassword(): String = generatePassword(characterOptions = characterOptions)
}