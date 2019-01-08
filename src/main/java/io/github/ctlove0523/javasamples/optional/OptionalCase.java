package io.github.ctlove0523.javasamples.optional;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: OptionalCase
 *
 * @author: ct
 * Date:     2019/1/8 22:33
 */
public class OptionalCase {

    @Test
    public void test_createsEmptyOptionalObject() throws Exception {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void test_createOptionalObjectWithStaticMethod() throws Exception {
        String val = "not null";
        Optional<String> hasVal = Optional.of(val);
        assertTrue(hasVal.isPresent());
    }

    @Test
    public void test_processOptionalValue() throws Exception {
        String val = "not null";
        Optional<String> hasVal = Optional.of(val);
        System.out.println(hasVal.toString());
        assertEquals("Optional[not null]", hasVal.toString());
    }

    @Test(expected = NullPointerException.class)
    public void test_throwNullPointerException() throws Exception {
        String val = null;
        Optional<String> hasVal = Optional.of(val);
    }

    @Test
    public void test_passNullParamNoException() throws Exception {
        String val = null;
        Optional<String> hasVal = Optional.ofNullable(val);
        assertFalse(hasVal.isPresent());
    }

    @Test
    public void test_checkValuePresentOrNot() throws Exception {
        Optional<String> opt = Optional.of("has value");
        assertTrue(opt.isPresent());

        opt = Optional.ofNullable(null);
        assertFalse(opt.isPresent());
    }

    @Test
    public void doSomeThingWhenExist() throws Exception {
        Optional<String> opt = Optional.of("baeldung");
        opt.ifPresent(name -> System.out.println(name.length()));
    }

    @Test
    public void test_getValueUseorElse() throws Exception {
        Optional<String> hasVal = Optional.of("Hello");
        String val = hasVal.orElse("no value");
        assertEquals("Hello", val);

        Optional<String> noVal = Optional.empty();
        String defaultVal = noVal.orElse("default");
        assertEquals("default", defaultVal);
    }

    @Test
    public void test_getValueUseorElseget() throws Exception {
        Optional<String> hasVal = Optional.of("Hello");
        String val = hasVal.orElseGet(() -> "no value");
        assertEquals("Hello", val);

        Optional<String> noVal = Optional.empty();
        String defaultVal = noVal.orElseGet(() -> "default");
        assertEquals("default", defaultVal);
    }

    @Test
    public void test_differenceorElseAndorElseGet() throws Exception {
        Optional<String> hasVal = Optional.of("value");
        System.out.println("enter orElse method");
        String var0 = hasVal.orElse(getDefaultValue());

        System.out.println("enter orElseGet method");
        String var1 = hasVal.orElseGet(this::getDefaultValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_throwsExecption() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElseThrow(
                IllegalArgumentException::new);
    }

    @Test
    public void test_getValueUseGet() {
        Optional<String> opt = Optional.of("value");
        String name = opt.get();
        assertEquals("value", name);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_throwsNoSuchElementException() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).get();
    }

    @Test
    public void test_filter() throws Exception {
        Optional<Integer> passTest = Optional.of(101);
        assertTrue(passTest.filter(integer -> integer.intValue() > 100).isPresent());
        Optional<Integer> notPassTest = Optional.of(99);
        assertFalse(notPassTest.filter(integer -> integer.intValue() > 100).isPresent());
    }

    public boolean checkPriceWithoutOptional(Phone phone) {
        boolean isInRange = false;

        if (phone != null && phone.getPrice() != null
                && (phone.getPrice() >= 3000
                && phone.getPrice() <= 5000)) {

            isInRange = true;
        }
        return isInRange;
    }

    public boolean checkPriceWithOptional(Phone phone) {
        return Optional.ofNullable(phone)
                .map(Phone::getPrice)
                .filter(p -> p >= 3000)
                .filter(p -> p <= 5000)
                .isPresent();
    }

    @Test
    public void test_mapList2ListSize() {
        List<String> companyNames = Arrays.asList(
                "Java", "C++", "", "C", "", "Python");
        Optional<List<String>> listOptional = Optional.of(companyNames);

        int size = listOptional
                .map(List::size)
                .orElse(0);
        assertEquals(6, size);
    }

    @Test
    public void test_mapString2StringSize() {
        String name = "Hello World";
        Optional<String> nameOptional = Optional.of(name);

        int len = nameOptional
                .map(String::length)
                .orElse(0);
        assertEquals(11, len);
    }

    @Test
    public void test_checkPassword() {
        String password = " password ";
        Optional<String> passOpt = Optional.of(password);
        boolean correctPassword = passOpt.filter(
                pass -> pass.equals("password")).isPresent();
        assertFalse(correctPassword);

        correctPassword = passOpt
                .map(String::trim)
                .filter(pass -> pass.equals("password"))
                .isPresent();
        assertTrue(correctPassword);
    }

    @Test
    public void test_flatMap() {
        Person person = new Person("ct", 26, "pwd");
        Optional<Person> personOptional = Optional.of(person);

        Optional<Optional<String>> nameOptionalWrapper
                = personOptional.map(Person::getName);
        Optional<String> nameOptional
                = nameOptionalWrapper.orElseThrow(IllegalArgumentException::new);
        String name1 = nameOptional.orElse("");
        assertEquals("ct", name1);

        String name = personOptional
                .flatMap(Person::getName)
                .orElse("");
        assertEquals("ct", name);
    }


    private String getDefaultValue() {
        System.out.println("enter method get default value");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "default value";
    }

}
