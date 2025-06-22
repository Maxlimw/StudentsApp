package tests

import android.content.Context
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.room.PersonDataBase
import rules.PreferenceRule
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

abstract class BaseTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple()
) {

    @get:Rule(order = 1)
    val prefs = PreferenceRule()

    @get:Rule(order = 2)
    val mockRule = WireMockRule(5000)

    @get:Rule(order = 3)
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    private lateinit var db: PersonDataBase

    @Before
    fun createDb() {
        db = PersonDataBase.getDBClient(InstrumentationRegistry.getInstrumentation().targetContext)
        db.personsDao().clearTable()
    }

    @After
    fun clearDB() {
        db.personsDao().clearTable()
    }

    fun fileToString(path: String, context: Context = InstrumentationRegistry.getInstrumentation().context): String {
        return BufferedReader(InputStreamReader(context.assets.open(path), StandardCharsets.UTF_8)).readText()
    }
}