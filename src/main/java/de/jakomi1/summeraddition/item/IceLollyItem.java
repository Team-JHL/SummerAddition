package de.jakomi1.summeraddition.item;

import de.jakomi1.summeraddition.procedures.IceLollyRightClickProcedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;

public class IceLollyItem extends Item {

    private static final Logger log = LoggerFactory.getLogger(IceLollyItem.class);

    public enum LollyType {
        RED, GREEN, BLUE, YELLOW, MIXED, BASIC
    }

    private final LollyType type;

    public IceLollyItem(LollyType type) {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(8)
                .rarity(type == LollyType.BASIC?Rarity.COMMON:Rarity.UNCOMMON)
        );
        this.type = type;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(type != LollyType.BASIC) {
            tooltip.add(Component.translatable("item.summeraddition.ice_lolly.lore"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }



    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(type != LollyType.BASIC) {
            IceLollyRightClickProcedure.execute(level, player, stack, type);  // echten Stack übergeben
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }


}
