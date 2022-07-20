package ru.gorbunovas.pcmarketplace.services;

import ru.gorbunovas.pcmarketplace.common.*
import ru.gorbunovas.pcmarketplace.common.models.PcMkplError
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs
import ru.gorbunovas.pcmarketplace.stubs.PcStubs

class OfferService {

    fun searchOffers(context: PcMkplContext, buildError: () -> PcMkplError): PcMkplContext {
        val request = context.adRequest

        return when (context.stubCase) {
            PcMkplStubs.SUCCESS -> context.successResponse {
                adsResponse.addAll(PcStubs.getModels().onEach { it.id = request.id })
            }
            else -> {
                context.errorResponse(buildError) {
                    it.copy(field = "ad.id", message = notFoundError(request.id.asString()))
                }
            }
        }
    }
}
