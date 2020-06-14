package sy7;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.myfirstapp.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//第二题：自定义内容提供者,点击“查询”按钮，可查询contacts表中的所有信息，也可以查询单条联系人的信息。
public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list);

        Button con_query = findViewById(R.id.con_query);
        con_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/contacts");
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if(cursor != null){
                    while (cursor.moveToNext()){

                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        String sex = cursor.getString(cursor.getColumnIndex("sex"));

                        Log.d("contacts",id+" "+name+" "+phone+" "+sex);
                    }
                    cursor.close();
                }
            }
        });
    }
}
