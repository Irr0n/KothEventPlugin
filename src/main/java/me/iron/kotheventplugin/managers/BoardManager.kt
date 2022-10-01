package me.iron.kotheventplugin.managers

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.scoreboard.*

class BoardManager {

    companion object {
        private val scoreboardManager = Bukkit.getScoreboardManager()
        val scoreboard = this.scoreboardManager.newScoreboard
        private var objective = scoreboard.registerNewObjective("score", Criteria.DUMMY, "Score")
    }


    fun setupScoreboard() {
        objective.displaySlot = DisplaySlot.SIDEBAR
        // todo: name colors with prefix
        val red = scoreboard.registerNewTeam("red")
        red.color(NamedTextColor.RED)
        red.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.RED.toString() + "Red").score = 0
        val blue = scoreboard.registerNewTeam("blue")
        blue.color(NamedTextColor.BLUE)
        blue.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.BLUE.toString() + "Blue").score = 0
        val green = scoreboard.registerNewTeam("green")
        green.color(NamedTextColor.GREEN)
        green.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.GREEN.toString() + "Green").score = 0
        val yellow = scoreboard.registerNewTeam("yellow")
        yellow.color(NamedTextColor.YELLOW)
        yellow.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.YELLOW.toString() + "Yellow").score = 0
        val orange = scoreboard.registerNewTeam("orange")
        orange.color(NamedTextColor.GOLD)
        orange.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.GOLD.toString() + "Orange").score = 0
        val purple = scoreboard.registerNewTeam("purple")
        purple.color(NamedTextColor.DARK_PURPLE)
        purple.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.DARK_PURPLE.toString() + "Purple").score = 0
        val pink = scoreboard.registerNewTeam("pink")
        pink.color(NamedTextColor.LIGHT_PURPLE)
        pink.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.LIGHT_PURPLE.toString() + "Pink").score = 0
        val cyan = scoreboard.registerNewTeam("cyan")
        cyan.color(NamedTextColor.AQUA)
        cyan.setAllowFriendlyFire(false)
        objective.getScore(ChatColor.AQUA.toString() + "Cyan").score = 0
    }


    fun updateScoreboard() {}

}