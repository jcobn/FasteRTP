package me.jcobn.fastertp.file.locales;

public enum Languages {
    ENGLISH("en.yml"),
    CZECH("cs.yml");

    private final String fileName;

    Languages(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
