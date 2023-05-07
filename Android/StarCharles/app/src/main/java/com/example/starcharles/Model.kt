package com.example.starcharles

import java.util.*

data class Character(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val height: String?,
    val mass: String?,
    val hairColor: String?,
    val skinColor: String?,
    val eyeColor: String?,
    val birthYear: String?,
    val gender: String?,
    val homeworld: String?,
    val films: List<String>?,
    val species: List<String>?,
    val vehicles: List<String>?,
    val starships: List<String>?,
    val created: String?,
    val edited: String?,
    val url: String
)

data class Characters(
    val results: List<Character>
)

data class Film(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val episodeId: Int,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val species: List<String>,
    val starships: List<String>,
    val vehicles: List<String>,
    val characters: List<String>,
    val planets: List<String>,
    val url: String,
    val created: String,
    val edited: String
)

data class Films(
    val results: List<Film>
)
