Here is an example of handling event and preview multiple call

lifecycleScope.launch {
            viewModel.cmd.collect {
                it?.let {
                    viewModel.cmd.value = null // set null to avoid re-call the event
                    if (it.cmd == Constants.CMD_TOAST) { // TOAST COMMAND
                        Toast.makeText(applicationContext, it.payload as String, Toast.LENGTH_LONG).show()
                    } else if (it.cmd == Constants.CMD_START_ACTIVITY) {
                        val payload = it.payload // START ACTIVITY COMMAND
                        Intent(applicationContext, SuccessActivity::class.java).also {
                            it.putExtra(Constants.USER_BEAN, payload as UserBean)
                            startActivity(it)
                        }
                    }
                }
            }
        }
