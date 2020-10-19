package com.android_dev_challenge.status.ui.activity.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.android_dev_challenge.status.R
import com.android_dev_challenge.status.common.AppConstants.STATUS_DETAIL
import com.android_dev_challenge.status.common.Result
import com.android_dev_challenge.status.ui.activity.StatusActivity
import com.android_dev_challenge.status.ui.activity.StatusDetailActivity
import com.android_dev_challenge.status.ui.adapter.ChildStatusAdapter
import com.android_dev_challenge.status.ui.dto.StatusDto
import com.android_dev_challenge.status.viewmodel.StatusActivityViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A placeholder fragment containing a simple view.
 */
class ChildStatusFragment : Fragment() {
    
    private lateinit var errorTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var contentLoadingProgressBar: ContentLoadingProgressBar

    private lateinit var childStatusAdapter: ChildStatusAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        errorTextView = root.findViewById(R.id.errorTextView)
        recyclerView = root.findViewById(R.id.statusRecyclerView)
        contentLoadingProgressBar = root.findViewById(R.id.contentLoadingProgressBar)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            showProgressBar()
        }
        (activity as StatusActivity).statusActivityViewModel.setTabIndex(getCurrentTabIndex())
        observeUserList()
    }

    private fun observeUserList() {
        val statusLiveData: LiveData<Result<StatusDto>> = (activity as StatusActivity).statusActivityViewModel.getStatusLiveData()
        statusLiveData.removeObservers(this)
        statusLiveData.observe(
            viewLifecycleOwner,
            { result: Result<StatusDto> ->

                when(result){
                    is Result.Failure -> {
                        showErrorView(result.errorHolder.message)
                    }
                    is Result.Success -> {
                        val status = result.value

                        if (status.childStatus.isNullOrEmpty())
                            showErrorView(getString(R.string.error_msg_fetching_data))
                        else {
                            hideProgressBar()
                            showUsersList()
                            setAdapter(status)
                        }
                    }
                }
            })
    }

    private fun setAdapter(status: StatusDto) {
        childStatusAdapter = ChildStatusAdapter { status: StatusDto.ChildStatus ->
            val intent =
                Intent(activity, StatusDetailActivity::class.java).putExtra(STATUS_DETAIL, status)
            startActivity(intent)
        }
        recyclerView.adapter = childStatusAdapter
        childStatusAdapter.statusList = status.childStatus
    }

    private fun getCurrentTabIndex(): Int = arguments?.getInt(ARG_SECTION_NUMBER) ?: 0

    private fun showProgressBar() {
        contentLoadingProgressBar.visibility = View.VISIBLE
        contentLoadingProgressBar.show()
    }

    private fun hideProgressBar() {
        contentLoadingProgressBar.hide()
        contentLoadingProgressBar.visibility = View.GONE

    }

    private fun showErrorView(errorMessage: String) {
        hideProgressBar()
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = errorMessage
        recyclerView.visibility = View.GONE
    }

    private fun showUsersList() {
        hideProgressBar()
        errorTextView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): ChildStatusFragment {
            return ChildStatusFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}