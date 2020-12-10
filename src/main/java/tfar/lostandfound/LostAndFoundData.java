package tfar.lostandfound;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LostAndFoundData extends WorldSavedData implements IItemHandlerModifiable {

    public final LazyOptional<IItemHandler> optional = LazyOptional.of(() -> this);

    public LostAndFoundData(String name) {
        super(name);
    }

    protected final List<ItemStack> stacks = new ArrayList<>();

    public static LostAndFoundData getDefaultInstance(ServerWorld world) {
        return world.getServer().getWorld(World.OVERWORLD).getSavedData().getOrCreate(() -> new LostAndFoundData(LostAndFound.s), LostAndFound.s);//overworld storage
    }

    public void addItem(ItemStack stack) {
        stacks.add(stack);
        markDirty();
    }

    public void removeItem(int index) {
        stacks.remove(index);
    }

    @Override
    public void read(CompoundNBT nbt) {
        ListNBT listNBT = nbt.getList("items", Constants.NBT.TAG_COMPOUND);
        for (INBT inbt : listNBT) {
            stacks.add(ItemStack.read((CompoundNBT) inbt));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT listNBT = new ListNBT();
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                listNBT.add(stack.serializeNBT());
            }
        }
        compound.put("items",listNBT);
        return compound;
    }

    @Override
    public int getSlots() {
        return stacks.size();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= stacks.size())
        return ItemStack.EMPTY;
        else return stacks.get(slot);
    }

    @Nonnull
    @Override
    //can't add items, only remove
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot >= stacks.size()) return ItemStack.EMPTY;
        ItemStack stackInSlot = getStackInSlot(slot);
        int toExtract = Math.min(amount,stackInSlot.getCount());

        ItemStack extracted = ItemHandlerHelper.copyStackWithSize(stackInSlot,toExtract);

        if (!simulate) {
            if (toExtract >= stackInSlot.getCount()) {
                removeItem(slot);
            } else {
                stackInSlot.shrink(toExtract);
            }
            markDirty();
        }
        return extracted;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        if (slot < stacks.size()) {
            if (stack.isEmpty()) {
                stacks.remove(slot);
            } else {
                stacks.set(slot, stack);
            }
        }
    }
}
