package Utils;

import javax.swing.JTextField;

public class StringUtils {
    public static boolean isOneEmpty(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().isBlank()) {
                return true;
            }
        }

        return false;
    }

}