package de.jakomi1.summeraddition.block;

import de.jakomi1.summeraddition.init.SummeradditionModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ParsleyPlantBlock extends FlowerBlock implements BonemealableBlock {
        public ParsleyPlantBlock() {
        super(MobEffects.MOVEMENT_SPEED.getDelegate(), 100,
                Properties.of()
                        .mapColor(MapColor.PLANT)
                        .sound(SoundType.CROP)
                        .noCollission()
                        .instabreak()
                        .pushReaction(PushReaction.DESTROY));
    }

    @Override
    public int getFlammability(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction face) {
        return 60;
    }


    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        int amount = 1 + random.nextInt(2);
        for (int i = 0; i < amount; i++) {
            popResource(world, pos, new net.minecraft.world.item.ItemStack(
                    SummeradditionModItems.PARSLEY.get()
            ));
        }
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }



}
