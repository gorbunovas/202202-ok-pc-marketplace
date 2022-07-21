package ru.gorbunovas.pcmarketplace.api

import ru.gorbunovas.pcmarketplace.api.v1.models.ResponseResult
import ru.gorbunovas.pcmarketplace.common.models.PcMkplError

fun buildError() = PcMkplError(
    field = "_", code = ResponseResult.ERROR.value
)
