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

@Composable
fun FilmsScreen(viewModel: MainViewModel, navController: NavController, urls: List<String> = emptyList()) {
    val films = viewModel.films.collectAsState()

    if (urls.isNotEmpty()) {
        viewModel.fetchFilmsFromUrl(urls)
    } else {
        viewModel.fetchFilms()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(films.value) { film ->
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
                            val baseUrl = "https://swapi.dev/api/people/"

                            val characters = film.characters ?: emptyList()
                            val urlListString = characters
                                .map { it.replace(baseUrl, "") }
                                .map { it.replace("/", "") }
                                .joinToString(separator = "|")
                            navController.navigate("characters_screen/$urlListString")
                        })
                        .padding(16.dp)
                ) {
                    Text(film.title, style = MaterialTheme.typography.h6)
                    Text("Producer: ${film.producer}", style = MaterialTheme.typography.body1)
                    Text("Director: ${film.director}", style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}
