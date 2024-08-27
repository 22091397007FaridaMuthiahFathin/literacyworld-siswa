package id.literacyworld.siswa;

public class Article {
    // Variabel untuk menyimpan ID artikel, judul, dan konten
    public String articleId;
    public String title;
    public String content;

    // Constructor default diperlukan oleh Firebase
    public Article() {
        // Constructor default tanpa parameter
    }

    // Constructor dengan parameter untuk memudahkan inisialisasi
    public Article(String articleId, String title, String content) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
    }

    // Getter dan setter untuk articleId
    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    // Getter dan setter untuk title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter dan setter untuk content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
