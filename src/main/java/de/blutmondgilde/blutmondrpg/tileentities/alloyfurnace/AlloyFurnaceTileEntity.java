package de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace;

import de.blutmondgilde.blutmondrpg.recipe.AlloySmeltingRecipe;
import de.blutmondgilde.blutmondrpg.recipe.itemhandler.TwoInputSlots;
import de.blutmondgilde.blutmondrpg.tileentities.TileEntityList;
import de.blutmondgilde.blutmondrpg.util.RecipeUtil;
import de.blutmondgilde.blutmondrpg.util.SetBlockStateFlag;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.Optional;

public class AlloyFurnaceTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public static final int FUEL_SLOTS_COUNT = 1;
    public static final int INPUT_SLOTS_COUNT = 2;
    public static final int OUTPUT_SLOTS_COUNT = 1;
    public static final int TOTAL_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

    private final AlloyFurnaceZoneContents fuelZoneContents;
    private final AlloyFurnaceZoneContents inputZoneContents;
    private final AlloyFurnaceZoneContents outputZoneContents;

    private final AlloyFurnaceStateData furnaceStateData = new AlloyFurnaceStateData();


    public AlloyFurnaceTileEntity() {
        super(TileEntityList.ALLOY_FURNACE.get());
        fuelZoneContents = AlloyFurnaceZoneContents.createForTileEntity(FUEL_SLOTS_COUNT,
                this::canPlayerAccessInventory, this::markDirty);
        inputZoneContents = AlloyFurnaceZoneContents.createForTileEntity(INPUT_SLOTS_COUNT,
                this::canPlayerAccessInventory, this::markDirty);
        outputZoneContents = AlloyFurnaceZoneContents.createForTileEntity(OUTPUT_SLOTS_COUNT,
                this::canPlayerAccessInventory, this::markDirty);
    }

    public boolean canPlayerAccessInventory(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    public int numberOfBurningFuelSlots() {
        int burningCount = 0;
        for (int burnTime : furnaceStateData.burnTimeRemainings) {
            if (burnTime > 0) ++burningCount;
        }
        return burningCount;
    }

    @Override
    public void tick() {
        if (world.isRemote) return; // do nothing on client.
        ItemStack[] currentlySmeltingItem = getCurrentlySmeltingInputItem();
        boolean isBurning = false;

        if (!ItemStack.areItemsEqual(currentlySmeltingItem[0], ItemStack.EMPTY) && !ItemStack.areItemsEqual(currentlySmeltingItem[1], ItemStack.EMPTY)) {
            isBurning = true;
        }

        if (!ItemStack.areItemsEqual(currentlySmeltingItem[0], currentlySmeltingItemLastTick[0]) && !ItemStack.areItemsEqual(currentlySmeltingItem[1], currentlySmeltingItemLastTick[1])) {  // == and != don't work!
            furnaceStateData.cookTimeElapsed = 0;
        }
        currentlySmeltingItemLastTick = currentlySmeltingItem.clone();

        if (!currentlySmeltingItem[0].isEmpty() && !currentlySmeltingItem[1].isEmpty()) {
            int numberOfFuelBurning = burnFuel();

            if (numberOfFuelBurning > 0) {
                furnaceStateData.cookTimeElapsed += numberOfFuelBurning;
            } else {
                furnaceStateData.cookTimeElapsed -= 2;
            }
            if (furnaceStateData.cookTimeElapsed < 0) furnaceStateData.cookTimeElapsed = 0;

            int cookTimeForCurrentItem = getCookTime(this.world, currentlySmeltingItem[0], currentlySmeltingItem[1]);
            furnaceStateData.cookTimeForCompletion = cookTimeForCurrentItem;
            if (furnaceStateData.cookTimeElapsed >= cookTimeForCurrentItem) {
                smeltFirstSuitableInputItem();
                furnaceStateData.cookTimeElapsed = 0;
            }
        } else {
            furnaceStateData.cookTimeElapsed = 0;
        }

        int numberBurning = numberOfBurningFuelSlots();
        BlockState currentBlockState = world.getBlockState(this.pos);
        BlockState newBlockState = currentBlockState.with(AlloyFurnaceBlockInventory.BURNING_SIDES_COUNT, numberBurning).with(AlloyFurnaceBlockInventory.LIT, isBurning);
        if (!newBlockState.equals(currentBlockState)) {
            final int FLAGS = SetBlockStateFlag.get(SetBlockStateFlag.BLOCK_UPDATE, SetBlockStateFlag.SEND_TO_CLIENTS);
            world.setBlockState(this.pos, newBlockState, FLAGS);
            markDirty();
        }
    }

    private int burnFuel() {
        int burningCount = 0;
        boolean inventoryChanged = false;

        for (int fuelIndex = 0; fuelIndex < FUEL_SLOTS_COUNT; fuelIndex++) {
            if (furnaceStateData.burnTimeRemainings[fuelIndex] > 0) {
                --furnaceStateData.burnTimeRemainings[fuelIndex];
                ++burningCount;
            }

            if (furnaceStateData.burnTimeRemainings[fuelIndex] == 0) {
                ItemStack fuelItemStack = fuelZoneContents.getStackInSlot(fuelIndex);
                if (!fuelItemStack.isEmpty() && getItemBurnTime(this.world, fuelItemStack) > 0) {
                    int burnTimeForItem = getItemBurnTime(this.world, fuelItemStack);
                    furnaceStateData.burnTimeRemainings[fuelIndex] = burnTimeForItem;
                    furnaceStateData.burnTimeInitialValues[fuelIndex] = burnTimeForItem;
                    fuelZoneContents.decrStackSize(fuelIndex, 1);
                    ++burningCount;
                    inventoryChanged = true;

                    if (fuelItemStack.isEmpty()) {
                        ItemStack containerItem = fuelItemStack.getContainerItem();
                        fuelZoneContents.setInventorySlotContents(fuelIndex, containerItem);
                    }
                }
            }
        }
        if (inventoryChanged) markDirty();
        return burningCount;
    }

    private ItemStack[] getCurrentlySmeltingInputItem() {
        return smeltFirstSuitableInputItem(false);
    }

    private void smeltFirstSuitableInputItem() {
        smeltFirstSuitableInputItem(true);
    }

    private ItemStack[] smeltFirstSuitableInputItem(boolean performSmelt) {
        Integer firstInputSlot = null;
        Integer secondInputSlot = null;
        Integer firstOutputSlot = null;
        ItemStack result = ItemStack.EMPTY;

        ItemStack itemStack1 = inputZoneContents.getStackInSlot(0);
        ItemStack itemStack2 = inputZoneContents.getStackInSlot(1);

        if (!itemStack2.isEmpty() && !itemStack1.isEmpty()) {
            result = getSmeltingResultForItem(this.world, itemStack1, itemStack2);
            if (!result.isEmpty()) {
                for (int outputIndex = 0; outputIndex < OUTPUT_SLOTS_COUNT; outputIndex++) {
                    if (willItemStackFit(outputZoneContents, outputIndex, result)) {
                        firstInputSlot = 0;
                        secondInputSlot = 1;
                        firstOutputSlot = outputIndex;
                        break;
                    }
                }
            }
        }

        if (firstInputSlot == null) return new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY};
        if (secondInputSlot == null) return new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY};

        ItemStack[] returnValue = new ItemStack[]{inputZoneContents.getStackInSlot(firstInputSlot).copy(), inputZoneContents.getStackInSlot(secondInputSlot).copy()};
        if (!performSmelt) return returnValue;

        inputZoneContents.decrStackSize(firstInputSlot, 1);
        inputZoneContents.decrStackSize(secondInputSlot, 1);
        outputZoneContents.increaseStackSize(firstOutputSlot, result);

        markDirty();
        return returnValue;
    }

    public boolean willItemStackFit(AlloyFurnaceZoneContents furnaceZoneContents, int slotIndex, ItemStack itemStackOrigin) {
        ItemStack itemStackDestination = furnaceZoneContents.getStackInSlot(slotIndex);

        if (itemStackDestination.isEmpty() || itemStackOrigin.isEmpty()) {
            return true;
        }

        if (!itemStackOrigin.isItemEqual(itemStackDestination)) {
            return false;
        }

        int sizeAfterMerge = itemStackDestination.getCount() + itemStackOrigin.getCount();
        return sizeAfterMerge <= furnaceZoneContents.getInventoryStackLimit() && sizeAfterMerge <= itemStackDestination.getMaxStackSize();
    }

    public static ItemStack getSmeltingResultForItem(World world, ItemStack first, ItemStack second) {
        Optional<AlloySmeltingRecipe> matchingRecipe = getMatchingRecipeForInput(world, first, second);
        return matchingRecipe.map(furnaceRecipe -> furnaceRecipe.getRecipeOutput().copy()).orElse(ItemStack.EMPTY);
    }

    public static int getItemBurnTime(World world, ItemStack stack) {
        int burntime = net.minecraftforge.common.ForgeHooks.getBurnTime(stack);
        return burntime;
    }

    public static Optional<AlloySmeltingRecipe> getMatchingRecipeForInput(World world, ItemStack first, ItemStack second) {
        RecipeManager recipeManager = world.getRecipeManager();
        Inventory inventory = new Inventory(first, second);
        Optional<AlloySmeltingRecipe> matchingRecipe = RecipeUtil.getRecipes(world, AlloySmeltingRecipe.SERIALIZER.getRecipeType()).stream().filter(alloySmeltingRecipe -> alloySmeltingRecipe.matches(new TwoInputSlots(first, second))).findFirst();
        return matchingRecipe;
    }

    public static int getCookTime(World world, ItemStack first, ItemStack second) {
        Optional<AlloySmeltingRecipe> matchingRecipe = getMatchingRecipeForInput(world, first, second);
        return matchingRecipe.map(AlloySmeltingRecipe::getProcessingTime).orElse(600);
    }

    static public boolean isItemValidForFuelSlot(ItemStack itemStack) {
        return true;
    }

    static public boolean isItemValidForInputSlot(ItemStack itemStack) {
        return true;
    }

    static public boolean isItemValidForOutputSlot(ItemStack itemStack) {
        return false;
    }

    private final String FUEL_SLOTS_NBT = "fuelSlots";
    private final String INPUT_SLOTS_NBT = "inputSlots";
    private final String OUTPUT_SLOTS_NBT = "outputSlots";

    @Override
    public CompoundNBT write(CompoundNBT parentNBTTagCompound) {
        super.write(parentNBTTagCompound); // The super call is required to save and load the tile's location

        furnaceStateData.putIntoNBT(parentNBTTagCompound);
        parentNBTTagCompound.put(FUEL_SLOTS_NBT, fuelZoneContents.serializeNBT());
        parentNBTTagCompound.put(INPUT_SLOTS_NBT, inputZoneContents.serializeNBT());
        parentNBTTagCompound.put(OUTPUT_SLOTS_NBT, outputZoneContents.serializeNBT());
        return parentNBTTagCompound;
    }

    @Override
    public void read(CompoundNBT nbtTagCompound) {
        super.read(nbtTagCompound);

        furnaceStateData.readFromNBT(nbtTagCompound);

        CompoundNBT inventoryNBT = nbtTagCompound.getCompound(FUEL_SLOTS_NBT);
        fuelZoneContents.deserializeNBT(inventoryNBT);

        inventoryNBT = nbtTagCompound.getCompound(INPUT_SLOTS_NBT);
        inputZoneContents.deserializeNBT(inventoryNBT);

        inventoryNBT = nbtTagCompound.getCompound(OUTPUT_SLOTS_NBT);
        outputZoneContents.deserializeNBT(inventoryNBT);

        if (fuelZoneContents.getSizeInventory() != FUEL_SLOTS_COUNT
                || inputZoneContents.getSizeInventory() != INPUT_SLOTS_COUNT
                || outputZoneContents.getSizeInventory() != OUTPUT_SLOTS_COUNT
        )
            throw new IllegalArgumentException("Corrupted NBT: Number of inventory slots did not match expected.");
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT updateTagDescribingTileEntityState = getUpdateTag();
        final int METADATA = 42; // arbitrary.
        return new SUpdateTileEntityPacket(this.pos, METADATA, updateTagDescribingTileEntityState);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT updateTagDescribingTileEntityState = pkt.getNbtCompound();
        handleUpdateTag(updateTagDescribingTileEntityState);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        read(tag);
    }

    public void dropAllContents(World world, BlockPos blockPos) {
        InventoryHelper.dropInventoryItems(world, blockPos, fuelZoneContents);
        InventoryHelper.dropInventoryItems(world, blockPos, inputZoneContents);
        InventoryHelper.dropInventoryItems(world, blockPos, outputZoneContents);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.blutmondgilderpg.alloy_furnace");
    }

    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return AlloyFurnaceContainer.createContainerServerSide(windowID, playerInventory, inputZoneContents, outputZoneContents, fuelZoneContents, furnaceStateData);
    }

    private ItemStack[] currentlySmeltingItemLastTick = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY};
}

