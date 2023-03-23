package net.mineshafts.mnm.gui.widgets;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.OptionSliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.TranslatableOption;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.IntStream;

@Environment(value= EnvType.CLIENT)
public class SimpleChoice<T> {
    private static final Logger LOGGER = LogUtils.getLogger();
    /**
     * A set of callbacks for boolean options.
     *
     * @apiNote See also several {@code ofBoolean} methods in this class which provide easier ways
     * of creating a boolean option.
     */
    private final SimpleOption.TooltipFactory<T> tooltipFactory;
    final Function<T, Text> textGetter;
    private final Callbacks<T> callbacks;
    private final Codec<T> codec;
    private final T defaultValue;
    private final Consumer<T> changeCallback;
    T value;

    public SimpleChoice(SimpleOption.TooltipFactory<T> tooltipFactory, ValueTextGetter<T> valueTextGetter, Callbacks<T> callbacks, T defaultValue, Consumer<T> changeCallback) {
        this(tooltipFactory, valueTextGetter, callbacks, callbacks.codec(), defaultValue, changeCallback);
    }

    public SimpleChoice(SimpleOption.TooltipFactory<T> tooltipFactory, ValueTextGetter<T> valueTextGetter, Callbacks<T> callbacks, Codec<T> codec, T defaultValue, Consumer<T> changeCallback) {
        this.tooltipFactory = tooltipFactory;
        this.textGetter = valueTextGetter::toString;
        this.callbacks = callbacks;
        this.codec = codec;
        this.defaultValue = defaultValue;
        this.changeCallback = changeCallback;
        this.value = this.defaultValue;
    }

    /**
     * {@return the getter for the {@code tooltipFactoryGetter} parameter of the constructor
     * to indicate empty tooltips}
     */
    public static <T> SimpleOption.TooltipFactory<T> emptyTooltip() {
        return value -> null;
    }

    /**
     * {@return the getter for the {@code tooltipFactoryGetter} parameter of the constructor
     * to indicate constant tooltips}
     */
    public static <T> SimpleOption.TooltipFactory<T> constantTooltip(Text text) {
        return value -> Tooltip.of(text);
    }

    /**
     * {@return the getter for the {@code valueTextGetter} parameter of the constructor
     * to use the value's text as the option's rendered text}
     *
     * @apiNote This requires the value to be an enum implementing {@link TranslatableOption}.
     */
    public static <T extends TranslatableOption> ValueTextGetter<T> enumValueText() {
        return TranslatableOption::getText;
    }

    public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
        return this.createButton(options, x, y, width, value -> {});
    }

    public ClickableWidget createButton(GameOptions options, int x, int y, int width, Consumer<T> changeCallback) {
        return this.callbacks.getButtonCreator(this.tooltipFactory, options, x, y, width, changeCallback).apply(this);
    }

    /**
     * {@return the option's current value}
     */
    public T getValue() {
        return this.value;
    }

    public Codec<T> getCodec() {
        return this.codec;
    }

    /**
     * Sets the option's value.
     *
     * <p>The behavior for invalid {@code value} varies; the validator can either coerce
     * the passed value into a valid one by clamping etc, or fail the validation and reset to
     * the default value.
     *
     * <p>If the new value differs from the new value, {@link #changeCallback} will trigger.
     */
    public void setValue(T value) {
        T object = this.callbacks.validate(value).orElseGet(() -> {
            LOGGER.error("Illegal option value " + value);
            return this.defaultValue;
        });
        if (!MinecraftClient.getInstance().isRunning()) {
            this.value = object;
            return;
        }
        if (!Objects.equals(this.value, object)) {
            this.value = object;
            this.changeCallback.accept(this.value);
        }
    }

    public Callbacks<T> getCallbacks() {
        return this.callbacks;
    }

    @Environment(value=EnvType.CLIENT)
    public static interface ValueTextGetter<T> {
        public Text toString(T var2);
    }

    @Environment(value=EnvType.CLIENT)
    public record PotentialValuesBasedCallbacks<T>(List<T> values, Codec<T> codec) implements CyclingCallbacks<T>
    {
        @Override
        public Optional<T> validate(T value) {
            return this.values.contains(value) ? Optional.of(value) : Optional.empty();
        }

        @Override
        public CyclingButtonWidget.Values<T> getValues() {
            return CyclingButtonWidget.Values.of(this.values);
        }
    }

    @Environment(value=EnvType.CLIENT)
    static interface Callbacks<T> {
        public Function<SimpleChoice<T>, ClickableWidget> getButtonCreator(SimpleOption.TooltipFactory<T> var1, GameOptions var2, int var3, int var4, int var5, Consumer<T> var6);

        public Optional<T> validate(T var1);

        public Codec<T> codec();
    }

    @Environment(value=EnvType.CLIENT)
    static interface CyclingCallbacks<T>
            extends Callbacks<T> {
        public CyclingButtonWidget.Values<T> getValues();

        default public CyclingCallbacks.ValueSetter<T> valueSetter() {
            return SimpleChoice::setValue;
        }

        @Override
        default public Function<SimpleChoice<T>, ClickableWidget> getButtonCreator(SimpleOption.TooltipFactory<T> tooltipFactory, GameOptions gameOptions, int x, int y, int width, Consumer<T> changeCallback) {
            return option -> CyclingButtonWidget.builder(option.textGetter).values(this.getValues()).tooltip(tooltipFactory).initially(option.value).build(x, y, width, 20, Text.of(""), (button, value) -> {
                this.valueSetter().set((SimpleChoice<T>)option, value);
                gameOptions.write();
                changeCallback.accept(value);
            });
        }

        @Environment(value=EnvType.CLIENT)
        public static interface ValueSetter<T> {
            public void set(SimpleChoice<T> var1, T var2);
        }
    }
}
