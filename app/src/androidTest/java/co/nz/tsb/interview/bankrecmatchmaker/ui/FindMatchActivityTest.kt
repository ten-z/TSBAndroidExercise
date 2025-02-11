package co.nz.tsb.interview.bankrecmatchmaker.ui

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.nz.tsb.interview.bankrecmatchmaker.R
import junit.framework.TestCase.assertTrue
import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FindMatchActivityTest {

    @Test
    fun testWithCustomIntentExtra() {
        //Tests that the TARGET_MATCH_VALUE passed through the Intent is displayed correctly on the interface.
        val intent = Intent(ApplicationProvider.getApplicationContext(), FindMatchActivity::class.java).apply {
            putExtra(FindMatchActivity.TARGET_MATCH_VALUE, 300f)
        }

        ActivityScenario.launch<FindMatchActivity>(intent).use {
            onView(withId(R.id.match_text))
                .check(matches(withText(containsString("300"))))
        }
    }

    @Test
    fun testRecyclerViewClickUpdatesValue() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), FindMatchActivity::class.java).apply {
            putExtra(FindMatchActivity.TARGET_MATCH_VALUE, 1200f)
        }
        ActivityScenario.launch<FindMatchActivity>(intent).use {
            onView(withId(R.id.match_text))
                .check(matches(withText(containsString("1200"))))

            onView(withId(R.id.recycler_view))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0, click()
                    )
                )

            onView(withId(R.id.match_text))
                .check(matches(withText(containsString("951"))))
        }
    }

    @Test
    fun testAutoSelectionForSingleMatch() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), FindMatchActivity::class.java
        ).apply {
            putExtra(FindMatchActivity.TARGET_MATCH_VALUE, 250f)
        }

        ActivityScenario.launch<FindMatchActivity>(intent).use {

            onView(withId(R.id.recycler_view)).check { view, noViewFoundException ->
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }
                val recyclerView = view as RecyclerView
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(4)

                val itemView = viewHolder?.itemView
                val checkedItem = itemView as CheckedListItem
                assertTrue(checkedItem.isChecked)
            }
        }
    }

}