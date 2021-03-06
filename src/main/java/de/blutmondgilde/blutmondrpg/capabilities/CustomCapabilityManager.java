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
import de.blutmondgilde.blutmondrpg.capabilities.party.Group;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupProvider;
import de.blutmondgilde.blutmondrpg.capabilities.party.GroupStorage;
import de.blutmondgilde.blutmondrpg.capabilities.party.IGroup;
import de.blutmondgilde.blutmondrpg.enums.ClassLevel;
import de.blutmondgilde.blutmondrpg.event.GainExpEvent;
import de.blutmondgilde.blutmondrpg.handler.MobHandler;
import de.blutmondgilde.blutmondrpg.util.CapabilityHelper;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class CustomCapabilityManager {
    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(IModClass.class, new ModClassStorage(), ModClass::new);
        CapabilityManager.INSTANCE.register(IMobScaling.class, new MobScalingStorage(), MobScaling::new);
        CapabilityManager.INSTANCE.register(IGroup.class, new GroupStorage(), Group::new);
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
    }

    @SubscribeEvent
    public void onAttachGroupCapabilities(final AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof PlayerEntity)) return;
        final PlayerEntity player = (PlayerEntity) e.getObject();
        if (player instanceof FakePlayer) return;

        e.addCapability(new ResourceLocation(Ref.MOD_ID, "groupdata"), new GroupProvider());
        Ref.LOGGER.debug("Created Group Information for " + player.getUniqueID().toString());
    }

    @SubscribeEvent
    public void onMobSpawn(final EntityJoinWorldEvent e) {
        if (!(e.getEntity() instanceof MonsterEntity)) return;
        if (e.getWorld().isRemote) return;
        final MonsterEntity entity = (MonsterEntity) e.getEntity();
        final List<ServerPlayerEntity> playerList = BlutmondRPG.getMinecraftServer().getPlayerList().getPlayers();

        ClassLevel highestLevel = ClassLevel.L1;
        PlayerEntity highestLevelPlayer = playerList.get(0);
        for (PlayerEntity player : playerList) {
            if (getDistance(entity.getPosition(), player.getPosition()) > 128) return;
            final IModClass playerCap = CapabilityHelper.getClassCapability(player, "Exeption while seeking the highest level Player around a Mob. PlayerType: " + player.getClass());
            if (playerCap.getClassLevel().getId() < highestLevel.getId()) return;
            highestLevelPlayer = player;
            highestLevel = playerCap.getClassLevel();
        }

        IMobScaling monsterCap = CapabilityHelper.getMobScalingCapability(entity, "Exeption while loading MonsterEntity Capability. MonsterEntity: " + entity.getType().getRegistryName());
        monsterCap.setClosestPlayer(highestLevelPlayer);

        MobHandler.applyMobStats(entity);
    }

    private double getDistance(BlockPos start, BlockPos end) {
        return Math.pow(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getZ() - start.getZ(), 2), 0.5);
    }

    @SubscribeEvent
    public void onMobWasKilledByPlayer(final LivingDeathEvent e) {
        if (!(e.getEntity() instanceof MonsterEntity)) return;
        if (e.getSource().getTrueSource() == null) return;
        if (!(e.getSource().getTrueSource().getEntity() instanceof PlayerEntity)) return;
        PlayerEntity killer = (PlayerEntity) e.getSource().getTrueSource();
        Entity killed = e.getEntity();
        IMobScaling killedCap = CapabilityHelper.getMobScalingCapability(killed, "Exeption while loading Killed Capability.");
        ClassLevel mobLevel = CapabilityHelper.getClassCapability(killedCap.getClosestPlayer(), "Exeption while loading Killed level Capability.").getClassLevel();

        MinecraftForge.EVENT_BUS.post(new GainExpEvent(killer, killedCap.getExp(), mobLevel));
    }

    @SubscribeEvent
    public void onPlayerJoinWorld(final EntityJoinWorldEvent e) {
        if (!(e.getEntity() instanceof PlayerEntity)) return;
        if (e.getEntity() instanceof FakePlayer) return;
        final PlayerEntity player = (PlayerEntity) e.getEntity();
        final IGroup cap = CapabilityHelper.getGroupCapability(player, "Exeption while writing initial group information");
        if (!cap.getPartyMaster().toString().equals(Ref.FAKE_PLAYER.getId().toString())) return;

        cap.setPartyMaster(player.getUniqueID());
        cap.addMember(player.getUniqueID());

        Ref.LOGGER.debug("Added initial group information for " + player.getName().getString());
    }
}
