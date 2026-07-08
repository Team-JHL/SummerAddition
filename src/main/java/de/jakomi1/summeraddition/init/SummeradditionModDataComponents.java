package de.jakomi1.summeraddition.init;

import com.mojang.serialization.Codec;
import de.jakomi1.summeraddition.SummeradditionMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

import java.util.function.UnaryOperator;

public class SummeradditionModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> REGISTRY =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, SummeradditionMod.MODID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ICE_CREAM_TYPE =
            REGISTRY.register(
                    "ice_cream_type",
                    () -> DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
}