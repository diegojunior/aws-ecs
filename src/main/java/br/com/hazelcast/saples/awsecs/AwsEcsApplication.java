package br.com.hazelcast.saples.awsecs;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AwsEcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsEcsApplication.class, args);
    }

    private static Config config;

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.getNetworkConfig().getInterfaces().setEnabled(true).addInterface("10.0.*.*");
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getAwsConfig()
                .setEnabled(true)
                .setProperty("iam-role", "ecsTaskExecutionRole")
                .setProperty("region", "us-east-1")
                .setProperty("tag-key", "aws:cloudformation:stack-name")
                .setProperty("tag-value", "EC2ContainerService-test-cluster");
        return config;
    }


}
