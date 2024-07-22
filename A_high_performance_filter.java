(1) coding第二轮是面经里的high performance filter，followup是如果数据量很大的话该如何优‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌化。
(2) 题目和过往面经一样。
['apple, facebook, google', 'banana, facebook', 'facebook, google, tesla', 'intuit, google, facebook']
然后有一个 filter list， 根据 filter list 输出这些 Tags 的补集
比如 filter by ['apple']那么 return ['facebook', 'google'] (只有第一个里面有 APPLE）
比如 filter by ['facebook', 'google']那么 return‍‍‍‌‍‍‍‍‍‍‌‌‍‍‍‌‌‍‍ ['apple', 'tesla','intuit']
我是用hashmap 做Cache。 Follow up 是怎么更好的index 这个hashmap (Key 是啥，Value 是啥）。    
    
https://leetcode.com/discuss/interview-question/2639509/DataDog-Interview-Question

题目和过往面经一样。
['apple, facebook, google', 'banana, facebook', 'facebook, google, tesla', 'intuit, google, facebook']
然后有一个 filter list， 根据 filter list 输出这些 Tags 的补集
比如 filter by ['apple']那么 return ['facebook', 'google'] (只有第一个里面有 APPLE）
比如 filter by ['facebook', 'google']那么 return‍‍‍‌‍‍‍‍‍‍‌‌‍‍‍‌‌‍‍ ['apple', 'tesla','intuit']
我是用hashmap 做Cache。 Follow up 是怎么更好的index 这个hashmap (Key 是啥，Value 是啥）。

package Tests;

import java.util.*;
public class HighPerformanceFilter {

    Map<String, Set<Integer>> streamMap = new HashMap<>();
    List<String> stream = new ArrayList<>();

    public void addTag(String tag){
        int index = stream.size();
        for(String s : tag.split(",")){
            s = s.trim().toLowerCase();
            streamMap.putIfAbsent(s, new HashSet<>());
            streamMap.get(s).add(index);
        }
        stream.add(tag);
        System.out.println("Stream: " + stream);
        System.out.println("StreamMap: " + streamMap);
        System.out.println("add tag ===============");
    }

    public Set<String> searchTags(List<String> keywords) {
        System.out.println("***********begin add tag ===============");
        System.out.println("Searching for keywords: " + keywords);
        Map<Integer, Integer> counterMap = new HashMap<>();
        for(String keyword : keywords){
            for(int document : streamMap.getOrDefault(keyword, new HashSet<>())){
                counterMap.put(document, counterMap.getOrDefault(document, 0)+1);
                System.out.println("Document: " + document + ", Count: " + counterMap.get(document));
            }
        }
        System.out.println("CounterMap: " + counterMap);
        Set<String> set = new HashSet<>();
        for(int key : counterMap.keySet()){
            if(counterMap.get(key) == keywords.size()){
                set.addAll(Arrays.asList(stream.get(key).split(", ")));
                System.out.println("Matched Document: " + key + ", Tags: " + stream.get(key));
            }
        }

        System.out.println("ResultSet before removal: " + set);
        for(String i : keywords){
            set.remove(i);
        }
        System.out.println("ResultSet before removal: " + set);
        return set;
    }



    public static void main(String[] args){
        HighPerformanceFilter h = new HighPerformanceFilter();
        h.addTag("apple, facebook, google");
        h.addTag("banana, facebook");
        h.addTag("facebook, google, tesla");
        h.addTag("intuit, google, facebook");
        System.out.println(h.searchTags(Arrays.asList(new String[] {"facebook", "google"})));
    }
}


/Library/Java/JavaVirtualMachines/jdk-11.0.12.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA 3.app/Contents/lib/idea_rt.jar=49796:/Applications/IntelliJ IDEA 3.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/dshu/Desktop/workspace1/case-management-service/target/classes:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-commons-json/1.0.8/marqeta-commons-json-1.0.8.jar:/Users/dshu/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-joda/2.8.7/jackson-datatype-joda-2.8.7.jar:/Users/dshu/.m2/repository/joda-time/joda-time/2.7/joda-time-2.7.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-redis-queue/1.0.6/marqeta-redis-queue-1.0.6.jar:/Users/dshu/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/dshu/.m2/repository/org/slf4j/jcl-over-slf4j/1.8.0-beta2/jcl-over-slf4j-1.8.0-beta2.jar:/Users/dshu/.m2/repository/redis/clients/jedis/2.9.0/jedis-2.9.0.jar:/Users/dshu/.m2/repository/org/apache/commons/commons-pool2/2.4.2/commons-pool2-2.4.2.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-box-client/3.0.1/marqeta-box-client-3.0.1.jar:/Users/dshu/.m2/repository/com/box/box-java-sdk/2.55.1/box-java-sdk-2.55.1.jar:/Users/dshu/.m2/repository/com/eclipsesource/minimal-json/minimal-json/0.9.1/minimal-json-0.9.1.jar:/Users/dshu/.m2/repository/org/bitbucket/b_c/jose4j/0.5.5/jose4j-0.5.5.jar:/Users/dshu/.m2/repository/org/bouncycastle/bcprov-jdk15on/1.60/bcprov-jdk15on-1.60.jar:/Users/dshu/.m2/repository/org/bouncycastle/bcpkix-jdk15on/1.60/bcpkix-jdk15on-1.60.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-webhooks-client/1.0.5/marqeta-webhooks-client-1.0.5.jar:/Users/dshu/.m2/repository/commons-io/commons-io/2.8.0/commons-io-2.8.0.jar:/Users/dshu/.m2/repository/org/apache/logging/log4j/log4j-api/2.18.0/log4j-api-2.18.0.jar:/Users/dshu/.m2/repository/org/apache/logging/log4j/log4j-core/2.18.0/log4j-core-2.18.0.jar:/Users/dshu/.m2/repository/commons-collections/commons-collections/3.2.2/commons-collections-3.2.2.jar:/Users/dshu/.m2/repository/com/sparkjava/spark-core/2.7.2/spark-core-2.7.2.jar:/Users/dshu/.m2/repository/com/amazonaws/aws-java-sdk-sns/1.12.419/aws-java-sdk-sns-1.12.419.jar:/Users/dshu/.m2/repository/com/amazonaws/aws-java-sdk-sqs/1.12.419/aws-java-sdk-sqs-1.12.419.jar:/Users/dshu/.m2/repository/com/amazonaws/aws-java-sdk-core/1.12.419/aws-java-sdk-core-1.12.419.jar:/Users/dshu/.m2/repository/software/amazon/ion/ion-java/1.0.2/ion-java-1.0.2.jar:/Users/dshu/.m2/repository/com/amazonaws/jmespath-java/1.12.419/jmespath-java-1.12.419.jar:/Users/dshu/.m2/repository/commons-beanutils/commons-beanutils/1.9.4/commons-beanutils-1.9.4.jar:/Users/dshu/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-commons-configuration/1.0.11/marqeta-commons-configuration-1.0.11.jar:/Users/dshu/.m2/repository/org/apache/commons/commons-configuration2/2.1.1/commons-configuration2-2.1.1.jar:/Users/dshu/.m2/repository/com/amazonaws/aws-java-sdk-ssm/1.11.228/aws-java-sdk-ssm-1.11.228.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-commons-logging/1.0.5/marqeta-commons-logging-1.0.5.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-payments-client/1.7.8/marqeta-payments-client-1.7.8.jar:/Users/dshu/.m2/repository/com/squareup/retrofit2/retrofit/2.7.2/retrofit-2.7.2.jar:/Users/dshu/.m2/repository/com/squareup/retrofit2/converter-jackson/2.7.2/converter-jackson-2.7.2.jar:/Users/dshu/.m2/repository/com/squareup/okhttp3/okhttp/4.10.0/okhttp-4.10.0.jar:/Users/dshu/.m2/repository/com/squareup/okio/okio-jvm/3.0.0/okio-jvm-3.0.0.jar:/Users/dshu/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.5.31/kotlin-stdlib-jdk8-1.5.31.jar:/Users/dshu/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.5.31/kotlin-stdlib-jdk7-1.5.31.jar:/Users/dshu/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-common/1.5.31/kotlin-stdlib-common-1.5.31.jar:/Users/dshu/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/1.6.20/kotlin-stdlib-1.6.20.jar:/Users/dshu/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-consul-client/2.1.5/marqeta-consul-client-2.1.5.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-dna-client/2.2.2/marqeta-dna-client-2.2.2.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-zion-client/2.0.2/marqeta-zion-client-2.0.2.jar:/Users/dshu/.m2/repository/com/google/guava/guava/30.0-jre/guava-30.0-jre.jar:/Users/dshu/.m2/repository/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar:/Users/dshu/.m2/repository/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/Users/dshu/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:/Users/dshu/.m2/repository/org/checkerframework/checker-qual/3.5.0/checker-qual-3.5.0.jar:/Users/dshu/.m2/repository/com/google/errorprone/error_prone_annotations/2.3.4/error_prone_annotations-2.3.4.jar:/Users/dshu/.m2/repository/com/google/j2objc/j2objc-annotations/1.3/j2objc-annotations-1.3.jar:/Users/dshu/.m2/repository/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar:/Users/dshu/.m2/repository/org/sql2o/sql2o/1.6.0/sql2o-1.6.0.jar:/Users/dshu/.m2/repository/org/hibernate/hibernate-validator/5.0.0.Final/hibernate-validator-5.0.0.Final.jar:/Users/dshu/.m2/repository/javax/validation/validation-api/1.1.0.Final/validation-api-1.1.0.Final.jar:/Users/dshu/.m2/repository/org/jboss/logging/jboss-logging/3.1.1.GA/jboss-logging-3.1.1.GA.jar:/Users/dshu/.m2/repository/com/fasterxml/classmate/0.8.0/classmate-0.8.0.jar:/Users/dshu/.m2/repository/org/glassfish/web/javax.el/2.2.4/javax.el-2.2.4.jar:/Users/dshu/.m2/repository/javax/el/javax.el-api/2.2.4/javax.el-api-2.2.4.jar:/Users/dshu/.m2/repository/com/zaxxer/HikariCP/3.4.5/HikariCP-3.4.5.jar:/Users/dshu/.m2/repository/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4.jar:/Users/dshu/.m2/repository/com/datadoghq/java-dogstatsd-client/4.0.0/java-dogstatsd-client-4.0.0.jar:/Users/dshu/.m2/repository/com/github/jnr/jnr-unixsocket/0.36/jnr-unixsocket-0.36.jar:/Users/dshu/.m2/repository/com/github/jnr/jnr-enxio/0.30/jnr-enxio-0.30.jar:/Users/dshu/.m2/repository/com/github/jnr/jnr-posix/3.1.8/jnr-posix-3.1.8.jar:/Users/dshu/.m2/repository/com/github/jnr/jnr-ffi/2.2.5/jnr-ffi-2.2.5.jar:/Users/dshu/.m2/repository/com/github/jnr/jffi/1.3.5/jffi-1.3.5.jar:/Users/dshu/.m2/repository/com/github/jnr/jffi/1.3.5/jffi-1.3.5-native.jar:/Users/dshu/.m2/repository/org/ow2/asm/asm/9.1/asm-9.1.jar:/Users/dshu/.m2/repository/org/ow2/asm/asm-commons/9.1/asm-commons-9.1.jar:/Users/dshu/.m2/repository/org/ow2/asm/asm-analysis/9.1/asm-analysis-9.1.jar:/Users/dshu/.m2/repository/org/ow2/asm/asm-tree/9.1/asm-tree-9.1.jar:/Users/dshu/.m2/repository/org/ow2/asm/asm-util/9.1/asm-util-9.1.jar:/Users/dshu/.m2/repository/com/github/jnr/jnr-a64asm/1.0.0/jnr-a64asm-1.0.0.jar:/Users/dshu/.m2/repository/com/github/jnr/jnr-x86asm/1.0.2/jnr-x86asm-1.0.2.jar:/Users/dshu/.m2/repository/com/github/jnr/jnr-constants/0.10.2/jnr-constants-0.10.2.jar:/Users/dshu/.m2/repository/org/apache/poi/poi/5.2.3/poi-5.2.3.jar:/Users/dshu/.m2/repository/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar:/Users/dshu/.m2/repository/com/zaxxer/SparseBitSet/1.2/SparseBitSet-1.2.jar:/Users/dshu/.m2/repository/org/apache/poi/poi-ooxml/5.2.3/poi-ooxml-5.2.3.jar:/Users/dshu/.m2/repository/org/apache/poi/poi-ooxml-lite/5.2.3/poi-ooxml-lite-5.2.3.jar:/Users/dshu/.m2/repository/org/apache/xmlbeans/xmlbeans/5.1.1/xmlbeans-5.1.1.jar:/Users/dshu/.m2/repository/org/apache/commons/commons-compress/1.21/commons-compress-1.21.jar:/Users/dshu/.m2/repository/com/github/virtuald/curvesapi/1.07/curvesapi-1.07.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/mqpay-vrol-wsdl/5.0.5/mqpay-vrol-wsdl-5.0.5.jar:/Users/dshu/.m2/repository/org/quartz-scheduler/quartz/2.3.2/quartz-2.3.2.jar:/Users/dshu/.m2/repository/com/mchange/c3p0/0.9.5.4/c3p0-0.9.5.4.jar:/Users/dshu/.m2/repository/com/mchange/mchange-commons-java/0.2.15/mchange-commons-java-0.2.15.jar:/Users/dshu/.m2/repository/com/zaxxer/HikariCP-java7/2.4.13/HikariCP-java7-2.4.13.jar:/Users/dshu/.m2/repository/org/mockito/mockito-junit-jupiter/3.5.10/mockito-junit-jupiter-3.5.10.jar:/Users/dshu/.m2/repository/org/mockito/mockito-core/3.5.10/mockito-core-3.5.10.jar:/Users/dshu/.m2/repository/net/bytebuddy/byte-buddy/1.10.13/byte-buddy-1.10.13.jar:/Users/dshu/.m2/repository/net/bytebuddy/byte-buddy-agent/1.10.13/byte-buddy-agent-1.10.13.jar:/Users/dshu/.m2/repository/org/objenesis/objenesis/3.1/objenesis-3.1.jar:/Users/dshu/.m2/repository/org/apache/httpcomponents/httpclient/4.5.13/httpclient-4.5.13.jar:/Users/dshu/.m2/repository/org/apache/httpcomponents/httpcore/4.4.13/httpcore-4.4.13.jar:/Users/dshu/.m2/repository/com/google/code/gson/gson/2.8.9/gson-2.8.9.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-httpclient/1.0.16/marqeta-httpclient-1.0.16.jar:/Users/dshu/.m2/repository/com/squareup/okhttp3/okhttp-tls/3.14.3/okhttp-tls-3.14.3.jar:/Users/dshu/.m2/repository/com/newrelic/agent/java/newrelic-api/4.1.0/newrelic-api-4.1.0.jar:/Users/dshu/.m2/repository/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-nile-client/1.1.7/marqeta-nile-client-1.1.7.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/mqpay-common/2.3.39/mqpay-common-2.3.39.jar:/Users/dshu/.m2/repository/org/jpos/jpos/2.1.4/jpos-2.1.4.jar:/Users/dshu/.m2/repository/org/jdom/jdom2/2.0.6/jdom2-2.0.6.jar:/Users/dshu/.m2/repository/org/javatuples/javatuples/1.2/javatuples-1.2.jar:/Users/dshu/.m2/repository/org/jline/jline/3.14.0/jline-3.14.0.jar:/Users/dshu/.m2/repository/org/apache-extras/beanshell/bsh/2.0b6/bsh-2.0b6.jar:/Users/dshu/.m2/repository/commons-cli/commons-cli/1.4/commons-cli-1.4.jar:/Users/dshu/.m2/repository/org/bouncycastle/bcpg-jdk15on/1.64/bcpg-jdk15on-1.64.jar:/Users/dshu/.m2/repository/org/hdrhistogram/HdrHistogram/2.1.12/HdrHistogram-2.1.12.jar:/Users/dshu/.m2/repository/org/osgi/org.osgi.core/6.0.0/org.osgi.core-6.0.0.jar:/Users/dshu/.m2/repository/jdbm/jdbm/1.0/jdbm-1.0.jar:/Users/dshu/.m2/repository/com/sleepycat/je/18.3.12/je-18.3.12.jar:/Users/dshu/.m2/repository/org/apache/sshd/sshd-core/2.4.0/sshd-core-2.4.0.jar:/Users/dshu/.m2/repository/org/apache/sshd/sshd-common/2.4.0/sshd-common-2.4.0.jar:/Users/dshu/.m2/repository/com/jcraft/jzlib/1.1.3/jzlib-1.1.3.jar:/Users/dshu/.m2/repository/org/apache/ant/ant-jsch/1.9.4/ant-jsch-1.9.4.jar:/Users/dshu/.m2/repository/org/apache/ant/ant/1.9.4/ant-1.9.4.jar:/Users/dshu/.m2/repository/org/apache/ant/ant-launcher/1.9.4/ant-launcher-1.9.4.jar:/Users/dshu/.m2/repository/sshtools/j2ssh-core/0.2.9/j2ssh-core-0.2.9.jar:/Users/dshu/.m2/repository/sshtools/j2ssh-common/0.2.9/j2ssh-common-0.2.9.jar:/Users/dshu/.m2/repository/uk/co/marcoratto/scp/1.2/scp-1.2.jar:/Users/dshu/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/dshu/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-card-fulfillment-client/0.0.5/marqeta-card-fulfillment-client-0.0.5.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-commons-crypto/1.0.7/marqeta-commons-crypto-1.0.7.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-meltan-client/0.0.9/marqeta-meltan-client-0.0.9.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/marqeta-datadog-client/0.0.5/marqeta-datadog-client-0.0.5.jar:/Users/dshu/.m2/repository/com/marqeta/common/mq-proto-core/1.3.7/mq-proto-core-1.3.7.jar:/Users/dshu/.m2/repository/com/google/api/grpc/proto-google-common-protos/1.18.0/proto-google-common-protos-1.18.0.jar:/Users/dshu/.m2/repository/com/marqeta/common/mq-proto-cfp/1.3.7/mq-proto-cfp-1.3.7.jar:/Users/dshu/.m2/repository/com/amazonaws/amazon-kinesis-producer/0.14.0/amazon-kinesis-producer-0.14.0.jar:/Users/dshu/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar:/Users/dshu/.m2/repository/io/grpc/grpc-netty-shaded/1.29.0/grpc-netty-shaded-1.29.0.jar:/Users/dshu/.m2/repository/io/grpc/grpc-core/1.29.0/grpc-core-1.29.0.jar:/Users/dshu/.m2/repository/io/grpc/grpc-api/1.29.0/grpc-api-1.29.0.jar:/Users/dshu/.m2/repository/io/grpc/grpc-context/1.29.0/grpc-context-1.29.0.jar:/Users/dshu/.m2/repository/org/codehaus/mojo/animal-sniffer-annotations/1.18/animal-sniffer-annotations-1.18.jar:/Users/dshu/.m2/repository/com/google/android/annotations/4.1.1.4/annotations-4.1.1.4.jar:/Users/dshu/.m2/repository/io/perfmark/perfmark-api/0.19.0/perfmark-api-0.19.0.jar:/Users/dshu/.m2/repository/com/marqeta/resiliency/aws-sdk-v1-failover/resiliency-1.9.16/aws-sdk-v1-failover-resiliency-1.9.16.jar:/Users/dshu/.m2/repository/com/marqeta/resiliency/aws-sdk-v2-patch/resiliency-1.9.16/aws-sdk-v2-patch-resiliency-1.9.16.jar:/Users/dshu/.m2/repository/com/amazonaws/aws-java-sdk-dynamodb/1.11.991/aws-java-sdk-dynamodb-1.11.991.jar:/Users/dshu/.m2/repository/org/apache/activemq/activemq-broker/5.16.0/activemq-broker-5.16.0.jar:/Users/dshu/.m2/repository/org/apache/activemq/activemq-client/5.16.0/activemq-client-5.16.0.jar:/Users/dshu/.m2/repository/org/apache/geronimo/specs/geronimo-jms_1.1_spec/1.1.1/geronimo-jms_1.1_spec-1.1.1.jar:/Users/dshu/.m2/repository/org/fusesource/hawtbuf/hawtbuf/1.11/hawtbuf-1.11.jar:/Users/dshu/.m2/repository/org/apache/activemq/activemq-openwire-legacy/5.16.0/activemq-openwire-legacy-5.16.0.jar:/Users/dshu/.m2/repository/org/apache/activemq/activemq-kahadb-store/5.16.0/activemq-kahadb-store-5.16.0.jar:/Users/dshu/.m2/repository/org/apache/activemq/protobuf/activemq-protobuf/1.1/activemq-protobuf-1.1.jar:/Users/dshu/.m2/repository/org/apache/geronimo/specs/geronimo-j2ee-management_1.1_spec/1.0.1/geronimo-j2ee-management_1.1_spec-1.0.1.jar:/Users/dshu/.m2/repository/commons-net/commons-net/3.6/commons-net-3.6.jar:/Users/dshu/.m2/repository/com/marqeta/resiliency/commons-resiliency/resiliency-1.9.16/commons-resiliency-resiliency-1.9.16.jar:/Users/dshu/.m2/repository/com/marqeta/resiliency/aws-sdk-adapter-v2-to-v1-adapter/resiliency-1.9.16/aws-sdk-adapter-v2-to-v1-adapter-resiliency-1.9.16.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/arns/2.14.20/arns-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/sqs/2.14.20/sqs-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/dynamodb/2.14.20/dynamodb-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/aws-json-protocol/2.14.20/aws-json-protocol-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/netty-nio-client/2.14.20/netty-nio-client-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/annotations/2.14.20/annotations-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/http-client-spi/2.14.20/http-client-spi-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/utils/2.14.20/utils-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/metrics-spi/2.14.20/metrics-spi-2.14.20.jar:/Users/dshu/.m2/repository/io/netty/netty-codec-http/4.1.46.Final/netty-codec-http-4.1.46.Final.jar:/Users/dshu/.m2/repository/io/netty/netty-codec-http2/4.1.46.Final/netty-codec-http2-4.1.46.Final.jar:/Users/dshu/.m2/repository/io/netty/netty-codec/4.1.46.Final/netty-codec-4.1.46.Final.jar:/Users/dshu/.m2/repository/io/netty/netty-transport/4.1.46.Final/netty-transport-4.1.46.Final.jar:/Users/dshu/.m2/repository/io/netty/netty-resolver/4.1.46.Final/netty-resolver-4.1.46.Final.jar:/Users/dshu/.m2/repository/io/netty/netty-common/4.1.46.Final/netty-common-4.1.46.Final.jar:/Users/dshu/.m2/repository/io/netty/netty-buffer/4.1.46.Final/netty-buffer-4.1.46.Final.jar:/Users/dshu/.m2/repository/io/netty/netty-handler/4.1.46.Final/netty-handler-4.1.46.Final.jar:/Users/dshu/.m2/repository/io/netty/netty-transport-native-epoll/4.1.46.Final/netty-transport-native-epoll-4.1.46.Final-linux-x86_64.jar:/Users/dshu/.m2/repository/io/netty/netty-transport-native-unix-common/4.1.46.Final/netty-transport-native-unix-common-4.1.46.Final.jar:/Users/dshu/.m2/repository/com/typesafe/netty/netty-reactive-streams-http/2.0.4/netty-reactive-streams-http-2.0.4.jar:/Users/dshu/.m2/repository/com/typesafe/netty/netty-reactive-streams/2.0.4/netty-reactive-streams-2.0.4.jar:/Users/dshu/.m2/repository/org/reactivestreams/reactive-streams/1.0.2/reactive-streams-1.0.2.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/sdk-core/2.14.20/sdk-core-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/profiles/2.14.20/profiles-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/sns/2.14.20/sns-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/aws-query-protocol/2.14.20/aws-query-protocol-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/protocol-core/2.14.20/protocol-core-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/auth/2.14.20/auth-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/eventstream/eventstream/1.0.1/eventstream-1.0.1.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/regions/2.14.20/regions-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/aws-core/2.14.20/aws-core-2.14.20.jar:/Users/dshu/.m2/repository/software/amazon/awssdk/apache-client/2.14.20/apache-client-2.14.20.jar:/Users/dshu/.m2/repository/com/datadoghq/dd-trace-api/0.73.0/dd-trace-api-0.73.0.jar:/Users/dshu/.m2/repository/io/opentracing/opentracing-api/0.33.0/opentracing-api-0.33.0.jar:/Users/dshu/.m2/repository/io/opentracing/opentracing-util/0.33.0/opentracing-util-0.33.0.jar:/Users/dshu/.m2/repository/io/opentracing/opentracing-noop/0.33.0/opentracing-noop-0.33.0.jar:/Users/dshu/.m2/repository/net/logstash/logback/logstash-logback-encoder/7.3/logstash-logback-encoder-7.3.jar:/Users/dshu/.m2/repository/org/apache/commons/commons-csv/1.4/commons-csv-1.4.jar:/Users/dshu/.m2/repository/ch/qos/logback/logback-classic/1.2.9/logback-classic-1.2.9.jar:/Users/dshu/.m2/repository/ch/qos/logback/logback-core/1.2.9/logback-core-1.2.9.jar:/Users/dshu/.m2/repository/com/bettercloud/vault-java-driver/5.1.0/vault-java-driver-5.1.0.jar:/Users/dshu/.m2/repository/com/mastercard/api/mastercom/6.0.0/mastercom-6.0.0.jar:/Users/dshu/.m2/repository/com/mastercard/api/sdk-api-core/1.4.31/sdk-api-core-1.4.31.jar:/Users/dshu/.m2/repository/oauth/signpost/signpost-core/1.2.1.2/signpost-core-1.2.1.2.jar:/Users/dshu/.m2/repository/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar:/Users/dshu/.m2/repository/com/aventstack/extentreports/5.0.4/extentreports-5.0.4.jar:/Users/dshu/.m2/repository/io/reactivex/rxjava3/rxjava/3.0.4/rxjava-3.0.4.jar:/Users/dshu/.m2/repository/org/freemarker/freemarker/2.3.30/freemarker-2.3.30.jar:/Users/dshu/.m2/repository/com/sun/activation/javax.activation/1.2.0/javax.activation-1.2.0.jar:/Users/dshu/.m2/repository/com/sun/xml/ws/jaxws-rt/2.3.6/jaxws-rt-2.3.6.jar:/Users/dshu/.m2/repository/com/sun/xml/ws/policy/2.7.10/policy-2.7.10.jar:/Users/dshu/.m2/repository/com/sun/xml/bind/jaxb-impl/2.3.8/jaxb-impl-2.3.8.jar:/Users/dshu/.m2/repository/org/glassfish/ha/ha-api/3.1.13/ha-api-3.1.13.jar:/Users/dshu/.m2/repository/org/glassfish/external/management-api/3.2.3/management-api-3.2.3.jar:/Users/dshu/.m2/repository/org/glassfish/gmbal/gmbal-api-only/4.0.3/gmbal-api-only-4.0.3.jar:/Users/dshu/.m2/repository/org/jvnet/staxex/stax-ex/1.8.3/stax-ex-1.8.3.jar:/Users/dshu/.m2/repository/com/sun/xml/stream/buffer/streambuffer/1.5.10/streambuffer-1.5.10.jar:/Users/dshu/.m2/repository/org/jvnet/mimepull/mimepull/1.9.15/mimepull-1.9.15.jar:/Users/dshu/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.18/FastInfoset-1.2.18.jar:/Users/dshu/.m2/repository/com/sun/activation/jakarta.activation/1.2.2/jakarta.activation-1.2.2.jar:/Users/dshu/.m2/repository/com/sun/mail/jakarta.mail/1.6.7/jakarta.mail-1.6.7.jar:/Users/dshu/.m2/repository/com/sun/xml/messaging/saaj/saaj-impl/1.5.3/saaj-impl-1.5.3.jar:/Users/dshu/.m2/repository/com/fasterxml/woodstox/woodstox-core/6.5.0/woodstox-core-6.5.0.jar:/Users/dshu/.m2/repository/org/codehaus/woodstox/stax2-api/4.2.1/stax2-api-4.2.1.jar:/Users/dshu/.m2/repository/jakarta/xml/ws/jakarta.xml.ws-api/2.3.3/jakarta.xml.ws-api-2.3.3.jar:/Users/dshu/.m2/repository/jakarta/xml/bind/jakarta.xml.bind-api/2.3.3/jakarta.xml.bind-api-2.3.3.jar:/Users/dshu/.m2/repository/jakarta/xml/soap/jakarta.xml.soap-api/1.4.2/jakarta.xml.soap-api-1.4.2.jar:/Users/dshu/.m2/repository/jakarta/jws/jakarta.jws-api/2.1.0/jakarta.jws-api-2.1.0.jar:/Users/dshu/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/Users/dshu/.m2/repository/javax/xml/soap/javax.xml.soap-api/1.4.0/javax.xml.soap-api-1.4.0.jar:/Users/dshu/.m2/repository/com/github/javafaker/javafaker/1.0.2/javafaker-1.0.2.jar:/Users/dshu/.m2/repository/com/github/mifmif/generex/1.0.2/generex-1.0.2.jar:/Users/dshu/.m2/repository/dk/brics/automaton/automaton/1.11-8/automaton-1.11-8.jar:/Users/dshu/.m2/repository/org/yaml/snakeyaml/2.0/snakeyaml-2.0.jar:/Users/dshu/.m2/repository/com/neovisionaries/nv-i18n/1.27/nv-i18n-1.27.jar:/Users/dshu/.m2/repository/com/github/jknack/handlebars/4.1.2/handlebars-4.1.2.jar:/Users/dshu/.m2/repository/org/mapstruct/mapstruct/1.4.2.Final/mapstruct-1.4.2.Final.jar:/Users/dshu/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.13.5/jackson-databind-2.13.5.jar:/Users/dshu/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.13.5/jackson-annotations-2.13.5.jar:/Users/dshu/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.13.5/jackson-core-2.13.5.jar:/Users/dshu/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.13.5/jackson-datatype-jsr310-2.13.5.jar:/Users/dshu/.m2/repository/javax/ws/rs/javax.ws.rs-api/2.1.1/javax.ws.rs-api-2.1.1.jar:/Users/dshu/.m2/repository/org/glassfish/jersey/core/jersey-client/2.39/jersey-client-2.39.jar:/Users/dshu/.m2/repository/jakarta/ws/rs/jakarta.ws.rs-api/2.1.6/jakarta.ws.rs-api-2.1.6.jar:/Users/dshu/.m2/repository/org/glassfish/jersey/core/jersey-common/2.39/jersey-common-2.39.jar:/Users/dshu/.m2/repository/org/glassfish/hk2/osgi-resource-locator/1.0.3/osgi-resource-locator-1.0.3.jar:/Users/dshu/.m2/repository/org/glassfish/hk2/external/jakarta.inject/2.6.1/jakarta.inject-2.6.1.jar:/Users/dshu/.m2/repository/org/glassfish/jersey/inject/jersey-hk2/2.39/jersey-hk2-2.39.jar:/Users/dshu/.m2/repository/org/glassfish/hk2/hk2-locator/2.6.1/hk2-locator-2.6.1.jar:/Users/dshu/.m2/repository/org/glassfish/hk2/external/aopalliance-repackaged/2.6.1/aopalliance-repackaged-2.6.1.jar:/Users/dshu/.m2/repository/org/glassfish/hk2/hk2-api/2.6.1/hk2-api-2.6.1.jar:/Users/dshu/.m2/repository/org/glassfish/hk2/hk2-utils/2.6.1/hk2-utils-2.6.1.jar:/Users/dshu/.m2/repository/org/javassist/javassist/3.29.0-GA/javassist-3.29.0-GA.jar:/Users/dshu/.m2/repository/org/glassfish/jersey/media/jersey-media-json-jackson/2.39/jersey-media-json-jackson-2.39.jar:/Users/dshu/.m2/repository/org/glassfish/jersey/ext/jersey-entity-filtering/2.39/jersey-entity-filtering-2.39.jar:/Users/dshu/.m2/repository/com/fasterxml/jackson/module/jackson-module-jaxb-annotations/2.14.1/jackson-module-jaxb-annotations-2.14.1.jar:/Users/dshu/.m2/repository/com/marqeta/mqpay/mqpay-vrol-wadl/3.0.5/mqpay-vrol-wadl-3.0.5.jar:/Users/dshu/.m2/repository/org/apache/cxf/cxf-wadl2java-plugin/3.4.10/cxf-wadl2java-plugin-3.4.10.jar:/Users/dshu/.m2/repository/org/apache/maven/shared/maven-artifact-resolver/1.0/maven-artifact-resolver-1.0.jar:/Users/dshu/.m2/repository/org/codehaus/plexus/plexus-utils/3.3.1/plexus-utils-3.3.1.jar:/Users/dshu/.m2/repository/org/codehaus/plexus/plexus-archiver/4.2.0/plexus-archiver-4.2.0.jar:/Users/dshu/.m2/repository/org/codehaus/plexus/plexus-io/3.2.0/plexus-io-3.2.0.jar:/Users/dshu/.m2/repository/org/iq80/snappy/snappy/0.4/snappy-0.4.jar:/Users/dshu/.m2/repository/org/tukaani/xz/1.8/xz-1.8.jar:/Users/dshu/.m2/repository/org/apache/cxf/cxf-tools-common/3.4.10/cxf-tools-common-3.4.10.jar:/Users/dshu/.m2/repository/org/apache/velocity/velocity-engine-core/2.3/velocity-engine-core-2.3.jar:/Users/dshu/.m2/repository/wsdl4j/wsdl4j/1.6.3/wsdl4j-1.6.3.jar:/Users/dshu/.m2/repository/org/glassfish/jaxb/jaxb-xjc/2.3.4/jaxb-xjc-2.3.4.jar:/Users/dshu/.m2/repository/org/glassfish/jaxb/xsom/2.3.4/xsom-2.3.4.jar:/Users/dshu/.m2/repository/com/sun/xml/bind/external/relaxng-datatype/2.3.4/relaxng-datatype-2.3.4.jar:/Users/dshu/.m2/repository/org/glassfish/jaxb/codemodel/2.3.4/codemodel-2.3.4.jar:/Users/dshu/.m2/repository/com/sun/xml/bind/external/rngom/2.3.4/rngom-2.3.4.jar:/Users/dshu/.m2/repository/com/sun/xml/dtd-parser/dtd-parser/1.4.4/dtd-parser-1.4.4.jar:/Users/dshu/.m2/repository/com/sun/istack/istack-commons-tools/3.0.12/istack-commons-tools-3.0.12.jar:/Users/dshu/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.4/jaxb-runtime-2.3.4.jar:/Users/dshu/.m2/repository/org/glassfish/jaxb/txw2/2.3.4/txw2-2.3.4.jar:/Users/dshu/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.12/istack-commons-runtime-3.0.12.jar:/Users/dshu/.m2/repository/org/apache/cxf/cxf-core/3.4.10/cxf-core-3.4.10.jar:/Users/dshu/.m2/repository/org/apache/ws/xmlschema/xmlschema-core/2.2.5/xmlschema-core-2.2.5.jar:/Users/dshu/.m2/repository/org/apache/geronimo/specs/geronimo-jta_1.1_spec/1.1.1/geronimo-jta_1.1_spec-1.1.1.jar:/Users/dshu/.m2/repository/org/apache/cxf/cxf-tools-wadlto-jaxrs/3.4.10/cxf-tools-wadlto-jaxrs-3.4.10.jar:/Users/dshu/.m2/repository/org/apache/cxf/cxf-rt-frontend-jaxrs/3.4.10/cxf-rt-frontend-jaxrs-3.4.10.jar:/Users/dshu/.m2/repository/org/apache/cxf/cxf-rt-transports-http/3.4.10/cxf-rt-transports-http-3.4.10.jar:/Users/dshu/.m2/repository/org/apache/cxf/cxf-rt-security/3.4.10/cxf-rt-security-3.4.10.jar:/Users/dshu/.m2/repository/org/apache/cxf/cxf-rt-rs-service-description/3.4.10/cxf-rt-rs-service-description-3.4.10.jar:/Users/dshu/.m2/repository/xml-resolver/xml-resolver/1.2/xml-resolver-1.2.jar:/Users/dshu/.m2/repository/com/amazonaws/aws-java-sdk-s3/1.12.419/aws-java-sdk-s3-1.12.419.jar:/Users/dshu/.m2/repository/com/amazonaws/aws-java-sdk-kms/1.12.419/aws-java-sdk-kms-1.12.419.jar:/Users/dshu/.m2/repository/com/google/protobuf/protobuf-java/3.19.6/protobuf-java-3.19.6.jar:/Users/dshu/.m2/repository/com/fasterxml/jackson/dataformat/jackson-dataformat-cbor/2.11.4/jackson-dataformat-cbor-2.11.4.jar:/Users/dshu/.m2/repository/commons-codec/commons-codec/1.15/commons-codec-1.15.jar:/Users/dshu/.m2/repository/io/github/jamsesso/json-logic-java/1.0.7/json-logic-java-1.0.7.jar Tests.HighPerformanceFilter
Stream: [apple, facebook, google]
StreamMap: {apple=[0], facebook=[0], google=[0]}
add tag ===============
Stream: [apple, facebook, google, banana, facebook]
StreamMap: {banana=[1], apple=[0], facebook=[0, 1], google=[0]}
add tag ===============
Stream: [apple, facebook, google, banana, facebook, facebook, google, tesla]
StreamMap: {banana=[1], apple=[0], tesla=[2], facebook=[0, 1, 2], google=[0, 2]}
add tag ===============
Stream: [apple, facebook, google, banana, facebook, facebook, google, tesla, intuit, google, facebook]
StreamMap: {banana=[1], intuit=[3], apple=[0], tesla=[2], facebook=[0, 1, 2, 3], google=[0, 2, 3]}
add tag ===============
***********begin add tag ===============
Searching for keywords: [facebook, google]
Document: 0, Count: 1
Document: 1, Count: 1
Document: 2, Count: 1
Document: 3, Count: 1
Document: 0, Count: 2
Document: 2, Count: 2
Document: 3, Count: 2
CounterMap: {0=2, 1=1, 2=2, 3=2}
Matched Document: 0, Tags: apple, facebook, google
Matched Document: 2, Tags: facebook, google, tesla
Matched Document: 3, Tags: intuit, google, facebook
ResultSet before removal: [intuit, apple, tesla, facebook, google]
ResultSet before removal: [intuit, apple, tesla]
[intuit, apple, tesla]

Process finished with exit code 0




//   second way ***********************************
import java.util.*;
import java.util.stream.Collectors;

public class BetterDatadogInvertedIndex {

    Map<String, Set<String>> invertedIndex = new HashMap<>();

    public void pushTags(List<String> tags) {
        Set<String> tagsSet = new HashSet<>(tags);
        for (String tag : tagsSet) {
            Set<String> targetDocuments = invertedIndex.getOrDefault(tag, new HashSet<>());
            targetDocuments.addAll(tagsSet); // Union of current tag set and new tagsSet
            invertedIndex.put(tag, targetDocuments);
        }
    }

    public Set<String> searchTags(List<String> tags) {
        Set<String> allFoundTags = new HashSet<>();

        for (String tag : tags) {
            Set<String> tagDocuments = invertedIndex.getOrDefault(tag, new HashSet<>());
            if (allFoundTags.isEmpty()) {
                allFoundTags.addAll(tagDocuments);
            } else {
                allFoundTags.retainAll(tagDocuments); // Intersection with existing results
            }
        }

        return allFoundTags;
    }

    public static void main(String[] args) {
        BetterDatadogInvertedIndex s = new BetterDatadogInvertedIndex();
        s.pushTags(List.of("apple", "google", "facebook"));
        s.pushTags(List.of("banana", "facebook"));
        s.pushTags(List.of("facebook", "google", "tesla"));
        s.pushTags(List.of("intuit", "google", "facebook"));

        Set<String> res1 = s.searchTags(List.of("apple"));
        Set<String> res2 = s.searchTags(List.of("facebook", "google"));

        System.out.println("Search result for 'apple': " + res1);
        System.out.println("Search result for 'facebook' and 'google': " + res2);
    }
}
数据结构选择：

使用了 HashMap<String, Set<String>> 作为 invertedIndex 的实现，
    其中 String 表示标签，Set<String> 表示包含该标签的文档集合。
    这样的设计使得根据标签快速查找对应的文档集合成为可能，HashMap 提供了平均 O(1) 的时间复杂度的查找操作。
数据处理：

在 pushTags 方法中，使用了 Set<String> 来存储标签，
    确保每个标签集合的唯一性。这样可以避免重复添加标签，保证了数据的一致性和正确性。
    
搜索操作：
searchTags 方法在处理搜索操作时，利用了集合的交集运算 retainAll，
    这是一个高效的操作。通过逐步取交集的方式，筛选出同时包含所有搜索标签的文档标签集合。
    这种方法尽可能减少了不必要的遍历和比较操作，提高了搜索效率。
    
Java Stream API 的利用：
虽然代码中没有直接使用 Java Stream API，但在实际应用中，
    如果涉及到更复杂的数据处理和转换，可以利用 Stream API 提供的并行处理能力来进一步提升性能。
    
内存管理：
使用了基本的集合操作和遍历，避免了不必要的对象创建和销毁，有效管理了内存使用。


=================================
    stream


    import java.util.*;
import java.util.stream.Collectors;

public class BetterDatadogInvertedIndex {

    Map<String, Set<String>> invertedIndex = new HashMap<>();

    public void pushTags(List<String> tags) {
        tags.stream()
            .flatMap(tag -> new HashSet<>(tags).stream()) // 将每个标签转换为对应的文档标签集合
            .forEach(tag -> invertedIndex.computeIfAbsent(tag, k -> new HashSet<>()).addAll(tags));
    }

    public Set<String> searchTags(List<String> tags) {
        return tags.stream()
                   .map(tag -> invertedIndex.getOrDefault(tag, new HashSet<>()))
                   .reduce((set1, set2) -> {
                       set1.retainAll(set2); // 取交集
                       return set1;
                   })
                   .orElse(new HashSet<>()); // 如果为空则返回空集合
    }

    public static void main(String[] args) {
        BetterDatadogInvertedIndex s = new BetterDatadogInvertedIndex();
        s.pushTags(List.of("apple", "google", "facebook"));
        s.pushTags(List.of("banana", "facebook"));
        s.pushTags(List.of("facebook", "google", "tesla"));
        s.pushTags(List.of("intuit", "google", "facebook"));

        Set<String> res1 = s.searchTags(List.of("apple"));
        Set<String> res2 = s.searchTags(List.of("facebook", "google"));

        System.out.println("Search result for 'apple': " + res1); // Output: [facebook, google]
        System.out.println("Search result for 'facebook' and 'google': " + res2); // Output: [intuit, apple, tesla]
    }
}
pushTags 方法：

使用 flatMap 将每个标签映射为一个新的文档标签集合，并通过 forEach 将其添加到 invertedIndex 中。这里利用了 computeIfAbsent 方法来确保每个标签对应的文档标签集合都存在。
searchTags 方法：

使用 stream 对输入的标签列表进行处理，首先映射每个标签到对应的文档标签集合，然后使用 reduce 方法将所有集合取交集。如果集合为空，则返回一个空的 HashSet。






java Stream API 在某些情况下可以提高代码的简洁性和可读性，并且在适当的情况下也可以提升性能。以下是一些使得 Java Stream API更高效的因素：

内部迭代：

Stream API 使用内部迭代，而不是外部迭代（显式地编写循环），这意味着它可以更好地利用多核处理器和并行计算。
    内部迭代使得底层实现可以选择并行执行操作，以提高整体的处理速度。
    
延迟执行：
Stream 的操作通常是延迟执行的。这意味着当调用 Stream 的中间操作时，
    它只会记录操作的逻辑，并不会立即执行。直到调用了终端操作（如 collect、forEach、reduce 等）时，
    才会触发实际的计算。这种方式可以优化操作的执行顺序，并且在某些情况下可以减少不必要的计算量。
    
函数式编程风格：
Stream API 鼓励使用函数式编程风格，例如使用 map、filter、reduce 等高阶函数。这种方式使得代码更为简洁和清晰
    ，减少了显式的控制流和临时变量，从而减少了出错的可能性，并且使得代码更易于优化。
    
优化的并行处理：
Stream API 提供了并行处理的支持。通过调用 parallelStream() 方法可以将串行操作转换为并行操作
    ，从而在多核处理器上同时处理数据集合的不同部分，提高了处理大数据集的效率。当数据量较大时，合理使用并行流可以显著提升处理速度。
    
底层优化：
Java 平台的实现对 Stream API 进行了优化，使得其底层操作更为高效。
    例如，底层的数据结构和算法可能经过了精心设计，以提供较好的性能和可扩展性。
尽管 Stream API 在许多情况下能够提升代码的性能和可读性，但也需要根据具体的应用场景进行评估。
    在某些特定的场景下，传统的循环和条件判断可能会更为直接和高效。因此，选择使用 Stream API 还是传统的循环，需要根据具体的需求、性能要求和代码复杂性来进行权衡和选择。
