package com.codemobiles.android.cmauthenmvvm.viewmodel

sealed class NavigationCommand {
    data object NoDestination : NavigationCommand()
    data object HomeDestination : NavigationCommand()
    data object SuccessDestination : NavigationCommand()
    class AlertDialogDestination(val title:String, val content:String) : NavigationCommand()

}