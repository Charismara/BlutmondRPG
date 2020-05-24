package de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Predicate;

public class AlloyFurnaceZoneContents implements IInventory {

    public static AlloyFurnaceZoneContents createForTileEntity(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda, Notify markDirtyNotificationLambda) {
        return new AlloyFurnaceZoneContents(size, canPlayerAccessInventoryLambda, markDirtyNotificationLambda);
    }

    public static AlloyFurnaceZoneContents createForClientSideContainer(int size) {
        return new AlloyFurnaceZoneContents(size);
    }

    public CompoundNBT serializeNBT() {
        return furnaceComponentContents.serializeNBT();
    }

    public void deserializeNBT(CompoundNBT nbt) {
        furnaceComponentContents.deserializeNBT(nbt);
    }

    public void setCanPlayerAccessInventoryLambda(Predicate<PlayerEntity> canPlayerAccessInventoryLambda) {
        this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
    }

    public void setMarkDirtyNotificationLambda(Notify markDirtyNotificationLambda) {
        this.markDirtyNotificationLambda = markDirtyNotificationLambda;
    }

    public void setOpenInventoryNotificationLambda(Notify openInventoryNotificationLambda) {
        this.openInventoryNotificationLambda = openInventoryNotificationLambda;
    }


    public void setCloseInventoryNotificationLambda(Notify closeInventoryNotificationLambda) {
        this.closeInventoryNotificationLambda = closeInventoryNotificationLambda;
    }


    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return canPlayerAccessInventoryLambda.test(player);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return furnaceComponentContents.isItemValid(index, stack);
    }

    @FunctionalInterface
    public interface Notify {
        void invoke();
    }

    @Override
    public void markDirty() {
        markDirtyNotificationLambda.invoke();
    }

    @Override
    public void openInventory(PlayerEntity player) {
        openInventoryNotificationLambda.invoke();
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        closeInventoryNotificationLambda.invoke();
    }

    @Override
    public int getSizeInventory() {
        return furnaceComponentContents.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < furnaceComponentContents.getSlots(); ++i) {
            if (!furnaceComponentContents.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return furnaceComponentContents.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (count < 0) throw new IllegalArgumentException("count should be >= 0:" + count);
        return furnaceComponentContents.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        int maxPossibleItemStackSize = furnaceComponentContents.getSlotLimit(index);
        return furnaceComponentContents.extractItem(index, maxPossibleItemStackSize, false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        furnaceComponentContents.setStackInSlot(index, stack);
    }

    @Override
    public void clear() {
        for (int i = 0; i < furnaceComponentContents.getSlots(); ++i) {
            furnaceComponentContents.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public ItemStack increaseStackSize(int index, ItemStack itemStackToInsert) {
        ItemStack leftoverItemStack = furnaceComponentContents.insertItem(index, itemStackToInsert, false);
        return leftoverItemStack;
    }

    public boolean doesItemStackFit(int index, ItemStack itemStackToInsert) {
        ItemStack leftoverItemStack = furnaceComponentContents.insertItem(index, itemStackToInsert, true);
        return leftoverItemStack.isEmpty();
    }

    // ---------

    private AlloyFurnaceZoneContents(int size) {
        this.furnaceComponentContents = new ItemStackHandler(size);
    }

    private AlloyFurnaceZoneContents(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda, Notify markDirtyNotificationLambda) {
        this.furnaceComponentContents = new ItemStackHandler(size);
        this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
        this.markDirtyNotificationLambda = markDirtyNotificationLambda;
    }

    // the function that the container should call in order to decide if the
    // given player can access the container's Inventory or not.  Only valid server side
    //  default is "true".
    private Predicate<PlayerEntity> canPlayerAccessInventoryLambda = x -> true;

    // the function that the container should call in order to tell the parent TileEntity that the
    // contents of its inventory have been changed.
    // default is "do nothing"
    private Notify markDirtyNotificationLambda = () -> {
    };

    // the function that the container should call in order to tell the parent TileEntity that the
    // container has been opened by a player (eg so that the chest can animate its lid being opened)
    // default is "do nothing"
    private Notify openInventoryNotificationLambda = () -> {
    };

    // the function that the container should call in order to tell the parent TileEntity that the
    // container has been closed by a player
    // default is "do nothing"
    private Notify closeInventoryNotificationLambda = () -> {
    };

    private final ItemStackHandler furnaceComponentContents;
}
