package com.example.final_exam.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatRadioButton
import com.example.final_exam.databinding.DialogAddQuestionBinding
import com.example.final_exam.models.PossibleAnswerModel
import com.example.final_exam.models.QuestionModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata

class AddQuestionDialog : BottomSheetDialogFragment() {
    private var _binding: DialogAddQuestionBinding? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private val binding get() = _binding!!
    private var currentIndexSelected = -1
    private val answersList = mutableListOf(
            PossibleAnswerModel(),
            PossibleAnswerModel(),
            PossibleAnswerModel())
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.also {
                if (it.data != null) {
                    val type = requireContext().contentResolver.getType(it.data!!)
                    val imageRef = storageReference.child("images/${it.data?.toString()?.substringAfterLast("/")}")

                    val metaData = storageMetadata {
                        contentType = "image/$type"
                    }
                    val uploadTask = imageRef.putFile(it.data!!, metaData)
                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        imageRef.downloadUrl
                    }.addOnCompleteListener { successTask ->
                        if (successTask.isSuccessful) {
                            answersList[currentIndexSelected] = answersList[currentIndexSelected].copy(imageUrl = successTask.result.toString())
                            Toast.makeText(requireContext(), successTask.result.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogAddQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnList = listOf(binding.btnChooseImage1, binding.btnChooseImage2, binding.btnChooseImage3)
        storage = Firebase.storage
        storageReference = storage.reference



        btnList.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                currentIndexSelected = index
                val intent = Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                }

                resultLauncher.launch(Intent.createChooser(
                        intent,
                        "Choose File"
                ))


            }

        }
        binding.submit.setOnClickListener {
            onSubmitClicked()
        }
    }

    private fun onSubmitClicked() {
        val etList = listOf(binding.etAnswer1, binding.etAnswer2, binding.etAnswer3)
        val radioList = listOf(binding.radio1, binding.radio2, binding.radio3)


        if (validateAnswers(etList,radioList)) {
            if (parentFragment is CreateQuizFragment) {
                (requireParentFragment() as CreateQuizFragment).addItem(createQuestionModel(etList,radioList))
                dismissAllowingStateLoss()
            }
        } else {
            Toast.makeText(requireContext(), "Please enter all answers and correct one", Toast.LENGTH_LONG).show()
        }

    }

    private fun validateAnswers(list: List<AppCompatEditText> ,radioList:List<AppCompatRadioButton>): Boolean {

        return list.all {
           it.text.toString().isNotEmpty()
        } && radioList.any {
            it.isChecked
        }
    }

    private fun createQuestionModel(list: List<AppCompatEditText>,radioList:List<AppCompatRadioButton>): QuestionModel {
        answersList.forEachIndexed { index, possibleAnswerModel ->
            answersList[index] = answersList[index].copy(answer = list[index].text.toString())
        }
        radioList.forEachIndexed { index, appCompatRadioButton ->
            answersList[index] = answersList[index].copy(correct = appCompatRadioButton.isChecked)
        }
        return QuestionModel(
                binding.etQuestion.text.toString(),
                answersList.map {
                    it.toMap()
                }
        )
    }

    companion object {
        const val POSSIBLE_ANSWER_KEY = "POSSIBLE_ANSWER_KEY"
        fun newInstance(): AddQuestionDialog {
            return AddQuestionDialog()
        }
    }
}