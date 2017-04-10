package com.example.a.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.checkbox_chocolate);
        EditText userName = (EditText) findViewById(R.id.name_field);
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolate.isChecked();
        String name = userName.getText().toString();
        displayOrderSummary(5, hasWhippedCream, hasChocolate, name);

        }
    // Create Intent to send an order email
    private void composeEmail(String Subject, String Message){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, Subject);
        intent.putExtra(Intent.EXTRA_TEXT, Message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View v) {
        if (quantity==100){
            Toast.makeText(this, "100 is a maximum number of order", Toast.LENGTH_SHORT).show();
        }
        else {
            quantity += 1;
            displayQuantity(quantity);
        }
    }

    public void decrement(View v) {
        if (quantity==1){
            Toast.makeText(this, "1 is a minimum number of order", Toast.LENGTH_SHORT).show();
        } else {
            quantity -= 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given message on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText("" + message);
    }

    private int calculatePrice(int price, boolean hasCream, boolean hasChocolate) {
        if (hasCream){
            price += 1;
        }
        if (hasChocolate){
            price += 2;
        }
        return quantity * price;
    }

    private void displayOrderSummary(int price, boolean hasCream, boolean Choco, String name){
        String priceMessage = "Name: "+ name;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nAdd Whipped Cream? " + hasCream;
        priceMessage += "\nAdd Chocolate? " + Choco;
        priceMessage += "\nTotal: $" + calculatePrice(price, hasCream, Choco);
        priceMessage += "\nThank You!";
        composeEmail("JustJava Order from "+ name, priceMessage);
        //displayMessage(priceMessage);
    }

}
