package ru.gorbunovas.pcmarketplace.api

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import ru.gorbunovas.mappers.*
import ru.gorbunovas.pcmarketplace.api.v1.models.IRequest
import ru.gorbunovas.pcmarketplace.api.v1.models.IResponse
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.helpers.asMkplError
import ru.gorbunovas.pcmarketplace.common.models.*


suspend inline fun <reified Q : IRequest, reified R : IResponse>
        ApplicationCall.controllerHelperV1(command: PcMkplCommand? = null, block: PcMkplContext.() -> Unit) {
    val ctx = PcMkplContext(
        timeStart = Clock.System.now(),
    )
    try {
        val request = receive<Q>()
        ctx.fromTransport(request)
        ctx.block()
        val response = ctx.toTransportAd()
        respond(response)
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = PcMkplState.FAILING
        ctx.errors.add(e.asMkplError())
        ctx.block()
        val response = ctx.toTransportAd()
        respond(response)
    }
}
