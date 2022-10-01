package me.iron.kotheventplugin.managers

import me.iron.kotheventplugin.PluginManager
import me.iron.kotheventplugin.utils.GameStates
import me.iron.kotheventplugin.utils.RepeatTask
import org.bukkit.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

class GameManager(private var plugin: PluginManager) {

    private var gameState: GameStates = GameStates.LOBBY


    fun startScoreboardTask() {
        BoardManager().setupScoreboard()
        val repeatTask = RepeatTask(plugin)
        val task = repeatTask.generateRepeatTask({ updatePlayerScoreboards() }, 20)
    }

    fun updatePlayerScoreboards() {
        for (player in Bukkit.getOnlinePlayers()) {

            // println(player.scoreboard.getEntityTeam((player as Entity)))
            player.scoreboard = BoardManager.scoreboard
        }
    }


    private val borderSize = 200

    fun setGameState(gameState: GameStates) {
        this.gameState = gameState
        when (gameState) {
            GameStates.LOBBY -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.gameMode = GameMode.SPECTATOR
                    if (it.world.getGameRuleValue(GameRule.KEEP_INVENTORY) == false)
                    it.world.setGameRule(GameRule.KEEP_INVENTORY, true)
                }
            }

            // disable Kit GUI
            GameStates.PREP -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.gameMode = GameMode.SURVIVAL
                    it.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 300, 1000, true))
                    it.teleport(Location(it.world,
                        borderSize * Random.nextDouble() - borderSize,
                        256.0,
                        borderSize * Random.nextDouble() - borderSize))

                }
            }

            // allow points to be earned
            GameStates.START -> {}

            // stop point accumulation
            GameStates.END -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.gameMode = GameMode.SPECTATOR
                }
            }
        }

    }
}