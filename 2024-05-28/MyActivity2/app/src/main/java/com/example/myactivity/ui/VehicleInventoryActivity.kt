package com.example.myactivity.ui
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.R
import com.example.myactivity.client.VehicleCli
import com.example.myactivity.data.model.Product
import com.example.myactivity.data.model.User
import com.example.myactivity.data.model.VehicleInventory

class VehicleInventoryActivity : AppCompatActivity() {

    private lateinit var etInventoryId: EditText
    private lateinit var etProductId: EditText
    private lateinit var etUserId: EditText
    private lateinit var etQuantity: EditText

    private lateinit var btnGetVehicleInventory: Button
    private lateinit var btnCreateVehicleInventory: Button
    private lateinit var btnUpdateVehicleInventory: Button
    private lateinit var btnDeleteVehicleInventory: Button

    private lateinit var vehicleCli: VehicleCli

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)

        // Initialize views
        etInventoryId = findViewById(R.id.etInventoryId)
        etProductId = findViewById(R.id.etProductId)
        etUserId = findViewById(R.id.etUserId)
        etQuantity = findViewById(R.id.etQuantity)

        btnGetVehicleInventory = findViewById(R.id.btnGetVehicleInventory)
        btnCreateVehicleInventory = findViewById(R.id.btnCreateVehicleInventory)
        btnUpdateVehicleInventory = findViewById(R.id.btnUpdateVehicleInventory)
        btnDeleteVehicleInventory = findViewById(R.id.btnDeleteVehicleInventory)

        // Initialize VehicleCli
        vehicleCli = VehicleCli(this)

        // Set click listeners
        btnGetVehicleInventory.setOnClickListener {
            val inventoryId = etInventoryId.text.toString().toInt()
            vehicleCli.getVehicleInventoryById(inventoryId)
        }

        btnCreateVehicleInventory.setOnClickListener {
            val productId = etProductId.text.toString().toInt()
            val userId = etUserId.text.toString().toInt()
            val quantity = etQuantity.text.toString()

            val product = Product(productId, "", "", 0.0, 0)
            val user = User(userId, "", "")
            val vehicleInventory = VehicleInventory(0, product, user, quantity)

            vehicleCli.createVehicleInventory(vehicleInventory)
        }

        btnUpdateVehicleInventory.setOnClickListener {
            val inventoryId = etInventoryId.text.toString().toInt()
            val productId = etProductId.text.toString().toInt()
            val userId = etUserId.text.toString().toInt()
            val quantity = etQuantity.text.toString()

            val product = Product(productId, "", "", 0.0, 0)
            val user = User(userId, "", "")
            val vehicleInventory = VehicleInventory(inventoryId, product, user, quantity)

            vehicleCli.updateVehicleInventory(inventoryId, vehicleInventory)
        }

        btnDeleteVehicleInventory.setOnClickListener {
            val inventoryId = etInventoryId.text.toString().toInt()
            vehicleCli.deleteVehicleInventory(inventoryId)
        }
    }
}
