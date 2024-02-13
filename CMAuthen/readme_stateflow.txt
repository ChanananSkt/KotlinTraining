# https://developermemos.com/posts/mutable-state-flow-android

1. create and initail
val messageStateFlow = MutableStateFlow("Hello, World!")

2. update
messageStateFlow.update { "Hello, Android!" }

3. observe
ageStateFlow.collect { message ->
    textView.text = message
}

4. observe only the latest change
messageStateFlow.collectLatest { message ->
    textView.text = message
}


- .value
- apply
- collect