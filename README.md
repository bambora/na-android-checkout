<img src="http://www.beanstream.com/wp-content/uploads/2015/08/Beanstream-logo.png" />
# PayForm for Android

##### Table of Contents

* [Platform Support](#platform-support)
* [PayForm](#payform)
* [How It Works](#payform-functionality)
* [Demo App](#payform-demo)
* [Integration](#payform-integration)
* [Customization](#payform-customization)
* [Building Locally and Contributing](#contributing)
* [Examples](#examples)

<a name="platform-support"/>
## Android Support
 * Minimum SDK 19

<a name="payform"/>
## PayForm

PayForm is a small client-side Android library that handles customer credit card input within the merchant's app. Most apps will let users launch PayForm from something like a button action.

This Android library limits the scope of a merchant's PCI compliance by removing the need for them to pass the sensitive information (credit card number, CVD, or expiry) through their servers and from having to write and store code that comes in contact with that sensitive information.

<a name="payform-functionality"/>
## How It Works
The PayForm activity is instantiated and presented by your app code. The resulting payment form may contain input fields for a shipping address, for a billing address and for credit card details.

Once the user has completed all fields with valid input an onActivityResult, provided by you, is called and passed a PayFromResult containing address information and a token for the credit card details.

By integrating PayForm a developer can easily provide a way for users to accept payments in an Android app. PayForm provides some client-side validation, smart field data formatting and a design that works in all Android device form factors.

<a name="payform-demo"/>
## Demo App
Use this [demo](https://github.com/Beanstream/beanstream-android-payform-demo) to see how to integrate PayForm

<a name="payform-integration"/>
## Integration

#### Step 1: Setup Artifactory Credentials
Add your Artifactory credentials to ***[USER_HOME]/.gradle/gradle.properties***
```
# Artifactory Credentials
bic_artifactory_url=https://beanstream.jfrog.io/beanstream

## Replace USERNAME and PASSWORD
bic_artifactory_user=USERNAME
bic_artifactory_password=PASSWORD
```

#### Step 2: Import PayForm Library
* Add the following to your app's ***build.gradle***:
```
android {
    ...
    repositories {
        jcenter()
        maven {
            credentials {
                username "${bic_artifactory_user}"
                password "${bic_artifactory_password}"
            }
            url "${bic_artifactory_url}/libs-release"
        }
    }
}

dependencies {
    compile(group: 'com.beanstream.android', name: 'payform', version: '0.1.0', ext: 'aar')
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.android.support:cardview-v7:24.2.1'
    ...
}
```

#### Step 3: Launch PayForm Activity
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
#### Step 4: Get the Results
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

#### Step 5: Process the Token
Now that you have tokenized card data on your server, use it to either
* [process or pre-authorize a payment](http://developer.beanstream.com/documentation/take-payments/purchases/take-payment-legato-token/)
* [create a payment profile](http://developer.beanstream.com/tokenize-payments/create-new-profile/).

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
  * `$ git clone git@github.com:Beanstream/beanstream-android-payform.git`
* Open the cloned repository in Android Studio
* Fork the repo to commit changes to and issue Pull Requests as needed.

---

# API References
* [REST API](http://developer.beanstream.com/documentation/rest-api-reference/)
* [Tokenization](http://developer.beanstream.com/documentation/take-payments/purchases/take-payment-legato-token/)
* [Payment](http://developer.beanstream.com/documentation/take-payments/purchases/card/)
* [Legato](http://developer.beanstream.com/documentation/legato/)

---
