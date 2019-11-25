package com.uplooking.stream;

import com.google.common.collect.Lists;
import com.uplooking.xml.UserDTO;

import java.math.BigDecimal;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 自定义Collectors
 */
public class CollectorsDemo {

    public static void main(String[] args) {
        List<UserDTO> list = Lists.newArrayList();
        Map<String, IntSummaryStatistics> map = list.stream()
                .collect(
                        Collectors.groupingBy(
                                UserDTO::getUsername,
                                TreeMap::new,
                                Collectors.summarizingInt(UserDTO::getAge)));

        Map<String, BigDecimal> treeMap = list.stream()
                .collect(
                        Collectors.groupingBy(
                                UserDTO::getUsername,
                                TreeMap::new,
                                CollectorsUtil.summingBigDecimal(UserDTO::getCount)));

    }

}
