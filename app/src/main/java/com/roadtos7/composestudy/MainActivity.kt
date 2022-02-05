package com.roadtos7.composestudy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roadtos7.composestudy.ui.theme.ComposeStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                var isButtonClicked by remember { mutableStateOf(false) }

                ComposeStudyTheme {
                    Conversation(
                        messages = SampleData.conversationSample,
                        isClicked = isButtonClicked,
                        onMessageClick = { if (!isButtonClicked) isButtonClicked = true }
                    )
                }
            }
        }
    }

    @Composable
    fun MessageCard(message: Message, onMessageClick: () -> Unit) {
        Row(modifier = Modifier
            .padding(all = 8.dp)
            .clickable {
                Log.d("MainActivity", "Is Clicked ")
                onMessageClick()
            })
        {
            Image(
                painter = painterResource(id = R.drawable.flowers),
                contentDescription = "this is flower",
                modifier = Modifier
                    .size(40.dp)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = message.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(all = 4.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun Conversation(messages: List<Message>, isClicked: Boolean, onMessageClick: () -> Unit) {
        val spaceSize: Int by animateIntAsState(if (isClicked) 20 else -50)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(spaceSize.dp)) {
            items(messages) { message ->
                MessageCard(message = message, onMessageClick = onMessageClick)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        var isButtonClicked by remember { mutableStateOf(false) }

        ComposeStudyTheme {
            Conversation(
                messages = SampleData.conversationSample,
                isClicked = isButtonClicked,
                onMessageClick = { if (!isButtonClicked) isButtonClicked = true }
            )
        }
    }
}
