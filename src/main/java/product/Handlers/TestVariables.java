package product.Handlers;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.SneakyThrows;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.SessionMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Getter
public enum TestVariables implements ITestVariables {
    RANDOM_FIRST_NAME,
    RANDOM_LAST_NAME,
    RANDOM_CITY,
    RANDOM_STREET_ADDRESS,
    RANDOM_DATE_OF_BIRTH("01/01/1930", "01/01/2000"),
    RANDOM_ZIP_CODE,
    RANDOM_STATE,
    RANDOM_PASSWORD,
    RANDOM_EMAIL("candidate+", "@upgrade-challenge.com"),
    RANDOM_UUID("candidate+", "@upgrade-challenge.com");

    String text;
    Object ruleOne;
    Object ruleTwo;

    TestVariables() {
        this.text = this.name().toLowerCase();
    }

    TestVariables(Object ruleOne, Object ruleTwo) {
        this();
        this.ruleOne = ruleOne;
        this.ruleTwo = ruleTwo;
    }

    @Override
    @SneakyThrows
    public <T> T get() {
        Faker faker = new Faker();
        SessionMap<Object, Object> session = Serenity.getCurrentSession();
        Object exact = session.get(this);
        if (exact == null) {
            switch (this) {
                case RANDOM_FIRST_NAME:
                    exact = faker.name().firstName();
                    break;
                case RANDOM_LAST_NAME:
                    exact = faker.name().lastName();
                    break;
                case RANDOM_STREET_ADDRESS:
                    exact = faker.address().streetAddress();
                    break;
                case RANDOM_CITY:
                    exact = faker.address().cityName();
                    break;
                case RANDOM_ZIP_CODE:
                    exact = faker.address().zipCode();
                    break;
                case RANDOM_STATE:
                    exact = faker.address().stateAbbr();
                    break;
                case RANDOM_DATE_OF_BIRTH:
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date dateBefore = dateFormat.parse(this.ruleOne.toString());
                    Date dateAfter = dateFormat.parse(this.ruleTwo.toString());
                    exact = dateFormat.format(faker.date().between(dateBefore, dateAfter));
                    break;
                case RANDOM_EMAIL:
                    exact = "" + this.ruleOne + new Date().getTime() + this.ruleTwo;
                    break;
                case RANDOM_PASSWORD:
                    exact = "qaA1" + faker.internet().password(9, 11, true);
                    break;
                case RANDOM_UUID:
                    exact = "" + UUID.randomUUID();
                    break;
            }
            session.put(this, exact);
        }
        return (T) exact;
    }
}

