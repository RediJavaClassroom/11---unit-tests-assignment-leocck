package com.redi.j2.coffeemaker;

import com.redi.j2.exceptions.InvalidRecipeIngredientAmountException;
import com.redi.j2.exceptions.InvalidRecipePriceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void constructorShouldInitializeProperties() throws InvalidRecipePriceException, InvalidRecipeIngredientAmountException {

        // given - some recipe configuration
        String name = "Cappuccino";
        BigDecimal price = BigDecimal.valueOf(2.75);
        int coffee = 3;
        int milk = 2;
        int chocolate = 1;
        int sugar = 1;

        // when - we create a Recipe with those values
        Recipe recipe = new Recipe(name, price, coffee, milk, chocolate, sugar);

        // then - the getters return the same values
        assertEquals(name, recipe.getName(), "Name is not matching");
        assertEquals(price, recipe.getPrice(), "Price is not matching");
        assertEquals(coffee, recipe.getAmountCoffee(), "Coffee is not matching");
        assertEquals(milk, recipe.getAmountMilk(), "Milk is not matching");
        assertEquals(chocolate, recipe.getAmountChocolate(), "Chocolate is not matching");
        assertEquals(sugar, recipe.getAmountSugar(), "Sugar is not matching");
    }

    @ParameterizedTest(name = "Invalid {0}")
    @MethodSource("provideExceptionParams")
    void constructorShouldThrowExceptionForInvalidParam(String paramName, String name, BigDecimal price, int coffee, int milk, int chocolate, int sugar, Class<Throwable> exception) {

        // given - some recipe configuration with invalid amount

        // when - we instantiate a Recipe with these values

        // then - expect an exception to be thrown
        assertThrows(exception, () ->
                new Recipe(name, price, coffee, milk, chocolate, sugar),
                String.format("Constructor should throw an exception for invalid %s", paramName)
        );
    }

    @Test
    void equalsMethodShouldCompareRecipesByName() throws InvalidRecipePriceException, InvalidRecipeIngredientAmountException {

        // given - a Recipe
        Recipe r1 = new Recipe("Cappuccino", BigDecimal.ONE, 5, 4, 3, 2);

        // and - another Recipe with the same name
        Recipe r2 = new Recipe(r1.getName(), BigDecimal.valueOf(2.0), 1, 1, 1, 1);

        // when - we compare these objects
        boolean equals = r1.equals(r2);

        // then - they should be considered equal
        assertTrue(equals, "Recipes must be considered equal if they have the same name");
    }

    private static Stream<Arguments> provideExceptionParams() {
        return Stream.of(
                Arguments.of("Price", "Cappuccino", BigDecimal.valueOf(-1), 5, 4, 3, 2, InvalidRecipePriceException.class),
                Arguments.of("Coffee Amount", "Cappuccino", BigDecimal.valueOf(1), -5, 4, 3, 2, InvalidRecipeIngredientAmountException.class),
                Arguments.of("Milk Amount", "Cappuccino", BigDecimal.valueOf(1), 5, -4, 3, 2, InvalidRecipeIngredientAmountException.class),
                Arguments.of("Chocolate Amount", "Cappuccino", BigDecimal.valueOf(1), 5, 4, -3, 2, InvalidRecipeIngredientAmountException.class),
                Arguments.of("Sugar Amount", "Cappuccino", BigDecimal.valueOf(1), 5, 4, 3, -2, InvalidRecipeIngredientAmountException.class)
        );
    }
}