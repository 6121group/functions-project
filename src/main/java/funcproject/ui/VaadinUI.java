package funcproject.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import funcproject.calculation.FirstFunction;
import funcproject.calculation.SecondFunction;
import funcproject.calculation.Calculation;
import funcproject.calculation.ThirdFunction;

import java.util.ArrayList;
import java.util.List;

import static funcproject.calculation.Measurement.*;

/**
 * @author kudryavtseva
 * @version $Id$
 */
@SpringUI
@Theme("valo-flat")
public class VaadinUI extends UI {
    private static final String FIRST_FUNCTION = "Аналитическое представление";
    private static final String SECOND_FUNCTION = "Интегральное представление";
    private static final String THIRD_FUNCTION = "Рекурсивное представление";

    TextField gammaField = new TextField();
    TextField nField = new TextField();
    TextField kField = new TextField();

    ComboBox<String> functionsBox = new ComboBox();
    Label title = new Label("Вычисление ортогональных функций");
    Label transpose = new Label();
    Label inverse = new Label();
    Label xtx = new Label();
    Label resultLbl = new Label();
    Label calcTimeParallel = new Label();
    Label calcTime = new Label();
    CheckBox sequential = new CheckBox("Вычислить последовательным методом");
    VerticalLayout fieldLayout = new VerticalLayout();
    VerticalLayout functionsLayout = new VerticalLayout();
    VerticalLayout layout = new VerticalLayout();
    VerticalLayout result = new VerticalLayout();
    Button calcBtnParallel = new Button("Вычислить");

    @Override
    protected void init(VaadinRequest request) {
        title.setStyleName(ValoTheme.LABEL_H1);

        gammaField.setPlaceholder("\u03B3");
        nField.setPlaceholder("n");
        kField.setPlaceholder("k");

        functionsBox.setItems(FIRST_FUNCTION, SECOND_FUNCTION, THIRD_FUNCTION);
        functionsBox.setSelectedItem(FIRST_FUNCTION);
        functionsBox.setWidth("300");
        functionsBox.setEmptySelectionAllowed(false);

        fieldLayout.addComponents(gammaField, nField, kField, functionsBox, sequential, calcBtnParallel);
        fieldLayout.setMargin(true);

        Panel fields = new Panel("Исходные данные", fieldLayout);
        fields.setSizeFull();
        gammaField.setWidth("300");
        kField.setWidth("300");
        nField.setWidth("300");

        functionsLayout.setMargin(true);

        result.addComponents(resultLbl, transpose, inverse, xtx, calcTimeParallel, calcTime);
        resultLbl.setStyleName(ValoTheme.LABEL_BOLD);

        Panel resultPanel = new Panel("Результаты вычислений");
        resultPanel.setContent(result);
        resultPanel.setVisible(false);

        layout.addComponents(title, fields, resultPanel);
        setContent(layout);

        calcBtnParallel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        calcBtnParallel.addClickListener(event -> {
            resultPanel.setVisible(false);

            if (!validate())
                return;

            double gamma = Double.parseDouble(gammaField.getValue().replace(',', '.'));
            int n = Integer.parseInt(nField.getValue());
            int k = Integer.parseInt(kField.getValue());

            transpose.setValue("");
            inverse.setValue("");
            xtx.setValue("");

            String function = functionsBox.getSelectedItem().get();
            Calculation calculation = null;
            switch (function) {
                case FIRST_FUNCTION: {
                    calculation = new FirstFunction();
                    break;
                }
                case SECOND_FUNCTION: {
                    calculation = new SecondFunction();
                    break;
                }
                case THIRD_FUNCTION: {
                    calculation = new ThirdFunction();
                    break;
                }
            }

            long timer = 0;

            String resultMsg = "Матрица вычислена успешно";
            try {
                timer = calculation.getTimeParallel(gamma, k, n);
            } catch (Exception e) {
                resultMsg = "При вычислении матрицы возникли ошибки";
                e.printStackTrace();
                resultLbl.setValue(resultMsg);
                resultPanel.setVisible(true);
            }
            resultLbl.setValue(resultMsg);
            resultPanel.setVisible(true);

            double[][] matrix = calculation.getMatrix();

            if (k != n) {
                Notification.show("Матрица не квадратная, невозможно рассчитать обратную матрицу", Notification.Type.WARNING_MESSAGE);
                transpose.setValue("X ⷮ: " + format(transposeMeasure(matrix)) + " нс");
                return;
            }

            transpose.setValue("X ⷮ: " + format(transposeMeasure(matrix)) + " нс");
            inverse.setValue("X\u207B\u00B9: " + format(inverseMeasure(matrix)) + " нс");
            xtx.setValue("(X ⷮX)\u207B\u00B9: " + format(xtxMeasure(matrix)) + " нс");
            calcTimeParallel.setValue("\nВремя параллельного вычисления матрицы: " + format(timer) + " нс");

            if(sequential.getValue()) {
                timer = calculation.getTimeSequential(gamma, k, n);
                calcTime.setValue("Время последовательного вычисления матрицы: " + format(timer) + " нс");
            }
        });
    }

    private String format(double number) {
        return java.text.NumberFormat.getInstance().format(number);
    }

    private boolean validate() {
        List<TextField> fields = new ArrayList<>();
        fields.add(gammaField);
        fields.add(nField);
        fields.add(kField);

        for (TextField field : fields) {
            if (field.isEmpty()) {
                field.focus();
                Notification.show("Заполните поле " + field.getPlaceholder(), Notification.Type.ERROR_MESSAGE);
                return false;
            }
        }

        try {
            Double.parseDouble(gammaField.getValue().replace(',', '.'));
        } catch (NumberFormatException e) {
            Notification.show("Введите вещественное число", Notification.Type.ERROR_MESSAGE);
        }

        try {
            int k = Integer.parseInt(kField.getValue());
            int n = Integer.parseInt(nField.getValue());
            if(n > 600 || k > 600) {
                Notification.show("Количество столбцов и строк матрицы не должно превышать 600", Notification.Type.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            Notification.show("Значения n и k должны быть целыми", Notification.Type.ERROR_MESSAGE);
        }
        return true;
    }
}
