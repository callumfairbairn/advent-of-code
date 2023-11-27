import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSFnode() {
    @Test
    fun testParseAndToString() {
        assertEquals("1", SFnode("1").toString())
        assertEquals("[1,2]", SFnode("[1,2]").toString())
        assertEquals("[[1,2],3]", SFnode("[[1,2],3]").toString())
        assertEquals("[[1,2],[1,2]]", SFnode("[[1,2],[1,2]]").toString())
    }

    @Test
    fun testSplitSnailfish() {
        assertEquals("[2,3]", SFnode("5").split().toString())
        assertEquals("[3,3]", SFnode("6").split().toString())
        assertEquals("[3,4]", SFnode("7").split().toString())
        assertThrows<Exception> {
            SFnode("[2,3]").split()
        }
    }

    @Test
    fun testSetsUpParent() {
        val oneTwo = SFnode("[1,2]")
        assertEquals(oneTwo, oneTwo.left?.parent)
        assertEquals(oneTwo, oneTwo.left?.parent)
    }


    @Test
    fun addFish() {
        val node1 = SFnode("[1,2]")
        val newParent = node1.add("[3,4]")
        assertEquals("[[1,2],[3,4]]", newParent.toString())
    }

    @Test
    fun addNumbers() {
        val node1 = SFnode("1")
        node1.add("3")
        assertEquals("4", node1.toString())
    }

    @Test
    fun getMagnitude() {
        assertEquals(7, SFnode("[1,2]").getMagnitude())
        assertEquals(27, SFnode("[[1,2],3]").getMagnitude())
        assertEquals(143, SFnode("[[1,2],[[3,4],5]]").getMagnitude())
        assertEquals(3488, SFnode("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").getMagnitude())
    }

    @Nested
    inner class TestClosestLeft() {
        @Test
        fun testReturnsNullForRoot() {
            val node = SFnode("[1,2]")
            assertEquals(null, node.getClosestLeft())
        }

        @Test
        fun testReturnsLeftSibling() {
            val node = SFnode("[1,2]")
            assertEquals(null, node.left!!.getClosestLeft())
        }

        @Test
        fun testReturnsRightSibling() {
            val node = SFnode("[1,2]")
            assertEquals("1", node.right!!.getClosestLeft().toString())
        }

        @Test
        fun testReturnsLeftChild() {
            val node = SFnode("[[1,2],3]")
            assertEquals("2", node.right!!.getClosestLeft().toString())
        }
    }

    @Nested
    inner class TestClosestRight() {
        @Test
        fun testReturnsNullForRoot() {
            val node = SFnode("[1,2]")
            assertEquals(null, node.getClosestRight())
        }

        @Test
        fun testReturnsLeftSibling() {
            val node = SFnode("[1,2]")
            assertEquals(null, node.right!!.getClosestRight())
        }

        @Test
        fun testReturnsRightSibling() {
            val node = SFnode("[1,2]")
            assertEquals("2", node.left!!.getClosestRight().toString())
        }

        @Test
        fun testReturnsLeftChild() {
            val node = SFnode("[[1,2],3]")
            assertEquals("3", node.left!!.right!!.getClosestRight().toString())
        }
    }

    @Nested
    inner class TestExplode() {
        @Test
        fun testExplodesRoot() {
            val node = SFnode("[1,2]")
            node.explode()
            assertEquals("0", node.toString())
        }

        @Test
        fun testAffectsParent() {
            val node = SFnode("[1,[1,2]]")
            node.right?.explode()
            assertEquals("[2,0]", node.toString())
        }

        @Test
        fun testFirstTestCase() {
            val node = SFnode("[[[[[9,8],1],2],3],4]")
            node.left?.left?.left?.left?.explode()
            assertEquals("[[[[0,9],2],3],4]", node.toString())
        }

        @Test
        fun testSecondTestCase() {
            val node = SFnode("[7,[6,[5,[4,[3,2]]]]]")
            node.right?.right?.right?.right?.explode()
            assertEquals("[7,[6,[5,[7,0]]]]", node.toString())
        }

        @Test
        fun testThirdTestCase() {
            val node = SFnode("[[6,[5,[4,[3,2]]]],1]")
            node.left?.right?.right?.right?.explode()
            assertEquals("[[6,[5,[7,0]]],3]", node.toString())
        }

        @Test
        fun testFourthTestCase() {
            val node = SFnode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
            node.left?.right?.right?.right?.explode()
            assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", node.toString())
        }

        @Test
        fun testFifthTestCase() {
            val node = SFnode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
            node.right?.right?.right?.right?.explode()
            assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", node.toString())
        }

        @Test
        fun testSixthTestCase() {
            val node = SFnode("[[[[[6,7],[6,7]],[[7,7],[0,7]]],0],0]")
            node.left?.left?.left?.left?.explode()
            assertEquals("[[[[0,[13,7]],[[7,7],[0,7]]],0],0]", node.toString())
        }
    }
}

internal class TestNodeReducer() {
    @Nested
    inner class TestFindNodeToExplode() {
        @Test
        fun testFindsNothing() {
            val root = SFnode("[1,2]")
            val reducer = NodeReducer(root)
            assertEquals(null, reducer.findNodeToExplode(root, 0))
        }

        @Test
        fun testFindsNodeNestedDeeplyToTheLeft() {
            val root = SFnode("[[[[[1,2],1],2],3],4]")
            val reducer = NodeReducer(root)
            assertEquals("[1,2]", reducer.findNodeToExplode(root, 0).toString())
        }

        @Test
        fun testFindsNodeNestedDeeplyToTheRight() {
            val root = SFnode("[7,[6,[5,[4,[3,2]]]]]")
            val reducer = NodeReducer(root)
            assertEquals("[3,2]", reducer.findNodeToExplode(root, 0).toString())
        }

        @Test
        fun testAnother() {
            val root = SFnode("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]")
            val reducer = NodeReducer(root)
            assertEquals("[8,4]", reducer.findNodeToExplode(root, 0).toString())
        }
    }

    @Nested
    inner class TestFindNodeToSplit() {
        @Test
        fun testFindsNothing() {
            val root = SFnode("[1,2]")
            val reducer = NodeReducer(root)
            assertEquals(null, reducer.findNodeToSplit(root))
        }

        @Test
        fun testNodeGreaterThan10() {
            val root = SFnode("[[[[0,7],4],[15,[0,13]]],[1,1]]")
            val reducer = NodeReducer(root)
            assertEquals("15", reducer.findNodeToSplit(root).toString())
        }
    }

    @Nested
    inner class TestStep() {
        @Test
        fun testStep() {
            val node = SFnode("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")
            val nodeReducer = node.reducer
            // explode
            assertEquals("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]", nodeReducer.step().toString())
            // explode
            assertEquals("[[[[0,7],4],[15,[0,13]]],[1,1]]", nodeReducer.step().toString())
            // split
            assertEquals("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", nodeReducer.step().toString())
            // split
            assertEquals("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]", nodeReducer.step().toString())
            // explode
            assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", nodeReducer.step().toString())
            // no-op
            assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", nodeReducer.step().toString())
        }
    }

    @Nested
    inner class TestReduce() {
        @Test
        fun testReduce() {
            val node = SFnode("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")
            node.reducer.reduce()
            assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", node.toString())
        }
    }
}

internal class TestSnailfishAdder() {

    @Test
    fun testAddAndAddReduce1() {
        val list = listOf(
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
            "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"
        )
        assertEquals(
            "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]",
            SnailfishAdder(list).addAll().toString()
        )
    }

    @Test
    fun testAddAndAddReduce2() {
        val list = listOf(
            "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]",
            "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]"
        )
        assertEquals(
            "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]",
            SnailfishAdder(list).addAll().toString()
        )
    }

    @Test
    fun testAddAndAddReduce3() {
        val list = listOf(
            "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]",
            "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]"
        )
        assertEquals(
            "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]",
            SnailfishAdder(list).addAll().toString()
        )
    }

    @Test
    fun testAddsListOfNumbers() {
        val list = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]")
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", SnailfishAdder(list).addAll().toString())
    }

    @Test
    fun testAddsList2() {
        val list = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]")
        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", SnailfishAdder(list).addAll().toString())
    }

    @Test
    fun testAddsList3() {
        val list = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]", "[6,6]")
        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", SnailfishAdder(list).addAll().toString())
    }

    @Test
    fun testAddsListLong() {
        val list = listOf(
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
            "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
            "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
            "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
            "[7,[5,[[3,8],[1,4]]]]",
            "[[2,[2,2]],[8,[8,1]]]",
            "[2,9]",
            "[1,[[[9,3],9],[[9,0],[0,7]]]]",
            "[[[5,[7,4]],7],1]",
            "[[[[4,2],2],6],[8,7]]",
        )
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", SnailfishAdder(list).addAll().toString())
    }
}