package com.example.loquicleanarchitecture.view.chat

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.MessagesFixtures
import com.example.loquicleanarchitecture.model.Dialog
import com.example.loquicleanarchitecture.model.Message
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesListAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_chatroom.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChatRoomFragment : DaggerFragment(), MessageInput.InputListener,
    MessageInput.AttachmentsListener,
    MessageInput.TypingListener,
    MessagesListAdapter.SelectionListener,
    MessagesListAdapter.OnLoadMoreListener {

    private val TOTAL_MESSAGES_COUNT = 100

    private val senderId = "0"
    private lateinit var imageLoader: ImageLoader
    private var messagesAdapter: MessagesListAdapter<Message>? = null

    private var menu: Menu? = null
    private var selectionCount: Int = 0
    private var lastLoadedDate: Date? = null

    lateinit var dialogsAdapter: DialogsListAdapter<Dialog>

    @Inject
    lateinit var picasso: Picasso

    override fun onStart() {
        super.onStart()
        messagesAdapter!!.addToStart(MessagesFixtures.textMessage, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_chatroom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageLoader = ImageLoader { imageView, url, payload -> picasso.load(url).into(imageView) }
        initAdapter()

        input.setInputListener(this)
        input.setTypingListener(this)
        input.setAttachmentsListener(this)
    }

    private fun initAdapter() {
        messagesAdapter = MessagesListAdapter(senderId, imageLoader)
        messagesAdapter!!.enableSelectionMode(this)
        messagesAdapter!!.setLoadMoreListener(this)
        messagesAdapter!!.registerViewClickListener(
            R.id.messageUserAvatar
        ) { view, message ->
            makeText(
                this.context,
                message.user.name + " avatar click",
                Toast.LENGTH_LONG
            ).show()
        }
        messagesList.setAdapter(messagesAdapter)
    }

    private fun loadMessages() {
        Handler().postDelayed({
            val messages = MessagesFixtures.getMessages(lastLoadedDate)
            lastLoadedDate = messages[messages.size - 1].createdAt
            messagesAdapter!!.addToEnd(messages, false)
        }, 1000)
    }

    private fun getMessageStringFormatter(): MessagesListAdapter.Formatter<Message> {
        return MessagesListAdapter.Formatter { message ->
            val createdAt = SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                .format(message.createdAt)

            var text = message.text
            if (text == null) text = "[attachment]"

            String.format(
                Locale.getDefault(), "%s: %s (%s)",
                message.user.name, text, createdAt
            )
        }
    }

    override fun onSubmit(input: CharSequence?): Boolean {
        messagesAdapter!!.addToStart(
            MessagesFixtures.getTextMessage(input.toString()), true
        )
        return true
    }

    override fun onAddAttachments() {
        messagesAdapter!!.addToStart(
            MessagesFixtures.imageMessage, true
        )
    }

    override fun onStartTyping() {
        Log.v("Typing listener", getString(R.string.start_typing_status))
    }

    override fun onStopTyping() {
        Log.v("Typing listener", getString(R.string.stop_typing_status))
    }

    override fun onSelectionChanged(count: Int) {
        // todo
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        Log.i("TAG", "onLoadMore: $page $totalItemsCount")
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages()
        }
    }
}