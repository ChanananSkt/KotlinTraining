https://stackoverflow.com/questions/65008486/globalscope-vs-coroutinescope-vs-lifecyclescope

* .launch(Dispatch.Default) {} == .launch{ }
* GlobalScope.launch {}
* lifecycleScope.launch {}
* viewModelScope.launch {}
* withContext(Dispatchers.Main) {}

- GlobalScope.launch (Dispatchers.Main) {  } // not safe if activity or fragment is destroyed before the job is finished
- lifecycleScope.launch{} // safe because the job will cancelled automatically when user exits the activity or fragment before
- viewModelScope.launch {} // same as above but within viewModel scope
- withContext.launch(Dispatch.Main) {} // To switch thread to update UI
- Dispatchers Dispatchers determine which thread pool should be used. Dispatchers class is also CoroutineContext which can be added to CoroutineContext
- Dispatchers.Default: CPU-intensive work, such as sorting large lists, doing complex calculations and similar. A shared pool of threads on the JVM backs it.
- Dispatchers.IO: networking or reading and writing from files. In short – any input and output, as the name states
- Dispatchers.Main: mandatory dispatcher for performing UI-related events in Android's main or UI thread.


GlobalScope.launch(Dispatchers.IO): Launches a top-level coroutine on Dispatchers.IO. Coroutine is unbound and keeps running until finished or cancelled. Often discouraged since programmer has to maintain a reference to join() or cancel().
GlobalScope.launch: Same as above, but GlobalScope uses Dispatchers.Default if not specified. Often discouraged.
CoroutineScope(Dispatchers.IO).launch: Creates a coroutine scope which uses Dispatchers.IO unless a dispatcher is specified in the coroutine builder i.e. launch
CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main): Bonus one. Uses the same coroutine scope as above (if the scope instance is same!) but overrides Dispatcher.IO with Dispatchers.Main for this coroutine.
lifecycleScope.launch(Dispatchers.IO): Launches a coroutine within the lifecycleScope provided by AndroidX. Coroutine gets cancelled as soon as lifecycle is invalidated (i.e. user navigates away from a fragment). Uses Dispatchers.IO as thread pool.
lifecycleScope.launch: Same as above, but uses Dispatchers.Main if not specified.
viewModelScope.launch: Same as lifecycleScope but life longs only in viewModel

# Examples
GlobalScope.launch (Dispatchers.Main) {  }
lifecycleScope.launch { }
viewModelScope.launch { }