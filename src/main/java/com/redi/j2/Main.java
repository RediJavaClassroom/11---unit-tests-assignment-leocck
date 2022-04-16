package com.redi.j2;

import com.redi.j2.coffeemaker.CoffeeMaker;
import com.redi.j2.coffeemaker.Inventory;
import com.redi.j2.coffeemaker.Recipe;
import com.redi.j2.coffeemaker.RecipeBook;
import com.redi.j2.exceptions.InvalidRecipeIngredientAmountException;
import com.redi.j2.exceptions.InvalidRecipePriceException;
import com.redi.j2.utils.ColorType;
import com.redi.j2.utils.ConsoleUtils;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    /**
     * Main function, starting point of the application.
     * @param args not used
     */
    public static void main(String[] args) {

        // creating the application and coffee machine
        Main m = new Main(new CoffeeMaker());

        // printing the header
        ConsoleUtils.showHeader();

        // set the animation time for a lower value (>0) if you don't want to wait
        int animationTime = 5000;

        ConsoleUtils.playWarmingUpAnimation(animationTime);

        // start the application
        m.mainMenu();
    }

    /**
     * The coffee maker "machine"
     */
    private final CoffeeMaker coffeeMaker;

    /**
     * Constructor
     * @param coffeeMaker the coffee maker "machine"
     */
    public Main(CoffeeMaker coffeeMaker) {
        this.coffeeMaker = coffeeMaker;
    }

    /**
     * This method is the starting point of the application.
     * It shows the main menu, reads the input from the user,
     * calls the corresponding functions for each option,
     * and keep running in an infinite loop, until the user 
     * selects the "Exit" option
     */
    public void mainMenu() {

        while (true) {

            // print menu

            System.out.println();

            System.out.println(ConsoleUtils.menuFormatter(
                    "MAIN MENU",
                    new String[] {
                            "Make Coffee",
                            "Show Inventory",
                            "Refill Ingredients",
                            "Add a Recipe",
                            "Edit a Recipe",
                            "Delete a Recipe",
                            "Exit"
                    },
                    new int[]{ 1, 2, 3, 4, 5, 6, 0},
                    new boolean[] {
                            canMakeCoffee(),
                            true,
                            true,
                            canAddRecipes(),
                            canEditRecipes(),
                            canDeleteRecipes(),
                            true
                    },
                    ColorType.BRIGHT_PURPLE,
                    ColorType.BLUE,
                    ColorType.PURPLE
            ));

            System.out.println();

            // get user command

            try {
                int userInput = Integer.parseInt(ConsoleUtils.readInput("Choose an Option: "));

                if (userInput >= 0 && userInput <=6) {
                    if (userInput == 1) makeCoffee();
                    if (userInput == 2) showInventory();
                    if (userInput == 3) refillIngredients();
                    if (userInput == 4) addRecipe();
                    if (userInput == 5) editRecipe();
                    if (userInput == 6) deleteRecipe();
                    if (userInput == 0) break;
                } else {
                    System.out.println(ConsoleUtils.warningMessage("Please enter a number from 0 - 6"));
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleUtils.warningMessage("Please enter a number from 0 - 6"));
            }
        }
    }

    /**
     * Logic to enable the "Delete a Recipe" menu or not
     * @return if the menu should be enabled or not
     */
    private boolean canDeleteRecipes() {
        return coffeeMaker.getAllRecipes().size() > 0;
    }

    /**
     * Logic to enable the "Edit a Recipe" menu or not
     * @return if the menu should be enabled or not
     */
    private boolean canEditRecipes() {
        return coffeeMaker.getAllRecipes().size() > 0;
    }

    /**
     * Logic to enable the "Add a Recipe" menu or not
     * @return if the menu should be enabled or not
     */
    private boolean canAddRecipes() {
        return coffeeMaker.getAllRecipes().size() < RecipeBook.MAX_RECIPES;
    }

    /**
     * Logic to enable the "Make Coffee" menu or not
     * @return if the menu should be enabled or not
     */
    private boolean canMakeCoffee() {
        List<Recipe> recipes = coffeeMaker.getAllRecipes();
        for (Recipe recipe : recipes) {
            if (coffeeMaker.hasEnoughIngredientsToMakeRecipe(recipe.getName())) return true;
        }
        return false;
    }

    /**
     * This method shows available Recipes, controls the input
     * from the user, triggers the preparation of the Recipe,
     * and deducts the price from the provided money
     */
    private void makeCoffee() {

        List<Recipe> recipes = coffeeMaker.getAllRecipes();
        String[] names = new String[recipes.size()+1];
        int[] numbers = new int[recipes.size()+1];
        boolean[] enabled = new boolean[recipes.size()+1];
        for(int i = 0; i < recipes.size(); i++) {
            names[i] = recipes.get(i).getName() + " (" + ConsoleUtils.formatCurrency(recipes.get(i).getPrice()) + ")";
            numbers[i] = i+1;
            enabled[i] = coffeeMaker.hasEnoughIngredientsToMakeRecipe(recipes.get(i).getName());
        }
        names[recipes.size()] = "Back";
        numbers[recipes.size()] = 0;
        enabled[recipes.size()] = true;

        System.out.println();
        System.out.println(ConsoleUtils.menuFormatter(
                "COFFEE",
                names,
                numbers,
                enabled,
                ColorType.BRIGHT_GREEN,
                ColorType.BLUE,
                ColorType.GREEN
        ));

        System.out.println();

        int recipeToPurchase;
        while(true) {
            try {
                recipeToPurchase = Integer.parseInt(ConsoleUtils.readInput("Please select the number of the recipe to purchase: "));
                if (recipeToPurchase < 0 || recipeToPurchase >= numbers.length) {
                    System.out.println(ConsoleUtils.warningMessage("Please enter a valid option\n"));
                }
                else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
            }
        }

        if (recipeToPurchase == 0) {
            return;
        }

        Recipe coffee = recipes.get(recipeToPurchase-1);
        System.out.print("Selected product: ");
        System.out.println(ConsoleUtils.colorOutput(coffee.getName(), ColorType.BRIGHT_GREEN));

        BigDecimal amountPaid;
        while (true) {
            try {
                amountPaid = new BigDecimal(ConsoleUtils.readInput("Please enter the amount of money you will use to pay: $"));
                if (amountPaid.subtract(coffee.getPrice()).doubleValue() < 0) {
                    System.out.println(ConsoleUtils.warningMessage("Sorry, this is not enough money to purchase a " + coffee.getName()));
                    ConsoleUtils.readInput("Press ENTER to continue");
                    return;
                }
                else break;
            } catch (Exception e) {
                System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
            }
        }

        boolean prepareCoffee = coffeeMaker.makeCoffee(coffee.getName());
        if (prepareCoffee) {
            System.out.println("Thank you! Here's your "+coffee.getName()+" and your change (" + ConsoleUtils.formatCurrency(amountPaid.subtract(coffee.getPrice())) + ")");
        }
        else {
            System.out.println("Sorry, it was not possible to make your "+coffee.getName()+", here's your money back (" + ConsoleUtils.formatCurrency(amountPaid) + ")");
        }

        ConsoleUtils.readInput("Press ENTER to continue");
    }

    /**
     * This method only shows the current amounts
     * of ingredients in the inventory
     */
    private void showInventory() {

        Inventory inventory = coffeeMaker.getInventory();

        System.out.println();

        System.out.println(ConsoleUtils.menuFormatter(
                "INVENTORY",
                new String[] {
                        "Coffee: " + inventory.getCoffee(),
                        "Milk: " + inventory.getMilk(),
                        "Chocolate: " + inventory.getChocolate(),
                        "Sugar: " + inventory.getSugar()
                },
                new int[]{1, 2, 3, 4},
                ColorType.CYAN,
                ColorType.BLUE,
                ColorType.BLUE
        ));

        System.out.println();

        ConsoleUtils.readInput("Press ENTER to continue");
    }

    /**
     * This method asks the amount to add to 
     * the current ingredients from the inventory
     */
    private void refillIngredients() {
        int amountCoffee = 0, amountMilk = 0, amountChocolate = 0, amountSugar = 0;

        try {
            amountCoffee = Integer.parseInt(ConsoleUtils.readInput("Please enter how many new Coffee units: "));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            amountMilk = Integer.parseInt(ConsoleUtils.readInput("Please enter how many new Milk units: "));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            amountChocolate = Integer.parseInt(ConsoleUtils.readInput("Please enter how many new Chocolate units: "));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            amountSugar = Integer.parseInt(ConsoleUtils.readInput("Please enter how many new Sugar units: "));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        coffeeMaker.addIngredients(amountCoffee, amountMilk, amountChocolate, amountSugar);
        showInventory();
    }

    /**
     * Adds a new Recipe to the coffee maker
     */
    private void addRecipe() {
        String name;
        BigDecimal price = null;
        int amountCoffee = 0, amountMilk = 0, amountChocolate = 0, amountSugar = 0;

        name = ConsoleUtils.readInput("Please enter the name of the new Recipe: ");

        try {
            price = new BigDecimal(ConsoleUtils.readInput("Please enter the price: $"));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            amountCoffee = Integer.parseInt(ConsoleUtils.readInput("Please enter how many new Coffee units: "));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            amountMilk = Integer.parseInt(ConsoleUtils.readInput("Please enter how many new Milk units: "));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            amountChocolate = Integer.parseInt(ConsoleUtils.readInput("Please enter how many new Chocolate units: "));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            amountSugar = Integer.parseInt(ConsoleUtils.readInput("Please enter how many new Sugar units: "));
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            boolean result = coffeeMaker.addRecipe(
                    new Recipe(name, price, amountCoffee, amountMilk, amountChocolate, amountSugar)
            );
            if (result) {
                System.out.println("Recipe '"+name+"' added to the Menu");
            }
            else {
                System.out.println(ConsoleUtils.warningMessage("It was not possible to create the Recipe"));
            }
        } catch (InvalidRecipeIngredientAmountException | InvalidRecipePriceException e) {
            System.out.println(ConsoleUtils.warningMessage("It was not possible to create the Recipe: "+e.getMessage()));
        }

        ConsoleUtils.readInput("Press ENTER to continue");
    }

    /**
     * Edits the properties of an existing Recipe
     */
    private void editRecipe() {
        List<Recipe> recipes = coffeeMaker.getAllRecipes();
        String[] names = new String[recipes.size()+1];
        int[] numbers = new int[recipes.size()+1];
        for(int i = 0; i < recipes.size(); i++) {
            names[i] = recipes.get(i).getName() + " (" + ConsoleUtils.formatCurrency(recipes.get(i).getPrice()) + ")";
            numbers[i] = i+1;
        }
        names[recipes.size()] = "Back";
        numbers[recipes.size()] = 0;

        System.out.println();
        System.out.println(ConsoleUtils.menuFormatter(
                "RECIPES",
                names,
                numbers,
                ColorType.BRIGHT_GREEN,
                ColorType.BLUE,
                ColorType.GREEN
        ));

        System.out.println();

        int recipeToEdit;
        while(true) {
            try {
                recipeToEdit = Integer.parseInt(ConsoleUtils.readInput("Please select the number of the recipe to edit: "));
                if (recipeToEdit < 0 || recipeToEdit >= numbers.length) {
                    System.out.println(ConsoleUtils.warningMessage("Please enter a valid option\n"));
                }
                else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
            }
        }

        if (recipeToEdit == 0) {
            return;
        }

        Recipe recipe = recipes.get(recipeToEdit-1);
        System.out.print("Selected recipe: ");
        System.out.println(ConsoleUtils.colorOutput(recipe.getName(), ColorType.BRIGHT_GREEN));

        BigDecimal price = null;
        int amountCoffee = 0, amountMilk = 0, amountChocolate = 0, amountSugar = 0;

        try {
            String strPrice = ConsoleUtils.readInput("Please enter the new price ("+ConsoleUtils.formatCurrency(recipe.getPrice())+"): $");
            price = (!strPrice.equals(""))? new BigDecimal(strPrice) : recipe.getPrice();
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            String strCoffee = ConsoleUtils.readInput("Please enter the new Coffee units ("+recipe.getAmountCoffee()+"): ");
            amountCoffee = (!strCoffee.equals(""))? Integer.parseInt(strCoffee) : recipe.getAmountCoffee();
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            String strMilk = ConsoleUtils.readInput("Please enter the new Milk units ("+recipe.getAmountMilk()+"): ");
            amountMilk = (!strMilk.equals(""))? Integer.parseInt(strMilk) : recipe.getAmountMilk();
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            String strChocolate = ConsoleUtils.readInput("Please enter the new Chocolate units ("+recipe.getAmountChocolate()+"): ");
            amountChocolate = (!strChocolate.equals(""))? Integer.parseInt(strChocolate) : recipe.getAmountChocolate();
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            String strSugar = ConsoleUtils.readInput("Please enter the new Sugar units ("+recipe.getAmountSugar()+"): ");
            amountSugar = (!strSugar.equals(""))? Integer.parseInt(strSugar) : recipe.getAmountSugar();
        } catch (Exception e) {
            System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
        }

        try {
            boolean result = coffeeMaker.updateRecipe(
                    new Recipe(recipe.getName(), price, amountCoffee, amountMilk, amountChocolate, amountSugar)
            );
            if (result) {
                System.out.println("Recipe '"+recipe.getName()+"' was updated");
            }
            else {
                System.out.println(ConsoleUtils.warningMessage("It was not possible to update the Recipe"));
            }
        } catch (InvalidRecipeIngredientAmountException | InvalidRecipePriceException e) {
            System.out.println(ConsoleUtils.warningMessage("It was not possible to update the Recipe: "+e.getMessage()));
        }

        ConsoleUtils.readInput("Press ENTER to continue");
    }

    /**
     * Allows the user to delete an existing Recipe
     */
    private void deleteRecipe() {
        List<Recipe> recipes = coffeeMaker.getAllRecipes();
        String[] names = new String[recipes.size()+1];
        int[] numbers = new int[recipes.size()+1];
        for(int i = 0; i < recipes.size(); i++) {
            names[i] = recipes.get(i).getName() + " (" + ConsoleUtils.formatCurrency(recipes.get(i).getPrice()) + ")";
            numbers[i] = i+1;
        }
        names[recipes.size()] = "Back";
        numbers[recipes.size()] = 0;

        System.out.println();
        System.out.println(ConsoleUtils.menuFormatter(
                "RECIPES",
                names,
                numbers,
                ColorType.BRIGHT_GREEN,
                ColorType.BLUE,
                ColorType.GREEN
        ));

        System.out.println();

        int recipeToEdit;
        while(true) {
            try {
                recipeToEdit = Integer.parseInt(ConsoleUtils.readInput("Please select the number of the recipe to remove: "));
                if (recipeToEdit < 0 || recipeToEdit >= numbers.length) {
                    System.out.println(ConsoleUtils.warningMessage("Please enter a valid option\n"));
                }
                else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleUtils.warningMessage("Please enter a valid number\n"));
            }
        }

        if (recipeToEdit == 0) {
            return;
        }

        Recipe recipe = recipes.get(recipeToEdit-1);
        System.out.print("Selected recipe: ");
        System.out.println(ConsoleUtils.colorOutput(recipe.getName(), ColorType.BRIGHT_RED));
        String confirmation = ConsoleUtils.readInput(ConsoleUtils.colorOutput("Are you really sure about that? (type YES): ", ColorType.YELLOW));

        if (confirmation.equalsIgnoreCase("YES")) {
            boolean result = coffeeMaker.removeRecipe(recipe.getName());
            if (result) {
                System.out.println("Recipe '"+recipe.getName()+"' was removed");
            }
            else {
                System.out.println(ConsoleUtils.warningMessage("It was not possible to remove the Recipe"));
            }
        }
        else {
            System.out.println("Operation cancelled");
        }

        ConsoleUtils.readInput("Press ENTER to continue");
    }
}
