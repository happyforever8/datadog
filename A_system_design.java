(1)System design: A system to detect if someone shares your photo on Instagram. 
    Assuming you have a Instagram post firehose, and ML model f‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌or facial detection.

(2)第二轮，华人大哥，人不太nice，系统设计flight折扣原题，准备了很久，整个过程基本一气呵成。
    大哥表示满意，但后面其中一个follow-up，关于为什么一个地方用DB存而不直接用queue。
    可能没答出他想听到的，最后HR feedback显示挂在了这一轮。

    不好意思，具体不太记得了，不过不是notification那里。
印象中是查和存price data那里的scale。
总之看以往面经特别多人都挂在这个题上了，真心希望以后有个过了人发一下自己的设计。

(3)design youtube，这个也聊感觉还好，最后面试官也不知道问什么了，就聊了聊他们组正在做的东西。但最后的feedback是我自己drive的不太够，让他们问了太多的问题

(4) System D: design a web crawler system, how to s‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌cale, how to reduce resource usage.

(5)system design轮是设计mint.com

(5.1) 要求设计一个系统可以通过用户的银行消费记录分析出用户在某类消费的数量和价值等信息。
    一开始搞错重点了想着要考设计payment这些，结果说到一半面试官建议就把我们当做一个3rd party就好具体payment部分不用关心，
    主要是分类整合用户信息……讨论metric设计， api/MQ， 
    db（我自己的设计是单独一个db存每一条action的log，但感觉这个应该是不需要的），
    如何监控这个系统正常运行（我当时有点慌只想到了heartbeat，zookeeper欢迎大家补充）
(5.2)
    设计一个financial tracking system，类似mint，之前好几个面经里都提到过。
功能比较简单，只需要用户可以连结银行账户，然后显示自己的开销，不需要分类，不需要制定预算，
    不需要notification，面试官只让我focus在基本功能。很基本的distributed system，
    用message queue和worker在连接银行账户和获取数据的时候做aysn，
    设计database schema，用SQL和cache，怎么scale，怎么shard，
    系统什么时候refresh数据‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌，user authentication怎么做，如何使用authentication token和refresh token。

(6) 第三轮SD,
设计一个类似m‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌int.com的系统，design an application which will collect and store purchases from credit 
    and debit cards and provide the user with insight into their spending habits. 这轮讲的时候我觉得讲的挺好，
    有具体讨论Database choice, schema, pw如何存etc.但最后给的反馈是这一轮fail了，
    因为没有讲太多detailed design。我也是不大懂要detail到怎样。

(7) 第二轮bq：
一个director面得，第一个proj大概聊了半个小时，具体除了很多细节技术和我的职责，
    就是不经意间的bq常见问题： 组内组外协调，队员犯错或不同意见，
    遇到的困难，self motivation。后面时间不太够了有大概花了10min聊了聊其他的项目和我喜欢和讨厌现公司的点。


(8) 题目也是以往面经题目。让你设计一个flight ticket deals email notification system, 
    要求 1.不能发重复的deal 2.如果有新users加入且subscribe 了他想知道的目的地的deal, 之前发过的no‍‍‍‌‍‍‍‍‍‍‌‌‍‍‍‌‌‍‍tification也需要发给他
我用的是message queue 做传送notifications, 用cache 做read heavy 的缓存。
    期间一直问如果十分钟内有10 billion deals 咋办 怎么存，要不你试试问你老板怎么去存。。。

    最后，你可以和Recruiter schedule time 讨论feedback. 他家这点很好啊， 
    每轮过没过都跟你讲。 结果就是 system design 挂了，因为我的设计too heavy read/too heavy read。 和这个一样哈哈


    4轮是system design，低价机票通知系统

    1. 系统设计：Flight Ticket Discount Notification system，要多确认需求，
    能简化不少设计。不能发重复的deal，只发邮件notificiation。不用考虑多个航程转机的细节，
    也不用考虑discount怎么来的，有一个推荐系统直接给定discount。大家一定注意时间，把重要模块讲清楚。

    系统设计那块有啥注意的点嘛？
我能想到的就是要用db记录发过的status，以避免重复，还有别的点嘛
    Notification本身要用到message queue，加cache避免high QPS to external flight API，
    要考虑flight价格变化速度等等。很多dive deep的点。


    题目也是以往面经题目。让你设计一个flight ticket deals email notification system, 
    要求 1.不能发重复的deal 2.如果有新users加入且subscribe 了他想知道的目的地的deal, 
    之前发过的no‍‍‍‌‍‍‍‍‍‍‌‌‍‍‍‌‌‍‍tification也需要发给他
我用的是message queue 做传送notifications, 用cache 做read heavy 的缓存。
    期间一直问如果十分钟内有10 billion deals 咋办 怎么存，要不你试试问你老板怎么去存。。。

    10 billion deals or 10 billion notifications?
Forget about 10 billion deals b/c it bombards users.
Assume 10 billion notifications in 10 minutes with 10 billion users.
First of all, it'll crash external systems, include email/SMS servers. Therefore, the system must 
    (a) batch processes (b) spread out notifications into days. 
    (Hours might not be feasible.) (c) multiple external email/SMS servers.
Well, maybe also forget it. If it works, it's more like a DDOS.
    It must be the center of hatred b/c it creates too much junk emails 
    and crash/saturate external systems. Most likely the system will be blocked permanently.

    系统设计是设计打折机票通知，蛮多non functional的要求，感觉能讲完就很不容易的，有被问到数据库的设计。

(9) ：设计一个图片匹配检测和通知系统，重点在如何设计通知部分






    
