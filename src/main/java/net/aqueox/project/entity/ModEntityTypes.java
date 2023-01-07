package net.aqueox.project.entity;

import net.aqueox.project.Project;
import net.aqueox.project.entity.custom.ChomperEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Project.MOD_ID);

    public static final RegistryObject<EntityType<ChomperEntity>> CHOMPER =
            ENTITY_TYPES.register("chomper",
                    () -> EntityType.Builder.of(ChomperEntity::new, MobCategory.MONSTER)
                            .sized(0.4f,1.5f)
                            .build(new ResourceLocation(Project.MOD_ID,"chomper").toString()));


    public static void register(IEventBus eventbus){
        ENTITY_TYPES.register(eventbus);
    }
}
