package de.blutmondgilde.blutmondrpg.event;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.enums.ClassLevel;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

import java.util.UUID;

public class GainExpEvent extends Event {

    public GainExpEvent(PlayerEntity playerEntity, double amountInPercent) {
        handleSinglePlayer(playerEntity, amountInPercent, playerEntity.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Could not load player level")).getClassLevel());
    }

    public GainExpEvent(PlayerEntity playerEntity, double amount, ClassLevel mobLevel) {
        IGroup groupCap = playerEntity.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exception while loading group information."));
        if (groupCap.getMemberList().size() > 1) {
            handleGroup(playerEntity, amount, mobLevel);
        } else {
            handleSinglePlayer(playerEntity, amount, mobLevel);
        }
    }

    private void handleSinglePlayer(PlayerEntity playerEntity, double amount, ClassLevel mobLevel) {
        IModClass cap = playerEntity.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exception while loading player class information"));
        double expInPercent = amount * getDiffFactor(mobLevel.getId() - cap.getClassLevel().getId());
        double neededExpToLevelUp = ClassLevel.getLevelFromId(cap.getClassLevel().getId() + 1).getExp();
        double exp = neededExpToLevelUp * expInPercent;
        cap.addClassExp(exp);
        CustomNetworkManager.syncPlayerClass(playerEntity);
        Ref.LOGGER.debug("Added " + exp + " Exp to player " + playerEntity.getName().getString() + " which level diff: " + (mobLevel.getId() - cap.getClassLevel().getId()));
    }

    private void handleGroup(PlayerEntity playerEntity, double amount, ClassLevel mobLevel) {
        IGroup group = playerEntity.getCapability(GroupProvider.GROUP_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exception while loading group information"));
        for (UUID uuid : group.getMemberList()) {
            final PlayerEntity player = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayerByUUID(uuid);
            handleSinglePlayer(playerEntity, amount, mobLevel);
        }
    }

    private double getDiffFactor(int levelDiff) {
        if (levelDiff < -5) {
            return 0.0;
        } else {
            if (levelDiff < -3) {
                return 0.25;
            } else {
                if (levelDiff < -1) {
                    return 0.5;
                } else {
                    if (levelDiff == -1) {
                        return 0.75;
                    } else {
                        if (levelDiff == 0) {
                            return 1;
                        } else {
                            if (levelDiff >= 20) {
                                return 100;
                            } else {
                                if (levelDiff >= 10) {
                                    return 10;
                                } else {
                                    if (levelDiff >= 5) {
                                        return 4;
                                    } else {
                                        if (levelDiff >= 2) {
                                            return 2;
                                        } else {
                                            return 1.5;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
