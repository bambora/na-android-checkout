# NA Android SDK

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

NA Android SDK is a small client-side Android library that handles customer credit card input within the merchant's app. Most apps will let users launch NA Android SDK from something like a button action.

This Android library limits the scope of a merchant's PCI compliance by removing the need for them to pass the sensitive information (credit card number, CVD, or expiry) through their servers and from having to write and store code that comes in contact with that sensitive information.

<a name="functionality"/>

## How It Works
The payment form activity is instantiated and presented by your app code. The resulting payment form may contain input fields for a shipping address, for a billing address and for credit card details.

Once the user has completed all fields with valid input an onActivityResult, provided by you, is called and passed a PayFromResult containing address information and a token for the credit card details.

By integrating NA Android SDK a developer can easily provide a way for users to accept payments in an Android app. NA Android SDK provides some client-side validation, smart field data formatting and a design that works in all Android device form factors.

<a name="demo"/>

## Demo App
Use this [demo](https://github.com/bambora/na-android-sdk-demo) to see how to integrate NA Android SDK

<a name="integration"/>

## Integration

#### Step 1: Import NA Android SDK Library
* Add the following to your app's ***build.gradle***:
```
android {
    ...
    repositories {
        jcenter()
        maven {
            url "https://beanstream.jfrog.io/beanstream/libs-release"
        }
    }
}

dependencies {
    compile(group: 'com.bambora.android', name: 'payform', version: '0.1.1', ext: 'aar')
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.android.support:cardview-v7:24.2.1'
    ...
}
```

#### Step 2: Launch Payment Form Activity
From your app you will need to launch the activity with ***options*** and ***purchase*** information.

***Example:***
```
private void startPayForm() {
  Options options = getOptionsForThisDemo();
  Purchase purchase = getPurchaseForThisDemo();

  Intent intent = new Intent(PayFormActivity.ACTION_PAYFORM_LAUNCH);
  intent.putExtra(PayFormActivity.EXTRA_OPTIONS, options);
  intent.putExtra(PayFormActivity.EXTRA_PURCHASE, purchase);

  startActivityForResult(intent, PayFormActivity.REQUEST_PAYFORM);
}
```
#### Step 3: Get the Results
You will need to collect the ***PayFromResult*** that contains the ***cardInfo.code*** that is your payment processing token.

***Example:***
```
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
To set custom colors for the payment form you will need to add ***res/values/themes.xml*** to your project and pass in the resource id to the payment form.

***Example:***
```
<!--res/values/themes.xml-->
<resources>
    <style name="Theme.PayForm.Custom" parent="Theme.PayForm">
        <!--primary-->
        <item name="android:textColorPrimary">@color/payformSecondary</item>
        <item name="colorPrimary">@color/payformPrimary</item>

        <!--accents-->
        <item name="colorAccent">@color/payformAccent</item>

        <!--backgrounds-->
        <item name="android:colorBackground">@color/payformBackground</item>
        <item name="android:windowBackground">@color/payformBackground</item>
    </style>
</resources>
```

<a name="contributing"/>

## Building Locally and Contributing
* Clone repository:
  * `$ git clone git@github.com:bambora/na-android-sdk.git`
* Open the cloned repository in Android Studio
* Fork the repo to commit changes to and issue Pull Requests as needed.

---

# API References
* [Payments REST API](http://dev.na.bambora.com/docs/references/merchant_API/)
* [Take Payments](http://dev.na.bambora.com/docs/references/merchant_SDKs/take_payments)

---
