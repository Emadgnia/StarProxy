package com.example.starcharles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(listOf())
    val characters: StateFlow<List<Character>> = _characters

    private val _films = MutableStateFlow<List<Film>>(listOf())
    val films: StateFlow<List<Film>> = _films

    private val apiService = ApiService.create()
    fun fetchFilmsFromUrl(urls: List<String>) {
        viewModelScope.launch {
            val films = mutableListOf<Film>()

            for (url in urls) {
                val filmFromUrl = apiService.fetchFilmsFromUrl(url)
                films.add(filmFromUrl)
            }

            _films.value = films
        }
    }
    fun fetchFilms() {
        viewModelScope.launch {
            val films = apiService.fetchFilms()
            _films.value = films.results
        }
    }
    fun fetchCharactersFromUrl(urls: List<String>) {
        viewModelScope.launch {
            val characters = mutableListOf<Character>()

            for (url in urls) {
                val characterFromUrl = apiService.fetchCharactersFromUrl(url)
                characters.add(characterFromUrl)
            }

            _characters.value = characters
        }
    }
    fun fetchCharacters() {
        viewModelScope.launch {
            val characters = apiService.fetchCharacters()
            _characters.value = characters.results
        }
    }
}
