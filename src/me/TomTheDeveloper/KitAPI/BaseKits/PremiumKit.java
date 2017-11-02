package me.TomTheDeveloper.KitAPI.BaseKits;

import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.Utils.Util;
import pl.Plajer.GameAPI.LanguageManager;

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
        Util.addLore(itemStack, LanguageManager.getLanguageFile().get("Unlock-This-Kit-In-The-Store").toString());
        return itemStack;
    }
}
