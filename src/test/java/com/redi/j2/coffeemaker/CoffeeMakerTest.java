package com.redi.j2.coffeemaker;

import com.redi.j2.exceptions.InvalidRecipeIngredientAmountException;
import com.redi.j2.exceptions.InvalidRecipePriceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeMakerTest {

    @Test
    void makeCoffeeMethodShouldWork() throws InvalidRecipePriceException, InvalidRecipeIngredientAmountException {

        // given - a CoffeeMaker machine
        CoffeeMaker coffeeMaker = new CoffeeMaker();

        // and - a recipe
        Recipe recipe = new Recipe("My Recipe", BigDecimal.ONE, 1, 1, 1, 1);
        coffeeMaker.addRecipe(recipe);

        // and - the inventory
        Inventory inventory = coffeeMaker.getInventory();

        // and - enough ingredients
        coffeeMaker.addIngredients(recipe.getAmountCoffee(), recipe.getAmountMilk(), recipe.getAmountChocolate(), recipe.getAmountSugar());
        int oldAmountCoffee = inventory.getCoffee();
        int oldAmountMilk = inventory.getMilk();
        int oldAmountChocolate = inventory.getChocolate();
        int oldAmountSugar = inventory.getSugar();

        // when - we try to make the recipe
        boolean result = coffeeMaker.makeCoffee(recipe.getName());

        // then - it should work
        assertTrue(result, "The coffee maker should make a recipe if there are enough ingredients");

        // and - the amount of ingredients is deducted from the inventory
        assertEquals(oldAmountCoffee - recipe.getAmountCoffee(), inventory.getCoffee(), "The amount of coffee must be deducted from the inventory");
        assertEquals(oldAmountMilk - recipe.getAmountMilk(), inventory.getMilk(), "The amount of milk must be deducted from the inventory");
        assertEquals(oldAmountChocolate - recipe.getAmountChocolate(), inventory.getChocolate(), "The amount of chocolate must be deducted from the inventory");
        assertEquals(oldAmountSugar - recipe.getAmountSugar(), inventory.getSugar(), "The amount of sugar must be deducted from the inventory");
    }

    @Test
    void makeCoffeeMethodShouldNotMakeRecipeWhenThereAreNoIngredients() throws InvalidRecipePriceException, InvalidRecipeIngredientAmountException {

        // given - a Coffee Maker machine
        CoffeeMaker coffeeMaker = new CoffeeMaker();

        // and - the inventory
        Inventory inventory = coffeeMaker.getInventory();

        // and - a recipe
        Recipe recipe = new Recipe("My Recipe", BigDecimal.ONE,
                inventory.getCoffee()+1, 1, 1, 1);
        coffeeMaker.addRecipe(recipe);

        // and - the current amount of ingredients
        int oldAmountCoffee = inventory.getCoffee();
        int oldAmountMilk = inventory.getMilk();
        int oldAmountChocolate = inventory.getChocolate();
        int oldAmountSugar = inventory.getSugar();

        // when - we try to make the recipe
        boolean result = coffeeMaker.makeCoffee(recipe.getName());

        // then - it should not
        assertFalse(result, "The coffee maker should not make a recipe if there aren't enough ingredients");

        // and - the amount of ingredients should not be changed
        assertEquals(oldAmountCoffee, inventory.getCoffee(), "The amount of coffee in the inventory should not change");
        assertEquals(oldAmountMilk, inventory.getMilk(), "The amount of milk  in the inventory should not change");
        assertEquals(oldAmountChocolate, inventory.getChocolate(), "The amount of chocolate in the inventory should not change");
        assertEquals(oldAmountSugar, inventory.getSugar(), "The amount of sugar in the inventory should not change");
    }
}