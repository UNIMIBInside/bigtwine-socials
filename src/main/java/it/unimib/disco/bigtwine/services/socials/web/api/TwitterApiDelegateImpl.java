package it.unimib.disco.bigtwine.services.socials.web.api;

import it.unimib.disco.bigtwine.services.socials.domain.RequestToken;
import it.unimib.disco.bigtwine.services.socials.repository.RequestTokenRepository;
import it.unimib.disco.bigtwine.services.socials.web.api.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.*;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;


import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class TwitterApiDelegateImpl implements TwitterApiDelegate {

    private final NativeWebRequest request;
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final UsersConnectionRepository usersConnectionRepository;
    private final RequestTokenRepository requestTokenRepository;

    // private final String consumerKey = "0NJb4MIbWfmM6WSWe0V4P1xpI";
    // private final String consumerSecret = "rBg3TxXPjgvJFvhWlxd0QWTD3aaIh1aeAY2oWOzUT9fcoqhIeT";
    private Map<String, OAuthToken> requestTokens = new HashMap<>();
    private Map<String, OAuthToken> accessTokens = new HashMap<>();

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TwitterApiDelegateImpl(
        NativeWebRequest request,
        ConnectionFactoryLocator connectionLocator,
        UsersConnectionRepository usersConnectionRepository,
        RequestTokenRepository requestTokenRepository) {
        this.request = request;
        this.connectionFactoryLocator = connectionLocator;
        this.usersConnectionRepository = usersConnectionRepository;
        this.requestTokenRepository = requestTokenRepository;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<InlineResponse200> twitterTryV1(String accessToken) {
        OAuth10Provider provider = OAuth10Provider.valueOf("TWITTER");
        // TwitterConnectionFactory connectionFactory = (TwitterConnectionFactory)this.connectionFactoryLocator.getConnectionFactory("twitter");
        // OAuthToken oAuthToken = this.accessTokens.get(accessToken);
        // Connection<Twitter> connection = connectionFactory.createConnection(oAuthToken);
        Connection<Twitter> connection = this.usersConnectionRepository
            .createConnectionRepository("1")
            .getPrimaryConnection(Twitter.class);

        Twitter twitter = connection.getApi();

        String screenName = twitter.userOperations().getScreenName();

        return ResponseEntity.ok(new InlineResponse200().name(screenName));
    }
}
