package com.creatorstool.shaketrace.utils

/**
 * @Created by akash on 7/20/2023.
 * Know more about author on https://akash.cloudemy.in
 */
sealed class DataHelper<T>(val data: T? = null, val msg: String = "") {

    class Success<T>(data: T) : DataHelper<T>(data = data)
    class Error<T>(msg: String) : DataHelper<T>(msg = msg)
    class Loading<T>() : DataHelper<T>()
}