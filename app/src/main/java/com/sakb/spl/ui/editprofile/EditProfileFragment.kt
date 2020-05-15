package com.sakb.spl.ui.editprofile

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.base.BaseFragment
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.databinding.FragmentEditProfileBinding
import com.sakb.spl.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

const val STORAGE_REQUEST_CODE = 15
const val GALLERY_REQUEST_CODE = 12

class EditProfileFragment : BaseFragment() {


    private lateinit var binding : FragmentEditProfileBinding
    override val viewModel by viewModel<EditProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loadMyProfile()

        viewModel.profileResultLiveData.observe(this, Observer {data->
                        binding.userNameVal.setText( data.data?.displayName)
                        binding.emailVal.setText(data.data?.email)
                        binding.addressVal.text = data.data?.teamName
                        binding.addressVal.setOnClickListener {
                            dialogTeams()
                        }
                        binding.changePhotoBtn.setOnClickListener {
                            if (isPermissionGranted()) {
                                pickPhotoFromGallery()
                            }
                            else {
                                // IF he refuse before ...
                                if (shouldShowRequestPermissionRationale( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    || shouldShowRequestPermissionRationale( Manifest.permission.READ_EXTERNAL_STORAGE) ) {
                                    context?.toast(getString(R.string.permissions_info_sd))
                                }else {
                                    requestPermeation()
                                }
                        }}
                        binding.editProfileBtn.setOnClickListener {
                            val best_team_link = viewModel.selectedTeamPosition?.let {
                                viewModel.teams?.get(it)?.link
                            }

                            viewModel.updateProfile(

                                best_team_link?:""                                 ,
                                ""+binding.userNameVal.text,
                                ""+binding.emailVal.text,
                                viewModel.newImageUrl?:""
                            )
                        }
                        if (data.data?.image?.contains("http") == true) {
                            Timber.e("yesssss=======http")
                            Glide.with(this).load(data.data.image).circleCrop()
                                .into(binding.profileImage)
                        }
                        else
                            Glide.with(this).load(Constants.baseUrl+ data.data?.image).circleCrop().into(binding.profileImage)



            })


        viewModel.teamsResultLiveData.observe(this,
            Observer {data->

                        //toast(""+data.Message)
                       // Timber.e(""+data?.data?.size)
                       // adapter = SingleAdapter( data?.data)
                       // binding.recyclerView.adapter = adapter
                      /*  adapter?.onItemClick = {pos, data->
                            val user = PrefManager.getUser()
                            viewModel.updateProfile(""+user?.data?.accessToken,""+data?.link)
                        }*/

                        viewModel.updateTeamsList(data.data)
                        //createList()




            })




        viewModel.uploadingImage.observe(this,
            Observer {data->
                viewModel.newImageUrl = data.data
                       // context?.toast(""+data.Message)
            })


    }




   private fun dialogTeams() {
        val options = viewModel.teamsNames.toTypedArray()
        var selectedItem = 0
        val builder = AlertDialog.Builder(requireContext(),R.style.MaterialThemeDialog)
        builder.setTitle(getString(R.string.select_fav_team))
        builder.setSingleChoiceItems(options
            , 0
        ) { _: DialogInterface, item: Int ->
            selectedItem = item
        }
       builder.setPositiveButton(R.string.okkk) { dialogInterface: DialogInterface, _: Int ->
           binding.addressVal.text = options[selectedItem]
           viewModel.selectedTeamPosition = selectedItem
//           Toast.makeText(requireContext().getApplicationContext(),
//               "selected item = " + options[selectedItem], Toast.LENGTH_SHORT).show();
           dialogInterface.dismiss()
       }
       builder.setNegativeButton(R.string.cancell) { dialogInterface: DialogInterface, _: Int ->
           dialogInterface.dismiss()
       }
       builder.create()
        builder.show();
    }



    private fun isPermissionGranted(): Boolean {
        return (  ActivityCompat.checkSelfPermission(
            context!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED &&

                ActivityCompat. checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

                )



    }
    private fun requestPermeation() = requestPermissions(
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE),
        STORAGE_REQUEST_CODE
    )
    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        if (intent.resolveActivity(context?.packageManager!!) != null) {
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {

        try {
            super.onActivityResult(requestCode, resultCode, intent)

            if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && intent != null) {

                val realPath : String = getRealPathFromURI(intent.data!!)
                ImageCompression(requireContext(), realPath, object : ImageCompressionListener {
                    override fun onStart() {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onCompressed(filePath: String) {

                        val bitmap : Bitmap = BitmapFactory.decodeFile(filePath)


                            Glide.with(binding.profileImage)
                                .load(bitmap)
                               // .placeholder(R.drawable.profile_ic)
                                //.error(R.drawable.profile_ic)
                                .circleCrop()
                                .into(binding.profileImage)


                        //  binding.personPhoto.loadCircleImage(bitmap)
                        val baseImageString = bitmap.toStringBase()

                        // Todo done here
                        //val prefs = PrefManager
                     //   val user = prefs.getUser()
                       // val lang =prefs.getLanguage()
                        //  imageString = baseImageString
                        viewModel.ChangeImage(baseImageString)
                        //    performUploadImage(userInfo?.accessToken,baseImageString,bitmap )

                    }

                } ).execute()


            }
        } catch (e: Exception) {

        }
    }
    fun getRealPathFromURI(contentUri: Uri) :String {
        val proj : Array<String> =Array(1) { MediaStore.Audio.Media.DATA}
        val  cursor : Cursor? = context?.contentResolver?.query(contentUri, proj, null, null, null)



        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor?.moveToFirst()
        return cursor?.getString(columnIndex!!)!!
    }

}
