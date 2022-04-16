package com.redi.j2.exceptions;

/**
 * Thrown when trying to create a Recipe with an invalid ingredient amount (e.g. negative number)
 */
public class InvalidRecipeIngredientAmountException extends Exception {

    public InvalidRecipeIngredientAmountException(String ingredientName) {
        super("Units of "+ingredientName+" must be a positive integer");
    }
}
