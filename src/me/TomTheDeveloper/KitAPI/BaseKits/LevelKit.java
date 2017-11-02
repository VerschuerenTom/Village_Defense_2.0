package me.TomTheDeveloper.KitAPI.BaseKits;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import me.TomTheDeveloper.Handlers.ConfigurationManager;
import me.TomTheDeveloper.Utils.Util;
import pl.Plajer.GameAPI.LanguageManager;

/**
 * Created by Tom on 14/08/2014.
 */
public abstract class LevelKit extends Kit {



    int level;

    public void setLevel(int level){
        this.level = level;
        FileConfiguration config = ConfigurationManager.getConfig("Kits");
        String name = getClass().getName().substring(getClass().getName().indexOf("K"));
        if(!config.contains(name)) {
            config.set("Required-Level." + name, level);
            try {
                config.save(ConfigurationManager.getFile("Kits"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            this.level = config.getInt("Required-Level." + name);
        }
    }

    public int getLevel(){
        return level;
    }

    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(getMaterial());
        setItemNameAndLore(itemStack, getName(), getDescription());
        Util.addLore(itemStack, LanguageManager.getLanguageFile().get("Unlocks-at-level").toString().replaceAll("(&([a-f0-9]))", "\u00A7$2").replaceAll("%NUMBER%",Integer.toString(getLevel())));
        return itemStack;
    }
}
