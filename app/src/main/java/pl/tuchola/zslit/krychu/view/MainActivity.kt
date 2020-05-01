package pl.tuchola.zslit.krychu.view
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.io.ActivityLog


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        nav_view.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, main_drawer_layout, main_toolbar, R.string.hint_open_drawer, R.string.hint_close_drawer)
        main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        ActivityLog(this).writeLine(getString(R.string.log_main_activity_launched));
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, HomeFragment()).commit()
            nav_view.setCheckedItem(R.id.nav_item_home)
        }
    }

    override fun onBackPressed() {
        if(main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
             main_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            (R.id.nav_item_home) -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, HomeFragment()).commit()
            }

            (R.id.nav_item_weather) -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, WeatherFragment()).commit()
            }

            (R.id.nav_item_info) -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, InformationFragment()).commit()
            }
        }
        main_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
