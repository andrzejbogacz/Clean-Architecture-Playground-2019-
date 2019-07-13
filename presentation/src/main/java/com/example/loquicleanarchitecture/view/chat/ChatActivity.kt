package com.example.loquicleanarchitecture.view.chat

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.loquicleanarchitecture.BaseActivity
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.MessagesFixtures
import com.example.loquicleanarchitecture.model.Message
import com.example.loquicleanarchitecture.utils.AppUtils
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : BaseActivity(), MessageInput.InputListener,
    MessageInput.AttachmentsListener,
    MessageInput.TypingListener,
    MessagesListAdapter.SelectionListener,
    MessagesListAdapter.OnLoadMoreListener {


    private val TOTAL_MESSAGES_COUNT = 100

    val senderId = "0"
    lateinit var imageLoader: ImageLoader
    var messagesAdapter: MessagesListAdapter<Message>? = null

    private var menu: Menu? = null
    private var selectionCount: Int = 0
    private var lastLoadedDate: Date? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initToolbar()

        imageLoader =
            ImageLoader { imageView, url, payload -> Picasso.get().load(url).into(imageView) }
        initAdapter()

        input.setInputListener(this)
        input.setTypingListener(this)
        input.setAttachmentsListener(this)

    }

    override fun onStart() {
        super.onStart()
        messagesAdapter!!.addToStart(MessagesFixtures.textMessage, true)
    }

    override fun onSubmit(input: CharSequence): Boolean {
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

    private fun initAdapter() {
        messagesAdapter = MessagesListAdapter(senderId, imageLoader)
        messagesAdapter!!.enableSelectionMode(this)
        messagesAdapter!!.setLoadMoreListener(this)
        messagesAdapter!!.registerViewClickListener(
            R.id.messageUserAvatar
        ) { view, message ->
            AppUtils.showToast(
                this,
                message.getUser().getName() + " avatar click",
                false
            )
        }
        messagesList.setAdapter(messagesAdapter)
    }

    private fun initToolbar() {
        // Setup Actionbar / Toolbar
        setSupportActionBar(mToolbar as Toolbar?)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        //    (mToolbar as Toolbar?)!!.inflateMenu(R.menu.chat_actions_menu)
    }

    override fun onSelectionChanged(count: Int) {
        this.selectionCount = count
        menu!!.findItem(R.id.action_delete).isVisible = count > 0
        menu!!.findItem(R.id.action_copy).isVisible = count > 0
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> messagesAdapter!!.deleteSelectedMessages()
            R.id.action_copy -> {
                messagesAdapter!!.copySelectedMessagesText(this, getMessageStringFormatter(), true)
                AppUtils.showToast(this, "message copied", true)
            }
        }
        return true
    }

    override fun onStartTyping() {
        Log.v("Typing listener", getString(R.string.start_typing_status))
    }

    override fun onStopTyping() {
        Log.v("Typing listener", getString(R.string.stop_typing_status))
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        Log.i("TAG", "onLoadMore: $page $totalItemsCount")
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages()
        }
    }

    fun loadMessages() {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chat_actions_menu, menu)
        this.menu = menu
        onSelectionChanged(0)
        return true
    }

    override fun onBackPressed() {
        if (selectionCount == 0) {
            super.onBackPressed()
        } else {
            messagesAdapter!!.unselectAllItems()
        }
    }

    companion object {
    }
}


