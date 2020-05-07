package pl.tuchola.zslit.krychu.main
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_configuration.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.common.Boast
import pl.tuchola.zslit.krychu.files.AppConfiguration
import pl.tuchola.zslit.krychu.weather.WeatherLocation

class ConfigurationFragment : Fragment() {

    private fun showDebianificationConfirmation() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context!!)
        builder.setTitle(R.string.configuration_confirm_header)
        builder.setMessage(R.string.configuration_confirm_body)
        builder.setPositiveButton(R.string.configuration_ans_positive) {dialog,_->
            debianification_switch.isChecked = false
            AppConfiguration(context!!).enablePatternDebianifying = false
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.configuration_ans_negative) {dialog,_->
            debianification_switch.isChecked = true
            dialog.dismiss()
        }
        builder.setOnCancelListener {
            debianification_switch.isChecked = true
        }
        builder.create().show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val settings = AppConfiguration(context!!)
        weatherLocation_spinner.adapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_dropdown_item, WeatherLocation.allowedLocations)
        weatherLocation_spinner.setSelection(WeatherLocation.allowedLocations.indexOf(settings.weatherLocation))
        debianification_switch.isChecked = settings.enablePatternDebianifying

        debianification_switch.setOnCheckedChangeListener {_,_->
            if(debianification_switch.isChecked) {
                settings.enablePatternDebianifying = true
            } else {
                showDebianificationConfirmation()
            }
        }

        friendConfigPhoto_imageView.setOnClickListener {
            Boast.showLongMessage(getString(R.string.configuration_friend_touched), context!!)
        }

        weatherLocation_spinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(weatherLocation_spinner.selectedItem == "Lubiewo") {
                    Boast.showLongMessage(getString(R.string.configuration_lubiewo_chosen), context!!)
                    weatherLocation_spinner.setSelection(WeatherLocation.allowedLocations.indexOf(settings.weatherLocation))
                }
                settings.weatherLocation = weatherLocation_spinner.selectedItem as String
            }
        }
    }

}