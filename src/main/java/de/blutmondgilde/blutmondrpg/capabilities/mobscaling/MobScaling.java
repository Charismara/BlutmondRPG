package de.blutmondgilde.blutmondrpg.capabilities.mobscaling;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.enums.ClassLevel;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.util.FakePlayer;

public class MobScaling implements IMobScaling {
    private PlayerEntity closestPlayer;
    private float maxHP, attackDamage, exp;

    public MobScaling() {
        this.closestPlayer = new FakePlayer(BlutmondRPG.getMinecraftServer().getWorld(DimensionType.OVERWORLD), Ref.FAKE_PLAYER);
        this.maxHP = 9999;
        this.attackDamage = 0;
        this.exp = 0;
    }

    @Override
    public PlayerEntity getClosestPlayer() {
        return this.closestPlayer;
    }

    @Override
    public void setClosestPlayer(PlayerEntity player) {
        this.closestPlayer = player;
        IModClass playerCap = player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Tried to get class capability from a non-player entity"));
        ClassLevel level = playerCap.getClassLevel();
        this.maxHP = level.getId() * 0.75F;
        this.attackDamage = level.getId();
        this.exp = 0.05F;
    }

    @Override
    public float getMaxHP() {
        return this.maxHP;
    }

    @Override
    public void setMaxHP(float hp) {
        this.maxHP = hp;
    }

    @Override
    public float getDamage() {
        return this.attackDamage;
    }

    @Override
    public void setDamage(float damage) {
        this.attackDamage = damage;
    }

    @Override
    public float getExp() {
        return this.exp;
    }

    @Override
    public void setExp(float exp) {
        this.exp = exp;
    }
}
