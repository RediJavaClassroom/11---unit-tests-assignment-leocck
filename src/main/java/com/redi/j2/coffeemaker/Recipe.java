package com.redi.j2.coffeemaker;

import com.redi.j2.exceptions.InvalidRecipeIngredientAmountException;
import com.redi.j2.exceptions.InvalidRecipePriceException;

import java.math.BigDecimal;

public class Recipe {

    /**
     * The name
     */
    private String name;

    /**
     * The price
     */
    private BigDecimal price;

    /**
     * The required amount of Coffee
     */
    private int amountCoffee;

    /**
     * The required amount of Milk
     */
    private int amountMilk;

    /**
     * The required amount of Chocolate
     */
    private int amountChocolate;

    /**
     * The required amount of Sugar
     */
    private int amountSugar;

    /**
     * Getter for Name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for Price
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Setter for Price
     * @param price The new price
     * @throws InvalidRecipePriceException in case the parameter is a negative number
     */
    public void setPrice(BigDecimal price) throws InvalidRecipePriceException {
        if (price.compareTo(BigDecimal.ZERO) >= 0) {
            this.price = price;
        } else {
            throw new InvalidRecipePriceException();
        }
    }

    /**
     * Getter for required amount of Coffee
     * @return the amount
     */
    public int getAmountCoffee() {
        return amountCoffee;
    }

    /**
     * Setter for required amount of Coffee
     * @param amountCoffee the new amount
     * @throws InvalidRecipeIngredientAmountException in case the parameter is a negative number
     */
    public void setAmountCoffee(int amountCoffee) throws InvalidRecipeIngredientAmountException {
        if (amountCoffee >= 0) {
            this.amountCoffee = amountCoffee;
        } else {
            throw new InvalidRecipeIngredientAmountException("coffee");
        }
    }

    /**
     * Getter for required amount of Milk
     * @return the amount
     */
    public int getAmountMilk() {
        return amountMilk;
    }

    /**
     * Setter for required amount of Milk
     * @param amountMilk the new amount
     * @throws InvalidRecipeIngredientAmountException in case the parameter is a negative number
     */
    public void setAmountMilk(int amountMilk) throws InvalidRecipeIngredientAmountException {
        if (amountMilk >= 0) {
            this.amountMilk = amountMilk;
        } else {
            throw new InvalidRecipeIngredientAmountException("milk");
        }
    }

    /**
     * Getter for required amount of Chocolate
     * @return the amount
     */
    public int getAmountChocolate() {
        return amountChocolate;
    }

    /**
     * Setter for required amount of Chocolate
     * @param amountChocolate the new amount
     * @throws InvalidRecipeIngredientAmountException in case the parameter is a negative number
     */
    public void setAmountChocolate(int amountChocolate) throws InvalidRecipeIngredientAmountException {
        if (amountChocolate >= 0) {
            this.amountChocolate = amountChocolate;
        } else {
            throw new InvalidRecipeIngredientAmountException("chocolate");
        }
    }

    /**
     * Getter for required amount of Sugar
     * @return the amount
     */
    public int getAmountSugar() {
        return amountSugar;
    }

    /**
     * Setter for required amount of Sugar
     * @param amountSugar the new amount
     * @throws InvalidRecipeIngredientAmountException in case the parameter is a negative number
     */
    public void setAmountSugar(int amountSugar) throws InvalidRecipeIngredientAmountException {
        if (amountSugar >= 0) {
            this.amountSugar = amountSugar;
        } else {
            throw new InvalidRecipeIngredientAmountException("sugar");
        }
    }

    /**
     * Parameterized constructor
     * @param name the name
     * @param price the price
     * @param amountCoffee the required amount of Coffee
     * @param amountMilk the required amount of Milk
     * @param amountChocolate the required amount of Chocolate
     * @param amountSugar the required amount of Sugar
     * @throws InvalidRecipeIngredientAmountException in case a required ingredient amount is a negative number
     * @throws InvalidRecipePriceException in case the price is a negative number
     */
    public Recipe(String name, BigDecimal price, int amountCoffee, int amountMilk, int amountChocolate, int amountSugar)
            throws InvalidRecipeIngredientAmountException, InvalidRecipePriceException {
        setName(name);
        setPrice(price);
        setAmountCoffee(amountCoffee);
        setAmountMilk(amountMilk);
        setAmountChocolate(amountChocolate);
        setAmountSugar(amountSugar);
    }

    /**
     * Creates a string representation of the Recipe
     * @return a string representation
     */
    @Override
    public String toString() {
        return "Recipe{" + "name='" + name + '\'' +
                ", price=" + price +
                ", amountCoffee=" + amountCoffee +
                ", amountMilk=" + amountMilk +
                ", amountChocolate=" + amountChocolate +
                ", amountSugar=" + amountSugar +
                '}';
    }

    /**
     * Compares this Recipe with another one
     * @param o Another object (potentially a Recipe)
     * @return If the object is equals to this Recipe
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        return getName().equals(recipe.getName());
    }

    /**
     * Calculates a hash code based on all the properties
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getAmountCoffee();
        result = 31 * result + getAmountMilk();
        result = 31 * result + getAmountChocolate();
        result = 31 * result + getAmountSugar();
        return result;
    }
}
