package com.example.dynamiclayout

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.ls.LSInput
import java.util.Locale

const val margin:Int =16

//extension property
val Int.pixel:Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

class MainActivity : AppCompatActivity() {
    private var questions:MutableList<Question> = mutableListOf()
    private lateinit var quiz_container:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quiz_container = findViewById(R.id.quiz_container)
        setupQuestions()
        setupQuiz()
        setupSubmitButton()
    }

    private fun setupSubmitButton() {
        var bt = Button(this)

        bt.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { gravity = Gravity.CENTER_HORIZONTAL }.apply { topMargin = margin.pixel }
        bt.text = "Submit"
        bt.setOnClickListener {
            evalQuiz()
        }
        quiz_container.addView(bt)
    }

    private fun evalQuiz(){
        var score =0
        questions.forEach { q->
            when(q.type){
                QuestionType.Text ->{
                    val edtText = quiz_container.findViewById<EditText>(q.id)

                    edtText?.let {
                        val userAns = it.text.toString().trim().lowercase(Locale.getDefault())
                        Log.d("text",userAns + q.answers[0])
                        if(userAns.contentEquals(q.answers[0])){
                            score++
                            Log.d("RESP","$score")
                        }
                    }
                }

                QuestionType.Radio->{
                    val radioGroup = quiz_container.findViewById<RadioGroup>(q.id)

                    radioGroup?.let {
                        val checkedId = it.checkedRadioButtonId
                        if(checkedId>0){
                            val rb = quiz_container.findViewById<RadioButton>(checkedId)
                            val userAns = rb.text
                            if(userAns == q.answers[0]){
                                score++
                            }
                        }
                    }
                }

                QuestionType.CheckBox->{
                    var correct =true

                    q.options?.forEachIndexed{index, s->
                        val checkId = (q.id.toString()+index.toString()).toInt()
                        val checkBox = quiz_container.findViewById<CheckBox>(checkId)

                        if(q.answers.contains(checkBox.text)){
                            if(!checkBox.isChecked){
                                correct =false
                            }
                        }else{
                            if(checkBox.isChecked)
                                correct = false
                        }
                    }
                    if(correct) score++
                }
            }
        }
        Toast.makeText(this@MainActivity,"You scored $score out of ${questions.size} ",Toast.LENGTH_LONG).show()
    }

    private fun setupQuestions() {
        questions.add(Question(1,QuestionType.Text,"What is capital of Nagaland ?",null, listOf("kohima")))
        questions.add(
            Question(2,QuestionType.Radio,"Which is the largest(area) state of of India?",listOf("Bihar","MP","UP","Rajasthan"),
                listOf("Rajasthan")))

        questions.add(
            Question(3,QuestionType.CheckBox,"Which of these are state capitals?",listOf("Guwahati","Chennai","Varanasi","Dispur"),listOf("Chennai","Dispur")))

    }

    private fun setupQuiz(){
        questions.forEachIndexed { index, question ->
            when(question.type){
                QuestionType.Text->{
                    setupTextQuestion(index,question)
                }
                QuestionType.Radio->{
                    setupRadioQuestion(index,question)
                }
                QuestionType.CheckBox->{
                    setupCheckBoxQuestion(index,question)
                }
            }
        }
    }

    private fun setupCheckBoxQuestion(index: Int, question: Question) {
        val tv = getQuestionTextView(index,question.qText)
        quiz_container.addView(tv)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        question.options?.forEachIndexed { index, s ->
            val checkbox = CheckBox(this)
            checkbox.id = (question.id.toString()+index.toString()).toInt()
            checkbox.text = s
            checkbox.layoutParams = params
            quiz_container.addView(checkbox)
        }
    }

    private fun setupRadioQuestion(index: Int, question: Question) {
        val tv = getQuestionTextView(index,question.qText)

        val radioGroup = RadioGroup(this)
        radioGroup.id = question.id
        radioGroup.orientation = RadioGroup.VERTICAL

        radioGroup.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        question.options?.forEachIndexed { index, s ->
            val rb = RadioButton(this)
            rb.text= s
            rb.id = (question.id.toString() + index.toString()).toInt()
            radioGroup.addView(rb)
        }
        quiz_container.addView(tv)
        quiz_container.addView(radioGroup)

    }

    private fun setupTextQuestion(counter: Int, question: Question) {
        val tv = getQuestionTextView(counter,question.qText)

        val edittext = EditText(this)
        edittext.id = question.id
        edittext.isSingleLine = true
        edittext.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        quiz_container.addView(tv)
        quiz_container.addView(edittext)

    }

    private fun getQuestionTextView(counter: Int, qText: String): TextView {
        val tv = TextView(this)
        tv.text = "Q${counter}. ${qText}"

        tv.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { topMargin= margin.pixel }

        return tv
    }

}