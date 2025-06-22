package screens

import android.view.View
import androidx.test.espresso.action.ViewActions.swipeLeft
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.check.KCheckBox
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import ru.tinkoff.favouritepersons.R

class MainScreen(testContext: TestContext<*>) : BaseScreen(testContext) {

    private val addPersonButton = KButton { withId(R.id.fab_add_person)}
    private val addPersonByNetworkButton = KButton { withId(R.id.fab_add_person_by_network)}
    private val addPersonManuallyButton = KButton { withId(R.id.fab_add_person_manually)}
    private val noPersonsMessage = KTextView { withId(R.id.tw_no_persons)}
    private val sortButton = KButton { withId(R.id.action_item_sort) }
    private val radioButtonDefault = KCheckBox { withId(R.id.bsd_rb_default)}
    private val radioButtonAge = KCheckBox { withId(R.id.bsd_rb_age)}
    private val noInternetToast = KTextView { withText("Internet error! Check your connection") }

    private val listPerson = KRecyclerView(
        builder = { withId(R.id.rv_person_list) },
        itemTypeBuilder = {
            itemType {
                ItemRecyclerView(it)
            }
        }
    )

    class ItemRecyclerView(matcher: Matcher<View>) : KRecyclerItem<ItemRecyclerView>(matcher) {
        val personCard = KView(matcher) { withId(R.id.person_info_constraint) }
        val personName = KTextView(matcher) { withId(R.id.person_name) }
        val personInfo = KTextView(matcher) { withId(R.id.person_private_info) }
        val personAvatar = KImageView(matcher) { withId(R.id.person_avatar) }
        val personEmail = KTextView(matcher) { withId(R.id.person_email) }
        val personPhone = KTextView(matcher) { withId(R.id.person_phone) }
        val personAddress = KTextView(matcher) { withId(R.id.person_address) }
        val personRating = KTextView(matcher) { withId(R.id.person_rating) }
    }

    private fun clickAddPersonButton() {
        step("Нажимаем кнопку добавить") {
            addPersonButton.click()
        }
    }

    private fun clickAddPersonByNetworkButton() {
        step("Нажимаем кнопку добавить человека из интернета") {
            addPersonByNetworkButton.click()
        }
    }

    private fun clickAddPersonManuallyButton() {
        step("Нажимаем кнопку добавить человека вручную") {
            addPersonManuallyButton.click()
        }
    }

    fun clickSortButton() {
        step("Нажимаем кнопку сортировки") {
            sortButton.click()
        }
    }

    fun clickSortByAge() {
        step("Нажимаем кнопку сортировки по возрасту") {
            radioButtonAge.click()
        }
    }

    fun clickPersonOnPosition(position: Int) {
        step("Нажимаем на человека на позиции $position") {
            listPerson.childAt<ItemRecyclerView>(position) {
                personCard.click()
            }
        }
    }
    fun addPersonByNetwork() {
        step("Добавляем человека из интернета") {
            clickAddPersonButton()
            clickAddPersonByNetworkButton()
        }
    }

    fun addPersonManually() {
        step("Добавляем человека вручную") {
            clickAddPersonButton()
            clickAddPersonManuallyButton()
        }
    }

    fun addPersonByNetworkManyTimes(count: Int) {
        clickAddPersonButton()
        step("Добавляем $count человек из интернета") {
            for (i in 0 until count) {
                addPersonByNetworkButton.click()
            }
        }
    }

    fun deletePersonOnPosition(position: Int) {
        step("Удаляем человека на позиции $position") {
            listPerson.childAt<ItemRecyclerView>(position) {
                view.perform(swipeLeft())
            }
        }
    }

    fun checkNoPersonsMessageNotDisplayed() {
        noPersonsMessage.isNotDisplayed()
    }

    fun checkPersonListSize(size: Int) {
        step("Проверяем что список содержит $size человек") {
            assertEquals(size, listPerson.getSize())
        }
    }

    fun checkPersonNameNotDisplayed(name: String, position: Int) {
        step("Проверяем, что в списке нет человека с именем $name") {
            listPerson.childAt<ItemRecyclerView>(position) {
                personName.hasNoText(name)
            }
        }
    }

    fun checkDefaultSortIsSelected() {
        step("Проверяем что выбрана сортировка по умолчанию") {
            radioButtonDefault.isChecked()
        }
    }

    fun checkAgeOfPeopleInList(ages: Array<String>) {
        step("Проверяем возраста людей в списке") {
            for (i in ages.indices)
                listPerson.childAt<ItemRecyclerView>(i) {
                    personInfo.containsText(ages[i])
                }
        }
    }

    fun checkPersonInfoAtPosition(position: Int, name: String, gender: String, email: String, phone: String, address: String, score: String) {
        step("Проверяем данные человека") {
            listPerson.childAt<ItemRecyclerView>(position) {
                personName.hasText(name)
                personInfo.containsText(gender)
                personEmail.hasText(email)
                personPhone.hasText(phone)
                personAddress.hasText(address)
                personRating.hasText(score)
                personAvatar.isDisplayed()
            }
        }
    }

    fun checkAgeOfPersonAtPosition(position: Int, age: String) {
        step("Проверяем возраст человека на позиции $position") {
            listPerson.childAt<ItemRecyclerView>(position) {
                personInfo.containsText(age)
            }
        }
    }

    fun checkNoInternetToastDisplayed() {
        step("Проверяем что отображается снэкбар отстутствия интернета") {
            noInternetToast.isDisplayed()
        }
    }
}