package com.lipin.Utils;

import com.lipin.App;
import javafx.stage.Stage;

import java.io.File;
import java.util.prefs.Preferences;


public class XML_IO_Util {
    //檢查有無xml檔,如無xml檔則回傳null
    public File getMyLoveFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        String filePath = prefs.get("filePath", null);

        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPreferencesFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(App.class);

        if (file != null) {
            prefs.put("filePath", file.getPath());
            System.out.println("XML_IO_Util.setPreferencesFilePath:保存成功:"+file.getPath());
        } else {
            prefs.remove("filePath");
            System.out.println("XML_IO_Util.setPreferencesFilePath:刪除成功");
        }
    }
}
