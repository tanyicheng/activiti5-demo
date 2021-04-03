package com.barrett.test.font;

import java.awt.*;

public class SystemFont {
    public static void main(String[] args) {
        String[] fontName = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String fontname : fontName)
            System.out.println(fontname);

    }
}
