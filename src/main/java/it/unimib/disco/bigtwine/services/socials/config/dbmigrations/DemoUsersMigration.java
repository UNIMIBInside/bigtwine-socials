package it.unimib.disco.bigtwine.services.socials.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.eluder.spring.social.mongodb.MongoConnection;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

@ChangeLog(order = "001")
public class DemoUsersMigration {
    @ChangeSet(order = "01", author = "initiator", id = "01-addDemoUsers")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        MongoConnection demoConnection = new MongoConnection();
        demoConnection.setUserId("demo");
        demoConnection.setProviderId("twitter");
        demoConnection.setProviderUserId("1190939321529966592");
        demoConnection.setDisplayName("@big_twine");
        demoConnection.setAccessToken("1190939321529966592-f1wsxlZiXXKxiNYqMFaMadm4Vwu91S");
        demoConnection.setSecret("f5rw1g3UKatRpKmByC3VqLDsStRGXAxwbVH2lH1E92zUi");
        demoConnection.setProfileUrl("http://twitter.com/big_twine");
        demoConnection.setImageUrl("http://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png");
        demoConnection.setCreated(new Date());

        mongoTemplate.save(demoConnection);
    }
}
