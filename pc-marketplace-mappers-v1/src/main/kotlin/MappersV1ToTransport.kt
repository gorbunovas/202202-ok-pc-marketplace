package ru.gorbunovas.mappers

import ru.gorbunovas.mappers.exceptions.UnknownMkplCommand
import ru.gorbunovas.pcmarketplace.api.v1.models.*
import ru.gorbunovas.pcmarketplace.common.*
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.models.product.*

fun PcMkplContext.toTransportAd(): IResponse = when (val cmd = command) {
    PcMkplCommand.CREATE -> toTransportCreate()
    PcMkplCommand.READ -> toTransportRead()
    PcMkplCommand.UPDATE -> toTransportUpdate()
    PcMkplCommand.DELETE -> toTransportDelete()
    PcMkplCommand.SEARCH -> toTransportSearch()
    PcMkplCommand.OFFERS -> toTransportOffers()
    PcMkplCommand.COMMENT -> toTransportComment()
    PcMkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun PcMkplContext.toTransportCreate() = AdCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PcMkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun PcMkplContext.toTransportRead() = AdReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PcMkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun PcMkplContext.toTransportUpdate() = AdUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PcMkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun PcMkplContext.toTransportDelete() = AdDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PcMkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun PcMkplContext.toTransportSearch() = AdSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PcMkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)

fun PcMkplContext.toTransportOffers() = AdOffersResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PcMkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    offers = adsResponse.toTransportAd()
)

fun PcMkplContext.toTransportComment() = AdCommentResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == PcMkplState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),

    //offers = adsResponse.toTransportAd()
)

fun List<PcMkplAd>.toTransportAd(): List<AdResponseObject>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun PcMkplAd.toTransportAd(): AdResponseObject = AdResponseObject(
    id = id.takeIf { it != PcMkplAdId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != PcMkplUserId.NONE }?.asString(),
    adType = adType.toTransportAd(),
    visibility = visibility.toTransportAd(),
    permissions = permissionsClient.toTransportAd(),
    product = product.toTransport(),
)

private fun IPcMkplAdProduct.toTransport(): IAdProduct? = when(this) {
    IPcMkplAdProduct.NONE -> null
    PcMkplAdProductNone -> null
    is PcMkplAdProductPc -> this.toTransport()
}

private fun PcMkplAdProductPc.toTransport() = AdProductPC(
    productType = this.type ?: "",
    price = this.price ?: 0,
    motherboard = this.motherboard ?: "",
    hdd = this.hdd ?: "",
    cpu = this.cpu.toTransport(),
    ram = this.ram.toTransport(),
    formfactor = this.formFactor.toTransport()
)

private fun PcMkplAdProductPcCpu?.toTransport(): AdProductPCcpu = AdProductPCcpu(
    core = this?.core ?: 0,
    clock = this?.clock ?: 0,
    model = this?.model ?: ""
)

private fun PcMkplAdProductPcRam?.toTransport(): AdProductPCram = AdProductPCram(
    clock = this?.clock ?: 0,
    type = this?.typeRam?.toTransport(),
    model = this?.model ?: ""
)

private fun PcMkplAdProductPcRam.TypeRam.toTransport(): AdProductPCram.Type? = when(this) {
    PcMkplAdProductPcRam.TypeRam.DDR3 -> AdProductPCram.Type.DDR3
    PcMkplAdProductPcRam.TypeRam.DDR4 -> AdProductPCram.Type.DDR4
    PcMkplAdProductPcRam.TypeRam.DDR5 -> AdProductPCram.Type.DDR5
    PcMkplAdProductPcRam.TypeRam.DDR6 -> AdProductPCram.Type.DDR6
    PcMkplAdProductPcRam.TypeRam.NONE -> null
}

private fun PcMkplAdProductPcFormfactor.toTransport(): AdProductPC.Formfactor? = when(this) {
    PcMkplAdProductPcFormfactor.FULL_TOWER -> AdProductPC.Formfactor.FULL_TOWER
    PcMkplAdProductPcFormfactor.MIDI_TOWER -> AdProductPC.Formfactor.MIDI_TOWER
    PcMkplAdProductPcFormfactor.MINI_TOWER -> AdProductPC.Formfactor.MINI_TOWER
    PcMkplAdProductPcFormfactor.SFF -> AdProductPC.Formfactor.SFF
    PcMkplAdProductPcFormfactor.NONE -> null
}


private fun Set<PcMkplAdPermissionClient>.toTransportAd(): Set<AdPermissions>? = this
    .map { it.toTransportAd() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun PcMkplAdPermissionClient.toTransportAd() = when (this) {
    PcMkplAdPermissionClient.READ -> AdPermissions.READ
    PcMkplAdPermissionClient.UPDATE -> AdPermissions.UPDATE
    PcMkplAdPermissionClient.MAKE_VISIBLE_OWNER -> AdPermissions.MAKE_VISIBLE_OWN
    PcMkplAdPermissionClient.MAKE_VISIBLE_GROUP -> AdPermissions.MAKE_VISIBLE_GROUP
    PcMkplAdPermissionClient.MAKE_VISIBLE_PUBLIC -> AdPermissions.MAKE_VISIBLE_PUBLIC
    PcMkplAdPermissionClient.DELETE -> AdPermissions.DELETE
}

private fun PcMkplVisibility.toTransportAd(): AdVisibility? = when (this) {
    PcMkplVisibility.VISIBLE_PUBLIC -> AdVisibility.PUBLIC
    PcMkplVisibility.VISIBLE_TO_GROUP -> AdVisibility.REGISTERED_ONLY
    PcMkplVisibility.VISIBLE_TO_OWNER -> AdVisibility.OWNER_ONLY
    PcMkplVisibility.NONE -> null
}

private fun PcMkplDealSide.toTransportAd(): DealSide? = when (this) {
    PcMkplDealSide.DEMAND -> DealSide.DEMAND
    PcMkplDealSide.SUPPLY -> DealSide.PROPOSAL
    PcMkplDealSide.NONE -> null
}

private fun List<PcMkplError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun PcMkplError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
