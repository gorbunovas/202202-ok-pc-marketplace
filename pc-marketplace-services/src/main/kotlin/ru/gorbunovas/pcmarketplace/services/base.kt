package ru.gorbunovas.pcmarketplace.services

import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*

fun PcMkplContext.errorResponse(buildError: () -> PcMkplError, error: (PcMkplError) -> PcMkplError) = apply {
    state = PcMkplState.FAILING
    errors.add(error(buildError()))
}

fun PcMkplContext.successResponse(context: PcMkplContext.() -> Unit) = apply(context)
    .apply { state = PcMkplState.RUNNING }

val notFoundError: (String) -> String = { "Not found ad by id $it" }