package me.iron.kotheventplugin.managers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.ScoreboardManager

class BoardManager {

    private val scoreboardManager = Bukkit.getScoreboardManager()

    private val scoreboard = scoreboardManager.newScoreboard
    var objective = scoreboard.registerNewObjective("score", "dummy", "Score")

    fun setupScoreboard() {
        objective.displaySlot = DisplaySlot.SIDEBAR

        val red = scoreboard.registerNewTeam("red")
        red.prefix = ChatColor.RED.toString() + ""
        red.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.RED.toString() + "Red").score = 0
        val blue = scoreboard.registerNewTeam("blue")
        blue.prefix = ChatColor.BLUE.toString() + ""
        blue.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.BLUE.toString() + "Blue").score = 0
        val green = scoreboard.registerNewTeam("green")
        green.prefix = ChatColor.GREEN.toString() + ""
        green.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.GREEN.toString() + "Green").score = 0
        val yellow = scoreboard.registerNewTeam("yellow")
        yellow.prefix = ChatColor.YELLOW.toString() + ""
        yellow.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.YELLOW.toString() + "Yellow").score = 0
        val orange = scoreboard.registerNewTeam("orange")
        orange.prefix = ChatColor.GOLD.toString() + ""
        orange.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.GOLD.toString() + "Orange").score = 0
        val purple = scoreboard.registerNewTeam("purple")
        purple.prefix = ChatColor.DARK_PURPLE.toString() + ""
        purple.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.DARK_PURPLE.toString() + "Purple").score = 0
        val pink = scoreboard.registerNewTeam("pink")
        pink.prefix = ChatColor.LIGHT_PURPLE.toString() + ""
        pink.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.LIGHT_PURPLE.toString() + "Pink").score = 0
        val cyan = scoreboard.registerNewTeam("cyan")
        cyan.prefix = ChatColor.AQUA.toString() + ""
        cyan.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.AQUA.toString() + "Cyan").score = 0
    }


    fun updateScoreboard() {}

    fun getScoreboardManager(): ScoreboardManager? {
        return scoreboardManager
    }

    fun getScoreboard(): Scoreboard? {
        return scoreboard
    }
}