package com.nixsolutions.usermanagement;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class UserTest extends TestCase {
    
    // Тест актуальный для текущей даты 7-Дек-2017
    private static final int CURRENT_YEAR = 2017;
    private static final int YEAR_OF_BIRTH = 1971;
    
    // Тест(1) для случая где день рождения уже прошел, но месяц еще идет в этом году    
    // Константа которая определяет по текущему году и году рождения
    // пользователя его возраст
    private static final int ETALONE_AGE_1 = CURRENT_YEAR - YEAR_OF_BIRTH;
    private static final int DAY_OF_BIRTH_1 = 5;
    private static final int MONTH_OF_BIRTH_1 = Calendar.DECEMBER;
    
    // Тест(2) для случая где месяц рождения прошел в этом году
    private static final int ETALONE_AGE_2 = CURRENT_YEAR - YEAR_OF_BIRTH;
    private static final int DAY_OF_BIRTH_2 = 5;
    private static final int MONTH_OF_BIRTH_2 = Calendar.NOVEMBER;
    
    // Тест(3) для случая день рождения сегодя
    
    // Тест(4) для случая месяц рождения идет, но день рождения еще впереди
    
    // Тест(5) для случая когда месяц рождения еще не начался
    
    private User user;
    private Date dateOfBirthd;
    
    protected void setUp() throws Exception {
        super.setUp();
        user = new User();
    }

    
    public void testGetFullName() {
        user.setFirstName("John");
        user.setLastName("Doe");
        assertEquals("Doe, John", user.getFullName());
    }
    
    public void testGetAge1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_1, DAY_OF_BIRTH_1);
        dateOfBirthd = calendar.getTime();
        user.setDateOfBirth(dateOfBirthd);
        assertEquals(ETALONE_AGE_1, user.getAge());
    }
    
    public void testGetAge2() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_2);
        dateOfBirthd = calendar.getTime();
        user.setDateOfBirth(dateOfBirthd);
        assertEquals(ETALONE_AGE_2, user.getAge());
    }
}
