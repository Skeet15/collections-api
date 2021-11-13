package com.collections.shared.converter;

import java.util.function.Function;

public interface GenericConverter <I, O> extends Function<I, O> {
    default O convert(final I input) {
        O output = null;
        if (input != null) {
            output = this.apply(input);
        }
        return output;
    }
}
