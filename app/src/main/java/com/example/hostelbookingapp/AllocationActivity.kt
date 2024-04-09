package com.example.hostelbookingapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.booking_h.R

class AllocationActivity : AppCompatActivity() {

    private lateinit var floorSpinner: Spinner
    private lateinit var roomSpinner: Spinner
    private lateinit var wingSpinner: Spinner
    private lateinit var membersTextView: TextView

    private val totalRoomsPerWing = 13
    private var availableRoomsPerWing = totalRoomsPerWing
    private var membersInSelectedRoom = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allocation)

        floorSpinner = findViewById(R.id.floorSpinner)
        roomSpinner = findViewById(R.id.RoomSpinner)
        wingSpinner = findViewById(R.id.wingSpinner)
        membersTextView = findViewById(R.id.membersTextView)

        // Populate spinners with options
        setupFloorSpinner()
        setupRoomSpinner()
        setupWingSpinner()

        // Set listeners for spinners
        floorSpinner.onItemSelectedListener = FloorSpinnerListener()
        wingSpinner.onItemSelectedListener = WingSpinnerListener()
        roomSpinner.onItemSelectedListener = RoomSpinnerListener()
    }

    private fun setupFloorSpinner() {
        val floors = arrayOf("Floor 1", "Floor 2", "Floor 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, floors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floorSpinner.adapter = adapter
    }

    private fun setupRoomSpinner() {
        val rooms = Array(totalRoomsPerWing) { "Room ${it + 1}" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rooms)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roomSpinner.adapter = adapter
    }

    private fun setupWingSpinner() {
        val wings = arrayOf("Left Wing", "Right Wing")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wings)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wingSpinner.adapter = adapter
    }

    // Listener for floor spinner selection
    inner class FloorSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // Reset available rooms when floor changes
            availableRoomsPerWing = totalRoomsPerWing
            // Update room spinner based on floor selection
            setupRoomSpinner()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    // Listener for wing spinner selection
    inner class WingSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // Reset available rooms when wing changes
            availableRoomsPerWing = totalRoomsPerWing
            // Update room spinner based on wing selection
            setupRoomSpinner()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    // Listener for room spinner selection
    inner class RoomSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // Calculate and display the number of members in the selected room
            membersTextView.text = "Members in Room: $membersInSelectedRoom"

            // Update number of members in the room (decrement by 1)
            membersInSelectedRoom--
            if (membersInSelectedRoom <= 0) {
                // Room is filled, reduce available rooms count by one
                availableRoomsPerWing--
                if (availableRoomsPerWing == 0) {
                    // All rooms in the wing are booked, notify the user to choose the next floor
                    // You can display a message or enable/disable UI components accordingly
                    // For example:
                    // Toast.makeText(this@AllocationActivity, "All rooms in this wing are booked. Please choose the next floor.", Toast.LENGTH_SHORT).show()
                } else {
                    // Reset number of members for the next room
                    membersInSelectedRoom = 7
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}