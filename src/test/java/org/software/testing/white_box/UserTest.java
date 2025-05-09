package org.software.testing.white_box;

import org.junit.Test;

import java.util.List;
import org.software.testing.*;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testEquals_SameObject() {
        User user = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        assertTrue(user.equals(user)); // Same object should be equal
    }

    @Test
    public void testEquals_NullObject() {
        User user = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        assertFalse(user.equals(null)); // Should not be equal to null
    }

    @Test
    public void testEquals_DifferentClass() {
        User user = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        String other = "Not a User";
        assertFalse(user.equals(other)); // Should not be equal to a different class
    }

    @Test
    public void testEquals_DifferentName() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ali", "777", List.of("TSR001", "TG002", "TDK003"));
        assertFalse(user1.equals(user2)); // Different names should not be equal
    }

    @Test
    public void testEquals_DifferentId() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ahmed", "888", List.of("TSR001", "TG002", "TDK003"));
        assertFalse(user1.equals(user2)); // Different IDs should not be equal
    }

    @Test
    public void testEquals_DifferentFavouriteMovieIds() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ahmed", "777", List.of("TSR001", "TG002", "AK007"));
        assertFalse(user1.equals(user2)); // Different favorite movie IDs should not be equal
    }

    @Test
    public void testEquals_SameFavouriteMovieIdsDifferentOrder() {
        User user1 = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        User user2 = new User("Ahmed", "777", List.of("TDK003", "TG002", "TSR001"));
        assertTrue(user1.equals(user2)); // Same IDs in different order should be equal
    }

    @Test
    public void testToString() {
        User user = new User("Ahmed", "777", List.of("TSR001", "TG002", "TDK003"));
        String expected = "User{name='Ahmed', id='777', favouriteMovieIds=[TSR001, TG002, TDK003]}";
        assertEquals(expected, user.toString()); // Verify the string representation
    }
}