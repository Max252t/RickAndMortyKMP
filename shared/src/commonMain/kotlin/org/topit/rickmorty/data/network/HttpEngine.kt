package org.topit.rickmorty.data.network

import io.ktor.client.engine.HttpClientEngine

internal expect fun createHttpEngine(): HttpClientEngine
