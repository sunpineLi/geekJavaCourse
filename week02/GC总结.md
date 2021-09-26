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
> 串行回收，执行效率低    
### 2.2 UseParallelGC
> 并行回收，执行效率高
### 2.3 UseConcMarkSweepGC
> 6个阶段
### 2.4 UseG1GC
> 防止FullGC导致退化成串行化影响效率\
> 原则上不能指定G1垃圾收集器的年轻代大小,因为G1的回收方式是小批量划定区块（region）进行。
