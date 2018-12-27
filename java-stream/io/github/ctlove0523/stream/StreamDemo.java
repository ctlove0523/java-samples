package io.github.ctlove0523.stream
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * StreamDemo
 *
 * @author tong chen
 * 2018/12/26
 */
public class StreamDemo {
    private static final String EMPLOYEE_NAME = "deployees.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeReference<List<Employee>> reference = new TypeReference<List<Employee>>() {
    };

    public static void main(String[] args) throws Exception {
        List<Employee> employees = mapper.readValue(new File(EMPLOYEE_NAME), reference);
        List<String> names = employees.stream().map(new Function<Employee, String>() {
            @Override
            public String apply(Employee employee) {
                return employee.getName();
            }
        }).collect(Collectors.toList());

        int sum = employees.stream().collect(Collectors.summingInt(Employee::getSalary));
        System.out.println(sum);

        Map<String, List<Employee>> byDepartment = employees.stream().collect(Collectors.groupingBy(Employee::getDepartName));
        System.out.println(byDepartment.size());

        Map<String, Integer> totalBySalay = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartName, Collectors.summingInt(Employee::getSalary)));
        System.out.println(totalBySalay);

        Map<String, Integer> countByDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartName, Collectors.summingInt(new ToIntFunction<Employee>() {
                @Override
                public int applyAsInt(Employee value) {
                    return 1;
                }
            })));
        System.out.println(countByDepartment);
        System.out.println(countByDepartment.entrySet().stream()
            .max(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o1.getValue() - o2.getValue();
                }
            }).get().getKey());

         // Calculate the average of all employee salaries
         double doubleValue = employees.stream().collect(Collectors.averagingDouble(value -> value.getSalary()));
    }
}
