package com.projects.tests;

import com.projects.base.BaseTest;
import com.projects.pages.CartPage;
import com.projects.pages.CheckoutPage;
import com.projects.pages.LoginPage;
import com.projects.testdata.TestData;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Swag Labs UI Tests")
@Feature("Checkout Functionality")
public class CheckoutTest extends BaseTest {

    private void loginAndAddItem() {
        driver.get(BASE_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(TestData.VALID_USERNAME);
        loginPage.enterPassword(TestData.VALID_PASSWORD);
        loginPage.clickLogin();

        CartPage cartPage = new CartPage(driver);
        cartPage.addItemToCart();
    }

    @Test
    @Story("Valid Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User should be able to checkout with valid data")
    public void testSuccessfulCheckout() {
        loginAndAddItem();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.openCart();
        checkoutPage.clickCheckout();
        checkoutPage.enterCheckoutInfo(TestData.VALID_FIRST_NAME, TestData.VALID_LAST_NAME, TestData.VALID_POSTAL_CODE);
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        assertEquals("Thank you for your order!", checkoutPage.getSuccessMessage());
    }

    @Test
    @Story("Checkout with Empty First Name")
    @Severity(SeverityLevel.NORMAL)
    @Description("User should see error when first name is missing")
    public void testCheckoutMissingFirstName() {
        loginAndAddItem();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.openCart();
        checkoutPage.clickCheckout();
        checkoutPage.enterCheckoutInfo(TestData.EMPTY_FIRST_NAME, TestData.VALID_LAST_NAME, TestData.VALID_POSTAL_CODE);
        checkoutPage.clickContinue();

        assertTrue(checkoutPage.isErrorVisible());
        assertTrue(checkoutPage.getErrorMessage().toLowerCase().contains("first name"));
    }

    @Test
    @Story("Checkout with Empty Last Name")
    @Severity(SeverityLevel.NORMAL)
    @Description("User should see error when last name is missing")
    public void testCheckoutMissingLastName() {
        loginAndAddItem();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.openCart();
        checkoutPage.clickCheckout();
        checkoutPage.enterCheckoutInfo(TestData.VALID_FIRST_NAME, TestData.EMPTY_LAST_NAME, TestData.VALID_POSTAL_CODE);
        checkoutPage.clickContinue();

        assertTrue(checkoutPage.isErrorVisible());
        assertTrue(checkoutPage.getErrorMessage().toLowerCase().contains("last name"));
    }

    @Test
    @Story("Checkout with Empty Postal Code")
    @Severity(SeverityLevel.NORMAL)
    @Description("User should see error when postal code is missing")
    public void testCheckoutMissingPostalCode() {
        loginAndAddItem();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.openCart();
        checkoutPage.clickCheckout();
        checkoutPage.enterCheckoutInfo(TestData.VALID_FIRST_NAME, TestData.VALID_LAST_NAME, TestData.EMPTY_POSTAL_CODE);
        checkoutPage.clickContinue();

        assertTrue(checkoutPage.isErrorVisible());
        assertTrue(checkoutPage.getErrorMessage().toLowerCase().contains("postal code"));
    }

    @Test
    @Story("Checkout Without Items")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Checkout button is disabled when the cart is empty")
    public void testCheckoutWithEmptyCart() {
        driver.get(BASE_URL);

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(TestData.VALID_USERNAME);
        loginPage.enterPassword(TestData.VALID_PASSWORD);
        loginPage.clickLogin();

        // Step 2: Open Cart (without adding any items)
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.openCart();

        // Step 3: Verify cart is empty
        boolean isCartEmpty = driver.findElements(By.className("cart_item")).isEmpty();
        assertTrue(isCartEmpty, "Cart should be empty before attempting checkout");

        // Step 4: Verify Checkout button is disabled
        boolean isCheckoutButtonEnabled = driver.findElement(By.id("checkout")).isEnabled();
        assertFalse(isCheckoutButtonEnabled, "Checkout button should be disabled when cart is empty");
    }


    @Test
    @Story("Checkout with Special Characters in Name")
    @Severity(SeverityLevel.MINOR)
    @Description("User should be able to checkout with special characters in names")
    public void testCheckoutWithSpecialCharacters() {
        loginAndAddItem();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.openCart();
        checkoutPage.clickCheckout();
        checkoutPage.enterCheckoutInfo(TestData.SPECIAL_CHAR_FIRST_NAME, TestData.SPECIAL_CHAR_LAST_NAME, TestData.VALID_POSTAL_CODE);
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        assertEquals("Thank you for your order!", checkoutPage.getSuccessMessage());
    }

    @Test
    @Story("Cancel Checkout")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking cancel during checkout returns the user to the cart")
    public void testCancelDuringCheckout() {
        loginAndAddItem();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.openCart();
        checkoutPage.clickCheckout();
        checkoutPage.enterCheckoutInfo(TestData.VALID_FIRST_NAME, TestData.VALID_LAST_NAME, TestData.VALID_POSTAL_CODE);
        checkoutPage.clickContinue();
        checkoutPage.clickCancel();

        assertTrue(driver.getCurrentUrl().contains("inventory.html") ||
                        driver.getCurrentUrl().contains("cart.html"),
                "User should be redirected out of checkout after cancelling");
    }


}
