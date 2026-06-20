package de.jakomi1.summeraddition.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TomatoStewItem extends Item {
    public TomatoStewItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.COMMON)
                .food(new FoodProperties.Builder()
                        .nutrition(4)
                        .saturationMod(0.5f)
                        .build()
                ));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        ItemStack ret = new ItemStack(Items.BOWL);
        super.finishUsingItem(stack, world, entity);
        if (stack.isEmpty()) {
            return ret;
        } else {
            if (entity instanceof Player player) {
                if (!player.getAbilities().instabuild && !player.getInventory().add(ret)) {
                    player.drop(ret, false);
                }
            }
            return stack;
        }
    }
}
