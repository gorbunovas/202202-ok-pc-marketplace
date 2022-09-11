import org.junit.Test
import ru.gorbunovas.pcmarketplace.api.v1.apiV1ResponseDeserialize
import ru.gorbunovas.pcmarketplace.api.v1.apiV1ResponseSerialize
import ru.gorbunovas.pcmarketplace.api.v1.models.*
import kotlin.test.*


class SerializationResponseTest {

    private val createResponse = AdCreateResponse(
        ad = AdResponseObject(
            title = "Title",
            description = "Description",
            product = AdProductPC(
                cpu = AdProductPCcpu(core = 8, clock = 2200),
                ram = AdProductPCram(AdProductPCram.Type.DDR6, clock = 1333),
                productType = "WorkStation",
                price = 100000.0
            )
        )
    )

    @Test
    fun serializeTest() {
        val jsonTest = apiV1ResponseSerialize(createResponse)
        assertContains(jsonTest, """"title":"Title"""")
        assertContains(jsonTest, """"responseType":"create"""")
        assertContains(jsonTest, "\"price\":100000")
    }

    @Test
    fun deserializeTest() {
        val jsonTest = apiV1ResponseSerialize(createResponse)
        val objectTest = apiV1ResponseDeserialize<AdCreateResponse>(jsonTest)
        assertEquals("Title", objectTest.ad?.title)
        assertEquals("Description", objectTest.ad?.description)
        val product = objectTest.ad?.product as AdProductPC
        assertEquals(8, product.cpu?.core)
        assertEquals(1333, product.ram?.clock)
    }

    @Test
    fun deserializeResponseTest() {
        val jsonTest = apiV1ResponseSerialize(createResponse)
        val objectTest = apiV1ResponseDeserialize<IResponse>(jsonTest) as AdCreateResponse
        assertEquals("Title", objectTest.ad?.title)
        assertEquals("Description", objectTest.ad?.description)
        val product = objectTest.ad?.product as AdProductPC
        assertEquals(100000.0, product.price)
    }

}