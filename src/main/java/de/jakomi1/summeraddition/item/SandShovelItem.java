package de.jakomi1.summeraddition.item;

import de.jakomi1.summeraddition.procedures.SandShovelBlockDestroyedWithToolProcedure;
import de.jakomi1.summeraddition.procedures.SandShovelRightclickedOnBlockProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SandShovelItem extends ShovelItem {

    public SandShovelItem() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 512;
            }

            @Override
            public float getSpeed() {
                return 1.0f;
            }

            @Override
            public float getAttackDamageBonus() {
                return -1.0f;
            }

            @Override
            public int getLevel() {
                return 4;
            }

            @Override
            public int getEnchantmentValue() {
                return 2;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(Items.SAND);
            }
        }, 1.0f, -3.0f, new Item.Properties());
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        boolean ret = super.mineBlock(stack, world, state, pos, entity);
        SandShovelBlockDestroyedWithToolProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity, stack);
        return ret;
    }


    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        //tooltip.add(Component.literal("This shovel only can break sand or build sand castles!"));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        SandShovelRightclickedOnBlockProcedure.execute(
                context.getLevel(),
                context.getClickedPos().getX(),
                context.getClickedPos().getY(),
                context.getClickedPos().getZ(),
                context.getPlayer(),
                Objects.requireNonNull(context.getPlayer()).getItemInHand(context.getHand()) // hier richtig ItemStack übergeben
        );
        return InteractionResult.SUCCESS;
    }

}
