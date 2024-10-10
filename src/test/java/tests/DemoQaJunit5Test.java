package tests;

import data.Hobbies;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pages.RegistrationPage;
import utils.RandomUtils;

import java.util.stream.Stream;

public class DemoQaJunit5Test extends TestBase{

    RegistrationPage registrationPage = new RegistrationPage();
    RandomUtils randomUtils = new RandomUtils();

    @ValueSource(strings = {
            "test@gmail.com",
            "abc@yandex.ru"
    })
    @ParameterizedTest(name = "Успешная регистрация с полным набором данных с email = {0}")
    void successfulRegistrationTest(String userEmail) {

        String firstName = randomUtils.getRandomFirstName(),
                lastName = randomUtils.getRandomLastName(),
                userGender = randomUtils.getRandomGender(),
                userPhone = randomUtils.getRandomUserPhone(),
                userDayOfBirth = randomUtils.getRandomDayOfBirth(),
                userMonthOfBirth = randomUtils.getRandomMonthOfBirth(),
                userYearOfBirth = randomUtils.getRandomYearOfBirth(),
                userSubjects = randomUtils.getRandomSubjects(),
                userHobbies = randomUtils.getRandomHobbies(),
                userUploadPicture = randomUtils.getRandomUploadPicture(),
                userCurrentAddress = randomUtils.getRandomCurrentAddress(),
                userState = randomUtils.getRandomUserState(),
                userCity = randomUtils.getRandomUserCity(userState);

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

    @CsvFileSource(resources = "/data/testData.csv")
    @ParameterizedTest(name = "Регистрация с минимальным объемом данных для " +
            "firstName = {0}, " +
            "lastName = {1}, " +
            "userGender = {2}")
    public void successfulMinDataRegistrationUsingCsvFileSourceTest(String firstName, String lastName, String userGender) {

        String userPhone = randomUtils.getRandomUserPhone();

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

    @EnumSource(Hobbies.class)
    @ParameterizedTest(name = "Регистрация с минимальным объемом данных с выбором Hobbies = {0}")
    public void successfulRegistrationUsingEnumSourceTest(Hobbies hobbies) {

        String firstName = randomUtils.getRandomFirstName(),
                lastName = randomUtils.getRandomLastName(),
                userGender = randomUtils.getRandomGender(),
                userPhone = randomUtils.getRandomUserPhone();

        registrationPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(userGender)
                .setPhoneNumber(userPhone)
                .setHobbies(hobbies.getHobbies())
                .clickSubmit();

        // Проверка результатов теста

        registrationPage.checkResults("Student Name", firstName + " " + lastName)
                .checkResults("Gender", userGender)
                .checkResults("Mobile", userPhone)
                .checkResults("Hobbies", hobbies.getHobbies());
    }

    static Stream<Arguments> negativeRegistrationUsingMethodSourceTest() {
        return Stream.of(
                Arguments.of(
                        Hobbies.MUSIC,
                        "9821374"
                ),
                Arguments.of(
                        Hobbies.READING,
                        "351231244"
                ),
                Arguments.of(
                        Hobbies.SPORTS,
                        "111"
                )
        );
    }

    @MethodSource()
    @ParameterizedTest(name = "Негативный кейс с некорректным номером телефона с комбинацией разных hobbies = {0} и userPhone = {1}")
    public void negativeRegistrationUsingMethodSourceTest(Hobbies hobbies, String phoneNumber) {

        String firstName = randomUtils.getRandomFirstName(),
                lastName = randomUtils.getRandomLastName(),
                userRandomEmail = randomUtils.getRandomEmail(),
                userGender = randomUtils.getRandomGender();

        registrationPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(userGender)
                .setEmail(userRandomEmail)
                .setHobbies(hobbies.getHobbies())
                .setPhoneNumber(phoneNumber)
                .clickSubmit();

        // Проверка результатов теста

        registrationPage.checkUnsuccessfulRegistration();
    }

}