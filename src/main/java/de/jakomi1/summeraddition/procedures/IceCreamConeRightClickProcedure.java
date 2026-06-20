package de.jakomi1.summeraddition.procedures;

import de.jakomi1.summeraddition.item.IceCreamConeItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.obj.ObjMaterialLibrary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.Display.ItemDisplay;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import static de.jakomi1.summeraddition.procedures.IceLollyRightClickProcedure.iceTrackerMap;

public class IceCreamConeRightClickProcedure {

    public static void execute(@NotNull Level world, @NotNull Player player, @NotNull ItemStack stack, IceCreamConeItem.IceCreamType type) {
        if (world.isClientSide()) {
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
            return;
        }
        if(!player.isCrouching()) {
            // Cooldown setzen (200 Ticks = 10 Sekunden)
            player.getCooldowns().addCooldown(stack.getItem(), 200);

            // Haltbarkeit reduzieren
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
            if (world instanceof ServerLevel serverLevel) {
                UUID uuid = player.getUUID();
                long gameTime = serverLevel.getGameTime();

                IceLollyRightClickProcedure.IceTracker tracker = iceTrackerMap.getOrDefault(uuid, new IceLollyRightClickProcedure.IceTracker(0, gameTime));

                if (gameTime - tracker.firstUseTick() <= 200) {
                    tracker = new IceLollyRightClickProcedure.IceTracker(tracker.count() + 1, tracker.firstUseTick());
                    if (tracker.count() >= 3) {
                        player.setTicksFrozen(400);
                        iceTrackerMap.remove(uuid);
                    } else {
                        iceTrackerMap.put(uuid, tracker);
                    }
                } else {
                    iceTrackerMap.put(uuid, new IceLollyRightClickProcedure.IceTracker(1, gameTime));
                }
            }

            if(stack.getItem() instanceof IceCreamConeItem coneItem) {
                coneItem.applyEffect(player);
            }
        } else {
            player.setItemInHand(player.getUsedItemHand(), new ItemStack(Items.AIR));

            ItemDisplay display = (ItemDisplay) EntityType.ITEM_DISPLAY.create(world);
            if (display != null) {
                display.setPos(player.position().add(0,1.5,0));

                // Reflection um private/package-private Methode aufzurufen
                try {
                    Method setItemStack = ItemDisplay.class.getDeclaredMethod("setItemStack", ItemStack.class);
                    setItemStack.setAccessible(true);
                    setItemStack.invoke(display, stack);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                world.addFreshEntity(display);

                Vec3 look = player.getLookAngle();
                final Vec3[] velocity = {look.scale(0.55)};
                double gravity = 0.0185;

                MinecraftForge.EVENT_BUS.addListener((TickEvent.ServerTickEvent event) -> {
                    if (!display.isRemoved()) {
                        // Gravitation anwenden
                        velocity[0] = velocity[0].subtract(0, gravity, 0);

                        Vec3 pos = display.position();
                        Vec3 nextPos = pos.add(velocity[0]);

                        AABB displayBox = display.getBoundingBox().move(velocity[0]);
                        Level level = display.level();

                        // Blockkollision prüfen
                        boolean collided = level.getBlockCollisions(display, displayBox).iterator().hasNext();

                        // Itemkollision prüfen
                        if (!collided) {
                            List<Entity> entities = level.getEntities(display, displayBox, e -> e instanceof ItemEntity);
                            collided = !entities.isEmpty();
                        }

                        if (collided) {
                            // Partikel spawnen (nur auf ServerLevel)
                            if (level instanceof ServerLevel serverLevel) {
                                // Partikel
                                serverLevel.sendParticles(
                                        ParticleTypes.EXPLOSION,
                                        pos.x, pos.y, pos.z,
                                        10, 0.2, 0.2, 0.2, 0.1
                                );

                                // Explosionsradius
                                double explosionRadius = 4.0;

                                // AABB um die Explosion
                                AABB explosionBox = new AABB(
                                        pos.x - explosionRadius, pos.y - explosionRadius, pos.z - explosionRadius,
                                        pos.x + explosionRadius, pos.y + explosionRadius, pos.z + explosionRadius
                                );

                                // Spieler in Reichweite sammeln
                                List<Player> playersInRange = serverLevel.getEntitiesOfClass(
                                        Player.class,
                                        explosionBox,
                                        p -> p.isAlive() && !p.isSpectator()
                                );

                                // Beispielverwendung
                                for (Player p : playersInRange) {
                                    if(stack.getItem() instanceof IceCreamConeItem coneItem) {
                                        coneItem.applyEffect(p);
                                    }
                                }
                            }

                            // Display entfernen
                            display.remove(Entity.RemovalReason.DISCARDED);
                        } else {
                            display.setPos(nextPos.x, nextPos.y, nextPos.z);
                        }
                    }
                });


            }

        }
    }

}
