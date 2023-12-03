import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day3KtTest {

    @Test
    fun checkGoLeft() {
        assertEquals("123", goLeft("123.567", 3), "1")
        assertEquals("23", goLeft(".23.567.", 3), "2")
        assertEquals(""   , goLeft("12...567", 3), "3")
    }

    @Test
    fun checkGoRight() {
        assertEquals("567", goRight("123.567", 3), "1")
        assertEquals("567", goRight("123.567.", 3), "2")
        assertEquals(""   , goRight("123..567", 3), "3")
    }

    @Test
    fun checkGetNumbersAround() {
        assertEquals(emptyList<String>(), getNumbersAround(3, listOf("11...22", "33.*.44", "55...66")))
        assertEquals(listOf("111", "222", "333", "444", "555", "666"), getNumbersAround(3, listOf("111.222", "333*444", "555.666")))
        assertEquals(listOf("11", "22", "33", "44", "55", "66"), getNumbersAround(3, listOf(".11.22.", ".33*44.", ".55.66.")))
        assertEquals(listOf("1", "2", "3", "4", "5", "6"), getNumbersAround(3, listOf("1.1.2.2", "3.3*4.4", "5.5.6.6")))
        assertEquals(listOf("1", "2", "3", "4", "5", "6"), getNumbersAround(3, listOf("..1.2..", "..3*4..", "..5.6..")))

        assertEquals(listOf("111", "333", "444", "555"), getNumbersAround(3, listOf(".111...", "333*444", "...555.")))
        assertEquals(listOf("111"), getNumbersAround(3, listOf("..111..", "...*...", ".......")))


        assertEquals(listOf("222", "444", "666"), getNumbersAround(0, listOf(".222", "*444", ".666")))
        assertEquals(emptyList<String>(), getNumbersAround(0, listOf("..22", "*.44", "..66")))
        assertEquals(listOf("111", "333", "555"), getNumbersAround(3, listOf("111.", "333*", "555.")))

        assertEquals(listOf("459", "222"), getNumbersAround(3, listOf("....459", "2..*...", "....222")))
    }
}