package me.TomTheDeveloper;

import net.minecraft.server.v1_8_R3.Block;

/**
 * Created by Tom on 14/08/2014.
 */
public class CastlePort {


    private int MAX;
    private int MIN;
    private int HEALTH;
    @SuppressWarnings("unused")
	private Block block;

    public CastlePort(Block block) {
        this.block = block;
    }


    public int getMAX() {
        return MAX;
    }

    public void setMAX(int MAX) {
        this.MAX = MAX;
    }

    public int getMIN() {
        return MIN;
    }

    public void setMIN(int MIN) {
        this.MIN = MIN;
    }

    public int getHEALTH() {
        return HEALTH;
    }

    public void setHEALTH(int HEALTH) {
        this.HEALTH = HEALTH;
    }


}
