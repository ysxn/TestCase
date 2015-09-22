
package com.codezyw.common;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.Handler;

/**
 * <a href="http://www.trinea.cn/android/java-android-thread-pool/"
 * target="_blank">http://www.trinea.cn/android/java-android-thread-pool/</a>
 * <p>
 * <a href="http://dongxuan.iteye.com/blog/901689"
 * target="_blank">http://dongxuan.iteye.com/blog/901689</a>
 * <p>
 * <a href="http://dongxuan.iteye.com/blog/902571"
 * target="_blank">http://dongxuan.iteye.com/blog/902571</a>
 * <p>
 * <a href="http://www.cnblogs.com/dolphin0520/p/3932906.html"
 * target="_blank">http://www.cnblogs.com/dolphin0520/p/3932906.html</a>
 * <p>
 * in.srain.cube.image.impl.DefaultImageTaskExecutor
 * <p>
 * in.srain.cube.diskcache.lru.LruActionTracer
 * <p>
 * in.srain.cube.concurrent.SimpleExecutor
 * <p>
 * net.shopnc.android.handler.ImageLoader
 * <p>
 * com.zyw.videofileplayer.ThreadPool
 * <p>
 * org.qii.weiciyuan.support.lib.MyAsyncTask
 * <p>
 * <p>
 */
public class ThreadPoolHelper {

    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    /**
     * 前记：<br>
     * jdk官方文档（javadoc）是学习的最好，最权威的参考。<br>
     * 文章分上中下。上篇中主要介绍ThreadPoolExecutor接受任务相关的两方面入参的意义和区别，
     * 池大小参数corePoolSize和maximumPoolSize
     * ，BlockingQueue选型（SynchronousQueue，LinkedBlockingQueue
     * ，ArrayBlockingQueue）；<br>
     * 中篇中主要聊聊与keepAliveTime这个参数相关的话题；<br>
     * 下片中介绍一下一些比较少用的该类的API，及他的近亲：ScheduledThreadPoolExecutor。<br>
     * 如果理解错误，请直接指出。<br>
     * 查看JDK帮助文档，可以发现该类比较简单，继承自AbstractExecutorService，
     * 而AbstractExecutorService实现了ExecutorService接口。<br>
     * ThreadPoolExecutor的完整构造方法的签名是：<br>
     * 
     * <pre>
     * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, 
     *       long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
     *       ThreadFactory threadFactory, RejectedExecutionHandler handler)
     * </pre>
     * 
     * 先记着，后面慢慢解释。<br>
     * ===============================神奇分割线==================================<br>
     * 其实对于ThreadPoolExecutor的构造函数网上有N多的解释的，大多讲得都很好，不过我想先换个方式，从Executors这个类入手。<br>
     * 因为他的几个构造工厂构造方法名字取得令人很容易了解有什么特点。但是其实Executors类的底层实现便是ThreadPoolExecutor！<br>
     * ThreadPoolExecutor是Executors类的底层实现。<br>
     * 在JDK帮助文档中，有如此一段话：<br>
     * “强烈建议程序员使用较为方便的 Executors 工厂方法<br>
     * Executors.newCachedThreadPool()（无界线程池，可以进行自动线程回收）、<br>
     * Executors.newFixedThreadPool(int)（固定大小线程池）和<br>
     * Executors.newSingleThreadExecutor()（单个后台线程），<br>
     * 它们均为大多数使用场景预定义了设置。”<br>
     * 可以推断出ThreadPoolExecutor与Executors类必然关系密切。<br>
     * ===============================神奇分割线==================================<br>
     * OK，那就来看看源码吧，从newFixedThreadPool开始。<br>
     * ExecutorService newFixedThreadPool(int nThreads):固定大小线程池。<br>
     * 可以看到，corePoolSize和maximumPoolSize的大小是一样的（实际上，后面会介绍，
     * 如果使用无界queue的话maximumPoolSize参数是没有意义的），<br>
     * keepAliveTime和unit的设值表明什么？-就是该实现不想keep alive！<br>
     * 最后的BlockingQueue选择了LinkedBlockingQueue，该queue有一个特点，他是无界的。<br>
     * 
     * <pre>
     * public static ExecutorService newFixedThreadPool(int nThreads) {
     *     return new ThreadPoolExecutor(nThreads, nThreads,
     *             0L, TimeUnit.MILLISECONDS,
     *             new LinkedBlockingQueue&lt;Runnable&gt;());
     * }
     * </pre>
     * 
     * ExecutorService newSingleThreadExecutor()：单线程。<br>
     * 可以看到，与fixedThreadPool很像，<br>
     * 只不过fixedThreadPool中的传入参数corePoolSize和maximumPoolSize的大小直接退化为1<br>
     * 
     * <pre>
     * public static ExecutorService newSingleThreadExecutor() {
     *     return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1,
     *             0L, TimeUnit.MILLISECONDS,
     *             new LinkedBlockingQueue&lt;Runnable&gt;()));
     * }
     * </pre>
     * 
     * ExecutorService newCachedThreadPool()：无界线程池，可以进行自动线程回收。<br>
     * 这个实现就有意思了。首先是无界的线程池，所以我们可以发现maximumPoolSize为big big。<br>
     * 其次BlockingQueue的选择上使用SynchronousQueue。<br>
     * 可能对于该BlockingQueue有些陌生，简单说：该QUEUE中，每个插入操作必须等待另一个线程的对应移除操作。<br>
     * 比如，我先添加一个元素，接下来如果继续想尝试添加则会阻塞，直到另一个线程取走一个元素，反之亦然。<br>
     * （想到什么？就是缓冲区为1的生产者消费者模式^_^）<br>
     * 注意到介绍中的自动回收线程的特性吗，为什么呢？<br>
     * 先不说，但注意到该实现中corePoolSize和maximumPoolSize的大小不同。<br>
     * 
     * <pre>
     * public static ExecutorService newCachedThreadPool() {
     *     return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
     *             60L, TimeUnit.SECONDS,
     *             new SynchronousQueue&lt;Runnable&gt;());
     * }
     * </pre>
     * 
     * ===============================神奇分割线==================================<br>
     * 到此如果有很多疑问，那是必然了（除非你也很了解了）<br>
     * 先从BlockingQueue<Runnable> workQueue这个入参开始说起。<br>
     * 在JDK中，其实已经说得很清楚了，一共有三种类型的queue。以下为引用：（我会稍微修改一下，并用红色突出显示）<br>
     * 所有 BlockingQueue 都可用于传输和保持提交的任务。<br>
     * 可以使用此队列与池大小进行交互：<br>
     * 如果运行的线程少于 corePoolSize，则 Executor 始终首选添加新的线程，而不进行排队。<br>
     * （什么意思？如果当前运行的线程小于corePoolSize，则任务根本不会存放添加到queue中，而是直接抄家伙（thread）开始运行）<br>
     * 如果运行的线程等于或多于 corePoolSize，则 Executor 始终首选将请求加入队列，而不添加新的线程。<br>
     * 如果无法将请求加入队列，则创建新的线程，除非创建此线程超出 maximumPoolSize，在这种情况下，任务将被拒绝。<br>
     * 先不着急举例子，因为首先需要知道queue上的三种类型。<br>
     * 排队有三种通用策略：<br>
     * <p>
     * 1.直接提交。工作队列的默认选项是
     * SynchronousQueue，它将任务直接提交给线程而不保持它们。在此，如果不存在可用于立即运行任务的线程，则试图把任务加入队列将失败
     * ，因此会构造一个新的线程。此策略可以避免在处理可能具有内部依赖性的请求集时出现锁。直接提交通常要求无界 maximumPoolSizes
     * 以避免拒绝新提交的任务。当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。<br>
     * <p>
     * 2.无界队列。使用无界队列（例如，不具有预定义容量的 LinkedBlockingQueue）将导致在所有 corePoolSize
     * 线程都忙时新任务在队列中等待。这样，创建的线程就不会超过 corePoolSize。（因此，maximumPoolSize
     * 的值也就无效了。）当每个任务完全独立于其他任务，即任务执行互不影响时，适合于使用无界队列；例如，在 Web
     * 页服务器中。这种排队可用于处理瞬态突发请求，当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。<br>
     * <p>
     * 3.有界队列。当使用有限的 maximumPoolSizes 时，有界队列（如
     * ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制
     * 。队列大小和最大池大小可能需要相互折衷：使用大型队列和小型池可以最大限度地降低 CPU
     * 使用率、操作系统资源和上下文切换开销，但是可能导致人工降低吞吐量。如果任务频繁阻塞（例如，如果它们是 I/O
     * 边界），则系统可能为超过您许可的更多线程安排时间。使用小型队列通常要求较大的池大小，CPU
     * 使用率较高，但是可能遇到不可接受的调度开销，这样也会降低吞吐量。 <br>
     * ===============================神奇分割线==================================<br>
     * 到这里，该了解的理论已经够多了，可以调节的就是corePoolSize和maximumPoolSizes
     * 这对参数还有就是BlockingQueue的选择。<br>
     * <p>
     * 例子一：使用直接提交策略，也即SynchronousQueue。<br>
     * 首先SynchronousQueue是无界的，也就是说他存数任务的能力是没有限制的，但是由于该Queue本身的特性，
     * 在某次添加元素后必须等待其他线程取走后才能继续添加。在这里不是核心线程便是新创建的线程，但是我们试想一样下，下面的场景。<br>
     * 我们使用一下参数构造ThreadPoolExecutor：<br>
     * 
     * <pre>
     * new ThreadPoolExecutor(  
     *             2, 3, 30, TimeUnit.SECONDS,   
     *             new <span style="white-space: normal;">SynchronousQueue</span><Runnable>(),   
     *             new RecorderThreadFactory("CookieRecorderPool"),   
     *             new ThreadPoolExecutor.CallerRunsPolicy());
     * </pre>
     * 
     * 当核心线程已经有2个正在运行.<br>
     * 此时继续来了一个任务（A），根据前面介绍的“如果运行的线程等于或多于 corePoolSize，则 Executor
     * 始终首选将请求加入队列，而不添加新的线程。”,所以A被添加到queue中。<br>
     * 又来了一个任务（B），且核心2个线程还没有忙完，OK，接下来首先尝试1中描述，但是由于使用的SynchronousQueue，所以一定无法加入进去
     * 。<br>
     * 此时便满足了上面提到的“如果无法将请求加入队列，则创建新的线程，除非创建此线程超出maximumPoolSize，在这种情况下，任务将被拒绝。”，
     * 所以必然会新建一个线程来运行这个任务。<br>
     * 暂时还可以，但是如果这三个任务都还没完成，连续来了两个任务，第一个添加入queue中（这里应该是无法加入队列），后一个呢？queue中无法插入，
     * 而线程数达到了maximumPoolSize，所以只好执行异常策略了。<br>
     * 所以在使用SynchronousQueue通常要求maximumPoolSize是无界的，这样就可以避免上述情况发生（
     * 如果希望限制就直接使用有界队列
     * ）。对于使用SynchronousQueue的作用jdk中写的很清楚：此策略可以避免在处理可能具有内部依赖性的请求集时出现锁。<br>
     * 什么意思？如果你的任务A1，A2有内部关联，A1需要先运行，那么先提交A1，再提交A2，当使用SynchronousQueue我们可以保证，
     * A1必定先被执行，在A1没有被执行前，A2不可能添加入queue中<br>
     * <p>
     * 例子二：使用无界队列策略，即LinkedBlockingQueue<br>
     * 这个就拿newFixedThreadPool来说，根据前文提到的规则：<br>
     * 如果运行的线程少于 corePoolSize，则 Executor 始终首选添加新的线程，而不进行排队。<br>
     * 那么当任务继续增加，会发生什么呢？<br>
     * 如果运行的线程等于或多于 corePoolSize，则 Executor 始终首选将请求加入队列，而不添加新的线程。<br>
     * OK，此时任务变加入队列之中了，那什么时候才会添加新线程呢？<br>
     * 如果无法将请求加入队列，则创建新的线程，除非创建此线程超出 maximumPoolSize，在这种情况下，任务将被拒绝。<br>
     * 这里就很有意思了，可能会出现无法加入队列吗？不像SynchronousQueue那样有其自身的特点，对于无界队列来说，总是可以加入的（资源耗尽，
     * 当然另当别论 ）。换句说，永远也不会触发产生新的线程！corePoolSize大小的线程数会一直运行，忙完当前的，就从队列中拿任务开始运行。
     * 所以要防止任务疯长 ，比如任务运行的时间比较长，而添加任务的速度远远超过处理任务的时间，而且还不断增加，如果任务内存大一些，不一会儿就爆了，呵呵。
     * <br>
     * 可以仔细想想哈。<br>
     * <p>
     * 例子三：有界队列，使用ArrayBlockingQueue。<br>
     * 这个是最为复杂的使用，所以JDK不推荐使用也有些道理。与上面的相比，最大的特点便是可以防止资源耗尽的情况发生。<br>
     * 举例来说，请看如下构造方法：<br>
     * 
     * <pre>
     * new ThreadPoolExecutor(
     *         2, 4, 30, TimeUnit.SECONDS,
     *         new ArrayBlockingQueue&lt;Runnable&gt;(2),
     *         new RecorderThreadFactory(&quot;CookieRecorderPool&quot;),
     *         new ThreadPoolExecutor.CallerRunsPolicy());
     * </pre>
     * 
     * 假设，所有的任务都永远无法执行完。<br>
     * 对于首先来的A,B来说直接运行，接下来，如果来了C,D，他们会被放到queu中，如果接下来再来E,F，则增加线程运行E，F。但是如果再来任务，
     * 队列无法再接受了，线程数也到达最大的限制了，所以就会使用拒绝策略来处理。<br>
     * 总结：<br>
     * ThreadPoolExecutor的使用还是很有技巧的。<br>
     * 使用无界queue可能会耗尽系统资源。<br>
     * 使用有界queue可能不能很好的满足性能，需要调节线程数和queue大小<br>
     * 线程数自然也有开销，所以需要根据不同应用进行调节。<br>
     * 通常来说对于静态任务可以归为：<br>
     * 数量大，但是执行时间很短<br>
     * 数量小，但是执行时间较长<br>
     * 数量又大执行时间又长<br>
     * 除了以上特点外，任务间还有些内在关系<br>
     * ===============================神奇分割线==================================<br>
     * 通过上篇文章，我们可以总结出：ThreadPoolExecutor中额定的“工人”数量由corePoolSize决定，当任务数量超过额定工人数量时
     * ，将任务缓存在BlockingQueue之中，当发现如果连queue中也放不下时（可能是因为使用有界queue，
     * 也可能是使用SynchronousQueue），<br>
     * ThreadPoolExecutor会请求“老板”再派几个(maximumPoolSize-corePoolSize)“工人”过来。<br>
     * 接下来发生的事情有两种情况：<br>
     * 1.任务不再过来了 - keepAliveTime,
     * 那几个老板额外派来(maximumPoolSize-corePoolSize)“工人”干完活后停留在线程池里面的时间<br>
     * 2.任务仍然继续过来 - RejectedExecutionHandler, 拒绝策略来处理<br>
     * ===============================神奇分割线==================================<br>
     * keepAliveTime<br>
     * jdk中的解释是：当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。<br>
     * 有点拗口，其实这个不难理解，在使用了“池”的应用中，大多都有类似的参数需要配置。比如数据库连接池，DBCP中的maxIdle，minIdle参数。
     * <br>
     * 什么意思？接着上面的解释，后来向老板派来的工人始终是“借来的”，俗话说“有借就有还”，但这里的问题就是什么时候还了，
     * 如果借来的工人刚完成一个任务就还回去，后来发现任务还有，那岂不是又要去借？这一来一往，老板肯定头也大死了。<br>
     * 合理的策略：既然借了，那就多借一会儿。直到“某一段”时间后，发现再也用不到这些工人时，便可以还回去了。
     * 这里的某一段时间便是keepAliveTime的含义，TimeUnit为keepAliveTime值的度量。<br>
     * RejectedExecutionHandler<br>
     * 另一种情况便是，即使向老板借了工人，但是任务还是继续过来，还是忙不过来，这时整个队伍只好拒绝接受了。<br>
     * RejectedExecutionHandler接口提供了对于拒绝任务的处理的自定方法的机会。
     * 在ThreadPoolExecutor中已经默认包含了4中策略，因为源码非常简单，这里直接贴出来:<br>
     * new ThreadPoolExecutor.CallerRunsPolicy()<br>
     * new ThreadPoolExecutor.AbortPolicy()<br>
     * new ThreadPoolExecutor.DiscardPolicy()<br>
     * new ThreadPoolExecutor.DiscardOldestPolicy()<br>
     * <p>
     * 1.CallerRunsPolicy：线程调用运行该任务的 execute 本身。此策略提供简单的反馈控制机制，能够减缓新任务的提交速度。<br>
     * 
     * <pre>
     * public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
     *     if (!e.isShutdown()) {
     *         r.run();
     *     }
     * }
     * </pre>
     * 
     * 这个策略显然不想放弃执行任务。但是由于池中已经没有任何资源了，那么就直接使用调用该execute的线程本身来执行。<br>
     * <p>
     * 2.AbortPolicy：处理程序遭到拒绝将抛出运行时 RejectedExecutionException<br>
     * 
     * <pre>
     * public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
     *     throw new RejectedExecutionException();
     * }
     * </pre>
     * 
     * 这种策略直接抛出异常，丢弃任务。<br>
     * <p>
     * 3.DiscardPolicy：不能执行的任务将被删除<br>
     * 
     * <pre>
     * public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
     * }
     * </pre>
     * 
     * 这种策略和AbortPolicy几乎一样，也是丢弃任务，只不过他不抛出异常。<br>
     * <p>
     * 4.DiscardOldestPolicy：如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）
     * <br>
     * 
     * <pre>
     * public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
     *     if (!e.isShutdown()) {
     *         e.getQueue().poll();
     *         e.execute(r);
     *     }
     * }
     * </pre>
     * 
     * 该策略就稍微复杂一些，在pool没有关闭的前提下首先丢掉缓存在队列中的最早的任务，然后重新尝试运行该任务。这个策略需要适当小心。<br>
     * 设想:如果其他线程都还在运行，那么新来任务踢掉旧任务，缓存在queue中，再来一个任务又会踢掉queue中最老任务。<br>
     * 总结：<br>
     * keepAliveTime和maximumPoolSize及BlockingQueue的类型均有关系。如果BlockingQueue是无界的，
     * 那么永远不会触发maximumPoolSize，自然keepAliveTime也就没有了意义。<br>
     * 反之，如果核心数较小，有界BlockingQueue数值又较小，同时keepAliveTime又设的很小，如果任务频繁，
     * 那么系统就会频繁的申请回收线程。<br>
     */
    public static void explain() {

    }

    /**
     * java中有封装好的类，可以直接调用：<br>
     * Stack:<br>
     * 1-->public Stack()创建一个空堆栈<br>
     * 2-->public boolean empty()测试堆栈是否为空;<br>
     * 3-->public E pop()移除堆栈顶部的对象，并作为此函数的值返回该对象。 <br>
     * 4-->public E push(E item)把项压入堆栈顶部<br>
     * 5-->public E peek()查看堆栈顶部的对象，但不从堆栈中移除它。 <br>
     * 6-->public boolean empty()测试堆栈是否为空<br>
     */
    public static void stack() {

    }

    /**
     * 
     */
    public static void collection() {

    }

    /**
     * Queue接口与List、Set同一级别，都是继承了Collection接口。<br>
     * 
     * <pre>
     * 在前面我们接触的队列都是非阻塞队列，比如PriorityQueue、LinkedList（LinkedList是双向链表，它实现了Dequeue接口）。
     * 
     * 　　使用非阻塞队列的时候有一个很大问题就是：它不会对当前线程产生阻塞，那么在面对类似消费者-生产者的模型时，就必须额外地实现同步策略以及线程间唤醒策略，这个实现起来就非常麻烦。但是有了阻塞队列就不一样了，它会对当前线程产生阻塞，比如一个线程从一个空的阻塞队列中取元素，此时线程会被阻塞直到阻塞队列中有了元素。当队列中有元素后，被阻塞的线程会自动被唤醒（不需要我们编写代码去唤醒）。这样提供了极大的方便性。
     * 
     *  　　http://www.cnblogs.com/dolphin0520/p/3932906.html
     * 
     * 一.几种主要的阻塞队列
     * 
     * 　　自从Java 1.5之后，在java.util.concurrent包下提供了若干个阻塞队列，主要有以下几个：
     * 
     * 　　ArrayBlockingQueue：基于数组实现的一个阻塞队列，在创建ArrayBlockingQueue对象时必须制定容量大小。并且可以指定公平性与非公平性，默认情况下为非公平的，即不保证等待时间最长的队列最优先能够访问队列。
     * 
     * 　　LinkedBlockingQueue：基于链表实现的一个阻塞队列，在创建LinkedBlockingQueue对象时如果不指定容量大小，则默认大小为Integer.MAX_VALUE。
     * 
     * 　　PriorityBlockingQueue：以上2种队列都是先进先出队列，而PriorityBlockingQueue却不是，它会按照元素的优先级对元素进行排序，按照优先级顺序出队，每次出队的元素都是优先级最高的元素。注意，此阻塞队列为无界阻塞队列，即容量没有上限（通过源码就可以知道，它没有容器满的信号标志），前面2种都是有界队列。
     * 
     * 　　DelayQueue：基于PriorityQueue，一种延时阻塞队列，DelayQueue中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素。DelayQueue也是一个无界队列，因此往队列中插入数据的操作（生产者）永远不会被阻塞，而只有获取数据的操作（消费者）才会被阻塞。
     * 
     * 二.阻塞队列中的方法 VS 非阻塞队列中的方法
     * 
     * 1.非阻塞队列中的几个主要方法：
     * 
     * 　　add(E e):将元素e插入到队列末尾，如果插入成功，则返回true；如果插入失败（即队列已满），则会抛出异常；
     * 
     * 　　remove()：移除队首元素，若移除成功，则返回true；如果移除失败（队列为空），则会抛出异常；
     * 
     * 　　offer(E e)：将元素e插入到队列末尾，如果插入成功，则返回true；如果插入失败（即队列已满），则返回false；
     * 
     * 　　poll()：移除并获取队首元素，若成功，则返回队首元素；否则返回null；
     * 
     * 　　peek()：获取队首元素，若成功，则返回队首元素；否则返回null
     * 
     *  
     * 
     * 　　对于非阻塞队列，一般情况下建议使用offer、poll和peek三个方法，不建议使用add和remove方法。因为使用offer、poll和peek三个方法可以通过返回值判断操作成功与否，而使用add和remove方法却不能达到这样的效果。注意，非阻塞队列中的方法都没有进行同步措施。
     * 
     * 2.阻塞队列中的几个主要方法：
     * 
     * 　　阻塞队列包括了非阻塞队列中的大部分方法，上面列举的5个方法在阻塞队列中都存在，但是要注意这5个方法在阻塞队列中都进行了同步措施。除此之外，阻塞队列提供了另外4个非常有用的方法：
     * 
     * 　　put(E e)
     * 
     * 　　take()
     * 
     * 　　offer(E e,long timeout, TimeUnit unit)
     * 
     * 　　poll(long timeout, TimeUnit unit)
     * 
     * 　　
     * 
     * 　　put方法用来向队尾存入元素，如果队列满，则等待；
     * 
     * 　　take方法用来从队首取元素，如果队列为空，则等待；
     * 
     * 　　offer方法用来向队尾存入元素，如果队列满，则等待一定的时间，当时间期限达到时，如果还没有插入成功，则返回false；否则返回true；
     * 
     * 　　poll方法用来从队首取元素，如果队列空，则等待一定的时间，当时间期限达到时，如果取到，则返回null；否则返回取得的元素；
     * </pre>
     * 
     * remove、element、offer 、poll、peek 其实是属于Queue接口。 <br>
     * 阻塞队列的操作可以根据它们的响应方式分为以下三类：<br>
     * <p>
     * 1.add、remove和element操作在你试图为一个已满的队列增加元素或从空队列取得元素时抛出异常。<br>
     * <p>
     * 2.当然，在多线程程序中，队列在任何时间都可能变成满的或空的，所以你可能想使用offer、poll、peek方法。<br>
     * 这些方法在无法完成任务时只是给出一个出错提示而不会抛出异常。 <br>
     * 注意：poll和peek方法出错则返回null。因此，向队列中插入null值是不合法的。 <br>
     * 还有带超时的offer和poll方法变种，例如，下面的调用：<br>
     * boolean success = q.offer(x,100,TimeUnit.MILLISECONDS);<br>
     * 尝试在100毫秒内向队列尾部插入一个元素。如果成功，立即返回true；否则，当到达超时时，返回false。同样地，调用：<br>
     * Object head = q.poll(100, TimeUnit.MILLISECONDS);<br>
     * 如果在100毫秒内成功地移除了队列头元素，则立即返回头元素；否则在到达超时时，返回null。<br>
     * <p>
     * 3.最后，我们有阻塞操作put和take。put方法在队列满时阻塞，take方法在队列空时阻塞。<br>
     * <p>
     * java.ulil.concurrent包提供了阻塞队列的4个变种。<br>
     * <p>
     * 1.默认情况下，LinkedBlockingQueue的容量是没有上限的（说的不准确
     * ，在不指定时容量为Integer.MAX_VALUE，不要然的话在put时怎么会受阻呢），<br>
     * 但是也可以选择指定其最大容量，它是基于链表的队列，此队列按 FIFO（先进先出）排序元素。 <br>
     * <p>
     * 2.ArrayBlockingQueue在构造时需要指定容量，并可以选择是否需要公平性，<br>
     * 如果公平参数被设置true，等待时间最长的线程会优先得到处理<br>
     * （其实就是通过将ReentrantLock设置为true来达到这种公平性的：即等待时间最长的线程会先操作）。<br>
     * 通常，公平性会使你在性能上付出代价，只有在的确非常需要的时候再使用它。<br>
     * 它是基于数组的阻塞循环队列，此队列按 FIFO（先进先出）原则对元素进行排序。 <br>
     * <p>
     * 3.PriorityBlockingQueue是一个带优先级的队列，而不是先进先出队列。<br>
     * 元素按优先级顺序被移除，该队列也没有上限<br>
     * （看了一下源码，PriorityBlockingQueue是对PriorityQueue的再次包装，是基于堆数据结构的，<br>
     * 而PriorityQueue是没有容量限制的，与ArrayList一样，所以在优先阻塞队列上put时是不会受阻的。<br>
     * 虽然此队列逻辑上是无界的，但是由于资源被耗尽，所以试图执行添加操作可能会导致OutOfMemoryError），<br>
     * 但是如果队列为空，那么取元素的操作take就会阻塞，所以它的取元素操作take是受阻的。<br>
     * 另外，往入该队列中的元素要具有比较能力。 <br>
     * <p>
     * 4.最后，DelayQueue（基于PriorityQueue来实现的）是一个存放Delayed元素的无界阻塞队列，<br>
     * 只有在延迟期满时才能从中提取元素。该队列的头部是延迟期满后保存时间最长的 Delayed元素。<br>
     * 如果延迟都还没有期满，则队列没有头部，并且poll将返回null。<br>
     * 当一个元素的 getDelay(TimeUnit.NANOSECONDS)方法返回一个小于或等于零的值时，则出现期满，poll就以移除这个元素了。
     * <br>
     * 此队列不允许使用 null 元素。 下面是延迟接口： <br>
     * 
     * <pre>
     * public interface Delayed extends Comparable&lt;Delayed&gt; {
     *     long getDelay(TimeUnit unit);
     * }
     * </pre>
     * 
     * 放入DelayQueue的元素还将要实现compareTo方法，DelayQueue使用这个来为元素排序。<br>
     * <p>
     * A collection designed for holding elements prior to processing. Besides
     * basic {@link java.util.Collection Collection} operations, queues provide
     * additional insertion, extraction, and inspection operations. Each of
     * these methods exists in two forms: one throws an exception if the
     * operation fails, the other returns a special value (either <tt>null</tt>
     * or <tt>false</tt>, depending on the operation). The latter form of the
     * insert operation is designed specifically for use with
     * capacity-restricted <tt>Queue</tt> implementations; in most
     * implementations, insert operations cannot fail.
     * <p>
     * <table BORDER CELLPADDING=3 CELLSPACING=1>
     * <tr>
     * <td></td>
     * <td ALIGN=CENTER><em>Throws exception</em></td>
     * <td ALIGN=CENTER><em>Returns special value</em></td>
     * </tr>
     * <tr>
     * <td><b>Insert</b></td>
     * <td>{@link #add add(e)}</td>
     * <td>{@link #offer offer(e)}</td>
     * </tr>
     * <tr>
     * <td><b>Remove</b></td>
     * <td>{@link #remove remove()}</td>
     * <td>{@link #poll poll()}</td>
     * </tr>
     * <tr>
     * <td><b>Examine</b></td>
     * <td>{@link #element element()}</td>
     * <td>{@link #peek peek()}</td>
     * </tr>
     * </table>
     * <p>
     * Queues typically, but do not necessarily, order elements in a FIFO
     * (first-in-first-out) manner. Among the exceptions are priority queues,
     * which order elements according to a supplied comparator, or the elements'
     * natural ordering, and LIFO queues (or stacks) which order the elements
     * LIFO (last-in-first-out). Whatever the ordering used, the <em>head</em>
     * of the queue is that element which would be removed by a call to
     * {@link #remove() } or {@link #poll()}. In a FIFO queue, all new elements
     * are inserted at the <em> tail</em> of the queue. Other kinds of queues
     * may use different placement rules. Every <tt>Queue</tt> implementation
     * must specify its ordering properties.
     * <p>
     * The {@link #offer offer} method inserts an element if possible, otherwise
     * returning <tt>false</tt>. This differs from the
     * {@link java.util.Collection#add Collection.add} method, which can fail to
     * add an element only by throwing an unchecked exception. The
     * <tt>offer</tt> method is designed for use when failure is a normal,
     * rather than exceptional occurrence, for example, in fixed-capacity (or
     * &quot;bounded&quot;) queues.
     * <p>
     * The {@link #remove()} and {@link #poll()} methods remove and return the
     * head of the queue. Exactly which element is removed from the queue is a
     * function of the queue's ordering policy, which differs from
     * implementation to implementation. The <tt>remove()</tt> and
     * <tt>poll()</tt> methods differ only in their behavior when the queue is
     * empty: the <tt>remove()</tt> method throws an exception, while the
     * <tt>poll()</tt> method returns <tt>null</tt>.
     * <p>
     * The {@link #element()} and {@link #peek()} methods return, but do not
     * remove, the head of the queue.
     * <p>
     * The <tt>Queue</tt> interface does not define the <i>blocking queue
     * methods</i>, which are common in concurrent programming. These methods,
     * which wait for elements to appear or for space to become available, are
     * defined in the {@link java.util.concurrent.BlockingQueue} interface,
     * which extends this interface.
     * <p>
     * <tt>Queue</tt> implementations generally do not allow insertion of
     * <tt>null</tt> elements, although some implementations, such as
     * {@link LinkedList}, do not prohibit insertion of <tt>null</tt>. Even in
     * the implementations that permit it, <tt>null</tt> should not be inserted
     * into a <tt>Queue</tt>, as <tt>null</tt> is also used as a special return
     * value by the <tt>poll</tt> method to indicate that the queue contains no
     * elements.
     * <p>
     * <tt>Queue</tt> implementations generally do not define element-based
     * versions of methods <tt>equals</tt> and <tt>hashCode</tt> but instead
     * inherit the identity based versions from class <tt>Object</tt>, because
     * element-based equality is not always well-defined for queues with the
     * same elements but different ordering properties.
     * 
     * @see java.util.Collection
     * @see LinkedList
     * @see PriorityQueue
     * @see java.util.concurrent.LinkedBlockingQueue
     * @see java.util.concurrent.BlockingQueue
     * @see java.util.concurrent.ArrayBlockingQueue
     * @see java.util.concurrent.LinkedBlockingQueue
     * @see java.util.concurrent.PriorityBlockingQueue
     * @since 1.5
     * @author Doug Lea
     * @param <E> the type of elements held in this collection
     */
    @SuppressWarnings("unused")
    public static void queue() {
        LinkedList<String> linkedList = new LinkedList<String>();
    }

    /**
     * <a href="http://www.cnblogs.com/dolphin0520/p/3933551.html">Java
     * ConcurrentModificationException异常原因和解决方法</a>
     * <p>
     * 
     * <pre>
     * <a href="http://www.cnblogs.com/dolphin0520/p/3933404.html">同步容器</a>
     * 一.为什么会出现同步容器？
     * 
     * 　　在Java的集合容器框架中，主要有四大类别：List、Set、Queue、Map。
     * 
     * 　　List、Set、Queue接口分别继承了Collection接口，Map本身是一个接口。
     * 
     * 　　注意Collection和Map是一个顶层接口，而List、Set、Queue则继承了Collection接口，分别代表数组、集合和队列这三大类容器。
     * 
     * 　　像ArrayList、LinkedList都是实现了List接口，HashSet实现了Set接口，而Deque（双向队列，允许在队首、队尾进行入队和出队操作）继承了Queue接口，PriorityQueue实现了Queue接口。另外LinkedList（实际上是双向链表）实现了了Deque接口。
     * 
     * 　　像ArrayList、LinkedList、HashMap这些容器都是非线程安全的。
     * 
     * 　　如果有多个线程并发地访问这些容器时，就会出现问题。
     * 
     * 　　因此，在编写程序时，必须要求程序员手动地在任何访问到这些容器的地方进行同步处理，这样导致在使用这些容器的时候非常地不方便。
     * 
     * 　　所以，Java提供了同步容器供用户使用。
     * 
     * 二.Java中的同步容器类
     * 
     * 　　在Java中，同步容器主要包括2类：
     * 
     * 　　1）Vector、Stack、HashTable
     * 
     * 　　2）Collections类中提供的静态工厂方法创建的类
     * 
     * 　　Vector实现了List接口，Vector实际上就是一个数组，和ArrayList类似，但是Vector中的方法都是synchronized方法，即进行了同步措施。
     * 
     * 　　Stack也是一个同步容器，它的方法也用synchronized进行了同步，它实际上是继承于Vector类。
     * 
     * 　　HashTable实现了Map接口，它和HashMap很相似，但是HashTable进行了同步处理，而HashMap没有。
     * 
     * 　　Collections类是一个工具提供类，注意，它和Collection不同，Collection是一个顶层的接口。在Collections类中提供了大量的方法，比如对集合或者容器进行排序、查找等操作。最重要的是，在它里面提供了几个静态工厂方法来创建同步容器类，
     * </pre>
     * <p>
     * 
     * <pre>
     * <a href="http://www.cnblogs.com/dolphin0520/p/3938914.html">CopyOnWrite</a>
     * Copy-On-Write简称COW，是一种用于程序设计中的优化策略。其基本思路是，从一开始大家都在共享同一个内容，当某个人想要修改这个内容的时候，才会真正把内容Copy出去形成一个新的内容然后再改，这是一种延时懒惰策略。从JDK1.5开始Java并发包里提供了两个使用CopyOnWrite机制实现的并发容器,它们是CopyOnWriteArrayList和CopyOnWriteArraySet。CopyOnWrite容器非常有用，可以在非常多的并发场景中使用到。
     * 
     * 什么是CopyOnWrite容器
     * 
     * 　　CopyOnWrite容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
     * </pre>
     * <p>
     * 
     * <pre>
     * <a href="http://www.cnblogs.com/dolphin0520/p/3932905.html">并发容器</a>
     * Collections.synchronizedXxx()同步容器等相比，util.concurrent中引入的并发容器主要解决了两个问题： 
     * 1）根据具体场景进行设计，尽量避免synchronized，提供并发性。 
     * 2）定义了一些并发安全的复合操作，并且保证并发环境下的迭代操作不会出错。
     * 
     * 　　util.concurrent中容器在迭代时，可以不封装在synchronized中，可以保证不抛异常，但是未必每次看到的都是"最新的、当前的"数据。
     * 
     * 　　下面是对并发容器的简单介绍：
     * 
     * 　　ConcurrentHashMap代替同步的Map（Collections.synchronized（new HashMap()）），众所周知，HashMap是根据散列值分段存储的，同步Map在同步的时候锁住了所有的段，而ConcurrentHashMap加锁的时候根据散列值锁住了散列值锁对应的那段，因此提高了并发性能。ConcurrentHashMap也增加了对常用复合操作的支持，比如"若没有则添加"：putIfAbsent()，替换：replace()。这2个操作都是原子操作。
     * 
     * 　　CopyOnWriteArrayList和CopyOnWriteArraySet分别代替List和Set，主要是在遍历操作为主的情况下来代替同步的List和同步的Set，这也就是上面所述的思路：迭代过程要保证不出错，除了加锁，另外一种方法就是"克隆"容器对象。
     * 
     * 　　ConcurrentLinkedQuerue是一个先进先出的队列。它是非阻塞队列。
     * 
     *  　  ConcurrentSkipListMap可以在高效并发中替代SoredMap（例如用Collections.synchronzedMap包装的TreeMap）。
     * 
     * 　　ConcurrentSkipListSet可以在高效并发中替代SoredSet（例如用Collections.synchronzedSet包装的TreeMap）。
     * 
     * 　　
     * 
     * 　　本篇文章着重讲解2个并发容器：ConcurrentHashMap和CopyOnWriteArrayList其中的ConcurrentHashMap，CopyOnWriteArrayList在下一篇文章中讲述。
     * 
     * 　　<a href="http://www.iteye.com/topic/1103980">原文链接</a>
     * 
     * 　　大家都知道HashMap是非线程安全的，Hashtable是线程安全的，但是由于Hashtable是采用synchronized进行同步，相当于所有线程进行读写时都去竞争一把锁，导致效率非常低下。
     * 
     * 　　ConcurrentHashMap可以做到读取数据不加锁，并且其内部的结构可以让其在进行写操作的时候能够将锁的粒度保持地尽量地小，不用对整个ConcurrentHashMap加锁。
     * </pre>
     */
    public static void concurrent() {

    }

    /**
     * 2、Java 线程池<br>
     * Java通过Executors提供四种线程池，分别为：<br>
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。<br>
     * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。<br>
     * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。<br>
     * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO,
     * LIFO, 优先级)执行。<br>
     * (1). newCachedThreadPool<br>
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。示例代码如下：<br>
     * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。<br>
     */
    public static void test1() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cachedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println(index);
                }
            });
        }
    }

    /**
     * (2). newFixedThreadPool<br>
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下：<br>
     * 因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。<br>
     * 定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()。
     * 可参考PreloadDataCache。<br>
     */
    public static void test2() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * (3) newScheduledThreadPool<br>
     * 创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：<br>
     * 表示延迟3秒执行。<br>
     */
    public static void test3() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {

            @Override
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);
    }

    /**
     * 定期执行示例代码如下：<br>
     * 表示延迟1秒后每3秒执行一次。<br>
     * ScheduledExecutorService比Timer更安全，功能更强大，后面会有一篇单独进行对比。<br>
     */
    public static void test4() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
            }
        }, 1, 3, TimeUnit.SECONDS);
    }

    /**
     * (4)、newSingleThreadExecutor<br>
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。示例代码如下：<br>
     * 结果依次输出，相当于顺序执行各个任务。<br>
     * 现行大多数GUI程序都是单线程的。Android中单线程可用于数据库操作，文件操作，应用批量安装，
     * 应用批量删除等不适合并发但可能IO阻塞性及影响UI线程响应的操作。<br>
     */
    public static void test5() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 子线程一般会处理这个数据结构，并且通过Handle通知主线程
     */
    public static abstract class ThreadCallBackData {

    }

    /**
     * 子线程
     */
    public static abstract class ThreadBaseRunnable implements Runnable {
        /** 线程池中处理的数据 **/
        @SuppressWarnings("unused")
        private ThreadCallBackData threadCallBackData;
        /** 回调主线程的Handler **/
        @SuppressWarnings("unused")
        private Handler handler;

        public ThreadBaseRunnable() {

        }

        public ThreadBaseRunnable(final ThreadCallBackData threadCallBackData, final Handler handler) {
            this.threadCallBackData = threadCallBackData;
            this.handler = handler;
        }

        @Override
        public void run() {
        }
    }
}
