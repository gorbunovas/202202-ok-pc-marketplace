package ru.gorbunovas.pcmarketplace.api

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.gorbunovas.pcmarketplace.services.*


fun Route.pcAd(PcAdServices: AdService) {
    route("ad") {
        post("create") {
            call.createAd(PcAdServices)
        }
        post("read") {
            call.readAd(PcAdServices)
        }
        post("update") {
            call.updateAd(PcAdServices)
        }
        post("delete") {
            call.deleteAd(PcAdServices)
        }
        post("search") {
            call.searchAd(PcAdServices)
        }
    }
}

fun Route.pcOffer(offerService: OfferService) {
    route("ad") {
        post("offers") {
            call.offersAd(offerService)
        }
    }
}