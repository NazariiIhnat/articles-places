package components.items;

import lombok.Getter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelListener;

@Getter
public class SpinnerDialog extends JDialog {

    private JSpinner spinner;
    private SpinnerNumberModel spinnerNumberModel;
    private JButton okButton;

    public SpinnerDialog(JFrame frame) {
        super(frame, "Wyjmij ilość");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 176, 82);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        spinner = new JSpinner();
        spinner.setBounds(10, 11, 66, 20);
        contentPane.add(spinner);
        spinnerNumberModel = new SpinnerNumberModel();
        spinnerNumberModel.setValue(1);
        spinnerNumberModel.setMinimum(1);
        spinnerNumberModel.setStepSize(1);
        spinnerNumberModel.setValue(spinnerNumberModel.getMinimum());
        spinner.setModel(spinnerNumberModel);
        spinner.addMouseWheelListener(changeValue());
        spinnerNumberModel.addChangeListener(e -> {
            Object value = spinnerNumberModel.getValue();
            try{
                int number = (int) value;
                if(number > (Integer) spinnerNumberModel.getMaximum())
                    spinnerNumberModel.setValue(spinnerNumberModel.getMaximum());
            } catch (NumberFormatException ignored) {

            }
        });

        okButton = new JButton("OK");
        okButton.setBounds(86, 10, 62, 23);
        contentPane.add(okButton);
    }

    private MouseWheelListener changeValue() {
        return e -> {
            if(e.getWheelRotation() == -1) {
                try {
                    if ((int) spinnerNumberModel.getNextValue() <= (Integer) spinnerNumberModel.getMaximum())
                        spinnerNumberModel.setValue(spinnerNumberModel.getNextValue());
                } catch (NullPointerException ex){
                    spinnerNumberModel.setValue(spinnerNumberModel.getMaximum());
                }
            }
            else {
                try {
                    if ((int) spinnerNumberModel.getPreviousValue() >= (Integer) spinnerNumberModel.getMinimum())
                        spinnerNumberModel.setValue(spinnerNumberModel.getPreviousValue());
                } catch (NullPointerException ex) {
                    spinnerNumberModel.setValue(spinnerNumberModel.getMinimum());
                }
            }
        };
    }
}
