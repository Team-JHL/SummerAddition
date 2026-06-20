//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.jakomi1.summeraddition.init;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SummeradditionModPaintings {
    public static final DeferredRegister<PaintingVariant> REGISTRY;
    public static final RegistryObject<PaintingVariant> SUMMER_SIDE;

    public SummeradditionModPaintings() {
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, "summeraddition");
        SUMMER_SIDE = REGISTRY.register("summer_side", () -> {
            return new PaintingVariant(16, 16);
        });
    }
}
