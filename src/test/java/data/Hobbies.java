package data;

public enum Hobbies {
    SPORTS("Sports"),
    READING("Reading"),
    MUSIC("Music");

    private final String name;

    Hobbies(String name) {
        this.name = name;
    }

    public String getHobbies() {
        return name;
    }
}
