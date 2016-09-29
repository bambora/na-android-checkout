<img src="http://www.beanstream.com/wp-content/uploads/2015/08/Beanstream-logo.png" />
# Beanstream PayForm for Android

##### Table of Contents

* [Overview](#overview)
* [Platform Support](#platform-support)
* [PayForm](#payform)
* [How It Works](#payform-functionality)
* [Integration](#payform-integration)
* [Customization](#payform-customization)
* [Building Locally and Contributing](#contributing)
* [Examples](#examples)

<a name="overview"/>
## Overview

PayForm is a small Android framework project that you can add to your app project. Most apps will let users launch PayForm to gather credit card details from something like a button action.

<a name="platform-support"/>
## Android Support
 * Minimum SDK 19

<a name="payform"/>
## PayForm

PayForm is a Beanstream client-side Android framework that handles customer credit card input within the merchant's app. This Android framework limits the scope of a merchant's PCI compliance by removing the need for them to pass the sensitive information (credit card number, CVD, or expiry) through their servers and from having to write and store code that comes in contact with that sensitive information.

By integrating PayForm a developer can easily provide a way for users to accept payments in an Android app. PayForm provides some client-side validation, smart field data formatting and a design that works in all Android device form factors.

<a name="payform-functionality"/>
## How It Works
The PayForm activity is instantiated and presented by your app code. The resulting payment form may contain input fields for a shipping address, for a billing address and for credit card details.

Once the user has completed all fields with valid input an onActivityResult, provided by you, is called and passed a PayFromResult containing address information and a token for the credit card details.

---

<a name="payform-integration"/>
## Integration
Adding PayForm to your app could not be easier.

#### Step 1: Setup Dev Tools
* Setup artifactory

#### Step 2: Add PayForm To Your App
Add PayForm to your application.

Example: [Add PayForm](#examples-payform-activity)

#### Step 3: Process The Payment

Whether you collect the tokenized card data and send it asynchronously to your server, or take any other action, you will need to collect the cardInfo code string value that is your token to process the payment with.

Now that you have tokenized card data on your server, use it to either [process or pre-authorize a payment](http://developer.beanstream.com/documentation/take-payments/purchases/take-payment-legato-token/), or create a [payment profile](http://developer.beanstream.com/tokenize-payments/create-new-profile/).

---
<a name="payform-customization"/>
## Customization
You can supply several parameters to configure the form, such as your company name, logo, product description, price, currency, and whether billing/shipping addresses should be displayed.

##### Required Parameters
|  Parameter | Description |
| :------------- | :------------- |
| amount  | amount you are going to charge the customer |
| currency  |  currency being used for this purchase  |

##### Optional parameters:
|  Parameter | Description |
| :------------- | :------------- |
| description  | description of the purchase  |
| companyLogoResourceId  | your company logo's resource id |
| companyName  | your company logo's resource id |
| companyLogoResourceId  | your company name |
| isBillingAddressRequired  | if the billing address is required - **true/false** |
| isShippingAddressRequired  | if the shipping address is required - **true/false** |
| themeResourceId  | the resourceId of the custom theme |
| tokenRequestTimeoutInSeconds  | maximum time to wait during the token request |

##### Optional Theme Resource
To set custom colors for the PayForm you will need to add ***res/values/themes.xml*** to your project and pass in the resource id to the PayForm.

***Example:***
```
<!--res/values/themes.xml-->
<resources>

    <style name="Theme.PayFormCustom" parent="Theme.PayForm">
        <!--primary-->
        <item name="android:textColorPrimary">@color/demoSecondary</item>
        <item name="colorPrimary">@color/demoPrimary</item>

        <!--accents-->
        <item name="colorAccent">@color/demoAccent</item>

        <!--backgrounds-->
        <item name="android:colorBackground">@color/demoBackground</item>
        <item name="android:windowBackground">@color/demoBackground</item>
    </style>
</resources>
```

---

<a name="contributing"/>
## Building Locally and Contributing
* Clone repository:
  * `$ git clone git@github.com:Beanstream/beanstream-android-payform.git`
* Open the cloned repository in Android Studio
* Fork the repo to commit changes to and issue Pull Requests as needed.

---

# API References
* [REST API](http://developer.beanstream.com/documentation/rest-api-reference/)
* [Tokenization](http://developer.beanstream.com/documentation/take-payments/purchases/take-payment-legato-token/)
* [Payment](http://developer.beanstream.com/documentation/take-payments/purchases/card/)
* [Legato](http://developer.beanstream.com/documentation/legato/)


<a name="examples"/>
## Examples


<a name="examples-payform-activity"/>
### PayForm Activity Example
An example of launching PayForm from a button click and then displaying the results.

```
public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final Button button = (Button) findViewById(R.id.demo_pay_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startPayForm();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PayFormActivity.REQUEST_PAYFORM) {
            String error = "";
            PayFormResult result = null;

            if (resultCode == Activity.RESULT_OK) {
                result = data.getParcelableExtra(PayFormActivity.EXTRA_PAYFORM_RESULT);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                error = getResources().getString(R.string.demo_payform_cancelled);
            } else {
                error = getResources().getString(R.string.demo_payform_error);
                result = data.getParcelableExtra(PayFormActivity.EXTRA_PAYFORM_RESULT);
            }

            showError(error);
            showResults(result);
        }
    }

    private void startPayForm() {

        Currency currency = Currency.getInstance(Purchase.CURRENCY_CODE_CANADA);

        // Required Purchase parameters
        Purchase purchase = getPurchaseForThisDemo();
        Purchase purchase = new Purchase(123.45, currency);

		// Optional Purchase parameters
        purchase.setDescription("Item 1, Item 2, Item 3, Item 4"); // default: ""

        // Optional parameters
        Options options = getOptionsForThisDemo();

        options.setCompanyLogoResourceId(R.drawable.custom_company_logo); // default: null
        options.setCompanyName("Cabinet of Curiosities"); // default: ""

        options.setIsBillingAddressRequired(false); // default: true
        options.setIsShippingAddressRequired(false); // default: true

        options.setThemeResourceId(R.style.Theme_PayFormCustom); // default: Theme.PayForm
        options.setTokenRequestTimeoutInSeconds(7); // default: 6

        // Launch PayForm
        Intent intent = new Intent("payform.LAUNCH");
        intent.putExtra(PayFormActivity.EXTRA_OPTIONS, options);
        intent.putExtra(PayFormActivity.EXTRA_PURCHASE, purchase);

        startActivityForResult(intent, PayFormActivity.REQUEST_PAYFORM);
    }

    private void showError(String error) {
        TextView text = (TextView) findViewById(R.id.demo_payform_error);
        text.setText(error);
    }

    private void showResults(PayFormResult payFormResult) {
        String result = "";
        try {
            if (payFormResult != null) {
                result = payFormResult.toJsonObject().toString(4);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("showResults", result);

        TextView text = (TextView) findViewById(R.id.demo_payform_results);
        text.setText(result);
        text.setVisibility(View.VISIBLE);
        text.setMovementMethod(new ScrollingMovementMethod());
    }
}
```

---
