package io.blusalt.simreg.presentation.ftue

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.blusalt.simreg.R
import io.blusalt.simreg.databinding.SignupFragmentBinding
import io.blusalt.simreg.presentation.BaseFragment
import javax.inject.Inject

class SignupFragment : BaseFragment() {

    companion object {
        const val TAG = "SignupFragment"
        fun newInstance() = SignupFragment()
    }

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private lateinit var viewModel: SignupViewModel
    private lateinit var binding:SignupFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = SignupFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProvider).get(SignupViewModel::class.java)

        binding.next.setOnClickListener {
            validateField(false)
        }
        binding.nextLogin.setOnClickListener {
            validateField(true)
        }
        initViewModelObservers()
    }

    private fun initViewModelObservers(){
        viewModel.userSignupResultUiData.observe(viewLifecycleOwner, {
            binding.next.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            if (it.isSuccess){
                findNavController().navigate(R.id.to_identityCaptureFragment)
            }else{
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.loginUserResultUiData.observe(viewLifecycleOwner, {
            binding.nextLogin.visibility = View.VISIBLE
            binding.progressBar2.visibility = View.GONE
            if (it.isSuccess){
                findNavController().navigate(R.id.to_homeIdentityListFragment)
            }else{
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validateField(isLogin:Boolean) {
        var isValid = true
        var focusView: View? = null

        binding.emailField.error = null
        binding.passwordField.error = null

        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()

        if (email.isNullOrEmpty()) {
            binding.emailField.error = getString(R.string.invalid_email)
            isValid = false
            focusView = binding.emailField
        }

        if (password.isNullOrEmpty()) {
            binding.passwordField.error = getString(R.string.invalid_password)
            isValid = false
            focusView = binding.passwordField
        }

        if (isValid) {
            hideSoftKey()
            if (isLogin){
                binding.nextLogin.visibility = View.GONE
                binding.progressBar2.visibility = View.VISIBLE
                loginUser(email, password)
            }else{
                binding.next.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                createNewUserOnFirebase(email, password)
            }
        } else {
            focusView?.requestFocus()
        }
    }

    private fun createNewUserOnFirebase(email:String, password:String){
        viewModel.createUser(email, password)
    }

    private fun loginUser(email:String, password:String){
        viewModel.loginUser(email, password)
    }

    private fun hideSoftKey() {
        val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = requireActivity().currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}