package me.iron.kotheventplugin.commands

import me.iron.kotheventplugin.managers.BoardManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class TeamSelect : CommandExecutor {

    private var boardManager: BoardManager = BoardManager()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can run this command.")
            return true
        }

        if (args?.isEmpty() == true || args == null) {
            sender.sendMessage("You need to specify a team to join.")
            return false
        }

        val scoreboard = boardManager.scoreboard

        println("you have stated " + args[0])

        when (args[0]) {
            "red" -> {
                println("red")
                println(scoreboard.teams)
                (sender as Entity).let { scoreboard.getTeam("red")?.addEntity(it) }

            }
            "blue" -> (sender as Entity).let { scoreboard.getTeam("blue")?.addEntity(it) }
        }

        sender.sendMessage("You have joined team " + (scoreboard.getEntityTeam(sender as Entity)?.name ?: "none"))

        return true
    }
}