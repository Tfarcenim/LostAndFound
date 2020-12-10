package tfar.lostandfound;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class LostAndFoundSlot extends SlotItemHandler {

    protected final int index;
    public LostAndFoundSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.index = index;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public LostAndFoundData getItemHandler() {
        return (LostAndFoundData)super.getItemHandler();
    }

    @Override
    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        return super.onTake(player, stack);
    }
}
