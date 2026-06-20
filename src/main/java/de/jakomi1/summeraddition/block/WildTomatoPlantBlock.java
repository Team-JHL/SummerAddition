package de.jakomi1.summeraddition.block;

import de.jakomi1.summeraddition.init.SummeradditionModBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class WildTomatoPlantBlock extends FlowerBlock implements BonemealableBlock {



    public WildTomatoPlantBlock() {
        super(() -> MobEffects.MOVEMENT_SPEED, 100,
                Properties.of()
                        .mapColor(MapColor.PLANT)
                        .sound(SoundType.CROP)
                        .noCollission()
                        .instabreak()
                        .pushReaction(PushReaction.DESTROY));
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        int amount = 1 + random.nextInt(2);
        for (int i = 0; i < amount; i++) {
            popResource(world, pos, new net.minecraft.world.item.ItemStack(
                    de.jakomi1.summeraddition.init.SummeradditionModItems.TOMATO.get()
            ));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @OnlyIn(Dist.CLIENT)
    public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((bs, world, pos, index) -> {
            return world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5D, 1.0D);
        }, SummeradditionModBlocks.WILD_TOMATO_PLANT.get());
    }

    @OnlyIn(Dist.CLIENT)
    public static void itemColorLoad(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, index) -> {
            return GrassColor.get(0.5D, 1.0D);
        }, SummeradditionModBlocks.WILD_TOMATO_PLANT.get());
    }
}
