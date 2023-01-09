package net.aqueox.project.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.aqueox.project.Project;
import net.aqueox.project.entity.custom.ChomperEntity;
import net.aqueox.project.entity.custom.NoxEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.time.chrono.ChronoZonedDateTime;

public class NoxRenderer extends GeoEntityRenderer<NoxEntity> {

    public NoxRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NoxModel());
        this.shadowRadius = 0.2f;
    }

    @Override
    public ResourceLocation getTextureLocation(NoxEntity instance) {
        return new ResourceLocation(Project.MOD_ID, "textures/entity/nox.png");
    }

    @Override
    public RenderType getRenderType(NoxEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.9f, 0.9f, 0.9f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
