# SQ1AndroidCountrySelector

Android customizable country picker based on current Locale

![Screenshot](art/main%20screen.png)

## Getting Started
Add sq1countryselector to your project and customize your view

### Decide what you want to show 

```
<io.square1.sq1countryselector.view.SQ1CountrySelector
            android:id="@+id/sq1CountrySelector"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:hint="Busca tu pais"
            app:itemTextSize="12sp"
            app:showFlag="true"
            app:roundedFlag="true"
            app:filter = "true"
            app:flagPosition="start"/>
```

- **hint**: String -> Sets hint into search view
- **itemTextSize**: dimension -> Sets item list text size
- **showFlag** : Boolean -> Flag visibility
- **roundedFlag** : Boolean -> Sets rounded flag if true
- **filter** : Boolean -> Show searchview to filter results if true
- **flagPosition** : start | end -> Flag position on item list

### Implement SQ1CountrySelector.ItemClickListener 

1. Use **setListener(listener: ItemClickListener)** method to register your listener

```
// Set Listeners
sq1CountrySelector.setListener(this)

```

2. Let listener implements **SQ1CountrySelector.ItemClickListener**

```
override fun onClick(country: CountryModel) {
    Toast.makeText(this, "Country Selected ${country.name}", Toast.LENGTH_LONG).show()
}
```

3. Use Country as you want

```
data class CountryList(val countries: ArrayList<CountryModel>)

data class CountryModel(var name: String, val alpha2Code: String, val callingCodes: ArrayList<String>)
```
