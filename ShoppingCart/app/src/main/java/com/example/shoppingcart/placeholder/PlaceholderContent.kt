package com.example.shoppingcart.placeholder

import com.example.shoppingcart.R
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<Int, PlaceholderItem> = HashMap()


    init {
        // Add some sample items.
        addItem(PlaceholderItem(1, "Pixel 3a", 45000F, R.drawable.pixel3,
            "Meet Pixel 3a. Premium. For less.",
            "Save your photos and videos with free, unlimited storage in high quality with Google Photos."))
        addItem(PlaceholderItem(2, "Google Home", 7000F, R.drawable.google_home,
            "Help is here. Meet Google Home.",
            "Get answers, play songs, tackle your day, enjoy your entertainment and control your smart home with just your voice."))
        addItem(PlaceholderItem(3, "Pixel Stand", 6500F, R.drawable.pixel_stand,
            "Fast, wireless charging.",
            "Charge your Pixel wirelessly with Pixel Stand, and do more with your Google Assistant."))
        addItem(PlaceholderItem(4, "Chromecast", 3500F, R.drawable.chromecast,
            "Stream from your device to your TV.",
            "Enjoy hundreds of Android or iPhone apps2, and play or pause directly from your phone."))

    }

    private fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

/*
    private fun createPlaceholderItem(position: Int): PlaceholderItem {
        return PlaceholderItem(position ,"Pixel 3a " + position, makeDetails(position))
    }
*/
/*

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }
*/

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(val id: Int, val name: String,
                               var price: Float, val imageId: Int, val shortDescription: String, val longDescription: String, var rating: Int = 0,var discountApplied: Boolean = false) {
//        override fun toString(): String = content
    }
}