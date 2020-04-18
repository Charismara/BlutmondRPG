package de.blutmondgilde.blutmondrpg.capabilities;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.capabilities.mobscaling.IMobScaling;
import de.blutmondgilde.blutmondrpg.capabilities.mobscaling.MobScaling;
import de.blutmondgilde.blutmondrpg.capabilities.mobscaling.MobScalingProvider;
import de.blutmondgilde.blutmondrpg.capabilities.mobscaling.MobScalingStorage;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.IModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClass;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassProvider;
import de.blutmondgilde.blutmondrpg.capabilities.modclass.ModClassStorage;
import de.blutmondgilde.blutmondrpg.enums.ClassLevel;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class CustomCapabilityManager {
    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(IModClass.class, new ModClassStorage(), ModClass::new);
        CapabilityManager.INSTANCE.register(IMobScaling.class, new MobScalingStorage(), MobScaling::new);
    }

    @SubscribeEvent
    public void onAttachClassCapabilities(final AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof PlayerEntity)) return;
        final PlayerEntity player = (PlayerEntity) e.getObject();
        if (player instanceof FakePlayer) return;

        e.addCapability(new ResourceLocation(Ref.MOD_ID, "modclassdata"), new ModClassProvider());

        Ref.LOGGER.debug("Added Class Information to Player: " + player.getUniqueID().toString());
    }

    @SubscribeEvent
    public void onAttachMobCapabilities(final AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof MonsterEntity)) return;

        e.addCapability(new ResourceLocation(Ref.MOD_ID, "mobscalingdata"), new MobScalingProvider());
        Ref.LOGGER.debug("Attached Mob Capability to " + e.getObject().getType().getRegistryName());
    }

    @SubscribeEvent
    public void onMobSpawn(final EntityJoinWorldEvent e) {
        if (!(e.getEntity() instanceof MonsterEntity)) return;
        final MonsterEntity entity = (MonsterEntity) e.getEntity();
        final List<ServerPlayerEntity> playerList = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayers();

        ClassLevel highestLevel = ClassLevel.L1;
        PlayerEntity highestLevelPlayer = playerList.get(0);
        for (PlayerEntity player : playerList) {
            if (getDistance(entity.getPosition(), player.getPosition()) > 128) return;
            final IModClass playerCap = player.getCapability(ModClassProvider.MOD_CLASS_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while seeking the highest level Player around a Mob. PlayerType: " + player.getClass()));
            if (playerCap.getClassLevel().getId() < highestLevel.getId()) return;
            highestLevelPlayer = player;
            highestLevel = playerCap.getClassLevel();
        }

        IMobScaling monsterCap = entity.getCapability(MobScalingProvider.MOB_SCLAING_CAPABILITY).orElseThrow(() -> new IllegalStateException("Exeption while loading MonsterEntity Capability. MonsterEntity: " + entity.getType().getRegistryName()));
        monsterCap.setClosestPlayer(highestLevelPlayer);

        Ref.LOGGER.debug("Added Player Information from " + highestLevelPlayer.getName().getString() + " to " + entity.getType().getRegistryName());
    }


    private double getDistance(BlockPos start, BlockPos end) {
        return Math.pow(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getZ() - start.getZ(), 2), 0.5);
    }
}
