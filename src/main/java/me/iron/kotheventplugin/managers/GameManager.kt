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
import kotlin.random.Random

class GameManager(private var plugin: PluginManager) {

    private var gameState: GameStates = GameStates.LOBBY

    private var scoring = false


    fun startScoreboardTask() {
        BoardManager().setupScoreboard()
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
        if (!this.scoring) { Bukkit.getLogger().info("scoring is $scoring so i wont loop"); return }
        // get all players and their altitudes
        var playerAltitudes = players.map { Pair(it, it.location.blockY) } as MutableList<Pair<Player, Int>>
        // get the highest player
        fun findHighestPlayer(playerAltitudes: MutableList<Pair<Player, Int>>): Pair<Player, Int> {
            return playerAltitudes.maxBy { it.second }
        }
        val highestPlayer = findHighestPlayer(playerAltitudes)
        Bukkit.getLogger().info("found " + highestPlayer.first.name)
        // get that player's team
        fun findScoringTeam(player: Player): Team? {
            return highestPlayer.first.let { BoardManager.scoreboard.getEntityTeam(it as Entity) }
        }
        var scoringTeam = findScoringTeam(highestPlayer.first)
        // if the highest player is not on a team, then get the next
        while (scoringTeam == null) {
            playerAltitudes.remove(highestPlayer)
            scoringTeam = findScoringTeam(findHighestPlayer(playerAltitudes).first)
        }
        // add one point to the scoreboard for that team
        Bukkit.getLogger().info("adding point to " + TeamNameUtil.styleTeamDisplayName(scoringTeam.name))
        BoardManager.scoreboard.getObjective("score")!!.getScore(TeamNameUtil.styleTeamDisplayName(scoringTeam.name)).score += 1
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
                Bukkit.getLogger().info("reset scores")
                resetScores()
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
            GameStates.START -> {
                Bukkit.getLogger().info("activated scoring")
                this.scoring = true }

            // stop point accumulation
            GameStates.END -> {
                Bukkit.getLogger().info("deactivated scoring")
                this.scoring = false
                Bukkit.getOnlinePlayers().forEach {
                    it.gameMode = GameMode.SPECTATOR
                }
            }
        }

    }
}