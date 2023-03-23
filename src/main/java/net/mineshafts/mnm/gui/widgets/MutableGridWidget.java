package net.mineshafts.mnm.gui.widgets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.client.gui.widget.WrapperWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.Divider;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class MutableGridWidget extends WrapperWidget {
    public final List<ClickableWidget> children = new ArrayList<ClickableWidget>();
    private final List<Element> grids = new ArrayList<Element>();
    private final Positioner mainPositioner = Positioner.create();

    public MutableGridWidget() {
        this(0, 0);
    }

    public MutableGridWidget(int x, int y) {
        this(x, y, Text.empty());
    }

    public MutableGridWidget(int x, int y, Text message) {
        super(x, y, 0, 0, message);
    }

    public void recalculateDimensions() {
        int m;
        int l;
        int i = 0;
        int j = 0;
        for (Element element : this.grids) {
            i = Math.max(element.getRowEnd(), i);
            j = Math.max(element.getColumnEnd(), j);
        }
        int[] is = new int[j + 1];
        int[] js = new int[i + 1];
        for (Element element2 : this.grids) {
            Divider divider = new Divider(element2.getHeight(), element2.occupiedRows);
            for (int k = element2.row; k <= element2.getRowEnd(); ++k) {
                js[k] = Math.max(js[k], divider.nextInt());
            }
            Divider divider2 = new Divider(element2.getWidth(), element2.occupiedColumns);
            for (l = element2.column; l <= element2.getColumnEnd(); ++l) {
                is[l] = Math.max(is[l], divider2.nextInt());
            }
        }
        int[] ks = new int[j + 1];
        int[] ls = new int[i + 1];
        ks[0] = 0;
        for (m = 1; m <= j; ++m) {
            ks[m] = ks[m - 1] + is[m - 1];
        }
        ls[0] = 0;
        for (m = 1; m <= i; ++m) {
            ls[m] = ls[m - 1] + js[m - 1];
        }
        for (Element element3 : this.grids) {
            int n;
            l = 0;
            for (n = element3.column; n <= element3.getColumnEnd(); ++n) {
                l += is[n];
            }
            element3.setX(this.getX() + ks[element3.column], l);
            n = 0;
            for (int o = element3.row; o <= element3.getRowEnd(); ++o) {
                n += js[o];
            }
            element3.setY(this.getY() + ls[element3.row], n);
        }
        this.width = ks[j] + is[j];
        this.height = ls[i] + js[i];
    }

    public <T extends ClickableWidget> T add(T widget, int row, int column) {
        return this.add(widget, row, column, this.copyPositioner());
    }

    public <T extends ClickableWidget> T add(T widget, int row, int column, Positioner positioner) {
        return this.add(widget, row, column, 1, 1, positioner);
    }

    public <T extends ClickableWidget> T add(T widget, int row, int column, int occupiedRows, int occupiedColumns) {
        return this.add(widget, row, column, occupiedRows, occupiedColumns, this.copyPositioner());
    }

    public <T extends ClickableWidget> T add(T widget, int row, int column, int occupiedRows, int occupiedColumns, Positioner positioner) {
        if (occupiedRows < 1) {
            throw new IllegalArgumentException("Occupied rows must be at least 1");
        }
        if (occupiedColumns < 1) {
            throw new IllegalArgumentException("Occupied columns must be at least 1");
        }
        this.grids.add(new Element(widget, row, column, occupiedRows, occupiedColumns, positioner));
        this.children.add(widget);
        return widget;
    }

    @Override
    protected List<? extends ClickableWidget> wrappedWidgets() {
        return this.children;
    }

    public Positioner copyPositioner() {
        return this.mainPositioner.copy();
    }

    public Positioner getMainPositioner() {
        return this.mainPositioner;
    }

    public Adder createAdder(int columns) {
        return new Adder(columns);
    }

    @Environment(value= EnvType.CLIENT)
    static class Element
            extends WrapperWidget.WrappedElement {
        final int row;
        final int column;
        final int occupiedRows;
        final int occupiedColumns;

        Element(ClickableWidget widget, int row, int column, int occupiedRows, int occupiedColumns, Positioner positioner) {
            super(widget, positioner.toImpl());
            this.row = row;
            this.column = column;
            this.occupiedRows = occupiedRows;
            this.occupiedColumns = occupiedColumns;
        }

        public int getRowEnd() {
            return this.row + this.occupiedRows - 1;
        }

        public int getColumnEnd() {
            return this.column + this.occupiedColumns - 1;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public final class Adder {
        private final int columns;
        private int totalOccupiedColumns;

        Adder(int columns) {
            this.columns = columns;
        }

        public <T extends ClickableWidget> T add(T widget) {
            return this.add(widget, 1);
        }

        public <T extends ClickableWidget> T add(T widget, int occupiedColumns) {
            return this.add(widget, occupiedColumns, this.getMainPositioner());
        }

        public <T extends ClickableWidget> T add(T widget, Positioner positioner) {
            return this.add(widget, 1, positioner);
        }

        public <T extends ClickableWidget> T add(T widget, int occupiedColumns, Positioner positioner) {
            int i = this.totalOccupiedColumns / this.columns;
            int j = this.totalOccupiedColumns % this.columns;
            if (j + occupiedColumns > this.columns) {
                ++i;
                j = 0;
                this.totalOccupiedColumns = MathHelper.roundUpToMultiple(this.totalOccupiedColumns, this.columns);
            }
            this.totalOccupiedColumns += occupiedColumns;
            return MutableGridWidget.this.add(widget, i, j, 1, occupiedColumns, positioner);
        }

        public Positioner copyPositioner() {
            return MutableGridWidget.this.copyPositioner();
        }

        public Positioner getMainPositioner() {
            return MutableGridWidget.this.getMainPositioner();
        }
    }
}
