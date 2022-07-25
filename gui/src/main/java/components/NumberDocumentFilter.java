package components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberDocumentFilter extends DocumentFilter {

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if(text == null)
            fb.remove(0, fb.getDocument().getLength());
        else {
            try {
                int i = Integer.parseInt(text);
                super.replace(fb, offset, length, text, attrs);
            } catch (NumberFormatException ignore) {

            }
        }
    }
}
