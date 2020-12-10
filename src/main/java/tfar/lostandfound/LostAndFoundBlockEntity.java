package tfar.lostandfound;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LostAndFoundBlockEntity extends TileEntity implements INamedContainerProvider {

    public LostAndFoundBlockEntity() {
        super(LostAndFound.blockEntity);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(LostAndFound.block.getTranslationKey());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap,LostAndFoundData.getDefaultInstance((ServerWorld) world).optional);
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new LostAndFoundMenu(p_createMenu_1_,p_createMenu_2_,LostAndFoundData.getDefaultInstance((ServerWorld)world));
    }
}
