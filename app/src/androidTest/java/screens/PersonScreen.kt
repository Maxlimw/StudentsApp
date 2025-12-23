package screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import matchers.GenderErrorMatcher
import personData.PersonData
import ru.tinkoff.favouritepersons.R

class PersonScreen(testContext: TestContext<*>) : BaseScreen(testContext) {
    private val nameField = KEditText { withId(R.id.et_name)}
    private val surnameField = KEditText { withId(R.id.et_surname)}
    private val genderField = KEditText { withId(R.id.et_gender)}
    private val birthDayField = KEditText { withId(R.id.et_birthdate)}
    private val emailField = KEditText { withId(R.id.et_email)}
    private val phoneField = KEditText { withId(R.id.et_phone)}
    private val addressField = KEditText { withId(R.id.et_address)}
    private val photoField = KEditText { withId(R.id.et_image)}
    private val scoreField = KEditText { withId(R.id.et_score)}
    private val saveButton = KButton { withId(R.id.submit_button)}
    private val genderLayout = onView( withId(R.id.til_gender))
    private val genderErrorText = KTextView { withText("Поле должно быть заполнено буквами М или Ж")}

    fun clickSaveButton() {
        step("Нажимаем кнопку сохранить") {
            saveButton.click()
        }
    }

    fun clickGenderField() {
        step("Нажимаем на поле пол") {
            genderField.click()
        }
    }

    fun editPersonName(name: String) {
        step("Вводим имя $name") {
            nameField.replaceText(name)
        }
    }

    private fun editPersonSurname(surname: String) {
        step("Вводим фамилию $surname") {
            surnameField.replaceText(surname)
        }
    }

    fun editPersonGender(gender: String) {
        step("Вводим $gender пол") {
            genderField.replaceText(gender)
        }
    }

    private fun editPersonBirthDay(birthDay: String) {
        step("Вводим дату рождения $birthDay") {
            birthDayField.replaceText(birthDay)
        }
    }

    private fun editPersonEmail(email: String) {
        step("Вводим почту $email") {
            emailField.replaceText(email)
        }
    }

    private fun editPersonPhone(phone: String) {
        step("Вводим номер телефона $phone") {
            phoneField.replaceText(phone)
        }
    }

    private fun editPersonAddress(address: String) {
        step("Вводим адрес $address") {
            addressField.replaceText(address)
        }
    }

    private fun editPersonPhoto(photo: String) {
        step("Вводим ссылку на фото $photo") {
            photoField.replaceText(photo)
        }
    }

    private fun editPersonScore(score: String) {
        step("Вводим количество баллов $score") {
            scoreField.replaceText(score)
        }
    }

    fun createPerson() {
        step("Создаем человека") {
            val newPerson = PersonData()
            editPersonName(newPerson.name)
            editPersonSurname(newPerson.surname)
            editPersonGender(newPerson.gender)
            editPersonBirthDay(newPerson.birthday)
            editPersonEmail(newPerson.email)
            editPersonPhone(newPerson.phone)
            editPersonAddress(newPerson.address)
            editPersonPhoto(newPerson.photo)
            editPersonScore(newPerson.score)
        }
    }

    fun checkPersonName(name: String) {
        step("Проверяем имя $name в поле имени") {
            nameField.hasText(name)
        }
    }

    fun checkPersonFields(name: String, surname: String, gender: String, birthDay: String) {
        step("Проверяем имя, фамилию, пол и дату рождения человека") {
            checkPersonName(name)
            surnameField.hasText(surname)
            genderField.hasText(gender)
            birthDayField.hasText(birthDay)
        }
    }

    fun checkGenderError() {
        step("Проверяем ошибку в поле выбора пола") {
            genderLayout.check(
                matches(
                    GenderErrorMatcher("Поле должно быть заполнено буквами М или Ж")
                )
            )
        }
    }

    fun checkGenderErrorNotExist() {
        step("Проверяем, что ошибка в поле выбора пола не отображается") {
            genderErrorText.doesNotExist()
        }
    }
}