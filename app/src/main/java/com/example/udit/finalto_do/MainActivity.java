package com.example.udit.finalto_do;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.drawable.arrow_down_float;
import static android.R.drawable.arrow_up_float;
import static android.R.drawable.checkbox_off_background;
import static android.R.drawable.checkbox_on_background;
import static android.R.drawable.status_bar_item_app_background;
import static com.example.udit.finalto_do.R.color.red;
import static com.example.udit.finalto_do.R.color.yellow;

public class MainActivity extends AppCompatActivity implements expenseactvity.onDownload {
    ListView linearlist;
    int n = 20;
    int k;
    private View pressedView = null;
    static int m=0;
    Toolbar toolBar;
    int i=0;
    static String color;
    ImageButton updown ;
    Boolean firsttimeuser=false;
    int count=0;
    //CheckBox checkbox;
    long arr[] = new long[50];
    ArrayList<expense> expenselist;
    ArrayList<expense> expenselist1;
   // BitmapDrawable bg =(BitmapDrawable)getResources().getDrawable(checkbox_on_background,null);
    expenselistadapter expenselistadapter;
    expenselistadapter expenselistadapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  toolbar=(Toolbar)findViewById(R.id.toolbar);
//Intent i = getIntent();
        //color=i.getStringExtra(intent.color);
       // linearlist.setBackgroundColor(Integer.parseInt(color+""));
        expenseactvity.setOnDownload(this);
//checkBox=(CheckBox)findViewById(R.id.checkboxclicked);
//        checkboxfunction();
        for (int i=0;i<50;i++)
        {
            arr[i]=0;
        }
        updown=(ImageButton)findViewById(R.id.updown);
       toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        linearlist = (ListView) findViewById(R.id.linearlist);
        expenselist = new ArrayList<>();
expenselist1 = new ArrayList<>();
        expenselistadapter = new expenselistadapter(this, expenselist);
        expenselistadapter1=new expenselistadapter(this,expenselist1);
        linearlist.setAdapter(expenselistadapter);
        updateExpensesList();
        linearlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, expenseactvity.class);
                expense e =expenselist.get(position);
                final int k= e.id;
                i.putExtra(intent.idq,k);
                startActivityForResult(i,1);
            }
        });


        linearlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            //                firsttimeuser = true;

                linearlist.setBackgroundColor(Integer.parseInt(yellow+""));
                expense e =expenselist.get(position);
                final int k= e.id;
                Toast.makeText(MainActivity.this,"id is "+e.id +"   "+k,Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("DELETE");
                builder.setMessage("are you sure you want to delete");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databasehelper expenseOpenHelper = new databasehelper(MainActivity.this);
                        SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();

                        //ContentValues cv=new ContentValues();
//                        Cursor cursor =database.query(ExpenseOpenHelper.Expense_Table_name,null,ExpenseOpenHelper.Expense_Id+"=?",coloumn,null,null,null,null);
                        database.delete(databasehelper.Expense_Table_name,databasehelper.Expense_Id+"="+k,null);
                        updateExpensesList();
                        expenselistadapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"Changes saved",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialoge =builder.create();
                dialoge.show();
                return true;
            }
        });
updateExpensesList();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,expenseactvity.class);
                startActivity(i);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

    }

    public  void updateExpensesList() {
        databasehelper expenseOpenHelper =new databasehelper(this);
        expenselist.clear();
        SQLiteDatabase database = expenseOpenHelper.getReadableDatabase();
        Cursor cursor =database.query(databasehelper.Expense_Table_name,null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            String title =cursor.getString(cursor.getColumnIndex(databasehelper.Expense_title));
            String desciption =cursor.getString(cursor.getColumnIndex(databasehelper.Expense_desciption));
            String category = cursor.getString((cursor.getColumnIndex(databasehelper.Expense_Category)));
            int id =cursor.getInt(cursor.getColumnIndex(databasehelper.Expense_Id));
            expense e = new expense(title,desciption,category,id);
            expenselist.add(e);

        }

    }
    public void onClick(View v) {
if(m==0)
{
    updown.setImageResource(android.R.drawable.arrow_up_float);

    m=1;
    linearlist.setVisibility(View.INVISIBLE);
    }
    else if(m==1)
    { m=0;
    linearlist.setVisibility(View.VISIBLE);
        updown.setImageResource(android.R.drawable.arrow_down_float);
}
}

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == 1) {
            if (resultcode == RESULT_OK) {
    //            Intent i =getIntent();
  //              color=i.getStringExtra(intent.color);
//                linearlist.setBackgroundColor(Integer.parseInt(color+""));
                updateExpensesList();
                expenselistadapter.notifyDataSetChanged();

            }
        }
        if(requestcode==2)
        {
            if (resultcode == RESULT_OK) {
                Intent i =getIntent();
                color=i.getStringExtra(intent.color);
                linearlist.setBackgroundColor(Integer.parseInt(color+""));
                updateExpensesList();
                expenselistadapter.notifyDataSetChanged();

            }

        }
        if(resultcode==4||requestcode==4)
        {
            Intent i =getIntent();
            final String title =i.getStringExtra(intent.EXPENSE_TITLE);
            String[] column ={title+""};
            Toast.makeText(MainActivity.this,"Recieved"+title,Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View v = getLayoutInflater().inflate(R.layout.dialognotificationview, null);
            TextView tv = (TextView) v.findViewById(R.id.dialogtextview);
            builder.setTitle("ADD");
            final TextView ttitle = (TextView) v.findViewById(R.id.titletextdialog);
            final TextView tdescirption = (TextView) v.findViewById(R.id.desciptiondialogtext);
            final TextView tcategory =(TextView)v.findViewById(R.id.categorydialogtext);
            databasehelper databsehelper = new databasehelper(MainActivity.this);
            SQLiteDatabase database = databsehelper.getWritableDatabase();
            Cursor cursor = database.query(databsehelper.Expense_Table_name,null,databsehelper.Expense_title+"=?",column,null,null,null);
           while(cursor.moveToNext())
           {
               if(title==cursor.getString(cursor.getColumnIndex(databsehelper.Expense_title)))
               {
                   String desc = cursor.getString(cursor.getColumnIndex(databsehelper.Expense_desciption));
                   String catgory=cursor.getString(cursor.getColumnIndex(databsehelper.Expense_Category));
                   ttitle.setText(title+"");
                   tdescirption.setText(desc);
                   tcategory.setText(catgory);
               }
           }
            tv.setText("Are you sure you wamt to delete");
            builder.setView(v);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databasehelper expenseOpenHelper = new databasehelper(MainActivity.this);
                    SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();

                    //ContentValues cv=new ContentValues();
//Cursor cursor =database.query(databasehelper.Expense_Table_name,null,databasehelper.Expense_title+"="+title,null,null,null,null,null);
                    database.delete(databasehelper.Expense_Table_name,databasehelper.Expense_title+"="+title,null);
                    updateExpensesList();
                    expenselistadapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"Changes saved",Toast.LENGTH_SHORT).show();

                    expenselistadapter.notifyDataSetChanged();

                    }

                }
            );
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            databasehelper expenseOpenHelper = new databasehelper(MainActivity.this);
            database = expenseOpenHelper.getWritableDatabase();

                        //ContentValues cv=new ContentValues();
//Cursor cursor =database.query(databasehelper.Expense_Table_name,null,databasehelper.Expense_title+"="+title,null,null,null,null,null);
                        database.delete(databasehelper.Expense_Table_name,databasehelper.Expense_title+"="+title,null);
                        updateExpensesList();
                        expenselistadapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"Changes saved",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

            //noinspection SimplifiableIfStatement

   if(id==R.id.search)
            {
                java.util.Calendar mCurrentDate = java.util.Calendar.getInstance();
                int year = mCurrentDate.get(java.util.Calendar.YEAR);
                int month = mCurrentDate.get(java.util.Calendar.MONTH);
                final int day = mCurrentDate.get(java.util.Calendar.DAY_OF_MONTH);
//                DatePickerDialog mdatepicker;
                DatePickerDialog mdatepicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int year1 = year;
                        int month1 = month;
                        int day1 = dayOfMonth;
                        //categorytextView.setText(year1+"/"+month1+"/"+day1);
                    }
                }, year, month, day);
                mdatepicker.show();
            String date = String.valueOf(year+"/"+month+"/"+day);
                databasehelper helper = new databasehelper(this);
                SQLiteDatabase databse = helper.getWritableDatabase();
                Cursor cursor = databse.query(helper.Expense_Table_name,null,null,null,null,null,null);
                while(cursor.moveToNext())
                {
                    if(date == cursor.getString(cursor.getColumnIndex(helper.Expense_date)))
                    {
                        String title = cursor.getString(cursor.getColumnIndex(helper.Expense_title));
                        String desc = cursor.getString(cursor.getColumnIndex(helper.Expense_desciption));
                        String cate = cursor.getString(cursor.getColumnIndex(helper.Expense_Category));
                        String time =cursor.getString(cursor.getColumnIndex(helper.Expense_time));
                        int  id1 = cursor.getInt(cursor.getColumnIndex(helper.Expense_Id));
                        expense e = new expense(title,desc,cate,id1);
                        expenselist1.add(e);
                        expenselistadapter1.notifyDataSetChanged();
                    }
                }
    }

   else if (id == R.id.add) {
                Intent i = new Intent(MainActivity.this, expenseactvity.class);
                startActivityForResult(i, 1);
                updateExpensesList();
            } else if (id == R.id.aboutus) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://wwww.codingninjas.in");
                i.setData(uri);
                startActivity(i);
            } else if (id == R.id.Contactus) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel:9996522222");
                i.setData(uri);
                startActivity(i);
            } else if (id == R.id.feedback) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SENDTO);
                Uri uri = Uri.parse("mailto:msxjndn@gmail.com");
                i.setData(uri);
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
            else if(id==R.id.select)
            {
                count =9;
                linearlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        toolBar.setTitle("Select to delete");
                        i=i+1;
                        if(pressedView!=null) {
                            view.setBackgroundResource(R.color.colorPrimary);
                            arr[i] = id;
                            pressedView=view;
                        }
                    }
                });
            }

           if(id==R.id.delete) {
                if (count == 9) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("DELETE");
                    builder.setMessage("are you sure you want to delete");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            while (arr[i] != 0) {
                                long id1 = arr[i];
                                databasehelper expenseOpenHelper = new databasehelper(MainActivity.this);
                                SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
                                database.delete(expenseOpenHelper.Expense_Table_name, expenseOpenHelper.Expense_Id + "=" + id1, null);
                                updateExpensesList();
                                expenselistadapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
                                i++;
                            }
                        }
                    });

                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialoge = builder.create();
                    dialoge.show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("DELETE");
                    builder.setMessage("are you sure you want to delete");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                                expenselist.clear();
                                databasehelper expenseOpenHelper = new databasehelper(MainActivity.this);
                                SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
                                database.delete(expenseOpenHelper.Expense_Table_name,null, null);
                                updateExpensesList();
                                expenselistadapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();


                        }
                    });

                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialoge = builder.create();
                    dialoge.show();
                }
            }
           return true;

    }
}

