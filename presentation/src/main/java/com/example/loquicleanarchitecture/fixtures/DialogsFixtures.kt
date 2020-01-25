package com.example.loquicleanarchitecture.fixtures

import com.example.loquicleanarchitecture.model.Dialog
import com.example.loquicleanarchitecture.model.Message
import com.example.loquicleanarchitecture.model.User

import java.util.ArrayList
import java.util.Calendar
import java.util.Date
internal class DialogsFixtures private constructor() : FixturesData() {
    init {
        throw AssertionError()
    }

    companion object {

        val dialogs: ArrayList<Dialog>
            get() {
                val chats = ArrayList<Dialog>()

                for (i in 0..19) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_MONTH, -(i * i))
                    calendar.add(Calendar.MINUTE, -(i * i))

                    chats.add(getDialog(i, calendar.time))
                }

                return chats
            }

        private fun getDialog(i: Int, lastMessageCreatedAt: Date): Dialog {
            val users = users
            val message = getMessage(lastMessageCreatedAt)
            return Dialog(
                randomId,
                users[0].name,
                randomAvatar,
                users,
                getMessage(lastMessageCreatedAt),
                if (i < 3) 3 - i else 0
            )
        }

        private val users: ArrayList<User>
            get() {
                val users = ArrayList<User>()
                val usersCount = 1 + rnd.nextInt(4)

                for (i in 0 until usersCount) {
                    users.add(user)
                }

                return users
            }

        private val user: User
            get() = User(
                randomId,
                randomName,
                randomAvatar,
                randomBoolean
            )

        private fun getMessage(date: Date): Message {
            return Message(
                randomId,
                user,
                randomMessage,
                date
            )
        }
    }
}
