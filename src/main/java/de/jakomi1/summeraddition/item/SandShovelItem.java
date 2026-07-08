package de.jakomi1.summeraddition.item;

import de.jakomi1.summeraddition.procedures.SandShovelBlockDestroyedWithToolProcedure;
import de.jakomi1.summeraddition.procedures.SandShovelRightclickedOnBlockProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SandShovelItem extends ShovelItem {


    public SandShovelItem() {

        super(
                new Tier() {

                    @Override
                    public int getUses() {
                        return 512;
                    }

                    @Override
                    public float getSpeed() {
                        return 6.0f;
                    }

                    @Override
                    public float getAttackDamageBonus() {
                        return 1.0f;
                    }


                    @Override
                    public @NotNull net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
                        return BlockTags.INCORRECT_FOR_WOODEN_TOOL;
                    }


                    @Override
                    public int getEnchantmentValue() {
                        return 15;
                    }

                    @Override
                    public @NotNull Ingredient getRepairIngredient() {
                        return Ingredient.of(Items.SAND);
                    }

                },
                new Item.Properties()
        );
    }



    @Override
    public boolean mineBlock(
            @NotNull ItemStack stack,
            @NotNull Level world,
            @NotNull BlockState state,
            @NotNull BlockPos pos,
            @NotNull LivingEntity entity
    ) {

        boolean result = super.mineBlock(
                stack,
                world,
                state,
                pos,
                entity
        );


        SandShovelBlockDestroyedWithToolProcedure.execute(
                world,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                entity,
                stack
        );


        return result;
    }



    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            @NotNull TooltipContext level,
            @NotNull List<Component> tooltip,
            @NotNull TooltipFlag flag
    ) {

        super.appendHoverText(
                stack,
                level,
                tooltip,
                flag
        );
    }



    @Override
    public @NotNull InteractionResult useOn(
            UseOnContext context
    ) {


        if (context.getPlayer() != null) {

            SandShovelRightclickedOnBlockProcedure.execute(
                    context.getLevel(),
                    context.getClickedPos().getX(),
                    context.getClickedPos().getY(),
                    context.getClickedPos().getZ(),
                    context.getPlayer(),
                    context.getPlayer().getItemInHand(context.getHand())
            );

        }


        return InteractionResult.SUCCESS;
    }
}