package product.Handlers;

import framework.IEnumElements;

public interface ITestVariables extends IEnumElements {

    String name();

    String getText();

    <T> T get();

    default String getString() {
        Object object = this.get();
        return (object == null) ? null : object.toString();
    }
}
