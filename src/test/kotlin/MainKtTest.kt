package org.abc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTest{
    @Test
    fun `player should start with 0`(){
        val players = mutableListOf<String>()

        players.add("swe")
        val res = initPlayerPos(players)

        assertEquals(0,res["swe"])
    }

    @Test
    fun `player wins when he reaches 100`(){
        val players = mutableListOf<String>()
        players.add("swe")
        val position=mutableMapOf<String, Int>()


        val currentPosition = 95
        val diceRoll=5
        val ladderPositions= mutableListOf(10,20)
        val snakePositions= mutableListOf(25,15)

        val updatedPosition = updatePosition(currentPosition, diceRoll, ladderPositions, snakePositions)
        position["swe"]=updatedPosition


        assertEquals("swe",checkWin(position))
    }

    @Test
    fun `player moves are based on dice rolls`(){
        val players = mutableListOf<String>()
        players.add("swe")
        val currentPosition = 4
        val diceRoll=5
        val ladderPositions= mutableListOf(10,20)
        val snakePositions= mutableListOf(25,15)

        val updatedPosition = updatePosition(currentPosition, diceRoll, ladderPositions, snakePositions)

        assertEquals(9,updatedPosition)
    }

    @Test
    fun `dice roll should be within the range for 1 to 6`(){
        val diceRoll = diceRoll()

        assertEquals(true, diceRoll in 1..6)
    }

    @Test
    fun `player who lands on snake moves to its tail`(){
        val players = mutableListOf<String>()
        players.add("swe")
        val currentPosition = 20
        val diceRoll=5
        val ladderPositions= mutableListOf(10,20)
        val snakePositions= mutableListOf(25,15)

        val updatedPosition = updatePosition(currentPosition, diceRoll, ladderPositions, snakePositions)

        assertEquals(15,updatedPosition)
    }

    @Test
    fun `player who lands on ladder moves to the ladder top`(){
        val players = mutableListOf<String>()
        players.add("swe")
        val currentPosition = 5
        val diceRoll=5
        val ladderPositions= mutableListOf(10,20)
        val snakePositions= mutableListOf(25,15)

        val updatedPosition = updatePosition(currentPosition, diceRoll, ladderPositions, snakePositions)

        assertEquals(20,updatedPosition)
    }

    @Test
    fun `ladder base is lower than ladder top`(){
        val ladderPositions= mutableListOf(10,20)
        assertEquals(true,isLadderPosCorrect(ladderPositions))
    }

    @Test
    fun `snake tail is lower than snake head`(){
        val snakePositions= mutableListOf(25,15)
        assertEquals(true, isSnakesPosCorrect(snakePositions))

    }

    @Test
    fun `the board is numbered from 1 to 100`(){
        assertEquals(true, boundaryCheck(0,1))
        assertEquals(true,boundaryCheck(99,0))
        assertEquals(false, boundaryCheck(99,2))
    }

    @Test
    fun `player should take turns`(){
        val players = mutableListOf<String>()
        players.add("swe")
        players.add("abc")
        val ladderPositions= mutableListOf(10,20)
        val snakePositions= mutableListOf(25,15)
        val result=game(ladderPositions, snakePositions, players)
        println(result)
    }

    @Test
    fun `player repeats turn when 6`(){
        val players = mutableListOf<String>()
        players.add("swe")
        var diceRoll=6
        assertEquals(true, repeatOrNot(diceRoll))
        diceRoll=5
        assertEquals(false, repeatOrNot(diceRoll))

    }

    @Test
    fun `player remains in the same position if the dice roll goes beyond 100`(){
        val players = mutableListOf<String>()
        players.add("swe")
        val currentPosition = 97
        val diceRoll=5
        val ladderPositions= mutableListOf(10,20)
        val snakePositions= mutableListOf(25,15)

        val updatedPosition = updatePosition(currentPosition, diceRoll, ladderPositions, snakePositions)

        assertEquals(97,updatedPosition)
    }
}