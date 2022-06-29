import org.junit.Test
import ru.gorbunovas.pcmarketplace.api.v1.*
import ru.gorbunovas.pcmarketplace.api.v1.models.*
import kotlin.test.*

class SerializationRequestTest {

    private val createRequest = AdCreateRequest(
        requestId = "test",
        ad = AdCreateObject(
            title = "Title",
            description = "Description",
            DealSide.DEMAND,
            product = AdProductPC(
                motherboard = "z360",
                formfactor = AdProductPC.Formfactor.MIDI_TOWER,
                cpu = AdProductPCcpu(core = 8, clock = 2200),
                ram = AdProductPCram(AdProductPCram.Type.DDR6, clock = 1333),
                productType = "WorkStation"
            )
        )
    )

    @Test
    fun serializeTest() {
        val jsonTest = apiV1RequestSerialize(createRequest)
        assertContains(jsonTest, """"title":"Title"""")
        assertContains(jsonTest, """"requestType":"create"""")
    }

    @Test
    fun deserializeTest() {
        val jsonTest = apiV1RequestSerialize(createRequest)
        val objectTest = apiV1RequestDeserialize<AdCreateRequest>(jsonTest)
        assertEquals("Title", objectTest.ad?.title)
        assertEquals("Description", objectTest.ad?.description)
        assertEquals("WorkStation", objectTest.ad?.product?.productType)
        assert(objectTest.ad?.product is AdProductPC)
        val product = objectTest.ad?.product as AdProductPC
        assertEquals(1333, product.ram?.clock)
        assertEquals(AdProductPCram.Type.DDR6, product.ram?.type)
        assertEquals(2200, product.cpu?.clock)
        assertEquals(8, product.cpu?.core)
    }
}