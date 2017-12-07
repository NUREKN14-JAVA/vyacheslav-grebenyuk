package com.nixsolutions.usermanagement;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class UserTest extends TestCase {
    
    // “ест актуальный дл€ текущей даты 7-ƒек-2017
    private static final int CURRENT_YEAR = 2017;
    private static final int YEAR_OF_BIRTH = 1971;
    
    // “ест(1) дл€ случа€ где день рождени€ уже прошел, но мес€ц еще идет в этом году    
    //  онстанта котора€ определ€ет по текущему году и году рождени€
    // пользовател€ его возраст
    private static final int ETALONE_AGE_1 = CURRENT_YEAR - YEAR_OF_BIRTH;
    private static final int DAY_OF_BIRTH_1 = 5;
    private static final int MONTH_OF_BIRTH_1 = Calendar.DECEMBER;
    
    // “ест(2) дл€ случа€ где мес€ц рождени€ прошел в этом году
    private static final int ETALONE_AGE_2 = CURRENT_YEAR - YEAR_OF_BIRTH;
    private static final int DAY_OF_BIRTH_2 = 5;
    private static final int MONTH_OF_BIRTH_2 = Calendar.NOVEMBER;
    
    // “ест(3) дл€ случа€ день рождени€ сегод€
    
    // “ест(4) дл€ случа€ мес€ц рождени€ идет, но день рождени€ еще впереди
    
    // “ест(5) дл€ случа€ когда мес€ц рождени€ еще не началс€
    
    
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
