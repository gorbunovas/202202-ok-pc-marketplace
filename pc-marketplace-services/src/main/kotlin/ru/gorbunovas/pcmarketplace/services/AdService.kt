package ru.gorbunovas.pcmarketplace.services

import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.*


class AdService {
        private val processor = PcMkplAdProcessor()

        suspend fun exec(context: PcMkplContext) = processor.exec(context)

        suspend fun createAd(context: PcMkplContext) = processor.exec(context)
        suspend fun readAd(context: PcMkplContext) = processor.exec(context)
        suspend fun updateAd(context: PcMkplContext) = processor.exec(context)
        suspend fun deleteAd(context: PcMkplContext) = processor.exec(context)
        suspend fun searchAd(context: PcMkplContext) = processor.exec(context)
        suspend fun searchOffers(context: PcMkplContext) = processor.exec(context)

}