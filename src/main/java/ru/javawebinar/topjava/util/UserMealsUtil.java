package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> mealList, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        List<UserMealWithExcess> mealWithExceedList = new LinkedList<>();

        for (UserMeal userMeal : mealList) {
            int caloriesOfMeals = caloriesPerDayMap.getOrDefault(userMeal.getLocalDate(), 0) + userMeal.getCalories();
            caloriesPerDayMap.put(userMeal.getLocalDate(), caloriesOfMeals);
        }

        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExcess userMealWithExceed = new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), caloriesPerDayMap.get(userMeal.getLocalDate()) > caloriesPerDay);
                mealWithExceedList.add(userMealWithExceed);
            }
        }
        return mealWithExceedList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> mealList, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = mealList
                .stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate,
                Collectors.summingInt(UserMeal::getCalories)));

        return mealList
                .stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), caloriesPerDayMap.get(userMeal.getLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
