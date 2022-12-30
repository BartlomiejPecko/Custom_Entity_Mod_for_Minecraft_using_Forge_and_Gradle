package net.aqueox.project.world.feature;

import com.google.common.base.Suppliers;
import net.aqueox.project.Project;
import net.aqueox.project.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Project.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_XENOTIME_ORES =
            Suppliers.memoize(() -> List.of(
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.XENOTIME_ORE.get().defaultBlockState())));


    public static final RegistryObject<ConfiguredFeature<?, ?>> XENOTIME_ORE = CONFIGURED_FEATURES.register("xenotime_ore",
            ()-> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_XENOTIME_ORES.get(), 2)));

    public static void register(IEventBus eventbus) {
        CONFIGURED_FEATURES.register(eventbus);
    }

}
