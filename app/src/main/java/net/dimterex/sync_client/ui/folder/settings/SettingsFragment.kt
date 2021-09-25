package net.dimterex.sync_client.ui.folder.settings

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import net.dimterex.sync_client.R
import net.dimterex.sync_client.data.entries.ConnectionsLocalModel
import net.dimterex.sync_client.data.entries.FolderMappingLocalModel
import net.dimterex.sync_client.entity.FolderSelectModel
import net.dimterex.sync_client.presenter.menu.settings.SettingsPresenter
import net.dimterex.sync_client.presenter.menu.settings.SettingsView
import net.dimterex.sync_client.ui.base.BaseFragment
import net.dimterex.sync_client.ui.folder.sync.adapter.FolderSelectionAdapter

class SettingsFragment : BaseFragment<SettingsPresenter>(), SettingsView {

    private lateinit var adapter: FolderSelectionAdapter

    override var profile: ConnectionsLocalModel? = null
    set(value) {

        if (value != null) {
            ip_address_textbox?.editText?.setText(value.ip_address)
            port_textbox?.editText?.setText(value.ip_port.toString())
            login_textbox?.editText?.setText(value.login)
            password_textbox?.editText?.setText(value.password)
        }

        field = value
    }

    override fun add_new_event(message: String) {
        adapter.items.forEach { x ->
            x.folders.add(message)
        }
    }

    override fun initPresenter(): SettingsPresenter = SettingsPresenter(this)

    override fun layoutId(): Int = R.layout.fragment_profile

    override fun initView() {
        val data = ArrayList<String>()
        presenter.getAvailableFolders().forEach { x ->
            data.add(x)
        }

        adapter = FolderSelectionAdapter()
        folders_list.layoutManager = LinearLayoutManager(folders_list.context)
        folders_list.adapter = adapter

        presenter.getMappingFolders().forEach{ x ->
            adapter.add(FolderSelectModel(x, data))
        }

        saveSettingsButton.setOnClickListener { view ->
            presenter.save()
        }

        addFolderButton.setOnClickListener { view ->
            adapter.add(FolderSelectModel(createFolderMappingLocalModel(), data))
        }

        checkConnectionButton.setOnClickListener { view ->
            presenter.check_connection(get_ip_address(), get_ip_port(), get_login(), get_password())
        }
    }

    override fun showMenu() {
        activity?.main_bottom_navigation?.visibility = View.VISIBLE
    }

    override fun get_ip_port(): Int {
        return port_textbox?.editText?.text.toString().toInt()
    }

    override fun get_login(): String {
        return login_textbox?.editText?.text.toString()
    }

    override fun get_password(): String {
        return password_textbox?.editText?.text.toString()
    }

    override fun get_sync_folders(): Array<FolderSelectModel> {
        return adapter.items.toTypedArray()
    }

    override fun get_ip_address(): String  {
        return ip_address_textbox?.editText?.text.toString()
    }

    private fun createFolderMappingLocalModel(): FolderMappingLocalModel {
        var i = 0

        for (item in adapter.items)
        {
            if (i == item.folFolderMappingLocalModel.id)
            {
                i++
            }
            else
            {
                break
            }
        }

        return FolderMappingLocalModel(i, String(), String())
    }
}
