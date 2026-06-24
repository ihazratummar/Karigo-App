package com.karigojob.share.utils


/**
 * @author hazratummar
 * Created on 23/06/26
 */

fun String.toInitials(): String {
    return this.trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.first().uppercaseChar() }
        .joinToString("")
}

fun String?.firstName(): String {
    return this
        ?.trim()
        ?.takeIf { it.isNotBlank() }
        ?.substringBefore(" ")
        ?: ""
}

fun Double.formatNumber() : String {
    return if (this % 1.0 == 0.0) {
        this.toInt().toString()
    }else{
        this.toString()
    }
}