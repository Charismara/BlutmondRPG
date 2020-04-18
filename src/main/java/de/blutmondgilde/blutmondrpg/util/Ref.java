package de.blutmondgilde.blutmondrpg.util;

import com.mojang.authlib.GameProfile;
import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public final class Ref {
    public static final String MOD_ID = "blutmondrpg";
    public static final Logger LOGGER = LogManager.getLogger(BlutmondRPG.class);
    public static final GameProfile FAKE_PLAYER = new GameProfile(UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5"), "Notch");
}
