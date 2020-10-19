// https://github.com/robolectric/robolectric/issues/3698
// View Pager with Robolectric is throwing IllegalStateException: FragmentManager is already executing transaction

package com.android_dev_challenge.status.common

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.internal.util.reflection.Fields
import org.mockito.invocation.InvocationOnMock
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowViewGroup


@Implements(ViewPager::class)
class ShadowViewPager : ShadowViewGroup() {
    @RealObject
    var realViewPager: ViewPager? = null

    @Implementation
    fun setAdapter(adapter: PagerAdapter) {
        Shadow.directlyOn(realViewPager, ViewPager::class.java).adapter = addWorkaround(adapter)
    }

    private fun addWorkaround(adapter: PagerAdapter): PagerAdapter {
        val spied = Mockito.spy(adapter)
        val fragmentManager: FragmentManager? = getFragmentManagerFromAdapter(spied)
        Mockito.doAnswer { invocation: InvocationOnMock ->
            if (fragmentManager != null) {
                if (fragmentManager.fragments.isEmpty()) invocation.callRealMethod()
            }
            null
        }.`when`(spied).finishUpdate(ArgumentMatchers.any())
        return spied
    }

    private fun getFragmentManagerFromAdapter(adapter: PagerAdapter): FragmentManager? {
        for (instanceField in Fields.allDeclaredFieldsOf(adapter).instanceFields()) {
            val obj = instanceField.read()
            if (obj is FragmentManager) {
                return obj
            }
        }
        return null
    }
}