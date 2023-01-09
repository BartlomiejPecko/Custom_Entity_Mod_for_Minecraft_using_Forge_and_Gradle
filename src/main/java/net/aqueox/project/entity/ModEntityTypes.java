package net.aqueox.project.entity;

import net.aqueox.project.Project;
import net.aqueox.project.entity.custom.NoxEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Project.MOD_ID);


    public static final RegistryObject<EntityType<NoxEntity>> NOX = ENTITY_TYPES.register("nox",
            () -> EntityType.Builder.of(NoxEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.8f)
                    .build(new ResourceLocation(Project.MOD_ID, "nox").toString()));


    public static void register(IEventBus eventbus){
        ENTITY_TYPES.register(eventbus);
    }
}
