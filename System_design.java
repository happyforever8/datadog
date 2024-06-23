
https://www.1point3acres.com/bbs/thread-1065882-2-1.html

讲讲极高频的mint.com design，
以下仅代表我个人准备的内容，仅供参考, 反正我遇到的面试官在我说了以下一半内容不到的情况下（时间关系）就让我过了。
functional 和 nonfunctional requirements
functional requirement:
1, user bind account to financial accounts
2, going to the target financial accounts to get users' expenses information
3, categorize these expenses records to several buckets types and calculate total amounts of each for each month
4, users can read monthly report about their expenses distributions
5, if users spend more than the threshold defined, it will notify the users.
non-functional:
available 10millions users, read 1/day and 10expenses/day, 100millions write/day 1200 rps, 1kB/write, 100GB/day to storage  3T/month distrubuted DB and multiple servers
reliable
performance: fast computation and fast data receiving/writing low users waiting time for a report
scalable:
security
我准备的图见附件
system design面试60分钟，留给你讨论题目的时间只有45~50分钟。必须在10分钟之内讨论完functional， nonfunctional requirements和back envelope calculation。calculation不需要很精确，只要通过每天/每月增加的storage 和 RPS证明必须要用distributed system面试官就认为你上道了。然后你必须在另外20分钟内画完所有functional requirement的service graph和列出来Data Storage里面要存储的数据的大体内容和互相关系。这个题我列的data storage conent就是 users table，users finance accounts table, users expenses records from bank accounts, reports DB, (如果你在reports DB 和 users expenses raw data 之间加一个aggregated data by user,month,expenses bucket 作为中间的dynamic data storage用作动态生成功能更多的reports的作用那也很好)。 然后你必须有最后15到20分钟讲讲整个系统的bottle neck，怎么优化，或者对于data storage你怎么选型，最好能把理由归结到你之前列的non-functional requirements上。这里面可讲的部分包括：
1， scheduled expenses records crawling 和 data aggregation calculation是 batch job 还是 streaming job
2， 根据data storage内容的性质分析是用SQL还是nonSQL，并列出理由（我在附件中写的是 users， finance accounts data 用 SQL。 users expenses raw data用 BLOB like s3, reports DB 用 nonSQL mangoDB）
3， 讨论系统的性能瓶颈，这里可以是reports DB的读取，也可以是持续读取users expenses来计算更新reports，取决于你之前back envelope number的设定。而writing如果是batch job 可以讨论会在writing的时候，怎么缓解对reading的影响，streaming的话writing强度如何等等，然后就自然引入下面几点的对reading和writing的优化。
4，reports DB用partitions做horizontal sharding, 讨论用user id做partition key。然后谈谈怎么做partitions load balance to avoid skewed hot data, 然后提到consisitent hash之类的。然后对每一个partition 加多个read replicas， 然后谈谈这个 read replicas和 master node之间怎么data sync. 这个例子你可说expenses reports不需要很强的最新数据同步性，所以可以用async write更新read replicas来增加writing的performance, 如果你认为writing performance不重要因为writing not intensive，而users reading latest and consistent reports 更重要那就说要用synchronous way去更新read replicas
5， 添加cache缓存最近读过的reports
6， 讨论是否可以通过区分active users 和non-active users来采用不同的reports生成策略。因为active users的data reading intensity更大，那么是否可以设batch job主动生成reports以减少用户request等待时间？non-active users 几乎很少request reports，是否可以不主动生成reports减少系统资源开销？这样可以通过有request的时候临时生成reports来应对。那么由此带来的用户等待时间极其长怎么办，可以引入第7点
7， display reports service 接到request，可以先查看cache，如果cache没有则用async模式把request放入队列中由reports query/generation service读取队列。这样display reports service可以先返回客户端消息表示reports正在生成。reports query/generation service读取request之后先查询DB是否存在reports，如果没有则现场query aggregated dataset去生成reports存入DB，存入cache然后返回客户端reports或者用另一个队列把消息送会给display reports service（display reports service这时候需要保持与客户端的long term TCP链接？），或者用另一个notification service去通知用户reports已生成。这些各种options都可以讲讲去实现异步生成reports的功能。
8， 出于security（non-functional requirements），可以设定在用户添加或查看银行账户的时候，需要额外的认证。可以讲讲怎么设计一个给用户手机发送passcode然后让用户输入passcode来认证用户，然后在front end service生成一个加强版短期有效的cookie的方案。我随便写了一下可见于附件图中。
9， 讲一讲怎么monitor 你的DB partition nodes， 各个servers去异常检测，比如让这些node每隔10秒1分钟之类的发送 heartbeat， CPU usage， memory usage rate给monitoring server，如果需要scale怎么办，如果node not responding怎么办，然后就可以讨论一下DB nodes replicas re-election来生成新的master nodes， hot ready servers fail-over backup, 怎么scale DB partitions， replicas， 和 service servers之类的
10， 因为datadog就是做monitoring的，它很希望你说一说给这个系统设计一个logs based monitoring system架构。这个内容我有被面试官主动问道，所以我就在附件中附上了monitoring and alerting system架构参考图。
这10点只是我认知范围内可以谈的内容的举例。相信大家还会有其他很好的提高nonfunctional requirement的点可以详细谈。实际上因为时间关系你不可能谈这么多点。我的经验是如果你谈到了3个点，那就很有可能过关了，如果你谈了4到5点，那你就相当稳了。
最后说一点我的感受吧。
1， 多看看 youtube上的interviewing.io 这个频道 https://www.youtube.com/@interviewingio，FLAANG面试官真人和训练者实战，非常权威非常有帮助。
2， the easiest way to sound like a smart guy in system design interview: 画图的时候直接用这个模版 client -> load balancer -> rate limiter(prevent malfunctional users sending abnormally high volume of requests) -> front end servers(act like api gateway，authentication etc) -> each service components based on your functional requirements -> DB 无脑用，百利而无一弊。
3， 不要有题家思维认为system design会有一个在面试官脑海中的标准答案，而我要做的就是要揣测这个标准答案然后回答出来让面试官满意。实际上面试官没有标准答案，连requirement是什么都是高度开放的。面试官就是想让你自己折腾，想看你对系统设计的理解深度怎么样。你要做的就是自己假设一个requirement，然后自圆其说为了达到这个requirement该怎么设计系统，这个系统的瓶颈在哪儿，怎么改进。面试官的评判标准就是看你是否能利用系统设计的知识对你自己提的假设所产生的难点提出一个解决方案，并且这个解决方案逻辑自洽，符合行业广泛共识。
4， 不要问面试官太多requirements问题，自己假设自己说。不要花太多时间在requirements gathering和back envelope data calculation上。不要怕自己不和面试官沟通自说自话导致偏离了面试官的本意。因为面试时间非常非常有限，时间过得很快，你必须在10分钟之内进入画图设计阶段才有充足时间谈细节，谈瓶颈，谈优化，而这些才是面试官真正想听的。面试官真的没有太多预设的本意藏着不说故意等你问。你尽管假设然后往下说根据这个假设自己的想法，如果面试官觉得你偏离了其预设的内容TA会自动纠正你的。你要做的就是如果TA明确指出你的方向之后，不要和TA犟，接受这个设定，然后在此基础上继续假设继续说，直到下次TA纠偏你。面试官不会因为纠偏你而扣你分，因为考点根本不是猜requirements是什么，考点永远是你针对你提出的requirement有什么好的system design解决方案。当然面试官也有可能会challenge你提出的解决方案而打断你，那你要判断是否是你的设计不合理或者是你也可以坚持己见用自洽的逻辑和知识储备捍卫你的观点，这也可以是亮点，当然取决于面试官和你自己的硬实力了，就不多说了。
5， mock interview 真的很重要，对于我们这种英语不是母语的人来说更重要。看人吃豆腐牙快，自己上了才知道原来这么不顺手。强烈推荐去一些付费的找真实FLANNG面试官mock interview的平台练练。你即使不用和真人mock interview也要自己找个没人的地方假设处于面试中把整个流程走一遍，一切和真实面试一样去画图，对着图大声说，然后掐时间算好，就能发现原来自己有这么多问题之前都没意识到。然后结合网上mock interview的真人视频（比如上面第1点的）去听听面试官给训练者的反馈，反复自己模拟练习，会很有帮助。
