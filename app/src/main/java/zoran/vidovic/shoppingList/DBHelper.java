package zoran.vidovic.shoppingList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class DBHelper extends SQLiteOpenHelper {

    public static final String DbName = "shared_list_app.db";

    public static final String users_tableName  = "users";
    public static final String users_username = "username" ;
    public static final String users_password = "password";
    public static final String users_email = "email";

    public static final String lists_tableName  = "LISTS";
    public static final String lists_listName = "listName";
    public static final String lists_creatorName = "creatorName";
    public static final String lists_shared = "shared";

    public static final String items_table_name = "ITEMS";
    public static final String items_column_name = "name";
    public static final String items_column_list_name = "listName";
    public static final String items_column_checked = "checked";
    public static final String items_column_id = "id";




    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + users_tableName +
                " (" + users_username + " TEXT, " +
                users_email + " TEXT, " +
                users_password + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + lists_tableName +
                " (" + lists_listName + " TEXT, " +
                lists_creatorName + " TEXT, " +
                lists_shared + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + items_table_name +
                " (" + items_column_name + " TEXT, " +
                items_column_list_name + " TEXT, " +
                items_column_checked + " TEXT," +
                items_column_id + " TEXT);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertUser(String username, String email, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(users_username, username);
        values.put(users_email, email);
        values.put(users_password, password);

        db.insert(users_tableName, null, values);
        close();
    }
    public void insertList(Lists l, String user){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(lists_listName, l.getName());
        values.put(lists_creatorName,user);
        values.put(lists_shared,l.getShared());

        db.insert(lists_tableName,null, values);
        close();
    }
    public void insertItem(Items i,String uID, String list) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(items_column_name, i.getItems());
        values.put(items_column_list_name, list);
        values.put(items_column_checked, Boolean.toString(i.getBox()));
        values.put(items_column_id, uID);

        db.insert(items_table_name, null, values);
        close();
    }

    public boolean checkUsernamePw(String username, String password){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(users_tableName, null, users_username + "=? AND " + users_password + "=?",
                new String[] { username, password }, null, null, null);
        if(cursor.getCount() <= 0) {
            return false;
        }

        close();
        return true;
    }

    public boolean checkUsername(String username){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(users_tableName, null, users_username + "=?",
                new String[] { username }, null, null, null);
        if(cursor.getCount() <= 0) {
            return true;
        }

        close();
        return false;
    }

    //// LISTS
    public boolean doesListExist(String listname){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(lists_tableName, null, lists_listName + "=?",
                new String[] { listname }, null, null, null);
        if(cursor.getCount() <= 0) {
            return false;
        }

        close();
        return true;
    }

    private Lists createList(Cursor cursor) {
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow(lists_listName));
        String mShared = cursor.getString(cursor.getColumnIndexOrThrow(lists_shared));

        return new Lists(mTitle, mShared);
    }

    public Lists[] readAllLists(String user) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(lists_tableName, null, lists_creatorName + "=? OR " + lists_shared + "=?",
                new String[] {user, "Yes"}, null, null, null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        Lists[] lists = new Lists[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            lists[i++] = createList(cursor);
        }

        close();
        return lists;
    }

    public Lists[] readUserLists(String user) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(lists_tableName, null, lists_creatorName + "=?",
                new String[] {user}, null, null, null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        Lists[] lists = new Lists[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            lists[i++] = createList(cursor);
        }

        close();
        return lists;
    }

    public void deleteList (String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(lists_tableName, lists_listName + "=?", new String[] { name });

        close();
    }
    //// ITEMS

    public boolean doesTaskExist(String taskname, String uID){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(items_table_name, null, items_column_name + "=?", new String[] { taskname }, null, null, null);
        if(cursor.getCount() <= 0) {
            return false;
        }

        close();
        return true;
    }
    public Items[] readItems (String list) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(items_table_name, null, items_column_list_name + "=?", new String[] {list}, null, null, null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        Items[] lists = new Items[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            lists[i++] = createItem(cursor);
        }

        close();
        return lists;
    }
    private Items createItem (Cursor cursor) {
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow(items_column_name));
        String mChecked = cursor.getString(cursor.getColumnIndexOrThrow(items_column_checked));
        String uID = cursor.getString(cursor.getColumnIndexOrThrow(items_column_id));
        return new Items(mTitle, Boolean.valueOf(mChecked), uID);
    }
    public void deleteItem (String uID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(items_table_name, items_column_id + " =?", new String[] { uID });
        close();
    }
    public void updateItem(String uID, String mChecked){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(items_table_name, null, items_column_id + "=?", new String[] {uID}, null, null, null);
        cursor.moveToFirst();
        String title = cursor.getString(cursor.getColumnIndexOrThrow(items_column_name));
        String listName =  cursor.getString(cursor.getColumnIndexOrThrow(items_column_list_name));

        ContentValues cv = new ContentValues();
        cv.put(items_column_name, title);
        cv.put(items_column_list_name, listName);
        cv.put(items_column_checked, mChecked);
        cv.put(items_column_id, uID);

        db.update(items_table_name, cv, items_column_id + "=?", new String[] {uID});
        close();
    }

}

