package faker;

import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class QuoteFaker extends Faker {

    public QuoteFaker() {
    }

    public QuoteFaker(Locale locale) {
        super(locale);
    }

    public QuoteFaker(Random random) {
        super(random);
    }

    public QuoteFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public UserEntityProvider userEntity() {
        return getProvider(UserEntityProvider.class, UserEntityProvider::new, this);
    }

    public AuthEntityProvider authEntity() {
        return getProvider(AuthEntityProvider.class, AuthEntityProvider::new, this);
    }

    public QuoteEntityProvider quoteEntity() {
        return getProvider(QuoteEntityProvider.class, QuoteEntityProvider::new, this);
    }

    public SourceEntityProvider sourceEntity() {
        return getProvider(SourceEntityProvider.class, SourceEntityProvider::new, this);
    }

    public SourceTypeEntityProvider sourceTypeEntity() {
        return getProvider(SourceTypeEntityProvider.class, SourceTypeEntityProvider::new, this);
    }

}
