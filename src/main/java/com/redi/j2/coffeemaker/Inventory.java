package com.redi.j2.coffeemaker;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains the amounts of ingredients used by the Recipes
 */
public class Inventory {

    /**
     * A list of ingredients and corresponding amounts in stock
     */
    private final Map<Ingredient, Integer> stock;

    /**
     * The parameterized constructor
     * @param coffee the initial amount of Coffee
     * @param milk the initial amount of Milk
     * @param chocolate the initial amount of Chocolate
     * @param sugar the initial amount of Sugar
     */
    public Inventory(int coffee, int milk, int chocolate, int sugar) {
        stock = new HashMap<>();
        stock.put(Ingredient.COFFEE, coffee);
        stock.put(Ingredient.MILK, milk);
        stock.put(Ingredient.SUGAR, sugar);
        stock.put(Ingredient.CHOCOLATE, chocolate);
    }

    /**
     * Getter for Coffee
     * @return the current amount in stock
     */
    public int getCoffee() {
        return stock.get(Ingredient.COFFEE);
    }

    /**
     * Getter for Milk
     * @return the current amount in stock
     */
    public int getMilk() {
        return stock.get(Ingredient.MILK);
    }

    /**
     * Getter for Chocolate
     * @return the current amount in stock
     */
    public int getChocolate() {
        return stock.get(Ingredient.CHOCOLATE);
    }

    /**
     * Getter for Sugar
     * @return the current amount in stock
     */
    public int getSugar() {
        return stock.get(Ingredient.SUGAR);
    }

    /**
     * Method that adds an amount of certain ingredient to the stock
     * @param ingredient the ingredient to add
     * @param amount the amount to add
     */
    public void addIngredient(Ingredient ingredient, int amount) {
        int currentAmount = stock.get(ingredient);
        stock.put(ingredient, currentAmount + amount);
    }

    /**
     * Removes an amount of certain ingredient from the stock,
     * if and only if the stock has enough amount
     * @param ingredient the ingredient to be removed
     * @param amount the amount to remove
     * @return true if the amount was removed, false otherwise
     */
    public boolean removeAmount(Ingredient ingredient, int amount) {
        int currentAmount = stock.get(ingredient);
        if (currentAmount < amount) {
            return false;
        }
        stock.put(ingredient, currentAmount - amount);
        return true;
    }

    /**
     * Generates a String representation of the class
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Inventory{" + "stock=" + stock + '}';
    }
}
