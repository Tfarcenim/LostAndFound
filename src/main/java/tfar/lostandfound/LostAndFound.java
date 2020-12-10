package tfar.lostandfound;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LostAndFound.MODID)
public class LostAndFound
{
    // Directly reference a log4j logger.

    public static final String MODID = "lostandfound";
    public static final Block block = new LostAndFoundBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS));
    public static final TileEntityType<LostAndFoundBlockEntity> blockEntity = TileEntityType.Builder.create(LostAndFoundBlockEntity::new,block).build(null);
    public static final ContainerType<LostAndFoundMenu> menu = new ContainerType<>(LostAndFoundMenu::new);


    public LostAndFound() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        bus.addListener(this::setup);
        // Register the doClientStuff method for modloading
        bus.addListener(this::doClientStuff);
        bus.addGenericListener(Block.class,this::block);
        bus.addGenericListener(Item.class,this::item);
        bus.addGenericListener(TileEntityType.class,this::blockEntity);
        bus.addGenericListener(ContainerType.class,this::menu);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST,this::itemDespawn);
    }

    private void itemDespawn(ItemExpireEvent e) {
        World world = e.getEntityItem().world;
        LostAndFoundData.getDefaultInstance((ServerWorld) world).addItem(e.getEntityItem().getItem().copy());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(menu, LostAndFoundScreen::new);
    }

    public static final String s = "lost_and_found";

    private void block(final RegistryEvent.Register<Block> e) {
        e.getRegistry().register(block.setRegistryName(s));
    }

    private void item(final RegistryEvent.Register<Item> e) {
        e.getRegistry().register(new BlockItem(block,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(s));
    }

    private void blockEntity(final RegistryEvent.Register<TileEntityType<?>> e) {
        e.getRegistry().register(blockEntity.setRegistryName(s));
    }

    private void menu(final RegistryEvent.Register<ContainerType<?>> e) {
        e.getRegistry().register(menu.setRegistryName(s));
    }
}
