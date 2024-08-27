package id.literacyworld.siswa;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.literacy.literacyworld_murid.R;

public class EditArticleActivity extends AppCompatActivity {

    private static final String TAG = "EditArticleActivity";
    private String articleId;
    private EditText editTitle;
    private EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);

        // Inisialisasi EditText untuk judul dan isi artikel
        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        Button saveButton = findViewById(R.id.saveButton);

        // Mendapatkan data artikel dari Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            articleId = extras.getString("articleId");
            String title = extras.getString("title");
            String content = extras.getString("content");

            // Tambahkan log untuk memeriksa data yang diterima
            Log.d(TAG, "articleId: " + articleId);
            Log.d(TAG, "title: " + title);
            Log.d(TAG, "content: " + content);

            // Mengisi EditText dengan data artikel
            if (title != null) editTitle.setText(title);
            if (content != null) editContent.setText(content);
        } else {
            Toast.makeText(this, "Data artikel tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Mengatur onClickListener untuk tombol Save
        saveButton.setOnClickListener(v -> {
            // Mendapatkan judul dan isi artikel dari EditText dan menghilangkan spasi di awal dan akhir
            String updatedTitle = editTitle.getText().toString().trim();
            String updatedContent = editContent.getText().toString().trim();

            if (articleId != null && !articleId.isEmpty()) {
                DatabaseReference articleRef = FirebaseDatabase.getInstance().getReference("articles").child(articleId);

                // Update title only if it is not empty
                if (!updatedTitle.isEmpty()) {
                    articleRef.child("title").setValue(updatedTitle)
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "Title updated successfully");
                                Toast.makeText(EditArticleActivity.this, "Judul artikel diperbarui", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Failed to update title", e);
                                Toast.makeText(EditArticleActivity.this, "Gagal memperbarui judul", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(EditArticleActivity.this, "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

                // Update content only if it is not empty
                if (!updatedContent.isEmpty()) {
                    articleRef.child("content").setValue(updatedContent)
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "Content updated successfully");
                                Toast.makeText(EditArticleActivity.this, "Isi artikel diperbarui", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Failed to update content", e);
                                Toast.makeText(EditArticleActivity.this, "Gagal memperbarui isi", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(EditArticleActivity.this, "Isi tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

                // Menutup aktivitas pengeditan setelah menyimpan perubahan
                Toast.makeText(EditArticleActivity.this, "Artikel diperbarui", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.e(TAG, "articleId is null or empty");
                Toast.makeText(EditArticleActivity.this, "Terjadi kesalahan, artikel tidak dapat diperbarui", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
