package insertData.utils;

import com.sun.deploy.net.proxy.ProxyUnavailableException;
import jedis.util.ReadProperties;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;

import java.util.*;

public class RedisConfUtil {

    private static Integer serversNum = 0;
    private final static String serverKey = "redis.server";
    private final static String passwordKey = "redis.password";
    private final static String splitConfKey = "\\|";
    private final static String splitServerKey = ",";
    private final static String splitHostAndPortKey = ":";

    private final static List<RedisConf> redisConfS = new ArrayList<>();


    static {

        /**
         * 获取server信息
         */

        String server = ReadProperties.getValue(serverKey);
//        System.out.println(server);
        String password = ReadProperties.getValue(passwordKey);
        String[] passWords = {};


        /***
         * 获取"|"分割后的
         */

        String[] servers = server.split(splitConfKey);
        if (!StringUtils.isEmpty(password)) {
            passWords = password.split(splitConfKey);
            if(servers.length != passWords.length){
                throw new RuntimeException("password is null or server is null ");
            }
        }

        /**
         * 获取单个结点
         */


        for (int i = 0; i < servers.length; i++) {

            List<String> hostAndSevers = Arrays.asList(servers[i].split(splitServerKey));
            RedisConf redisConf = new RedisConf();
            if (hostAndSevers.size() == 1) {
                redisConf.setCluster(false);
            }
            HashSet<HostAndPort> hostAndPorts = new HashSet<>();

            hostAndSevers.forEach(str -> {
                String[] hostAndPort = str.split(splitHostAndPortKey);
                if(hostAndPort.length !=2){
                    throw new RuntimeException("hostAndPort.length ！=2");
                }
                hostAndPorts.add(new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
            });

            redisConf.setHostAndPorts(hostAndPorts);

            if(!StringUtils.isEmpty(password)){
                redisConf.setPassWord(passWords[i]);
            }

            serversNum++;
//            System.out.println(serversNum);
            redisConfS.add(redisConf);

//            for (RedisConf N : redisConfS) {
//                System.out.println(N);
//            }

        }
    }


    public static List<RedisConf> getRedisConfS() {
        return redisConfS;
    }


    public static class RedisConf {

        private Boolean isCluster = true;
        private Set<HostAndPort> hostAndPorts;
        private String passWord;


        public Boolean getCluster() {
            return isCluster;
        }

        public void setCluster(Boolean cluster){
            isCluster = cluster;

        }

        public Set<HostAndPort> getHostAndPorts() {
            return hostAndPorts;
        }

        public void setHostAndPorts(Set<HostAndPort> hostAndPorts) {
            this.hostAndPorts = hostAndPorts;
        }

        public String getPassword() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }
//        @Override
//        public String toString(){
//            return isCluster + hostAndPorts + passWord;
//        }


    }


}
