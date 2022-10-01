package me.iron.kotheventplugin.managers

import me.iron.kotheventplugin.PluginManager
import me.iron.kotheventplugin.utils.RepeatTask
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.scheduler.BukkitRunnable

class GameManager(private var plugin: PluginManager) {

    private var boardManager: BoardManager = BoardManager()

    private var gameState: GameState = GameState.LOBBY


    fun startScoreboardTask() {
        boardManager.setupScoreboard()
        val repeatTask = RepeatTask(plugin)
        val task = repeatTask.generateRepeatTask({ updatePlayerScoreboards() }, 20)
    }

    fun updatePlayerScoreboards() {
        for (player in Bukkit.getOnlinePlayers()) {

            // println(player.scoreboard.getEntityTeam((player as Entity)))
            player.scoreboard = boardManager.getScoreboard()!!
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