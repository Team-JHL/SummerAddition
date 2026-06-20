package de.jakomi1.summeraddition;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.jakomi1.summeraddition.init.SummeradditionModBlocks;
import de.jakomi1.summeraddition.init.SummeradditionModItems;
import de.jakomi1.summeraddition.init.SummeradditionModPaintings;
import de.jakomi1.summeraddition.init.SummeradditionModTabs;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("removal")
@Mod(SummeradditionMod.MODID)
public class SummeradditionMod {
    public static final String MODID = "summeraddition";
    public static final Logger LOGGER = LogManager.getLogger(SummeradditionMod.class);

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, MODID),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int messageID = 0;


    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public SummeradditionMod() {
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SummeradditionModBlocks.REGISTRY.register(modEventBus);
        SummeradditionModItems.REGISTRY.register(modEventBus);
        SummeradditionModTabs.REGISTRY.register(modEventBus);
        SummeradditionModPaintings.REGISTRY.register(modEventBus);

    }


    public static <T> void addNetworkMessage(
            Class<T> messageType,
            BiConsumer<T, FriendlyByteBuf> encoder,
            Function<FriendlyByteBuf, T> decoder,
            BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {

        PACKET_HANDLER.registerMessage(messageID++, messageType, encoder, decoder, messageConsumer);
    }


    public static void queueServerWork(int ticks, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
            workQueue.add(new AbstractMap.SimpleEntry<>(action, ticks));
        } else {
            LOGGER.warn("queueServerWork wasn't called with server thread!");
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> toRun = new ArrayList<>();

            for (AbstractMap.SimpleEntry<Runnable, Integer> work : workQueue) {
                int ticksLeft = work.getValue() - 1;
                work.setValue(ticksLeft);

                if (ticksLeft <= 0) {
                    toRun.add(work);
                }
            }

            toRun.forEach(entry -> {
                try {
                    entry.getKey().run();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            });

            workQueue.removeAll(toRun);
        }
    }


    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ResourceLocation MY_RECIPE = new ResourceLocation("summeraddition", "sand_shovel");
        RecipeManager recipeManager = Objects.requireNonNull(player.getServer()).getRecipeManager();
        Optional<? extends Recipe<?>> recipeOpt = recipeManager.byKey(MY_RECIPE);
        if (recipeOpt.isPresent()) {
            Recipe<?> recipe = recipeOpt.get();
            player.getRecipeBook().add(recipe);
            player.getRecipeBook().sendInitialRecipeBook(player);
        }
    }
}
