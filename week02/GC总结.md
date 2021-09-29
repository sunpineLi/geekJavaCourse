# GC总结
## 1. 常用GC参数
- -XX:+PrintGC 打印GC日志
- -XX:+PrintGCDetails, 打印GC日志详情，比PrintGC更详细
- -XX:+PrintGCDateStamps, 打印GC时间戳（在PrintGCDetails的基础上）
- -Xloggc:文件名, 将GC日志输出到文件
> -Xms1g -Xmx1g 堆内存设置 \
> 当堆设置较小时容易频繁fullGC，甚至OutOfMemory \
> 建议-Xms与-Xmx一样大，否则初始内存分配小GC频繁
## 2. 不同GC策略
JDK8可指定的垃圾收集器(如果使用不支持的GC组合，会启动失败)：
> 1. -XX:+UseSerialGC 
> 2. -XX:+UseParallelGC *(JDK8默认)*
> 3. -XX:+UseParallelGC -XX:+UseParallelOldGC (等价-XX:+UseParallelGC)
> 4. -XX:+UseConcMarkSweepGC
> 5. -XX:+UseConcMarkSweepGC -XX:+UseParNewGC (等价-XX:+UseConcMarkSweepGC)
> 6. -XX:+UseG1GC
### 2.1 UseSerialGC
> 年轻代使用 mark-copy算法，对老年代使用 mark-sweep-compact算法。\
> 单线程，不能进行并行处理，执行效率低，只适合几百MB堆内存的JVM，而且是单核CPU时比较有用
### 2.2 UseParallelGC
> 年轻代使用 mark-copy算法，对老年代使用 mark-sweep-compact算法。\
> 两者在执行 标记和 复制/整理 阶段时都使用多个线程, 减少GC时间\
> 适用于多核服务器，主要目标是增加吞吐量，容易出现长时间的卡顿（FullGC）
### 2.3 UseConcMarkSweepGC
> 对年轻代采用并行STW方式的 mark-copy 算法 ，对老年代主要使用并发 mark-sweep 算法 \
> 设计目标是避免在老年代垃圾收集时出现长时间的卡顿 \
> 在 mark-and-sweep (标记-清除) 阶段的大部分工作和应用线程一起并发执行。
> 不对老年代进行整理，而是使用空闲列表(free-lists)来管理内存空间的回收 (老年代内存碎片问题) \
> 不可预测的暂停时间，特别是堆内存较大的情况下 \

> 6个阶段(针对老年代) \
> 阶段 1: Initial Mark(初始标记, STW) \
> 标记所有的根对象，根对象直接引用的对象，被年轻代中存活对象所引用的对象 \
> 阶段 2: Concurrent Mark(并发标记) \
> 遍历老年代，标记所有的存活对象，从前一阶段 “Initial Mark” 找到的根对象开始算起。（对象引用关系在变化） \
> 阶段 3: Concurrent Preclean(并发预清理)
> 通过“ Card（卡片）”的方式将发生了改变的区域标记为“脏”区，这些脏对象会被统计出来，他们所引用的对象也会被标记 \
> 阶段 4: Concurrent Abortable Preclean(可取消的并发预清理) \
> 尝试在 STW 的 Final Remark阶段 之前尽可能地多做一些工作 \
> 阶段 5: Final Remark(最终标记 STW) \
> 完成老年代中所有存活对象的标记\
> 阶段 6: Concurrent Sweep(并发清除)\
> 删除不再使用的对象, 回收空间\
> 阶段 7: Concurrent Reset(并发重置)
> 重置CMS算法相关的内部数据，为下一次GC循环做准备。

### 2.4 UseG1GC
> 堆不再分成年轻代和老年代，而是划分为多个（通常是2048个）可以存放对象的 小块堆区域
(smaller heap regions) \
> 每个小块，可能一会被定义成 Eden 区，一会被指定为 Survivor区或者Old区。在逻辑上，所有的Eden区和Survivor区合起来就是年轻代，所有的Old区拼在一起那就是老年代 \
> 每次GC暂停都会收集所有年轻代的内存块，但一般只包含部分老年代的内存块 \
> 在并发阶段估算每个小堆块存活对象的总数。构建回收集的原则是：垃圾最多的小块会被优先收集。(G1名称的由来)。

> 防止FullGC导致退化成串行化影响效率 \
> 原则上不能指定G1垃圾收集器的年轻代大小, 因为G1的回收方式是小批量划定区块（region）进行。
