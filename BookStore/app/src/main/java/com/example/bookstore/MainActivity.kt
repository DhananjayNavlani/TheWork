package com.example.bookstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookstore.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pref
        get() = getSharedPreferences(getString(R.string.preference_file), MODE_PRIVATE)
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val config = RealmConfiguration.Builder(setOf(Author::class,Books::class)).name("bookstore").build()
        realm = Realm.open(config)

        val isFirstRun = pref.getBoolean("isFirstRun",true)
        if(isFirstRun){
//            pref.edit().putBoolean("isFirstRun",false).apply()

            /*
                        CoroutineScope(Dispatchers.IO).launch {
                            realm.write{
                                val bookListAmitav = listOf<Books>(
                                    createBook(
                                        bTitle = "Sea of Poppies",
                                        bGenre = "Novel",
                                        pubName = "Viking Press (India)",
                                        pubDate = "14 October 2008",
                                        bPrice = 319.0,
                                        bIsbn = "978-0-374-17422-4",
                                        imageUrl = R.string.imageUrl_sea_of_poppies,
                                        bSynopsis = R.string.synopsis_sea_of_poppies
                                    ),
                                    createBook(
                                        "The Hungry Tide",
                                        "Novel",
                                        "HarperCollins",
                                        "2005",
                                        295.0,
                                        "0-00-714178-5",
                                        R.string.imageUrl_the_hungry_tide,
                                        R.string.synopsis_the_hungry_tide
                                    ),
                                    createBook(
                                        "The Shadow Lines"
                                        ,"Fiction"
                                        ,"Ravi Dayal Publishers"
                                        ,"1988"
                                        ,978.0
                                        ,"81-7530-043-4"
                                        ,R.string.imageUrl_the_shadow_lines
                                        ,R.string.synopsis_the_shadow_lines
                                    ),
                                    createBook(
                                        "River of Smoke"
                                        ,"Novel"
                                        ,"Penguin Group"
                                        ,"18 June 2011"
                                        ,859.0
                                        ,"978-0-670-08215-5"
                                        ,       R.string.imageUrl_river_of_smoke
                                        ,       R.string.synopsis_river_of_smoke
                                    ),
                                    createBook(
                                        "The Great Deranegment"
                                        ,"Non-fiction"
                                        ,"Penguin Books"
                                        ,"2016"
                                        ,1999.0
                                        ,"9789386057433"
                                        ,       R.string.imageUrl_the_great_derangement
                                        ,      R.string.synopsis_the_great_derangement
                                    ),
                                    createBook(
                                        "The Glass Palace"
                                        ,"History Fiction"
                                        ,"Ravi Dayal, Penguin India"
                                        ,"2000"
                                        ,448.0
                                        ,"81-7530-0310"
                                        ,       R.string.imageUrl_the_glass_palace
                                        ,      R.string.synopsis_the_glass_palace
                                    ),
                                    createBook(
                                        "Gun Island"
                                        ,"Novel"
                                        ,"Penguin Hamish Hamilton"
                                        ,"10 June 2019"
                                        ,426.0
                                        ,"0670089168"
                                        ,       R.string.imageUrl_gun_island
                                        ,      R.string.synopsis_gun_island
                                    ),
                                    createBook(
                                        "The Nutmeg's Curse"
                                        ,  "Non-fiction"
                                        , "Penguin Allen Lane"
                                        ,"14 October 2021"
                                        ,368.0
                                        ,"0670095621"
                                        ,       R.string.imageUrl_the_nutmeg_curse
                                        ,      R.string.synopsis_the_nutmeg_curse
                                    ),
                                    createBook(
                                        "The Calcutta Chromosome"
                                        ,"Non-Thriller, speculative fiction"
                                        ,"Picador"
                                        ,"1995"
                                        ,252.0
                                        ,"0-330-34758-6"
                                        ,       R.string.imageUrl_the_calcutta_chromosome
                                        ,      R.string.synopsis_the_calcutta_chromosome
                                    ),
                                    createBook(
                                        "Flood of Fire"
                                        ,"Novel"
                                        ,"John Murray"
                                        ,"28 May 2015"
                                        ,368.0
                                        ,"978-0-670-08216-2"
                                        ,       R.string.imageUrl_flood_of_fire
                                        ,      R.string.synopsis_flood_of_fire
                                    )
                                )
                                val nameAmitavGhosh = copyToRealm(
                                    createAuthor(
                                        "Amitav Ghosh",
                                        "India",
                                        R.string.bio_amitav_ghosh,
                                        "11 July 1956",
                                        R.string.imageUrl_amitav_ghosh,
                                        bookListAmitav
                                    )
                                )

                                val bookListSalman = listOf<Books>(
                                    createBook(                                     "The Satanic Verses"
                                        ,"Magic realism"
                                        ,"Viking Penguin"
                                        ,"September 26, 1988"
                                        ,1056.07
                                        ,"0-670-82537-9"
                                        ,       R.string.imageUrl_the_satanic_verse
                                        ,      R.string.synopsis_the_satanic_verse),
                                    createBook(                                     "Midnight's Children"
                                        ,"Magic realism, historiographic metafiction"
                                        ,"Jonathan Cape"
                                        ,"1981"
                                        ,429.0
                                        ,"0-224-01823-X"
                                        ,       R.string.imageUrl_midnight_children
                                        ,      R.string.synopsis_midnight_children),
                                    createBook(                                   "Victory City"
                                        , "Literary Fiction, Science Fiction & Fantasy"
                                        ,"Random House"
                                        ,"7 February 2023"
                                        ,446.0
                                        ,"9780593243398"
                                        ,       R.string.imageUrl_victory_city
                                        ,      R.string.synopsis_victory_city),
                                    createBook(                                   "Shame"
                                        ,"Magic realism"
                                        ,"Jonathan Cape"
                                        ,"8 September 1983"
                                        ,1247.0
                                        ,"978-0-224-02952-0"
                                        ,       R.string.imageUrl_shame
                                        ,      R.string.synopsis_shame),
                                    createBook(                                    "Haroun and the Sea of Stories"
                                        ,"Magic realism"
                                        ,"Granta"
                                        ,"27 September 1990"
                                        ,190.50
                                        ,"978-0-14-014223-5"
                                        ,       R.string.imageUrl_haroun
                                        ,      R.string.synopsis_haroun),
                                    createBook(                                  "The Enchantress of Florence"
                                        ,"Novel"
                                        ,"Random House"
                                        ,"April 11, 2008"
                                        ,950.0
                                        ,"0-375-50433-8"
                                        ,       R.string.imageUrl_the_enchantress_of_florence
                                        ,      R.string.synopsis_enchantress_of_florence),
                                    createBook(                                  "Quichotte"
                                        ,"Novel"
                                        ,"Johnathan Cape"
                                        ,"29 August 2019"
                                        ,291.48
                                        ,"0670089168"
                                        ,       R.string.imageUrl_quichotte
                                        ,      R.string.synopsis_quichotte),
                                    createBook(                                  "Imaginary Homelands"
                                        ,"Essays"
                                        ,"Penguin Books"
                                        ,"1 May 1992"
                                        ,1350.0
                                        ,"0140140360"
                                        ,       R.string.imageUrl_imaginary_homelands
                                        ,      R.string.synopsis_imaginary_homelands),
                                    createBook(                                  "Luka and the Fire of Life"
                                        ,"Fiction"
                                        , "Random House"
                                        ,"20 September 2010"
                                        ,360.0
                                        ,"0679783474"
                                        ,       R.string.imageUrl_luka_and_fire_of_life
                                        ,      R.string.synopsis_luka_and_fire_of_life),
                                    createBook(                                    "Fight Of The Century"
                                        , "Law Material"
                                        ,"Avid Reader Press / Simon & Schuster"
                                        ,"21 January 2020"
                                        ,368.0
                                        ,"1501190407"
                                        ,       R.string.imageUrl_fightOfCentury
                                        ,      R.string.synopsis_fightOfCentury)
                                )
                                val nameSalmanRushdie = copyToRealm(
                                    createAuthor(
                                        "Salman Rushdie"
                                        , "United Kingdom"
                                        ,       R.string.bio_salman_rushdie
                                        ,"19 June 1947"
                                        ,R.string.imageUrl_salman_rushdie,
                                        bookListSalman
                                    )
                                )

                                val bookListMary = listOf<Books>(
                                    createBook(
                                        "Local Woman Missing"
                                        ," Thriller, Suspense"
                                        ,"Park Row"
                                        ,"18 May 2021"
                                        ,1941.0
                                        ,"0778389448"
                                        ,       R.string.imageUrl_local_woman_missing
                                        ,      R.string.synopsis_local_woman_missing
                                    ),
                                    createBook(
                                        "The Good Girl"
                                        ,"Thriller, Suspense"
                                        ,"MIRA"
                                        ,"30 May 2017"
                                        ,1242.46
                                        ,"0778319253"
                                        ,       R.string.imageUrl_the_good_girl
                                        ,      R.string.synopsis_the_good_girl
                                    ),
                                    createBook(
                                        "Just the Nicest Couple"
                                        ,"Thriller, Suspense, Psychological thriller"
                                        ,"Park Row"
                                        ,"10 January 2023"
                                        ,1825.0
                                        ,"0778333116"
                                        ,       R.string.imageUrl_just_nicest_couple
                                        ,      R.string.synopsis_just_nicest_couple
                                    ),
                                    createBook(
                                        "The Other Mrs"
                                        ,"Thriller, Suspense, Novel"
                                        ,"Park Row"
                                        ,"8 February 2020"
                                        ,1740.0
                                        ,"0778369110"
                                        ,       R.string.imageUrl_the_other_mrs
                                        ,      R.string.synopsis_the_other_mrs
                                    ),
                                    createBook(
                                        "Pretty Baby"
                                        ,"Thriller, Suspense, Novel"
                                        ,"MIRA"
                                        ,"2 February 2016"
                                        ,985.0
                                        ,"0778318745"
                                        ,       R.string.imageUrl_pretty_baby
                                        ,      R.string.synopsis_pretty_baby
                                    ),
                                    createBook(
                                        "Every Last Lie"
                                        ,"Suspense Novel"
                                        ,"Park Row"
                                        ,"27 June 2017"
                                        ,2253.0
                                        ,"0778319989"
                                        ,       R.string.imageUrl_every_last_lie
                                        ,      R.string.synopsis_every_last_lie
                                    ),
                                    createBook(
                                        "Don't You Cry"
                                        ,"Thriller, Suspense, Romance Novel"
                                        , "Mira Books"
                                        ,"10 January 2017"
                                        ,1170.0
                                        ,"0778330516"
                                        ,       R.string.imageUrl_dont_you_cry
                                        ,      R.string.synopsis_dont_you_cry
                                    ),
                                    createBook(
                                        "When the Lights Go Out"
                                        ,"Mystery, Fiction"
                                        ,"Park Row"
                                        ,"4 September 2018"
                                        ,4346.0
                                        ,"9780778330783"
                                        ,       R.string.imageUrl_when_lights_go_out
                                        ,      R.string.synopsis_when_lights_go_out
                                    )
                                )
                                val nameMary = copyToRealm(
                                    createAuthor(
                                        "Mary Kubinca",
                                        "United States",
                                        R.string.bio_mary_kubica,
                                        "February 1978",
                                        R.string.imageUrl_mary_kubica,
                                        bookListMary
                                    )
                                )

                            }
                        }
            */

/*            val bookListSalman = listOf(
                createBook(                                     "The Satanic Verses"
                    ,"Magic realism"
                    ,"Viking Penguin"
                    ,"September 26, 1988"
                    ,1056.07
                    ,"0-670-82537-9"
                    ,       R.string.imageUrl_the_satanic_verse
                    ,      R.string.synopsis_the_satanic_verse),
                createBook(                                     "Midnight's Children"
                    ,"Magic realism, historiographic metafiction"
                    ,"Jonathan Cape"
                    ,"1981"
                    ,429.0
                    ,"0-224-01823-X"
                    ,       R.string.imageUrl_midnight_children
                    ,      R.string.synopsis_midnight_children),
                createBook(                                   "Victory City"
                    , "Literary Fiction, Science Fiction & Fantasy"
                    ,"Random House"
                    ,"7 February 2023"
                    ,446.0
                    ,"9780593243398"
                    ,       R.string.imageUrl_victory_city
                    ,      R.string.synopsis_victory_city),
                createBook(                                   "Shame"
                    ,"Magic realism"
                    ,"Jonathan Cape"
                    ,"8 September 1983"
                    ,1247.0
                    ,"978-0-224-02952-0"
                    ,       R.string.imageUrl_shame
                    ,      R.string.synopsis_shame),
                createBook(                                    "Haroun and the Sea of Stories"
                    ,"Magic realism"
                    ,"Granta"
                    ,"27 September 1990"
                    ,190.50
                    ,"978-0-14-014223-5"
                    ,       R.string.imageUrl_haroun
                    ,      R.string.synopsis_haroun),
                createBook(                                  "The Enchantress of Florence"
                    ,"Novel"
                    ,"Random House"
                    ,"April 11, 2008"
                    ,950.0
                    ,"0-375-50433-8"
                    ,       R.string.imageUrl_the_enchantress_of_florence
                    ,      R.string.synopsis_enchantress_of_florence),
                createBook(                                  "Quichotte"
                    ,"Novel"
                    ,"Johnathan Cape"
                    ,"29 August 2019"
                    ,291.48
                    ,"0670089168"
                    ,       R.string.imageUrl_quichotte
                    ,      R.string.synopsis_quichotte),
                createBook(                                  "Imaginary Homelands"
                    ,"Essays"
                    ,"Penguin Books"
                    ,"1 May 1992"
                    ,1350.0
                    ,"0140140360"
                    ,       R.string.imageUrl_imaginary_homelands
                    ,      R.string.synopsis_imaginary_homelands),
                createBook(                                  "Luka and the Fire of Life"
                    ,"Fiction"
                    , "Random House"
                    ,"20 September 2010"
                    ,360.0
                    ,"0679783474"
                    ,       R.string.imageUrl_luka_and_fire_of_life
                    ,      R.string.synopsis_luka_and_fire_of_life),
                createBook(                                    "Fight Of The Century"
                    , "Law Material"
                    ,"Avid Reader Press / Simon & Schuster"
                    ,"21 January 2020"
                    ,368.0
                    ,"1501190407"
                    ,       R.string.imageUrl_fightOfCentury
                    ,      R.string.synopsis_fightOfCentury)
            )
            val bookListMary = listOf<Books>(
                createBook(
                    "Local Woman Missing"
                    ," Thriller, Suspense"
                    ,"Park Row"
                    ,"18 May 2021"
                    ,1941.0
                    ,"0778389448"
                    ,       R.string.imageUrl_local_woman_missing
                    ,      R.string.synopsis_local_woman_missing
                ),
                createBook(
                    "The Good Girl"
                    ,"Thriller, Suspense"
                    ,"MIRA"
                    ,"30 May 2017"
                    ,1242.46
                    ,"0778319253"
                    ,       R.string.imageUrl_the_good_girl
                    ,      R.string.synopsis_the_good_girl
                ),
                createBook(
                    "Just the Nicest Couple"
                    ,"Thriller, Suspense, Psychological thriller"
                    ,"Park Row"
                    ,"10 January 2023"
                    ,1825.0
                    ,"0778333116"
                    ,       R.string.imageUrl_just_nicest_couple
                    ,      R.string.synopsis_just_nicest_couple
                ),
                createBook(
                    "The Other Mrs"
                    ,"Thriller, Suspense, Novel"
                    ,"Park Row"
                    ,"8 February 2020"
                    ,1740.0
                    ,"0778369110"
                    ,       R.string.imageUrl_the_other_mrs
                    ,      R.string.synopsis_the_other_mrs
                ),
                createBook(
                    "Pretty Baby"
                    ,"Thriller, Suspense, Novel"
                    ,"MIRA"
                    ,"2 February 2016"
                    ,985.0
                    ,"0778318745"
                    ,       R.string.imageUrl_pretty_baby
                    ,      R.string.synopsis_pretty_baby
                ),
                createBook(
                    "Every Last Lie"
                    ,"Suspense Novel"
                    ,"Park Row"
                    ,"27 June 2017"
                    ,2253.0
                    ,"0778319989"
                    ,       R.string.imageUrl_every_last_lie
                    ,      R.string.synopsis_every_last_lie
                ),
                createBook(
                    "Don't You Cry"
                    ,"Thriller, Suspense, Romance Novel"
                    , "Mira Books"
                    ,"10 January 2017"
                    ,1170.0
                    ,"0778330516"
                    ,       R.string.imageUrl_dont_you_cry
                    ,      R.string.synopsis_dont_you_cry
                ),
                createBook(
                    "When the Lights Go Out"
                    ,"Mystery, Fiction"
                    ,"Park Row"
                    ,"4 September 2018"
                    ,4346.0
                    ,"9780778330783"
                    ,       R.string.imageUrl_when_lights_go_out
                    ,      R.string.synopsis_when_lights_go_out
                )
            )*/

            realm.writeBlocking {
 /*               val managedBookListAmitav = mutableListOf<Books>()
                for(book in bookListamitav){
                    book.id = nextBookId()
                    val managedBook = copyToRealm(book)
                    managedBookListAmitav.add(managedBook)
                }*/
                val bookListamitav = listOf(         copyToRealm(createBook(
                    "Sea of Poppies",
                    "Novel",
                    "Viking Press (India)",
                    "14 October 2008",
                    319.0,
                    "978-0-374-17422-4",
                    R.string.imageUrl_sea_of_poppies,
                    R.string.synopsis_sea_of_poppies
                )),
                    copyToRealm(createBook(
                        "The Hungry Tide",
                        "Novel",
                        "HarperCollins",
                        "2005",
                        295.0,
                        "0-00-714178-5",
                        R.string.imageUrl_the_hungry_tide,
                        R.string.synopsis_the_hungry_tide
                    )),
                    copyToRealm(createBook(
                        "The Shadow Lines"
                        ,"Fiction"
                        ,"Ravi Dayal Publishers"
                        ,"1988"
                        ,978.0
                        ,"81-7530-043-4"
                        ,R.string.imageUrl_the_shadow_lines
                        ,R.string.synopsis_the_shadow_lines
                    )),
                    copyToRealm(createBook(
                        "River of Smoke"
                        ,"Novel"
                        ,"Penguin Group"
                        ,"18 June 2011"
                        ,859.0
                        ,"978-0-670-08215-5"
                        ,       R.string.imageUrl_river_of_smoke
                        ,       R.string.synopsis_river_of_smoke
                    )),
                    copyToRealm(createBook(
                        "The Great Deranegment"
                        ,"Non-fiction"
                        ,"Penguin Books"
                        ,"2016"
                        ,1999.0
                        ,"9789386057433"
                        ,       R.string.imageUrl_the_great_derangement
                        ,      R.string.synopsis_the_great_derangement
                    )),
                    copyToRealm(createBook(
                        "The Glass Palace"
                        ,"History Fiction"
                        ,"Ravi Dayal, Penguin India"
                        ,"2000"
                        ,448.0
                        ,"81-7530-0310"
                        ,       R.string.imageUrl_the_glass_palace
                        ,      R.string.synopsis_the_glass_palace
                    )),
                    copyToRealm(createBook(
                        "Gun Island"
                        ,"Novel"
                        ,"Penguin Hamish Hamilton"
                        ,"10 June 2019"
                        ,426.0
                        ,"0670089168"
                        ,       R.string.imageUrl_gun_island
                        ,      R.string.synopsis_gun_island
                    )),
                    copyToRealm(createBook(
                        "The Nutmeg's Curse"
                        ,  "Non-fiction"
                        , "Penguin Allen Lane"
                        ,"14 October 2021"
                        ,368.0
                        ,"0670095621"
                        ,       R.string.imageUrl_the_nutmeg_curse
                        ,      R.string.synopsis_the_nutmeg_curse
                    )),
                    copyToRealm(createBook(
                        "The Calcutta Chromosome"
                        ,"Non-Thriller, speculative fiction"
                        ,"Picador"
                        ,"1995"
                        ,252.0
                        ,"0-330-34758-6"
                        ,       R.string.imageUrl_the_calcutta_chromosome
                        ,      R.string.synopsis_the_calcutta_chromosome
                    )),
                    copyToRealm(createBook(
                        "Flood of Fire"
                        ,"Novel"
                        ,"John Murray"
                        ,"28 May 2015"
                        ,368.0
                        ,"978-0-670-08216-2"
                        ,       R.string.imageUrl_flood_of_fire
                        ,      R.string.synopsis_flood_of_fire
                    )))


                val nameAmitavGhosh = copyToRealm(
                    Author().apply{
                        _id = nextAuthorId()
                        authorName = "Amitav Ghosh"
                        authorCitizenship = "India"
                        authorBio = getString(R.string.bio_amitav_ghosh)
                        authorDob = "11 July 1956"
                        aimageUrl = getString(R.string.imageUrl_amitav_ghosh)
                        booklist.addAll(bookListamitav)
                    }
                )

/*
                val nameSalmanRushdie = copyToRealm(
                    createAuthor(
                        "Salman Rushdie"
                        , "United Kingdom"
                        ,       R.string.bio_salman_rushdie
                        ,"19 June 1947"
                        ,R.string.imageUrl_salman_rushdie,
                        bookListSalman
                    )
                )

                val nameMary = copyToRealm(
                    createAuthor(
                        "Mary Kubinca",
                        "United States",
                        R.string.bio_mary_kubica,
                        "February 1978",
                        R.string.imageUrl_mary_kubica,
                        bookListMary
                    )
                )
*/
            }
        }


        val authorsList = realm.query<Author>().find()
        val screenSlideAdapter = ScreenSlideAdapter(this)
        binding.apply {
            viewpager.adapter = screenSlideAdapter
            TabLayoutMediator(tabLayout,viewpager){
                tab, position ->
                for(author in authorsList)
                    tab.text = "${author._id}"
                if(authorsList.isEmpty()) tab.text = "Hi: ${position+1}"
            }.attach()
        }


    }

    private fun createAuthor(name: String, citizenShip: String, bio: Int, dob: String,imageUrl: Int, list: List<Books> ,aDied: String? = null): Author{
        val result = Author()
        result.apply{
            _id = nextAuthorId()
            authorName = name
            authorCitizenship = citizenShip
            authorBio = getString(bio)
            authorDob = dob
            aimageUrl = getString(imageUrl)
            authorDied = aDied
            booklist.addAll(list)
        }
        return result
    }

    private fun createBook(bTitle: String, bGenre: String, pubName: String, pubDate: String, bPrice: Double, bIsbn: String, imageUrl: Int, bSynopsis: Int): Books {
/*
        var result: Books? = null
        realm.writeBlocking {
            result = copyToRealm(
                 Books().apply {
                id = nextBookId()
                title = bTitle
                genre = bGenre
                publisherName = pubName
                publishDate = pubDate
                price = bPrice
                isbn = bIsbn
                bimageUrl = getString(imageUrl)
                synopsis = getString(bSynopsis)
            }
            )
        }

        return result ?: Books()
*/

        val result = Books().apply {
//                id = nextBookId()
                title = bTitle
                genre = bGenre
                publisherName = pubName
                publishDate = pubDate
                price = bPrice
                isbn = bIsbn
                bimageUrl = getString(imageUrl)
                synopsis = getString(bSynopsis) }

        return result
    }

    private fun nextBookId(): Int {
        val id: Int
        try {
            val res = realm.query<Books>().find()
            id = res.maxOf { it.id } + 1
        }catch (ex: NoSuchElementException){
            return 1
        }
        return id
    }

    private fun nextAuthorId(): Int {
        val id: Int
        try {
            val res = realm.query<Author>().find()
            id = res.maxOf { it._id } + 1
        }catch (ex: NoSuchElementException){
            return 1
        }
        return id
    }

    private inner class ScreenSlideAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
        override fun getItemCount(): Int {
            val count: Int
            try{
                count = realm.query<Author>().find().count()
            }catch (ex: Exception){
                return 14
            }
            return count
        }

        override fun createFragment(position: Int): Fragment {
            return ScreenSlideFragment.newInstance(position)
        }

    }
}