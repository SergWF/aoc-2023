import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day5_1KtTest {

    private val mapping = Mapping(50L, 98L, 2L)
    private val seedToSoilMap = listOf(
        Mapping(50L, 98L, 2L),
        Mapping(52L, 50L, 48L)
    )
    private val humidityToLocation = listOf(
        Mapping(60, 56, 37),
        Mapping(56, 93, 4),
    )
    val soilMap = Section("seed-to-soil map", seedToSoilMap)

    private val soilToFertilizerMap = listOf(
        Mapping(0, 15, 37),
        Mapping(37, 52, 2),
        Mapping(39, 0, 15)
    )

    @Test
    fun `getDestination should return destination for listed`() {
        assertEquals(81, soilMap.getDestination(79))
        assertEquals(57, soilMap.getDestination(55))
    }

    @Test
    fun `getDestination should return destination for not listed`() {
        assertEquals(13, soilMap.getDestination(13))
        assertEquals(14, soilMap.getDestination(14))
    }


    @Test
    fun belongsTo() {
        assertFalse(mapping.belongsTo(97))
        assertTrue(mapping.belongsTo(98))
        assertTrue(mapping.belongsTo(99))
        assertFalse(mapping.belongsTo(100))
    }

    @Test
    fun findDestination() {
        assertNull(mapping.findDestination(97))
        assertEquals(50, mapping.findDestination(98))
        assertEquals(51, mapping.findDestination(99))
        assertNull(mapping.findDestination(100))
    }

    @Test
    fun getSection() {
        val lines = readData("day5.test.txt")
        assertEquals(Section("seed-to-soil map", seedToSoilMap), getSection("seed-to-soil map", lines))
        assertEquals(
            Section("soil-to-fertilizer map", soilToFertilizerMap),
            getSection("soil-to-fertilizer map", lines)
        )
        assertEquals(
            Section("humidity-to-location map", humidityToLocation),
            getSection("humidity-to-location map", lines)
        )
    }

}