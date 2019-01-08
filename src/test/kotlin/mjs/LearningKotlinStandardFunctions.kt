package mjs

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import io.mockk.clearAllMocks
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
@DisplayName("Learning Kotlin standard functions")
class LearningKotlinStandardFunctions {

    data class Item(val name: String, val size: Int, var colour: String = "blue")

    interface Runner {
        fun run(message: String)
    }

    var runner = mockk<Runner>(relaxed = true)

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Nested
    @DisplayName("Functions that return the function result")
    inner class ReturningFunctionResult {

        @Test
        @DisplayName("`let` calls the function with `this` as its argument and returns the function result")
        fun letTest() {
            val result = Item("Block", 5).let {
                runner.run("let")
                it.size * 2
            }
            assert(result).isEqualTo(10)
            verify(exactly = 1) { runner.run("let") }
        }

        @Test
        @DisplayName("`run` calls the function with `this` as its receiver and returns the function result")
        fun runTest() {
            val item = Item("Block", 5)
            val result = item.run {
                runner.run("run")
                colour = "cyan"
                name.toUpperCase()
            }
            assert(result).isEqualTo("BLOCK")
            assert(item.colour).isEqualTo("cyan")
            verify(exactly = 1) { runner.run("run") }
        }

        @Test
        @DisplayName("`with` calls the function with a given receiver and returns the function result")
        fun withTest() {
            val item = Item("Block", 5)
            val result = with(item) {
                runner.run("with")
                colour = "puce"
                size * 5
            }
            assert(result).isEqualTo(25)
            assert(item.colour).isEqualTo("puce")
            verify(exactly = 1) { runner.run("with") }
        }
    }

    @Nested
    @DisplayName("Functions that return `this`")
    inner class FuntionsThatReturnThis {

        @Test
        @DisplayName("`also` calls the function with `this` as its argument and returns this")
        fun alsoTest() {
            val item = Item("Block", 5)
            val result = item.also {
                runner.run("also")
                it.colour = "red"
            }
            assert(result).isSameAs(item)
            assert(result.colour).isEqualTo("red")
            verify(exactly = 1) { runner.run("also") }
        }

        @Test
        @DisplayName("`apply` calls the function with `this` as its receiver and returns this")
        fun applyTest() {
            val item = Item("Block", 5)
            val result = item.apply {
                runner.run("apply")
                colour = "green"
            }
            assert(result).isSameAs(item)
            assert(result.colour).isEqualTo("green")
            verify(exactly = 1) { runner.run("apply") }
        }
    }
}

