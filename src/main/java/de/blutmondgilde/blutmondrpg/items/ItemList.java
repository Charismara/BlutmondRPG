package de.blutmondgilde.blutmondrpg.items;

import de.blutmondgilde.blutmondrpg.BlutmondRPG;
import de.blutmondgilde.blutmondrpg.blocks.BlockList;
import de.blutmondgilde.blutmondrpg.items.templates.FuelItem;
import de.blutmondgilde.blutmondrpg.items.templates.MaterialBlockItem;
import de.blutmondgilde.blutmondrpg.items.templates.MaterialItem;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ItemList {
    private static final DeferredRegister<Item> ITEM_REGISTRY = BlutmondRPG.getItemRegistry();
    //
    public static final RegistryObject<Item> PRIMORDIUM = ITEM_REGISTRY.register("primordium", () -> new FuelItem(4000)); //1 coal + 8 wood logs
    public static final RegistryObject<Item> THERMOCATALYTIC_REAGENT = ITEM_REGISTRY.register("thermocatalytic_reagent", () -> new FuelItem(32000)); // 7 primordium + 1 blaze rod + 1 coal
    //Ingots
    public static final RegistryObject<Item> COPPER_INGOT = ITEM_REGISTRY.register("copper_ingot", MaterialItem::new);
    public static final RegistryObject<Item> TIN_INGOT = ITEM_REGISTRY.register("tin_ingot", MaterialItem::new);
    public static final RegistryObject<Item> BRONZE_INGOT = ITEM_REGISTRY.register("bronze_ingot", MaterialItem::new);
    public static final RegistryObject<Item> STEEL_INGOT = ITEM_REGISTRY.register("steel_ingot", MaterialItem::new);
    public static final RegistryObject<Item> PLATINUM_INGOT = ITEM_REGISTRY.register("platinum_ingot", MaterialItem::new);
    public static final RegistryObject<Item> DARK_STEEL_INGOT = ITEM_REGISTRY.register("dark_steel_ingot", MaterialItem::new);
    public static final RegistryObject<Item> MITHRIL_INGOT = ITEM_REGISTRY.register("mithril_ingot", MaterialItem::new);
    public static final RegistryObject<Item> DELDRIMOR_STEEL_INGOT = ITEM_REGISTRY.register("deldrimor_steel_ingot", MaterialItem::new);
    //Blocks
    public static final RegistryObject<Item> COPPER_BLOCK = ITEM_REGISTRY.register("copper_block", () -> new MaterialBlockItem(BlockList.COPPER_BLOCK));
    public static final RegistryObject<Item> TIN_BLOCK = ITEM_REGISTRY.register("tin_block", () -> new MaterialBlockItem(BlockList.TIN_BLOCK));
    public static final RegistryObject<Item> BRONZE_BLOCK = ITEM_REGISTRY.register("bronze_block", () -> new MaterialBlockItem(BlockList.BRONZE_BLOCK));
    public static final RegistryObject<Item> STEEL_BLOCK = ITEM_REGISTRY.register("steel_block", () -> new MaterialBlockItem(BlockList.STEEL_BLOCK));
    public static final RegistryObject<Item> PLATINUM_BLOCK = ITEM_REGISTRY.register("platinum_block", () -> new MaterialBlockItem(BlockList.PLATINUM_BLOCK));
    public static final RegistryObject<Item> DARK_STEEL_BLOCK = ITEM_REGISTRY.register("dark_steel_block", () -> new MaterialBlockItem(BlockList.DARK_STEEL_BLOCK));
    public static final RegistryObject<Item> MITHRIL_BLOCK = ITEM_REGISTRY.register("mithril_block", () -> new MaterialBlockItem(BlockList.MITHRIL_BLOCK));
    public static final RegistryObject<Item> DELDRIMOR_STEEL_BLOCK = ITEM_REGISTRY.register("deldrimor_steel_block", () -> new MaterialBlockItem(BlockList.DELDRIMOR_STEEL_BLOCK));
    //Nuggets
    public static final RegistryObject<Item> COPPER_NUGGET = ITEM_REGISTRY.register("copper_nugget", MaterialItem::new);
    public static final RegistryObject<Item> TIN_NUGGET = ITEM_REGISTRY.register("tin_nugget", MaterialItem::new);
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEM_REGISTRY.register("bronze_nugget", MaterialItem::new);
    public static final RegistryObject<Item> STEEL_NUGGET = ITEM_REGISTRY.register("steel_nugget", MaterialItem::new);
    public static final RegistryObject<Item> PLATINUM_NUGGET = ITEM_REGISTRY.register("platinum_nugget", MaterialItem::new);
    public static final RegistryObject<Item> DARK_STEEL_NUGGET = ITEM_REGISTRY.register("dark_steel_nugget", MaterialItem::new);
    public static final RegistryObject<Item> MITHRIL_NUGGET = ITEM_REGISTRY.register("mithril_nugget", MaterialItem::new);
    public static final RegistryObject<Item> DELDRIMOR_STEEL_NUGGET = ITEM_REGISTRY.register("deldrimor_steel_nugget", MaterialItem::new);
    //Crafting
    public static final RegistryObject<Item> MITHRILIUM_CHUNK = ITEM_REGISTRY.register("mithrilium_chunk", MaterialItem::new);
    //Ores
    public static final RegistryObject<Item> COPPER_ORE = ITEM_REGISTRY.register("copper_ore", () -> new MaterialBlockItem(BlockList.COPPER_ORE));
    public static final RegistryObject<Item> TIN_ORE = ITEM_REGISTRY.register("tin_ore", () -> new MaterialBlockItem(BlockList.TIN_ORE));
    public static final RegistryObject<Item> PLATINUM_ORE = ITEM_REGISTRY.register("platinum_ore", () -> new MaterialBlockItem(BlockList.PLATINUM_ORE));
    public static final RegistryObject<Item> MITHRIL_ORE = ITEM_REGISTRY.register("mithril_ore", () -> new MaterialBlockItem(BlockList.MITHRIL_ORE));

    public ItemList() {
        Ref.LOGGER.debug("Items registered");
    }
}
