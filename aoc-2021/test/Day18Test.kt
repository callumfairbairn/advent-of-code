import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSFnode() {
    @Test
    fun testParseAndToString() {
        assertEquals("1", SFnode("1").toString())
        assertEquals("[1, 2]", SFnode("[1, 2]").toString())
        assertEquals("[[1, 2], 3]", SFnode("[[1, 2], 3]").toString())
        assertEquals("[[1, 2], [1, 2]]", SFnode("[[1, 2], [1, 2]]").toString())
    }

    @Test
    fun testSplitSnailfish() {
        assertEquals("[2, 3]", SFnode("5").split().toString())
        assertEquals("[3, 3]", SFnode("6").split().toString())
        assertEquals("[3, 4]", SFnode("7").split().toString())
        assertThrows<Exception> {
            SFnode("[2, 3]").split()
        }
    }

    @Test
    fun testSetsUpParent() {
        val oneTwo = SFnode("[1, 2]")
        assertEquals(oneTwo, oneTwo.left?.parent)
        assertEquals(oneTwo, oneTwo.left?.parent)
    }

    @Test
    fun testGetClosestLeft() {
        val node1 = SFnode("1")
        assertEquals(null, node1.getClosestLeft())

        val nodeA = SFnode("[1, 2]")
        assertEquals(null, nodeA.left!!.getClosestLeft())
        assertEquals("1", nodeA.right!!.getClosestLeft().toString())

        val nodeB = SFnode("[[3, 4], [5, 6]]")
//        assert
    }

//    @Test
//    fun explodeSnailfish() {
//        val node1 = SFnode().parse("[2, 3]")
//        assertEquals(SFnode().parse("0"), node1.explode())
//
//        val node2 = SFnode(node1, SFnode().parse("[4, 5]"))
//        assertEquals(SFnode().parse("[[2, 3], [4, 5]]"), node2)
//        node1.explode()
//        assertEquals(SFnode().parse("[0, [7, 5]]"), node2)
//    }
}