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
我用的是message queue 做传送notifications, 用cache 做read heavy 的缓存。期间一直问如果十分钟内有10 billion deals 咋办 怎么存，要不你试试问你老板怎么去存。。。
