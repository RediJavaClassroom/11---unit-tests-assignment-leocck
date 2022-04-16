# [Class 11 - Unit Tests Assignment](https://redi-school.github.io/intermediate-java/11-software-testing/)

## Exercise

Software engineers love caffeine, so we are planning to install a new coffee maker in the
classroom. Fortunately, we at ReDI School developed control software for a shiny new CoffeeMaker machine, 
and we can start using it real soon.

We just have to test it.

You will be working with the JUnit testing framework to create unit test cases, find bugs and
fix the CoffeeMaker code from ReDI School. The example code comes with some seeded faults. 
For this exercise you are required to create unit test cases for all the application
classes with JUnit, execute those against the code, detect faults, and fix as many issues as
possible.

===============================

### Understanding the Provided Code

First, let's understand how the project is structured:
- `com.redi.j2` package
  - `Main` class: runs the application and has logic to interact with the user
  - [`coffeemaker`](src/main/java/com/redi/j2/coffeemaker) package: has all the classes with the coffee machine logic
  - `exceptions` package: the exceptions handled by the application
  - `utils` package: utility classes used by the Main class

Your task will be to create unit tests for all classes inside the [`coffeemaker`](src/main/java/com/redi/j2/coffeemaker) package
- Don't worry about the `Main` class or the other packages
  - As the Main class requires keyboard input, you do not need to write unit tests for
    it. Instead, your unit tests should target the other classes of the system.
- For each class inside the package, you have to create a corresponding testing class
  - e.g. for the class `Recipe`, you have to create a corresponding `RecipeTests` class

#### Hints:
- Spend some time trying to understand the use cases
- Explore the package and analyse the code internal documentation
- Is also valid to run the application and check how it works
  - We would recommend performing some exploratory testing (human-driven
    exploration) using the `Main` class to understand how the system 
    should function before you write unit tests.
  - If you find other faults during exploratory testing, write more unit tests and then fix the code
   
===============================
 
![CoffeeMaker 2022](img/coffeemaker.png)
### Coffee Maker 2022 (User Manual)

Thanks for buying the Coffee Maker 2022!

Here's a list of features:

#### Initialization
- The Coffee Maker 2022 takes a bit of time to warm up, be patient!

#### Inventory
- The Coffee Maker 2022 works with four ingredients (Coffee, Milk, Chocolate and Sugar)
- The machine comes pre-loaded with 10 units of each ingredient
- To add more amounts of each ingredient, use the `Refill Ingredients` function
- The Parallel Universe Storage™ mechanism allows you to store unlimited amounts of ingredients
  - There is no maximum amount for an ingredient
- To see the current amount of each ingredient, use the `Check Inventory` function

#### Recipes
- The Coffee Maker 2022 comes already with one amazing recipe (Cappuccino)
- To add your own recipes, please use the `Add a Recipe` function
  - *Important:* The name of the recipes **must be unique**, and you cannot use negative prices or amount of ingredients
- The machine supports a maximum of **4 recipes** (amazing, isn't?)
- You can also `Edit` or `Remove` an existing recipe

#### Preparing a Recipe
- To start making coffee, please select the `Make Coffee` option and follow the instructions
- The machine will prepare the recipe and calculate the change to give to the customer
- Because the Coffee Maker 2022 is super smart, you cannot make a recipe if there are not enough ingredients

#### Turning it Off
- The same way a programmer needs Coffee, the CoffeeMaker 2022 also needs energy!
- Please don't ever shut down the application after the machine was initialized
  - If you do that, all new recipes and ingredients will be lost
