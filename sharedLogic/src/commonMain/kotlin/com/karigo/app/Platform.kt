package com.karigo.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform