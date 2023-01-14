package com.example.rgr

val name = Field(
    "Ім'я",
    "Введіть ім'я",
    "String",
    "",
    true,
    true
)

val email = Field(
    "Пошта",
    "Введіть пошту",
    "String",
    "@",
    false,
    true
)

val phone = Field(
    "Номер телефону",
    "Введіть номер телефону",
    "String",
    "+380",
    false,
    false
)

val city = Field(
    "Місто",
    "Введіть назву міста, де зараз живете",
    "String",
    "",
    false,
    false
)


val country = Field(
    "Країна",
    "Введіть назву країни, громадянство якої маєте",
    "String",
    "",
    false,
    false
)
val age = Field(
    "Вік",
    "Введіть ваш вік",
    "Number",
    "",
    false,
    false
)
val religion = Field(
    "Віросповідання",
    "Введіть до якого віросповідання ви належите",
    "String",
    "",
    false,
    false
)

val defaultFields = mutableListOf(
    name,
    email,
    phone,
    city,
    country,
    age,
    religion
)

