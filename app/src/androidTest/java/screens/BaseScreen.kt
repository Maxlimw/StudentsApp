package screens

import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.kaspersky.kaspresso.testcases.models.info.StepInfo

abstract class BaseScreen(private val testContext: TestContext<*>) : KScreen<BaseScreen>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    open fun step(description: String, actions: (StepInfo) -> Unit) {
        testContext.step(description, actions)
    }
}