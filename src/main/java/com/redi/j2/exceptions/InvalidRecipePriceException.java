package com.redi.j2.exceptions;

/**
 * Thrown when trying to create a Recipe with an invalid price (e.g. negative number)
 */
public class InvalidRecipePriceException extends Exception{

    public InvalidRecipePriceException() {
        super("Price must be a positive integer");
    }
}
