package com.codemobiles.android.cmauthenmvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codemobiles.android.cmauthenmvvm.ui.main.ui.theme.CMAuthenTheme

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DetailCompose()
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DetailCompose() {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Detail Fragment") },
                navigationIcon = {
                    IconButton(onClick = { findNavController().popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                // Customize Colors here
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                ),
            )
        }
        ) { innerPadding ->
            Body(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White),

                )

        }
    }

    @Composable
    fun Body(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Yellow),

                horizontalArrangement = Arrangement.SpaceAround

            ) {
                Button(onClick = { findNavController().popBackStack() }) {
                    Text("Tap1")
                }
                Button(onClick = { findNavController().popBackStack() }) {
                    Text("Tap2")
                }
            }
            MyListView()
        }
    }

    val myList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    @Composable
    fun MyListView(modifier: Modifier = Modifier) {
        LazyColumn() {
            items(myList) {
                ListItem(it)
            }
        }

    }

    private @Composable
    fun ListItem(text: String) {
        Column {
            Text(text)
//            Image(
//                painterResource(R.drawable.cmdev_default_black),
//                contentDescription = "",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.height(170.dp).fillMaxWidth()
//            )

            NetworkImage(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                url = "https://i.ytimg.com/vi/AZ7vubYWna8/maxresdefault.jpg"
            )

        }
    }


    @Composable
    fun NetworkImage(url: String, modifier: Modifier = Modifier) {
        AsyncImage(
            modifier = modifier,
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = "Loaded image"
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CMAuthenTheme {
            DetailCompose()
        }
    }
}