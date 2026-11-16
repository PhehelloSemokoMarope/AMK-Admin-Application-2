AMK Admin Application

AMK Admin (com.example.amkadminapplication) is a native Android application built with Kotlin and XML layouts for fashion design and studio management. It connects to Firebase Authentication and Cloud Firestore under the amk-clothing Firebase project to support staff access and studio operations.

Features and Screens

- Authentication and Entry: MainActivity, WelcomeActivity, LoginActivity, and RegisterActivity handle app routing, staff/designer sign-in, and account registration.
- Dashboard: HomepageActivity provides navigation across system modules, sign-out, and live order count metrics.
- Customers and Orders: CustomerManagementActivity and CreateOrderActivity manage customer records and order creation.
- Products, Inventory, and Stock Updates: ManageProductsActivity, AddItemsActivity, and QuickUpdateActivity handle product uploads, inventory item entry, and update logging.
- Staff, Measurements, Payments, Messages, and Reports: StaffManagementActivity, RecordMeasurementsActivity, ProcessPaymentActivity, AdminMessagesActivity, and ReportActivity support team management, client measurements, payment recording, messages, and order/inventory reports.

Firebase and Firestore Setup

- Firebase Project: Connected to amk-clothing via app/google-services.json for package com.example.amkadminapplication.
- Firestore Collections: Aligned to lowercase collections in the amk-clothing database, including customers, orders, inventory, staff, products, payments, measurements, and stock_updates.
