package fr.lejazzy.rtp.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class GuiRTP {

    public Inventory createInventory() {
        Inventory inventory = Bukkit.createInventory(null, 27, "RTP Menu");

        ItemStack item = new ItemStack(Material.DIAMOND);
        inventory.setItem(13, item);

        return inventory;
    }
}