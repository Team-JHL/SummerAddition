package de.jakomi1.summeraddition.procedures;

import de.jakomi1.summeraddition.init.SummeradditionModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Objects;
import java.util.ServiceLoader;

public class SandShovelRightclickedOnBlockProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null) return;
        if(!(entity instanceof Player player)) return;
        if(!(world instanceof ServerLevel level)) return;
        BlockPos pos = BlockPos.containing(x, y, z);
        Block currentBlock = world.getBlockState(pos).getBlock();

        if (currentBlock == Blocks.SAND) {

            upgradeBlock(world, pos, SummeradditionModBlocks.SAND_CASTLE.get());
            awardAdvancement(entity, "summeraddition:build_sand_castle");
            damageItem(itemstack, player, level);
            playSound(world, pos);
            cooldown(entity, itemstack, 40);

        } else if (currentBlock == SummeradditionModBlocks.SAND_CASTLE.get()) {

            upgradeBlock(world, pos, Blocks.SAND);
            damageItem(itemstack, player, level);
            playSound(world, pos);
            cooldown(entity, itemstack, 40);
        }
    }


    private static void upgradeBlock(LevelAccessor world, BlockPos pos, Block newBlock) {
        world.setBlock(pos, newBlock.defaultBlockState(), 3);
    }


    public static void awardAdvancement(Entity entity, String advancementId) {
        if (entity instanceof ServerPlayer player) {

            ResourceLocation id = ResourceLocation.parse(advancementId);

            AdvancementHolder adv = Objects.requireNonNull(player.getServer())
                    .getAdvancements()
                    .get(id);

            if (adv != null) {
                AdvancementProgress progress =
                        player.getAdvancements().getOrStartProgress(adv);

                if (!progress.isDone()) {
                    for (String criterion : progress.getRemainingCriteria()) {
                        player.getAdvancements().award(adv, criterion);
                    }
                }
            }
        }
    }


    private static void damageItem(ItemStack stack, Player player, ServerLevel level) {
        stack.hurtAndBreak(10, level, player, item -> {} );
    }


    private static void playSound(LevelAccessor world, BlockPos pos) {

        ResourceLocation soundId =
                ResourceLocation.fromNamespaceAndPath("minecraft", "block.sand.fall");

        SoundEvent sound =
                BuiltInRegistries.SOUND_EVENT.get(soundId);

        if (world instanceof Level level) {

            if (!level.isClientSide()) {
                assert sound != null;
                level.playSound(
                        null,
                        pos,
                        sound,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                );

            } else {
                assert sound != null;
                level.playLocalSound(
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        sound,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F,
                        false
                );
            }
        }
    }


    private static void cooldown(Entity entity, ItemStack itemstack, int ticks) {

        if (entity instanceof Player player) {
            player.getCooldowns()
                    .addCooldown(itemstack.getItem(), ticks);
        }
    }
}