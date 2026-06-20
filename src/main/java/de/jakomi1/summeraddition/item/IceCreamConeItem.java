package de.jakomi1.summeraddition.item;

import de.jakomi1.summeraddition.procedures.IceCreamConeRightClickProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

import javax.annotation.Nullable;
import java.util.List;

public class IceCreamConeItem extends Item{
    public enum IceCreamType {
        PINK(MobEffects.ABSORPTION), LIME(MobEffects.HERO_OF_THE_VILLAGE), MIXED(null), ORANGE(MobEffects.JUMP);

        public final MobEffect effect;
        IceCreamType(MobEffect effect) {
            this.effect = effect;
        }
    }

    private final IceCreamConeItem.IceCreamType type;
    public IceCreamConeItem(IceCreamConeItem.IceCreamType type) {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(4)
                .rarity(Rarity.UNCOMMON)
        );
        this.type = type;
    }

    /** Erzeugt einen ItemStack mit NBT-Farbe */
    public ItemStack createStack() {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putString("IceCreamType", type.name());
        return stack;
    }

    public void applyEffect(Player player) {
        applyEffect(player, 300);
    }

    public void applyEffect(Player player, int ticks) {
        if(type.effect != null) {
            player.addEffect(new MobEffectInstance(type.effect, ticks));
        } else {
            for (IceCreamType type: IceCreamType.values()) {
                if(type.effect == null) continue;
                player.addEffect(new MobEffectInstance(type.effect, ticks));
            }
        }
    }
    /** Liefert eine Kopie des Standard-ItemStacks mit NBT-Farbe zurück */
    public ItemStack getDefaultStack() {
        return createStack().copy();
    }

    /** Liest die Lolly-Farbe aus dem ItemStack */
    public static IceCreamType getType(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("IceCreamType")) {
            try {
                return IceCreamType.valueOf(tag.getString("IceCreamType"));
            } catch (IllegalArgumentException ignored) {}
        }
        return IceCreamType.PINK;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.summeraddition.ice_cream_cone.lore"));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack freshStack = this.createStack();

        IceCreamConeRightClickProcedure.execute(level, player, freshStack, type);
        stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }


}
