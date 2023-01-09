package net.aqueox.project.entity.client;

import net.aqueox.project.Project;
import net.aqueox.project.entity.custom.NoxEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NoxModel extends AnimatedGeoModel<NoxEntity> {
    @Override
    public ResourceLocation getModelResource(NoxEntity object) {
        return new ResourceLocation(Project.MOD_ID, "geo/nox.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NoxEntity object) {
        return new ResourceLocation(Project.MOD_ID, "textures/entity/nox.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NoxEntity animatable) {
        return new ResourceLocation(Project.MOD_ID, "animations/nox.animation.json");
    }
}
