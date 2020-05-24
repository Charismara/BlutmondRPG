package de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace;

import de.blutmondgilde.blutmondrpg.tileentities.ContainerList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class AlloyFurnaceContainer extends Container {
    public static AlloyFurnaceContainer createContainerServerSide(int windowID, PlayerInventory playerInventory, AlloyFurnaceZoneContents inputZoneContents, AlloyFurnaceZoneContents outputZoneContents, AlloyFurnaceZoneContents fuelZoneContents, AlloyFurnaceStateData furnaceStateData) {
        return new AlloyFurnaceContainer(windowID, playerInventory, inputZoneContents, outputZoneContents, fuelZoneContents, furnaceStateData);
    }

    public static AlloyFurnaceContainer createContainerClientSide(int windowID, PlayerInventory playerInventory, PacketBuffer extraData) {
        AlloyFurnaceZoneContents inputZoneContents = AlloyFurnaceZoneContents.createForClientSideContainer(INPUT_SLOTS_COUNT);
        AlloyFurnaceZoneContents outputZoneContents = AlloyFurnaceZoneContents.createForClientSideContainer(OUTPUT_SLOTS_COUNT);
        AlloyFurnaceZoneContents fuelZoneContents = AlloyFurnaceZoneContents.createForClientSideContainer(FUEL_SLOTS_COUNT);
        AlloyFurnaceStateData furnaceStateData = new AlloyFurnaceStateData();

        return new AlloyFurnaceContainer(windowID, playerInventory, inputZoneContents, outputZoneContents, fuelZoneContents, furnaceStateData);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    public static final int FUEL_SLOTS_COUNT = AlloyFurnaceTileEntity.FUEL_SLOTS_COUNT;
    public static final int INPUT_SLOTS_COUNT = AlloyFurnaceTileEntity.INPUT_SLOTS_COUNT;
    public static final int OUTPUT_SLOTS_COUNT = AlloyFurnaceTileEntity.OUTPUT_SLOTS_COUNT;
    public static final int FURNACE_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int HOTBAR_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX;
    private static final int PLAYER_INVENTORY_FIRST_SLOT_INDEX = HOTBAR_FIRST_SLOT_INDEX + HOTBAR_SLOT_COUNT;
    private static final int FIRST_FUEL_SLOT_INDEX = PLAYER_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int FIRST_INPUT_SLOT_INDEX = FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT;
    private static final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT;

    public AlloyFurnaceContainer(int windowID, PlayerInventory invPlayer, AlloyFurnaceZoneContents inputZoneContents, AlloyFurnaceZoneContents outputZoneContents, AlloyFurnaceZoneContents fuelZoneContents, AlloyFurnaceStateData furnaceStateData) {
        super(ContainerList.ALLOY_FURNACE.get(), windowID);
        this.inputZoneContents = inputZoneContents;
        this.outputZoneContents = outputZoneContents;
        this.fuelZoneContents = fuelZoneContents;
        this.furnaceStateData = furnaceStateData;
        this.world = invPlayer.player.world;

        trackIntArray(furnaceStateData);

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 183;

        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            int slotNumber = x;
            addSlot(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 125;

        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(invPlayer, slotNumber, xpos, ypos));
            }
        }

        final int FUEL_SLOTS_XPOS = 80;
        final int FUEL_SLOTS_YPOS = 96;
        // Add the tile fuel slots
        for (int x = 0; x < FUEL_SLOTS_COUNT; x++) {
            int slotNumber = x;
            addSlot(new SlotFuel(fuelZoneContents, slotNumber, FUEL_SLOTS_XPOS + SLOT_X_SPACING * x, FUEL_SLOTS_YPOS));
        }

        final int INPUT_SLOTS_XPOS = 26;
        final int INPUT_SLOTS_YPOS = 50;
        // Add the tile input slots
        for (int y = 0; y < INPUT_SLOTS_COUNT; y++) {
            int slotNumber = y;
            addSlot(new SlotSmeltableInput(inputZoneContents, slotNumber, INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS + (SLOT_Y_SPACING + 3) * y));
        }

        final int OUTPUT_SLOTS_XPOS = 134;
        final int OUTPUT_SLOTS_YPOS = 24 + SLOT_Y_SPACING * 2;
        for (int y = 0; y < OUTPUT_SLOTS_COUNT; y++) {
            int slotNumber = y;
            addSlot(new SlotOutput(outputZoneContents, slotNumber, OUTPUT_SLOTS_XPOS, OUTPUT_SLOTS_YPOS + SLOT_Y_SPACING * y));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return fuelZoneContents.isUsableByPlayer(player) && inputZoneContents.isUsableByPlayer(player)
                && outputZoneContents.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex) {
        Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
        int index2;
        if (sourceSlotIndex == 0) {
            index2 = 1;
        } else {
            index2 = 0;
        }
        Slot sourceSlot2 = inventorySlots.get(index2);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;
        ItemStack sourceItemStack = sourceSlot.getStack();
        ItemStack sourceItemStack2 = sourceSlot2.getStack();
        ItemStack sourceStackBeforeMerge = sourceItemStack.copy();
        ItemStack sourceStackBeforeMerge2 = sourceItemStack2.copy();
        boolean successfulTransfer = false;

        SlotZone sourceZone = SlotZone.getZoneFromIndex(sourceSlotIndex);

        switch (sourceZone) {
            case OUTPUT_ZONE:
                successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, true);
                if (!successfulTransfer) {
                    successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, true);
                }
                if (successfulTransfer) {
                    sourceSlot.onSlotChange(sourceItemStack, sourceStackBeforeMerge);
                }
                break;

            case INPUT_ZONE:
            case FUEL_ZONE:
                successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
                if (!successfulTransfer) {
                    successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
                }
                break;

            case PLAYER_HOTBAR:
            case PLAYER_MAIN_INVENTORY:
                if (!AlloyFurnaceTileEntity.getSmeltingResultForItem(world, sourceItemStack, sourceItemStack2).isEmpty()) {
                    successfulTransfer = mergeInto(SlotZone.INPUT_ZONE, sourceItemStack, false);
                }
                if (!successfulTransfer && AlloyFurnaceTileEntity.getItemBurnTime(world, sourceItemStack) > 0) {
                    successfulTransfer = mergeInto(SlotZone.FUEL_ZONE, sourceItemStack, true);
                }
                if (!successfulTransfer) {
                    if (sourceZone == SlotZone.PLAYER_HOTBAR) { // main inventory
                        successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
                    } else {
                        successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
                    }
                }
                break;

            default:
                throw new IllegalArgumentException("unexpected sourceZone:" + sourceZone);
        }
        if (!successfulTransfer) return ItemStack.EMPTY;

        if (sourceItemStack.isEmpty()) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        if (sourceItemStack.getCount() == sourceStackBeforeMerge.getCount()) {
            return ItemStack.EMPTY;
        }
        sourceSlot.onTake(player, sourceItemStack);
        return sourceStackBeforeMerge;
    }

    private boolean mergeInto(SlotZone destinationZone, ItemStack sourceItemStack, boolean fillFromEnd) {
        return mergeItemStack(sourceItemStack, destinationZone.firstIndex, destinationZone.lastIndexPlus1, fillFromEnd);
    }

    public double fractionOfFuelRemaining(int fuelSlot) {
        if (furnaceStateData.burnTimeInitialValues[fuelSlot] <= 0) return 0;
        double fraction = furnaceStateData.burnTimeRemainings[fuelSlot] / (double) furnaceStateData.burnTimeInitialValues[fuelSlot];
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    public int secondsOfFuelRemaining(int fuelSlot) {
        if (furnaceStateData.burnTimeRemainings[fuelSlot] <= 0) return 0;
        return furnaceStateData.burnTimeRemainings[fuelSlot] / 20; // 20 ticks per second
    }

    public double fractionOfCookTimeComplete() {
        if (furnaceStateData.cookTimeForCompletion == 0) return 0;
        double fraction = furnaceStateData.cookTimeElapsed / (double) furnaceStateData.cookTimeForCompletion;
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    public static class SlotFuel extends Slot {
        public SlotFuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return AlloyFurnaceTileEntity.isItemValidForFuelSlot(stack);
        }
    }

    // SlotSmeltableInput is a slot for input item
    public static class SlotSmeltableInput extends Slot {
        public SlotSmeltableInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        // if this function returns false, the player won't be able to insert the given item into this slot
        @Override
        public boolean isItemValid(ItemStack stack) {
            return AlloyFurnaceTileEntity.isItemValidForInputSlot(stack);
        }
    }

    // SlotOutput is a slot that will not accept any item
    public static class SlotOutput extends Slot {
        public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        // if this function returns false, the player won't be able to insert the given item into this slot
        @Override
        public boolean isItemValid(ItemStack stack) {
            return AlloyFurnaceTileEntity.isItemValidForOutputSlot(stack);
        }
    }

    private final AlloyFurnaceZoneContents inputZoneContents;
    private final AlloyFurnaceZoneContents outputZoneContents;
    private final AlloyFurnaceZoneContents fuelZoneContents;
    private final AlloyFurnaceStateData furnaceStateData;

    private final World world;

    private enum SlotZone {
        FUEL_ZONE(FIRST_FUEL_SLOT_INDEX, FUEL_SLOTS_COUNT),
        INPUT_ZONE(FIRST_INPUT_SLOT_INDEX, INPUT_SLOTS_COUNT),
        OUTPUT_ZONE(FIRST_OUTPUT_SLOT_INDEX, OUTPUT_SLOTS_COUNT),
        PLAYER_MAIN_INVENTORY(PLAYER_INVENTORY_FIRST_SLOT_INDEX, PLAYER_INVENTORY_SLOT_COUNT),
        PLAYER_HOTBAR(HOTBAR_FIRST_SLOT_INDEX, HOTBAR_SLOT_COUNT);

        SlotZone(int firstIndex, int numberOfSlots) {
            this.firstIndex = firstIndex;
            this.slotCount = numberOfSlots;
            this.lastIndexPlus1 = firstIndex + numberOfSlots;
        }

        public final int firstIndex;
        public final int slotCount;
        public final int lastIndexPlus1;

        public static SlotZone getZoneFromIndex(int slotIndex) {
            for (SlotZone slotZone : SlotZone.values()) {
                if (slotIndex >= slotZone.firstIndex && slotIndex < slotZone.lastIndexPlus1) return slotZone;
            }
            throw new IndexOutOfBoundsException("Unexpected slotIndex");
        }
    }
}
