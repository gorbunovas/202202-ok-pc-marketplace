package ru.gorbunovas.mappers

import org.junit.Test
import ru.gorbunovas.pcmarketplace.api.v1.models.*
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPc
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPcCpu
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPcFormfactor
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = AdCreateRequest(
            requestId = "1234",
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS,
            ),
            ad = AdCreateObject(
                title = "title",
                description = "desc",
                adType = DealSide.DEMAND,
                visibility = AdVisibility.PUBLIC,
                product = AdProductPC(
                    price = 10000,
                    hdd = "ssd 1Tb",
                    motherboard = "s360",
                    formfactor = AdProductPC.Formfactor.MINI_TOWER,
                    productType = "server",
                    ram = AdProductPCram(AdProductPCram.Type.DDR4, clock = 1333, model = "ram"),
                    cpu = AdProductPCcpu(clock = 2300, core = 12, model = "i7")
                )
            ),
        )

        val context = PcMkplContext()
        context.fromTransport(req)

        assertEquals(PcMkplStubs.SUCCESS, context.stubCase)
        assertEquals(PcMkplWorkMode.STUB, context.workMode)
        assertEquals("title", context.adRequest.title)
        assertEquals(PcMkplVisibility.VISIBLE_PUBLIC, context.adRequest.visibility)
        assertEquals(PcMkplDealSide.DEMAND, context.adRequest.adType)
        val product = context.adRequest.product as PcMkplAdProductPc
        assertEquals("ssd 1Tb", product.hdd)
        assertEquals(1333, product.ram.clock)
        assertEquals("ram", product.ram.model)
        assertEquals("i7", product.cpu.model)
        assertEquals(2300, product.cpu.clock)
        assertEquals(12, product.cpu.core)
        assertEquals(PcMkplAdProductPcFormfactor.MINI_TOWER, product.formFactor)
        assertEquals("server", product.type)
        assertEquals(10000, product.price)
    }

    @Test
    fun toTransport() {
        val context = PcMkplContext(
            requestId = PcMkplRequestId("1234"),
            command = PcMkplCommand.CREATE,
            adResponse = PcMkplAd(
                title = "title",
                description = "desc",
                adType = PcMkplDealSide.DEMAND,
                visibility = PcMkplVisibility.VISIBLE_PUBLIC,
                product = PcMkplAdProductPc(
                    price = 10000,
                    hdd = "ssd 1Tb",
                    motherboard = "s360",
                    formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
                    type = "server",
                    cpu = PcMkplAdProductPcCpu(model = "i10", core = 8)
                )
            ),
            errors = mutableListOf(
                PcMkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = PcMkplState.RUNNING,
        )

        val req = context.toTransportAd() as AdCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.ad?.title)
        assertEquals("desc", req.ad?.description)
        assertEquals(AdVisibility.PUBLIC, req.ad?.visibility)
        assertEquals(DealSide.DEMAND, req.ad?.adType)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
        val product = req.ad?.product as AdProductPC
        assertEquals("s360", product.motherboard)
        assertEquals("i10", product.cpu?.model)
        assertEquals(8, product.cpu?.core)
        assertEquals("server", product.productType)
        assertEquals(10000, product.price)
        assertEquals(AdProductPC.Formfactor.FULL_TOWER, product.formfactor)
    }
}
