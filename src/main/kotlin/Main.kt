package org.abc
fun main() {
    val ladderPositions =ladderInput()
    val snakePositions=snakeInput()
    val players=playerInput()
    val result=game(ladderPositions, snakePositions, players)
    println(result)
}

fun game(
    ladderPositions: MutableList<Int>,
    snakePositions: MutableList<Int>,
    players: MutableList<String>
) :String{
    val playerDiceRolls = mutableMapOf<String, MutableList<Int>>()
    val playerPositions = initPlayerPos(players)
    for (player in players) {
        playerDiceRolls[player] = mutableListOf()
    }


    while(checkWin(playerPositions)==null){
        for (player in players) {
            if (eachTurn(
                    playerDiceRolls,
                    player,
                    playerPositions,
                    ladderPositions,
                    snakePositions,
                    players
                )
            ) return "Game Over"
        }
    }
    return "Game Over"
}

fun eachTurn(
    playerDiceRolls: MutableMap<String, MutableList<Int>>,
    player: String,
    playerPositions: MutableMap<String, Int>,
    ladderPositions: MutableList<Int>,
    snakePositions: MutableList<Int>,
    players: MutableList<String>
): Boolean {
    val diceRoll = diceRoll()
    playerDiceRolls[player]?.add(diceRoll)
    val currentPosition = playerPositions[player]!!
    val updatedPosition = updatePosition(currentPosition, diceRoll, ladderPositions, snakePositions)
    playerPositions[player] = updatedPosition
    if (checkWin(playerPositions) != null) {
        for (player in players) {
            println("$player ${playerDiceRolls[player]}")
        }
        println("${checkWin(playerPositions)} wins the game")
        return true
    }
    if(repeatOrNot(diceRoll)){
        eachTurn(
            playerDiceRolls,
            player,
            playerPositions,
            ladderPositions,
            snakePositions,
            players
        )
    }
    return false
}
fun repeatOrNot(diceRoll:Int):Boolean{
    if (diceRoll==6)return true
    else return false
}


fun initPlayerPos(players: MutableList<String>): MutableMap<String, Int> {
    val playerPositions = mutableMapOf<String, Int>()
    for (player in players) {
        playerPositions[player] = 0
    }
    return playerPositions
}

fun boundaryCheck(pos: Int, i: Int): Boolean {
    if (pos + i <=100 && pos+i>=0) {
        return true;
    }
    return false
}

private fun snakePos(snakePositions: MutableList<Int>, pos: Int): Int {
    var pos1 = pos
    var index = snakePositions.indexOf(pos1)
    if (index % 2 == 0) pos1 = snakePositions[index + 1]
    return pos1
}

private fun ladderPos(ladderPositions: MutableList<Int>, pos: Int): Int {
    var pos1 = pos
    var index = ladderPositions.indexOf(pos1)

    if (index % 2 == 0 && pos1 + ladderPositions[index + 1] < 100) {
        pos1 = ladderPositions[index + 1]
    }
    return pos1
}

private fun ladderInput(): MutableList<Int> {
    var x = readlnOrNull()
    val numberOfLadders = x!!.toInt()
    val ladderPositions = mutableListOf<Int>()
    ladders(numberOfLadders, ladderPositions)

    return ladderPositions
}

private fun snakeInput(): MutableList<Int> {
    var x = readlnOrNull()
    val numberOfSnakes = x!!.toInt()
    val snakePositions = mutableListOf<Int>()
    snakes(numberOfSnakes, snakePositions)
    return snakePositions
}

private fun ladders(numberOfLadders: Int, ladderPositions: MutableList<Int>) {
    for (i in 1..numberOfLadders) {
        var y = readlnOrNull()
        var z = y?.split(" ")
        val ladder = mutableListOf<Int>()
        z?.mapNotNullTo(ladder) { it.toIntOrNull() }
        if (isLadderPosCorrect(ladder)){
        ladderPositions.add(ladder[0])
        ladderPositions.add(ladder[1])}

    }
}

fun isLadderPosCorrect(ladder: MutableList<Int>): Boolean {
    if (ladder[0] < ladder[1]) {
        return true;
    }
    return false
}

fun isSnakesPosCorrect(snake: MutableList<Int>):Boolean{
    if(snake[0]>snake[1]){
        return true;
    }
    return false;
}
private fun snakes(numberOfSnakes: Int, snakePositions: MutableList<Int>) {
    for (i in 1..numberOfSnakes) {
        var y = readlnOrNull()
        var z = y?.split(" ")
        val snake = mutableListOf<Int>()
        z?.mapNotNullTo(snake) { it.toIntOrNull() }
        if(isSnakesPosCorrect(snake)){
        snakePositions.add(snake[0])
        snakePositions.add(snake[1])
    }}
}


private fun playerInput(): MutableList<String> {
    val numPlayers = readln().toInt()
    val players = mutableListOf<String>()
    for (i in 1..numPlayers) {
        val name = readln()
        players.add(name)
    }
    return players
}

fun checkWin(playerPositions: MutableMap<String, Int>): String? {
    for (player in playerPositions) {
        if (player.value == 100) {
            return player.key
        }
    }
    return null
}

fun diceRoll(): Int {
    return (1..6).random()
}

fun updatePosition(
    currentPosition: Int,
    diceRoll: Int,
    ladderPositions: MutableList<Int>,
    snakePositions: MutableList<Int>
)
        : Int {
    var pos = currentPosition
    if (!boundaryCheck(pos, diceRoll)) {
        return pos
    }
    pos += diceRoll
    if (pos in ladderPositions) {
        pos = ladderPos(ladderPositions, pos)
    }
    else if (pos in snakePositions) {
        pos = snakePos(snakePositions, pos)
    }

    return pos
}


