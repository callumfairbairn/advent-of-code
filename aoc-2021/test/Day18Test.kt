import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSFnode() {
    @Test
    fun testEquals() {
        assertEquals(SFnode(2), SFnode(2))
        assertEquals(SFnode(SFnode(2), SFnode(3)), SFnode(SFnode(2), SFnode(3)))
    }

    @Test
    fun testToString() {
        val one = SFnode(1)
        val oneTwo = SFnode(one, SFnode(2))
        val oneTwoThree = SFnode(oneTwo, SFnode(3))
        val oneTwoOneTwo = SFnode(oneTwo, oneTwo)
        assertEquals("1", one.toString())
        assertEquals("[1, 2]", oneTwo.toString())
        assertEquals("[[1, 2], 3]", oneTwoThree.toString())
        assertEquals("[[1, 2], [1, 2]]", oneTwoOneTwo.toString())
    }

    @Test
    fun testParseSnailfish() {
        assertEquals(SFnode(1), SFnode().parse("1"))
        assertEquals(SFnode(SFnode(1), SFnode(2)), SFnode().parse("[1, 2]"))
        assertEquals(SFnode(SFnode(1), SFnode(SFnode(2), SFnode(3))), SFnode().parse("[1, [2, 3]]"))
        assertEquals(SFnode(SFnode(SFnode(1), SFnode(2)), SFnode(3)), SFnode().parse("[[1, 2], 3]"))
        assertEquals(SFnode(SFnode(SFnode(1), SFnode(2)), SFnode(SFnode(3), SFnode(4))), SFnode().parse("[[1, 2], [3, 4]]"))
    }

    @Test
    fun testSplitSnailfish() {
        assertEquals(SFnode().parse("[2, 3]"), SFnode(5).split())
        assertEquals(SFnode().parse("[3, 3]"), SFnode(6).split())
        assertEquals(SFnode().parse("[3, 4]"), SFnode(7).split())
        assertThrows<Exception> {
            SFnode(SFnode(2), SFnode(3)).split()
        }
    }

    @Test
    fun testSetsUpParent() {
        val one = SFnode(1)
        val two = SFnode(2)
        val oneTwo = SFnode(one, two)
        assertEquals(oneTwo, one.parent)
        assertEquals(oneTwo, two.parent)
    }

    @Test
    fun testGetClosestLeft() {
        val node1 = SFnode(1)
        assertEquals(null, node1.getClosestLeft())

        val node2 = SFnode(2)
        val nodeA = SFnode(node1, node2)
        assertEquals(null, nodeA.left!!.getClosestLeft())
        assertEquals(node1, nodeA.right!!.getClosestLeft())


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