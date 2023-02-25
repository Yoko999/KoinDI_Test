package com.example.dikointest

import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.*
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
public class AppKoinTest:KoinTest{
    private val instrumentedTestModule = module {
        single { SomeDB(androidApplication().applicationContext) }
        viewModel { params -> MyViewModel(get(),params.get(), params.get()) }
        scope<FirstFragment> {
            scoped {params -> MyFactoryForFragmentClass(params.get()) }
        }
        scope<SecondFragment> {
            scoped {params -> MyFactoryForFragmentClass(params.get()) }
        }
    }

   // @get:Rule
   // val koinTestRule = KoinTestRule(modules = listOf(instrumentedTestModule))

    @Before
    fun setup() {
        loadKoinModules(instrumentedTestModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(instrumentedTestModule)
    }

    @Test
    public fun testLaunch(){
        val activityScenario: ActivityScenario<MainActivity> = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)
        activityScenario.onActivity {
            val frNavigation = it.supportFragmentManager.primaryNavigationFragment
            println("Nav = $frNavigation")
            Assert.assertNotNull(frNavigation)

            val fr1 = frNavigation?.childFragmentManager?.fragments?.get(0)
            println("fr1 = $fr1")
            Assert.assertTrue(fr1 is FirstFragment)
            Assert.assertEquals("My Some str param",(fr1 as FirstFragment).fr1VM.str)
        }
    }
}