package de.jakomi1.summeraddition.init;

import de.jakomi1.summeraddition.SummeradditionMod;
import de.jakomi1.summeraddition.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SummeradditionModItems {

    public static final DeferredRegister.Items REGISTRY =
            DeferredRegister.createItems(SummeradditionMod.MODID);

    public static final DeferredHolder<Item, Item> SAND_SHOVEL =
            REGISTRY.register("sand_shovel", SandShovelItem::new);

    public static final DeferredHolder<Item, Item> TOMATO =
            REGISTRY.register("tomato", TomatoItem::new);

    public static final DeferredHolder<Item, Item> PARSLEY =
            REGISTRY.register("parsley", ParsleyItem::new);

    public static final DeferredHolder<Item, Item> TOMATO_STEW =
            REGISTRY.register("tomato_stew", TomatoStewItem::new);

    public static final DeferredHolder<Item, Item> ICE_LOLLY =
            REGISTRY.register("ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.BASIC));

    public static final DeferredHolder<Item, Item> BLUE_ICE_LOLLY =
            REGISTRY.register("blue_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.BLUE));

    public static final DeferredHolder<Item, Item> YELLOW_ICE_LOLLY =
            REGISTRY.register("yellow_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.YELLOW));

    public static final DeferredHolder<Item, Item> RED_ICE_LOLLY =
            REGISTRY.register("red_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.RED));

    public static final DeferredHolder<Item, Item> GREEN_ICE_LOLLY =
            REGISTRY.register("green_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.GREEN));

    public static final DeferredHolder<Item, Item> MIXED_ICE_LOLLY =
            REGISTRY.register("mixed_ice_lolly", () -> new IceLollyItem(IceLollyItem.LollyType.MIXED));

    public static final DeferredHolder<Item, Item> CONE =
            REGISTRY.register("cone", Cone::new);

    public static final DeferredHolder<Item, Item> PINK_ICE_CREAM_CONE =
            REGISTRY.register("pink_ice_cream_cone", () -> new IceCreamConeItem(IceCreamConeItem.IceCreamType.PINK));

    public static final DeferredHolder<Item, Item> MIXED_ICE_CREAM_CONE =
            REGISTRY.register("mixed_ice_cream_cone", () -> new IceCreamConeItem(IceCreamConeItem.IceCreamType.MIXED));

    public static final DeferredHolder<Item, Item> ORANGE_ICE_CREAM_CONE =
            REGISTRY.register("orange_ice_cream_cone", () -> new IceCreamConeItem(IceCreamConeItem.IceCreamType.ORANGE));

    public static final DeferredHolder<Item, Item> LIME_ICE_CREAM_CONE =
            REGISTRY.register("lime_ice_cream_cone", () -> new IceCreamConeItem(IceCreamConeItem.IceCreamType.LIME));

    public static final DeferredHolder<Item, Item> SAND_CASTLE =
            registerBlockItem(SummeradditionModBlocks.SAND_CASTLE);

    public static final DeferredHolder<Item, Item> WILD_TOMATO_PLANT =
            registerBlockItem(SummeradditionModBlocks.WILD_TOMATO_PLANT);

    private static DeferredHolder<Item, Item> registerBlockItem(DeferredHolder<Block, Block> block) {
        return REGISTRY.register(block.getId().getPath(),
                () -> new BlockItem(block.get(), new Item.Properties()));
    }
}