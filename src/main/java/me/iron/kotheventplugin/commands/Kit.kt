package me.iron.kotheventplugin.commands

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

class Kit() : CommandExecutor, Listener {

    private val invName = "Kit Selector"


    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.view.title != invName) {
            return
        }
        val player = event.whoClicked as Player
        val slot = event.slot
        event.isCancelled = true
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can run this command.")
            return true
        }
        val inv = Bukkit.createInventory(sender, 9 * 3, invName)
        inv.setItem(11, getItem(ItemStack(Material.IRON_SWORD), "&9Combat", "&aClick to Join!", "&aITEMSGOHERE"))
        inv.setItem(13, getItem(ItemStack(Material.FISHING_ROD), "&9Utility", "&aClick to Join!", "&aITEMSGOHERE"))
        inv.setItem(15, getItem(ItemStack(Material.IRON_PICKAXE), "&9Gatherer", "&aClick to Join!", "&aITEMSGOHERE"))
        sender.openInventory(inv)
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