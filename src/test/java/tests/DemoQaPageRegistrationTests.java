package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.RegistrationPage;
import utils.RandomUtils;

public class DemoQaPageRegistrationTests extends TestBase {


    RegistrationPage registrationPage = new RegistrationPage();
    RandomUtils randomUtils = new RandomUtils();


    String firstName = randomUtils.getRandomFirstName(),
            lastName = randomUtils.getRandomLastName(),
            userGender = randomUtils.getRandomGender(),
            userPhone = randomUtils.getRandomUserPhone(),
            userIncorrectPhone = randomUtils.getRandomIncorrectUserPhone(),
            userDayOfBirth = randomUtils.getRandomDayOfBirth(),
            userMonthOfBirth = randomUtils.getRandomMonthOfBirth(),
            userYearOfBirth = randomUtils.getRandomYearOfBirth(),
            userSubjects = randomUtils.getRandomSubjects(),
            userHobbies = randomUtils.getRandomHobbies(),
            userUploadPicture = randomUtils.getRandomUploadPicture(),
            userCurrentAddress = randomUtils.getRandomCurrentAddress(),
            userState = randomUtils.getRandomUserState(),
            userCity = randomUtils.getRandomUserCity(userState);

    @ValueSource(strings = {
            "test@gmail.com",
            "abc@yandex.ru"
    })
    @ParameterizedTest(name = "Успешная регистрация с полным набором данных с email = {0}")
    void successfulRegistrationTest(String userEmail) {

        registrationPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(userEmail)
                .setGender(userGender)
                .setPhoneNumber(userPhone)
                .setDateOfBirth(userDayOfBirth, userMonthOfBirth, userYearOfBirth)
                .setSubjects(userSubjects)
                .setHobbies(userHobbies)
                .setUserUploadPicture(userUploadPicture)
                .setUserCurrentAddress(userCurrentAddress)
                .setUserState(userState)
                .setUserCity(userCity)
                .clickSubmit();


        // Проверка результатов теста

        registrationPage.checkResults("Student Name", firstName + " " + lastName)
                .checkResults("Student Email", userEmail)
                .checkResults("Gender", userGender)
                .checkResults("Mobile", userPhone)
                .checkResults("Date of Birth", userDayOfBirth + " " + userMonthOfBirth + "," + userYearOfBirth)
                .checkResults("Subjects", userSubjects)
                .checkResults("Hobbies", userHobbies)
                .checkResults("Picture", userUploadPicture)
                .checkResults("Address", userCurrentAddress)
                .checkResults("State and City", userState + " " + userCity);
    }

    @Test
    public void successfulMinDataRegistrationTest() {
        registrationPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(userGender)
                .setPhoneNumber(userPhone)
                .clickSubmit();

        // Проверка результатов теста

        registrationPage.checkResults("Student Name", firstName + " " + lastName)
                .checkResults("Gender", userGender)
                .checkResults("Mobile", userPhone);
    }

    @Test
    public void negativeRegistrationTest() {
        registrationPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(userGender)
                .setPhoneNumber(userIncorrectPhone)
                .clickSubmit();

        // Проверка результатов теста

        registrationPage.checkUnsuccessfulRegistration();
    }

}