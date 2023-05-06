package com.example.comparapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.comparapp.databinding.FragmentSearchBinding
import com.example.comparapp.databinding.FragmentUserProfileBinding
import com.example.comparapp.viewModel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfileFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var _binding: FragmentUserProfileBinding

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_profile, container, false)
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentUserProfileBinding.inflate(inflater)

        binding.btnChangePassword.setOnClickListener { changePassword() }
        binding.btnSubscribePremium.setOnClickListener { subscribePremium() }
        binding.btnLogout.setOnClickListener { logOut() }
    }

    private fun changePassword() {
        TODO("Not yet implemented")
    }

    private fun subscribePremium() {
        viewModel.logout()
    }

    private fun logOut() {
        viewModel.logout()
        findNavController().navigate(R.id.landingPage)
    }


    private fun setUserDocID() {
        viewModel.user.observe(viewLifecycleOwner) {
            userDocID = it.userDocID!!
        }
    }


    private fun getUserData() {

        var imgProfile = view?.findViewById<ImageView>(R.id.img_profile)
        var txtName = view?.findViewById<TextView>(R.id.txt_name)
        var txtUsername = view?.findViewById<TextView>(R.id.txt_username)
        var txtnumFollowers = view?.findViewById<TextView>(R.id.txt_num_followers)
        var txtnumPoints = view?.findViewById<TextView>(R.id.txt_num_points)
        var txtnumFollowings = view?.findViewById<TextView>(R.id.txt_num_followings)

        CoroutineScope(Dispatchers.Main).launch{
            var user = db.getUserByDocID(userDocID!!)
            if ((user != null) && (imgProfile != null)) {
                txtUsername!!.text = "@" + user!!.username
                Glide.with(requireView()).load(user.profile_image).into(imgProfile)
                txtName!!.text = user.name
                txtnumFollowings!!.text = user.n_followings.toString()
                txtnumPoints!!.text = user.n_points.toString()
                txtnumFollowers!!.text = user.n_followers.toString()
            }
        }

    }
}