# Setup
- build.gradle.ki (top-level)
   dependencies {
      // to allow pass argument between fragment easier than traditional way without bundle builder
      val nav_version = "2.7.6"
      classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
   }

- build.gradle.kt (app)
   plugins {
        ...
       // to allow pass argument between fragment easier than traditional way without bundle builder
       id("androidx.navigation.safeargs")
   }

    dependencies {
        // Fragment Navigation
        val navVersion = "2.7.6"
        implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
        implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
        implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
        implementation("androidx.viewpager2:viewpager2:1.0.0")
        implementation("androidx.navigation:navigation-compose:2.7.6")
    }

# Create a res/navigation/nav_graph.xml
- right click at res
- select Android resource file
- resource type: navigation
- name: nav_graph.xml

# Add fragment into nav_graph.xml
- click + button
- drag an entry fragment
- drag more fragment
- create navigation action by linking between begin and end fragment (main_fragment -> success_fragment)
- create argument to handle input passed from begin fragment to destination fragment

# Navigation to another fragment
- findNavController().navigate(MainFragmentDirections.actionMainFragmentToSuccessFragment(mainViewModel.user.value)
- back to the begin fragment by calling : findNavController().navigateUp()
- call destination fragment and clear back stacks by setting attribute at action in nav_graph.xml
    Ex:
    <action
        android:id="@+id/action_mainFragment_to_successFragment"
        app:destination="@id/successFragment"
        app:popUpTo="@id/nav_graph"   <============
        app:popUpToInclusive="true"/> <============

- Navigate fragment in viewmodel by using observation method

    private fun observeNavigationEvent() {
            mainViewModel.navigationCommand.observe(viewLifecycleOwner) { command ->
                when (command) {
                    is NavigationCommand.HomeDestination -> {
                        findNavController().navigate(
                            MainFragmentDirections.actionMainFragmentToHomeFragment(mainViewModel.user.value)
                        )
                    }

                    is NavigationCommand.SuccessDestination -> {
                        findNavController().navigate(
                            MainFragmentDirections.actionMainFragmentToSuccessFragment(mainViewModel.user.value)
                        )
                    }

                    // Handle other navigation commands
                    else -> {}
                }
            }
        }

# Animation