package com.example.bookstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.bookstore.databinding.ActivityMainBinding
import com.example.bookstore.databinding.FragmentScreenSlideBinding
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScreenSlideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScreenSlideFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null
    private lateinit var binding: FragmentScreenSlideBinding
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val config = RealmConfiguration.Builder(setOf(Author::class,Books::class)).name("bookstore").build()
        realm = Realm.open(config)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_screen_slide, container, false)
        binding = FragmentScreenSlideBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val author = realm.query<Author>("_id == $param1").first().find()
        val bookList = author?.booklist?.toList()
        binding.apply {
            author?.run {
                Glide.with(this@ScreenSlideFragment)
                    .load(aimageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageAuthor)

                textAuthorName.text = authorName
                textDobAuthor.text = authorDob
                textCitizenshipAuthor.text = authorCitizenship
                textAboutAuthor.text = authorBio

                authorDied?.let {
                    textAuthorDeathDate.text = it
                    labelAuthorDied.visibility = View.VISIBLE
                    textAuthorDeathDate.visibility = View.VISIBLE
                }
            }

            with(booksRv){
                layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                adapter = bookList?.let { BookAdapter(requireActivity(),it) }
                setHasFixedSize(true)
            }
        }
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            ScreenSlideFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}