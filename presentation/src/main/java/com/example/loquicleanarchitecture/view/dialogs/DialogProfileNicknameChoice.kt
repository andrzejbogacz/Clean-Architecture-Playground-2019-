package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainViewModel
import javax.inject.Inject

class DialogProfileNicknameChoice : DialogFragment() {

    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: MainViewModel

    internal lateinit var listener: NicknameListener

    interface NicknameListener {
        fun applyNickname(nickname: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mainViewModel = ViewModelProvider(activity!!.viewModelStore, viewModelFactory).get(MainViewModel::class.java)

        val userData = mainViewModel.getUserDataLiveData().value!!

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_profile_nickname_choice, null)
        val ed_nickname = view.findViewById<EditText>(R.id.editText_profile_nickname_value)


        return activity?.let {
            // Use the Builder class for convenient dialog construction

            //Todo
            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.profile_dialog_nickname_title)
                .setPositiveButton(R.string.confirm,
                    { dialog, id ->
                        listener.applyNickname(ed_nickname.text.toString())
                    })
                .setNegativeButton(R.string.cancel,
                    { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NicknameListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }

}