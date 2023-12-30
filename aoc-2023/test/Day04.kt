import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestGetScore() {
    @Test
    fun testGetScore() {
        assertEquals(0, getScratchcardScore(0))
        assertEquals(1, getScratchcardScore(1))
        assertEquals(2, getScratchcardScore(2))
        assertEquals(4, getScratchcardScore(3))
        assertEquals(8, getScratchcardScore(4))
        assertEquals(16, getScratchcardScore(5))
    }
}