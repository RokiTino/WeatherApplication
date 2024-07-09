
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.myapplication.data.City
import com.example.android.myapplication.data.CityApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityApiService: CityApiService
) : ViewModel() {

    var cities by mutableStateOf<List<City>>(emptyList())
    var query by mutableStateOf("")

    fun searchCities() {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                try {
                    cities = cityApiService.searchCities(query)
                } catch (e: Exception) {
                    // Handle error
                    cities = emptyList()
                }
            } else {
                cities = emptyList()
            }
        }
    }
}
