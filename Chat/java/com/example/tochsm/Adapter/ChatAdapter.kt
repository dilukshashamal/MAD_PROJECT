package com.example.tochsm.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tochsm.Fragments.InboxFragment
import com.example.tochsm.Fragments.ProfileFragment
import com.example.tochsm.Model.Chat
import com.example.tochsm.Model.User
import com.example.tochsm.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter(private var mContext: Context,
                  private var CList:List<Chat>,
                  private var isFragment: Boolean= false) : RecyclerView.Adapter<ChatAdapter.ViewHolder>()
{
    private var firebaseUser: FirebaseUser? = null
    private var MESSAGE_TYPE_LEFT= 0
    private var MESSAGE_TYPE_RIGHT= 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == MESSAGE_TYPE_RIGHT){
            val view = LayoutInflater.from(mContext).inflate(R.layout.inbox_item_right,parent,false)
            return ViewHolder(view)
        }else{
            val view = LayoutInflater.from(mContext).inflate(R.layout.inbox_item_left,parent,false)
            return ViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return CList.size
    }


    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var message: TextView = itemView.findViewById(R.id.tv_message)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = CList[position]
        holder.message.text = chat.getMessage()

    }

    override fun getItemViewType(position: Int): Int{
        firebaseUser=FirebaseAuth.getInstance().currentUser
        if(CList[position].getSenderId()== firebaseUser!!.uid ){
            return MESSAGE_TYPE_RIGHT
        }else{
            return MESSAGE_TYPE_LEFT
        }
    }

}