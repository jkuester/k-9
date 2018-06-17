package com.fsck.k9.backend.api


import com.fsck.k9.mail.FetchProfile
import com.fsck.k9.mail.Flag
import com.fsck.k9.mail.Folder
import com.fsck.k9.mail.Message
import com.fsck.k9.mail.MessagingException


interface Backend {
    val supportsSeenFlag: Boolean
    val supportsExpunge: Boolean

    @Throws(MessagingException::class)
    fun getFolders(forceListAll: Boolean): List<FolderInfo>

    // TODO: Add a way to cancel the sync process
    fun sync(folder: String, syncConfig: SyncConfig, listener: SyncListener, providedRemoteFolder: Folder<*>?)

    @Throws(MessagingException::class)
    fun setFlag(folderServerId: String, messageServerIds: List<String>, flag: Flag, newState: Boolean)

    @Throws(MessagingException::class)
    fun markAllAsRead(folderServerId: String)

    @Throws(MessagingException::class)
    fun expunge(folderServerId: String)

    @Throws(MessagingException::class)
    fun expungeMessages(folderServerId: String, messageServerIds: List<String>)

    @Throws(MessagingException::class)
    fun deleteAllMessages(folderServerId: String)

    @Throws(MessagingException::class)
    fun moveMessages(
            sourceFolderServerId: String,
            targetFolderServerId: String,
            messageServerIds: List<String>
    ): Map<String, String>?

    @Throws(MessagingException::class)
    fun copyMessages(
            sourceFolderServerId: String,
            targetFolderServerId: String,
            messageServerIds: List<String>
    ): Map<String, String>?

    @Throws(MessagingException::class)
    fun search(
            folderServerId: String,
            query: String?,
            requiredFlags: Set<Flag>?,
            forbiddenFlags: Set<Flag>?
    ): List<String>

    @Throws(MessagingException::class)
    fun fetchMessage(folderServerId: String, messageServerId: String, fetchProfile: FetchProfile): Message
}
