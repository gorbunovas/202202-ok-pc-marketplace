package ru.gorbunovas.pcmarketplace.api

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.gorbunovas.mappers.*
import ru.gorbunovas.pcmarketplace.api.v1.models.AdOffersRequest
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.services.OfferService


suspend fun ApplicationCall.offersAd(offerService: OfferService) {
    val offersAdRequest = receive<AdOffersRequest>()
    respond(
        PcMkplContext().apply { fromTransport(offersAdRequest) }.let {
            offerService.searchOffers(it, ::buildError)
        }.toTransportOffers()
    )
}
