package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.Event;

public class SyncClassDataEvent extends Event {

    public SyncClassDataEvent(int classId, int classLevel, double classExp, float maxHP, float maxMana, float mana, float melee, float magic, float bow) {

        IModClass cap = Minecraft.getInstance().player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while syncing Class capability"));
        cap.setBasicClass(classId);
        cap.setClassLevel(classLevel);
        cap.setClassExp(classExp);
        cap.setMaxHP(maxHP);
        cap.setMaxMana(maxMana);
        cap.setCurrentMana(mana);
        cap.setMeleeDmg(melee);
        cap.setMagicDmg(magic);
        cap.setBowDmg(bow);

        Ref.LOGGER.debug("Class Information Synced");
    }
}
