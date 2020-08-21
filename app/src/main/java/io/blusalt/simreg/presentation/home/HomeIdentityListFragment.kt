package io.blusalt.simreg.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.blusalt.simreg.databinding.HomeIdentityListFragmentBinding
import io.blusalt.simreg.dto.UserIdentityDto
import io.blusalt.simreg.presentation.BaseFragment
import javax.inject.Inject

class HomeIdentityListFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeIdentityListFragment()
    }

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private lateinit var viewModel: HomeIdentityListViewModel
    private lateinit var binding:HomeIdentityListFragmentBinding
    private lateinit var mAdapter: HomeIdentityListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = HomeIdentityListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProvider).get(HomeIdentityListViewModel::class.java)
        mAdapter = HomeIdentityListAdapter()

        binding.recyclerView.apply {
            adapter = mAdapter
        }
        initViewModelObservers()
    }

    private fun initViewModelObservers(){
        viewModel.userIdentitiesUiData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            if (it.isSuccess){
                mAdapter.submitList(it.data as? MutableList<UserIdentityDto>)
                binding.errorTxt.visibility = View.GONE
            }else{
                binding.errorTxt.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        })
    }
}