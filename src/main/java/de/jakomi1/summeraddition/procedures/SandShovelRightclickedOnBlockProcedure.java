package de.jakomi1.summeraddition.procedures;

import de.jakomi1.summeraddition.init.SummeradditionModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@SuppressWarnings("removal")
public class SandShovelRightclickedOnBlockProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        Block currentBlock = world.getBlockState(pos).getBlock();

         if (currentBlock == Blocks.SAND) {
            upgradeBlock(world, pos, SummeradditionModBlocks.SAND_CASTLE.get());
            awardAdvancement(entity, "summeraddition:build_sand_castle");
            damageItem(itemstack);
            playSound(world, pos);
            cooldown(entity, itemstack, 40);

        } else if (currentBlock == SummeradditionModBlocks.SAND_CASTLE.get()) {
            upgradeBlock(world, pos, Blocks.SAND);
            damageItem(itemstack);
            playSound(world, pos);
            cooldown(entity, itemstack, 40);
        }
    }

    private static void upgradeBlock(LevelAccessor world, BlockPos pos, Block newBlock) {
        world.setBlock(pos, newBlock.defaultBlockState(), 3);
    }

    public static void awardAdvancement(Entity entity, String advancementId) {
        if (entity instanceof ServerPlayer player) {
            Advancement adv = Objects.requireNonNull(player.getServer()).getAdvancements().getAdvancement(new ResourceLocation(advancementId));
            if (adv != null) {
                AdvancementProgress progress = player.getAdvancements().getOrStartProgress(adv);
                if (!progress.isDone()) {
                    for (String criterion : progress.getRemainingCriteria()) {
                        player.getAdvancements().award(adv, criterion);
                    }
                }
            }
        }
    }

    private static void damageItem(ItemStack itemStack) {
        if (itemStack.hurt(10, RandomSource.create(), null)) {
            itemStack.shrink(1);
            itemStack.setDamageValue(0);
        }
    }

    private static void playSound(LevelAccessor world, BlockPos pos) {
        if (world instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, pos, Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sand.fall"))), SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sand.fall"))), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }

    private static void cooldown(Entity entity, ItemStack itemstack, int ticks) {
        if (entity instanceof Player player) {
            player.getCooldowns().addCooldown(itemstack.getItem(), ticks);
        }
    }
}
