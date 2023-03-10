package site.stellarburgers.nomoreparties;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import site.stellarburgers.nomoreparties.pom.ConstructorPage;
import site.stellarburgers.nomoreparties.pom.HeaderPage;
import site.stellarburgers.nomoreparties.pom.ProfilePage;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Testing navigation functionality")
public class NavigationTest extends BaseTest {

    @Test
    @DisplayName("Test navigation to the main page and to the user profile")
    @Description("Navigating to registration, passing valid data, logging in, then:" +
            "1. Navigating to the profile." +
            "2. Navigating to the main page using constructor button." +
            "3. Navigating to the profile." +
            "4. Navigating to the main page using link in the logo. " +
            "Deleting profile after the test.")
    public void testNavigation() {
        ConstructorPage page = open(url, HeaderPage.class)
                .goToLogin()
                .goToRegister()
                .validRegistration(name, email, validPassword)
                .validLogin(email, validPassword);

//        1. Navigating to the profile.
        ProfilePage profilePage = page.header().goToProfile();
        assertThat(profilePage.getActiveChapterTitle(), equalTo(successfulRegistrationProfileTabName));

//        2. Navigating to the main page using constructor button.
        ConstructorPage mainPage = profilePage.header().goToConstructor();
        assertThat(mainPage.getIngredientsTitle(), equalTo(ingredientsTittleText));

//        3.Navigating to the profile.
        profilePage = mainPage.header().goToProfile();
        assertThat(profilePage.getActiveChapterTitle(), equalTo(successfulRegistrationProfileTabName));

//        4. Navigating to the main page using link in the logo.
        mainPage = profilePage.header().goToLogo();
        assertThat(mainPage.getIngredientsTitle(), equalTo(ingredientsTittleText));

        deleteTestUser();
    }
}
