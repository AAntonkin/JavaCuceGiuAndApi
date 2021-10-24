package product.Handlers;

import framework.IEnumElements;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

@Getter
public enum VerificationVariables implements ITestVariables {
    DEFAULT,
    EMPTY,
    NOT_EMPTY;

    String text;

    VerificationVariables() {
        this.text = this.name().toLowerCase();
    }

    static public VerificationVariables getByText(@NonNull final String text) {
        Optional<VerificationVariables> _this = Optional.ofNullable(IEnumElements.getByText(text, VerificationVariables.class, true));
        return _this.orElse(DEFAULT);
    }

    @Override
    public <T> T get() {
        return (T) this;
    }
}
