package ru.gorbunovas.pcmarketplace.biz.repo

import com.crowdproj.kotlin.cor.ICorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.PcMkplDealSide
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.repo.DbAdFilterRequest
import ru.gorbunovas.pcmarketplace.common.repo.DbAdsResponse


fun ICorChainDsl<PcMkplContext>.repoOffers(title: String) = worker {
    this.title = title
    description = "Поиск предложений для объявления по названию"
    on { state == PcMkplState.RUNNING }
    handle {
        val adRequest = adRepoPrepare
        val filter = DbAdFilterRequest(
            titleFilter = adRequest.title,
            dealSide = when (adRequest.adType) {
                PcMkplDealSide.DEMAND -> PcMkplDealSide.SUPPLY
                PcMkplDealSide.SUPPLY -> PcMkplDealSide.DEMAND
                PcMkplDealSide.NONE -> PcMkplDealSide.NONE
            }
        )
        val dbResponse = if (filter.dealSide == PcMkplDealSide.NONE) {
            DbAdsResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    PcMkplError(
                        field = "adType",
                        message = "Type of ad must not be empty"
                    )
                )
            )
        } else {
            adRepo.searchAd(filter)
        }

        val resultAds = dbResponse.result
        when {
            !resultAds.isNullOrEmpty() -> adsRepoDone = resultAds.toMutableList()
            dbResponse.isSuccess -> return@handle
            else -> {
                state = PcMkplState.FAILING
                errors.addAll(dbResponse.errors)
            }
        }
    }
}
