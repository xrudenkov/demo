package ru.task.demo.model;

public enum LegalFormEnum {
    OOO("ООО"),
    ST("ИП"),
    AO("АО");

    private final String title;

    LegalFormEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static LegalFormEnum fromString(String text) {
        for (LegalFormEnum legalForm : LegalFormEnum.values()) {
            if (legalForm.title.equalsIgnoreCase(text)) {
                return legalForm;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "LegalFormEnum{" +
                "title='" + title + '\'' +
                '}';
    }
}
