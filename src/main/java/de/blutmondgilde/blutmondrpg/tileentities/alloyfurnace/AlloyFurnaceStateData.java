package de.blutmondgilde.blutmondrpg.tileentities.alloyfurnace;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

import java.util.Arrays;

public class AlloyFurnaceStateData implements IIntArray {
    public static final int FUEL_SLOTS_COUNT = AlloyFurnaceTileEntity.FUEL_SLOTS_COUNT;
    public int cookTimeElapsed;
    public int cookTimeForCompletion;

    public int[] burnTimeInitialValues = new int[FUEL_SLOTS_COUNT];
    public int[] burnTimeRemainings = new int[FUEL_SLOTS_COUNT];

    public void putIntoNBT(CompoundNBT nbtTagCompound) {
        nbtTagCompound.putInt("CookTimeElapsed", cookTimeElapsed);
        nbtTagCompound.putInt("CookTimeForCompletion", cookTimeElapsed);
        nbtTagCompound.putIntArray("burnTimeRemainings", burnTimeRemainings);
        nbtTagCompound.putIntArray("burnTimeInitial", burnTimeInitialValues);
    }

    public void readFromNBT(CompoundNBT nbtTagCompound) {
        cookTimeElapsed = nbtTagCompound.getInt("CookTimeElapsed");
        cookTimeForCompletion = nbtTagCompound.getInt("CookTimeForCompletion");
        burnTimeRemainings = Arrays.copyOf(nbtTagCompound.getIntArray("burnTimeRemainings"), FUEL_SLOTS_COUNT);
        burnTimeInitialValues = Arrays.copyOf(nbtTagCompound.getIntArray("burnTimeInitialValues"), FUEL_SLOTS_COUNT);
    }

    private final int COOKTIME_INDEX = 0;
    private final int COOKTIME_FOR_COMPLETION_INDEX = 1;
    private final int BURNTIME_INITIAL_VALUE_INDEX = 2;
    private final int BURNTIME_REMAINING_INDEX = BURNTIME_INITIAL_VALUE_INDEX + FUEL_SLOTS_COUNT;
    private final int END_OF_DATA_INDEX_PLUS_ONE = BURNTIME_REMAINING_INDEX + FUEL_SLOTS_COUNT;

    @Override
    public int get(int index) {
        validateIndex(index);
        if (index == COOKTIME_INDEX) {
            return cookTimeElapsed;
        } else if (index == COOKTIME_FOR_COMPLETION_INDEX) {
            return cookTimeForCompletion;
        } else if (index >= BURNTIME_INITIAL_VALUE_INDEX && index < BURNTIME_REMAINING_INDEX) {
            return burnTimeInitialValues[index - BURNTIME_INITIAL_VALUE_INDEX];
        } else {
            return burnTimeRemainings[index - BURNTIME_REMAINING_INDEX];
        }
    }

    @Override
    public void set(int index, int value) {
        validateIndex(index);
        if (index == COOKTIME_INDEX) {
            cookTimeElapsed = value;
        } else if (index == COOKTIME_FOR_COMPLETION_INDEX) {
            cookTimeForCompletion = value;
        } else if (index >= BURNTIME_INITIAL_VALUE_INDEX && index < BURNTIME_REMAINING_INDEX) {
            burnTimeInitialValues[index - BURNTIME_INITIAL_VALUE_INDEX] = value;
        } else {
            burnTimeRemainings[index - BURNTIME_REMAINING_INDEX] = value;
        }
    }

    @Override
    public int size() {
        return END_OF_DATA_INDEX_PLUS_ONE;
    }

    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds:" + index);
        }
    }
}
