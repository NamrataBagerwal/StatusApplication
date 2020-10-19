package com.android_dev_challenge.status.ui.activity

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android_dev_challenge.status.R
import com.android_dev_challenge.status.common.Result
import com.android_dev_challenge.status.common.ShadowViewPager
import com.android_dev_challenge.status.common.StatusApplicationTest
import com.android_dev_challenge.status.ui.activity.fragment.ChildStatusFragment
import com.android_dev_challenge.status.ui.adapter.ParentStatusTabAdapter
import com.android_dev_challenge.status.ui.dto.StatusDto
import com.android_dev_challenge.status.viewmodel.StatusActivityViewModel
import com.nhaarman.mockitokotlin2.given
import kotlinx.android.synthetic.main.activity_status.*
import kotlinx.android.synthetic.main.activity_status.view.*
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = StatusApplicationTest::class, shadows = [ShadowViewPager::class])
class StatusActivityTest : KoinTest {

    companion object {
        private const val IS_NOT_VISIBLE = "is not visible"
        private const val IS_VISIBLE = "is visible"
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var viewModel: StatusActivityViewModel

    private lateinit var activityScenario: ActivityScenario<StatusActivity>

    @Mock
    private lateinit var statusLiveData: LiveData<Result<StatusDto>>

    @Before
    fun setUp() {

        viewModel = declareMock {
            given(this.getStatusLiveData()).willReturn(statusLiveData)

        }

        activityScenario = launchActivity()
        activityScenario.moveToState(Lifecycle.State.CREATED)
    }

    @Test
    fun getStatusActivityViewModel() {
        assertNotNull(viewModel)
    }

    // Failing Test Case
    @Test
    fun onCreate() {
        activityScenario.onActivity { activity ->
            assertEquals(activity.getString(R.string.app_name), activity.appBarLayoutTitle.text)
            // https://github.com/robolectric/robolectric/issues/3698
            // View Pager with Robolectric is throwing IllegalStateException: FragmentManager is already executing transaction
            // TODO: This needs to be looked again
            assertNotNull(activity.tabs.view_pager)
            assertEquals(activity.view_pager, activity.tabs.view_pager)
            assertThat(
                activity.view_pager.adapter,
                Matchers.instanceOf(ParentStatusTabAdapter::class.java)
            )
            val adapter = activity.view_pager.adapter as ParentStatusTabAdapter
            assertEquals(2, adapter.count)
            assertEquals(activity.getString(R.string.tab_text_1), adapter.getPageTitle(0))
            assertEquals(activity.getString(R.string.tab_text_2), adapter.getPageTitle(1))
            assertThat(adapter.getItem(0), Matchers.instanceOf(ChildStatusFragment::class.java))
            assertEquals(adapter.getItem(0), adapter.getItem(1))
        }
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }
}