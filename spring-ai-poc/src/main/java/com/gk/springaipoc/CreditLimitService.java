package com.gk.springaipoc;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CreditLimitService {

    private static final Map<Integer, BigDecimal> ageMap = new ConcurrentHashMap<>();
    private static final Map<BigDecimal, BigDecimal> incomeMap = new ConcurrentHashMap<>();


    static {
        ageMap.put(20, new BigDecimal("10000"));
        ageMap.put(30, new BigDecimal("20000"));
        ageMap.put(40, new BigDecimal("50000"));


        incomeMap.put(new BigDecimal("2000"), new BigDecimal("10000"));
        incomeMap.put(new BigDecimal("4000"), new BigDecimal("20000"));
        incomeMap.put(new BigDecimal("8000"), new BigDecimal("40000"));
    }

    public String checkCreditLimit(CreditLimitInput input) {
        Integer closestAgeKey = findClosestKey(ageMap, input.age());
        BigDecimal closestIncomeKey = findClosestKey(incomeMap, input.income());

        BigDecimal ageCreditLimit = closestAgeKey != null ? ageMap.get(closestAgeKey) : BigDecimal.ZERO;
        BigDecimal incomeCreditLimit = closestIncomeKey != null ? incomeMap.get(closestIncomeKey) : BigDecimal.ZERO;

        BigDecimal creditLimit = ageCreditLimit.add(incomeCreditLimit).divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);

        if (ageCreditLimit.equals(BigDecimal.ZERO) && incomeCreditLimit.equals(BigDecimal.ZERO)) {
            return "Credit limit not available for age: " + input.age() + " and income: " + input.income();
        }
        return "Credit limit for age: " + input.age() + " and income: " + input.income() + " is " + creditLimit;
    }

    private <K extends Comparable<K>, V> K findClosestKey(Map<K, V> map, K key) {
        return map.keySet()
                .stream()
                .filter(k -> k.compareTo(key) <= 0)
                .max(Comparable::compareTo)
                .orElse(null);
    }
}
