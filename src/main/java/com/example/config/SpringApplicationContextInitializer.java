package com.example.config;

import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.MongoServiceInfo;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * Map of Service Types in Cloud Foundry
     */
    private static final Map<Class<? extends ServiceInfo>, String> serviceTypeToProfileName = new HashMap<>();

    static {
        serviceTypeToProfileName.put(MongoServiceInfo.class, "mongodb");
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        Cloud cloud = getCloud();

        ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
        String[] persistenceProfiles = getCloudProfile(cloud);
        if (persistenceProfiles == null) {
            persistenceProfiles = getActiveProfile(configurableEnvironment);
        }
        if (persistenceProfiles == null) {
            persistenceProfiles = new String[]{"in-memory"};
        }
        for (String persistenceProfile : persistenceProfiles) {
            configurableEnvironment.addActiveProfile(persistenceProfile);
        }

    }

    private String[] getCloudProfile(Cloud cloud) {
        if (cloud == null) {
            return null;
        }

        List<String> profiles = new ArrayList<>();
        List<ServiceInfo> serviceInfos = cloud.getServiceInfos();

        /** get service info from your cloud foundry account */
        for (ServiceInfo serviceInfo : serviceInfos) {
            if (serviceTypeToProfileName.containsKey(serviceInfo.getClass())) {
                profiles.add(serviceTypeToProfileName.get(serviceInfo.getClass()));
            }
        }

        if (profiles.size() > 1) {
            throw new IllegalStateException(
                    "Only one service of the following types may be bound to this application: " +
                            serviceTypeToProfileName.values().toString() + ". " +
                            "These services are bound to the application: [" +
                            StringUtils.collectionToCommaDelimitedString(profiles) + "]");
        }

        if (profiles.size() > 0) {
            return createProfileNames(profiles.get(0), "cloud");
        }

        return null;
    }

    private String[] getActiveProfile(ConfigurableEnvironment appEnvironment) {
        String serviceProfile = null;

        for (String profile : appEnvironment.getActiveProfiles()) {
            if (profile.compareTo("mongodb") == 0) {
                serviceProfile = profile;
            }
        }
        if (serviceProfile == null) {
            return null;
        } else {
            return createProfileNames(serviceProfile, "local");
        }
    }

    private String[] createProfileNames(String baseName, String suffix) {
        String[] profileNames = {baseName, baseName + "-" + suffix};
        return profileNames;
    }

    private Cloud getCloud() {
        try {
            CloudFactory cloudFactory = new CloudFactory();
            return cloudFactory.getCloud();
        } catch (CloudException ce) {
            return null;
        }
    }
}
