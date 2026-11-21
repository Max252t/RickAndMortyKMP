package org.topit.rickmorty.domain.errors

sealed class LoadException : Exception() {
    class NoConnection : LoadException()
    class Timeout : LoadException()
    class NotFound : LoadException()
    class Server(val statusCode: Int) : LoadException()
    class BadResponse : LoadException()
}
