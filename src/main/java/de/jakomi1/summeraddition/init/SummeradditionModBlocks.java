package de.jakomi1.summeraddition.init;

import de.jakomi1.summeraddition.block.ParsleyPlantBlock;
import de.jakomi1.summeraddition.block.SandCastleBlock;
import de.jakomi1.summeraddition.block.WildTomatoPlantBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static de.jakomi1.summeraddition.SummeradditionMod.MODID;

public class SummeradditionModBlocks {
    public static final DeferredRegister<Block> REGISTRY  = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> SAND_CASTLE = REGISTRY.register("sand_castle", SandCastleBlock::new);
    public static final RegistryObject<Block> WILD_TOMATO_PLANT = REGISTRY.register("wild_tomato_plant", WildTomatoPlantBlock::new);
    public static final RegistryObject<Block> PARLSEY_PLANT = REGISTRY.register("parsley_plant", ParsleyPlantBlock::new);

    public SummeradditionModBlocks() {
    }
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class BlocksClientSideHandler {
        @SubscribeEvent
        public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
            WildTomatoPlantBlock.blockColorLoad(event);
        }

        @SubscribeEvent
        public static void itemColorLoad(RegisterColorHandlersEvent.Item event) {
            WildTomatoPlantBlock.itemColorLoad(event);
        }
    }


}
