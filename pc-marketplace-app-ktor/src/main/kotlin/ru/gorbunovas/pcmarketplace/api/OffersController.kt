package ru.gorbunovas.pcmarketplace.api

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.gorbunovas.mappers.*
import ru.gorbunovas.pcmarketplace.api.v1.models.AdOffersRequest
import ru.gorbunovas.pcmarketplace.api.v1.models.AdOffersResponse
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplCommand
import ru.gorbunovas.pcmarketplace.services.AdService
import ru.gorbunovas.pcmarketplace.services.OfferService


suspend fun ApplicationCall.offersAd(service: AdService) =
    controllerHelperV1<AdOffersRequest, AdOffersResponse>(PcMkplCommand.OFFERS) {
        service.searchOffers(this)
    }