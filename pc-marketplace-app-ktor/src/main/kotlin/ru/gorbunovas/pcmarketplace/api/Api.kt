package ru.gorbunovas.pcmarketplace.api

import io.ktor.server.routing.*
import ru.gorbunovas.pcmarketplace.api.v1.*
import ru.gorbunovas.pcmarketplace.services.*


internal fun Routing.v1(adService: AdService, offerService: OfferService) {
    route("v1") {
        pcAd(adService)
        pcOffer(offerService)
    }
}
