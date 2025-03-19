package gui.utilities;

import engine.faction.Faction;

import javax.swing.*;
import javax.swing.text.*;

public class LoreBuilder {

        /**
         * Builds a JTextPane with a short piece of lore.
         * @return A JTextPane containing the lore text.
         */
        public static JTextPane buildLoreTextPane() {
            // Create a JTextPane
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false); // Make it read-only
            textPane.setFocusable(false);

            // Get the styled document
            StyledDocument doc = textPane.getStyledDocument();

            // Add a simple style
            Style style = textPane.addStyle("LoreStyle", null);
            StyleConstants.setFontSize(style, 12);
            StyleConstants.setItalic(style, true);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER); // Align text to the center

            // Insert the lore text
            try {
                doc.insertString(doc.getLength(), "In the shadow of ancient ruins,\n", style);
                doc.insertString(doc.getLength(), "heroes once forged their destinies.", style);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            // Apply text alignment explicitly
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            return textPane;
        }
    }


