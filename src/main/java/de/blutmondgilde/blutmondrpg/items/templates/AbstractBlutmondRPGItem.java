package de.blutmondgilde.blutmondrpg.items.templates;

import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public abstract class AbstractBlutmondRPGItem extends Item {
    private final ITextComponent name;

    public AbstractBlutmondRPGItem(Properties properties, String name) {
        super(properties);
        this.name = new StringTextComponent(name);
    }

    public ITextComponent getName() {
        return name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
