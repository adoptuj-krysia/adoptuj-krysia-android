package pl.tuchola.zslit.krychu.krychotron
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.common.Boast

class KrychotronEntryArrayAdapter(private val context: Context,
                                  private val dataSource: Array<KrychotronEntry>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val inflater = LayoutInflater.from(context)
            val rowView = convertView ?: inflater.inflate(R.layout.krychotron_listitem, parent, false)

            val label = rowView.findViewById<TextView>(R.id.krychotronEntry_label)!!
            val description = rowView.findViewById<TextView>(R.id.krychotronEntry_description)!!
            label.text = context.getString(R.string.krychotron_position, (position+1).toString())
            description.text = dataSource[position].description

            rowView.setOnClickListener {
                Boast.showLongMessage(context.getString(R.string.krychotron_boast, (position+1).toString()), context)
                val player = KrychotronEntryPlayer(dataSource[position], context)
                player.play()
            }

            return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return dataSource[position].hashCode().toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    /*override fun isEnabled(position: Int): Boolean = false
    override fun areAllItemsEnabled(): Boolean = false*/
}