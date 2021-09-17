package net.dimterex.sync_client.ui.folder.settings

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.obsez.android.lib.filechooser.ChooserDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_logs.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_repos.*
import kotlinx.android.synthetic.main.fragment_repos.logs_list
import net.dimterex.sync_client.R
import net.dimterex.sync_client.data.entries.ConnectionsLocalModel
import net.dimterex.sync_client.data.entries.FolderMappingLocalModel
import net.dimterex.sync_client.entity.FolderSelectModel
import net.dimterex.sync_client.presenter.menu.settings.SettingsPresenter
import net.dimterex.sync_client.presenter.menu.settings.SettingsView
import net.dimterex.sync_client.ui.base.BaseFragment
import net.dimterex.sync_client.ui.folder.sync.adapter.FolderSelectionAdapter
import net.dimterex.sync_client.ui.folder.sync.adapter.SyncEventsAdapter
import java.io.File

class SettingsFragment : BaseFragment<SettingsPresenter>(), SettingsView {

    private lateinit var adapter: FolderSelectionAdapter

    private var _port: EditText? = null
    private var _ip_address: EditText? = null
//    private var _ip_address : String = String()
//    private var _port : Int = 0


    override var profile: ConnectionsLocalModel? = null
    set(value) {

        if (value != null) {
            _ip_address?.setText(value.ip_address,  TextView.BufferType.EDITABLE)
            _port?.setText(value.ip_port.toString())
        }

        field = value
    }

    override fun initPresenter(): SettingsPresenter = SettingsPresenter(this)

    override fun layoutId(): Int = R.layout.fragment_profile

    override fun initView() {
        _ip_address = ip_address
        _port = port

        val data = arrayOf("one", "two", "three", "four", "five")
        adapter = FolderSelectionAdapter(data)
        folders_list.layoutManager = LinearLayoutManager(folders_list.context)
        folders_list.adapter = adapter

        presenter.getFolders().forEach{ x ->
            adapter.add(FolderSelectModel(x, data))
        }

        saveSettings.setOnClickListener {view ->
            presenter.save()
        }
    }

    override fun showMenu() {
        activity?.main_bottom_navigation?.visibility = View.VISIBLE
    }

    override fun get_ip_port(): Int {
        return _port?.text.toString().toInt()
    }

    override fun get_sync_folders(): Array<FolderSelectModel> {
        return adapter.items.toTypedArray()
    }

    override fun get_ip_address(): String  {
        return _ip_address?.text.toString()
    }
}
