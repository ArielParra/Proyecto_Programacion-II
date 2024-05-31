package Proyecto_Programacion;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;

public class CustomFontLoader {
    public static Font loadFont(String fontFileName, float size) throws IOException, FontFormatException {
        File fontFile = new File(fontFileName);
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(size);
        return customFont;
    }
}
