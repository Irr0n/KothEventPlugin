package me.iron.kotheventplugin.managers

import me.iron.kotheventplugin.PluginManager
import me.iron.kotheventplugin.utils.RepeatTask
import org.bukkit.Bukkit
import org.bukkit.entity.Entity

class GameManager(plugin: PluginManager) {

    private var boardManager: BoardManager? = null

    private var plugin: PluginManager? = null

    private var gameState: GameState = GameState.LOBBY


    fun startScoreboardTask() {
        boardManager?.setupScoreboard()
        RepeatTask(plugin)
        val task = RepeatTask({ updatePlayerScoreboards() }, 20)
    }

    fun updatePlayerScoreboards() {
        for (player in Bukkit.getOnlinePlayers()) {

            println(player.scoreboard.getEntityTeam((player as Entity)))
            player.scoreboard = boardManager?.getScoreboard()!!
        }
    }


    fun setGameState(gameState: GameState) {
        this.gameState = gameState
        when (gameState) {
            GameState.LOBBY, GameState.PREP, GameState.START, GameState.END -> {}
            else -> {}
        }
    }
}