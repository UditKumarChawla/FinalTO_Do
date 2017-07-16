package com.example.udit.finalto_do;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.net.InterfaceAddress;
import java.sql.Time;
import java.util.Calendar;

import static com.example.udit.finalto_do.R.color.colorPrimaryDark;
import static com.example.udit.finalto_do.R.color.colorgreen;
import static com.example.udit.finalto_do.R.color.darkpink;
import static com.example.udit.finalto_do.R.color.dim_foreground_material_light;
import static com.example.udit.finalto_do.R.color.drkred;
import static com.example.udit.finalto_do.R.color.fade;
import static com.example.udit.finalto_do.R.color.pink;
import static com.example.udit.finalto_do.R.color.red;
//import static com.example.udit.finalto_do.R.id.coloraccent;
import static com.example.udit.finalto_do.R.color.yellow;
import static com.example.udit.finalto_do.R.id.colordarkred;
import static com.example.udit.finalto_do.R.id.colorfade;
import static com.example.udit.finalto_do.R.id.colorprimary;
import static com.example.udit.finalto_do.R.id.expand_activities_button;

public class expenseactvity extends AppCompatActivity {
    String title;
    String desciption;
    String category;
    String id;
    static int color;
    int year;
    int month;
    int day;
    Button datebutton;
    Button timebutton;
    EditText datetext;
    EditText titletextView;
    EditText categorytextView;
    EditText desciptiontextView;
    EditText idtextview;
    LinearLayout linear;
    static int count;
    int idq = 0;
    long time;
    String title234;
    String newTitle;
    int year1, month1, day1, hour1, minute1;
    TimePicker ktimepicker;
    DatePicker kdatepicker;
    EditText timetext;
    TimePickerDialog mTimePicker;
    DatePickerDialog mdatepicker;
    static onDownload mListener;

    static void setOnDownload(onDownload listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenseactvity);
        titletextView = (EditText) findViewById(R.id.expensedetailtextview);
        desciptiontextView = (EditText) findViewById(R.id.desciptiontextview);
        datetext = (EditText) findViewById(R.id.dateedittext);
        timetext = (EditText) findViewById(R.id.timeedittext);
        linear = (LinearLayout) findViewById(R.id.linearlayout);
        categorytextView = (EditText) findViewById(R.id.categorytextview);
        datebutton = (Button) findViewById(R.id.datebutton);
        timebutton = (Button) findViewById(R.id.timebutton);
        datebutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                java.util.Calendar mCurrentDate = java.util.Calendar.getInstance();
                int year = mCurrentDate.get(java.util.Calendar.YEAR);
                int month = mCurrentDate.get(java.util.Calendar.MONTH);
                final int day = mCurrentDate.get(java.util.Calendar.DAY_OF_MONTH);
//                DatePickerDialog mdatepicker;
                mdatepicker = new DatePickerDialog(expenseactvity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datetext.setText(year + "/" + (month + 1) + "/" + dayOfMonth + "");
                        year1 = year;
                        month1 = month;
                        day1 = dayOfMonth;
                        //categorytextView.setText(year1+"/"+month1+"/"+day1);
                    }
                }, year, month, day);
                mdatepicker.show();
            }
        });
        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar mcurrentTime = java.util.Calendar.getInstance();
                int hour = mcurrentTime.get(java.util.Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(java.util.Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(expenseactvity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timetext.setText(selectedHour + ":" + selectedMinute);
                        hour1 = selectedHour;
                        minute1 = selectedMinute;
                        calendarsetting();
                    }
                }, hour, minute, false);
                mTimePicker.show();

            }
        });
        Intent i = getIntent();
        idq = i.getIntExtra(intent.idq, 0);
        databasehelper expenseOpenHelper = new databasehelper(this);
        final SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
        final Cursor cursor = database.query(databasehelper.Expense_Table_name, null, null, null, null, null, null);
        //final Cursor cursor1=database.query(databasehelper.Table_name,null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            if (idq == cursor.getInt(cursor.getColumnIndex(databasehelper.Expense_Id))) {
                String title = cursor.getString(cursor.getColumnIndex(databasehelper.Expense_title));
                String desciption = cursor.getString(cursor.getColumnIndex(databasehelper.Expense_desciption));
                String category = cursor.getString((cursor.getColumnIndex(databasehelper.Expense_Category)));
                String date = cursor.getString((cursor.getColumnIndex(databasehelper.Expense_date)));
                String time = cursor.getString((cursor.getColumnIndex(databasehelper.Expense_time)));
                titletextView.setText("" + title);
                desciptiontextView.setText("" + desciption);
                categorytextView.setText("" + category);
                datetext.setText(""+date);
                timetext.setText(""+time);
                setResult(RESULT_OK);
            }
        }

        //Toast.makeText(expenseactvity.this,System.currentTimeMillis()+"", Toast.LENGTH_LONG).show();
        //titletextView.setText(System.currentTimeMillis()+"");
        Button submit = (Button) findViewById(R.id.expensedetailbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                count = count + 1;
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year1);
                cal.set(Calendar.MONTH, month1);
                cal.set(Calendar.DAY_OF_MONTH, day1);
                cal.set(Calendar.HOUR_OF_DAY, hour1);
                cal.set(Calendar.MINUTE, minute1);
                long time = cal.getTimeInMillis();
                newTitle = titletextView.getText().toString();
                String newdesciption = desciptiontextView.getText().toString();
                String date1 = datetext.getText().toString();
                String time1 = timetext.getText().toString();
                String newcategory = categorytextView.getText().toString();
                 String id = idtextview.getText().toString();
                databasehelper expenseOpenHelper = new databasehelper(expenseactvity.this);
                SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                //ContentValues cv1 =new ContentValues();
                cv.put(databasehelper.Expense_title, newTitle);
                cv.put(databasehelper.Expense_Category, newcategory);
                cv.put(databasehelper.Expense_desciption, newdesciption);
                cv.put(databasehelper.Expense_time,time1);
                cv.put(databasehelper.Expense_date,date1);
                database.insert(databasehelper.Expense_Table_name, null, cv);
                //               database.insert(databasehelper.Table_name,null,cv);
                finish();
                count = count + 1;
                AlarmManager am = (AlarmManager) expenseactvity.this.getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(expenseactvity.this, Alarmreceiver.class);
                i.putExtra(intent.id, count);
                i.putExtra(intent.EXPENSE_TITLE, newTitle);
             //   startActivityForResult(i,4);
               // Toast.makeText(expenseactvity.this, newTitle + "", Toast.LENGTH_SHORT).show();
                //Toast.makeText(expenseactvity.this, time + "", Toast.LENGTH_LONG).show();

                Intent i1 = new Intent(expenseactvity.this, MainActivity.class);
                i1.putExtra(intent.color, color);
                Toast.makeText(expenseactvity.this, color + "", Toast.LENGTH_LONG).show();
                //i.putExtra(intent.EXPENSE_TITLE,title234);
setResult(RESULT_OK,i1);
//                startActivityForResult(i,4);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(expenseactvity.this, count, i, 0);
                am.set(AlarmManager.RTC, time, pendingIntent);
                Toast.makeText(expenseactvity.this, "alarm setting for" + count, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void notification(View v) {
        count = count + 1;
        AlarmManager am = (AlarmManager) expenseactvity.this.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(expenseactvity.this, Alarmreceiver.class);
        i.putExtra(intent.id, count);
        Toast.makeText(expenseactvity.this, time + "", Toast.LENGTH_LONG).show();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(expenseactvity.this, count, i, 0);
        am.set(AlarmManager.RTC, time, pendingIntent);
        Toast.makeText(expenseactvity.this, "alarm setting for" + count, Toast.LENGTH_SHORT).show();
        Intent i1 = new Intent(expenseactvity.this, MainActivity.class);
        i1.putExtra(intent.color, color);
        Toast.makeText(expenseactvity.this, color + "", Toast.LENGTH_LONG).show();
        //i.putExtra(intent.EXPENSE_TITLE,title234);
        startActivityForResult(i1,4);
        setResult(RESULT_OK, i1);
        finish();

    }

    public void clear(View v) {
        datetext.setText(" ");
        timetext.setText(" ");
        categorytextView.setText(" ");
        titletextView.setText(" ");
        desciptiontextView.setText(" ");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.delete) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(expenseactvity.this);
            builder.setTitle("DELETE");
            builder.setMessage("are you sure you want to delete");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databasehelper expenseOpenHelper = new databasehelper(expenseactvity.this);
                    SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();

                    //ContentValues cv=new ContentValues();
//                        Cursor cursor =database.query(ExpenseOpenHelper.Expense_Table_name,null,ExpenseOpenHelper.Expense_Id+"=?",coloumn,null,null,null,null);
                    database.delete(databasehelper.Expense_Table_name, databasehelper.Expense_Id + "=" + idq, null);

                    Toast.makeText(expenseactvity.this, "Changes saved", Toast.LENGTH_SHORT).show();
                    Intent i1 = new Intent(expenseactvity.this, MainActivity.class);
                    setResult(RESULT_OK, i1);
                    startActivity(i1);

                }
            });

            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent i1 = new Intent(expenseactvity.this, MainActivity.class);
                    setResult(RESULT_OK, i1);


                }
            });
            AlertDialog dialoge = builder.create();
            dialoge.show();
        } else if (id == R.id.color) {
            final Dialog dialog = new Dialog(expenseactvity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_view);

            dialog.findViewById(R.id.colorred).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linear.setBackgroundColor(Integer.parseInt(red + ""));
                    color = red;
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.colorpink).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linear.setBackgroundColor(Integer.parseInt(pink + ""));
                    color = pink;
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.colorprimary).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    color = colorPrimaryDark;
                    linear.setBackgroundColor(Integer.parseInt(colorPrimaryDark + ""));
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.colorgreen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    color = colorgreen;
                    linear.setBackgroundColor(Integer.parseInt(colorgreen + ""));
                    dialog.dismiss();
                }
            });

            dialog.show();
        } else if (id == R.id.textcolor) {
            final Dialog dialog = new Dialog(expenseactvity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogview2);
            dialog.findViewById(R.id.colorfade).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datetext.setBackgroundColor(Integer.parseInt(fade + ""));
                    titletextView.setBackgroundColor(Integer.parseInt(fade + ""));
                    categorytextView.setBackgroundColor(Integer.parseInt(fade + ""));
                    timetext.setBackgroundColor(Integer.parseInt(fade + ""));
                    desciptiontextView.setBackgroundColor(Integer.parseInt(fade + ""));

                }
            });
            dialog.findViewById(R.id.colorred).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //linear.setBackgroundColor(Integer.parseInt(red + ""));
                    //  color = red;
                    datetext.setBackgroundColor(Integer.parseInt(red + ""));
                    titletextView.setBackgroundColor(Integer.parseInt(red + ""));
                    categorytextView.setBackgroundColor(Integer.parseInt(red + ""));
                    timetext.setBackgroundColor(Integer.parseInt(red + ""));
                    desciptiontextView.setBackgroundColor(Integer.parseInt(red + ""));
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.colorpink).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //    linear.setBackgroundColor(Integer.parseInt(pink + ""));
                    //      color = pink;
                    datetext.setBackgroundColor(Integer.parseInt(pink + ""));
                    titletextView.setBackgroundColor(Integer.parseInt(pink + ""));
                    categorytextView.setBackgroundColor(Integer.parseInt(pink + ""));
                    timetext.setBackgroundColor(Integer.parseInt(pink + ""));
                    desciptiontextView.setBackgroundColor(Integer.parseInt(pink + ""));
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.colorprimary).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //        color = colorPrimaryDark;
                    //          linear.setBackgroundColor(Integer.parseInt(colorPrimaryDark + ""));

                    datetext.setBackgroundColor(Integer.parseInt(colorPrimaryDark + ""));
                    titletextView.setBackgroundColor(Integer.parseInt(colorPrimaryDark + ""));
                    categorytextView.setBackgroundColor(Integer.parseInt(colorPrimaryDark + ""));
                    timetext.setBackgroundColor(Integer.parseInt(colorPrimaryDark + ""));
                    desciptiontextView.setBackgroundColor(Integer.parseInt(colorPrimaryDark + ""));
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.colorgreen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //            color = colorgreen;
                    //              linear.setBackgroundColor(Integer.parseInt(colorgreen + ""));

                    datetext.setBackgroundColor(Integer.parseInt(colorgreen + ""));
                    titletextView.setBackgroundColor(Integer.parseInt(colorgreen + ""));
                    categorytextView.setBackgroundColor(Integer.parseInt(colorgreen + ""));
                    timetext.setBackgroundColor(Integer.parseInt(colorgreen + ""));
                    desciptiontextView.setBackgroundColor(Integer.parseInt(colorgreen + ""));
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.colordarkred).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datetext.setBackgroundColor(Integer.parseInt(drkred + ""));
                    titletextView.setBackgroundColor(Integer.parseInt(drkred + ""));
                    categorytextView.setBackgroundColor(Integer.parseInt(drkred + ""));
                    timetext.setBackgroundColor(Integer.parseInt(drkred + ""));
                    desciptiontextView.setBackgroundColor(Integer.parseInt(drkred + ""));
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.colordrkpink).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //            color = colorgreen;
                    //linear.setBackgroundColor(Integer.parseInt(darkpink + ""));
                    datetext.setBackgroundColor(Integer.parseInt(darkpink + ""));
                    titletextView.setBackgroundColor(Integer.parseInt(darkpink + ""));
                    categorytextView.setBackgroundColor(Integer.parseInt(darkpink + ""));
                    timetext.setBackgroundColor(Integer.parseInt(darkpink + ""));
                    desciptiontextView.setBackgroundColor(Integer.parseInt(darkpink + ""));
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.coloryellow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datetext.setBackgroundColor(Integer.parseInt(yellow + ""));
                    titletextView.setBackgroundColor(Integer.parseInt(yellow + ""));
                    categorytextView.setBackgroundColor(Integer.parseInt(yellow + ""));
                    timetext.setBackgroundColor(Integer.parseInt(yellow + ""));
                    desciptiontextView.setBackgroundColor(Integer.parseInt(yellow + ""));
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
        return true;
    }

    public void calendarsetting() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year1);
        cal.set(Calendar.MONTH, month1);
        cal.set(Calendar.DAY_OF_MONTH, day1);
        cal.set(Calendar.HOUR_OF_DAY, hour1);
        cal.set(Calendar.MINUTE, minute1);
        time = cal.getTimeInMillis();
if(System.currentTimeMillis()>=time)
{
    timetext.setError("invalid time setting ");
}

//        categorytextView.setText(System.currentTimeMillis()+"");
//
//       // Toast.makeText(expenseactvity.this,day1+"",Toast.LENGTH_LONG).show();
//        desciptiontextView.setText(time+"");
//    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4) {
            if (resultCode == 10||resultCode==RESULT_OK) {
                Intent i = getIntent();
                final String title = i.getStringExtra("title");
                String[] column = {title + ""};
                Toast.makeText(this, "Recieved" + title, Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View v = getLayoutInflater().inflate(R.layout.dialognotificationview, null);
                TextView tv = (TextView) v.findViewById(R.id.dialogtextview);
                builder.setTitle("ADD");
                final TextView ttitle = (TextView) v.findViewById(R.id.titletextdialog);
                final TextView tdescirption = (TextView) v.findViewById(R.id.desciptiondialogtext);
                final TextView tcategory = (TextView) v.findViewById(R.id.categorydialogtext);
                databasehelper databsehelper = new databasehelper(expenseactvity.this);
                SQLiteDatabase database = databsehelper.getWritableDatabase();
                Cursor cursor = database.query(databsehelper.Expense_Table_name, null, databsehelper.Expense_title + "=?", column, null, null, null);
                while (cursor.moveToNext()) {
                    if (title == cursor.getString(cursor.getColumnIndex(databsehelper.Expense_title))) {
                        String desc = cursor.getString(cursor.getColumnIndex(databsehelper.Expense_desciption));
                        String catgory = cursor.getString(cursor.getColumnIndex(databsehelper.Expense_Category));
                        ttitle.setText(title + "");
                        tdescirption.setText(desc);
                        tcategory.setText(catgory);
                    }
                }
                tv.setText("Are you sure you wamt to delete");
                builder.setView(v);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databasehelper expenseOpenHelper = new databasehelper(expenseactvity.this);
                                SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();

                                //ContentValues cv=new ContentValues();
//Cursor cursor =database.query(databasehelper.Expense_Table_name,null,databasehelper.Expense_title+"="+title,null,null,null,null,null);
                                database.delete(databasehelper.Expense_Table_name, databasehelper.Expense_title + "=" + title, null);

//                            MainActivity.expenselistadapter.notifyDataSetChanged();
//                            Toast.makeText(MainActivity.this,"Changes saved",Toast.LENGTH_SHORT).show();
//
//                            expenselistadapter.notifyDataSetChanged();

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
            }

        }
    }
    interface onDownload {
        void updateExpensesList();
    }

}

