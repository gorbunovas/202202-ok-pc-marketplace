package ru.gorbunovas.mappers

import ru.gorbunovas.mappers.exceptions.UnknownAdProduct
import ru.gorbunovas.mappers.exceptions.UnknownRequestClass
import ru.gorbunovas.pcmarketplace.api.v1.models.*
import ru.gorbunovas.pcmarketplace.common.*
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.models.product.*
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs

fun PcMkplContext.fromTransport(request: IRequest) = when(request){
    is AdCreateRequest -> fromTransport(request)
    is AdReadRequest -> fromTransport(request)
    is AdUpdateRequest -> fromTransport(request)
    is AdDeleteRequest -> fromTransport(request)
    is AdSearchRequest-> fromTransport(request)
    is AdOffersRequest -> fromTransport(request)
    is AdCommentRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request::class.java)
}

private fun String?.toAdId() = this?.let { PcMkplAdId(it) } ?: PcMkplAdId.NONE
private fun BaseAdIdRequestAd?.toAdWithId() = PcMkplAd(id = this?.id.toAdId())
private fun IRequest?.requestId() = this?.requestId?.let { PcMkplRequestId(it) } ?: PcMkplRequestId.NONE

private fun AdDebug?.transportToWorkMode(): PcMkplWorkMode = when(this?.mode) {
    AdRequestDebugMode.PROD -> PcMkplWorkMode.PROD
    AdRequestDebugMode.TEST -> PcMkplWorkMode.TEST
    AdRequestDebugMode.STUB -> PcMkplWorkMode.STUB
    null -> PcMkplWorkMode.PROD
}

private fun AdDebug?.transportToStubCase(): PcMkplStubs = when(this?.stub) {
    AdRequestDebugStubs.SUCCESS -> PcMkplStubs.SUCCESS
    AdRequestDebugStubs.NOT_FOUND -> PcMkplStubs.NOT_FOUND
    AdRequestDebugStubs.BAD_ID -> PcMkplStubs.BAD_ID
    AdRequestDebugStubs.BAD_TITLE -> PcMkplStubs.BAD_TITLE
    AdRequestDebugStubs.BAD_DESCRIPTION -> PcMkplStubs.BAD_DESCRIPTION
    AdRequestDebugStubs.BAD_VISIBILITY -> PcMkplStubs.BAD_VISIBILITY
    AdRequestDebugStubs.CANNOT_DELETE -> PcMkplStubs.CANNOT_DELETE
    AdRequestDebugStubs.BAD_SEARCH_STRING -> PcMkplStubs.BAD_SEARCH_STRING
    AdRequestDebugStubs.BAD_COMMENT -> PcMkplStubs.BAD_COMMENT
    null -> PcMkplStubs.NONE
}

fun PcMkplContext.fromTransport(request: AdCreateRequest) {
    command = PcMkplCommand.CREATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: PcMkplAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PcMkplContext.fromTransport(request: AdReadRequest) {
    command = PcMkplCommand.READ
    requestId = request.requestId()
    adRequest = request.ad.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PcMkplContext.fromTransport(request: AdUpdateRequest) {
    command = PcMkplCommand.UPDATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: PcMkplAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PcMkplContext.fromTransport(request: AdDeleteRequest) {
    command = PcMkplCommand.DELETE
    requestId = request.requestId()
    adRequest = request.ad.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PcMkplContext.fromTransport(request: AdSearchRequest) {
    command = PcMkplCommand.SEARCH
    requestId = request.requestId()
    adFilterRequest = request.adFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PcMkplContext.fromTransport(request: AdOffersRequest) {
    command = PcMkplCommand.OFFERS
    requestId = request.requestId()
    adRequest = request.ad.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun PcMkplContext.fromTransport(request: AdCommentRequest) {
    command = PcMkplCommand.COMMENT
    requestId = request.requestId()
    commentRequest = request.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun AdCommentRequest.toInternal(): PcMkplComment = PcMkplComment(
    buyerId = (this.buyerId ?: PcMkplUserId.NONE) as PcMkplUserId,
    text = this.text ?: "",
    title = this.title ?: ""
)

private fun AdSearchFilter?.toInternal(): PcMkplAdFilter = PcMkplAdFilter(
    searchString = this?.searchString ?: ""
)

private fun AdCreateObject.toInternal(): PcMkplAd = PcMkplAd(
    title = this.title ?: "",
    description = this.description ?: "",
    adType = this.adType.fromTransport(), // HelperMMapper.fromTransport(reuest)
    visibility = this.visibility.fromTransport(),
    product = this.product.fromTransport(),
)

private fun AdUpdateObject.toInternal(): PcMkplAd = PcMkplAd(
    id = this.id.toAdId(),
    title = this.title ?: "",
    description = this.description ?: "",
    adType = this.adType.fromTransport(),
    visibility = this.visibility.fromTransport(),
    product = this.product.fromTransport()
)

private fun AdVisibility?.fromTransport(): PcMkplVisibility = when(this) {
    AdVisibility.PUBLIC -> PcMkplVisibility.VISIBLE_PUBLIC
    AdVisibility.OWNER_ONLY -> PcMkplVisibility.VISIBLE_TO_OWNER
    AdVisibility.REGISTERED_ONLY -> PcMkplVisibility.VISIBLE_TO_GROUP
    null -> PcMkplVisibility.NONE
}

private fun DealSide?.fromTransport(): PcMkplDealSide = when(this) {
    DealSide.DEMAND -> PcMkplDealSide.DEMAND
    DealSide.PROPOSAL -> PcMkplDealSide.SUPPLY
    null -> PcMkplDealSide.NONE
}

private fun IAdProduct?.fromTransport(): IPcMkplAdProduct = when(val prod = this) {
    null -> IPcMkplAdProduct.NONE
    is AdProductPC -> prod.fromTransport()
    else -> throw UnknownAdProduct(prod)
}

private fun AdProductPC.fromTransport() = PcMkplAdProductPc(
    type = this.productType ?: "",
    price = this.price ?: 0,
    motherboard = this.motherboard ?: "",
    hdd = this.hdd ?: "",
    cpu = this.cpu.fromTransport(),
    ram = this.ram.fromTransport(),
    formFactor = this.formfactor.fromTransport()
)

private fun AdProductPCcpu?.fromTransport(): PcMkplAdProductPcCpu = PcMkplAdProductPcCpu(
    core = this?.core ?: 0,
    clock = this?.clock ?: 0,
    model = this?.model ?: ""
)

private fun AdProductPCram?.fromTransport(): PcMkplAdProductPcRam = PcMkplAdProductPcRam(
    clock = this?.clock ?: 0,
    typeRam = this?.type.fromTransport(),
    model = this?.model ?: ""
)

private fun AdProductPCram.Type?.fromTransport(): PcMkplAdProductPcRam.TypeRam = when(this) {
    AdProductPCram.Type.DDR3 -> PcMkplAdProductPcRam.TypeRam.DDR3
    AdProductPCram.Type.DDR4 -> PcMkplAdProductPcRam.TypeRam.DDR4
    AdProductPCram.Type.DDR5 -> PcMkplAdProductPcRam.TypeRam.DDR5
    AdProductPCram.Type.DDR6 -> PcMkplAdProductPcRam.TypeRam.DDR6
    null -> PcMkplAdProductPcRam.TypeRam.NONE
}

private fun AdProductPC.Formfactor?.fromTransport(): PcMkplAdProductPcFormfactor = when(this) {
    AdProductPC.Formfactor.FULL_TOWER -> PcMkplAdProductPcFormfactor.FULL_TOWER
    AdProductPC.Formfactor.MIDI_TOWER -> PcMkplAdProductPcFormfactor.MIDI_TOWER
    AdProductPC.Formfactor.MINI_TOWER -> PcMkplAdProductPcFormfactor.MINI_TOWER
    AdProductPC.Formfactor.SFF -> PcMkplAdProductPcFormfactor.SFF
    null -> PcMkplAdProductPcFormfactor.NONE
}
