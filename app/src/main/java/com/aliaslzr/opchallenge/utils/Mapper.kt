package com.aliaslzr.opchallenge.utils

interface Mapper<I, O, AA> {
    fun transform(
        input: I,
        additionalArgs: AA? = null,
    ): O
}