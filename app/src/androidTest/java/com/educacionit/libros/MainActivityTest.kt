package com.educacionit.libros

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    companion object {
        @JvmStatic
        @BeforeClass
        fun setup() {
            Intents.init()
        }
    }

    @Before
    fun before() {
        onView(withId(R.id.etUsuario)).perform(clearText())
        onView(withId(R.id.etContraseña)).perform(clearText())
    }

    @Test
    fun should_show_toast_error_when_user_and_password_are_empty() {
        onView(withId(R.id.etUsuario))
            .perform(typeText(""))

        onView(withId(R.id.etContraseña))
            .perform(typeText(""))

        hideKeyboard()
        onView(withId(R.id.btnIniciarSesion))
            .perform(click())

        onView(withText("Completar datos"))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_show_toast_error_when_user_is_empty() {
        onView(withId(R.id.etUsuario))
            .perform(typeText(""))

        onView(withId(R.id.etContraseña))
            .perform(typeText("123456"))

        hideKeyboard()
        onView(withId(R.id.btnIniciarSesion))
            .perform(click())

        onView(withText("Completar datos"))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_show_toast_error_when_password_are_empty() {
        onView(withId(R.id.etUsuario))
            .perform(typeText("Nico"))

        onView(withId(R.id.etContraseña))
            .perform(typeText(""))

        hideKeyboard()
        onView(withId(R.id.btnIniciarSesion))
            .perform(click())

        onView(withText("Completar datos"))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_launch_HomeActivity_when_data_is_valid_and_btnLogin_is_clicked() {
        onView(withId(R.id.etUsuario))
            .perform(typeText("Nico"))

        onView(withId(R.id.etContraseña))
            .perform(typeText("123456"))

        hideKeyboard()
        onView(withId(R.id.btnIniciarSesion))
            .perform(click())

        intended(hasComponent(HomeActivity::class.java.name))
    }

    private fun hideKeyboard() {
        onView(isRoot()).perform(ViewActions.closeSoftKeyboard())
    }
}