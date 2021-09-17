package net.dimterex.sync_client.presenter.base

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import net.dimterex.sync_client.App
import net.dimterex.sync_client.data.entries.ConnectionsLocalModel
import net.dimterex.sync_client.entity.FolderSelectModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

abstract class BasePresenter(private val view: BaseView) : KodeinAware {

    override val kodein: Kodein
        get() = App.kodein()

    open fun onCreate(arguments: Bundle? = null) {
        view.initView()
        if (view is HiddenMenuScreen) {
            view.hideMenu()
        } else {
            view.showMenu()
        }
    }

    open fun onDestroy() {
    }

    open fun onError(error: Throwable) {
        view.showError(error)
        error.printStackTrace()
    }


}

interface BaseView: LifecycleOwner {

    fun showMenu()

    fun initView()

    fun showError(error: Throwable)
}