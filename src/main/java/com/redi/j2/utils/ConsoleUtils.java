package com.redi.j2.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class ConsoleUtils {

    /**
     * Controls if colors should be enabled. Set this to false if you have issues with the colors;
     */
    private static boolean COLORS_ENABLED = true;

    static {
        Properties prop = new Properties();
        try {
            prop.load(ConsoleUtils.class.getClassLoader().getResourceAsStream("application.properties"));
            COLORS_ENABLED = Boolean.parseBoolean(prop.getProperty("colors.enabled"));
        }
        catch (IOException ex) {
            // ignore
        }
    }

    /**
     * Reads user input from the console and returns it as a String
     * @param message The message to show. Input will be done in the same line.
     * @return The input from the user
     */
    public static String readInput(String message) {
        System.out.print(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String returnString = "";
        try {
            returnString = br.readLine();
        }
        catch (IOException e){
            System.out.println(ConsoleUtils.warningMessage("Error reading input"));
        }
        if ("777".equals(returnString)) showEasterEgg();
        return (returnString != null)? returnString : "";
    }

    /**
     * Formats a String with color escaping characters supported by the console.
     * @param text The text to show
     * @param c The color
     * @return A formatted String with color, and reset character at the end
     */
    public static String colorOutput(String text, ColorType c) {
        if(!COLORS_ENABLED) return text;
        return c.getAnsiCode() + text + ColorType.RESET.getAnsiCode();
    }

    /**
     * Creates a string representation of a Menu.
     * @param menuTitle The title of the menu
     * @param menuNames An array with all the options from the menu
     * @param numbers An array with corresponding numbers for each option
     * @param enabled An array with configuration for each option
     * @param borderColor The color to be used in borders and title
     * @param numberColor The color to be used in numbers
     * @param nameColor The color to be used in option names
     * @return A String representation of the Menu
     */
    public static String menuFormatter(String menuTitle, String[] menuNames, int[] numbers, boolean[] enabled,
                                       ColorType borderColor, ColorType numberColor, ColorType nameColor) {

        StringBuilder builder = new StringBuilder();

        // checking the longest name in the menu
        int longestMenuName = menuTitle.length() + 8; // adding 1 space and 3 equal symbols on each side
        for (String menuName: menuNames) {
            longestMenuName = Math.max(longestMenuName, menuName.length() + 8); // 3 spaces + 2 pipe borders + 2 numbers + 1 dot
        }

        // calculating menu width (with borders)
        int menuWidth = (longestMenuName % 2 == menuTitle.length() % 2)? longestMenuName : longestMenuName + 1;

        // assembling the title
        if(COLORS_ENABLED) builder.append(borderColor.getAnsiCode());
        int amountSymbols = (menuWidth - menuTitle.length())/2 - 1;
        char[] borderTitle = new char[amountSymbols];
        Arrays.fill(borderTitle, '=');
        builder.append(new String(borderTitle));
        builder.append(" ");
        builder.append(menuTitle);
        builder.append(" ");
        builder.append(new String(borderTitle));
        builder.append("\n");

        // creating the menus
        for(int i=0; i<menuNames.length; i++) {
            if(COLORS_ENABLED) builder.append(borderColor.getAnsiCode());
            builder.append("| ");
            if(COLORS_ENABLED) builder.append(enabled[i]? numberColor.getAnsiCode() : ColorType.GRAY.getAnsiCode());
            builder.append(numbers[i]);
            builder.append(". ");
            if(COLORS_ENABLED) builder.append(enabled[i]? nameColor.getAnsiCode() : ColorType.GRAY.getAnsiCode());
            builder.append(menuNames[i]);
            char[] extraSpaces = new char[menuWidth - menuNames[i].length() - 7];
            Arrays.fill(extraSpaces, ' ');
            builder.append(new String(extraSpaces));
            if(COLORS_ENABLED) builder.append(borderColor.getAnsiCode());
            builder.append(" |");
            builder.append("\n");
        }

        // assembling the bottom border
        if(COLORS_ENABLED) builder.append(borderColor.getAnsiCode());
        char[] borderBottom = new char[menuWidth];
        Arrays.fill(borderBottom, '=');
        builder.append(new String(borderBottom));
        if(COLORS_ENABLED) builder.append(ColorType.RESET.getAnsiCode());

        return builder.toString();
    }

    /**
     *
     * @param title The title of the menu
     * @param names An array with all the options from the menu
     * @param numbers An array with corresponding numbers for each option
     * @param borderColor The color to be used in borders and title
     * @param numberColor The color to be used in numbers
     * @param nameColor The color to be used in option names
     * @return A String representation of the Menu
     */
    public static String menuFormatter(String title, String[] names, int[] numbers, ColorType borderColor, ColorType numberColor, ColorType nameColor) {
        boolean[] enabled = new boolean[names.length];
        Arrays.fill(enabled, true);
        return menuFormatter(title, names, numbers, enabled, borderColor, numberColor, nameColor);
    }

    /**
     * Formats a text with the default warning color
     * @param message The warning message text
     * @return The formatted message with color and reset character at the end
     */
    public static String warningMessage(String message) {
        if (!COLORS_ENABLED) return message;
        return colorOutput(message, ColorType.RED);
    }

    /**
     * Formats a number to have a currency character and two decimal numbers
     * @param value The value to format
     * @return The formatted value
     */
    public static String formatCurrency(BigDecimal value) {
        return String.format(Locale.ENGLISH, "$%.2f", value.doubleValue());
    }

    /**
     * This is a surprise
     */
    public static void showEasterEgg() {
        // image created at https://manytools.org/hacker-tools/convert-image-to-ansi-art
        InputStream is = ConsoleUtils.class.getClassLoader().getResourceAsStream("ansi-art.utf.ans");
        if(is == null) return;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            System.out.println();
            line = reader.readLine();
            while(line!=null) {
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ConsoleUtils.colorOutput("Here's an Easter Egg for you", ColorType.BRIGHT_PURPLE));
        ConsoleUtils.readInput("Press ENTER to continue");
    }

    public static void showHeader() {
        if (!COLORS_ENABLED) return;
        try {
            System.out.println();
            String border = colorOutput("================================================================================", ColorType.BRIGHT_YELLOW);
            System.out.println(border);
            URL url = ConsoleUtils.class.getClassLoader().getResource("header.png");
            BufferedImage img = ImageIO.read(url);
            for(int y=0; y<img.getHeight(); y++) {
                for(int x=0; x<img.getWidth(); x++) {
                    Color pixel = new Color(img.getRGB(x, y), true);
                    System.out.print(getBgAnsiColor(pixel) + " ");
                }
                System.out.print(ColorType.RESET.getAnsiCode() + "\n");
            }
            System.out.println(border);
            System.out.println();
        } catch (Exception e) {
            // ignore
        }
    }

    private static String getBgAnsiColor(Color color) {
        if (!COLORS_ENABLED) return "";
        if (color.getAlpha() == 0) return "\u001B[0m";
        return "\u001B[48;2;" + color.getRed() + ";" + color.getGreen() + ";" + color.getBlue() + "m";
    }

    /**
     * Plays a nice loading bar animation (fake, of course)
     * @param milliseconds the amount of time you want the animation to be
     */
    public static void playWarmingUpAnimation(int milliseconds){

        java.util.List<String> messages = List.of(
                "Initializing ...",
                "Checking Inventory",
                "Loading Recipes",
                "Warming Up",
                "Almost There",
                "Welcome to Coffee Maker 2022!"
        );

        try {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0 ; i <= 100 ; i++) {
                Thread.sleep(milliseconds/100);
                sb.setLength(0);
                if(COLORS_ENABLED) sb.append("\u001B[38;2;").append(255).append(";").append(255 - (150 * i / 100)).append(";").append(127 - (127 * i / 100)).append("m");
                sb.append("[").append(String.format("%-20s", ("#".repeat(i / 5)))).append("] ");
                sb.append(String.format("%3d", i)).append("% ");
                if(COLORS_ENABLED) sb.append(ColorType.BRIGHT_WHITE.getAnsiCode());
                sb.append(" |  ").append(messages.get((messages.size() - 1) * i / 100));
                if(COLORS_ENABLED) sb.append(ColorType.RESET.getAnsiCode());
                System.out.print(sb);
                System.out.print("\r");
            }
            Thread.sleep(2000);

        } catch (Exception ex) {
            // ignore
        }

        System.out.print("                                  \r");
    }
}
