package ru.gorbunovas.pcmarketplace.api

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.gorbunovas.mappers.*
import ru.gorbunovas.pcmarketplace.api.v1.models.*
import ru.gorbunovas.pcmarketplace.common.*
import ru.gorbunovas.pcmarketplace.services.*

suspend fun ApplicationCall.createAd(adService: AdService) {
    val createAdRequest = receive<AdCreateRequest>()
    respond(
        PcMkplContext().apply { fromTransport(createAdRequest) }.let {
            adService.createAd(it)
        }.toTransportCreate()
    )
}

suspend fun ApplicationCall.readAd(adService: AdService) {
    val readAdRequest = receive<AdReadRequest>()
    respond(
        PcMkplContext().apply { fromTransport(readAdRequest) }.let {
            adService.readAd(it, ::buildError)
        }.toTransportRead()
    )
}

suspend fun ApplicationCall.updateAd(adService: AdService) {
    val updateAdRequest = receive<AdUpdateRequest>()
    respond(
        PcMkplContext().apply { fromTransport(updateAdRequest) }.let {
            adService.updateAd(it, ::buildError)
        }.toTransportUpdate()
    )
}

suspend fun ApplicationCall.deleteAd(adService: AdService) {
    val deleteAdRequest = receive<AdDeleteRequest>()
    respond(
        PcMkplContext().apply { fromTransport(deleteAdRequest) }.let {
            adService.deleteAd(it, ::buildError)
        }.toTransportDelete()
    )
}

suspend fun ApplicationCall.searchAd(adService: AdService) {
    val searchAdRequest = receive<AdSearchRequest>()
    respond(
        PcMkplContext().apply { fromTransport(searchAdRequest) }.let {
            adService.searchAd(it, ::buildError)
        }.toTransportSearch()
    )
}
