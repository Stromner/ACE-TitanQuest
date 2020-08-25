package tq.character.editor.ui.utils.builders;

import java.awt.*;

public class GridBagConstraintsBuilder {
    private final GridBagConstraints constraints;

    public GridBagConstraintsBuilder() {
        constraints = new GridBagConstraints();
    }

    public GridBagConstraintsBuilder withGridHeight(int gridHeight) {
        constraints.gridheight = gridHeight;
        return this;
    }

    public GridBagConstraintsBuilder withGridWidth(int gridWidth) {
        constraints.gridwidth = gridWidth;
        return this;
    }

    public GridBagConstraintsBuilder withFill(int fill) {
        constraints.fill = fill;
        return this;
    }

    public GridBagConstraintsBuilder withDefaultPadding() {
        constraints.insets = new Insets(10, 10, 10, 10);
        return this;
    }

    public GridBagConstraintsBuilder withDefaultWidthPadding() {
        constraints.insets = new Insets(0, 10, 0, 10);
        return this;
    }

    public GridBagConstraintsBuilder withDefaultHeightPadding() {
        constraints.insets = new Insets(10, 0, 10, 0);
        return this;
    }

    public GridBagConstraintsBuilder withWeightX(int weightX) {
        constraints.weightx = weightX;
        return this;
    }

    public GridBagConstraintsBuilder withWeightY(int weightY) {
        constraints.weighty = weightY;
        return this;
    }

    public GridBagConstraintsBuilder withFillOutWeight() {
        constraints.weightx = 1;
        constraints.weighty = 1;
        return this;
    }

    public GridBagConstraints build() {
        return constraints;
    }
}
