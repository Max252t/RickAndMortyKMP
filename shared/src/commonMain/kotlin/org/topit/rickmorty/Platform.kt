package org.topit.rickmorty

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform