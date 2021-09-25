package net.dimterex.sync_client.api.Message.Action

import com.google.gson.annotations.SerializedName
import net.dimterex.sync_client.api.MessageAttr
import net.dimterex.sync_client.api.interfaces.IMessage

@MessageAttr(name = "FileRemoveResponse")
class RemoveFileResponce() : IMessage {

    @SerializedName("file_name")
    var file_name : String = ""

}