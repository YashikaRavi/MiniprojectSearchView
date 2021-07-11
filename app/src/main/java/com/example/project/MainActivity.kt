package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.project.databinding.ActivityMainBinding
import java.io.IOException
import retrofit2.HttpException
import androidx.recyclerview.widget.LinearLayoutManager

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var people: ArrayList<Albumdataformat> = arrayListOf()
    private var matchedPeople: ArrayList<Albumdataformat> = arrayListOf()
    private var userAdapter: AlbumAdapt = AlbumAdapt(people)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                Retrofit.API.getTodos()
            } catch (e: IOException) {
                Log.e(TAG, "NO INTERNET CONNECTION")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                userAdapter.album = response.body()!!
                people = response.body()!!
            } else {
                Log.e(TAG, "Response not success")
            }
            binding.progressBar.isVisible = false
        }
        setupRecyclerView()
        performSearch()
    }
    private fun setupRecyclerView()=binding.rvTodos.apply {
        userAdapter=AlbumAdapt(people).also {
            binding.rvTodos.adapter = it
            binding.rvTodos.adapter!!.notifyDataSetChanged()
        }
        binding.searchView.isSubmitButtonEnabled = true
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
    private fun performSearch() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true

            }
        })
    }
    private fun search(text: String?) {
        matchedPeople = arrayListOf()

        text?.let {
            people.forEach { person ->
                if (person.id.contains(text, true) || person.title.contains(text, true) ) {
                    matchedPeople.add(person)
                }
            }
            updateRecyclerView()
            if (matchedPeople.isEmpty()) {
                Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show()
            }
            updateRecyclerView()
        }
    }
    private fun updateRecyclerView() {
        binding.rvTodos.apply {
            userAdapter.album = matchedPeople
            userAdapter.notifyDataSetChanged()
        }
    }

}
