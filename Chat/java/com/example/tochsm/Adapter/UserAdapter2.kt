package com.example.tochsm.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tochsm.Fragments.ChatFragment
import com.example.tochsm.Fragments.InboxFragment
import com.example.tochsm.Model.User
import com.example.tochsm.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter2(private var mContext: Context,
                   private var mUser:List<User>,
                   private var isFragment: Boolean= false) : RecyclerView.Adapter<UserAdapter2.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_chat_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = mUser[position]

        holder.userName.text = user.getUsername()
        Picasso.get().load(user.getImage()).placeholder(R.drawable.profile).into(holder.userProfile)

        holder.layoutuser.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId", user.getUid())
            pref.apply()
            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .add(android.R.id.content, InboxFragment()).commit()

        })
    }

    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        var userName: TextView = itemView.findViewById(R.id.user_name)
        var userProfile: CircleImageView = itemView.findViewById(R.id.user_profile_image)
        var layoutuser: RelativeLayout = itemView.findViewById(R.id.userlay)

    }
}