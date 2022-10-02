package me.iron.kotheventplugin.managers

import me.iron.kotheventplugin.PluginManager
import me.iron.kotheventplugin.utils.GameStates
import me.iron.kotheventplugin.utils.RepeatTask
import me.iron.kotheventplugin.utils.TeamNameUtil
import org.bukkit.*
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scoreboard.Team
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

class GameManager(private var plugin: PluginManager) {

    private var gameState: GameStates = GameStates.LOBBY

    companion object {
        private var isScoringEnabled: AtomicBoolean = AtomicBoolean(false)
    }

    fun startScoreboardTask() {
        BoardManager().setupScoreboard()
        Bukkit.setDefaultGameMode(GameMode.SPECTATOR)
        val repeatTask = RepeatTask(plugin)
        val task = repeatTask.generateRepeatTask({ updatePlayerScoreboards() }, 20)
    }

    private fun resetScores() {
        BoardManager.scoreboard.teams.forEach {
            BoardManager.scoreboard.getObjective("score")!!.getScore(TeamNameUtil.styleTeamDisplayName(it.name)).score = 0
        }
    }

    private fun updatePlayerScoreboards() {
        val players = Bukkit.getOnlinePlayers()
        // add the plugin scoreboard to every player
        players.forEach { it.scoreboard = BoardManager.scoreboard }
        // continue if scoring is enabled
        if (!isScoringEnabled.get()) { return }
        // get all players and their altitudes
        var playerAltitudes = players.map { Pair(it, it.location.blockY) } as MutableList<Pair<Player, Int>>
        // get the highest player
        fun findHighestPlayer(playerAltitudes: MutableList<Pair<Player, Int>>): Pair<Player, Int> {
            return playerAltitudes.maxBy { it.second }
        }
        val highestPlayer = findHighestPlayer(playerAltitudes)
        // stop points from being earned at world height
        if (highestPlayer.second >= highestPlayer.first.world.maxHeight - 1) { return }
        // get that player's team
        fun findScoringTeam(player: Player): Team? {
            return highestPlayer.first.let { BoardManager.scoreboard.getEntityTeam(it as Entity) }
        }
        var scoringTeam = findScoringTeam(highestPlayer.first)
        // if the highest player is not on a team, then get the next
        while (scoringTeam == null) {
            if (playerAltitudes.isEmpty()) { return }
            playerAltitudes.remove(highestPlayer)
            scoringTeam = findScoringTeam(findHighestPlayer(playerAltitudes).first)
        }
        // add one point to the scoreboard for that team
        BoardManager.scoreboard.getObjective("score")!!.getScore(TeamNameUtil.styleTeamDisplayName(scoringTeam.name)).score += 1
    }


    private val borderSize = 70.0

    fun setGameState(gameState: GameStates) {
        this.gameState = gameState
        when (gameState) {
            GameStates.LOBBY -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.gameMode = GameMode.SPECTATOR
                    if (it.world.getGameRuleValue(GameRule.KEEP_INVENTORY) == false) {
                        it.world.setGameRule(GameRule.KEEP_INVENTORY, true)
                    }
                    if (it.world.worldBorder.size != 2 * borderSize) {
                        it.world.worldBorder.size = 2 * borderSize
                    }
                    it.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE)
                }
            }

            // disable Kit GUI
            GameStates.PREP -> {
                Bukkit.getLogger().info("reset scores")
                resetScores()
                Bukkit.getOnlinePlayers().forEach {
                    it.gameMode = GameMode.SURVIVAL
                    it.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 300, 1000, true))
                    it.teleport(Location(it.world,
                        it.world.spawnLocation.toBlockLocation().blockX + borderSize * Random.nextDouble() - borderSize,
                        256.0,
                        it.world.spawnLocation.toBlockLocation().blockX + borderSize * Random.nextDouble() - borderSize))

                }
            }

            // allow points to be earned
            GameStates.START -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title @a \"STARTING!\"")
                Bukkit.getLogger().info("activated scoring")
                isScoringEnabled.set(true) }

            // stop point accumulation
            GameStates.END -> {
                Bukkit.getLogger().info("deactivated scoring")
                isScoringEnabled.set(false)
                Bukkit.getOnlinePlayers().forEach {
                    it.gameMode = GameMode.SPECTATOR
                }
            }
        }

    }
}