package cellsociety_team13;

public enum AppResources {
    APP_TITLE("Cell Society"),
    APP_WIDTH(1000),
    APP_HEIGHT(750),
    APP_PADDING(25),
    APP_CSS("style.css"),
    APP_DATA("data/"),

    TITLE_IMAGE_LOCATION("resources/title_screen_image.png"),
    TITLE_IMAGE_ERROR("Title image not found: "),
    TITLE_BUTTON_WIDTH(250),
    TITLE_BUTTON_OFFSET(200),
    TITLE_FILESELECT_WIDTH(350),
    TITLE_FILESELECT_OFFSET(100),

    TITLE_BOX_HEIGHT(100),
    INPUT_PANEL_HEIGHT(150),
    INPUT_PANEL_PADDING(15),
    INPUT_BUTTON_WIDTH(200),
    GAME_SELECT_TITLE("New Game"),
    CHART_VIEW_TITLE("View Chart"),
    GRID_VIEW_TITLE("View Cells"),
    STEP_TITLE("Step"),
    RUN_TITLE("Run"),
    PAUSE_TITLE("Pause"),
    ANIMATION_SPEED(250),
    ANIMATION_MIN_RATE(0.25),
    ANIMATION_MAX_RATE(2.5),
    PARAMETER_COMBO_BOX_WIDTH(350),
    PARAMETER_DEFAULT_MESSAGE("N/A"),

    XML_MAIN("MAIN"),
    XML_PARAMETER("PARAMETER"),
    XML_CELLTYPE("CELLTYPE"),
    XML_GRID("GRID"),
    XML_LOCATION("LOCATION"),
    XML_MAIN_TITLE("TITLE"),
    XML_MAIN_RULE("RULE"),
    XML_MAIN_AUTHOR("AUTHOR"),
    XML_PARAMETER_NAME("NAME"),
    XML_PARAMETER_MIN("MIN"),
    XML_PARAMETER_MAX("MAX"),
    XML_PARAMETER_VAL("CURRENT"),
    XML_CELLTYPE_ID("ID"),
    XML_CELLTYPE_NAME("NAME"),
    XML_GRID_WIDTH("WIDTH"),
    XML_GRID_HEIGHT("HEIGHT"),
    XML_GRID_DEFAULTID("DEFAULTID"),
    XML_GRID_FILLMETHOD("FILLMETHOD"),
    XML_LOCATION_ID("ID"),
    XML_LOCATION_ROW("ROW"),
    XML_LOCATION_COL("COL"),
    XML_LOCATION_PERCENT("PERCENT"),
    XML_LOCATION_MANUAL_METHOD("manual"),
    XML_LOCATION_PERCENTAGE_METHOD("percentage"),
	
	FA_FOOD("FOOD"),
	FA_FOODSOURCE("FOODSOURCE"),
	FA_ANT("ANT"),
	FA_ANTS("ANTS"),
	FA_HOMEPHERO("HOMEPHERO"),
	FA_FOODPHERO("FOODPHERO"),
	FA_XOrientation("XOrientation"),
	FA_YOrientation("YOrientation"),
	FA_NEST("NEST"),
	FA_NESTFOOD("NESTFOOD"),
	FA_PATCH("PATCH"),

    GOL_LIVE_TYPE("LIVE"),
    GOL_DEAD_TYPE("DEAD");

    private double resourceDouble;
    private String resourceString;

    AppResources(String resource) {
        resourceString = resource;
        resourceDouble = -1;
    }

    AppResources(double resource) {
        resourceString = null;
        resourceDouble = resource;
    }

    public String getResource() {
        return resourceString;
    }

    public double getDoubleResource() {
        return resourceDouble;
    }
}
