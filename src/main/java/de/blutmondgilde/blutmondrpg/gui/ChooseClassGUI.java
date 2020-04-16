package de.blutmondgilde.blutmondrpg.gui;

import de.blutmondgilde.blutmondrpg.gui.elements.base.BaseImageButton;
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
            128,
            0,
            64,
            new ResourceLocation(Ref.MOD_ID, "textures/gui/basic_classes_icons.png"),
            (Client) -> handleChooseWarriorButton()
    );
    private BaseImageButton chooseMageButton = new BaseImageButton(
            8 + 64 + 8,
            16,
            64,
            64,
            0,
            0,
            64,
            new ResourceLocation(Ref.MOD_ID, "textures/gui/basic_classes_icons.png"),
            (Client) -> handleChooseMageButton()
    );
    private BaseImageButton chooseScoutButton = new BaseImageButton(
            8 + 64 + 8 + 64 + 8,
            16,
            64,
            64,
            192,
            0,
            64,
            new ResourceLocation(Ref.MOD_ID, "textures/gui/basic_classes_icons.png"),
            (Client) -> handleChooseScoutButton()
    );
    private BaseImageButton choosePriestButton = new BaseImageButton(
            8 + 64 + 8 + 64 + 8 + 64 + 8,
            16,
            64,
            64,
            64,
            0,
            64,
            new ResourceLocation(Ref.MOD_ID, "textures/gui/basic_classes_icons.png"),
            (Client) -> handleChoosePriestButton()
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
        addButton(choosePriestButton);
    }

    @Override
    public void render(int mouseX, int mouseY, float f) {
        super.render(mouseX, mouseY, f);
        if (chooseWarriorButton.isHovered()) {
            drawCenteredString(this.font, new TranslationTextComponent(Ref.MOD_ID + ".class.name.warrior").getString(), this.width / 2, this.height - 48, 10066329);
        }
        if (chooseMageButton.isHovered()) {
            drawCenteredString(this.font, new TranslationTextComponent(Ref.MOD_ID + ".class.name.mage").getString(), this.width / 2, this.height - 48, 10066329);
        }
        if (chooseScoutButton.isHovered()) {
            drawCenteredString(this.font, new TranslationTextComponent(Ref.MOD_ID + ".class.name.scout").getString(), this.width / 2, this.height - 48, 10066329);
        }
        if (choosePriestButton.isHovered()) {
            drawCenteredString(this.font, new TranslationTextComponent(Ref.MOD_ID + ".class.name.priest").getString(), this.width / 2, this.height - 48, 10066329);
        }
    }

    private void handleChooseWarriorButton() {
        //CustomNetworkManager.sendToServer(new ChangeClassPacket(UserClassList.WARRIOR.getId()));
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.closeScreen();
    }

    private void handleChooseMageButton() {
        //CustomNetworkManager.sendToServer(new ChangeClassPacket(UserClassList.MAGE.getId()));
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.closeScreen();
    }

    private void handleChooseScoutButton() {
        //CustomNetworkManager.sendToServer(new ChangeClassPacket(UserClassList.SCOUT.getId()));
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.closeScreen();
    }

    private void handleChoosePriestButton() {
        //CustomNetworkManager.sendToServer(new ChangeClassPacket(UserClassList.PRIEST.getId()));
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.closeScreen();
    }
}
