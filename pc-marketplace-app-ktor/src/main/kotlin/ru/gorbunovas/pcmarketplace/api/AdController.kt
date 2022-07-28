package ru.gorbunovas.pcmarketplace.api

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import ru.gorbunovas.mappers.*
import ru.gorbunovas.pcmarketplace.api.v1.models.*
import ru.gorbunovas.pcmarketplace.common.*
import ru.gorbunovas.pcmarketplace.common.helpers.asMkplError
import ru.gorbunovas.pcmarketplace.common.models.PcMkplCommand
import ru.gorbunovas.pcmarketplace.common.models.PcMkplState
import ru.gorbunovas.pcmarketplace.services.*


suspend fun ApplicationCall.createAd(adService: AdService) {
    val ctx = PcMkplContext(
        timeStart = Clock.System.now(),
    )
    try {
        val request = receive<AdCreateRequest>()
        ctx.fromTransport(request)
        adService.createAd(ctx)
        val response = ctx.toTransportAd()
        respond(response)
    } catch (e: Throwable) {
        ctx.command = PcMkplCommand.CREATE
        ctx.state = PcMkplState.FAILING
        ctx.errors.add(e.asMkplError())
        adService.createAd(ctx)
        val response = ctx.toTransportAd()
        respond(response)
    }
}

suspend fun ApplicationCall.readAd(service: AdService) =
    controllerHelperV1<AdReadRequest, AdReadResponse>(PcMkplCommand.READ) {
        service.readAd(this)
    }

suspend fun ApplicationCall.updateAd(service: AdService) =
    controllerHelperV1<AdUpdateRequest, AdUpdateResponse>(PcMkplCommand.UPDATE) {
        service.updateAd(this)
    }

suspend fun ApplicationCall.deleteAd(service: AdService) =
    controllerHelperV1<AdDeleteRequest, AdDeleteResponse>(PcMkplCommand.DELETE) {
        service.deleteAd(this)
    }

suspend fun ApplicationCall.searchAd(adService: AdService) =
    controllerHelperV1<AdSearchRequest, AdSearchResponse>(PcMkplCommand.SEARCH) {
        adService.searchAd(this)
    }
