/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=0,price=5;
    String priceMessage = "",checkMessage = "";
    boolean checked = false, hasWhippedCream = false, hasChocolate = false;
    TextView priceTextView,quantityTextView;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceTextView = findViewById(R.id.price_text_view);
        quantityTextView = findViewById(R.id.quantity_text_view);
        editName = findViewById(R.id.name);
    }

    /**
     * This method is called when the order button is clicked.
     */




    public void increment(View view) {
        quantity++;
        if(quantity<=100)
            display(quantity);
        else return;
    }

    public void decrement(View view) {
        quantity--;
        if(quantity>=0)
            display(quantity);
        else return;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int quantity) {
        quantityTextView.setText("" + quantity);
        displayPrice(quantity * price);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private String displayPrice(int number) {
        String priceMessage = "";
        priceMessage+=NumberFormat.getCurrencyInstance().format(number);
        priceTextView.setText(priceMessage);
        return priceMessage;
    }

    public void afterOrder(View view) {
        checkMessage = "";
        display(quantity);
        priceMessage = getString(R.string.name) + editName.getText() + "\n";

        if(hasWhippedCream || hasChocolate){
            setCheckMessage(hasWhippedCream,hasChocolate);
        }
        else
            checkMessage += getString(R.string.no_top_added) + "\n";

        priceMessage += checkMessage;
        priceMessage += getString(R.string.total);
        priceMessage += displayPrice(quantity*price) + "\n";
        priceMessage += getString(R.string.thank_you);
        priceTextView.setText(priceMessage );
        return;
    }

    public void mail(View mailView){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, editName.getText() + "'s order summary:");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        return;
    }

    public void onCheck(View checkView) {

        checked = ((CheckBox) checkView).isChecked();

        switch (checkView.getId()) {

            case R.id.whipped_cream:
                if (checked) {
                    hasWhippedCream = true;
                    quantity += 2;
                    display(quantity);
                } else {
                    hasWhippedCream = false;
                    quantity -= 2;
                    display(quantity);
                }
                break;

            case R.id.chocolate:
                if(checked){
                    hasChocolate = true;
                    quantity+=4;
                    display(quantity);
                }
                else{
                    hasChocolate = false;
                    quantity-=4;
                    display(quantity);
                }
                break;
        }

    }

    public void setCheckMessage(
            boolean hasWhippedCream,
            boolean hasChocolate
    ){
        if(hasWhippedCream)
            checkMessage += getString(R.string.whipped_cream_added) + "\n";
        if(hasChocolate)
            checkMessage += getString(R.string.chocolate_added) + "\n";
        return;

    }
}
