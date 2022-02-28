package com.example.tochsm

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class  SignUpActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signin_link_btn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        signup_btn.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount()
    {
        val fullName=fullname_signup.text.toString()
        val userName=username_signup.text.toString()
        val email=email_signup.text.toString()
        val password=password_signup.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this,"full name is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this,"user name is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"email is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"password is required.", Toast.LENGTH_LONG).show()

            else -> {
                val progressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setTitle("SignUp")
                progressDialog.setMessage("Please wait, this may take a while...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email , password)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful)
                        {
                            saveUserInfo(fullName ,userName ,email,progressDialog)
                        }
                        else
                        {
                            val message = task.exception!!.toString()
                            Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }

            }
        }
    }

    private fun saveUserInfo(fullName: String, userName: String, email: String, progressDialog: ProgressDialog)
    {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String,Any>()

        userMap["uid"] = currentUserID
        userMap["fullname"] = currentUserID
        userMap["username"] = currentUserID
        userMap["email"] = currentUserID
        userMap["bio"] = "hey I am shamal."
        userMap["image"] = "gs://tochsm-cb15f.appspot.com/Default Images/profile.png"
       userRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if(task.isSuccessful)
                {
                    progressDialog.dismiss()
                    Toast.makeText(this,"Account has been created successfully.", Toast.LENGTH_LONG).show()

                    val intent = Intent(this@SignUpActivity,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    val message = task.exception!!.toString()
                    Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }
}