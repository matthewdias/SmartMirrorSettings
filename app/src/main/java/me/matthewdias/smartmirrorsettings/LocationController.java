package me.matthewdias.smartmirrorsettings;

public class LocationController {
    public static void submitLocation(String zipcode, MainActivity mainActivity) {
        MirrorAPIAdapter adapter = mainActivity.getMirrorAPIAdapter();
        adapter.changeSetting("zipcode", zipcode);
    }

    public static void getLocation(MainActivity mainActivity) {
        MirrorAPIAdapter adapter = mainActivity.getMirrorAPIAdapter();
        adapter.getSetting("zipcode");
    }
}
