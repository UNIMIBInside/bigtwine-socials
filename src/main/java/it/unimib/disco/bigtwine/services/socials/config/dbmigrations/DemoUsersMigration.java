package it.unimib.disco.bigtwine.services.socials.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.eluder.spring.social.mongodb.MongoConnection;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeLog(order = "001")
public class DemoUsersMigration {
    @ChangeSet(order = "01", author = "initiator", id = "01-addDemoUsers")
    public void addDemoUsers(MongoTemplate mongoTemplate) {
        MongoConnection demoConnection = new MongoConnection();
        demoConnection.setUserId("demo");
        demoConnection.setProviderId("twitter");
        demoConnection.setProviderUserId("1190939321529966592");
        demoConnection.setDisplayName("@big_twine");
        demoConnection.setAccessToken("470cd39ebe709016f30c3ba5438701b34a40159037d4a11a0565d57581a74d05800cd4fcfbe559555ff352648cb6e8ae19cbc2949d88c63aa27c5d5ace22ae6f1f63fe31670b2520102949691fb37a90");
        demoConnection.setSecret("de0a6e0ca796b296d7644c4becb3ba2d53fefb6ef8afc2fcd373b05516335d6cb79002d0e26c0229ba6c8a23dce3bf0b69c51110719381005b10b9a66a3d4aa5");
        demoConnection.setProfileUrl("http://twitter.com/big_twine");
        demoConnection.setImageUrl("http://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png");

        mongoTemplate.save(demoConnection);
    }
}
