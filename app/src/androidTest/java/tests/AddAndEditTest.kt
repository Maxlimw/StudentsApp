package tests

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.http.Fault
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import screens.PersonScreen
import screens.MainScreen

class AddAndEditTest : BaseTest() {

    @Test
    @DisplayName("Кейс 5. Проверка открытия второго экрана с данными пользователя")
    fun openPersonInfoScreenTest() = run {
        stubFor(
            get("/api/")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("responses/first_person.json"))
                )
        )

        with(MainScreen(this)) {
            addPersonByNetwork()
            clickPersonOnPosition(0)
        }
        with(PersonScreen(this)) {
            checkPersonFields("Nellie", "Green", "W", "1977-01-01"  )
        }
    }

    @Test
    @DisplayName("Кейс 6. Проверка редактирования студента")
    fun editPersonTest() = run {
        stubFor(
            get("/api/")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("responses/first_person.json"))
                )
        )

        with(MainScreen(this)) {
            addPersonByNetwork()
            clickPersonOnPosition(0)
        }
        with(PersonScreen(this)) {
            editPersonName("Иосиф")
            checkPersonName("Иосиф")
        }
    }

    @Test
    @DisplayName("Кейс 7. Проверка добавления студента")
    fun addPersonManuallyTest() = run {
        with(MainScreen(this)) {
            addPersonManually()
        }
        with(PersonScreen(this)) {
            createPerson()
            clickSaveButton()
        }
        with(MainScreen(this)) {
            checkPersonInfoAtPosition(0, "Максим Лапин", "Male", "maksim@mail.ru", "+79999999999", "Улица Ленина", "52")
            checkAgeOfPersonAtPosition(0, "24")
        }
    }

    @Test
    @DisplayName("Кейс 8. Проверка отображения сообщения об ошибке")
    fun addPersonManuallyErrorTest() = run {
        with(MainScreen(this)) {
            addPersonManually()
        }
        with(PersonScreen(this)) {
            clickSaveButton()
            checkGenderError()
        }
    }

    @Test
    @DisplayName("Кейс 9. Проверка скрытия сообщения об ошибке при вводе данных в поле")
    fun errorTextHideTest() = run {
        with(MainScreen(this)) {
            addPersonManually()
        }
        with(PersonScreen(this)) {
            createPerson()
            editPersonGender("я")
            clickSaveButton()
            checkGenderError()
            clickGenderField()
            editPersonGender("")
            checkGenderErrorNotExist()
        }
    }

    @Test
    @DisplayName("Кейс 10. Проверка отображения сообщения об ошибке интернет-соединения")
    fun noInternetConnectionTest() = run {
        stubFor(
            get("/api/")
                .willReturn(
                    aResponse()
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)
                )
        )

        with(MainScreen(this)) {
            addPersonByNetwork()
            checkNoInternetToastDisplayed()
        }
    }
}