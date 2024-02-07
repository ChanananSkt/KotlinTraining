# Add test function in ExampleInstrumentedTest
# You can get application context with InstrumentationRegistry.getInstrumentation().targetContext

@Test
fun test_mainViewModel_sum() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val vm = MainViewModel(appContext)
    assertEquals(4, vm.demo_unit_test_sum(2,2))
}
