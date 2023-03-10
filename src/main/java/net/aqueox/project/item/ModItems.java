package net.aqueox.project.item;

import net.aqueox.project.Project;
import net.aqueox.project.entity.ModEntityTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Project.MOD_ID);

    public static final RegistryObject<Item> XENOTIME = ITEMS.register("xenotime",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    public static final RegistryObject<Item> NOX_SPAWN_EGG = ITEMS.register("nox_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.NOX,  0xADD8E6, 0x00008B,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
