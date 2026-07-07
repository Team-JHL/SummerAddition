package de.jakomi1.summeraddition.init;

import de.jakomi1.summeraddition.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SummeradditionModItems {

    public static final DeferredRegister<Item> REGISTRY =
            DeferredRegister.create(ForgeRegistries.ITEMS, "summeraddition");

    public static final RegistryObject<Item> SAND_SHOVEL = REGISTRY.register("sand_shovel", SandShovelItem::new);
    public static final RegistryObject<Item> TOMATO = REGISTRY.register("tomato", TomatoItem::new);
    public static final RegistryObject<Item> PARSLEY = REGISTRY.register("parsley", ParsleyItem::new);
    public static final RegistryObject<Item> TOMATO_STEW = REGISTRY.register("tomato_stew", TomatoStewItem::new);
    public static final RegistryObject<Item> ICE_LOLLY = REGISTRY.register("ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.BASIC));
    public static final RegistryObject<Item> BLUE_ICE_LOLLY = REGISTRY.register("blue_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.BLUE));
    public static final RegistryObject<Item> YELLOW_ICE_LOLLY = REGISTRY.register("yellow_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.YELLOW));
    public static final RegistryObject<Item> RED_ICE_LOLLY = REGISTRY.register("red_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.RED));
    public static final RegistryObject<Item> GREEN_ICE_LOLLY = REGISTRY.register("green_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.GREEN));
    public static final RegistryObject<Item> MIXED_ICE_LOLLY = REGISTRY.register("mixed_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.MIXED));
    public static final RegistryObject<Item> CONE = REGISTRY.register("cone", Cone::new);
    public static final RegistryObject<Item> PINK_ICE_CREAM_CONE = REGISTRY.register("pink_ice_cream_cone", () -> new IceCreamConeItem(IceCreamConeItem.IceCreamType.PINK));
    public static final RegistryObject<Item> MIXED_ICE_CREAM_CONE = REGISTRY.register("mixed_ice_cream_cone", () -> new IceCreamConeItem(IceCreamConeItem.IceCreamType.MIXED));
    public static final RegistryObject<Item> ORANGE_ICE_CREAM_CONE = REGISTRY.register("orange_ice_cream_cone", () -> new IceCreamConeItem(IceCreamConeItem.IceCreamType.ORANGE));
    public static final RegistryObject<Item> LIME_ICE_CREAM_CONE = REGISTRY.register("lime_ice_cream_cone", () -> new IceCreamConeItem(IceCreamConeItem.IceCreamType.LIME));

    public static final RegistryObject<Item> SAND_CASTLE = registerBlockItem(SummeradditionModBlocks.SAND_CASTLE);
    public static final RegistryObject<Item> WILD_TOMATO_PLANT = registerBlockItem(SummeradditionModBlocks.WILD_TOMATO_PLANT);

    private static RegistryObject<Item> registerBlockItem(RegistryObject<Block> block) {
        assert block.getId() != null;
        return REGISTRY.register(block.getId().getPath(), () ->
                new BlockItem(block.get(), new Item.Properties()));
    }
}
