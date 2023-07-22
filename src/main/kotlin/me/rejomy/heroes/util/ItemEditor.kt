package me.rejomy.heroes.util

import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

open class ItemEditor {

    fun lore(array: Array<String>): List<String> {
        val list = ArrayList<String>()
        for(i in array)
            list.add(replaceColor(i))
        return list
    }

    fun createItemStack(name: String, lore: List<String>?, material: Material, amount: Int): ItemStack {
        val item = ItemStack(material, amount)
        val meta = item.itemMeta

        meta.displayName = replaceColor(name)

        if (lore != null) {
            lore.forEach {
                replaceColor(it)
            }
            meta.lore = lore
        }

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_ENCHANTS)

        item.itemMeta = meta
        return item
    }

    fun createItemStack(name: String, lore: List<String>?, itemStack: ItemStack, amount: Int): ItemStack {
        val item = itemStack
        item.amount = amount
        val meta = item.itemMeta

        meta.displayName = replaceColor(name)

        if (lore != null) {
            lore.forEach {
                replaceColor(it)
            }
            meta.lore = lore
        }

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_ENCHANTS)

        item.itemMeta = meta
        return item
    }

    fun createItemStack(name: String, lore: List<String>?, material: Material, amount: Int, data: Short): ItemStack {
        val item: ItemStack = createItemStack(name, lore, material, amount)

        if(data > 0) item.durability = data

        return item
    }

}