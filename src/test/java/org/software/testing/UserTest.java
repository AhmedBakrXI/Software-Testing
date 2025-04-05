package org.software.testing;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void equals_sameAttributes_returnsTrue() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        assertEquals(user1, user2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ali", "777", List.of("TSR001", "TG002", "TDK003"));
        assertNotEquals(user1, user2);
    }

    @Test
    public void equals_differentId_returnsFalse() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ahmed", "888", List.of("TSR001", "TG002", "TDK003"));
        assertNotEquals(user1, user2);
    }

    @Test
    public void equals_differentFavouriteMovieIds_returnsFalse() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ahmed", "777", List.of("TSR001", "TG002", "AK007"));
        assertNotEquals(user1, user2);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        assertNotEquals(null, user1);
    }

    @Test
    public void equals_sameFavouriteMovieIdsDifferentOrder_returnsTrue() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ahmed", "777", List.of("TDK003", "TG002", "TSR001"));
        assertEquals(user1, user2);
    }

    @Test
    public void toString_returnsCorrectFormat() {
        User user = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        String expected = "User{name='Ahmed', id='777', favouriteMovieIds=[TSR001, TG002, TDK003]}";
        assertEquals(expected, user.toString());
    }
}