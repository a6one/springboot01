package com.uplooking.ComparisonChain;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Strings;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.*;

public class ComparisonChainDemo {

    public static void main(String[] args) {
        Person tom = new Person("tom", 12);
        Person perter = new Person("perter", 12);
        int result = ComparisonChain.start()
                .compare(tom, perter, Comparator.comparing(Person::getAge))
                .compare(2, 2)
                .result();
        System.out.println(result);


        String s = Strings.padStart("tom", 4, '0');
        System.out.println(s);

        String repeat = Strings.repeat("tom", 3);
        System.out.println(repeat);


        Map<String, Integer> map = new HashMap<String, Integer>() {
            //构造一个测试用Map集合
            {
                put("love", 1);
                put("miss", 2);
            }
        };

        Function<String, Integer> function = Functions.forMap(map);
        //调用apply方法，可以通过key获取相应的value
        System.out.println(function.apply("love"));//i love u

        List<Integer> list = Lists.newArrayList(2, 31, 1, 0, 23, 23, 2);

//        Predicates.
        System.out.println(list);
        list.sort(Ordering.natural().reverse());
        System.out.println(list);

        FluentIterable<Integer> iterable = FluentIterable.from(list).transform(input -> input + 1);
        System.out.println(iterable);
    }

    private static class Person {
        private String name;
        private Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
