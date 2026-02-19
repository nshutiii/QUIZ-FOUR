package com.example.quiz_four;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ApartmentActivity extends AppCompatActivity {
    private EditText etUnitNumber, etSquareFootage, etRentAmount, etLocation;
    private CheckBox cbIsAirBnb, cbAllowsPets;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private Apartment apartment;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment);

        dbHelper = new DatabaseHelper(this);

        etUnitNumber = findViewById(R.id.etUnitNumber);
        etSquareFootage = findViewById(R.id.etSquareFootage);
        etRentAmount = findViewById(R.id.etRentAmount);
        etLocation = findViewById(R.id.etLocation);
        cbIsAirBnb = findViewById(R.id.cbIsAirBnb);
        cbAllowsPets = findViewById(R.id.cbAllowsPets);
        btnSave = findViewById(R.id.btnSave);

        // Check if we're editing an existing apartment
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("apartment")) {
            apartment = (Apartment) intent.getSerializableExtra("apartment");
            if (apartment != null) {
                isEditMode = true;
                populateFields();
            }
        }

        btnSave.setOnClickListener(v -> saveApartment());
    }

    private void populateFields() {
        etUnitNumber.setText(apartment.getUnitNumber());
        etSquareFootage.setText(String.valueOf(apartment.getSquareFootage()));
        etRentAmount.setText(String.valueOf(apartment.getRentAmount()));
        etLocation.setText(apartment.getLocation());
        cbIsAirBnb.setChecked(apartment.getIsAirBnb());
        cbAllowsPets.setChecked(apartment.getAllowsPets());
    }

    private void saveApartment() {
        String unitNumber = etUnitNumber.getText().toString().trim();
        String squareFootageStr = etSquareFootage.getText().toString().trim();
        String rentAmountStr = etRentAmount.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        if (unitNumber.isEmpty()) {
            etUnitNumber.setError("Unit number is required");
            return;
        }

        if (squareFootageStr.isEmpty()) {
            etSquareFootage.setError("Square footage is required");
            return;
        }

        if (rentAmountStr.isEmpty()) {
            etRentAmount.setError("Rent amount is required");
            return;
        }

        try {
            Float squareFootage = Float.parseFloat(squareFootageStr);
            Double rentAmount = Double.parseDouble(rentAmountStr);
            Boolean isAirBnb = cbIsAirBnb.isChecked();
            Boolean allowsPets = cbAllowsPets.isChecked();

            if (isEditMode && apartment != null) {
                // Update existing apartment
                apartment.setUnitNumber(unitNumber);
                apartment.setSquareFootage(squareFootage);
                apartment.setRentAmount(rentAmount);
                apartment.setIsAirBnb(isAirBnb);
                apartment.setAllowsPets(allowsPets);
                apartment.setLocation(location);

                int rowsAffected = dbHelper.updateApartment(apartment);
                if (rowsAffected > 0) {
                    Toast.makeText(this, "Apartment updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to update apartment", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Create new apartment
                Apartment newApartment = new Apartment(unitNumber, squareFootage, rentAmount, isAirBnb, allowsPets, location);
                long id = dbHelper.addApartment(newApartment);
                if (id > 0) {
                    Toast.makeText(this, "Apartment saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to save apartment", Toast.LENGTH_SHORT).show();
                }
            }

            // Notify ApartmentListActivity to refresh
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for square footage and rent amount", Toast.LENGTH_SHORT).show();
        }
    }
}
