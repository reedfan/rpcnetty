package reed.ustc.factory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * created by reedfan on 2019/10/10 0010
 */
public class ZookeeperFactory {
    public static  CuratorFramework client;
    public static CuratorFramework create(){
        if(client == null){
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
            client = CuratorFrameworkFactory.newClient("47.101.47.253:2181",retryPolicy);
            client.start();
        }

        return client;

    }

    public static void main(String[] args) {
        CuratorFramework curatorFramework = create();
        try {
            curatorFramework.create().forPath("/test123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
