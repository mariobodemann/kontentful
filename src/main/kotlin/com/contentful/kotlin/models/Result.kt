package com.contentful.kotlin.models

public class Result<T>(private val value: T?, private val throwable: Throwable?) {
    public val success: Boolean = throwable == null
    public val failure: Boolean = !success

    companion object {
        public fun <T> succeed(value: T) = Result(value, null)
        public fun <T> fail(throwable: Throwable) = Result<T>(null, throwable)
    }

    public fun getOrNull(): T? = value
    public fun getOrThrow(): T = value ?: throw IllegalStateException("Value is null.", throwable)
}