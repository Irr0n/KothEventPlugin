package me.iron.kotheventplugin.commands

import me.iron.kotheventplugin.managers.BoardManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class TeamSelect : CommandExecutor {

    private var boardManager: BoardManager? = null

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can run this command.")
            return true
        }

        val scoreboard = boardManager?.getScoreboard()

        when (args?.get(0)) {
            "red" -> (sender as Entity?)?.let { scoreboard?.getTeam("red")?.addEntity(it) }
            "blue" -> (sender as Entity?)?.let { scoreboard?.getTeam("blue")?.addEntity(it) }
        }
        return true
    }
}