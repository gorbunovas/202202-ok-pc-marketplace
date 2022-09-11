package ru.gorbunovas.pcmarketplace.common.repo

import ru.gorbunovas.pcmarketplace.common.models.*

data class DbAdsResponse(
    override val result: List<PcMkplAd>?,
    override val isSuccess: Boolean,
    override val errors: List<PcMkplError> = emptyList(),
): IDbResponse<List<PcMkplAd>> {

    companion object {
        fun success(result: List<PcMkplAd>) = DbAdsResponse(result, true)
        fun error(errors: List<PcMkplError>) = DbAdsResponse(null, false, errors)
        fun error(error: PcMkplError) = DbAdsResponse(null, false, listOf(error))
    }
}
