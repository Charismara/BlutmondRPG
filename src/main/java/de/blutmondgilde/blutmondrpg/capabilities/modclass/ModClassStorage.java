package de.blutmondgilde.blutmondrpg.capabilities.modclass;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ModClassStorage implements Capability.IStorage<IModClass> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IModClass> capability, IModClass instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();

        tag.putInt("modClassId", instance.getBasicClass().getId());
        tag.putInt("modClassLevelId", instance.getClassLevel().getId());
        tag.putDouble("modClassExp", instance.getClassExp());
        tag.putFloat("modMaxHp", instance.getMaxHP());
        tag.putFloat("modMaxMana", instance.getMaxMana());
        tag.putFloat("modCurrenMana", instance.getCurrentMana());
        tag.putFloat("modMelee", instance.getMeleeDmg());
        tag.putFloat("modMagic", instance.getMagicDmg());
        tag.putFloat("modBow", instance.getBowDmg());

        return tag;
    }

    @Override
    public void readNBT(Capability<IModClass> capability, IModClass instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;

        instance.setBasicClass(tag.getInt("modClassId"));
        instance.setClassLevel(tag.getInt("modClassLevelId"));
        instance.setClassExp(tag.getDouble("modClassExp"));
        instance.setMaxHP(tag.getFloat("modMaxHp"));
        instance.setMaxMana(tag.getFloat("modMaxMana"));
        instance.setCurrentMana(tag.getFloat("modCurrenMana"));
        instance.setMeleeDmg(tag.getFloat("modMelee"));
        instance.setMagicDmg(tag.getFloat("modMagic"));
        instance.setBowDmg(tag.getFloat("modBow"));
    }
}
