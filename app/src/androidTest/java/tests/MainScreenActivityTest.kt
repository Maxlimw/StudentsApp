package tests

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.stubbing.Scenario
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import screens.MainScreen

class MainScreenActivityTest: BaseTest() {

    @Test
    @DisplayName("Кейс 1. Проверка скрытия сообщения об отсутствии студентов")
    fun noPersonsMessageNotDisplayedTest() = run {

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
            checkNoPersonsMessageNotDisplayed()
        }
    }

    @Test
    @DisplayName("Кейс 2. Проверка удаления студента")
    fun deletePersonTest() = run {

        val scenario = "deletePerson"
        stubFor(
            get("/api/")
                .inScenario(scenario)
                .whenScenarioStateIs(Scenario.STARTED)
                .willSetStateTo("secondPerson")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("responses/first_person.json"))
                )
        )
        stubFor(
            get("/api/")
                .inScenario(scenario)
                .whenScenarioStateIs("secondPerson")
                .willSetStateTo("thirdPerson")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("responses/second_person.json"))
                )
        )
        stubFor(
            get("/api/")
                .inScenario(scenario)
                .whenScenarioStateIs("thirdPerson")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("responses/third_person.json"))
                )
        )

        with(MainScreen(this)) {
            addPersonByNetworkManyTimes(3)
            deletePersonOnPosition(0)
            checkPersonListSize(2)
            checkPersonNameNotDisplayed("Vedant Rajesh", 0)
        }
    }

    @Test
    @DisplayName("Кейс 3. Проверка выбора по умолчанию в окне сортировки")
    fun defaultSortTest() = run {
        with(MainScreen(this)) {
            clickSortButton()
            checkDefaultSortIsSelected()
        }
    }

    @Test
    @DisplayName("Кейс 4. Проверка сортировки по возрасту")
    fun sortByAgeTest() = run {
        val scenario = "deletePerson"
        stubFor(
            get("/api/")
                .inScenario(scenario)
                .whenScenarioStateIs(Scenario.STARTED)
                .willSetStateTo("secondPerson")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("responses/first_person.json"))
                )
        )
        stubFor(
            get("/api/")
                .inScenario(scenario)
                .whenScenarioStateIs("secondPerson")
                .willSetStateTo("thirdPerson")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("responses/second_person.json"))
                )
        )
        stubFor(
            get("/api/")
                .inScenario(scenario)
                .whenScenarioStateIs("thirdPerson")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("responses/third_person.json"))
                )
        )

        with(MainScreen(this)) {
            addPersonByNetworkManyTimes(3)
            clickSortButton()
            clickSortByAge()
            checkAgeOfPeopleInList(arrayOf("68","67","48"))
        }
    }
}