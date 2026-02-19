package com.example.quiz_four;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ApartmentListActivity extends AppCompatActivity {
    private ListView lvApartments;
    private Button btnAddApartment;
    private ApartmentAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<Apartment> apartmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_list);

        dbHelper = new DatabaseHelper(this);
        lvApartments = findViewById(R.id.lvApartments);
        btnAddApartment = findViewById(R.id.btnAddApartment);

        loadApartments();

        btnAddApartment.setOnClickListener(v -> {
            Intent intent = new Intent(ApartmentListActivity.this, ApartmentActivity.class);
            startActivityForResult(intent, 1);
        });

        adapter = new ApartmentAdapter(this, apartmentList);
        adapter.setEditClickListener(apartment -> {
            Intent intent = new Intent(ApartmentListActivity.this, ApartmentActivity.class);
            intent.putExtra("apartment", apartment);
            startActivityForResult(intent, 1);
        });

        adapter.setDeleteClickListener(apartment -> {
            showDeleteConfirmation(apartment);
        });

        lvApartments.setAdapter(adapter);
    }

    private void loadApartments() {
        apartmentList = dbHelper.getAllApartments();
        if (adapter != null) {
            adapter.updateList(apartmentList);
        }
    }

    private void showDeleteConfirmation(Apartment apartment) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Apartment")
                .setMessage("Are you sure you want to delete apartment " + apartment.getUnitNumber() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    int rowsAffected = dbHelper.deleteApartment(apartment.getApartmentId());
                    if (rowsAffected > 0) {
                        Toast.makeText(this, "Apartment deleted successfully", Toast.LENGTH_SHORT).show();
                        loadApartments();
                    } else {
                        Toast.makeText(this, "Failed to delete apartment", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadApartments();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadApartments();
    }
}
