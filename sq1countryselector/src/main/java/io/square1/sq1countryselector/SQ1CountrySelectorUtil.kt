package io.square1.sq1countryselector

import android.content.Context
import com.google.gson.Gson
import io.square1.sq1countryselector.model.CountryList
import io.square1.sq1countryselector.model.CountryModel
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class SQ1CountrySelectorUtil(context: Context) {

    private lateinit var countryModel: CountryList

    fun getAllCountries(context: Context): ArrayList<CountryModel> {
        return parseCountries(context)
    }

    private fun parseCountries(context: Context): ArrayList<CountryModel> {
        val myJson = inputStreamToString(context.resources.openRawResource(R.raw.countries))
        countryModel = Gson().fromJson(myJson, CountryList::class.java)
        for (country in countryModel.countries) {
            country.name = getCountryName(country.alpha2Code)
        }
        countryModel.countries.sortWith(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
        return countryModel.countries
    }

    private fun getCountryName(code: String): String {
        val loc = Locale("", code)
        return loc.displayCountry
    }

    private fun inputStreamToString(inputStream: InputStream): String {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            ""
        }
    }
}