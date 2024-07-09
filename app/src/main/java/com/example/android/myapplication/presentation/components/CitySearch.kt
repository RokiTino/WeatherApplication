
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CitySearch(viewModel: CityViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = viewModel.query,
            onValueChange = {
                viewModel.query = it
                viewModel.searchCities()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        viewModel.cities.forEach { city ->
            Text(text = city.name, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}
