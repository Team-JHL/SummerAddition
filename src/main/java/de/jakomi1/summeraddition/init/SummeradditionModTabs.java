package de.jakomi1.summeraddition.init;

import de.jakomi1.summeraddition.SummeradditionMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SummeradditionModTabs {

    public static final DeferredRegister<CreativeModeTab> REGISTRY =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SummeradditionMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SUMMER_ADDITION =
            REGISTRY.register("summer_addition", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("item_group.summeraddition.summer_addition"))
                            .icon(() -> new ItemStack(SummeradditionModItems.ICE_LOLLY.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(SummeradditionModItems.SAND_SHOVEL.get());
                                output.accept(SummeradditionModItems.TOMATO.get());
                                output.accept(SummeradditionModItems.PARSLEY.get());
                                output.accept(SummeradditionModItems.TOMATO_STEW.get());

                                output.accept(SummeradditionModItems.ICE_LOLLY.get());
                                output.accept(SummeradditionModItems.RED_ICE_LOLLY.get());
                                output.accept(SummeradditionModItems.GREEN_ICE_LOLLY.get());
                                output.accept(SummeradditionModItems.BLUE_ICE_LOLLY.get());
                                output.accept(SummeradditionModItems.YELLOW_ICE_LOLLY.get());
                                output.accept(SummeradditionModItems.MIXED_ICE_LOLLY.get());

                                output.accept(SummeradditionModItems.CONE.get());
                                output.accept(SummeradditionModItems.PINK_ICE_CREAM_CONE.get());
                                output.accept(SummeradditionModItems.ORANGE_ICE_CREAM_CONE.get());
                                output.accept(SummeradditionModItems.LIME_ICE_CREAM_CONE.get());
                                output.accept(SummeradditionModItems.MIXED_ICE_CREAM_CONE.get());
                            })
                            .build()
            );
}