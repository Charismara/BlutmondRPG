package de.blutmondgilde.blutmondrpg.gui;

import de.blutmondgilde.blutmondrpg.enums.BasicClasses;
import de.blutmondgilde.blutmondrpg.enums.StringColors;
import de.blutmondgilde.blutmondrpg.gui.elements.base.BaseImageButton;
import de.blutmondgilde.blutmondrpg.network.ChangeClassPacket;
import de.blutmondgilde.blutmondrpg.network.CustomNetworkManager;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ChooseClassGUI extends Screen {
    private BaseImageButton chooseWarriorButton = new BaseImageButton(
            8,
            16,
            64,
            64,
            0,
            0,
            129,
            new ResourceLocation(Ref.MOD_ID, "textures/gui/warrior.png"),
            (Client) -> handleChooseWarriorButton()
    );
    private BaseImageButton chooseMageButton = new BaseImageButton(
            8 + 64 + 8,
            16,
            64,
            64,
            0,
            0,
            129,
            new ResourceLocation(Ref.MOD_ID, "textures/gui/mage.png"),
            (Client) -> handleChooseMageButton()
    );
    private BaseImageButton chooseScoutButton = new BaseImageButton(
            8 + 64 + 8 + 64 + 8,
            16,
            64,
            64,
            0,
            0,
            129,
            new ResourceLocation(Ref.MOD_ID, "textures/gui/scout.png"),
            (Client) -> handleChooseScoutButton()
    );

    public ChooseClassGUI() {
        super(new TranslationTextComponent(Ref.MOD_ID + ".gui.chooseclass.title"));
    }

    @Override
    protected void init() {
        super.init();

        addButton(chooseWarriorButton);
        addButton(chooseMageButton);
        addButton(chooseScoutButton);

    }

    @Override
    public void render(int mouseX, int mouseY, float f) {
        super.render(mouseX, mouseY, f);
        if (chooseWarriorButton.isHovered()) {
            drawCenteredString(this.font, new TranslationTextComponent(Ref.MOD_ID + ".class.name.warrior").getString(), this.width / 2, this.height - 48, StringColors.GRAY.getColorCode());
        }
        if (chooseMageButton.isHovered()) {
            drawCenteredString(this.font, new TranslationTextComponent(Ref.MOD_ID + ".class.name.mage").getString(), this.width / 2, this.height - 48, StringColors.GRAY.getColorCode());
        }
        if (chooseScoutButton.isHovered()) {
            drawCenteredString(this.font, new TranslationTextComponent(Ref.MOD_ID + ".class.name.scout").getString(), this.width / 2, this.height - 48, StringColors.GRAY.getColorCode());
        }
    }

    private void handleChooseWarriorButton() {
        CustomNetworkManager.sendToServer(new ChangeClassPacket(BasicClasses.WARRIOR.getId()));
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.closeScreen();
    }

    private void handleChooseMageButton() {
        CustomNetworkManager.sendToServer(new ChangeClassPacket(BasicClasses.CASTER.getId()));
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.closeScreen();
    }

    private void handleChooseScoutButton() {
        CustomNetworkManager.sendToServer(new ChangeClassPacket(BasicClasses.HUNTER.getId()));
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.closeScreen();
    }
}
