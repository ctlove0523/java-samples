public class FilteringStream {
    private static final String EMPLOYEE_NAME = "C:\\Users\\c00382802\\Downloads\\retry-demo\\src\\main\\resources\\deployees.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeReference<List<Employee>> reference = new TypeReference<List<Employee>>() {
    };

    /**
     * filer employee of test department
     *
     * @throws Exception
     */
    @Test
    public void test_useFilter() throws Exception {
        List<Employee> employees = mapper.readValue(new File(EMPLOYEE_NAME), reference);
        employees.stream().filter(employee -> null != employee && null != employee.getDepartName() && employee.getDepartName().equals("test"))
            .forEach(employee -> {
                Assert.assertTrue(null != employee);
                Assert.assertTrue(employee.getDepartName().equals("test"));
            });
    }

    /**
     * filer employee of test department
     *
     * @throws Exception
     */
    @Test
    public void test_useFlatMap() throws Exception {
        List<Employee> employees = mapper.readValue(new File(EMPLOYEE_NAME), reference);
        employees.stream().flatMap((Function<Employee, Stream<Employee>>) employee -> {
            if (employee.getDepartName().equals("test")) {
                return Stream.of(employee);
            }
            return Stream.empty();
        }).forEach(employee -> {
            Assert.assertTrue(null != employee);
            Assert.assertTrue(employee.getDepartName().equals("test"));
        });
    }
}
