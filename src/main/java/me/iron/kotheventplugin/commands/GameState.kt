package me.iron.kotheventplugin.commands

import me.iron.kotheventplugin.PluginManager
import me.iron.kotheventplugin.managers.GameManager
import me.iron.kotheventplugin.utils.GameStates
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class GameState(private var plugin: PluginManager) : CommandExecutor, Listener {

    private val gameManager = GameManager(plugin)

    private val invName = "Game State Selector"

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.view.title != invName) { return }
        val player = event.whoClicked as Player
        val slot = event.slot
        val currentPlayerInventory = player.inventory
        event.isCancelled = true
        when (slot) {
            10 -> gameManager.setGameState(GameStates.LOBBY)
            12 -> gameManager.setGameState(GameStates.PREP)
            14 -> gameManager.setGameState(GameStates.START)
            16 -> gameManager.setGameState(GameStates.END)
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can run this command.")
            return true
        }

        val inventory = Bukkit.createInventory(sender, 9 * 3, invName)
        inventory.setItem(10, getItem(ItemStack(Material.WHITE_WOOL), "&9LOBBY", "&aClick while in Lobby!"))
        inventory.setItem(12, getItem(ItemStack(Material.BLUE_WOOL), "&9PREP", "&aClick to Start Grace!"))
        inventory.setItem(14, getItem(ItemStack(Material.GREEN_WOOL), "&9START", "&aClick to Start Main Event!"))
        inventory.setItem(16, getItem(ItemStack(Material.RED_WOOL), "&9END", "&aClick to End Main Event!"))
        sender.openInventory(inventory)
        return true
    }

    private fun getItem(item: ItemStack, name: String, vararg lore: String): ItemStack? {
        val meta = item.itemMeta
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name))
        val lores: MutableList<String> = ArrayList()
        for (s in lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s))
        }
        meta.lore = lores
        item.itemMeta = meta
        return item
    }
}