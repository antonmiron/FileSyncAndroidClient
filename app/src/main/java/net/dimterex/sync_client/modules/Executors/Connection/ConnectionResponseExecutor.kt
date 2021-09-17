package net.dimterex.sync_client.modules.Executors.Connection

import net.dimterex.sync_client.api.Message.Connection.ConnectionResponse
import net.dimterex.sync_client.api.Modules.Common.IExecute
import net.dimterex.sync_client.entity.FileSyncState
import net.dimterex.sync_client.modules.FileStateEventManager

class ConnectionResponseExecutor(private val _FileState_eventManager: FileStateEventManager) : IExecute<ConnectionResponse> {
    override fun Execute(param: ConnectionResponse) {
        param.shared_folders.forEach{ x ->
            _FileState_eventManager.save_event(FileSyncState(x, 0))
        }
    }
}