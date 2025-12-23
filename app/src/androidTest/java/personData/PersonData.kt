package personData

data class PersonData(
    val name: String = "Максим",
    var surname: String = "Лапин",
    val gender: String = "М",
    val birthday: String = "2000-07-22",
    val email: String = "maksim@mail.ru",
    val phone: String = "+79999999999",
    val address: String = "Улица Ленина",
    val photo: String = "https://randomuser.me/api/portraits/men/56.jpg",
    val score: String = "52"
)