package de.jakomi1.summeraddition;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.jakomi1.summeraddition.init.*;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod(SummeradditionMod.MODID)
public class SummeradditionMod {

    public static final String MODID = "summeraddition";

    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue =
            new ConcurrentLinkedQueue<>();

    public SummeradditionMod(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(this);

        SummeradditionModBlocks.REGISTRY.register(modEventBus);
        SummeradditionModItems.REGISTRY.register(modEventBus);
        SummeradditionModTabs.REGISTRY.register(modEventBus);
        SummeradditionModPaintings.REGISTRY.register(modEventBus);
        SummeradditionModDataComponents.REGISTRY.register(modEventBus);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(
                MODID,
                "sand_shovel"
        );

        RecipeManager recipeManager = Objects.requireNonNull(player.getServer())
                .getRecipeManager();

        Optional<RecipeHolder<?>> recipeOpt = recipeManager.byKey(recipeId);

        recipeOpt.ifPresent(recipe -> {
            player.getRecipeBook().add(recipe);
            player.getRecipeBook().sendInitialRecipeBook(player);
        });
    }
}