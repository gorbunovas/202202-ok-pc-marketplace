package ru.gorbunovas.pcmarketplace.services

import ru.gorbunovas.pcmarketplace.common.*
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.stubs.*
import ru.gorbunovas.pcmarketplace.stubs.ru.gorbunovas.pcmarketplace.stubs.PcStubs


class AdService {
    fun createAd(mpContext: PcMkplContext): PcMkplContext {
        val response = when (mpContext.workMode) {
            PcMkplWorkMode.PROD -> TODO()
            PcMkplWorkMode.TEST -> mpContext.adRequest
            PcMkplWorkMode.STUB -> PcStubs.getModel()
        }
        return mpContext.successResponse {
            adResponse = response
        }
    }

    fun readAd(mpContext: PcMkplContext, buildError: () -> PcMkplError): PcMkplContext {
        val requestedId = mpContext.adRequest.id

        return when (mpContext.stubCase) {
            PcMkplStubs.SUCCESS -> mpContext.successResponse {
                adResponse = PcStubs.getModel().apply { id = requestedId }
            }
            else -> mpContext.errorResponse(buildError) {
                it.copy(field = "ad.id", message = notFoundError(requestedId.asString()))
            }
        }
    }

    fun updateAd(context: PcMkplContext, buildError: () -> PcMkplError) = when (context.stubCase) {
        PcMkplStubs.SUCCESS -> context.successResponse {
            adResponse = PcStubs.getModel {
                if (adRequest.visibility != PcMkplVisibility.NONE) visibility = adRequest.visibility
                if (adRequest.id != PcMkplAdId.NONE) id = adRequest.id
                if (adRequest.title.isNotEmpty()) title = adRequest.title
                if (adRequest.description.isNotEmpty()) description = adRequest.description
            }
        }
        else -> context.errorResponse(buildError) {
            it.copy(field = "ad.id", message = notFoundError(context.adRequest.id.asString()))
        }
    }


    fun deleteAd(context: PcMkplContext, buildError: () -> PcMkplError): PcMkplContext = when (context.stubCase) {
        PcMkplStubs.SUCCESS -> context.successResponse {
            adResponse = PcStubs.getModel { id = context.adRequest.id }
        }
        else -> context.errorResponse(buildError) {
            it.copy(
                field = "ad.id",
                message = notFoundError(context.adRequest.id.asString())
            )
        }
    }

    fun searchAd(context: PcMkplContext, buildError: () -> PcMkplError): PcMkplContext {
        val filter = context.adFilterRequest

        val searchableString = filter.searchString

        return when (context.stubCase) {
            PcMkplStubs.SUCCESS -> context.successResponse {
                adsResponse.addAll(
                    PcStubs.getModels()
                )
            }
            else -> context.errorResponse(buildError) {
                it.copy(
                    message = "Nothing found by $searchableString"
                )
            }
        }
    }
}