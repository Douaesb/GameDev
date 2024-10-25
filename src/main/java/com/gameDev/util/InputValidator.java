package com.gameDev.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    public static int validatePositiveInteger(String prompt) {
        int input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static double validatePositiveDouble(String prompt) {
        double input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Double.parseDouble(scanner.nextLine());
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static String validateString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.length() > 3) {
                return input;
            } else {
                System.out.println("Input must be more than 3 characters. Please try again.");
            }
        }
    }

    public static LocalDate validateDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a date in YYYY-MM-DD format.");
            }
        }
    }

    public static LocalDate validateEndDateAfterStartDate(LocalDate startDate, String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String input = scanner.nextLine();
            try {
                LocalDate endDate = LocalDate.parse(input);
                if (endDate.isAfter(startDate)) {
                    return endDate;
                } else {
                    System.out.println("End date must be after the start date. Please try again.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a date in YYYY-MM-DD format.");
            }
        }
    }
    public static String validateUniqueName(String prompt, List<String> existingNames) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!existingNames.contains(input)) {
                return input;
            } else {
                System.out.println("This name is already in use. Please enter a different name.");
            }
        }
    }

    public static int validateAge(String prompt) {
        int age;
        while (true) {
            System.out.print(prompt);
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age >= 16 && age <= 70) {
                    return age;
                } else {
                    System.out.println("Age must be between 16 and 70. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for age.");
            }
        }
    }

    public static <E extends Enum<E>> E validateEnum(Class<E> enumClass, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().toUpperCase();
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter one of the following: " +
                        String.join(", ", EnumSet.allOf(enumClass).stream().map(Enum::name).collect(Collectors.toList())));
            }
        }
    }

}
