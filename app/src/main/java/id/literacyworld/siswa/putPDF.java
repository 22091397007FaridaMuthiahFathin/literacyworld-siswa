package id.literacyworld.siswa;

public class putPDF {
    public String name;
    public String url;
    public boolean isRead; // Tambahkan variabel isRead

    public putPDF() {
    }

    public putPDF(String name, String url) {
        this.name = name;
        this.url = url;
        this.isRead = false; // Atur default isRead ke false
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read; // Atur nilai isRead sesuai parameter
    }
}