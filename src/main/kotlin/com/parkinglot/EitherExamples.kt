package com.parkinglot

import arrow.core.Either
import arrow.core.getOrElse

fun EitherExamples() {
    val result: Either<String, Int> = Either.Right(1)

    // Either :: when
    when (result) {
        is Either.Left -> 0
        is Either.Right -> result.value
    } // 1

    // Either :: fold
    result.fold(
        { 399 },
        { it }
    ) // 1

    // Either :: map
    result.map { it + 1 } // Right(2)

    // Either :: getOrElse
    result.getOrElse { 0 } // 1
}
