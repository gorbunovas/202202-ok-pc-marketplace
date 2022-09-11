package ru.gorbunovas.pcmarketplace.common.repo

import ru.gorbunovas.pcmarketplace.common.models.PcMkplAd
import ru.gorbunovas.pcmarketplace.common.models.PcMkplError

data class DbAdResponse(
    override val result: PcMkplAd?,
    override val isSuccess: Boolean,
    override val errors: List<PcMkplError> = emptyList()
): IDbResponse<PcMkplAd> {

    companion object {
        fun success(result:PcMkplAd) = DbAdResponse(result, true)
        fun error(errors: List<PcMkplError>) = DbAdResponse(null, false, errors)
        fun error(error: PcMkplError) = DbAdResponse(null, false, listOf(error))
    }
}
