package de.blutmondgilde.blutmondrpg.handler;

import de.blutmondgilde.blutmondrpg.capabilities.mobscaling.IMobScaling;
import de.blutmondgilde.blutmondrpg.capabilities.mobscaling.MobScalingProvider;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.MonsterEntity;

import java.util.UUID;

public class MobHandler {
    private static final UUID HP_MODIFIER_ID = UUID.fromString("2fcc35d1-3f05-40f0-8aa5-2931d714e8b6");
    private static final UUID MELEE_MODIFIER_ID = UUID.fromString("cdff4b2e-a85e-4a91-8ed1-97d1851a509c");

    public static void applyMobStats(MonsterEntity entity) {
        IMobScaling mobScaling = entity.getCapability(MobScalingProvider.MOB_SCLAING_CAPABILITY).orElseThrow(() -> new IllegalStateException("Couldn't get mob scaling capability"));

        IAttributeInstance entityHP = entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        IAttributeInstance entityDmg = entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

        double HpValue = entityHP.getBaseValue() * mobScaling.getMaxHP() - entityHP.getBaseValue();
        double DmgValue = entityDmg.getBaseValue() * mobScaling.getDamage() - entityDmg.getBaseValue();

        AttributeModifier hpMod = new AttributeModifier(HP_MODIFIER_ID, Ref.MOD_ID + "mob.hp", HpValue, AttributeModifier.Operation.ADDITION);
        AttributeModifier dmgMod = new AttributeModifier(MELEE_MODIFIER_ID, Ref.MOD_ID + "mob.melee", DmgValue, AttributeModifier.Operation.ADDITION);

        try {
            entityHP.applyModifier(hpMod);
            entityDmg.applyModifier(dmgMod);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }
}
