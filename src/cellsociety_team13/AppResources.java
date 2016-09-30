package cellsociety_team13;

public enum AppResources {
    APP_TITLE("Cell Society");

    private String resource;

    AppResources(String resourceName) {
        resource = resourceName;
    }

    public String getResource() {
        return resource;
    }
}
