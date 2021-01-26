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
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Р—Р°РІС‚СЂР°Рє", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "РћР±РµРґ", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "РЈР¶РёРЅ", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Р•РґР° РЅР° РіСЂР°РЅРёС‡РЅРѕРµ Р·РЅР°С‡РµРЅРёРµ", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Р—Р°РІС‚СЂР°Рє", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "РћР±РµРґ", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "РЈР¶РёРЅ", 410)
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
            LocalDate dateOfMeals = userMeal.getLocalTime();
            int oneMealCalories = userMeal.getCalories();
            int caloriesOfMeals = caloriesPerDayMap.getOrDefault(dateOfMeals, 0) + oneMealCalories;
            caloriesPerDayMap.put(dateOfMeals, caloriesOfMeals);
            LocalTime userMealTime = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(userMealTime, startTime, endTime)) {
                UserMealWithExcess userMealWithExceed = new UserMealWithExcess(userMeal, caloriesPerDayMap.get(userMeal.getLocalTime()) > caloriesPerDay);
                mealWithExceedList.add(userMealWithExceed);
            }
        }
        return mealWithExceedList;
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> mealList, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        return mealList
                .stream().filter(userMeal -> {
                    LocalDate dateOfMeals = userMeal.getLocalTime();
                    int oneMealCalories = userMeal.getCalories();
                    int caloriesOfMeals = caloriesPerDayMap.getOrDefault(dateOfMeals, 0) + oneMealCalories;
                    caloriesPerDayMap.put(dateOfMeals, caloriesOfMeals);
                    LocalTime userMealTime = userMeal.getDateTime().toLocalTime();
                    return userMealTime.compareTo(startTime) >= 0 && userMealTime.compareTo(endTime) <= 0;
                })
                .map(userMeal -> new UserMealWithExcess(userMeal, caloriesPerDayMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
