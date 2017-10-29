package me.TomTheDeveloper.Creatures.v1_8_R3;

import me.TomTheDeveloper.YoutuberInvasion;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 14/08/2014.
 */
public class GolemBuster extends EntityZombie {
    public int damage;
    private float bw;

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100D);
        return;
    }



    @SuppressWarnings("rawtypes")
    public GolemBuster(World world) {
        super(((CraftWorld) world).getHandle());
        this.bw = YoutuberInvasion.ZOMBIE_SPEED; //Change this to your liking. this is were you set the speed
        this.damage = 15; // set the damage
        //There's also a ton of options of you do this. play around with it


        List goalB = (List) getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();


        ((Navigation)getNavigation()).b(true);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathfinderGoalBreakDoorFaster(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, (float) (this.bw), false)); // this one to attack human
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, EntityIronGolem.class, (float) this.bw, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, (float) this.bw));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F)); // this one to look at human
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class,  true)); // this one to target human
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class,  false));
        this.setHealth(5);


    }

    @Override
    public void setOnFire(int i) {
        // don't set on fire
        //super.setOnFire(i);
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (damagesource != null && damagesource.getEntity() != null && damagesource.getEntity().getBukkitEntity().getType() == EntityType.IRON_GOLEM) {
            this.die();
            this.die();
            ItemStack[] itemStack = new ItemStack[]{new ItemStack(Material.ROTTEN_FLESH)};
            Bukkit.getServer().getPluginManager().callEvent(new EntityDeathEvent((LivingEntity) this.getBukkitEntity(), Arrays.asList(itemStack),expToDrop));
            IronGolem golem = (IronGolem) damagesource.getEntity().getBukkitEntity();
            //golem.getWorld().createExplosion(golem.getLocation(), 4);
            Entity primed= golem.getWorld().spawnEntity(golem.getLocation(), EntityType.PRIMED_TNT);



            return true;

        } else {
            super.damageEntity(damagesource, f);
            return false;
        }
    }
}
