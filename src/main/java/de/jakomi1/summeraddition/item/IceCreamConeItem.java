package de.jakomi1.summeraddition.item;

import de.jakomi1.summeraddition.init.SummeradditionModDataComponents;
import de.jakomi1.summeraddition.procedures.IceCreamConeRightClickProcedure;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IceCreamConeItem extends Item {

    public enum IceCreamType {
        PINK(MobEffects.ABSORPTION.getDelegate()),
        LIME(MobEffects.HERO_OF_THE_VILLAGE.getDelegate()),
        MIXED(null),
        ORANGE(MobEffects.JUMP.getDelegate());

        public final Holder<MobEffect> effect;

        IceCreamType(Holder<MobEffect> effect) {
            this.effect = effect;
        }
    }


    private final IceCreamType type;


    public IceCreamConeItem(IceCreamType type) {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(4)
                .rarity(Rarity.UNCOMMON)
        );

        this.type = type;
    }


    public ItemStack createStack() {
        ItemStack stack = new ItemStack(this);

        stack.set(
                SummeradditionModDataComponents.ICE_CREAM_TYPE.get(),
                type.name()
        );

        return stack;
    }


    public void applyEffect(Player player) {
        applyEffect(player, 300);
    }


    public void applyEffect(Player player, int ticks) {

        if (type.effect != null) {

            player.addEffect(
                    new MobEffectInstance(type.effect, ticks)
            );

        } else {

            for (IceCreamType iceCreamType : IceCreamType.values()) {

                if (iceCreamType.effect == null)
                    continue;

                player.addEffect(
                        new MobEffectInstance(
                                iceCreamType.effect,
                                ticks
                        )
                );
            }
        }
    }


    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return createStack();
    }


    public static IceCreamType getType(ItemStack stack) {

        String value = stack.get(
                SummeradditionModDataComponents.ICE_CREAM_TYPE.get()
        );


        if (value != null) {

            try {

                return IceCreamType.valueOf(value);

            } catch (IllegalArgumentException ignored) {

            }
        }


        return IceCreamType.PINK;
    }


    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            @NotNull TooltipContext level,
            List<Component> tooltip,
            @NotNull TooltipFlag flag
    ) {

        tooltip.add(
                Component.translatable(
                        "item.summeraddition.ice_cream_cone.lore"
                )
        );

        super.appendHoverText(stack, level, tooltip, flag);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level level,
            Player player,
            @NotNull InteractionHand hand
    ) {

        ItemStack stack = player.getItemInHand(hand);


        ItemStack freshStack = createStack();


        IceCreamConeRightClickProcedure.execute(
                level,
                player,
                freshStack,
                type,
                hand
        );


        if (!player.getAbilities().instabuild) {

            stack.hurtAndBreak(
                    1,
                    (ServerLevel) level,
                    player,
                    (item) -> {}
            );

        }

        return InteractionResultHolder.sidedSuccess(
                stack,
                level.isClientSide()
        );
    }
}