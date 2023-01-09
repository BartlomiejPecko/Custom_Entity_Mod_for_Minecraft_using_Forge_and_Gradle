package net.aqueox.project.event;

import net.aqueox.project.Project;
import net.aqueox.project.entity.ModEntityTypes;
import net.aqueox.project.entity.custom.ChomperEntity;
import net.aqueox.project.entity.custom.NoxEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Project.MOD_ID)
    public static class ForgeEvents {









    }
    @Mod.EventBusSubscriber(modid=Project.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.CHOMPER.get(), ChomperEntity.setAttributes());
            event.put(ModEntityTypes.NOX.get(), NoxEntity.setAttributes());
        }




    }
}