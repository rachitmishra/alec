package `in`.ceeq.lyte.firebase

import `in`.ceeq.lyte.data.Message
import android.databinding.ObservableArrayList
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseHelper<T> {

    private val mFirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    private val mDatabaseReference by lazy { mFirebaseDatabase.reference.child(mNode) }

    private lateinit var mDataSet: ObservableArrayList<T>

    private lateinit var mNode: String

    private lateinit var mClazz: Class<T>

    private val mChildEventListener: ChildEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError?) {
        }

        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            mDataSet.add(p0?.getValue(mClazz))
        }

        override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
            mDataSet.add(p0?.getValue(mClazz))
        }

        override fun onChildRemoved(p0: DataSnapshot?) {
            mDataSet.remove(p0?.getValue(mClazz))
        }
    }

    fun setDataSet(list: ObservableArrayList<T>) {
        mDataSet = list
    }

    fun setNode(node: String) {
        mNode = node
    }

    init {
        addListener()
    }

    private fun addListener() {
        mDatabaseReference.addChildEventListener(mChildEventListener)
    }

    fun push(message: Message) {
        mDatabaseReference.push().setValue(message)
    }

    fun removeListener() {
        mDatabaseReference.removeEventListener(mChildEventListener)
    }
}
