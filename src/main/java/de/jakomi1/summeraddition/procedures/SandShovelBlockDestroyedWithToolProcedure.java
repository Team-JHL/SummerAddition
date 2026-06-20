package de.jakomi1.summeraddition.procedures;

import de.jakomi1.summeraddition.init.SummeradditionModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class SandShovelBlockDestroyedWithToolProcedure {
    @SuppressWarnings("removal")
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null) return;

        BlockPos pos = new BlockPos((int) x, (int) y, (int) z);

        // Überprüfe Blocktypen
        if (world.getBlockState(pos).getBlock() == Blocks.SAND
                || world.getBlockState(pos).getBlock() == SummeradditionModBlocks.SAND_CASTLE.get()) {

            // Simulierter Schaden (eigentlich 0, z. B. zum Triggern von Events)
            entity.hurt(new DamageSource(
                    world.registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(DamageTypes.GENERIC)), 0.0F);
            return;
        }

        // Spiele Soundeffekt ab
        if (world instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, pos,
                        Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.break"))),
                        SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                level.playLocalSound(x, y, z,
                        Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.break"))),
                        SoundSource.PLAYERS, 1.0F, 1.0F, false);
            }
        }

        // Zufällige Item-Beschädigung (max. 512 Haltbarkeit)
        if (itemstack.hurt(32, RandomSource.create(), null)) {
            itemstack.setCount(itemstack.getCount() - 1); // Entferne Item, falls es kaputt geht
            itemstack.setDamageValue(0); // Zurücksetzen (optional, je nach Logik)
        }
    }
}
