package me.TomTheDeveloper.Kits;

import me.TomTheDeveloper.Handlers.ChatManager;
import me.TomTheDeveloper.Handlers.UserManager;
import me.TomTheDeveloper.KitAPI.BaseKits.LevelKit;
import me.TomTheDeveloper.User;
import me.TomTheDeveloper.Utils.ArmorHelper;
import me.TomTheDeveloper.Utils.Items;
import me.TomTheDeveloper.Utils.Util;
import me.TomTheDeveloper.Utils.WeaponHelper;
import me.TomTheDeveloper.YoutuberInvasion;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.List;

/**
 * Created by Tom on 21/07/2015.
 */
public class LooterKit extends LevelKit implements Listener {


    private YoutuberInvasion plugin;

    public LooterKit(YoutuberInvasion plugin) {
        this.plugin = plugin;
        setName(ChatManager.getFromLanguageConfig("Looter-Kit-Name", ChatManager.PREFIX + "Looter"));
        List<String> description = Util.splitString(ChatManager.getFromLanguageConfig("Looter-Kit-Description", "Get one additional rotten flesh on every zombie kill!!"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        setLevel(8);
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        if(UserManager.getUser(player.getUniqueId()).getInt("level") >= this.getLevel() || player.isOp()|| player.hasPermission("villagefense.kit.looter"))
            return true;
        return false;
    }

    @Override
    public void giveKitItems(Player player) {
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.STONE, 10));
        ArmorHelper.setColouredArmor(Color.ORANGE, player);
        player.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 8));

    }

    @Override
    public Material getMaterial() {
        return Material.ROTTEN_FLESH;
    }

    @Override
    public void reStock(Player player) {

    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if(event.getEntity().getType() != EntityType.ZOMBIE)
            return;
        if(event.getEntity().getKiller() == null)
            return;
        Player player = event.getEntity().getKiller();
        if(plugin.getGameAPI().getGameInstanceManager().getGameInstance(player) == null)
            return;
        User user = UserManager.getUser(player.getUniqueId());
        if(user.getKit() instanceof LooterKit){
            player.getInventory().addItem(new ItemStack(Material.ROTTEN_FLESH,1));
        }

    }
}
