package com.example.facebookposts.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.facebookposts.model.PostsModel
import com.example.facebookposts.R
import com.example.facebookposts.adapter.PostsAdapter
import com.example.facebookposts.model.PostModel
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONException

class HomeActivity : AppCompatActivity() {

    private val TAG = HomeActivity::class.java.simpleName
    private lateinit var posts: MutableList<PostsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        posts = ArrayList()
        fetchPosts()
        post_recycler!!.layoutManager = LinearLayoutManager(this@HomeActivity)
        post_recycler!!.adapter = PostsAdapter(this@HomeActivity, posts)
    }

    private fun fetchPosts() {
        val request = GraphRequest.newGraphPathRequest(
            AccessToken.getCurrentAccessToken(),
            "/335551787287211/posts"
        ) { response ->
            try {
                val postsParent = response.getJSONObject()
                val postsArray = postsParent.getJSONArray("data")
                for (i in 0 until postsArray.length()) {
                    val postObj = postsArray.getJSONObject(i)
                    posts.add(
                        PostsModel(
                            postObj.optString("id"),
                            postObj.optString("message"),
                            postObj.optString("full_picture")
                        )
                    )
                }
                post_recycler.adapter!!.notifyDataSetChanged()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,message,full_picture,message_tags,story,story_tags")
        request.parameters = parameters
        request.executeAsync()
    }
}