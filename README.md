# NA Android Checkout

##### Table of Contents

* [Platform Support](#platform-support)
* [Overview](#overview)
* [How It Works](#functionality)
* [Demo App](#demo)
* [Integration](#integration)
* [Customization](#customization)
* [Building Locally and Contributing](#contributing)
* [Examples](#examples)

<a name="platform-support"/>

## Android Support
 * Minimum SDK 19

<a name="overview"/>

## Overview

NA Android Checkout is a small client-side Android library that handles customer credit card input within the merchant's app. Most apps will let users launch Android Checkout from something like a button action.

This Android library limits the scope of a merchant's PCI compliance by removing the need for them to pass the sensitive information (credit card number, CVD, or expiry) through their servers and from having to write and store code that comes in contact with that sensitive information.

<a name="functionality"/>

## How It Works
The Checkout activity is instantiated and presented by your app code. The resulting form may contain input fields for a shipping address, for a billing address and for credit card details.

Once the user has completed all fields with valid input an onActivityResult, provided by you, is called and passed a CheckoutResult containing address information and a token for the credit card details.

By integrating Android Checkout a developer can easily provide a way for users to accept payments in an Android app. Checkout provides some client-side validation, smart field data formatting and a design that works in all Android device form factors.

<a name="demo"/>

## Demo App
Use this [demo](https://github.com/bambora/na-android-checkout-demo) to see how to integrate NA Android Checkout

<a name="integration"/>

## Integration

#### Step 1: Import NA Android Checkout Library
* Add the following to your app's ***build.gradle***:
```
android {
    ...
    repositories {
        jcenter()
        maven {
            url "https://bambora.jfrog.io/bambora/na-libs-release"
        }
    }
}

dependencies {
    compile(group: 'com.bambora.android', name: 'checkout', version: '0.2.0', ext: 'aar')
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.android.support:cardview-v7:24.2.1'
    ...
}
```

#### Step 2: Launch Checkout Activity
From your app you will need to launch the activity with ***options*** and ***purchase*** information.

***Example:***
```
private void startCheckout() {
  Options options = getOptionsForThisDemo();
  Purchase purchase = getPurchaseForThisDemo();

  Intent intent = new Intent(CheckoutActivity.ACTION_CHECKOUT_LAUNCH);
  intent.putExtra(CheckoutActivity.EXTRA_OPTIONS, options);
  intent.putExtra(CheckoutActivity.EXTRA_PURCHASE, purchase);

  startActivityForResult(intent, CheckoutActivity.REQUEST_CHECKOUT);
}
```
#### Step 3: Get the Results
You will need to collect the ***CheckoutResult*** that contains the ***cardInfo.code*** that is your payment processing token.

***Example:***
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  if (requestCode == CheckoutActivity.REQUEST_CHECKOUT) {
    String error = "";
    CheckoutResult result = null;

    if (resultCode == Activity.RESULT_OK) {
    	result = data.getParcelableExtra(CheckoutActivity.EXTRA_CHECKOUT_RESULT);
    } else if (resultCode == Activity.RESULT_CANCELED) {
    	error = getResources().getString(R.string.demo_checkout_cancelled);
    } else {
    	error = getResources().getString(R.string.demo_checkout_error);
    	result = data.getParcelableExtra(CheckoutActivity.EXTRA_CHECKOUT_RESULT);
    }

    showError(error);
    showResults(result);
  }
}
```

#### Step 4: Process the Token
Now that you have tokenized card data on your server, you can [take payments](http://dev.na.bambora.com/docs/references/merchant_SDKs/take_payments) either by
* processing or pre-authorizing a payment
* creating a payment profile

<a name="customization"/>

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
| companyName  | your company name |
| isBillingAddressRequired  | if the billing address is required - **true/false** |
| isShippingAddressRequired  | if the shipping address is required - **true/false** |
| themeResourceId  | the resourceId of the custom theme |
| tokenRequestTimeoutInSeconds  | maximum time to wait during the token request |

##### Optional Theme Resource
To set custom colors you will need to add ***res/values/themes.xml*** to your project and pass in the resource id to Checkout.

***Example:***
```
<!--res/values/themes.xml-->
<resources>
    <style name="Theme.Checkout.Custom" parent="Theme.Checkout">
        <!--primary-->
        <item name="android:textColorPrimary">@color/checkoutSecondary</item>
        <item name="colorPrimary">@color/checkoutPrimary</item>

        <!--accents-->
        <item name="colorAccent">@color/checkoutAccent</item>

        <!--backgrounds-->
        <item name="android:colorBackground">@color/checkoutBackground</item>
        <item name="android:windowBackground">@color/checkoutBackground</item>
    </style>
</resources>
```

<a name="contributing"/>

## Building Locally and Contributing
* Clone repository:
  * `$ git clone git@github.com:bambora/na-android-checkout.git`
* Open the cloned repository in Android Studio
* Fork the repo to commit changes to and issue Pull Requests as needed.

---

# API References
* [Payments REST API](http://dev.na.bambora.com/docs/references/merchant_API/)
* [Take Payments](http://dev.na.bambora.com/docs/references/merchant_SDKs/take_payments)

---
