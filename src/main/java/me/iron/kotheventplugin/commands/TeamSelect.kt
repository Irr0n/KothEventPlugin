package me.iron.kotheventplugin.commands

import me.iron.kotheventplugin.managers.BoardManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.lang.IllegalArgumentException

class TeamSelect : CommandExecutor {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can run this command.")
            return true
        }

        if (args?.isEmpty() == true || args == null) {
            sender.sendMessage("You need to specify a team to join.")
            return false
        }

        val team = args[0]

        val scoreboard = BoardManager.scoreboard

        try {
            (sender as Entity).let { scoreboard.getTeam(team)?.addEntity(it) }
            sender.sendMessage("You are now on the team " + (scoreboard.getEntityTeam(sender as Entity)?.name ?: "none"))
            return true
        } catch (_: IllegalArgumentException) {
            sender.sendMessage("There is no team named $team")
        }

        return false
    }
}