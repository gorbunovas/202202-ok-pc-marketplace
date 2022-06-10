package ru.gorbunovas.pcmarketplace.common

import kotlinx.datetime.*
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs
import java.net.CacheRequest

data class PcMkplContext(
    var command: PcMkplCommand = PcMkplCommand.NONE,
    var state: PcMkplState = PcMkplState.NONE,
    val errors: MutableList<PcMkplError> = mutableListOf(),

    var workMode: PcMkplWorkMode = PcMkplWorkMode.PROD,
    var stubCase: PcMkplStubs = PcMkplStubs.NONE,

    var requestId: PcMkplRequestId = PcMkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: PcMkplAd = PcMkplAd(),
    var adFilterRequest: PcMkplAdFilter = PcMkplAdFilter(),
    var commentRequest: PcMkplComment = PcMkplComment(),

    var adResponse: PcMkplAd = PcMkplAd(),
    var adsResponse: MutableList<PcMkplAd> = mutableListOf(),
)
