package com.zerodev.covidstats.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.zerodev.covidstats.R
import com.zerodev.covidstats.databinding.ActivityOnboardBinding
import com.zerodev.covidstats.datasource.OnboardData
import com.zerodev.covidstats.ui.adapter.OnboardAdapter

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding
    private lateinit var onboardAdapter: OnboardAdapter
    private lateinit var indicators: Array<ImageView>

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setupOnboard()
        setupOnboardIndicator()
        setCurrentIndicator(0)

        binding.vpOnboard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        binding.btnNext.setOnClickListener {
            binding.apply {
                if (vpOnboard.currentItem + 1 < onboardAdapter.itemCount) {
                    vpOnboard.currentItem = vpOnboard.currentItem + 1
                } else {
                    signIn()
                }
            }
        }
    }

    private val signInResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        signInResultLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Sign in failed, handle error
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                } else {
                    // do nothing
                }
            }
    }


    private fun setupOnboard() {
        val data = OnboardData.onboards(this)
        onboardAdapter = OnboardAdapter(data)
        binding.vpOnboard.adapter = onboardAdapter
    }

    private fun setupOnboardIndicator() {
        val indicatorsCount = onboardAdapter.itemCount
        indicators = Array(indicatorsCount) { ImageView(this) }
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 0, 8, 0)

        for (i in 0 until indicatorsCount) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.onboard_indicator_inactive
                )
            )
            indicators[i].layoutParams = params
            binding.llOnboardIndicator.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.llOnboardIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = binding.llOnboardIndicator.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.onboard_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.onboard_indicator_inactive
                    )
                )
            }
        }

        binding.apply {
            if (index == onboardAdapter.itemCount - 1) {
                btnNext.text = resources.getString(R.string.login_gmail)
                val icon = R.drawable.google_sign
                btnNext.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            } else {
                btnNext.text = resources.getString(R.string.next)
                btnNext.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
        }
    }
}