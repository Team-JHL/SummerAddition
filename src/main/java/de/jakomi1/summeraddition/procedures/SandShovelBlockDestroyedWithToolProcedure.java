package de.jakomi1.summeraddition.procedures;

import de.jakomi1.summeraddition.init.SummeradditionModBlocks;
import de.jakomi1.summeraddition.utils.SlotUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class SandShovelBlockDestroyedWithToolProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, @NotNull ItemStack itemstack) {
        if (entity == null) return;

        BlockPos pos = BlockPos.containing(x, y, z);

        if (world.getBlockState(pos).is(Blocks.SAND)
                || world.getBlockState(pos).is(SummeradditionModBlocks.SAND_CASTLE.get())) {
            return;
        }

        if (world instanceof Level level) {
            level.playSound(
                    null,
                    pos,
                    SoundEvents.ITEM_BREAK,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        if (entity instanceof Player player && world instanceof ServerLevel level) {
            itemstack.hurtAndBreak(
                    32,
                    level,
                    player,
                    item -> {}
            );
        }
    }
}