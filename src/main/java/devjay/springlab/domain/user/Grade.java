package devjay.springlab.domain.user;

public enum Grade {
    USER(1), MANAGER(2), ADMIN(3);

    private final int level;

    Grade(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
