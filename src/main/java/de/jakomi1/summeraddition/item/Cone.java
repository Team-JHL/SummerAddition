package de.jakomi1.summeraddition.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Cone extends Item{
    public Cone() {
        super(new Item.Properties()
                .stacksTo(64)
                .rarity(Rarity.COMMON));
    }
}
