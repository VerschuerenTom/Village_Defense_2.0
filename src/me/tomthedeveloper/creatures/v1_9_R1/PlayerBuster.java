package me.tomthedeveloper.creatures.v1_9_R1;

import me.tomthedeveloper.utils.CreatureUtils;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Tom on 15/08/2014.
 */
public class PlayerBuster extends EntityZombie {

    public int damage;
    private float bw;

    @SuppressWarnings("rawtypes")
    public PlayerBuster(org.bukkit.World world) {

        super(((CraftWorld) world).getHandle());
        this.bw = 1.5F; //Change this to your liking. this is were you set the speed
        this.damage = 15; // set the damage
        //There's also a ton of options of you do this. play around with it


        Set goalB = (Set) CreatureUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        Set goalC = (Set) CreatureUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        Set targetB = (Set) CreatureUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        Set targetC = (Set) CreatureUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        ((Navigation) getNavigation()).b(true);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, this.bw, false)); // this one to attack human
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, this.bw));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F)); // this one to look at human
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true, true)); // this one to target human
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true, false));
        this.setHealth(1);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100.0D);
        return;
    }

    @Override
    public void setOnFire(int i) {
        // don't set on fire
        //super.setOnFire(i);
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (damagesource != null && damagesource.getEntity() != null && damagesource.getEntity().getBukkitEntity().getType() == EntityType.PLAYER) {

            ItemStack[] itemStack = new ItemStack[]{new ItemStack(Material.ROTTEN_FLESH)};
            Bukkit.getServer().getPluginManager().callEvent(new EntityDeathEvent((LivingEntity) this.getBukkitEntity(), Arrays.asList(itemStack), expToDrop));
            Player player = (Player) damagesource.getEntity().getBukkitEntity();
            org.bukkit.entity.Entity primed = getBukkitEntity().getWorld().spawnEntity(getBukkitEntity().getLocation(), EntityType.PRIMED_TNT);
            this.die();


            return true;

        } else {
            super.damageEntity(damagesource, f);
            return false;
        }
    }
}
