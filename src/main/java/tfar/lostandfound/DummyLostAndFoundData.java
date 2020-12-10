package tfar.lostandfound;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;

//client copy of Lost and Found
public class DummyLostAndFoundData extends LostAndFoundData {

    public DummyLostAndFoundData(String name) {
        super(name);
        fill();
    }

    private void fill() {
        for (int i = 0; i < 54;i++) {
            stacks.add(ItemStack.EMPTY);
        }
    }

    @Override
    public int getSlots() {
        return 54;
    }

    public void removeItem(int index) {
        super.removeItem(index);
        addItem(ItemStack.EMPTY);
    }
}
