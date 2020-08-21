package io.blusalt.simreg.presentation.identity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.microblink.entities.recognizers.Recognizer
import com.microblink.entities.recognizers.RecognizerBundle
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer
import com.microblink.uisettings.ActivityRunner
import com.microblink.uisettings.BlinkIdUISettings
import io.blusalt.simreg.R
import io.blusalt.simreg.databinding.IdentityCaptureFragmentBinding
import io.blusalt.simreg.dto.UserIdentityDto
import io.blusalt.simreg.presentation.BaseFragment
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


class IdentityCaptureFragment : BaseFragment() {

    companion object {
        const val DOCUMENT_SCANNING_REQUEST_CODE = 1000
        fun newInstance() = IdentityCaptureFragment()
    }

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory
    private lateinit var viewModel: IdentityCaptureViewModel
    private lateinit var mRecognizer: BlinkIdCombinedRecognizer
    private lateinit var mRecognizerBundle: RecognizerBundle
    private lateinit var binding: IdentityCaptureFragmentBinding
    private var userIdentityDto: UserIdentityDto? = null
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IdentityCaptureFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelProvider).get(IdentityCaptureViewModel::class.java)
        // create BlinkIdCombinedRecognizer
        mRecognizer = BlinkIdCombinedRecognizer()
        // bundle recognizers into RecognizerBundle
        mRecognizerBundle = RecognizerBundle(mRecognizer)
        // Create viewModel observers
        initViewModelObservers()
        binding.captureIdentity.setOnClickListener {
            if (binding.captureIdentity.text.toString().equals(getString(R.string.upload_identity), true)) {
                val safeUserIdentityDto = userIdentityDto ?: return@setOnClickListener
                saveUserIdentityToFirebase(safeUserIdentityDto)
            } else {
                startDocumentScanning()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DOCUMENT_SCANNING_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // load the data into all recognizers bundled within your RecognizerBundle
                mRecognizerBundle.loadFromIntent(data)
                // now every recognizer object that was bundled within RecognizerBundle
                // has been updated with results obtained during scanning session
                // you can get the result by invoking getResult on recognizer
                val result = mRecognizer.result
                if (result.resultState == Recognizer.Result.State.Valid) {
                    // result is valid, you can use it however you wish
                    val userIdentityDto = UserIdentityDto()
                    userIdentityDto.firstName = result.firstName
                    userIdentityDto.lastName = result.lastName
                    userIdentityDto.nationality = result.nationality
                    userIdentityDto.address = result.address
                    userIdentityDto.personalId = result.personalIdNumber
                    userIdentityDto.documentNumber = result.documentNumber
                    userIdentityDto.sex = result.sex
                    userIdentityDto.dob = result.dateOfBirth.stringResult
                    userIdentityDto.expiryDate = result.dateOfExpiry.stringResult

                    this.userIdentityDto = userIdentityDto
                    updateUi()
                } else {
                    showToast(getString(R.string.invalid_identity))
                }
            }
        }
    }

    private fun startDocumentScanning() {
        // Settings for BlinkIdActivity
        val settings = BlinkIdUISettings(mRecognizerBundle)
        // Start activity
        ActivityRunner.startActivityForResult(
            this,
            DOCUMENT_SCANNING_REQUEST_CODE, settings
        )
    }

    private fun saveUserIdentityToFirebase(userIdentityDto: UserIdentityDto) {
        binding.captureIdentity.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        viewModel.saveUserIdentity(userIdentityDto)
    }

    private fun initViewModelObservers() {
        viewModel.saveIdentityCaptureUiData.observe(viewLifecycleOwner, Observer {
            binding.captureIdentity.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            if (it.isSuccess) {
                findNavController().navigate(R.id.to_homeIdentityListFragment)
            } else {
                showToast(it.message)
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun updateUi() {
        binding.userIdentityDto = userIdentityDto
        binding.captureIdentity.text = getString(R.string.upload_identity)
        binding.infoLayout.visibility = View.GONE
        binding.identityInfo.root.visibility = View.VISIBLE
    }
}