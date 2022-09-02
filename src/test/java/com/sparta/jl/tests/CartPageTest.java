package com.sparta.jl.tests;

import com.sparta.jl.pom.POMUtils;
import com.sparta.jl.pom.pages.CartPage;
import com.sparta.jl.pom.pages.CheckoutPage.CheckoutStepOnePage;
import com.sparta.jl.pom.pages.HomePage;
import com.sparta.jl.pom.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CartPageTest {
    static WebDriver driver;
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver";
    private CartPage cartPage;

    @BeforeAll
    static void setupAll() {
        POMUtils.setDriverLocation(DRIVER_LOCATION);
        driver = new ChromeDriver();
    }

    @BeforeEach
    void setup() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.goToHomePage();
        cartPage = homePage.gotoCartPage(driver);
    }

    @Test
    @DisplayName("Check continue shopping button returns to HomePage")
    void checkContinueShoppingButtonReturnsToHomePage() {
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", cartPage.clickContinueShopping(driver).getUrl());
    }
    @Test
    @DisplayName("Check that when continue shopping and going back to cart items persists")
    void checkThatWhenContinueShoppingAndGoingBackToCartItemsPersists() {
        Assertions.assertTrue(cartPage.isItemsPersistent());
    }

    @Test
    @DisplayName("Check that return list item names are correct")
    void checkThatReturnListItemNamesAreCorrect() {
        Assertions.assertTrue(cartPage.isReturnItemListCorrect());
    }

    @Test
    @DisplayName("Check that correct amount of items in cart")
    void checkThatCorrectAmountOfItemsInCart() {
        Assertions.assertEquals(2,cartPage.numberOfSomeItemsInCart());
    }
    @Test
    @DisplayName("Check that when all items added is correct amount")
    void checkThatWhenAllItemsAddedIsCorrectAmount() {
        Assertions.assertEquals(6,cartPage.numberOfAllItemsInCart());
    }

    @Test
    @DisplayName("Check all remove buttons work")
    void checkAllRemoveButtonsWork() {
        Assertions.assertEquals(0,cartPage.removeAllItemsButton());
    }
    @Test
    @DisplayName("Check that the remove buttons work")
    void checkThatTheRemoveButtonsWork() {
        Assertions.assertEquals(5, cartPage.removeOneItemButton());
    }

    @Test
    @DisplayName("Check list of remove buttons are correct numbers")
    void checkListOfRemoveButtonsAreCorrectNumbers() {
        Assertions.assertTrue(cartPage.numberOfRemoveButtons());
    }

    @Test
    @DisplayName("Check Checkout button goes to correct url")
    void checkCheckoutButtonGoesToCorrectUrl() {
        Assertions.assertEquals("https://www.saucedemo.com/checkout-step-one.html", cartPage.clickCheckout(driver).getURL());
    }
    @AfterAll
    static void tearDownAll() {
        driver.quit();
    }

    @AfterEach
    void tearDown() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}