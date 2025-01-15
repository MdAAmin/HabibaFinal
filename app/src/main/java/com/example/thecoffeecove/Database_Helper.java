package com.example.thecoffeecove;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database_Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hiba.db";
    private static final int DATABASE_VERSION = 2; // Updated version to handle database upgrades

    // Table Names
    private static final String TABLE_ADMIN = "admin";
    private static final String TABLE_PRODUCT = "product";
    private static final String TABLE_ORDERS = "orders"; // Added orders table

    // Column Names for Admin Table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Product Table Column Names
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
    public static final String COLUMN_PRODUCT_IMAGE = "image";

    // Orders Table Column Names
    public static final String COLUMN_ORDER_ID = "_id"; // Order ID
    public static final String COLUMN_ORDER_PRODUCT_NAME = "product_name";
    public static final String COLUMN_ORDER_PRICE = "price";
    public static final String COLUMN_ORDER_QUANTITY = "quantity";
    public static final String COLUMN_ORDER_INSTRUCTION = "order_instruction"; // Added column for order instructions
    public static final String COLUMN_ORDER_COFFEE_TYPE = "coffee_type";  // Added coffee type column

    // Default Admin
    private static final String DEFAULT_ADMIN_USERNAME = "habiba";
    private static final String DEFAULT_ADMIN_PASSWORD = "12345hH@";

    public Database_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Admin Table
        String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_ADMIN_TABLE);

        // Create Product Table
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_PRODUCT_PRICE + " REAL,"
                + COLUMN_PRODUCT_QUANTITY + " INTEGER,"
                + COLUMN_PRODUCT_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);

        // Create Orders Table with coffee type
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_PRODUCT_NAME + " TEXT,"
                + COLUMN_ORDER_PRICE + " REAL,"
                + COLUMN_ORDER_QUANTITY + " INTEGER,"
                + COLUMN_ORDER_COFFEE_TYPE + " TEXT" + ")"; // Added coffee_type
        db.execSQL(CREATE_ORDERS_TABLE);

        // Add a default admin user
        addAdmin(db, DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add the orders table if upgrading from version 1
            String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                    + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ORDER_PRODUCT_NAME + " TEXT,"
                    + COLUMN_ORDER_PRICE + " REAL,"
                    + COLUMN_ORDER_QUANTITY + " INTEGER,"
                    + COLUMN_ORDER_INSTRUCTION + " TEXT,"
                    + COLUMN_ORDER_COFFEE_TYPE + " TEXT" + ")"; // Added coffee_type
            db.execSQL(CREATE_ORDERS_TABLE);
        }
    }

    private void addAdmin(SQLiteDatabase db, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_ADMIN, null, values);
    }

    public boolean checkAdmin(String username, String password) {
        return checkCredentials(TABLE_ADMIN, username, password);
    }

    private boolean checkCredentials(String tableName, String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public void insertProduct(String name, double price, int quantity, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(COLUMN_PRODUCT_IMAGE, imageByteArray);
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCT, null, null, null, null, null, null);
    }

    public Cursor getProductByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_PRICE, COLUMN_PRODUCT_QUANTITY, COLUMN_PRODUCT_IMAGE};
        String selection = COLUMN_PRODUCT_NAME + "=?";
        String[] selectionArgs = {productName};
        return db.query(TABLE_PRODUCT, columns, selection, selectionArgs, null, null, null);
    }

    public boolean deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, COLUMN_PRODUCT_NAME + "=?", new String[]{productName});
        db.close();
        return true;
    }

    public void updateProduct(int productId, String productName, double price, int quantity, byte[] productImageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(COLUMN_PRODUCT_IMAGE, productImageByteArray);
        db.update(TABLE_PRODUCT, values, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public boolean insertOrder(String productName, double price, int quantity, String coffeeType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_PRODUCT_NAME, productName);
        values.put(COLUMN_ORDER_PRICE, price);
        values.put(COLUMN_ORDER_QUANTITY, quantity);
        values.put(COLUMN_ORDER_COFFEE_TYPE, coffeeType); // Insert coffee type

        long result = db.insert(TABLE_ORDERS, null, values);
        db.close();

        return result != -1; // Return true if insert was successful, false otherwise
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS, null, null, null, null, null, null);
    }
}
