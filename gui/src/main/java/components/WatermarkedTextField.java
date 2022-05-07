package components;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class WatermarkedTextField extends JTextField {

    private final String watermarkText;
    private static final Color TEXT_COLOR = new Color(0, 0, 0);
    private static final Color WATERMARK_TEXT_COLOR = new Color(187, 187, 187);

    public WatermarkedTextField(String watermarkText) {
        this.watermarkText = watermarkText;
        addFocusListener(new Watermarker(watermarkText));
        setText(watermarkText);
        setForeground(WATERMARK_TEXT_COLOR);
    }

    public boolean isEmpty() {
        return getText().split(" ").length == 0 |
                getText().equals("") |
                getText().equals(watermarkText) && getForeground().equals(WATERMARK_TEXT_COLOR);
    }

    public void clearInputAndSetWatermark() {
        setText(watermarkText);
        setForeground(WATERMARK_TEXT_COLOR);
    }

    class Watermarker implements FocusListener {

        private final String watermarkText;

        Watermarker(String watermarkText){
            this.watermarkText = watermarkText;
        }

        @Override
        public void focusGained(FocusEvent e) {
            JTextComponent textComponent = (JTextComponent) e.getComponent();
            if(textComponent.getText().equals(watermarkText)
                    & textComponent.getForeground().equals(WATERMARK_TEXT_COLOR)){
                textComponent.setText("");
                textComponent.setForeground(TEXT_COLOR);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextComponent textComponent = (JTextComponent) e.getComponent();
            if(isEmpty(textComponent.getText()) & textComponent.getForeground().equals(TEXT_COLOR)){
                textComponent.setText(watermarkText);
                textComponent.setForeground(WATERMARK_TEXT_COLOR);
            }
        }

        private boolean isEmpty(String text) {
            return text.split(" ").length == 0 | text.equals("");
        }

        String getWatermarkText() {
            return watermarkText;
        }
    }
}
