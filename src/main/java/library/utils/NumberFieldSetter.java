package library.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class NumberFieldSetter {

    public static void setTextField (TextField textField){

        UnaryOperator<TextFormatter.Change> filter;
        filter = new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                String text = change.getText();
                for (int i = 0; i < text.length(); i++)
                    if (!Character.isDigit(text.charAt(i)))
                        return null;
                return change;
            }
        };

        textField.setTextFormatter(new TextFormatter<String>(filter));
    }

}
