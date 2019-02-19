# SQ1AndroidCountrySelector
A customizable country picker
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
