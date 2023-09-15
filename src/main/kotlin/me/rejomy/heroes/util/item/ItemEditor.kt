package me.rejomy.heroes.util.item

import me.rejomy.heroes.util.toColor
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

open class ItemEditor {

    fun lore(array: Array<String>): List<String> {
        val list = ArrayList<String>()
        for(i in array)
            list.add(toColor(i))
        return list
    }

    fun createItemStack(name: String, lore: Array<String>, material: Material, amount: Int): ItemStack {

        val item = ItemStack(material, amount)

        return createItemStack(name, lore.toList(), item, amount)

    }

    fun createItemStack(name: String, lore: List<String>?, material: Material, amount: Int): ItemStack {

        val item = ItemStack(material, amount)

        return createItemStack(name, lore, item, amount)

    }

    fun createItemStack(name: String, lore: List<String>?, item: ItemStack, amount: Int): ItemStack {

        item.amount = amount

        val meta = item.itemMeta

        meta.displayName = toColor(name)

        if (lore != null) {
            lore.forEach {
                toColor(it)
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