package me.tomthedeveloper.creatures.v1_7_R4;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

import me.tomthedeveloper.utils.CreatureUtils;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityVillager;
import net.minecraft.server.v1_7_R4.EntityZombie;
import net.minecraft.server.v1_7_R4.PathfinderGoalBreakDoor;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_7_R4.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_7_R4.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_7_R4.PathfinderGoalSelector;

/**
 * Created by Tom on 12/08/2014.
 */
public class Youtuber extends EntityZombie {

    private final PathfinderGoalBreakDoor bs = new PathfinderGoalBreakDoor(this);


    public Youtuber(org.bukkit.World world) {
        super(((CraftWorld) world).getHandle());
        List goalB = (List) CreatureUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) CreatureUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) CreatureUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) CreatureUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();


        getNavigation().b(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
        //this.goalSelector.a(4, new PathfinderGoalBreakFence(this));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.a(0.6F, 1.8F);
// this are its default goals.
    }


}
