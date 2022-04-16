package com.redi.j2.coffeemaker;

import java.util.ArrayList;
import java.util.List;

/**
 * A catalog of Recipes
 */
public class RecipeBook {

    /**
     * Defines the maximum amount of Recipes this catalog can have
     */
    public static final int MAX_RECIPES = 4;

    /**
     * The list of Recipes
     */
    private final List<Recipe> recipes;

    /**
     * Getter for the list of Recipes
     * @return the list of Recipes
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Default constructor
     */
    public RecipeBook() {
        recipes = new ArrayList<>();
    }

    /**
     * Adds a new recipe to the catalog.
     * It does not allow duplicates, and controls the maximum size of the list.
     * @param newRecipe A new recipe
     * @return true if added the Recipe, false otherwise
     */
    public boolean addRecipe(Recipe newRecipe) {
        if (recipes.contains(newRecipe)) {
            return false;
        }
        if (recipes.size() == MAX_RECIPES) {
            return false;
        }
        recipes.add(newRecipe);
        return true;
    }

    /**
     * Removes a recipe by a given name
     * @param name the name of the Recipe to remove
     * @return true if the Recipe was found and removed, false otherwise
     */
    public boolean removeRecipe(String name) {
        for (Recipe r : recipes) {
            if (r.getName().equals(name)) {
                return recipes.remove(r);
            }
        }
        return false;
    }

    /**
     * Updates a given Recipe. It finds the corresponding recipe by its name.
     * @param recipe the recipe object with all the information
     * @return true if the Recipe was found and updated, false otherwise
     */
    public boolean updateRecipe(Recipe recipe) {
        for (int i=0; i<recipes.size(); i++) {
            if (recipes.get(i).getName().equals(recipe.getName())) {
                recipes.set(i, recipe);
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a Recipe in the list
     * @param name the name of the Recipe
     * @return the Recipe, if found, or null otherwise
     */
    public Recipe getRecipe(String name) {
        for (Recipe r : recipes) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }
}
