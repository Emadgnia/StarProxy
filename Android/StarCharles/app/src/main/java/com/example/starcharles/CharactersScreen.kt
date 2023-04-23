package com.example.starcharles
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.starcharles.MainViewModel
@Composable
fun CharactersScreen(viewModel: MainViewModel, navController: NavController, urls: List<String> = emptyList()) {
    val characters = viewModel.characters.collectAsState()
    if (urls.isNotEmpty()) {
        viewModel.fetchCharactersFromUrl(urls)
    } else {
        viewModel.fetchCharacters()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(characters.value) { character ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            val baseUrl = "https://swapi.dev/api/films/"

                            val films = character.films ?: emptyList()
                            val urlListString = films
                                .map { it.replace(baseUrl, "") }
                                .map { it.replace("/", "") }
                                .joinToString(separator = "|")
                            navController.navigate("films_screen/$urlListString")
                        })
                        .padding(16.dp)
                ) {
                    Text(character.name, style = MaterialTheme.typography.h6)
                    Text("Gender: ${character.gender ?: "N/A"}", style = MaterialTheme.typography.subtitle1)
                    Text("Hair Color: ${character.hairColor ?: "N/A"}", style = MaterialTheme.typography.subtitle1)
                    Text("Height: ${character.height}", style = MaterialTheme.typography.subtitle1)
                }
            }
        }
    }
}
