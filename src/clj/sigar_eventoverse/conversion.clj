(ns sigar-eventoverse.conversion
  (:import [java.lang.Character]
           [org.hyperic.sigar CpuInfo CpuPerc ProcMem ProcCpu DiskUsage FileSystemUsage Tcp Cpu Mem NetStat Swap ]))

;;
;; Implementation
;;

(defn camel-to-dash
  "Converts camel-cased string to dash-separated"
  [s]
  (subs (apply str (map #(if (Character/isUpperCase %)
                           (str "-" (clojure.string/lower-case %))
                           %)
                        s))
        1))

(defn add-prefix
  [prefix val]
  (str prefix "." val))

(defn- apply-to-keys [m f]
  "Applies function f to all values in map m"
  (into {} (for [[k v] m]
             [(f k) v])))

(defn- apply-to-values [m f]
  "Applies function f to all values in map m"
  (into {} (for [[k v] m]
             [k (f v)])))

(defn array-of [t]
  (.getClass (java.lang.reflect.Array/newInstance t 0)))

;;
;; ToMap
;;

(defprotocol ToMap
  (to-map [input]))

(extend-protocol ToMap
  CpuInfo
  (to-map [^CpuInfo cpu-info]
    (apply-to-keys
     (.toMap cpu-info)
     camel-to-dash))

  CpuPerc
  (to-map [^CpuPerc cpu-perc]
    {:system.cpu.user     (.getUser cpu-perc)
     :system.cpu.sys      (.getSys cpu-perc)
     :system.cpu.idle     (.getIdle cpu-perc)
     :system.cpu.wait     (.getWait cpu-perc)
     :system.cpu.nice     (.getNice cpu-perc)
     :system.cpu.combined (.getCombined cpu-perc)
     :system.cpu.irc      (.getIrq cpu-perc)})

  Mem
  (to-map [^Mem mem]
    {:system.memory.actual_free  (.getActualFree mem)
     :system.memory.actual_used  (.getActualUsed mem)
     :system.memory.ram          (.getRam mem)
     :system.memory.free         (.getFree mem)
     :system.memory.total        (.getTotal mem)
     :system.memory.used         (.getUsed mem)
     :system.memory.used_percent (.getUsedPercent mem)
     :system.memory.free_percent (.getFreePercent mem)})

  NetStat
  (to-map [^NetStat netstat]
    {:system.netword.all_inbound_total  (.getAllInboundTotal netstat)
     :system.netword.all_outbound_total (.getAllOutboundTotal netstat)
     :system.netword.tcp_bound          (.getTcpBound netstat)
     :system.network.tcp_close          (.getTcpClose netstat)
     :system.network.tcp_close_wait     (.getTcpCloseWait netstat)
     :system.network.tcp_closing        (.getTcpClosing netstat)
     :system.network.tcp_established    (.getTcpEstablished netstat)
     :system.network.tcp_fin_wait_1     (.getTcpFinWait1 netstat)
     :system.network.tcp_fin_wait_2     (.getTcpFinWait2 netstat)
     :system.network.tcp_idle           (.getTcpIdle netstat)
     :system.network.tcp_inbound_total  (.getTcpInboundTotal netstat)
     :system.network.tcp_last_ack       (.getTcpLastAck netstat)
     :system.network.tcp_listen         (.getTcpListen netstat)
     :system.network.tcp_outbound_total (.getTcpOutboundTotal netstat)
     :system.network.tcp_syn_recv       (.getTcpSynRecv netstat)
     :system.network.tcp_syn_sent       (.getTcpSynSent netstat)
     :system.network.tcp_time_wait      (.getTcpTimeWait netstat)})

  ProcMem
  (to-map [^ProcMem proc-mem]
    {:system.process.memory.major-faults (.getMajorFaults proc-mem)
     :system.process.memory.minor-faults (.getMinorFaults proc-mem)
     :system.process.memory.page-faults  (.getPageFaults proc-mem)
     :system.process.memory.resident     (.getResident proc-mem)
     :system.process.memory.rss          (.getRss proc-mem)
     :system.process.memory.share        (.getShare proc-mem)
     :system.process.memory.size         (.getSize proc-mem)})


  ProcCpu
  (to-map [^ProcCpu proc-cpu]
    {:system.process.cpu.last_time  (.getLastTime proc-cpu)
     :system.process.cpu.percent    (.getPercent proc-cpu)
     :system.process.cpu.start_time (.getStartTime proc-cpu)
     :system.process.cpu.sys        (.getSys proc-cpu)
     :system.process.cpu.total      (.getTotal proc-cpu)
     :system.process.cpu.user       (.getUser proc-cpu)})

  Swap
  (to-map [^Swap swap]
    {:system.swap.free     (.getFree swap)
     :system.swap.page_in  (.getPageIn swap)
     :system.swap.page_out (.getPageOut swap)
     :system.swap.total    (.getTotal swap)
     :system.swap.used     (.getUsed swap)})

  DiskUsage
  (to-map [^DiskUsage disk-usage]
    {:system.disk.usage.read_bytes   (.getReadBytes disk-usage)
     :system.disk.usage.reads        (.getReads disk-usage)
     :system.disk.usage.service_time (.getServiceTime disk-usage)
     :system.disk.usage.write_bytes  (.getWriteBytes disk-usage)
     :system.disk.usage.writes       (.getWrites disk-usage)})

  Tcp
  (to-map [^Tcp tcp]
    {:system.network.tcp.active_opens  (.getActiveOpens tcp)
     :system.network.tcp.attempt_fails (.getAttemptFails tcp)
     :system.network.tcp.curr_estab    (.getCurrEstab tcp)
     :system.network.tcp.estab_resets  (.getEstabResets tcp)
     :system.network.tcp.in_errs       (.getInErrs tcp)
     :system.network.tcp.in_segs       (.getInSegs tcp)
     :system.network.tcp.out_rsts      (.getOutRsts tcp)
     :system.network.tcp.out_segs      (.getOutSegs tcp)
     :system.network.tcp.passive_opens (.getPassiveOpens tcp)
     :system.network.tcp.retrans_segs  (.getRetransSegs tcp)})

  FileSystemUsage
  (to-map [^FileSystemUsage file-system]
    {:system.fs.usage.available    (.getAvail file-system)
     :system.fs.usage.queue        (.getDiskQueue file-system)
     :system.fs.usage.read_bytes   (.getDiskReadBytes file-system)
     :system.fs.usage.reads        (.getDiskReads file-system)
     :system.fs.usage.service_time (.getDiskServiceTime file-system)
     :system.fs.usage.write_bytes  (.getDiskWriteBytes file-system)
     :system.fs.usage.writes       (.getDiskWrites file-system)
     :system.fs.usage.files        (.getFiles file-system)
     :system.fs.usage.free         (.getFree file-system)
     :system.fs.usage.free_files   (.getFreeFiles file-system)
     :system.fs.usage.total        (.getTotal file-system)
     :system.fs.usage.used         (.getUsed file-system)
     :system.fs.usage.used_percent (.getUsePercent file-system)}))
