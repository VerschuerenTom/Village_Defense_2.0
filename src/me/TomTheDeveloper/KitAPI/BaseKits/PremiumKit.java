package me.TomTheDeveloper.KitAPI.BaseKits;

import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Utils.Util;

/**
 * Created by Tom on 25/07/2014.
 */
public abstract class PremiumKit extends Kit {

    private int pointsNeeded;

    protected PremiumKit() {
    }


    public int getPointsNeeded() {
        return pointsNeeded;
    }



    public void setPointsNeeded(int pointsNeeded) {
        this.pointsNeeded = pointsNeeded;
    }

    @Override
    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(getMaterial());
        setItemNameAndLore(itemStack, getName(), getDescription());
        Util.addLore(itemStack, ChatManager.colorMessage("Kits.Kit-Menu.Locked-Lores.Unlock-In-Store"));
        return itemStack;
    }
}
