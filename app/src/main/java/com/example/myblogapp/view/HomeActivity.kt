package com.example.myblogapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myblogapp.adapters.PostsAdapter
import com.example.myblogapp.databinding.ActivityHomeBinding
import com.example.myblogapp.domain.data.PreferenceManager
import com.example.myblogapp.model.Posts
import com.example.myblogapp.view.interfaces.OnPostSavedListener
import com.example.myblogapp.viewModel.PostViewModel
import com.google.firebase.Timestamp
import java.util.Date

class HomeActivity : AppCompatActivity(), OnPostSavedListener {

    private lateinit var binding: ActivityHomeBinding
    private var posts = mutableListOf<Posts>()
    private lateinit var adapter: PostsAdapter
    private val viewModel by lazy { ViewModelProvider(this)[PostViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvents()
        observeData()

    }

    override fun onPostSaved(title: String, content: String) {
        val name = PreferenceManager.getName(this).toString()
        val uid = PreferenceManager.getUID(this)
        val time = Timestamp.now()
        val post = Posts(title, name, uid, content, time)
        viewModel.sendData(post)
        observeData()
    }

    private fun observeData() {
        viewModel.fetchPostData().observe(this) {
            posts = it.sortedByDescending { post -> post.date}.toMutableList()
            initRecyclerView()
        }
    }

    private fun setEvents() {
        binding.newPostBtn.setOnClickListener {
            NewPostFragment().show(supportFragmentManager, "new post")
        }
    }

    private fun initRecyclerView() {
        adapter = PostsAdapter(posts, this)
        binding.postsRv.layoutManager = LinearLayoutManager(this)
        binding.postsRv.adapter = adapter
    }
}