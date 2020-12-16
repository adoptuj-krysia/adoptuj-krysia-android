package pl.tuchola.zslit.krychu.krychotron.view
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.common.Boast
import pl.tuchola.zslit.krychu.krychotron.KrychotronEntry
import pl.tuchola.zslit.krychu.krychotron.KrychotronEntryPlayer
import pl.tuchola.zslit.krychu.krychotron.KrychotronType

class KrychotronEntryArrayAdapter(private val context: Context,
                                  private val dataSource: Array<KrychotronEntry>,
                                  private val type: KrychotronType) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    private fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val header = inflater.inflate(R.layout.krychotron_header, parent, false)

        val title = if (type == KrychotronType.KRYCHOTRON) context.getString(R.string.krychotron_header)
        else context.getString(R.string.pelonixon_header)

        val description = if (type == KrychotronType.KRYCHOTRON) context.getString(R.string.krychotron_description)
        else context.getString(R.string.pelonixon_description)

        header.findViewById<TextView>(R.id.krychotronHeaderTitle).text = title
        header.findViewById<TextView>(R.id.krychotronHeaderContent).text = description
        header.isEnabled = false

        return header;
    }

    private fun getItemView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = convertView ?: inflater.inflate(R.layout.krychotron_listitem, parent, false)

        val label = rowView.findViewById<TextView>(R.id.krychotronEntry_label)!!
        val description = rowView.findViewById<TextView>(R.id.krychotronEntry_description)!!
        label.text = context.getString(R.string.krychotron_position, (position).toString())
        description.text = dataSource[position - 1].description

        rowView.setOnClickListener {
            Boast.showLongMessage(context.getString(R.string.krychotron_boast, (position).toString()), context)
            val player = KrychotronEntryPlayer(dataSource[position - 1], context)
            player.play()
        }

        return rowView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return if(position == 0)
            getHeaderView(position, convertView, parent)
        else
            getItemView(position, convertView, parent)
    }

    override fun getItem(position: Int): Any {
        return if(position == 0)
            KrychotronEntry(0,"")
        else
            dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return if(position == 0)
            dataSource[position].hashCode().toLong()
        else
            0
    }

    override fun getCount(): Int {
        return dataSource.size + 1
    }

    /*override fun isEnabled(position: Int): Boolean = false
    override fun areAllItemsEnabled(): Boolean = false*/
}