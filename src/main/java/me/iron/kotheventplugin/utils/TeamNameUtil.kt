package me.iron.kotheventplugin.utils

import org.bukkit.ChatColor

class TeamNameUtil {

    companion object {
        // return the color-styled name for each team
        fun styleTeamDisplayName(teamName: String): String {
            fun capitalizeFirstLetter(teamName: String): String {
                return teamName[0].uppercase() + teamName.substring(1)
            }
            if (teamName == "orange") { return ChatColor.GOLD.toString() + capitalizeFirstLetter(teamName) }
            if (teamName == "cyan") { return ChatColor.AQUA.toString() + capitalizeFirstLetter(teamName) }
            if (teamName == "purple") { return ChatColor.DARK_PURPLE.toString() + capitalizeFirstLetter(teamName) }
            if (teamName == "pink") { return ChatColor.LIGHT_PURPLE.toString() + capitalizeFirstLetter(teamName) }
            return ChatColor.valueOf(teamName.uppercase()).toString() + capitalizeFirstLetter(teamName)
        }
    }
}