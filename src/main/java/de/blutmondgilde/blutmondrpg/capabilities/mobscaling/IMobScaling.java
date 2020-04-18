package de.blutmondgilde.blutmondrpg.capabilities.mobscaling;

import net.minecraft.entity.player.PlayerEntity;

public interface IMobScaling {
    PlayerEntity getClosestPlayer();

    void setClosestPlayer(PlayerEntity player);

    float getMaxHP();

    void setMaxHP(float hp);

    float getDamage();

    void setDamage(float damage);

    float getExp();

    void setExp(float exp);
}

