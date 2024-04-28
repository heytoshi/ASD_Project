package edu.miu.cs489.tsogt.backend;

import edu.miu.cs489.tsogt.backend.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnitTests {
    private User user;

    @Before
    public void setUp() {
        user = new User("John", "Doe", "john.doe@example.com", "test1234", "6412339999", "john_doe", true, true, true, true);;
    }

    @Test
    public void testGetFirstName() {
        String expected = "John";
        String actual = user.getFirstName();
        Assert.assertEquals(expected, actual);
    }
}
