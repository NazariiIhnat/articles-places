package components.items.create;

import components.location.ShowMessage;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberDocumentFilter extends DocumentFilter {

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        try{
            int i = Integer.parseInt(text);
            super.replace(fb, offset, length, text, attrs);
        } catch (NumberFormatException e) {
            if(text == null) super.replace(fb, offset, length, null, attrs);
            else ShowMessage.error("Błąd! Pole może zawierać tylko cyfry.");
        }
    }
}
