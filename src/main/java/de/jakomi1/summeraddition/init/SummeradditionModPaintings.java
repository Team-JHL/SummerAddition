package de.jakomi1.summeraddition.init;

import de.jakomi1.summeraddition.SummeradditionMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public class SummeradditionModPaintings {

    public static final DeferredRegister<PaintingVariant> REGISTRY =
            DeferredRegister.create(
                    Registries.PAINTING_VARIANT,
                    SummeradditionMod.MODID
            );

    public static final DeferredHolder<PaintingVariant, PaintingVariant> SUMMER_SIDE =
            REGISTRY.register("summer_side", () ->
                    new PaintingVariant(
                            16,
                            16,
                            ResourceLocation.fromNamespaceAndPath(
                                    SummeradditionMod.MODID,
                                    "summer_side"
                            )
                    )
            );
}