package com.example.tochsm.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tochsm.Adapter.ChatAdapter
import com.example.tochsm.Adapter.UserAdapter2
import com.example.tochsm.Model.Chat
import com.example.tochsm.Model.User
import com.example.tochsm.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_chat_layout.view.*
import kotlinx.android.synthetic.main.user_inbox.*
import kotlinx.android.synthetic.main.user_inbox.view.*
import kotlinx.coroutines.NonCancellable.children
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InboxFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var profileId: String
    private lateinit var firebaseUser: FirebaseUser
    //private var CList= ArrayList<Chat>()
    private var recyclerView: RecyclerView? = null
    private var chatAdapter: ChatAdapter? = null
    private var CList: MutableList<Chat>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.user_inbox, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_inbox)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        CList= ArrayList()
        chatAdapter = context?.let { ChatAdapter(it,CList as ArrayList<Chat>, true) }
        recyclerView?.adapter = chatAdapter
        firebaseUser =  FirebaseAuth.getInstance().currentUser!!

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if(pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }

        view.back_btn.setOnClickListener {
            val PFragment= ChatFragment()
            val Ptransaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
            Ptransaction.add(R.id.fragment_container,PFragment)
            Ptransaction.commit()
        }

        view.send_btn.setOnClickListener {
            var message: String = message_box.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(activity, "message is empty!", Toast.LENGTH_SHORT).show()
            } else {
                sendMessage(firebaseUser.uid, profileId, message)
                message_box.text.clear()
            }
        }

        userInfo()
        readMessage(firebaseUser.uid, profileId)

        return view
    }

    private fun userInfo()
    {
        val usersRef = FirebaseDatabase.getInstance().reference.child("Users").child(profileId)

        usersRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                // if(context != null){
                //    return
                //  }

                if(snapshot.exists())
                {
                    val user = snapshot.getValue<User>(User::class.java)
                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(view?.rec_profile_image)
                    view?.rec_user_name?.text = user.getUsername()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun sendMessage(senderId:String, receiverId:String, message:String ){

        var reference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        var hashMap:HashMap<String,String> = HashMap()
        hashMap.put("senderId",senderId)
        hashMap.put("receiverId",receiverId)
        hashMap.put("message",message)

        reference!!.child("Chats").push().setValue(hashMap)
    }

    private fun readMessage(senderId:String,receiverId:String ){

        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")
        ref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(datasnapshot: DataSnapshot) {
                   CList?.clear()
                    for(snapshot in datasnapshot.children)
                    {
                        val chat = snapshot.getValue(Chat::class.java)
                        if((chat!!.getSenderId().equals(senderId) && chat.getReceiverId().equals(receiverId)) ||
                            (chat.getSenderId().equals(receiverId) && chat.getReceiverId().equals(senderId)))
                        {
                            CList!!.add(chat)
                        }
                    }

                    chatAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
    override fun onStop() {
        super.onStop()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onPause() {
        super.onPause()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InboxFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}