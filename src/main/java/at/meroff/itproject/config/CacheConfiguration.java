package at.meroff.itproject.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(at.meroff.itproject.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Institute.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Institute.class.getName() + ".curricula", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Institute.class.getName() + ".lvas", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Curriculum.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Curriculum.class.getName() + ".idealPlans", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Curriculum.class.getName() + ".institutes", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Curriculum.class.getName() + ".curriculumSemesters", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Subject.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Subject.class.getName() + ".curriculumsubjects", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Subject.class.getName() + ".idealplanentries", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Subject.class.getName() + ".lvas", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.IdealPlan.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.IdealPlan.class.getName() + ".idealplanentries", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.IdealPlanEntries.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CurriculumSubject.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CurriculumSubject.class.getName() + ".collCSSources", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CurriculumSubject.class.getName() + ".collCSTargets", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CurriculumSubject.class.getName() + ".lvas", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Lva.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Lva.class.getName() + ".appointments", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Lva.class.getName() + ".csl1S", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Lva.class.getName() + ".csl2S", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Lva.class.getName() + ".curriculumsubjects", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Appointment.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CurriculumSemester.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CurriculumSemester.class.getName() + ".curriculumSubjects", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelOne.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CurriculumSubject.class.getName() + ".collisionLevelOnes", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelOne.class.getName() + ".collisionLevelTwos", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelTwo.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelTwo.class.getName() + ".collisionLevelThrees", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelThree.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelThree.class.getName() + ".collisionLevelFours", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelFour.class.getName(), jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelFour.class.getName() + ".sourceAppointments", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelFour.class.getName() + ".targetAppointments", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Appointment.class.getName() + ".sourceCollisionLevelFours", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.Appointment.class.getName() + ".targetCollisionLevelFours", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelFour.class.getName() + ".collisionLevelFives", jcacheConfiguration);
            cm.createCache(at.meroff.itproject.domain.CollisionLevelFive.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
