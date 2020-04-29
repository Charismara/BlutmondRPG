package de.blutmondgilde.blutmondrpg.gui;

import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class OtherPlayerGUI extends Screen {
    private final String playerName;
    private final BasicClasses playerClass;
    private final int playerLevel;
    private final float hp, maxHp;
    private final Minecraft client;

    public OtherPlayerGUI(final String name, final BasicClasses playerClass, final int level, final float hp, final float maxHP, final Minecraft minecraft) {
        super(new TranslationTextComponent("blutmondrpg.gui.otherplayer.title"));
        this.playerName = name;
        this.playerClass = playerClass;
        this.playerLevel = level;
        this.hp = hp;
        this.maxHp = maxHP;
        this.client = minecraft;
    }

    @Override
    protected void init() {
        super.init();
        addButton(new Button(this.width / 2 - 60, this.height / 4 * 3, 60, 20, (new TranslationTextComponent("blutmondrpg.gui.otherplayer.button.invite")).getString(), (p) -> onInviteButtonPressed()));
    }

    @Override
    public void render(int mouseX, int mouseY, float f) {
        super.render(mouseX, mouseY, f);

    }

    private void renderName() {
    }

    private void renderClass() {
    }

    private void renderLevel() {
    }

    private void renderHp() {
    }

    private void onInviteButtonPressed() {

    }
}
