package de.jakomi1.summeraddition.init;

import de.jakomi1.summeraddition.SummeradditionMod;
import de.jakomi1.summeraddition.block.ParsleyPlantBlock;
import de.jakomi1.summeraddition.block.SandCastleBlock;
import de.jakomi1.summeraddition.block.WildTomatoPlantBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class SummeradditionModBlocks {

    public static final DeferredRegister.Blocks REGISTRY =
            DeferredRegister.createBlocks(SummeradditionMod.MODID);

    public static final DeferredHolder<Block, Block> SAND_CASTLE =
            REGISTRY.register("sand_castle", SandCastleBlock::new);

    public static final DeferredHolder<Block, Block> WILD_TOMATO_PLANT =
            REGISTRY.register("wild_tomato_plant", WildTomatoPlantBlock::new);

    public static final DeferredHolder<Block, Block> PARSLEY_PLANT =
            REGISTRY.register("parsley_plant", ParsleyPlantBlock::new);

    @EventBusSubscriber(
            modid = SummeradditionMod.MODID,
            bus = EventBusSubscriber.Bus.MOD,
            value = Dist.CLIENT
    )
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