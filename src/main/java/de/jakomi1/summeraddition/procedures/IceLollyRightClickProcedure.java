package de.jakomi1.summeraddition.procedures;

import de.jakomi1.summeraddition.item.IceLollyItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

import static de.jakomi1.summeraddition.procedures.SandShovelRightclickedOnBlockProcedure.awardAdvancement;

public class IceLollyRightClickProcedure {

    public static final HashMap<UUID, IceTracker> iceTrackerMap = new HashMap<>();

    public static void execute(@NotNull Level level, @NotNull Player player, @NotNull ItemStack stack, IceLollyItem.LollyType color) {
        if (level.isClientSide()) {
            player.level().playSound(
                    player,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.HONEY_DRINK,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }
        if (level.isClientSide) return;

        player.getCooldowns().addCooldown(stack.getItem(), 200);

        stack.hurtAndBreak(1, player, p -> {
            p.broadcastBreakEvent(player.getUsedItemHand());
            if (!level.isClientSide()) ((ServerLevel)level).getServer().execute(() -> player.getInventory().add(new ItemStack(Items.STICK)));

        });




        // Track consumption within 200 ticks
        if (level instanceof ServerLevel serverLevel) {
            if(player.getTicksFrozen() == 0) {
                UUID uuid = player.getUUID();
                long gameTime = serverLevel.getGameTime();

                IceTracker tracker = iceTrackerMap.getOrDefault(uuid, new IceTracker(0, gameTime));

                if (gameTime - tracker.firstUseTick() <= 200) {
                    tracker = new IceTracker(tracker.count() + 1, tracker.firstUseTick());
                    if (tracker.count() >= 3) {
                        player.setTicksFrozen(100);
                        iceTrackerMap.remove(uuid);
                        awardAdvancement(player, "summeraddition:brain_frosting");
                    } else {
                        iceTrackerMap.put(uuid, tracker);
                    }
                } else {
                    iceTrackerMap.put(uuid, new IceTracker(1, gameTime));
                }
            } else {
                player.setTicksFrozen(400);
            }

        }

        // Apply color-based effects
        switch (color) {
            case RED -> player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 0));
            case GREEN -> player.addEffect(new MobEffectInstance(MobEffects.LUCK, 300, 0));
            case BLUE -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0));
            case YELLOW -> player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 300, 0));
            case MIXED -> addAllEffects(player);
        }
    }

    private static void addAllEffects(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 0));
        player.addEffect(new MobEffectInstance(MobEffects.LUCK, 300, 0));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 300, 0));
    }

    public record IceTracker(int count, long firstUseTick) {}
}
