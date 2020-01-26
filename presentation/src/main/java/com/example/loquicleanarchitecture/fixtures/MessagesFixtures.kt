package com.example.loquicleanarchitecture.fixtures

import com.example.data.model.Message
import com.example.data.model.User

import java.util.ArrayList
import java.util.Calendar
import java.util.Date
internal class MessagesFixtures private constructor() : FixturesData() {
    init {
        throw AssertionError()
    }

    companion object {

        val imageMessage: Message
            get() {
                val message = Message(randomId, user, null)
                message.setImage(Message.Image(randomImage))
                return message
            }

        val voiceMessage: Message
            get() {
                val message = Message(randomId, user, null)
                message.voice = Message.Voice("http://example.com", rnd.nextInt(200) + 30)
                return message
            }

        val textMessage: Message
            get() = getTextMessage(randomMessage)

        fun getTextMessage(text: String): Message {
            return Message(randomId, user, text)
        }

        fun getMessages(startDate: Date?): ArrayList<Message> {
            val messages = ArrayList<Message>()
            for (i in 0..9/*days count*/) {
                val countPerDay = rnd.nextInt(5) + 1

                for (j in 0 until countPerDay) {
                    val message: Message
                    if (i % 2 == 0 && j % 3 == 0) {
                        message = imageMessage
                    } else {
                        message = textMessage
                    }

                    val calendar = Calendar.getInstance()
                    if (startDate != null) calendar.time = startDate
                    calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1))

                    message.setCreatedAt(calendar.time)
                    messages.add(message)
                }
            }
            return messages
        }

        private val user: User
            get() {
                val even = rnd.nextBoolean()
                return User(
                    if (even) "0" else "1",
                    if (even) names[0] else names[1],
                    if (even) avatars[0] else avatars[1],
                    true
                )
            }
    }
}
