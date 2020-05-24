package de.blutmondgilde.blutmondrpg.items;

import de.blutmondgilde.blutmondrpg.blocks.BlockList;
import de.blutmondgilde.blutmondrpg.fluids.FluidList;
import de.blutmondgilde.blutmondrpg.items.templates.*;
import de.blutmondgilde.blutmondrpg.items.tiers.BlutmondTiers;
import de.blutmondgilde.blutmondrpg.util.Ref;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList {
    private static final DeferredRegister<Item> ITEM_REGISTRY = new DeferredRegister<>(ForgeRegistries.ITEMS, Ref.MOD_ID);
    //Fuel
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
    //Pickaxe
    public static final RegistryObject<Item> COPPER_PICKAXE = ITEM_REGISTRY.register("copper_pickaxe", () -> new PickaxeToolItem(BlutmondTiers.COPPER));
    public static final RegistryObject<Item> TIN_PICKAXE = ITEM_REGISTRY.register("tin_pickaxe", () -> new PickaxeToolItem(BlutmondTiers.TIN));
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEM_REGISTRY.register("bronze_pickaxe", () -> new PickaxeToolItem(BlutmondTiers.BRONZE));
    public static final RegistryObject<Item> STEEL_PICKAXE = ITEM_REGISTRY.register("steel_pickaxe", () -> new PickaxeToolItem(BlutmondTiers.STEEL));
    public static final RegistryObject<Item> PLATINUM_PICKAXE = ITEM_REGISTRY.register("platinum_pickaxe", () -> new PickaxeToolItem(BlutmondTiers.PLATINUM));
    public static final RegistryObject<Item> DARK_STEEL_PICKAXE = ITEM_REGISTRY.register("dark_steel_pickaxe", () -> new PickaxeToolItem(BlutmondTiers.DARK_STEEL));
    public static final RegistryObject<Item> MITHRIL_PICKAXE = ITEM_REGISTRY.register("mithril_pickaxe", () -> new PickaxeToolItem(BlutmondTiers.MITHRIL));
    public static final RegistryObject<Item> DELDRIMOR_STEEL_PICKAXE = ITEM_REGISTRY.register("deldrimor_steel_pickaxe", () -> new PickaxeToolItem(BlutmondTiers.DELDRIMOR_STEEL));
    //Axe
    public static final RegistryObject<Item> COPPER_AXE = ITEM_REGISTRY.register("copper_axe", () -> new AxeToolItem(BlutmondTiers.COPPER));
    public static final RegistryObject<Item> TIN_AXE = ITEM_REGISTRY.register("tin_axe", () -> new AxeToolItem(BlutmondTiers.TIN));
    public static final RegistryObject<Item> BRONZE_AXE = ITEM_REGISTRY.register("bronze_axe", () -> new AxeToolItem(BlutmondTiers.BRONZE));
    public static final RegistryObject<Item> STEEL_AXE = ITEM_REGISTRY.register("steel_axe", () -> new AxeToolItem(BlutmondTiers.STEEL));
    public static final RegistryObject<Item> PLATINUM_AXE = ITEM_REGISTRY.register("platinum_axe", () -> new AxeToolItem(BlutmondTiers.PLATINUM));
    public static final RegistryObject<Item> DARK_STEEL_AXE = ITEM_REGISTRY.register("dark_steel_axe", () -> new AxeToolItem(BlutmondTiers.DARK_STEEL));
    public static final RegistryObject<Item> MITHRIL_AXE = ITEM_REGISTRY.register("mithril_axe", () -> new AxeToolItem(BlutmondTiers.MITHRIL));
    public static final RegistryObject<Item> DELDRIMOR_STEEL_AXE = ITEM_REGISTRY.register("deldrimor_steel_axe", () -> new AxeToolItem(BlutmondTiers.DELDRIMOR_STEEL));
    //Shovel
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEM_REGISTRY.register("copper_shovel", () -> new ShovelToolItem(BlutmondTiers.COPPER));
    public static final RegistryObject<Item> TIN_SHOVEL = ITEM_REGISTRY.register("tin_shovel", () -> new ShovelToolItem(BlutmondTiers.TIN));
    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEM_REGISTRY.register("bronze_shovel", () -> new ShovelToolItem(BlutmondTiers.BRONZE));
    public static final RegistryObject<Item> STEEL_SHOVEL = ITEM_REGISTRY.register("steel_shovel", () -> new ShovelToolItem(BlutmondTiers.STEEL));
    public static final RegistryObject<Item> PLATINUM_SHOVEL = ITEM_REGISTRY.register("platinum_shovel", () -> new ShovelToolItem(BlutmondTiers.PLATINUM));
    public static final RegistryObject<Item> DARK_STEEL_SHOVEL = ITEM_REGISTRY.register("dark_steel_shovel", () -> new ShovelToolItem(BlutmondTiers.DARK_STEEL));
    public static final RegistryObject<Item> MITHRIL_SHOVEL = ITEM_REGISTRY.register("mithril_shovel", () -> new ShovelToolItem(BlutmondTiers.MITHRIL));
    public static final RegistryObject<Item> DELDRIMOR_STEEL_SHOVEL = ITEM_REGISTRY.register("deldrimor_steel_shovel", () -> new ShovelToolItem(BlutmondTiers.DELDRIMOR_STEEL));
    //Buckets
    public static final RegistryObject<Item> CRYSTALLIZER_BUCKET = ITEM_REGISTRY.register("crystallizer_bucket", () -> new BucketTemplate(FluidList.CRYSTALLIZER));
    //TileEntities
    public static final RegistryObject<Item> ALLOY_FURNACE = ITEM_REGISTRY.register("alloy_furnace", () -> new GenericContainerBlockItem(BlockList.ALLOY_FURNACE));

    public static void register(){
        ITEM_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        Ref.LOGGER.debug("Registered Items.");
    }
}
