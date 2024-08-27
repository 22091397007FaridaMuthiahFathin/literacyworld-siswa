package id.literacyworld.siswa;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.literacy.literacyworld_murid.R;

import java.util.ArrayList;
import java.util.List;

public class BacaPDF extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseReference;
    List<putPDF> uploadedPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baca_pdf);

        listView = findViewById(R.id.listview);
        uploadedPDF=new ArrayList<>();

        // Ambil data PDF dari Firebase
        retrievePDFFile();

        // Listener untuk item di ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                putPDF putPDF= uploadedPDF.get(i);

                // Buka PDF yang dipilih
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(putPDF.getUrl()));
                startActivity(intent);

                // Setelah pengguna mengklik item, tandai sebagai telah dibaca dan perbarui tampilan ListView
                putPDF.setRead(true);
                ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged();

                // Menonaktifkan item yang telah dibaca
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setEnabled(false);
            }
        });
    }

    // Mendapatkan data PDF dari Firebase Database
    private void retrievePDFFile() {

       databaseReference= FirebaseDatabase.getInstance().getReference("Upload PDF");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot ds:snapshot.getChildren()){

                   // Ambil data PDF dan tambahkan ke list
                   putPDF putPDF= ds.getValue(id.literacyworld.siswa.putPDF.class);
                   uploadedPDF.add(putPDF);
               }

               // Buat array nama file PDF
               String[] uploadsName =new String[uploadedPDF.size()];
               for(int i=0;i<uploadsName.length;i++){
                  uploadsName[i]=uploadedPDF.get(i).getName();
               }

               // Buat ArrayAdapter untuk ListView
               ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),
                       android.R.layout.simple_list_item_1, uploadsName){

                   @NonNull
                   @Override
                   public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                       View view = super.getView(position, convertView, parent);

                       // Atur tampilan TextView
                       TextView textView = (TextView) view.findViewById(android.R.id.text1);

                       textView.setTextColor(Color.BLACK);
                       textView.setTextSize(20);


                       // Atur tampilan ikon ceklis berdasarkan status file PDF
                       putPDF pdf = uploadedPDF.get(position);
                       if (pdf.isRead()) {
                           // Jika file telah dibaca, tampilkan ikon ceklis
                           Drawable checkIcon = ContextCompat.getDrawable(getContext(), R.drawable.checklist);
                           checkIcon.setBounds(0, 0, 80, 80); // Ubah ukuran ikon sesuai kebutuhan
                           textView.setCompoundDrawables(checkIcon, null, null, null);
                           textView.setEnabled(false); // Nonaktifkan item
                       } else {
                           // Jika file belum dibaca, hilangkan ikon ceklis
                           textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                           textView.setEnabled(true); // Aktifkan item
                       }
                       return view;
                   }
               };

               // Atur adapter untuk ListView
               listView.setAdapter(arrayAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               // Penanganan kesalahan jika pembatalan terjadi

           }
       });
    }
}