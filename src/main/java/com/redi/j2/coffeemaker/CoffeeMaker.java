package com.redi.j2.coffeemaker;

import com.redi.j2.exceptions.InvalidRecipeIngredientAmountException;
import com.redi.j2.exceptions.InvalidRecipePriceException;
import com.redi.j2.utils.ConsoleUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * The core of the application, this class represents a coffee maker machine
 */
public class CoffeeMaker {

    /**
     * The place where the ingredients are stored
     */
    private final Inventory inventory;

    /**
     * The catalog of Recipes that can be prepared in this machine
     */
    private final RecipeBook catalog;

    /**
     * Getter for the Inventory
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * The parameterized constructor.
     * The machine always starts with some amount of ingredients and one default Recipe
     */
    public CoffeeMaker() {
        inventory = new Inventory(10, 10, 10, 10);
        catalog = new RecipeBook();
        try {
            catalog.addRecipe(
                new Recipe("Cappuccino", BigDecimal.valueOf(2.7), 2, 3, 0, 1)
            );
        } catch (InvalidRecipeIngredientAmountException | InvalidRecipePriceException e) {
            System.out.println(ConsoleUtils.warningMessage("It was not possible to initialize the Coffee Maker machine, please contact your vendor.\nInternal Error: "+e.getMessage()));
            System.exit(1);
        }
    }

    /**
     * Adds a Recipe to the machine, if there is space in the catalog
     * @param r the new recipe
     * @return true if the Recipe was added, false otherwise
     */
    public boolean addRecipe(Recipe r) {
        return catalog.addRecipe(r);
    }

    /**
     * Removes a Recipe from the catalog
     * @param name the name of the recipe
     * @return true if the recipe was found and removed, false otherwise
     */
    public boolean removeRecipe(String name) {
        return catalog.removeRecipe(name);
    }

    /**
     * Updates a Recipe from the catalog.
     * It will find the corresponding Recipe by the name property.
     * @param r the recipe with all information to update
     * @return true if the recipe was found and updated, false otherwise
     */
    public boolean updateRecipe(Recipe r) {
        return catalog.updateRecipe(r);
    }

    /**
     * Adds ingredients to the inventory
     * @param coffee The amount of Coffee to add
     * @param milk The amount of Milk to add
     * @param chocolate The amount of Chocolate to add
     * @param sugar The amount of Sugar to add
     */
    public void addIngredients(int coffee, int milk, int chocolate, int sugar){
        inventory.addIngredient(Ingredient.COFFEE, coffee);
        inventory.addIngredient(Ingredient.MILK, milk);
        inventory.addIngredient(Ingredient.CHOCOLATE, chocolate);
        inventory.addIngredient(Ingredient.SUGAR, sugar);
    }

    /**
     * Getter for the List of all Recipes
     * @return all the Recipes from this machine
     */
    public List<Recipe> getAllRecipes() {
        return catalog.getRecipes();
    }

    /**
     * Makes a Recipe, if there are enough ingredients,
     * and deducts the value from the inventory
     * @param recipeName the recipe name
     * @return true if the coffee was made, false otherwise
     */
    public boolean makeCoffee(String recipeName) {

        Recipe recipe = catalog.getRecipe(recipeName);
        if (recipe == null) return false;

        if (!hasEnoughIngredientsToMakeRecipe(recipeName)) return false;

        inventory.removeAmount(Ingredient.COFFEE, recipe.getAmountCoffee());
        inventory.removeAmount(Ingredient.MILK, recipe.getAmountMilk());
        inventory.removeAmount(Ingredient.CHOCOLATE, recipe.getAmountChocolate());
        inventory.removeAmount(Ingredient.SUGAR, recipe.getAmountSugar());

        return true;
    }

    /**
     * Checks if there are enough ingredients to make a certain recipe
     * @param recipeName the name of the recipe
     * @return true if there are enough ingredients, false otherwise
     */
    public boolean hasEnoughIngredientsToMakeRecipe(String recipeName) {
        Recipe recipe = catalog.getRecipe(recipeName);
        if (recipe == null) return false;
        return inventory.getCoffee() >= recipe.getAmountCoffee() &&
                inventory.getMilk() >= recipe.getAmountMilk() &&
                inventory.getChocolate() >= recipe.getAmountChocolate() &&
                inventory.getSugar() >= recipe.getAmountSugar();
    }
}
