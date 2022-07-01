package pl.training.goodweather.commons

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(TicTacToeTests::class, TicTacToeTakeFieldTests::class, TicTacToeWinningSequencesTests::class)
class TicTacToeTestSuit

class TicTacToeTests  {

    @Test
    fun should_end_game_when_all_fields_are_taken() {
        val ticTacToe = TicTacToe(mutableSetOf(1, 3, 5, 8), mutableSetOf(2, 4, 6, 7))
        assertFalse(ticTacToe.isGameOver)
        ticTacToe.makeMove(9)
        assertTrue(ticTacToe.isGameOver)
    }

    @Test
    fun should_allow_only_free_fields_to_be_taken() {
        val ticTacToe = TicTacToe()
        ticTacToe.makeMove(1)
        assertFalse(ticTacToe.makeMove(1))
    }

    @Test
    fun should_change_player_after_field_is_taken() {
        val ticTacToe = TicTacToe()
        val player = ticTacToe.player
        ticTacToe.makeMove(1)
        assertNotEquals(ticTacToe.player, player)
    }

    @Test
    fun should_not_change_player_after_field_is_not_taken() {
        val ticTacToe = TicTacToe()
        ticTacToe.makeMove(1)
        val player = ticTacToe.player
        ticTacToe.makeMove(1)
        assertEquals(ticTacToe.player, player)
    }

    @Test(expected = java.lang.IllegalArgumentException::class)
    fun should_throw_exception_when_all_fields_are_taken() {
        TicTacToe(mutableSetOf(1, 3, 5, 8), mutableSetOf(1, 4, 6, 7, 9))
    }

    @Test(expected = java.lang.IllegalArgumentException::class)
    fun should_throw_exception_when_field_is_taken_by_two_players() {
        TicTacToe(mutableSetOf(1), mutableSetOf(1))
    }

}

@RunWith(Parameterized::class)
class TicTacToeTakeFieldTests(private val field: Int) {

    companion object {

        @JvmStatic
        @Parameters
        fun data() = listOf(arrayOf(0), arrayOf(10))

    }

    @Test
    fun should_allow_only_on_board_fields_to_be_taken() {
        val ticTacToe = TicTacToe()
        assertFalse(ticTacToe.makeMove(field))
    }

}


@RunWith(Parameterized::class)
class TicTacToeWinningSequencesTests(private val cross: MutableSet<Int>) {

    companion object {

        @JvmStatic
        @Parameters
        fun data() = listOf(
            setOf(1, 2, 3), setOf(4, 5, 6), setOf(7, 8, 9),
            setOf(1, 4, 7), setOf(2, 5, 8), setOf(3, 6, 9),
            setOf(1, 5, 9), setOf(3, 5, 7)
        )

    }

    @Test
    fun should_end_game_when_player_took_winning_sequence() {
        val ticTacToe = TicTacToe(cross, mutableSetOf())
        assertTrue(ticTacToe.isGameOver)
    }

}