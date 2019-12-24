package dtos;

import java.io.Serializable;

public class ReceiptDTO implements Serializable {

    private int id;

    private String filepath;

    private String fileName;

    public ReceiptDTO() {
    }

    public ReceiptDTO(int id, String filepath, String fileName) {
        this.id = id;
        this.filepath = filepath;
        this.fileName = fileName;
    }

    public ReceiptDTO(String filepath, String desiredName, String mimeType) {
        this(-1, filepath, desiredName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
