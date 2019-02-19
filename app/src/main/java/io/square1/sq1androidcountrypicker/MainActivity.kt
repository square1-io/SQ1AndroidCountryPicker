package io.square1.sq1androidcountrypicker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.square1.sq1countryselector.model.CountryModel
import io.square1.sq1countryselector.view.SQ1CountrySelector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SQ1CountrySelector.ItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(resources.getDrawable(R.mipmap.ic_launcher))
        supportActionBar?.title = getString(R.string.app_name)
        // Set Listeners
        sq1CountrySelector.setListener(this)
    }

    override fun onClick(country: CountryModel) {
        Toast.makeText(this, "Country Selected ${country.name}", Toast.LENGTH_LONG).show()
    }
}