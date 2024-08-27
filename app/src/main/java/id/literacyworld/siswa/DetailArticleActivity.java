package id.literacyworld.siswa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.literacy.literacyworld_murid.R;

public class DetailArticleActivity extends AppCompatActivity {

    private static final String TAG = "DetailArticleActivity";
    private String articleId;
    private String articleTitle;
    private String articleContent;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        // Mendapatkan data artikel dari Intent
        Intent intent = getIntent();
        articleId = intent.getStringExtra("articleId");
        articleTitle = intent.getStringExtra("title");
        articleContent = intent.getStringExtra("content");

        // Tambahkan log untuk memeriksa data yang diterima
        Log.d(TAG, "articleId: " + articleId);
        Log.d(TAG, "title: " + articleTitle);
        Log.d(TAG, "content: " + articleContent);

        // Menampilkan judul dan isi artikel
        TextView titleTextView = findViewById(R.id.textViewTitle);
        TextView contentTextView = findViewById(R.id.textViewContent);

        titleTextView.setText(articleTitle);
        contentTextView.setText(articleContent);

        // Inisialisasi Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("articles");

        // Mengatur onClickListener untuk tombol edit
        ImageButton editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArticle();
            }
        });
    }

    private void deleteArticle() {
        if (articleId != null) {
            Log.d(TAG, "Attempting to delete article with ID: " + articleId);
            databaseReference.child(articleId).removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Article deleted successfully");
                            Toast.makeText(DetailArticleActivity.this, "Article deleted successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Tutup activity setelah penghapusan
                        } else {
                            Log.e(TAG, "Failed to delete article", task.getException());
                            Toast.makeText(DetailArticleActivity.this, "Failed to delete article", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Log.e(TAG, "Article ID is null, cannot delete article");
        }
    }
}
